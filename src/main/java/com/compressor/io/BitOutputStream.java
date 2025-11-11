package com.compressor.io;

import java.io.*;

/**
 * Writes individual bits to an output stream by buffering them into bytes.
 */
public class BitOutputStream implements AutoCloseable {
    
    private FileOutputStream output;
    private boolean[] buffer = new boolean[8];
    private int bitCount = 0;

    /**
     * Creates a new bit output stream.
     * 
     * @param output The underlying file output stream
     */
    public BitOutputStream(FileOutputStream output) {
        this.output = output;
    }

    /**
     * Writes a single bit to the stream.
     * 
     * @param bit The bit to write ('0' or '1')
     * @throws IOException If an I/O error occurs
     */
    public void write(char bit) throws IOException {
        buffer[bitCount] = (bit == '1');
        bitCount++;
        
        if (bitCount == 8) {
            flush();
        }
    }

    /**
     * Flushes the current buffer to the output stream.
     * 
     * @throws IOException If an I/O error occurs
     */
    private void flush() throws IOException {
        int byteValue = 0;
        for (int index = 0; index < 8; index++) {
            byteValue = 2 * byteValue + (buffer[index] ? 1 : 0);
        }
        output.write(byteValue);
        bitCount = 0;
    }

    /**
     * Closes the stream, flushing any remaining bits.
     * 
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        // Flush remaining bits (padded with zeros)
        int byteValue = 0;
        for (int index = 0; index < 8; index++) {
            byteValue = 2 * byteValue + (buffer[index] ? 1 : 0);
        }
        output.write(byteValue);
        output.close();
    }
}

