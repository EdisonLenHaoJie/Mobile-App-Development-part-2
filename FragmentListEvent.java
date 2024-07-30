package com.fit2081.assignment12081;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.assignment12081.provider.EventViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class FragmentListEvent extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    MyRecyclerAdapter2 recyclerAdapter2;

    ArrayList<EventNavDrawer> data2 = new ArrayList<>();

    public FragmentListEvent() {
        // Required empty public constructor
    }


    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view =  inflater.inflate(R.layout.fragment_list_event, container, false);

        //SharedPreferences sharedPreferences = getContext().getSharedPreferences(KeyStore.FILE_NAME, Context.MODE_PRIVATE);

        //String addEventList = sharedPreferences.getString(KeyStore.KEY_EVENT_LIST_ID, "[]");
        //Gson gson = new Gson();

        //Type type = new TypeToken<ArrayList<EventNavDrawer>>() {}.getType();
        //data2 = gson.fromJson(addEventList,type);




        RecyclerView recyclerView = view.findViewById(R.id.event_frag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerAdapter2 = new MyRecyclerAdapter2();
        //recyclerAdapter2.setData(data2);
        recyclerView.setAdapter(recyclerAdapter2);


        EventViewModel eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), newData -> {
            recyclerAdapter2.setData(new ArrayList<EventNavDrawer>(newData));
            recyclerAdapter2.notifyDataSetChanged();
        });




        return view;

    }

}