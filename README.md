# Huffman Text Compressor

A Java application for compressing and decompressing text files using Huffman coding algorithm.

## Overview

This project implements a text compression tool with a graphical user interface. It uses the Huffman coding algorithm to compress text files, which can significantly reduce file size for text-heavy documents. The application provides an intuitive way to compress and decompress files with just a few clicks.

## Features

- **Text File Compression**: Compress text files to save disk space using optimal Huffman encoding
- **Text File Decompression**: Decompress previously compressed files to restore original content
- **Graphical User Interface**: Clean, easy-to-use interface for selecting files and performing operations
- **File Type Filtering**: Built-in filter for text files in the file chooser
- **Lossless Compression**: Perfect reconstruction of original files after decompression

## How It Works

The application uses Huffman coding, a lossless data compression algorithm that assigns variable-length codes to input characters based on their frequencies. Characters that occur more frequently are assigned shorter codes, resulting in overall size reduction.

### Compression Process:
1. The application reads the input file and counts the frequency of each character
2. It builds a Huffman tree based on these frequencies using a min-heap priority queue
3. It generates optimal Huffman codes for each character by traversing the tree
4. It replaces each character in the original file with its corresponding Huffman code
5. The compressed data is written bit-by-bit to a new file named **Compressed.txt**

### Decompression Process:
1. The application reads the compressed file bit by bit
2. It uses the Huffman tree to decode the compressed data
3. The original text is reconstructed and written to a new file named **Decompressed.txt**

## Project Structure

The project follows standard Maven directory structure and Java package conventions:

```
huffman-compressor/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── compressor/
│                   ├── core/                    # Core compression logic
│                   │   ├── HuffmanCompressor.java   # Main compression/decompression engine
│                   │   ├── HuffmanTree.java         # Huffman tree implementation
│                   │   └── TreeNode.java            # Tree node structure
│                   ├── gui/                     # Graphical user interface
│                   │   └── HuffmanCompressorGUI.java # Main GUI application
│                   ├── io/                      # Input/output operations
│                   │   ├── BitInputStream.java      # Bit-level input handling
│                   │   └── BitOutputStream.java     # Bit-level output handling
│                   └── util/                    # Utility classes
│                       ├── CharFrequencyPair.java   # Character-frequency data structure
│                       ├── HuffmanPriorityQueue.java # Min-heap priority queue
│                       └── FileTypeFilter.java      # File type filter for dialogs
├── pom.xml                                  # Maven build configuration
└── README.md                                # This file
```

### Package Organization:

- **com.compressor.core**: Contains the core Huffman coding algorithm implementation
- **com.compressor.gui**: Contains the Swing-based graphical user interface
- **com.compressor.io**: Contains classes for bit-level I/O operations
- **com.compressor.util**: Contains utility classes and data structures

## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Run

```bash
cd <projectPath eg. /Projects/textCmp>

# Compile all source files
javac -d target/classes -sourcepath src/main/java \
  src/main/java/com/compressor/gui/HuffmanCompressorGUI.java

# Run the application
java -cp target/classes com.compressor.gui.HuffmanCompressorGUI
```

## Using the Application

1. **Launch**: The application window will appear with the title "Huffman Text Compressor"

2. **Select a File**:
   - Click the **Browse** button
   - Navigate to a text file you want to compress
   - Select the file

3. **Compress a File**:
   - After selecting a file, click **Click to Compress**
   - The compressed file will be saved as `Compressed.txt` in the current directory
   - A success message will appear

4. **Decompress a File**:
   - Select a previously compressed file (e.g., `Compressed.txt`)
   - Click **Click to Decompress**
   - The decompressed file will be saved as `Decompressed.txt`
   - A success message will appear

---

## Testing the Compression

You can verify that compression works correctly:

```bash
# Create a test file
echo "This is a test file for Huffman compression. The quick brown fox jumps over the lazy dog." > test.txt

# Run the compressor (compress test.txt using the GUI)

# Compare file sizes
ls -lh test.txt Compressed.txt Decompressed.txt

# Verify decompressed file matches original
diff test.txt Decompressed.txt
# (No output means files are identical)
```

---

## Performance Notes

- **Compression Ratio**: Depends on text redundancy. Typical text files: 40-60% of original size
- **Best Results**: Files with repetitive patterns and limited character sets
- **Processing Time**: Very fast for typical text files (< 1 second for files under 1MB)

---

## File Locations

After running the application:
- **Compressed output**: `Compressed.txt` (in current working directory)
- **Decompressed output**: `Decompressed.txt` (in current working directory)

---

## Algorithm Complexity

- **Time Complexity**: O(n log n) where n is the number of unique characters
- **Space Complexity**: O(n) for storing the Huffman tree and encoding tables
 
 ---
## 👤 Maintainer

**Shristi Pathak**
- LinkedIn: [@shristi-pathak](https://www.linkedin.com/in/shristi-pathak/)

**Note**: This project was developed as a learning exercise to understand algorithm and data structures in Java.


**Happy Compressing! 🗜️**
