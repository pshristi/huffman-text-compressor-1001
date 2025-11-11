package com.compressor.util;

import java.util.ArrayList;

/**
 * A min-heap priority queue implementation for Huffman coding.
 * Priorities are based on frequency values.
 */
public class HuffmanPriorityQueue {
    
    private ArrayList<CharFrequencyPair> data;

    public HuffmanPriorityQueue() {
        this.data = new ArrayList<>();
        this.data.add(null); // Index 0 is unused for easier parent/child calculations
    }

    /**
     * Returns the number of elements in the queue.
     * 
     * @return The size of the queue
     */
    public int size() {
        return data.size();
    }

    /**
     * Inserts a new element into the priority queue.
     * 
     * @param pair The character-frequency pair to insert
     */
    public void insert(CharFrequencyPair pair) {
        data.add(pair);
        int childIndex = data.size() - 1;
        
        // Bubble up to maintain heap property
        while (childIndex > 1) {
            int parentIndex = childIndex / 2;
            if (data.get(parentIndex).getFrequency() < pair.getFrequency()) {
                return;
            }
            CharFrequencyPair temp = data.get(parentIndex);
            data.set(parentIndex, pair);
            data.set(childIndex, temp);
            childIndex = parentIndex;
        }
    }

    /**
     * Returns the minimum element without removing it.
     * 
     * @return The minimum element, or null if queue is empty
     */
    public CharFrequencyPair getMin() {
        if (data.size() == 1) {
            return null;
        }
        return data.get(1);
    }

    /**
     * Removes and returns the minimum element.
     * 
     * @return The minimum element, or null if queue is empty
     */
    public CharFrequencyPair removeMin() {
        if (data.size() == 1) {
            return null;
        }
        
        CharFrequencyPair minElement = data.get(1);
        data.set(1, data.get(data.size() - 1));
        data.remove(data.size() - 1);
        
        // Bubble down to maintain heap property
        int parentIndex = 1;
        while (true) {
            int minIndex = parentIndex;
            int leftIndex = 2 * parentIndex;
            int rightIndex = leftIndex + 1;
            
            if (leftIndex < data.size() && data.get(minIndex).getFrequency() > data.get(leftIndex).getFrequency()) {
                minIndex = leftIndex;
            }
            if (rightIndex < data.size() && data.get(minIndex).getFrequency() > data.get(rightIndex).getFrequency()) {
                minIndex = rightIndex;
            }
            if (minIndex == parentIndex) {
                break;
            }
            
            CharFrequencyPair temp = data.get(parentIndex);
            data.set(parentIndex, data.get(minIndex));
            data.set(minIndex, temp);
            parentIndex = minIndex;
        }
        
        return minElement;
    }
}

