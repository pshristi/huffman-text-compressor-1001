package com.compressor.core;

import com.compressor.util.CharFrequencyPair;

/**
 * Represents a node in the Huffman tree.
 */
public class TreeNode {
    
    private CharFrequencyPair data;
    private TreeNode left;
    private TreeNode right;

    /**
     * Creates a new tree node with the given data.
     * 
     * @param data The character-frequency pair for this node
     */
    public TreeNode(CharFrequencyPair data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public CharFrequencyPair getData() {
        return data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}

