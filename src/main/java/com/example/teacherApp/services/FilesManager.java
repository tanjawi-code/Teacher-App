package com.example.teacherApp.services;

import com.example.teacherApp.dao.SavedFilesSQLite;

public class FilesManager {

    private final SavedFilesSQLite savedFilesSQLite;

    public FilesManager(SavedFilesSQLite savedFilesSQLite) {
        this.savedFilesSQLite = savedFilesSQLite;
    }

    // Saving a file of a user (teacher).
    public void saveUserFile(String filePath, String fileName, String fileType, int userId) {
        savedFilesSQLite.insertFileToTable(filePath, fileName, fileType, userId);
    }

    // This is for getting the user file directory from the database.
    public String userFilePath(int userId, String filePth) {
        return savedFilesSQLite.getUserFilePath(userId, filePth);
    }
}
