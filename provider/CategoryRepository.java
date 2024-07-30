package com.fit2081.assignment12081.provider;

import com.fit2081.assignment12081.AddCategoryNavDrawer;
import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryRepository {

    private CategoryDAO categoryDAO;
    private LiveData<List<AddCategoryNavDrawer>> allCategories;

    CategoryRepository(Application application) {
        CategoryDatabase db = CategoryDatabase.getDatabase(application);
        categoryDAO = db.categoryDAO();
        allCategories = categoryDAO.getAllCategories();
    }

    LiveData<List<AddCategoryNavDrawer>> getAllCategories() {
        return allCategories;
    }

    LiveData<AddCategoryNavDrawer> getCategoryNavId(String CategoryNavId) {
        return categoryDAO.getCategoryNavId(CategoryNavId);
    }

    void incrementEventCount(String CategoryNavId) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.incrementEventCount(CategoryNavId);
        });
    }

    void DecrementEventCount(String CategoryNavId) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> {
            categoryDAO.DecrementEventCount(CategoryNavId);
        });
    }






    void insert(AddCategoryNavDrawer category) {
        CategoryDatabase.databaseWriteExecutor.execute(() -> categoryDAO.addCategory(category));
    }

    void deleteAll() {
        CategoryDatabase.databaseWriteExecutor.execute(() -> categoryDAO.deleteAllCategories());
    }


}
