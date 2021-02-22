package com.example.myapplication.Adapter;

import android.content.Context;
import android.text.TextUtils;
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

import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    List<Book> list;
    Context context;
    ClickBook ionClickBook;

    public HistoryAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setIonClickBook(ClickBook ionClickBook) {
        this.ionClickBook = ionClickBook;
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_in_history,parent,false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        final Book book = list.get(position);
        Picasso.with(context).load(book.getImageLink()).into(holder.imgBook);
        holder.tvTitle.setText(book.getTitle());
        holder.tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.tvTitle.setHorizontallyScrolling(true);
        holder.tvTitle.setSelected(true);
        holder.tvTitle.setMarqueeRepeatLimit(-1);
        holder.tvTitle.setFocusable(true);
        Locale local =new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(book.getPrice());
        holder.tvPricebook.setText(money+"đ");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ionClickBook.onClickItem(book);
            }
        });
        holder.tvDateBuy.setText(book.getDateBuy());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvTitle,tvPricebook,tvDateBuy;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBookInHis);
            tvTitle = itemView.findViewById(R.id.tvTitleInHis);
            tvPricebook= itemView.findViewById(R.id.tvPriceBookInHis);
            layout = itemView.findViewById(R.id.layoutClickerInHis);
            tvDateBuy = itemView.findViewById(R.id.tvDateBuy);

        }
    }
}
