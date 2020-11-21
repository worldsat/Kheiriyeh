package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.ContributionR;

import java.util.List;

@Dao
public interface ContributaionDao {
    @Query("SELECT * FROM ContributionR")
    List<ContributionR> getAll();

    @RawQuery(observedEntities = ContributionR.class)
    List<ContributionR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM ContributionR where isNew like 'true'")
    List<ContributionR> getAllNew();

    @Query("delete FROM ContributionR")
    void deleteAll();

    @Query("update  ContributionR set price=:price,description=:description,deviceCode=:deviceCode,terminalCode=:terminalCode,recieverCode=:recieverCode,fullName=:fullName,code=:code," +
            "nationalcode=:nationalcode,mobile=:mobile,phone=:phone,address=:address,birthDate=:birthDate WHERE id like (:id)")

    void update(int price, String description, int deviceCode, int terminalCode, int recieverCode, String fullName, String code
            , String nationalcode, String mobile, String phone, String address, String birthDate, int id);

    @Query("Delete from  ContributionR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(ContributionR ContributionR);

    @Delete
    void delete2(ContributionR ContributionR);
}