package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("books")
    Call<List<Book>> getBookJSon();
}
