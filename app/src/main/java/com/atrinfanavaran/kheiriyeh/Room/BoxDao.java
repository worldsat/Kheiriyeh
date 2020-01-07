package com.atrinfanavaran.kheiriyeh.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;

import java.util.List;

@Dao
public interface BoxDao {
    @Query("SELECT * FROM BoxR")
    List<BoxR> getAll();

    @Query("SELECT * FROM BoxR where number like (:number)")
    BoxR getAllFilterNumber(String number);


    @Query("SELECT * FROM BoxR where isNew like 'true'")
    List<BoxR> getAllNew();

    @Query("delete FROM BoxR")
    void deleteAll();

    @Query("update  BoxR set fullName=:fullName,number=:number,mobile=:mobile,code=:code,registerDate=:registerDate,address=:address,lon=:lng,lat=:lat" +
            " WHERE id like (:id)")
    void update(String fullName, String number, String mobile, String code, String registerDate, int id,String address, String lat, String lng);

    @Query("Delete from  BoxR  " +
            " WHERE id like (:id)")
    void delete(int id);


    @Insert
    void insertBox(BoxR boxR);

    @Delete
    void delete2(BoxR boxR);
}