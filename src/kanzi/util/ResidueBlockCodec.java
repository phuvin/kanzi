/*
Copyright 2011 Frederic Langlet
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
you may obtain a copy of the License at

                http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package kanzi.util;

import kanzi.BitStream;
import kanzi.entropy.ExpGolombDecoder;
import kanzi.entropy.ExpGolombEncoder;


// Encoder/decoder for residue block (post transfom+quantize steps)
public final class ResidueBlockCodec
{
    private final int width;
    private final int height;
    private final int blockDim;
    private final int scoreThreshold;
    private final BitStream stream;
    private final ExpGolombEncoder gEncoder;
    private final ExpGolombDecoder gDecoder;
    private final int rleThreshold;
    private final int maxNonZeros;
    private final int logMaxNonZeros;

    private static final int MAX_NON_ZEROS = 32;
    private static final byte SCAN_H  = 0;
    private static final byte SCAN_V  = 1;
    private static final byte SCAN_Z  = 2;
    private static final byte SCAN_HV = 3;

    // 8x8 block scan tables
    public static final int[][] SCAN_TABLES =
    {
        // SCAN_H : horizontal
        {  0,  1,  2,  3,  4,  5,  6,  7,
           8,  9, 10, 11, 12, 13, 14, 15,
          16, 17, 18, 19, 20, 21, 22, 23,
          24, 25, 26, 27, 28, 29, 30, 31,
          32, 33, 34, 35, 36, 37, 38, 39,
          40, 41, 42, 43, 44, 45, 46, 47,
          48, 49, 50, 51, 52, 53, 54, 55,
          56, 57, 58, 59, 60, 61, 62, 63
        },
        // SCAN_V : vertical
        {  0,  8, 16, 24, 32, 40, 48, 56,
           1,  9, 17, 25, 33, 41, 49, 57,
           2, 10, 18, 26, 34, 42, 50, 58,
           3, 11, 19, 27, 35, 43, 51, 59,
           4, 12, 20, 28, 36, 44, 52, 60,
           5, 13, 21, 29, 37, 45, 53, 61,
           6, 14, 22, 30, 38, 46, 54, 62,
           7, 15, 23, 31, 39, 47, 55, 63
        },
        // SCAN_Z : zigzag
        {  0,  1,  8,  2, 16,  3, 24,  4,
          32,  5, 40,  6, 48,  7, 56,  9,
          10, 17, 11, 25, 12, 33, 13, 41,
          14, 49, 15, 57, 18, 19, 26, 20,
          34, 21, 42, 22, 50, 23, 58, 27,
          28, 35, 29, 43, 30, 51, 31, 59,
          36, 37, 44, 38, 52, 39, 60, 45,
          46, 53, 47, 61, 54, 55, 62, 63
        },
        // SCAN_VH : mix vertical + horizontal
        {  0,  1,  8, 16,  9,  2,  3, 10,
          17, 24, 32, 25, 18, 11,  4,  5,
          12, 19, 26, 33, 40, 48, 41, 34,
          27, 20, 13,  6,  7, 14, 21, 28,
          35, 42, 49, 56, 57, 50, 43, 36,
          29, 22, 15, 23, 30, 37, 44, 51,
          58, 59, 52, 45, 38, 31, 39, 46,
          53, 60, 61, 54, 47, 55, 62, 63
        }
    };

    private static final int[] SCAN_TABLE_H  = SCAN_TABLES[SCAN_H];
    private static final int[] SCAN_TABLE_V  = SCAN_TABLES[SCAN_V];
    private static final int[] SCAN_TABLE_Z  = SCAN_TABLES[SCAN_Z];
    private static final int[] SCAN_TABLE_HV = SCAN_TABLES[SCAN_HV];


    public ResidueBlockCodec(int width, int height, int blockDim, int threshold, BitStream stream)
    {
        this(width, height, blockDim, threshold, stream, MAX_NON_ZEROS);
    }
    
    
    public ResidueBlockCodec(int width, int height, int blockDim, int threshold, 
            BitStream stream, int maxNonZeros)
    {
        if (width < 8)
            throw new IllegalArgumentException("The width parameter must be at least 8");

        if (height < 8)
            throw new IllegalArgumentException("The width parameter must be at least 8");

        if ((blockDim != 8) && (blockDim != 16))
            throw new IllegalArgumentException("The block dimension must be either 8 or 16");

        if ((maxNonZeros & (maxNonZeros - 1)) != 0)
            throw new IllegalArgumentException("Maximum number of non zero coefficients "
                    + "must be a power of 2");

        this.blockDim = blockDim;
        this.scoreThreshold = threshold;
        this.stream = stream;
        this.gEncoder = new ExpGolombEncoder(this.stream, false);
        this.gDecoder = new ExpGolombDecoder(this.stream, false);
        this.rleThreshold = 5;
        this.width = width;
        this.height = height;      
        int log = 0;

        for (int n=maxNonZeros+1; n>1; n>>=1)
          log++;

        this.logMaxNonZeros = log;
        this.maxNonZeros = maxNonZeros;
    }


    // Find scan order that minimizes the size of the output
    // Bit encode the residue data
    public boolean encode(int[] data, int blkptr)
    {
       boolean res = true;

       // Test horizontal scan order
       final int resH = this.getStatistics(data, blkptr, SCAN_TABLE_H);
       final int nonZeros = (resH >> 16) & 0xFF;
       final int skipH = resH & 0x01;

       if (skipH == 1)
       {
          // Skip block: write '1'
          res &= this.stream.writeBit(1);
          return res;
       }

       final int endH = (resH >> 8) & 0xFF;
       final boolean allSameSign = (((resH >> 1) & 0x01) == 1) ? true : false;

       // Test vertical scan order
       final int resV = this.getStatistics(data, blkptr, SCAN_TABLE_V);
       final int skipV = resV & 0x01;

       if (skipV == 1)
       {
          // Skip block: write '1'
          res &= this.stream.writeBit(1);
          return res;
       }

       final int endV = (resV >> 8) & 0xFF;

       // Test zigzag scan order
       final int resZ = this.getStatistics(data, blkptr, SCAN_TABLE_Z);
       final int skipZ = resZ & 0x01;

       if (skipZ == 1)
       {
          // Skip block: write '1'
          res &= this.stream.writeBit(1);
          return res;
       }

       final int endZ = (resZ >> 8) & 0xFF;

       // Test horizontal+vertical scan order
       final int resHV = this.getStatistics(data, blkptr, SCAN_TABLE_HV);
       final int skipHV = resHV & 0x01;

       if (skipHV == 1)
       {
          // Skip block: write '1'
          res &= this.stream.writeBit(1);
          return res;
       }
       
       final int endHV = (resHV >> 8) & 0xFF;
       final int min1 = (endH < endV) ? endH : endV;
       final int min2 = (endZ < endHV) ? endZ : endHV;
       final int min = (min1 < min2) ? min1 : min2;
       byte scan_order = SCAN_HV;

       if (min == endH)
          scan_order = SCAN_H;
       else if (min == endV)
          scan_order = SCAN_V;
       else if (min == endZ)
          scan_order = SCAN_Z;

       return this.encodeDirectional(data, blkptr, allSameSign, nonZeros, scan_order);
    }


    private int getStatistics(int[] data, int blkptr, int[] scanTable)
    {
       int idx = 1; // skip DC coefficient
       int end = (this.blockDim * this.blockDim) - 1;
       int score = 0;
       int max = 0;
       int nonZeros = 0;
       int allPositives = 1;
       int allNegatives = 1;

       // Find last non zero coefficient
       while ((end > 0) && (data[blkptr+scanTable[end]] == 0))
          end--;

       while (idx <= end)
       {
          int val = data[blkptr+scanTable[idx++]];

          if (val == 0)
          {
              // Detect runs of 0
              while ((idx < end) && (data[blkptr+scanTable[idx]] == 0))
                 idx++;
          }
          else
          {
             allPositives &= (1 - (val >>> 31));
             allNegatives &= (val >>> 31);
             val = (val + (val >> 31)) ^ (val >> 31); //abs
             score += val;
             nonZeros++;

             if (val > max)
                max = val;
             
             // Limit non zeros coefficients, ignore others
             if (nonZeros > this.maxNonZeros)
             {
                end = idx;            
                break;
             }
          }
       }

       final int skip = ((max <= 1) && (score < this.scoreThreshold)) ? 1 : 0;
       final int allSameSign = allPositives | allNegatives;
       return ((nonZeros & 0xFF) << 16) | ((end & 0xFF) << 8) | (allSameSign << 1) | skip;
    }


    private boolean encodeDirectional(int[] data, int blkptr, boolean allSameSign, 
            int nonZeros, byte scan_order)
    {
       // Encode scan order
       if (this.stream.writeBits(scan_order, 3) != 3)
          return false;

       // Encode number of non-zero coefficients
       if (this.stream.writeBits(nonZeros-1, this.logMaxNonZeros) != this.logMaxNonZeros)
          return false;

       int writeSigns = nonZeros;
       
//       if ((nonZeros > 1) && (nonZeros <= 6))
//       {
//         // If there is a 'decent' choice of success (nonZeros not too high),
//         // store the fact that all coefficients have the same sign (or not)           
//         if (this.stream.writeBit((allSameSign == true) ? 1 : 0) == false)
//           return false;
//         
//         writeSigns = (allSameSign == true) ? 1 : nonZeros;
//       }
       
       int idx = 0;
       boolean res = true;
       int run = 0;
       final int[] scanTable = SCAN_TABLES[scan_order];

       // Encode a small value as a repetition of '1' followed by '0' and sign.
       // Encode a small run of 0s as a repetition of '0'.
       // Encode large values or runs as a mix of repeated bits & exp-golomb encoding
       // A 'large' value follows value >= rleThreshold
       // EG: 4 => 111100, -2 => 1101
       // EG: 0,0,0,0 => 0000
       // EG: 11 (rleThreshold = 5) => 5+6 or 11111 00111 0
       // EG: 00000000000 (rleThreshold = 5) => (5+6)*0 or 00000 00111 (no sign)
       // EG: 1, 0, -3, 2, 0, 0, 0, 0, 0, 0, 0, 1 => 100 0 11101 1100 0 0 0 0 0 011 100
       while ((nonZeros > 0) && (res == true))
       {
         int val = data[blkptr+scanTable[idx]];
         idx++;

         if (val == 0)
         {
            run++;
            continue;
         }

         if (run > 0)
         {
           // If the run is too long, use a mix of bit counting & Exp-Golomb encoding
           int remainder = run - this.rleThreshold;

           // Encode run as 0s
           if (remainder >= 0)
             run = this.rleThreshold;

           while (run-- > 0)
             res &= this.stream.writeBit(0);

           if (remainder >= 0)
             res &= this.gEncoder.encodeByte((byte) remainder);
         }

         final int sign = val >>> 31;
         val = (val + (val >> 31)) ^ (val >> 31); //abs
         int remainder = val - this.rleThreshold;

         if (remainder >= 0)
           val = this.rleThreshold;

         // Encode value as 1s
         while (val-- > 0)
           res &= this.stream.writeBit(1);

         if (remainder >= 0)
           res &= this.gEncoder.encodeByte((byte) remainder);
         else
           res &= this.stream.writeBit(0); //end of value

         if (writeSigns-- > 0)
           res &= this.stream.writeBit(sign);
         
         nonZeros--;
       }

       return res;
    }


    // Decode the residue data, output to provided array
    public boolean decode(int[] data, int blkptr)
    {
       final int end = blkptr + this.width * this.height;
       final int dim2 = this.blockDim * this.blockDim;

       while (blkptr < end)
       {
          if (this.stream.readBit() == 1)
          {
              // Block skipped
              final int endi = blkptr + dim2;

              for (int i=blkptr; i<endi; i+=8)
              {
                 data[i]   = 0;
                 data[i+1] = 0;
                 data[i+2] = 0;
                 data[i+3] = 0;
                 data[i+4] = 0;
                 data[i+5] = 0;
                 data[i+6] = 0;
                 data[i+7] = 0;
              }

              blkptr = endi;
              continue;
          }

          // Decode scan order
          int scan_order = this.stream.readBit();
          scan_order <<= 1;
          scan_order |= this.stream.readBit();
          final int[] scanTable = SCAN_TABLES[scan_order];

          // Decode number of non-zero coefficients
          int nonZeros = (int) (1 + this.stream.readBits(this.logMaxNonZeros));
          int idx = 0;
          int counter = 1;
          int readSigns = nonZeros;
          
//          if ((nonZeros > 1) && (nonZeros <= 6))
//          {
//             // Check if all coefficients have a sign or only the first one
//             if (this.stream.readBit() == 1)
//                readSigns = 1;
//          }
              
          int val = this.stream.readBit();

          while (nonZeros > 0)
          {
             // If val == 0, reading a run or end of a value
             // Otherwise, reading a value
             while ((this.stream.readBit() == val) && (counter < this.rleThreshold))
                counter++;

             int nextCounter = 1 - val;

             if (counter == this.rleThreshold)
             {
                // Decode the exp-golomb encoded remainder
                counter += this.gDecoder.decodeByte();
                nextCounter = 0;
             }

             if (val == 1)
             {
                // If reading a value, need to get the sign
                if ((readSigns-- > 0) && (this.stream.readBit() == 1))
                   counter = -counter;

                // Output a value
                data[blkptr+scanTable[idx++]] = counter;
                nonZeros--;
             }
             else
             {
                // Output a run of 0s
                while (counter-- > 0)
                   data[blkptr+scanTable[idx++]] = 0;
             }

             counter = nextCounter;
             val ^= 1;
          }

          // Add remaining 0s
          while (idx < dim2)
             data[blkptr+scanTable[idx++]] = 0;

          blkptr += dim2;
       }

       return true;
    }
}
