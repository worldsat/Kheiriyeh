package com.atrinfanavaran.kheiriyeh.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.atrinfanavaran.kheiriyeh.Kernel.Room.User;
import com.atrinfanavaran.kheiriyeh.Kernel.Room.User2;
import com.atrinfanavaran.kheiriyeh.Kernel.Room.UserDao;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

@Database(entities = {BoxIncomeR.class, BoxR.class, RouteR.class, User.class, User2.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract BoxIncomeDao BoxIncomeDao();

    public abstract BoxDao BoxDao();

    public abstract RouteDao RouteDao();

}