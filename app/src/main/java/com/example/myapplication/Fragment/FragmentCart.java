package com.example.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Adapter.ClickBook;
import com.example.myapplication.InforBook;
import com.example.myapplication.Internet;
import com.example.myapplication.LogIn;
import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.SqlHelper;
import com.example.myapplication.databinding.FragmentCartBinding;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.BIND_ABOVE_CLIENT;
import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_ID;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.myapplication.Model.AccountAttribute.SHARE_PRE_NAME;

public class FragmentCart extends Fragment {
    FragmentCartBinding binding;
    SqlHelper sqlHelper;
    List<Book> list, listTemp;
    List<Book> listnnew;
    CartAdapter adapter, a2;
    SharedPreferences sp;
    int pos = 0;
    int a, b;

    public static FragmentCart newInstance() {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        list = new ArrayList<>();
        listnnew = new ArrayList<>();
        listTemp = new ArrayList<>();
        sqlHelper = new SqlHelper(getContext());
        getData();
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Internet.checkConnection(getContext())) {
                    if (getStatus()) {
                        if (list.size() > 0) {
                            onDialogOptionBuyShow();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.nothing), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        onDialogLoginShow();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                binding.swiperefresh.setRefreshing(false);
            }
        });


        return binding.getRoot();
    }

    private void onDialogShow(final Book book) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.confirm))
                .setMessage(getString(R.string.delete_item_request))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                        sqlHelper.deleteItemInCartNew(String.valueOf(book.getId()));
                        getData();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }

    public void getData() {
        if (getStatus()) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
            list.clear();
            listTemp.clear();
            list = sqlHelper.getAllBookInCartNew();
            listTemp = loc(list, listTemp, sharedPreferences.getInt(ACCOUNT_ID, 0));

            adapter = new CartAdapter(listTemp, getContext());
//            for (int i = 0; i < listTemp.size(); i++) {
//                int count = countItem(listTemp, listTemp.get(i).getTitle()); // so luong
//                listTemp.get(i).setMount(count);
//                int j = i + 1;
//                for (int k = i + 1; k < listTemp.size(); k++) {
//                    if (listTemp.get(k).getTitle().equals(listTemp.get(i).getTitle()) == true) {
//                        listTemp.remove(k);
//                        k--;
//                    }
//                }
//            }
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.listBookInCart.setAdapter(adapter);
            binding.listBookInCart.setLayoutManager(layoutManager2);
            adapter.notifyDataSetChanged();

            adapter.setClickBook(new ClickBook() {
                @Override
                public void onClickItem(Book book) {

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
            Locale local = new Locale("vi", "VN");
            NumberFormat numberFormat = NumberFormat.getInstance(local);
            String money = numberFormat.format(TotalMoney());
            if (TotalMoney() != 0) {
//            Toast.makeText(getContext(), getString(R.string.delete_item_cart), Toast.LENGTH_SHORT).show();
                binding.btnBuy.setText(getString(R.string.buy) + " " + money + " vnđ");
            } else {
                binding.btnBuy.setText(getString(R.string.nothing));
            }


        } else {
            listTemp = loc(list, listTemp, 0);
            listTemp = sqlHelper.getAllBookInCartNew();
            adapter = new CartAdapter(listTemp, getContext());
//            for (int i = 0; i < listTemp.size(); i++) {
//                int count = countItem(listTemp, listTemp.get(i).getTitle()); // so luong
//                listTemp.get(i).setMount(count);
//                int j = i + 1;
//                for (int k = i + 1; k < listTemp.size(); k++) {
//                    if (listTemp.get(k).getTitle().equals(listTemp.get(i).getTitle()) == true) {
//                        listTemp.remove(k);
//                        k--;
//                    }
//                }
//            }
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.listBookInCart.setAdapter(adapter);
            binding.listBookInCart.setLayoutManager(layoutManager2);
            adapter.notifyDataSetChanged();
        }


        adapter.setClickBook(new ClickBook() {
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

                onDialogShow(book);

            }
        });

        adapter.notifyDataSetChanged();
    }

    private void onDialogOptionBuyShow() {

        boolean[] booleans = {true, false, false, false};
        final List<String> strings = Arrays.asList(getResources().getStringArray(R.array.option_buy));
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.select_option_buy))
                //.setMessage("Yes or No")
                .setIcon(R.drawable.pay)
                .setSingleChoiceItems(R.array.option_buy, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        pos = position;
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                        String date = df.format(Calendar.getInstance().getTime());
                        sqlHelper.deleteCart();
                        Double price = 0.0;
                        for (Book x : listTemp
                        ) {
                            x.setDateBuy(date);
                            price += x.getPrice() * x.getMount();
                            x.setPrice(price);
                            sqlHelper.InsertBookToHistory(x);
                        }
                        listTemp.clear();
                        CartAdapter adapter = new CartAdapter(listTemp, getContext());
                        binding.listBookInCart.setAdapter(adapter);
                        binding.btnBuy.setText(getString(R.string.nothing));
                        Log.d("LOG", String.valueOf(which));
                        Toast.makeText(getContext(), "\t\t\t\t\t\t" + getString(R.string.your_choice) + " " + strings.get(pos), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), getString(R.string.delete_trans), Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }

    private void onDialogLoginShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.un_login))
                .setMessage(getString(R.string.sign_in_now))
                .setIcon(R.drawable.user)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), LogIn.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

    public int countItem(List<Book> list, String name) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().equals(name) == true) {
                count++;
            }
        }
        return count;
    }

    private List<Book> loc(List<Book> list, List<Book> listTemp, int id) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdAcc() == id) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;

    }

    private double TotalMoney() {
        double price = 0;
        for (Book x : listTemp
        ) {
            price += x.getPrice() * x.getMount();

        }
        return price;
    }

}