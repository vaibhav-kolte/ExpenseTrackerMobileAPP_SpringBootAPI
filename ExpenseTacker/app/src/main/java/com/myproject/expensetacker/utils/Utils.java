package com.myproject.expensetacker.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final String TAG = "Utils";

    public static String formatDate(String date) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return offsetDateTime.format(formatter);
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String fileName) {
        File filesDir = context.getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, fileName + ".jpg");

        try (OutputStream os = Files.newOutputStream(imageFile.toPath())) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            PrintLog.debugLog(TAG, "File created successfully: " + imageFile.getAbsolutePath());
        } catch (IOException e) {
            PrintLog.errorLog(TAG, "Error writing bitmap to file: " + e.getMessage());
        }

        if (imageFile.exists()) {
            return imageFile;
        } else {
            PrintLog.errorLog(TAG, "File creation failed");
            return null;
        }
    }
}
