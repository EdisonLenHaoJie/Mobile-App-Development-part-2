package com.fit2081.assignment12081.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment12081.EventNavDrawer;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository eventRepository;

    private LiveData<List<EventNavDrawer>> allEventsLiveData;

    public EventViewModel(@NonNull Application application) {
        super(application);

        eventRepository = new EventRepository(application);

        allEventsLiveData = eventRepository.getAllEvents();



    }

    public LiveData<List<EventNavDrawer>> getAllEvents() {
        return allEventsLiveData;
    }


    public void insert(EventNavDrawer event) {
        eventRepository.insert(event);
    }

    public void deleteAll() {
        eventRepository.deleteAll();
    }

    public void deleteEvent(String EventNavId) {
        eventRepository.deleteEvent(EventNavId);

    }
}