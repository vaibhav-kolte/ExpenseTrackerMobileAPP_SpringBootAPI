package com.myproject.expensetacker.repository;

import androidx.annotation.NonNull;

import com.myproject.expensetacker.repository.firebase.FirebaseManager;
import com.myproject.expensetacker.repository.retrofit.RetrofitManager;
import com.myproject.expensetacker.repository.room.RoomManager;
import com.myproject.expensetacker.utils.Constant;

// We declare this class final because we don't want to inherit this class
public final class ExpenseAPIImpl extends RetrofitManager {

    // Create private constructor because don't want to create object in this class
    private ExpenseAPIImpl() {
    }

    @NonNull
    public static ExpenseAPI getInstance() {
        Database database = Constant.USED_DATABASE;
        switch (database) {
            case ROOM:
                return new RoomManager();
            case FIREBASE:
                return new FirebaseManager();
            default:
                return new RetrofitManager();
        }
    }
}
