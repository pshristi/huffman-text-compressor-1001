package com.compressor.core;

import com.compressor.io.BitInputStream;
import com.compressor.io.BitOutputStream;
import com.compressor.util.CharFrequencyPair;
import com.compressor.util.HuffmanPriorityQueue;
import java.io.*;
import java.util.*;

/**
 * Main class for compressing and decompressing files using Huffman coding algorithm.
 */
public class HuffmanCompressor {

    private int currentByte;
    private HashMap<Integer, String> encodingMap = new HashMap<>();
    private HashMap<String, Integer> decodingMap = new HashMap<>();

    /**
     * Compresses the given file using Huffman coding.
     * 
     * @param filePath Path to the file to be compressed
     * @return Status code: 0 = file not found, 1 = empty file, 2 = success
     */
    public int compress(String filePath) {
        int[] frequencyArray = new int[256];
        
        // Read file and count character frequencies
        try (FileInputStream fileInput = new FileInputStream(filePath)) {
            currentByte = fileInput.read();
            boolean hasContent = false;
            
            while (currentByte != -1) {
                frequencyArray[currentByte]++;
                currentByte = fileInput.read();
                hasContent = true;
            }
            
            if (!hasContent) {
                return 1; // Empty file
            }
        } catch (FileNotFoundException e) {
            return 0; // File not found
        } catch (IOException e) {
            return 0; // IO error
        }

        // Build Huffman tree
        HuffmanPriorityQueue priorityQueue = new HuffmanPriorityQueue();
        
        for (int i = 0; i < 256; i++) {
            if (frequencyArray[i] != 0) {
                CharFrequencyPair pair = new CharFrequencyPair(i, frequencyArray[i]);
                priorityQueue.insert(pair);
            }
        }
        
        // Add EOF marker
        priorityQueue.insert(new CharFrequencyPair(-1, 1));

        HuffmanTree tree = new HuffmanTree();
        CharFrequencyPair leftNode, rightNode, parentNode;
        
        while (priorityQueue.size() != 2) {
            leftNode = priorityQueue.removeMin();
            rightNode = priorityQueue.removeMin();
            parentNode = tree.merge(leftNode, rightNode);
            priorityQueue.insert(parentNode);
        }
        
        boolean[] codePath = new boolean[100];
        tree.generateCodes(tree.getRoot(priorityQueue.removeMin()), encodingMap, decodingMap, 0, codePath, false);

        // Write compressed file
        try (FileInputStream input = new FileInputStream(filePath);
             FileOutputStream output = new FileOutputStream("Compressed.txt");
             BitOutputStream bitOutput = new BitOutputStream(output)) {
            
            String encodedString;
            do {
                currentByte = input.read();
                encodedString = encodingMap.get(currentByte);
                for (int j = 0; j < encodedString.length(); j++) {
                    bitOutput.write(encodedString.charAt(j));
                }
            } while (currentByte != -1);
            
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        }
        
        return 2; // Success
    }

    /**
     * Decompresses the given compressed file.
     * 
     * @param filePath Path to the compressed file
     * @return Status code: 0 = file not found/error, 3 = success
     */
    public int decompress(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath);
             FileOutputStream output = new FileOutputStream("Decompressed.txt");
             BitInputStream bitInput = new BitInputStream(output)) {
            
            currentByte = input.read();
            int status;
            
            while (currentByte != -1) {
                bitInput.appendBits(convertToBinaryString(currentByte));
                status = bitInput.write(decodingMap, encodingMap);
                if (status == 1) {
                    break; // EOF marker found
                }
                currentByte = input.read();
            }
            
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            return 0;
        }
        
        return 3; // Success
    }

    /**
     * Converts an integer to its 8-bit binary string representation.
     * 
     * @param value The integer value to convert
     * @return 8-bit binary string
     */
    private static String convertToBinaryString(int value) {
        String binaryString = "";
        int digit;
        
        while (value != 0) {
            digit = value % 2;
            value = value / 2;
            binaryString = (digit == 1 ? 1 : 0) + binaryString;
        }
        
        while (binaryString.length() < 8) {
            binaryString = '0' + binaryString;
        }
        
        return binaryString;
    }
}

