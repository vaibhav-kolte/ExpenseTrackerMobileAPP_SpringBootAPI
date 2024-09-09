package com.myproject.expensetacker.repository;

import androidx.annotation.NonNull;

import com.myproject.expensetacker.repository.firebase.FirebaseManager;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.repository.room.RoomManager;
import com.myproject.expensetacker.utils.Constant;

public abstract class ExpenseAPIImpl extends RetrofitManager {

    private ExpenseAPIImpl() {
    }

    @NonNull
    public static ExpenseAPI getInstance() {
        Database database = Constant.USED_DATABASE;
        if (database.equals(Database.ROOM)) {
            return new RoomManager();
        } else if (database.equals(Database.FIREBASE)) {
            return new FirebaseManager();
        }
        return new RetrofitManager();
    }

}
