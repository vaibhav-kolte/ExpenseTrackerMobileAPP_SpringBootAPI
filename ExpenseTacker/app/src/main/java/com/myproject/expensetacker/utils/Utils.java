package com.myproject.expensetacker.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static LocalDate nextCurrentMonthDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDate startCurrentMonthDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.with(TemporalAdjusters.firstDayOfMonth());
    }


    public static LocalDate startFinancialYearDate() {
        LocalDate currentDate = LocalDate.now();

        Month financialYearStartMonth = Month.APRIL;
        int financialYearStartDay = 1;

        LocalDate startOfCurrentFinancialYear;
        if (currentDate.isBefore(LocalDate.of(currentDate.getYear(), financialYearStartMonth, financialYearStartDay))) {
            startOfCurrentFinancialYear = LocalDate.of(currentDate.getYear() - 1, financialYearStartMonth, financialYearStartDay);
        } else {
            startOfCurrentFinancialYear = LocalDate.of(currentDate.getYear(), financialYearStartMonth, financialYearStartDay);
        }
        return startOfCurrentFinancialYear;
    }

    public static LocalDate nextFinancialYearDate() {
        return startFinancialYearDate().plusYears(1);
    }

    @NonNull
    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

}
