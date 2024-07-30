package com.fit2081.assignment12081.provider;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment12081.EventNavDrawer;

import java.util.List;

public class EventRepository {

    private EventDAO eventDAO;
    private LiveData<List<EventNavDrawer>> allEvents;

    EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDAO = db.eventDAO();
        allEvents = eventDAO.getAllEvents();
    }

    LiveData<List<EventNavDrawer>> getAllEvents() {
        return allEvents;
    }






    void insert(EventNavDrawer event) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.addEvent(event));
    }

    void deleteAll() {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteAllEvents());
    }

    void deleteEvent(String EventNavId) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteEvent(EventNavId));
    }


}