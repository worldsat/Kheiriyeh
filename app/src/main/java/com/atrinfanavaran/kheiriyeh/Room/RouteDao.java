package com.atrinfanavaran.kheiriyeh.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

import java.util.List;

@Dao
public interface RouteDao {
    @Query("SELECT * FROM RouteR")
    List<RouteR> getAll();

    @Query("delete FROM RouteR")
    void deleteAll();

    @Query("update  RouteR set code=:code,day=:day,address=:address,code=:code,lat=:lat,lon=:lon" +
            " WHERE id like (:id)")
    void update(String code, String day, String address, int id,String lat,String lon);

    @Query("Delete from  RouteR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(RouteR routeR);

    @Delete
    void delete2(RouteR routeR);
}