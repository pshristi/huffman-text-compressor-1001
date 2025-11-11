package com.compressor.core;

import com.compressor.util.CharFrequencyPair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Huffman tree for encoding and decoding operations.
 */
public class HuffmanTree {
    
    private HashMap<Integer, ArrayList<TreeNode>> nodesByFrequency;

    public HuffmanTree() {
        this.nodesByFrequency = new HashMap<>();
    }

    /**
     * Gets the root node for a given frequency pair.
     * 
     * @param pair The character-frequency pair
     * @return The root tree node
     */
    public TreeNode getRoot(CharFrequencyPair pair) {
        ArrayList<TreeNode> nodes = nodesByFrequency.get(pair.getFrequency());
        return nodes.get(0);
    }

    /**
     * Generates Huffman codes for all characters in the tree.
     * 
     * @param root Current tree node
     * @param encodingMap Map to store character to code mappings
     * @param decodingMap Map to store code to character mappings
     * @param depth Current depth in the tree
     * @param codePath Array representing the current code path
     * @param bit Current bit value (false for 0, true for 1)
     */
    public void generateCodes(TreeNode root, HashMap<Integer, String> encodingMap, 
                             HashMap<String, Integer> decodingMap, int depth, 
                             boolean[] codePath, boolean bit) {
        if (root == null) {
            return;
        }
        
        codePath[depth] = bit;
        
        // Leaf node - store the code
        if (root.getLeft() == null && root.getRight() == null) {
            StringBuilder code = new StringBuilder();
            for (int j = 1; j <= depth; j++) {
                code.append(codePath[j] ? 1 : 0);
            }
            String codeString = code.toString();
            encodingMap.put(root.getData().getCharacter(), codeString);
            decodingMap.put(codeString, root.getData().getCharacter());
        }
        
        // Traverse left and right subtrees
        generateCodes(root.getLeft(), encodingMap, decodingMap, depth + 1, codePath, false);
        generateCodes(root.getRight(), encodingMap, decodingMap, depth + 1, codePath, true);
    }

    /**
     * Merges two frequency pairs into a parent node.
     * 
     * @param left Left child pair
     * @param right Right child pair
     * @return The merged parent pair
     */
    public CharFrequencyPair merge(CharFrequencyPair left, CharFrequencyPair right) {
        CharFrequencyPair parentPair = new CharFrequencyPair(-2, left.getFrequency() + right.getFrequency());
        TreeNode parentNode = new TreeNode(parentPair);
        ArrayList<TreeNode> nodeList;

        // Handle different node types
        if (left.getCharacter() == -2 && right.getCharacter() == -2) {
            nodeList = nodesByFrequency.get(left.getFrequency());
            parentNode.setLeft(nodeList.get(0));
            nodeList.remove(0);
            
            nodeList = nodesByFrequency.get(right.getFrequency());
            parentNode.setRight(nodeList.get(0));
            nodeList.remove(0);
        } else if (left.getCharacter() == -2) {
            nodeList = nodesByFrequency.get(left.getFrequency());
            parentNode.setLeft(nodeList.get(0));
            nodeList.remove(0);
            parentNode.setRight(new TreeNode(right));
        } else if (right.getCharacter() == -2) {
            nodeList = nodesByFrequency.get(right.getFrequency());
            parentNode.setLeft(nodeList.get(0));
            nodeList.remove(0);
            parentNode.setRight(new TreeNode(left));
        } else {
            parentNode.setLeft(new TreeNode(left));
            parentNode.setRight(new TreeNode(right));
        }

        // Store the parent node
        int frequency = parentNode.getData().getFrequency();
        if (nodesByFrequency.containsKey(frequency)) {
            nodeList = nodesByFrequency.get(frequency);
            nodeList.add(parentNode);
        } else {
            nodeList = new ArrayList<>();
            nodeList.add(parentNode);
            nodesByFrequency.put(frequency, nodeList);
        }

        return parentPair;
    }
}

