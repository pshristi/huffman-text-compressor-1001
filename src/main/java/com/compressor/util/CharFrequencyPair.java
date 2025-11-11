package com.compressor.util;

/**
 * Represents a character and its frequency count.
 * Used in building the Huffman tree.
 */
public class CharFrequencyPair {
    
    private int character;
    private int frequency;

    /**
     * Creates a new character-frequency pair.
     * 
     * @param character The character (or special marker like -1 for EOF, -2 for internal node)
     * @param frequency The frequency count
     */
    public CharFrequencyPair(int character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public int getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }
}

