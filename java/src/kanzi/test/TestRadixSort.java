/*
Copyright 2011, 2012 Frederic Langlet
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

package kanzi.test;

import kanzi.util.sort.RadixSort;


public class TestRadixSort 
{
    public static void main(String[] args)
    {
        System.out.println("TestRadixSort");

        // Test behavior
        for (int ii=1; ii<20; ii++)
        {
            System.out.println("\n\nTest "+ii);
            int idx = ii;
            final int[] array = new int[64];
            java.util.Random random = new java.util.Random();

            for (int i=idx; i<array.length; i++)
                array[i] = 64+(random.nextInt() & 31);

            byte[] b = new byte[array.length];

            for (int i=idx; i<b.length; i++)
                b[i] = (byte) (array[i] & 255);

            System.out.println(new String(b));

            for (int i=idx; i<array.length; i++)
                System.out.print(array[i]+" ");

            System.out.println();
            
            // Alternate radix 1, 2, 4 & 8
            int radix = 1 << (ii & 3);
            System.out.println("Radix "+radix);
            new RadixSort(radix).sort(array, idx);

            for (int i=idx; i<b.length; i++)
                b[i] = (byte) (array[i] & 255);

            System.out.println(new String(b));

            for (int i=idx; i<array.length; i++)
            {
                System.out.print(array[i]+" ");
                
                if ((i > 0) && (array[i] < array[i-1]))
                {
                   System.err.println("Error at position "+(i-idx));
                   System.exit(1);
                }
            }
        }      

        // Test speed -- radix 4
        {
            System.out.println("\n\nSpeed test (radix 4)");
            int[] array = new int[10000];
            int[] array2 = new int[10000];
            int[] rnd = new int[10000];
            java.util.Random random = new java.util.Random();
            long before, after;

            RadixSort sorter = new RadixSort(4);

            for (int k=0; k<5; k++)
            {
                long sum = 0;
                long sum2 = 0;

                for (int i=0; i<rnd.length; i++)
                    rnd[i] = Math.abs(random.nextInt());

                for (int ii=0; ii<10000; ii++)
                {
                    for (int i=0; i<array.length; i++)
                        array[i] = rnd[i] & 255;

                     for (int i=0; i<array.length; i++)
                        array2[i] = rnd[i] & 255;

                    before = System.nanoTime();
                    sorter.sort(array, 0);
                    after = System.nanoTime();
                    sum += (after - before);
                    before = System.nanoTime();
                    java.util.Arrays.sort(array2);
                    after = System.nanoTime();
                    sum2 += (after - before);
                }

                System.out.println("Elapsed radix 4 sort [ms] : "+sum/1000000);
                System.out.println("Elapsed arrays.sort [ms]  : "+sum2/1000000);
            }
        }
    
        // Test speed -- radix 8
        {
            System.out.println("\n\nSpeed test (radix 8)");
            int[] array = new int[10000];
            int[] array2 = new int[10000];
            int[] rnd = new int[10000];
            java.util.Random random = new java.util.Random();
            long before, after;

            RadixSort sorter = new RadixSort(8);

            for (int k=0; k<5; k++)
            {
                long sum = 0;
                long sum2 = 0;

                for (int i=0; i<rnd.length; i++)
                    rnd[i] = Math.abs(random.nextInt());

                for (int ii=0; ii<10000; ii++)
                {
                    for (int i=0; i<array.length; i++)
                        array[i] = rnd[i] & 255;

                     for (int i=0; i<array.length; i++)
                        array2[i] = rnd[i] & 255;

                    before = System.nanoTime();
                    sorter.sort(array, 0);
                    after = System.nanoTime();
                    sum += (after - before);
                    before = System.nanoTime();
                    java.util.Arrays.sort(array2);
                    after = System.nanoTime();
                    sum2 += (after - before);
                }

                System.out.println("Elapsed radix 8 sort[ms] : "+sum/1000000);
                System.out.println("Elapsed arrays.sort [ms] : "+sum2/1000000);
            }
        }
    }
}
