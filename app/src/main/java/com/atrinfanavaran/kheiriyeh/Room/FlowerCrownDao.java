package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;

import java.util.List;

@Dao
public interface FlowerCrownDao {
    @Query("SELECT * FROM FlowerCrownR")
    List<FlowerCrownR> getAll();

    @RawQuery(observedEntities = FlowerCrownR.class)
    List<FlowerCrownR> getfilter(SupportSQLiteQuery filter);

    @Query("SELECT * FROM FlowerCrownR where isNew like 'true'")
    List<FlowerCrownR> getAllNew();

    @Query("delete FROM FlowerCrownR")
    void deleteAll();

    @Query("update  FlowerCrownR set flowerCrownType=:flowerCrownType,deceasedName=:deceasedName,charity=:charity,donator=:donator,donatorId=:donatorId,donatorId=:donatorId,ceremonyTypeId=:ceremonyTypeId,ceremonyType=:ceremonyType,price=:price,registerDate=:registerDate,registerDateEn=:registerDateEn,flowerCrownTypeId=:flowerCrownTypeId,deceasedNameId=:deceasedNameId,IntroducedId=:IntroducedId,Introduced=:Introduced" +
            " WHERE id like (:id)")
    void update(String flowerCrownType, String deceasedName, String charity, String donator, int donatorId, int ceremonyTypeId, String ceremonyType, int price, String registerDate, String registerDateEn, int flowerCrownTypeId, int deceasedNameId, int id, int IntroducedId, String Introduced);

    @Query("Delete from  FlowerCrownR  " +
            " WHERE id like (:id)")
    void delete(int id);

    @Insert
    void insertFlowerCrown(FlowerCrownR FlowerCrownR);

    @Delete
    void delete2(FlowerCrownR FlowerCrownR);
}