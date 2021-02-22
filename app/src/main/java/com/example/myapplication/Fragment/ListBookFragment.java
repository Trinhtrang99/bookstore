package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Adapter.BookAdapter;
import com.example.myapplication.Adapter.BookAdapter2;
import com.example.myapplication.Adapter.ClickBook;
import com.example.myapplication.InforBook;
import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentListBookBinding;

import java.util.ArrayList;
import java.util.List;


public class ListBookFragment extends Fragment {
    List<Book> list;
    BookAdapter2 adapter;
    FragmentListBookBinding bookBinding;

    public static ListBookFragment newInstance() {
        ListBookFragment fragment = new ListBookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_book, container, false);
        list = new ArrayList<>();
        list = (List<Book>) getArguments().getSerializable("KEY");
        adapter = new BookAdapter2(list, getContext());
        bookBinding.listBook.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL,false);
        bookBinding.listBook.setLayoutManager(gridLayoutManager);
        bookBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        adapter.setClickBook(new ClickBook() {
            @Override
            public void onClickItem(Book book) {
                Intent intent = new Intent(getContext(), InforBook.class);

                intent.putExtra("book", (Parcelable) book);
                intent.putExtra("des",book.getDescription());
                intent.putExtra("NumOfReview",book.getNumOfReview());
                intent.putExtra("Category",book.getCategory());
                intent.putExtra("Rate",book.getRateStar());

                startActivity(intent);
            }

            @Override
            public void onClickItemCong(Book book) {

            }

            @Override
            public void onClickItemTru(Book book) {

            }

            @Override
            public void onClickDelete(Book book) {

            }
        });

        return bookBinding.getRoot();
    }
    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book item : list) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}