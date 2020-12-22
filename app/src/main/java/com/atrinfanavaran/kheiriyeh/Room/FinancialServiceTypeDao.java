package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialServiceTypeR;

import java.util.List;

@Dao
public interface FinancialServiceTypeDao {
    @Query("SELECT * FROM FinancialServiceTypeR")
    List<FinancialServiceTypeR> getAll();

    @RawQuery(observedEntities = FinancialServiceTypeR.class)
    List<FinancialServiceTypeR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM FinancialServiceTypeR where isNew like 'true'")
    List<FinancialServiceTypeR> getAllNew();

    @Query("delete FROM FinancialServiceTypeR")
    void deleteAll();

    @Query("delete FROM FinancialServiceTypeR Where Isnew  not like 'true' ")
    void deleteAllOld();

    @Query("update  RouteR set code=:code,address=:address,code=:code,isNew=:isnew" +
            " WHERE id like (:id)")
    void update(String code, String address, int id, String isnew);

    @Query("Delete from  FinancialServiceTypeR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(FinancialServiceTypeR FinancialServiceTypeR);

    @Delete
    void delete2(FinancialServiceTypeR FinancialServiceTypeR);
}