package com.fit2081.assignment12081;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class AddCategoryNavDrawer {

    @PrimaryKey(autoGenerate = true)
    private int id;



    @ColumnInfo(name = "CategoryNavId")
    private String CategoryNavId;

    @ColumnInfo(name = "CategoryNavName")
    private String CategoryNavName;

    @ColumnInfo(name = "EventNavCount")
    private int EventNavCount;

    @ColumnInfo(name = "isNavActive")
    private boolean isNavActive;


    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    public AddCategoryNavDrawer(String CategoryNavId, String CategoryNavName, int EventNavCount, boolean isNavActive,String eventLocation) {
        this.CategoryNavId = CategoryNavId;
        this.CategoryNavName = CategoryNavName;
        this.EventNavCount = EventNavCount;
        this.isNavActive = isNavActive;
        this.eventLocation = eventLocation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryNavId() {
        return CategoryNavId;
    }

    public void setCategoryNavId(String categoryNavId) {
        CategoryNavId = categoryNavId;
    }

    public String getCategoryNavName() {
        return CategoryNavName;
    }

    public void setCategoryNavName(String categoryNavName) {
        CategoryNavName = categoryNavName;
    }

    public int getEventNavCount() {
        return EventNavCount;
    }

    public void setEventNavCount(int eventNavCount) {
        EventNavCount = eventNavCount;
    }

    public boolean isNavActive() {
        return isNavActive;
    }

    public void setNavActive(boolean navActive) {
        isNavActive = navActive;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}


