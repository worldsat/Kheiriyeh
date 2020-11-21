package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;

import java.util.List;

@Dao
public interface SponsorDao {
    @Query("SELECT * FROM SponsorR")
    List<SponsorR> getAll();

    @RawQuery(observedEntities = SponsorR.class)
    List<SponsorR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM SponsorR where isNew like 'true'")
    List<SponsorR> getAllNew();

    @Query("SELECT * FROM SponsorR  WHERE id like (:id)")
    SponsorR getSponsorById(int id);

    @Query("delete FROM SponsorR")
    void deleteAll();

    @Query("update  SponsorR set fullName=:fullName,code=:code,nationalcode=:nationalcode,mobile=:mobile,phone=:phone,address=:address,birthDate=:birthDate" +
            " WHERE id like (:id)")
    void update(String fullName, String code, String nationalcode, String mobile, String phone, String address, String birthDate, int id);

    @Query("Delete from  SponsorR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(SponsorR SponsorR);

    @Delete
    void delete2(SponsorR SponsorR);
}