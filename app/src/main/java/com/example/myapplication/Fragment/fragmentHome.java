package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BookAdapter;
import com.example.myapplication.Adapter.ClickBook;
import com.example.myapplication.InforBook;
import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RequestInterface;
import com.example.myapplication.SqlHelper;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragmentHome extends Fragment {
    List<String> mangquangcao = new ArrayList<>();
    FragmentHomeBinding binding;
    List<Book> list, listtemp, list2, list3;
    BookAdapter bookAdapter, bookAdapter2, bookAdapter3;
    SqlHelper sqlHelper;

    public static fragmentHome newInstance() {
        fragmentHome fragment = new fragmentHome();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progressCircular.setVisibility(View.GONE);
            }
        }, 700);
        list = new ArrayList<>();
        sqlHelper = new SqlHelper(getContext());
        //String imageLink, String title, String author, String publisher, int releaseYear, int numOfPage, double price, double rateStar, int numOfReview, String description, String categoty
        Book book = new Book("imageLink", "title", "author", "publisher", 3, 3, 9.5, 4.5, 67, "description", "categoty");
        sqlHelper.InsertBookToAllBook(book);
        getBookResponse();
        listtemp = new ArrayList<>();
        listtemp = sqlHelper.getAllBook();
        ViewFlipper();
        return binding.getRoot();
    }


    private void getBookResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bookshopb.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInteface = retrofit.create(RequestInterface.class);
        Call<List<Book>> call = requestInteface.getBookJSon();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                list = response.body();

                for (int i = 0; i < list.size(); i++) {
                    boolean check = false;
                    for (Book x : listtemp
                    ) {
                        if (x.getId() == list.get(i).getId()) {
                            check = true;
                        }
                    }
                    if (!check) {
                        sqlHelper.InsertBookToAllBook(list.get(i));
                    }

                    Log.d("book", "" + list.get(i));

                }

                bookAdapter = new BookAdapter(listtemp, getContext());
                binding.listNewBook.setAdapter(bookAdapter);
                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.listNewBook.setLayoutManager(layoutManager1);
                bookAdapter.notifyDataSetChanged();
                bookAdapter.setClickBook(new ClickBook() {
                    @Override
                    public void onClickItem(Book book) {
                        Intent intent = new Intent(getContext(), InforBook.class);

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


                Collections.reverse(listtemp);
                bookAdapter2 = new BookAdapter(listtemp, getContext());
                binding.listOfferBook.setAdapter(bookAdapter2);
                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.listOfferBook.setLayoutManager(layoutManager2);
                bookAdapter2.notifyDataSetChanged();
                bookAdapter2.setClickBook(new ClickBook() {
                    @Override
                    public void onClickItem(Book book) {
                        Intent intent = new Intent(getContext(), InforBook.class);

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
                list3 = new ArrayList<>();
                for (int j = listtemp.size() / 2; j >= 0; j--) {
                    list3.add(listtemp.get(j));
                }
                for (int j = listtemp.size() / 2 + 1; j < list.size() - 1; j++) {
                    list3.add(listtemp.get(j));
                }
                bookAdapter3 = new BookAdapter(list3, getContext());
                binding.listHotBook.setAdapter(bookAdapter3);
                RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                binding.listHotBook.setLayoutManager(layoutManager3);
                bookAdapter3.notifyDataSetChanged();
                bookAdapter3.setClickBook(new ClickBook() {
                    @Override
                    public void onClickItem(Book book) {
                        Intent intent = new Intent(getContext(), InforBook.class);

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
                binding.moreHotBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("KEY", (ArrayList<? extends Parcelable>) listtemp);
                        Navigation.findNavController(v).navigate(R.id.action_home_to_listBookFragment, bundle);
                    }
                });

                binding.moreNewBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("KEY", (ArrayList<? extends Parcelable>) list);
                        Navigation.findNavController(v).navigate(R.id.action_home_to_listBookFragment, bundle);
                    }
                });
                binding.moreOfferBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("KEY", (ArrayList<? extends Parcelable>) list3);
                        Navigation.findNavController(v).navigate(R.id.action_home_to_listBookFragment, bundle);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void ViewFlipper() {
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978768/Ma-giam-gia-Fahasa_kn9mcd.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978870/M%C3%A3-gi%E1%BA%A3m-gi%C3%A1-S%C3%A1ch_evuiwm.jpg");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/ma-giam-gia-sach_fbnt0s.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/m%C3%A3-gi%E1%BA%A3m-gi%C3%A1-vinabook-1_dfgmq2.jpg");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978884/ma-giam-gia-vinabook_m8cvi2.png");
        mangquangcao.add("https://res.cloudinary.com/yami177/image/upload/v1598978885/voucher_qslawk.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Picasso.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.viewFlipper.addView(imageView);
        }
        binding.viewFlipper.setFlipInterval(3000);
        binding.viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        binding.viewFlipper.setInAnimation(animation_slide_in);
        binding.viewFlipper.setOutAnimation(animation_slide_out);
    }
}