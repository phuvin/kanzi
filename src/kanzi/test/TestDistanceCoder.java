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

import java.util.Arrays;
import kanzi.IndexedByteArray;
import kanzi.function.DistanceCodec;


public class TestDistanceCoder
{
     private static byte[] DATA = new byte[] 
     {
        -125, -124, -123, -122, -121, -120, -119, 89, 16, 1, 1, 0, 18, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, 55, 16, 1, 1, 52, 52, 0, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, -110, 16, 1, 1, 13, 13, 13, 13, 13, 33, 3, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0, 13, 13, 13, 13, 13, 13, 13,
        13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 18, 13, 13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 1, 1, 0, 0, 0, 0, 0, 18, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 59, 16, 1, 1, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 1, 1, 101, 101, 101, 54, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 46, 46, 48, 116, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115,
        115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 41, 50, 110, 0, 0, 0, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 1, 1, 77, 77, 0, 0, 0, 0, 0, 0, 0, 18, 18, 0, 0, 0, 0, 16, 1, 1, 0, 0, 18, 0, 0, 96, 0, -32, -32, 16, 89, 89, 1, 1, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 89, 0, 89, 89, 89, 89, 89, 89, 89, 89, 18, 89, 89, 89, 89, 89, 89, 89, 89,
        89, 89, 89, 89, 89, 89, -28, 0, 0, 0, 0, 16, 1, 1, 0, 0, 18, 4, 4, 4, 3, 4, 3, 4, 3, 16, 3, 4, 3, 1, 1, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 4, 3, 4, 3, 4, 3, 4, 3, 4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 3, -9, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 0, 4, 3, 4, 3, 4, 3, 4, 3, 4, 4, 3, 3, 4, 3, 3, 4, 4, 3, 4, 3, 4, 18, 3, 4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 0, 3, 4, 3, 4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 3, 4, 3,
        4, 4, 3, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 4, 3, 16, 1, 1, 0, 0, 18, 0, 0, 0, 113, 113, 0, 16, 1, 1, 0, 18, 0, 16, 1, 1, 18, 18, 16, 1, 0, 18, 18, 16, 1, 1, 18, 16, 1, 1, 0, 18, 16, 1, 1, 18, 16, 1, 1, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 0, 16, 1, 1, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 18, 16, 1, 1, 50, 58, 58, 58, 58, 109, 114, 101, 44, 44, 115, 58, 58, 110, 110, 110, 97, 114, 97, 18, 73, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 108, 117, 108, 115, 107, 111, 104, 111, 101, 100, 100, 101, 114, 84, 103, 76, 115, 101, 108, 108, 115, 103, 119, 110, 112, 117, 116, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58,
        58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 58, 101, 101, 98, 112, 115, 100, 114, 101, 119, 119, 116, 115, 108, 116, 110, 112, 100, 121, 101, 101, 100, 108, 115, 100, 116, 116, 16, 1, 1, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 18, 16, 1, 1, 18, 16, 1, 1, 3, 3, 22, 18, 32, 16, 1, 1, 46, 18, 18, 40, 40, 40, 1, 16, 1, 18, 5, 16, 1, 18, 1, 1, 16, 1, 115, 115, 14, 9, 14, 9, 18, 16, 1, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 112, 100, 76, 65, 65, 84, 112, 18, 112, 110, 110, 110, 110, 116, 110, 110, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 48, 48, 1, 1, 16, 1, 99, 99, 99, 49, 54, 54, 49, 49, 49, 84, 18, 18, 100, 114, 114, 114, 109, 101, 116, 114, 119, 114, 97, 101, 109, 120, 110, 101, 117, 110, 101, 100, 117, 109, 101, 101, 103, 116, 108, 120, 101, 101, 114, 100, 114, 108, 101, 110, 101, 121, 101, 1, 16, 1, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103,
        103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 103, 108, 70, 103, 103, 103, 70, 18, 18, 115, 115, 110, 110, 110, 115, 121, 121, 121, 97, 97, 97, 108, 108, 108, 109, 109, 109, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120,
        120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 120, 97, 1, 16, 1, 46, 49, 49, 18, 18, 46, 46, 0, 0, 0, 16, 1, 32, 32, 32, 32, 95, 95, 18, 18, 1, 16, 1, 1, 99, 99, 18, 16, 1, 18, 16, 1, 0, 0, 18, 18, 16, 1, 18, 18, 16, 1, 46, 46, 46, 18, 0, 16, 1, 0, 18, 18, 18, 16, 1, 18, 18, 44, 16, 18, 44, 44, 44, 16, 1, 110, 110, 110, 121, 101, 101, 114, 114, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 18, 116, 16, 1, 116, 18, 18, 0, 16, 1, 18, 6, 6, 16, 1, 18, 100, 116, 116, 16, 1, 18, 0, 16, 1, 18,
        16, 1, 18, 16, 84, 84, 77, 32, 18, 13, 14, 6, 32, 20, 74, 116, 16, 18, 18, 115, 108, 108, 117, 12, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 18, 45, 12, 17, 15, 14, 111, 107, 107, 101, 111, 74, 101, 111, 100, 100, 100, 45, 16, 1, 18, 74, 74, 4, 4, 74, 45, 116, 10, 16, 1, 18, 12, 115, 15, 14, 74, 10, 77, 77, 74, 32, 16, 1, 78, 78, 73, 77, 18, 11, 7, 116, 100, 116, 108, 114, 74, 19, 19, 108, 74, 115, 9, 12, 16, 1, 18, 13, 24, 11, 16, 1, 18, 14, 14, 16, 1, 80, 78, 45, 45, 18, 18, 10, 10, 10, 32, 32, 32, 107, 14, 74, 117, 117, 117, 16, 1, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 18, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47,
        32, 32, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 80, 80, 80, 80, 16, 1, 18, 16, 45, 17, 9, 16, 1, 77, 77, 18, 74, 8, 6, 74, 47, 5, 13, 91, 91, 16, 1, 0, 0, 47, 0, 0, 46, 84, 84, 18, 70, 74, 112, 74, 120, 110, 74, 45, 5, 12, 11, 32, 32, 32, 12, 115, 16, 1, 73, 73, 65, 18, 18, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
        10, 100, 16, 1, 18, 47, 47, 47, 114, 101, 119, 119, 74, 14, 11, 16, 1, 65, 0, 10, 47, 18, 114, 116, 100, 116, 108, 110, 74, 74, 12, 32, 11, 74, 74, 16, 1, 18, 18, 9, 16, 1, 18, 18, 74, 74, 6, 32, 116, 32, 16, 1, 18, 3, 4, 5, 1, 7, 8, 9, 11, 12, 14, 15, 17, 19,
        21, 22, 23, 24, 27, 28, 30, 31, 32, 34, 35, 37, 38, 39, 41, 42, 43, 46, 47, 48, 49, 50, 52, 53, 2, 55, 56, 57, 59, 60, 62, 64, 66, 67, 69, 71, 73, 74, 75, 78, 80, 81, 82, 83, 85, 86, 89, 90, 91, 93, 95, 97, 99, 98, 102, 104, 106, 107, 109, 111, 112, 113, 116, 117, 118, 119, 120, 122, 123, 125, 127, -127, -126, 36, -124, -122, -121, -118, -117, -116, -115, -114, -113, -112, -111, 69, 83, 6, 54, 3, 4, 5, 7, 8, 10, 11, 13, 14, 16, 18, 20, 21, 22, 25, 26, 27, 29, 30, 31, 33, 34, 35, -125, 37, 38, 40, 41, 44, 45, 46, 47, 48, 49, 51, 52, 53, 55, 56, 58, 59, 61, 63, 65, 66, 68, 70, 72, 73, 76, 77, 78, 79, 81, 82, 84, 87, 88, 89, 90, 92, 94, 96, 97, 101, 100, 103, 105, 106, 108, 110, 111, 114, 115, 116, 117, 118, 119, 121, 122, 124, 126, -128, -127, -126, -123, -120, -119, -118, -117, -116, -115, -114, -113, -112, -111, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
        83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 74, 74, 74, 74, 32, 10, 10, 10, 74, 74, 40, 32, 32, 45, 83, 16, 1, 87, 87, 83, 69, 69, 72, 72, 18, 74, 74, 74, 100, 74, 74, 45, 45, 115, 74, 74, 74, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 16, 1, 18, 41, 41, 16, 1, 18, 45, 45, 45, 45, 45, 16, 1, 18, 65, 65, 74, 116, 16, 1, 18, 43, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
        45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 16, 83, 83, 1, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83,
        83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 83, 18, 18, 16, 1, 18, 18, 16, 1, 19, 91, 18, 41, 16, 1, 18, 16, 1, 18, 16, 1, 18, 16, 1, 48, 48, 18, 121, 16, 1, -14, 18, 16, 1, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 101, 118, 118, 118, 118, 18, 18, 116, 116, 116, 84, 108, 108, 76, 108, 108, 108, 116, 116, 116, 115, 100, 116, 122, 84, 110, 116, 47, 47, 47, 47, 47, 47, 6, 101, 101, 101, 101, 101, 114, 82, 82, 114, 114, 112, 112, 110, 110, 112, 112, 114, 119, 119, 82,
        82, 116, 116, 116, 101, 99, 100, 116, 110, 110, 110, 110, 110, 5, 110, 105, 105, 105, 114, 114, 114, 114, 114, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 109, 109, 119, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 112, 80, 80, 80, 80, 80, 80, 112, 80, 108, 108, 108, 77, 114, 100, 100, 99, 115, 114, 108, 112, 98, 98, 98, 98, 98, 98, 98, 98, 98, 66, 66, 66, 66, 112, 112, 112, 70, 101, 112, 112, 5, 118, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108,
        108, 108, 112, 80, 112, 80, 108, 108, 110, 99, 101, 68, 108, 99, 99, 116, 99, 116, 99, 116, 110, 114, 114, 109, 115, 108, 108, 66, 98, 105, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 74, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 12, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 108, 108, 97, 97, 1, 97, 18, 108, 117, 32, 32, 108, 116, 115, 32, 32, 114, 97, 98, 109, 109, 109, 114, 97, 97, 97, 97, 97, 97, 109, 109, 101, 115, 108, 102, 115, 105, 105, 105, 105, 105, 105, 109, 79, 79, 79, 97, 97, 105, 97, 97, 97,
        97, 97, 97, 97, 97, 109, 109, 109, 109, 107, 32, 32, 111, 65, 4, 101, 111, 32, 32, 104, 32, 117, 117, 32, 32, 16, 1, 110, 110, 110, 114, 114, 18, 105, 6, 110, 105, 105, 105, 105, 97, 97, 97, 97, 97, 97, 114, 115, 115, 114, 114, 114, 99, 99, 99, 99, 99, 99, 105, 105, 105, 105, 105, 105, 9, 8, 7, 116, 13, 11, 115, 101, 32, 32, 114, 115, 115, 115, 115, 101, 101, 101, 101, 101, 101, 101, 105, 101, 101, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 46, 9, 13, 12, 13, 12, 54, 0, 0, 9, 8, 32, 116, 103, 32, 105, 105, 105, 108, 32, 12, 13, 115, 115, 83, 83, 115, 115, 105, 105, 105, 101, 101, 101, 101, 101, 101, 97, 101, 101, 111, 111, 110, 101, 101, 101, 110, 108, 108, 101, 101, 101, 101, 101, 101, 108, 101, 1, 101, 101, 108, 101, 114, 101, 101, 108, 108, 108, 110, 101, 101, 101, 114,
        108, 101, 18, 101, 110, 111, 110, 110, 101, 101, 111, 111, 111, 95, 111, 110, 100, 100, 97, 97, 97, 105, 97, 97, 105, 32, 12, 12, 11, 11, 110, 6, 97, 97, 97, 97, 14, 13, 101, 101, 69, 108, 108, 110, 110, 110, 110, 110, 110, 110, 110, 110, 101, 101, 114, 116, 110, 108, 118, 108, 116, 110, 110, 108, 110, 108, 108, 108, 108, 110, 108, 110, 110, 108, 110, 108, 110, 110, 101, 110, 110, 110, 110, 110, 109, 110, 117, 108, 116, 100, 108, 116, 110, 116, 110, 109, 110, 108, 100, 116, 110, 109, 103, 1, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 109, 108, 108, 108, 108, 108, 100, 103, 103, 108, 101, 109, 110, 109, 110, 110, 110, 110, 110, 100, 108, 108, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109,
        99, 108, 100, 18, 114, 104, 104, 104, 104, 104, 110, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 66, 114, 103, 108, 108, 112, 112, 112, 112, 104, 104, 104, 104, 104, 106, 106, 106, 108, 108, 108, 114, 114, 115, 116, 116, 109, 100, 115, 107, 115, 105, 108, 108, 100, 114, 105, 116, 100, 108, 108, 98, 116, 98, 98, 8, 7, 114, 98, 114, 114, 116, 114, 114, 114, 114, 114, 114, 122, 122, 122, 122, 122, 116, -68, 76, 108, 108, 108, 98, 98, 110, 98, 98, 110, 98, 102, 102, 102, 105, 105, 105, 115, 115, 115, 98, 98, 110, 108, 116, 116, 116, 116, 116, 116, 116, 116, 11, 10, 6, 109, 109, 108, 108, 108, 108, 116, 116, 116, 104, 109, 7, 99, 99, 86, 86, 116, 70, 110, 110, 105, 109, 110, 110, 109, 114, 114, 109, 109, 109, 110, 116, 109, 109, 109, 109, 109, 77, 77, 77, 77, 77, 77, 109, 109, 77, 77, 77, 77, 115, 83, 116, 100,
        115, 110, 100, 108, 100, 108, 100, 115, 115, 100, 115, 110, 116, 110, 100, 116, 114, 98, 110, 100, 108, 98, 121, 121, 98, 98, 98, 98, 66, 98, 110, 116, 116, 116, 116, 116, 116, 116, 86, 86, 86, 108, 117, 118, 103, 99, 99, 99, 103, 68, 100, 103, 66, 66, 114, 100, 100, 68, 68, 100, 100, 82, 114, 114, 114, 114, 114, 114, 114, 114, 114, 99, 99, 99, 99, 99, 99, 102, 71, 108, 103, 103, 103, 71, 100, 116, 105, 32, 20, 10, 8, 116, 116, 116, 116, 84, 84, 84, 84, 79, 111, 79, 111, 111, 79, 111, 79, 1, 18, 117, 103, 115, 115, 105, 105, 105, 105, 105, 105, 12, 11, 6, 117, 10, 8, 9, 7, 110, 9, 7, 6, 32, 116, 5, 32, 108, 32, 105, 105, 112, 32, 97, 110, 111, 110, 1, 110, 110, 111, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 110, 18, 110, 105, 97, 97, 105, 97, 97, 105, 11, 97,
        97, 111, 111, 111, 111, 111, 111, 10, 9, 103, 103, 103, 110, 103, 103, 110, 116, 97, 111, 111, 111, 111, 111, 110, 99, 99, 99, 99, 99, 99, 112, 1, 115, 18, 115, 119, 32, 6, 32, 110, 99, 67, 67, 99, 99, 99, 99, 99, 97, 97, 97, 97, 99, 99, 99, 99, 99, 10, 115, 99, 99, 99, 99, 13, 1, 18, 100, 68, 68, 101, 115, 115, 115, 115, 115, 115, 115, 116, 102, 102, 102, 102, 101, 101, 101, 101, 101, 101, 117, 112, 32, 9, 77, 77, 77, 108, 108, 102, 108, 70, 70, 70, 115, 118, 110, 99, 99, 99, 99, 110, 114, 114, 101, 101, 101, 116, 104, 104, 66, 66, 101, 101, 102, 102, 116, 98, 98, 98, 98, 98, 98, 102, 101, 116, 109, 98, 13, 119, 119, 119, 87, 87, 119, 108, 108, 108, 108, 76, 97, 97, 97, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 119, 115, 115, 60, 60, 112, 14, 47, 47, 47,
        13, 101, 101, 100, 100, 100, 100, 116, 116, 116, 116, 116, 116, 116, 116, 115, 115, 115, 116, 116, 116, 116, 116, 116, 116, 116, 100, 100, 118, 116, 118, 108, 108, 108, 108, 76, 76, 101, 101, 101, 76, 76, 108, 108, 110, 110, 108, 100, 100, 117, 32, 32, 116, 77, 84, 84, 110, 110, 110, 100, 108, 114, 114, 110, 101, 108, 108, 108, 108, 108, 108, 116, 115, 114, 1, 18, 18, 16, 76, 76, 28, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 98, 98, 98, 1, 99, 99, 99, 99, 18, 99, 99, 110, 115, 115, 115, 115, 115, 115, 117, 108, 97, 104, 97, 101, 97, 101, 111, 97, 104, 1, 111, 108, 101, 97, 108, 111, 101, 101, 105, 97, 97, 97, 108, 111, 101, 97, 108, 18, 5, 9, 8, 10, 9, 47, 47, 47, 108, 71, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
        99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 103, 103, 99, 99, 80, 100, 100, 12, 11, 111, 108, 108, 109, 101, 101, 101, 105, 101, 101, 101, 105, 105, 105, 98, 100, 98, 98, 98, 98, 105, 98, 98, 98, 108, 105, 103, 103, 105, 103, 103, 112, 98, 116, 116, 103, 103, 105, 101, 101, 101, 98, 101, 101, 108, 102, 115, -68, 103, 101, 112, 112, 112, 108, 117, 108, 97, 112, 97, 115, 83, 103, 101, 32, 105, 32, 98, 32, 4, 9, 8, 112, 112, 112, 105, 105, 105, 105, 105, 105, 108, 111, 111, 111, 111, 111, 111, -68, 101, 111, 105, 111, 111, 111, 97, 97, 97, 111, 111, 102, 102, 102, 102, 108, 105, 117, 111, 117, 117, 111, 111, 104, 101, 111, 101, 111, 101, 101, 1, 114, 101, 101, 101, 111, 111, 111, 18, 19, 18, 114, 101, 101, 101, 101, 101, 111, 111, 111, 111, 121, 97, 97, 97, 105, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97,
        97, 97, 97, 97, 97, 114, 9, 8, 104, 101, 110, 101, 101, 101, 112, 4, 32, 8, 9, 7, 8, 114, 116, 117, 117, 5, 115, 32, 73, 73, 73, 111, 111, 111, 111, 111, 101, 101, 101, 117, 9, 13, 15, 111, 119, 101, 111, 111, 111, 111, 111, 111, 111, 111, 101, 111, 1, 111, 117, 117, 117, 109, 111, 111, 111, 111, 111, 111, 111, 111, 111, 111, 117, 117, 117, 111, 111, 111, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 111, 111, 18, 101, 97, 97, 114, 114, 114, 114, 114, 114, 105, 105, 73, 73, 73, 117, 97, 97, 97, 97, 101, 101, 97, 105, 117, 101, 101, 105, 105, 105, 105, 105, 105, 97, 97, 97, 105, 97, 97, 97, 97, 97, 97, 97, 97, 97, 105, 97, 97, 97, 97, 105, 105, 97, 97, 97, 97, 97, 97, 105, 97, 97, 101, 111, 111, 111, 111, 111, 105, 105, 105, 110, 110, 73, 105, 117, 105, 117, 105, 105, 105, 105, 105,
        105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 105, 97, 97, 97, 105, 105, 105, 117, 109, 97, 111, 105, 105, 111, 111, 111, 104, 117, 111, 105, 73, 32, 107, 111, 111, 111, 111, 97, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 111, 111, 111, 111, 101, 101, 101, 101, 111, 105, 105, 105, 105, 73, 105, 117, 105, 111, 111, 111, 97, 101, 97, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 97, 69, 105, 97, 65, 65, 101, 101, 101, 101, 102, 116, 84, 1, 98, 105, 98, 105, 105, 18, 18, 98, 105, 102, 102, 109, 67, 67, 110, 78, 32, 32, 32, 32, 108, 108, 108, 116, 116, 116, 84, 116, 114, 114, 114, 114, 114, 98, 111, 111, 111, 111, 114, 114, 114, 114, 99, 114, 114, 114, 114, 114, 99, 99, 111, 99, 99, 114, 114, 99, 99, 99, 75, 99, 67, 99, 99, 99, 99, 99, 75, 116, 116, 116, 99, 116, 99, 116, 116, 118, 118, 105, 105, 105, 105,
        105, 105, 105, 105, 116, 116, 116, 105, 105, 105, 116, 105, 112, 112, 112, 112, 112, 99, 109, 77, 109, 114, 105, 105, 105, 105, 99, 67, 99, 122, 67, 99, 99, 75, 116, 116, 84, 116, 116, 104, 104, 104, 104, 114, 102, 114, 116, 116, 116, 116, 6, 75, 116, 116, 11, 10, 112, 80, 80, 112, 116, 102, 116, 70, 116, 70, 116, 108, 116, 100, 100, 116, 108, 119, 119, 119, 104, 102, 70, 112, 70, 70, 119, 116, 116, 111, 111, 111, 111, 114, 114, 114, 111, 111, 111, 115, 115, 115, 115, 100, 114, 100, 100, 100, 100, 100, 114, 104, 110, 108, 108, 108, 108, 98, 98, 98, 98, 66, 66, 66, 105, 105, 1, 111, 117, 111, 111, 117, 111, 117, 18, 8, 13, 7, 11, 120, 120, 120, 120, 100, 112, 115, 121, 110, 32, 32, 32, 32, 32, 116, 108, 32, 32, 116, 32, 5, 111, 9, 101, 101, 16, 14, 13, 12, 83, 83, 83, 83, 111, 97, 111, 83, 105, 109, 109, 109, 112, 115, 83, 115, 117, 109, 109, 109, 109, 109, 10, 9, 119, 111, 65, 7, 12, 16, 15, 11, 97, 111, 79, 111,
        79, 79, 111, 111, 111, 111, 11, 10, 57, 1, 0, 15, 18, 97, 101, 101, 101, 111, 97, 111, 97, 97, 111, 101, 97, 101, 101, 101, 111, 101, 97, 101, 97, 101, 111, 97, 97, 101, 101, 111, 101, 101, -68, 111, 97, 117, 101, 101, 101, 1, 101, 101, 111, 101, 97, 97, 111, 97, 101, 101, 97, 111, 111, 101, 101, 111, 18, 12, 11, 116, 97, 102, 102, 102, 70, 70, 116, 103, 97, 97, 97, 97, 45, 45, 117, 117, 117, 117, 111, 111, 111, 111, 65, 67, 105, 105, 101, 101, 116, 84, 101, 101, 101, 101, 101, 101, 97, 97, 101, 9, 47, 47, 47, 103, 103, 103, 103, 103, 112, 104, 104, 104, 104, 111, 111, 111, 97, 101, 101, 101, 101, 101, 101, 101, 112, 112, 80, 112, 112, 99, 99, 99, 99, 99, 116, 116, 116, 99, 70, 102, 105, 9, 8, 99, 99, 99, 10, 9, 101, 101, 101, 101, 111, 101, 101, 111, 111, 111, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84, 84,
        84, 84, 84, 84, 84, 116, 5, 116, 111, 111, 100, 97, 116, 116, 116, 1, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 109, 115, 101, 119, 115, 115, 119, 109, 109, 101, 101, 101, 115, 101, 119, 115, 18, 119, 117, 116, 98, 115, 110, 97, 101, 101, 101, 101, 101, 116, 116, 10, 11, 9, 10, 117, 115, 112, 105, 10, 8, 9, 111, 111, 111, 111, 110, 110, 117, 117, 7, 115, 115, 115, 115, 115, 115, 105, 110, 11, 10, 114, 114, 114, 101, 101, 101, 101, 101, 101, 101, 103, 6, 116, 101, 101, 101, 101, 115, 10, 9, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 101, 101, 97, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 97, 97, 97, 97, 97, 97, 97, 105, 105, 105, 105, 101, 105, 105, -92, 105,
        105, 105, 105, 116, 121, 121, 121, 105, 110, 101, 105, 109, 12, 11, 47, 47, 47, 117, 117, 15, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 47, 14, 115, 115, 115, 115, 117, 111, 111, 111, 115, 115, 110, 110, 110, 110, 114, 115, 110, 120, 115, 114, 115, 120, 120, 120, 110, 99, 1, 110, 115, 111, 105, 114, 110, 115, 115, 101, 99, 99, 105, 105, 120, 101, 120, 120, 105, 120, 115, 18, 18, 110, 101, 32, 32, 5, 99, 105, 99, 101, 110, 110, 110, 110, 110, 110, 110, 105, 115, 119, 115, 110, 115, 115, 115, 97, 97, 99, 97, 116, 97, 101, 115, 105, 105, 105, 105, 73, 73, 73, 115, 115, 115, 115, 110, 111, 115, 108, 101, 110, 110, 110, 110, 110, 110, 115, 4, 100, 116, 32, 97, 105, 114, 117, 108, 110, 97, 112, 97, 97, 97, 97, 97, 97, 112, 112, 112, 112, 97, 32, 108, 108, 108, 115, 99, 105, 105, 104, 32, 6, 13, 61, 12, 116,
        116, 116, 116, 116, 116, 116, 116, 116, 116, 8, 8, 7, 7, 107, 107, 107, 107, 107, 107, 97, 105, 105, 105, 97, 105, 99, 99, 111, 105, 110, 9, 4, 110, 110, 110, 115, 9, 110, 110, 110, 116, 114, 114, 116, 116, 97, 114, 117, 117, 117, 117, 117, 117, 117, 117, 117, 117, 105, 105, 105, 102, 102, 105, 107, 105, 105, 105, 105, 105, 105, 97, 110, 110, 110, 1, 110, 110, 110, 110, 110, 110, 110, 110, 18, 18, 110, 115, 115, 113, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 114, 101, 97, 97, 81, 110, 114, 114, 109, 109, 109, 108, 108, 97, 83, 83, 83, 115, 115, 115, 114, 66, 116, 116, 116, 7, 82, 112, 112, 112, 112, 116, 111, 111, 111, 111, 99, 99, 112, 112, 97, 97, 98, 47, 98, 98, 98, 98, 98, 98, 66, 66, 66, 66, 70, -122, 1, 18, 18, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97,
        97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 110, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 105, 8, 8, 110, 7, 32, 32, 111, 1, 111, 111, 111, 111, 111, 111, 18, 18, 115, 115, 110, 111, 6, 98, 32, 98, 111, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 111, 115, 115, 115, 115, 101, 111, 111, 111, 111, 97, 111, 111, 111, 111, 1, 111, 111, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 97, 111, 18, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 115,
        66, 116, 98, 98, 66, 66, 1, 114, 116, 116, 116, 116, 66, 18, 116, 97, 97, 83, 114, 115, 115, 115, 116, 1, 18, 18, 105, 110, 110, 110, 102, -97, 105, 1, 18, 1, 18, 1, 18, 1, 18, 1, 18, 1, 18, 1, 18, 18, 1, 18, 18, 1, 18, 1, 18, 1, 18, 1, 18, 57, 1, 18, 1, 18, 1, 18, 1, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -61, 0, 0, 0, 0, 0, -61, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 83, 0, 49, 0, 0, 0, 0, 0, 0, 42, 0, 0, 0, 0, -2, -2, 0, -61, -61, 0, -61, -61, -61, -61, 98, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
        5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0, -70, -70, 0, -28, 0, 0, 0, 0, 0, 117, 107, 110, 110, 110, 110, 70, 102, 0, 0, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 115, 115, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -8, 0, 0, 0, 0, 0, 0, 0, 0, 6, -60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 112, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 117, 0, 0, -66, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -54, -54, 0, 0
      };

    public static void main(String[] args)
    {
        byte[] input;
        
        System.out.println("Test Distance Codec");
        
        // Correctness test
        System.out.println("Correctness test\n");

        for (int ii=0; ii<3; ii++)
        {
            if (ii == 2)
              input = DATA;
            else if (ii == 1)
              input = "rccrsaaaa".getBytes();
            else
              input = "abdbcrraaaa".getBytes();

            int length = input.length;
            IndexedByteArray src = new IndexedByteArray(new byte[length], 0);
            IndexedByteArray dst = new IndexedByteArray(new byte[Math.max(length, 256)], 0); // can be bigger than original !
            IndexedByteArray inv = new IndexedByteArray(new byte[length], 0);
            System.arraycopy(input, 0, src.array, 0, length);
            Arrays.fill(dst.array, (byte) 0xAA);
            System.out.println("\nSource");

            for (int i=0; i<length; i++)
                System.out.print((src.array[i] & 0xFF)+" ("+i+") ");

            System.out.println();
            DistanceCodec codec = new DistanceCodec(length);

            if (codec.forward(src, dst) == false)
            {
                System.err.println("Failure in forward() call !");
                System.exit(1);
            }

            System.out.println("\nDestination");

            System.out.println("header:");
            System.out.print("[ ");
            int alphabetSize = dst.array[0] & 0xFF;
            int headerSize = 1 + alphabetSize;

            for (int i=0; i<headerSize; i++)
                System.out.print((dst.array[i] & 0xFF)+" ("+i+") ");

            System.out.println("]");
            System.out.println("body:");
            System.out.print("[ ");

            for (int i=headerSize; i<dst.index; i++)
                System.out.print((dst.array[i] & 0xFF)+" ("+i+") ");

            System.out.println("]");
            System.out.println();
            System.out.println("Source size: "+src.index);
            System.out.println("Destination size: "+dst.index);

            codec.setSize(dst.index);
            src.index = 0;
            dst.index = 0;

            if (codec.inverse(dst, inv) == false)
            {
                System.err.println("Failure in inverse() call !");
                System.exit(1);
            }

            System.out.println("\nInverse");

            for (int i=0; i<length; i++)
            {
                System.out.print((inv.array[i] & 0xFF)+" ("+i+") ");

                if (inv.array[i] != src.array[i])
                {
                    System.err.println("Error at index "+i+" : "+src.array[i]+" <=> "+inv.array[i]);
                    System.exit(1);
                }
            }

            System.out.println("\nIdentical");
        }
    
        // Speed  test  
        System.out.println("\n\nSpeed test\n");
        input = DATA;
         
        int length = input.length;
        IndexedByteArray src = new IndexedByteArray(new byte[length], 0);
        IndexedByteArray dst = new IndexedByteArray(new byte[Math.max(length, 256)], 0); // can be bigger than original !
        IndexedByteArray inv = new IndexedByteArray(new byte[length], 0);
        System.arraycopy(input, 0, src.array, 0, length);
        DistanceCodec codec = new DistanceCodec(length);
        long before, after;
        int iter = 20000;
        long delta1 = 0;
        long delta2 = 0;

        for (int ii=0; ii<iter; ii++)
        {
            before = System.nanoTime();
            src.index = 0;
            dst.index = 0;
            codec.setSize(length);
            codec.forward(src, dst);
            after = System.nanoTime();
            delta1 += (after-before);
            before = System.nanoTime();
            codec.setSize(dst.index);
            dst.index = 0;
            inv.index = 0;
            codec.inverse(dst, inv);
            after = System.nanoTime();
            delta2 += (after-before);
        }
        

        System.out.println("Iterations: "+iter);
        System.out.println("Distance coding [ms]:   "+delta1/1000000L);
        System.out.println("Distance decoding [ms]: "+delta2/1000000L);
    }
}
