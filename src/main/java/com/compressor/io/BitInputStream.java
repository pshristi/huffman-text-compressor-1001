package com.compressor.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Reads bits from a compressed stream and decodes them using Huffman codes.
 */
public class BitInputStream implements AutoCloseable {
    
    private FileOutputStream output;
    private String workingBuffer;

    /**
     * Creates a new bit input stream.
     * 
     * @param output The output stream for decoded data
     */
    public BitInputStream(FileOutputStream output) {
        this.output = output;
        this.workingBuffer = "";
    }

    /**
     * Appends bits to the working buffer.
     * 
     * @param bits The bit string to append
     */
    public void appendBits(String bits) {
        workingBuffer += bits;
    }

    /**
     * Decodes and writes characters from the working buffer.
     * 
     * @param decodingMap Map from Huffman codes to characters
     * @param encodingMap Map from characters to Huffman codes
     * @return 1 if EOF marker found, -1 otherwise
     * @throws IOException If an I/O error occurs
     */
    public int write(HashMap<String, Integer> decodingMap, HashMap<Integer, String> encodingMap) 
            throws IOException {
        String codePrefix;
        int decodedChar;
        
        for (int index = 1; index < workingBuffer.length(); index++) {
            codePrefix = workingBuffer.substring(0, index);
            
            if (decodingMap.containsKey(codePrefix)) {
                decodedChar = decodingMap.get(codePrefix);
                
                if (decodedChar != -1) {
                    output.write(decodedChar);
                    workingBuffer = workingBuffer.substring(index);
                    index = 0;
                } else {
                    // EOF marker found
                    output.close();
                    return 1;
                }
            }
        }
        
        return -1;
    }

    /**
     * Closes the output stream.
     * 
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        output.close();
    }
}

