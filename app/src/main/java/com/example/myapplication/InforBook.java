package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BookAdapter;
import com.example.myapplication.Adapter.ClickBook;
import com.example.myapplication.Model.Book;
import com.example.myapplication.databinding.ActivityInforBookBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.myapplication.Model.AccountAttribute.SHARE_PRE_NAME;


public class InforBook extends AppCompatActivity {
    Book book;
    List<Book> bookList, similarBook;
    SqlHelper sqlHelper;
    int num;
    Double rate;
    ActivityInforBookBinding binding;
    BookAdapter bookAdapter;
    String cato;
    String des;
    int count = 0;
    List<Book> list;
int a,b=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_infor_book);
        similarBook = new ArrayList<>();
        list = new ArrayList<>();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sqlHelper = new SqlHelper(getBaseContext());
        Intent i = getIntent();
        book = (Book) i.getSerializableExtra("book");
        des = i.getStringExtra("des");
        num = i.getIntExtra("NumOfReview", 0);
        cato = i.getStringExtra("Category");
        rate = i.getDoubleExtra("Rate", 0.0);

        Picasso.with(getBaseContext()).load(book.getImageLink()).into(binding.imgBook);
        binding.tvBookName.setText(book.getTitle());
        binding.tvAuthor.setText("Tác giả: " + book.getAuthor());
        binding.tvDescrition.setText(des);
        binding.tvNumberOfPage.setText(book.getNumOfPage() + " " + getString(R.string.page));
        Locale local = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(book.getPrice());
        binding.tvPrice.setText(money + " đ");
        binding.tvNumOfReview.setText(num + " lượt đánh giá");
        binding.tvCategory.setText(cato);
        binding.tvstarOfBook.setText(rate + "");


        if (num > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            double star = rate / num;
            binding.tvstarOfBook.setText(decimalFormat.format(star));
        } else {
            binding.tvstarOfBook.setText("0");
        }


        similarBook = sqlHelper.getListBookName(cato);
        bookAdapter = new BookAdapter(similarBook, getBaseContext());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getBaseContext(), RecyclerView.HORIZONTAL, false);
        binding.similarBook.setAdapter(bookAdapter);
        binding.similarBook.setLayoutManager(layoutManager2);
        bookAdapter.setClickBook(new ClickBook() {
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

        binding.btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sqlHelper.UpDateDataBook(book.getTitle(), Double.parseDouble(String.valueOf(rate)), num + 1)) {
                    Toast.makeText(getBaseContext(), getString(R.string.your_evaluate) + " " + binding.rateStar.getRating() + " " + getString(R.string.star), Toast.LENGTH_SHORT).show();
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    Double sta = (rate + binding.rateStar.getRating()) / (num + 1);
                    binding.tvstarOfBook.setText(String.valueOf(decimalFormat.format(sta)));
                    binding.tvNumOfReview.setText((num + 1) + " " + getString(R.string.numOfEvaluate));
                    bookAdapter.notifyDataSetChanged();
                    num = num + 1;
                } else {

                    Toast.makeText(getBaseContext(), getString(R.string.evaluate_eror), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale local = new Locale("vi", "VN");
                final NumberFormat numberFormat = NumberFormat.getInstance(local);
                binding.th.setVisibility(View.VISIBLE);
                binding.name.setText(book.getTitle());
                Double tienq = book.getPrice();
                String aa = numberFormat.format(tienq);
                binding.gia.setText(aa + "vnđ");
                binding.cg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a = Integer.parseInt(binding.slg.getText().toString());
                        b= a+1;
                        book.setMount(a);
                        binding.slg.setText(String.valueOf(b));
                        Double tien = b*book.getPrice();

                        String money = numberFormat.format(tien);
                        binding.gia.setText(money +"vnđ");
                        if(a==0||b==0){
                            binding.slg.setText(0+"");
                            binding.gia.setText(0 +" vnđ");
                        }
                    }
                });
                binding.tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a = Integer.parseInt(binding.slg.getText().toString());
                        b= a-1;
                        book.setMount(b);
                        binding.slg.setText(String.valueOf(b));
                        Double tien = b*book.getPrice();
                        Locale local = new Locale("vi", "VN");
                        NumberFormat numberFormat = NumberFormat.getInstance(local);
                        String money = numberFormat.format(tien);
                        binding.gia.setText(money +"vnđ");
                        if(a==0||b==0){
                            binding.slg.setText(0+"");
                            binding.gia.setText(0 +"vnđ");

                        }
                    }
                });
                binding.ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
                    int idAcc = sharedPreferences.getInt(ACCOUNT_ID, 0);
                    final Book book1 = new Book(book.getId(), book.getImageLink(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getReleaseYear(), book.getNumOfPage(), book.getPrice(), rate, num, des, cato, b, idAcc);
                    sqlHelper.InsertBookToCartNew(book1);
                    binding.th.setVisibility(View.GONE);
                        Toast.makeText(InforBook.this, "Add to cart successful", Toast.LENGTH_SHORT).show();
                    }
                });
           }

        });


    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}