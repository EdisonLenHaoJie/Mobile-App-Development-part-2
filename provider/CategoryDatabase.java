package com.fit2081.assignment12081.provider;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.fit2081.assignment12081.AddCategoryNavDrawer;
import com.fit2081.assignment12081.EventNavDrawer;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AddCategoryNavDrawer.class}, version = 2 )
public abstract class CategoryDatabase extends RoomDatabase {
    public static final String CATEGORY_DATABASE = "category_database";

    public abstract CategoryDAO categoryDAO();

    private static volatile CategoryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Migration logic here
        }
    };

    static CategoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CategoryDatabase.class, CATEGORY_DATABASE)
                            .addMigrations(MIGRATION_1_2)
                            //.fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
