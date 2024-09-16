package com.myproject.expensetacker.utils;

import com.myproject.expensetacker.repository.Database;

public class Constant {

    public static final String[] expenseType = new String[]{"Select Tag", "My Self", "Home", "Member", "Bike",
            "Recharge", "Rent", "Medical", "Travel", "Outside food", "Investment"};

    public static final String API_URL = "https://9a7a-103-200-101-206.ngrok-free.app";

    public static final Database USED_DATABASE = Database.RETROFIT;
}
