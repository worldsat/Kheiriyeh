package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;

import java.util.List;

@Dao
public interface DeceasedNameDao {
    @Query("SELECT * FROM DeceasedNameR")
    List<DeceasedNameR> getAll();

    @RawQuery(observedEntities = DeceasedNameR.class)
    List<DeceasedNameR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM DeceasedNameR where isNew like 'true'")
    List<DeceasedNameR> getAllNew();

    @Query("delete FROM DeceasedNameR")
    void deleteAll();

    @Query("update  DeceasedNameR set deceasedSex=:deceasedSex,deceasedFullName=:deceasedFullName,deceaseAalias=:deceaseAalias" +
            " WHERE id like (:id)")
    void update(boolean deceasedSex, String deceasedFullName, String deceaseAalias, int id);

    @Query("Delete from  DeceasedNameR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertBox(DeceasedNameR DeceasedNameR);

    @Delete
    void delete2(DeceasedNameR DeceasedNameR);
}