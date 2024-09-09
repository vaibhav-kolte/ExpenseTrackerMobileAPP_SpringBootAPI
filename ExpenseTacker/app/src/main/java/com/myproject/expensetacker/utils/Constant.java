package com.myproject.expensetacker.utils;

import com.myproject.expensetacker.repository.Database;

public class Constant {

    public static final String[] expenseType = new String[]{"Select Tag", "My Self", "Home", "Member", "Bike",
            "Recharge", "Rent", "Medical", "Travel", "Outside food", "Investment"};

    public static final String API_URL = "https://06f9-2409-40c2-1152-9765-adf4-fc14-e95d-ec82.ngrok-free.app";

    public static final Database USED_DATABASE = Database.RETROFIT;
}
