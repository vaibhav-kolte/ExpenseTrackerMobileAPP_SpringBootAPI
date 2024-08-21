package com.myproject.expensetacker.interfaces;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

public class DatePicker {
    private final Context context;

    public DatePicker(Context context) {
        this.context = context;
    }

    public void getDate(DatePickerInterface pickerInterface) {
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        pickerInterface.setDate(year + "-" + formatStringTwoDigits(monthOfYear + 1) +
                                "-" + formatStringTwoDigits(dayOfMonth));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @SuppressLint("DefaultLocale")
    private static String formatStringTwoDigits(int num) {
        return String.format("%02d", num);
    }
}
