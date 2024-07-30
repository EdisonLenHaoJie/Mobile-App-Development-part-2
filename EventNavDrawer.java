package com.fit2081.assignment12081;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "events")
public class EventNavDrawer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name ="EventNavId")
    private String EventNavId;
    @ColumnInfo(name ="EventNavName")
    private String EventNavName;
    @ColumnInfo(name ="CategoryAddNavId")
    private String CategoryAddNavId;
    @ColumnInfo(name ="TicketNavCount")
    private int TicketNavCount;
    @ColumnInfo(name ="isAddNavActive")
    private boolean isAddNavActive;



    public EventNavDrawer(String EventNavId, String EventNavName, String CategoryAddNavId, int TicketNavCount, boolean isAddNavActive) {
        this.EventNavId = EventNavId;
        this.EventNavName = EventNavName;
        this.CategoryAddNavId = CategoryAddNavId;
        this.TicketNavCount = TicketNavCount;
        this.isAddNavActive = isAddNavActive;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventNavId() {
        return EventNavId;
    }

    public void setEventNavId(String eventNavId) {
        EventNavId = eventNavId;
    }



    public String getEventNavName() {
        return EventNavName;
    }

    public void setEventNavName(String eventNavName) {
        EventNavName = eventNavName;
    }

    public String getCategoryAddNavId() {
        return CategoryAddNavId;
    }

    public void setCategoryAddNavId(String categoryAddNavId) {
        CategoryAddNavId = categoryAddNavId;
    }

    public int getTicketNavCount() {
        return TicketNavCount;
    }

    public void setTicketNavCount(int ticketNavCount) {
        TicketNavCount = ticketNavCount;
    }

    public boolean isAddNavActive() {
        return isAddNavActive;
    }

    public void setAddNavActive(boolean addNavActive) {
        isAddNavActive = addNavActive;
    }
}
