package com.myproject.expensetacker.utils;

import com.myproject.expensetacker.repository.Database;

public class Constant {

    public static final String[] expenseType = new String[]{"Select Tag", "My Self", "Home", "Member", "Bike",
            "Recharge", "Rent", "Medical", "Travel", "Outside food", "Investment"};

    public static final String API_URL = "https://9cb2-2409-40c2-1032-5289-8d1a-3d72-91f6-96d6.ngrok-free.app";

    public static final Database USED_DATABASE = Database.RETROFIT;
}
