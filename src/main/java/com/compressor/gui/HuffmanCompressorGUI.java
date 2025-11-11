package com.compressor.gui;

import com.compressor.core.HuffmanCompressor;
import com.compressor.util.FileTypeFilter;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;

/**
 * The main GUI application for the Huffman Compressor.
 * Provides a user-friendly interface for file compression and decompression.
 */
public class HuffmanCompressorGUI {
    
    private JFrame mainFrame;
    private JFileChooser fileChooser;
    private String selectedFilePath;
    private JTextField filePathTextField;
    private JButton browseButton;
    private JButton compressButton;
    private JButton decompressButton;
    private JLabel titleLabel;
    private HuffmanCompressor compressor;

    /**
     * Main entry point for the application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HuffmanCompressorGUI window = new HuffmanCompressorGUI();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Creates a new instance of the GUI.
     */
    public HuffmanCompressorGUI() {
        initialize();
    }

    /**
     * Initializes the GUI components.
     */
    private void initialize() {
        
        mainFrame = new JFrame();
        mainFrame.setTitle("Huffman Text Compressor");
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);
        mainFrame.setBounds(100, 100, 852, 510);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);
        mainFrame.setLocationRelativeTo(null);
        
        titleLabel = new JLabel("                       Huffman Text Compressor");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(Color.DARK_GRAY);
        titleLabel.setBounds(10, 11, 746, 50);
        mainFrame.getContentPane().add(titleLabel);
        
        filePathTextField = new JTextField();
        filePathTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
        filePathTextField.setBounds(10, 173, 696, 42);
        filePathTextField.setColumns(10);
        mainFrame.getContentPane().add(filePathTextField);
        
        browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileTypeFilter filter = new FileTypeFilter(".txt", "text");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFilePath = selectedFile.getPath();
                    filePathTextField.setText(selectedFilePath);
                }
            }
        });
        browseButton.setBackground(Color.LIGHT_GRAY);
        browseButton.setBounds(717, 173, 89, 43);
        mainFrame.getContentPane().add(browseButton);
        
        compressButton = new JButton("Click to Compress");
        compressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                compressor = new HuffmanCompressor();
                
                if (filePathTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the file path");
                } else {
                    int result = compressor.compress(filePathTextField.getText());
                    
                    if (result == 2) {
                        JOptionPane.showMessageDialog(null, "Compressed file saved in current directory as 'Compressed.txt'");
                    } else if (result == 0) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } else {
                        JOptionPane.showMessageDialog(null, "Empty file");
                    }
                }
            }
        });
        compressButton.setBackground(Color.ORANGE);
        compressButton.setBounds(200, 347, 221, 42);
        mainFrame.getContentPane().add(compressButton);
        
        decompressButton = new JButton("Click to Decompress");
        decompressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                
                if (filePathTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter the file path");
                } else {
                    int result = compressor.decompress(filePathTextField.getText());
                    
                    if (result == 3) {
                        JOptionPane.showMessageDialog(null, "Decompressed file saved in current directory as 'Decompressed.txt'");
                    } else if (result == 0) {
                        JOptionPane.showMessageDialog(null, "File not found");
                    } else {
                        JOptionPane.showMessageDialog(null, "Empty file");
                    }
                }
            }
        });
        decompressButton.setBackground(Color.ORANGE);
        decompressButton.setBounds(450, 347, 221, 42);
        mainFrame.getContentPane().add(decompressButton);
    }
}

