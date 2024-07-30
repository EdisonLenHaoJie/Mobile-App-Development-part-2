package com.fit2081.assignment12081.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit2081.assignment12081.EventNavDrawer;

import java.util.List;

@Dao
public interface EventDAO {


    @Query("select * from events")
    LiveData<List<EventNavDrawer>> getAllEvents();

    @Insert
    void addEvent(EventNavDrawer event);


    @Query("delete from events")
    void deleteAllEvents();


    @Query("delete from events where EventNavId=:EventNavId")
    void deleteEvent(String EventNavId);
}
