package com.atrinfanavaran.kheiriyeh.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.atrinfanavaran.kheiriyeh.Kernel.Room.User;
import com.atrinfanavaran.kheiriyeh.Kernel.Room.User2;
import com.atrinfanavaran.kheiriyeh.Kernel.Room.UserDao;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxIncomeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.BoxR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.ContributionR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DeceasedNameR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.DonatorR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialAidR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FinancialServiceTypeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.FlowerCrownTypeR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.RouteR;
import com.atrinfanavaran.kheiriyeh.Room.Domian.SponsorR;

@Database(entities = {BoxIncomeR.class, BoxR.class, FlowerCrownTypeR.class, FinancialServiceTypeR.class, FinancialAidR.class, FlowerCrownR.class, DonatorR.class, DeceasedNameR.class, SponsorR.class, RouteR.class, ContributionR.class, User.class, User2.class}
        , version = 24)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract BoxIncomeDao BoxIncomeDao();

    public abstract BoxDao BoxDao();

    public abstract RouteDao RouteDao();

    public abstract FlowerCrownTypeDao FlowerCrownTypeDao();

    public abstract FinancialServiceTypeDao FinancialServiceTypeDao();

    public abstract FinancialAidDao FinancialAidDao();

    public abstract FlowerCrownDao FlowerCrownDao();

    public abstract SponsorDao SponsorDao();

    public abstract DonatorDao DonatorDao();

    public abstract DeceasedNameDao DeceasedNameDao();

    public abstract ContributaionDao ContributaionDao();

}