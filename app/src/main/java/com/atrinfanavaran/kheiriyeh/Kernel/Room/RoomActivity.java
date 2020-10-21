package com.atrinfanavaran.kheiriyeh.Kernel.Room;

import androidx.room.Room;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.atrinfanavaran.kheiriyeh.R;
import com.atrinfanavaran.kheiriyeh.Room.AppDatabase;

import java.util.List;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "RoomDb").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        db.userDao().deleteByTitle();

        User user = new User();
        user.firstName = "mohsen";
        user.lastName = "jamali";
        db.userDao().insertAll(user);

        List<User> list = db.userDao().getAll();

        for (int i = 0; i < list.size(); i++) {
            Log.i("moh3n", "onCreate: " + list.get(i).lastName);
        }

        List<User2> test = db.userDao().getAll2();

    }
}
