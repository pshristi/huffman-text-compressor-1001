package com.compressor.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * A file filter for the file chooser dialog.
 * Filters files by extension.
 */
public class FileTypeFilter extends FileFilter {
 
    private String extension;
    private String description;
     
    /**
     * Creates a new file type filter.
     * 
     * @param extension The file extension to filter (e.g., ".txt")
     * @param description Human-readable description of the file type
     */
    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }
  
    /**
     * Determines if a file should be accepted by this filter.
     * 
     * @param file The file to test
     * @return true if the file should be shown, false otherwise
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().toLowerCase().endsWith(extension);
    }
     
    /**
     * Gets the description of this filter.
     * 
     * @return The description string
     */
    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}

