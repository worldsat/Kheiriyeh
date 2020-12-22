package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownTypeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;

import java.util.List;

@Dao
public interface FlowerCrownTypeDao {
    @Query("SELECT * FROM FlowerCrownTypeR")
    List<FlowerCrownTypeR> getAll();

    @RawQuery(observedEntities = FlowerCrownTypeR.class)
    List<FlowerCrownTypeR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM FlowerCrownTypeR where isNew like 'true'")
    List<FlowerCrownTypeR> getAllNew();

    @Query("delete FROM FlowerCrownTypeR")
    void deleteAll();

    @Query("update  RouteR set code=:code,address=:address,code=:code,isNew=:isnew" +
            " WHERE id like (:id)")
    void update(String code,  String address, int id, String isnew);

    @Query("Delete from  FlowerCrownTypeR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(FlowerCrownTypeR flowerCrownTypeR);

    @Delete
    void delete2(FlowerCrownTypeR flowerCrownTypeR);
}