package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Adapter.ClickBook;
import com.example.myapplication.Model.Book;
import com.example.myapplication.databinding.ActivityCartBinding;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    ActivityCartBinding binding;
    SqlHelper sqlHelper;
    List<Book> list;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        list = new ArrayList<>();
        sqlHelper = new SqlHelper(getBaseContext());
        list = sqlHelper.getAllBookInCart();
        adapter = new CartAdapter(list, getBaseContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        binding.listBookInCart.setAdapter(adapter);
        binding.listBookInCart.setLayoutManager(layoutManager2);
        System.out.println("listcarrt  : " + list.size());

        adapter.setClickBook(new ClickBook() {
            @Override
            public void onClickItem(Book book) {
                Intent intent = new Intent(getBaseContext(), InforBook.class);

                intent.putExtra("book", (Parcelable) book);
                intent.putExtra("des", book.getDescription());
                intent.putExtra("NumOfReview", book.getNumOfReview());
                intent.putExtra("Category", book.getCategory());
                intent.putExtra("Rate", book.getRateStar());
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
    }
}