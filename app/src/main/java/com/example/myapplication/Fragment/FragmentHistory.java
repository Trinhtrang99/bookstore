package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.myapplication.Adapter.HistoryAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.SqlHelper;
import com.example.myapplication.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistory extends Fragment {

    FragmentHistoryBinding binding;
    SqlHelper sqlHelper;
    List<Book> list;
    HistoryAdapter adapter;

    public static FragmentHistory newInstance(String param1, String param2) {
        FragmentHistory fragment = new FragmentHistory();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        sqlHelper = new SqlHelper(getContext());


        list = new ArrayList<>();
     getData();
        binding.btnDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.deleteHistory();
                getData();
            }
        });
        return binding.getRoot();
    }
    public void getData(){
        list = sqlHelper.getAllBookInHistory();
        adapter = new HistoryAdapter(list, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        binding.listHistory.setAdapter(adapter);
        binding.listHistory.setLayoutManager(gridLayoutManager);
    }
}