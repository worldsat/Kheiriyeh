package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Kernel.Room.User;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

import java.util.List;

@Dao
public interface RouteDao {
    @Query("SELECT * FROM RouteR")
    List<RouteR> getAll();

    @RawQuery(observedEntities = RouteR.class)
    List<RouteR> getfilter(SupportSQLiteQuery  filter);

    @Query("SELECT * FROM RouteR where isNew like 'true'")
    List<RouteR> getAllNew();

    @Query("delete FROM RouteR")
    void deleteAll();

    @Query("update  RouteR set code=:code,day=:day,address=:address,code=:code,isNew=:isnew" +
            " WHERE id like (:id)")
    void update(String code, String day, String address, int id,String isnew);

    @Query("Delete from  RouteR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(RouteR routeR);

    @Delete
    void delete2(RouteR routeR);
}