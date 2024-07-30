package com.fit2081.assignment12081.provider;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment12081.AddCategoryNavDrawer;
import com.fit2081.assignment12081.EventNavDrawer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    private EventRepository eventRepository;

    private LiveData<List<AddCategoryNavDrawer>> allCategoriesLiveData;

    public AtomicBoolean flag;
    public CategoryViewModel(@NonNull Application application) {
        super(application);

        categoryRepository = new CategoryRepository(application);
        eventRepository = new EventRepository(application);

        allCategoriesLiveData = categoryRepository.getAllCategories();



    }


    public boolean addEventIfCategoryExists(EventNavDrawer eventNavDrawer, LifecycleOwner owner, Context context) {
        LiveData<AddCategoryNavDrawer> categoryLiveData = categoryRepository.getCategoryNavId(eventNavDrawer.getCategoryAddNavId());
        flag = new AtomicBoolean(true);
        categoryLiveData.observe(owner, categories -> {
            if (categories != null) {
                eventRepository.insert(eventNavDrawer);
                categoryRepository.incrementEventCount(eventNavDrawer.getCategoryAddNavId());


            } else {

                Toast.makeText(context, "Category ID does not exist in the database ", Toast.LENGTH_SHORT).show();
                flag.set(false);
            }

            categoryLiveData.removeObservers(owner);
        });

        return flag.get();


    }


    public void noEventIfCategoryExists(EventNavDrawer eventNavDrawer, LifecycleOwner owner, Context context) {
        LiveData<AddCategoryNavDrawer> categoryLiveData = categoryRepository.getCategoryNavId(eventNavDrawer.getCategoryAddNavId());

        categoryLiveData.observe(owner, categories -> {
            if (categories != null) {
                //eventRepository.insert(eventNavDrawer);
                categoryRepository.DecrementEventCount(eventNavDrawer.getCategoryAddNavId());
            } else {

                //Toast.makeText(context, "Invalid or non-existent category ID", Toast.LENGTH_SHORT).show();
                return;
            }
            categoryLiveData.removeObservers(owner);
        });
    }

    public LiveData<List<AddCategoryNavDrawer>> getAllCategories() {
        return allCategoriesLiveData;
    }

    public void insert(AddCategoryNavDrawer category) {
        categoryRepository.insert(category);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }



}
