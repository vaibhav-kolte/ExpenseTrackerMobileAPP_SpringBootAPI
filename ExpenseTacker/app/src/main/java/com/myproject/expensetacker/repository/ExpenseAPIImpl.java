package com.myproject.expensetacker.repository;

import com.myproject.expensetacker.repository.firebase.FirebaseManager;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.repository.room.RoomManager;

public class ExpenseAPIImpl extends RetrofitManager {

    private ExpenseAPIImpl() {
    }

    public static ExpenseAPI getInstance(Database database) {
        if (database.equals(Database.ROOM)) {
            return new RoomManager();
        } else if (database.equals(Database.FIREBASE)) {
            return new FirebaseManager();
        }
        return new RetrofitManager();
    }

}
