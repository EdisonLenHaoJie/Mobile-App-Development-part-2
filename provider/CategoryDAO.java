package com.fit2081.assignment12081.provider;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.fit2081.assignment12081.AddCategoryNavDrawer;

import java.util.List;

@Dao
public interface CategoryDAO {


    @Query("select * from categories")
    LiveData<List<AddCategoryNavDrawer>> getAllCategories();


    @Insert
    void addCategory(AddCategoryNavDrawer category);


    @Query("DELETE FROM categories")
    void deleteAllCategories();


    @Query("select * from categories where CategoryNavId = :CategoryNavId")
    LiveData<AddCategoryNavDrawer> getCategoryNavId(String CategoryNavId);


    @Query("Update categories set EventNavCount = EventNavCount + 1 where CategoryNavId = :CategoryNavId")
    void incrementEventCount(String CategoryNavId);


    @Query("Update categories set EventNavCount = EventNavCount - 1 where CategoryNavId = :CategoryNavId")
    void DecrementEventCount(String CategoryNavId);







}
