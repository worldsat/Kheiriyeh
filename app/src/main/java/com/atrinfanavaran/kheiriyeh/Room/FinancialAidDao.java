package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialAidR;

import java.util.List;

@Dao
public interface FinancialAidDao {
    @Query("SELECT * FROM FinancialAidR")
    List<FinancialAidR> getAll();

    @RawQuery(observedEntities = FinancialAidR.class)
    List<FinancialAidR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM FinancialAidR where isNew like 'true'")
    List<FinancialAidR> getAllNew();

    @Query("delete FROM FinancialAidR")
    void deleteAll();

    @Query("update  RouteR set code=:code,day=:day,address=:address,code=:code,isNew=:isnew" +
            " WHERE id like (:id)")
    void update(String code, String day, String address, int id, String isnew);

    @Query("Delete from  FinancialAidR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(FinancialAidR FinancialAidR);

    @Delete
    void delete2(FinancialAidR FinancialAidR);
}