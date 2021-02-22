package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookAdapter2 extends RecyclerView.Adapter<BookAdapter2.ViewHolder> {
    List<Book> list;
    Context context;
    ClickBook clickBook;

    public void setClickBook(ClickBook clickBook) {
        this.clickBook = clickBook;
    }

    public BookAdapter2(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter2.ViewHolder holder, final int position) {
        Picasso.with(context).load(list.get(position).getImageLink()).into(holder.imgBook);
        Locale local = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(list.get(position).getPrice());
        holder.tvPricebook.setText(money + "đ");
        holder.tvTitle.setText(list.get(position).getTitle());
        int tem = list.get(position).getNumOfReview() * 5;
        double rate = (list.get(position).getRateStar() / tem) * 5;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

//        if (list.get(position).getNumOfReview() == 0) {
//            holder.rate.setText("0");
//        } else {
//            holder.rate.setText(decimalFormat.format(rate));
//        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBook.onClickItem(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterList(ArrayList<Book> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle, tvPricebook;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPricebook = itemView.findViewById(R.id.tvPriceBook);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}


