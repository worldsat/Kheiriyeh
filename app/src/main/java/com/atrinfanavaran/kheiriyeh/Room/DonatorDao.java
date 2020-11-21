package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;

import java.util.List;

@Dao
public interface DonatorDao {
    @Query("SELECT * FROM DonatorR")
    List<DonatorR> getAll();

    @RawQuery(observedEntities = DonatorR.class)
    List<DonatorR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM DonatorR where isNew like 'true'")
    List<DonatorR> getAllNew();

    @Query("delete FROM DonatorR")
    void deleteAll();

    @Query("update  DonatorR set donatorFullName=:fullname,donatorAlias=:Alias,donatorMobile=:mobile" +
            " WHERE id like (:id)")
    void update(String fullname, String Alias, String mobile, int id);

    @Query("Delete from  DonatorR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(DonatorR DonatorR);

    @Delete
    void delete2(DonatorR DonatorR);
}