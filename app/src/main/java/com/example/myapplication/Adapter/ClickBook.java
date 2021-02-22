package com.example.myapplication.Adapter;

import com.example.myapplication.Model.Book;

public interface ClickBook {

    void onClickItem(Book book);
    void onClickItemCong(Book book);
    void onClickItemTru(Book book);
    void onClickDelete(Book book);
}
