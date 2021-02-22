package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.SqlHelper;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<Book> list;
    Context context;
    ClickBook clickBook;
    int row;

    public void setClickBook(ClickBook clickBook) {
        this.clickBook = clickBook;
    }

    public CartAdapter(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_more, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {
        Picasso.with(context).load(list.get(position).getImageLink()).into(holder.imgBook);
        holder.tvCount.setText(String.valueOf(list.get(position).getMount()));
        final Locale local = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getInstance(local);
        String money = numberFormat.format(list.get(position).getPrice());
        holder.tvPricebook.setText(money + "đ");
        holder.tvTitle.setText(list.get(position).getTitle());
        int tem = list.get(position).getNumOfReview() * 5;
        double rate = (list.get(position).getRateStar() / tem) * 5;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        if (list.get(position).getNumOfReview() == 0) {
            holder.rate.setText("0");
        } else {
            holder.rate.setText(decimalFormat.format(rate));
        }
        holder.imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBook.onClickItem(list.get(position));
            }
        });
        holder.imgCong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SqlHelper sqlHelper = new SqlHelper(context);
                clickBook.onClickItemCong(list.get(position));
                row = position;
               int  c =  Integer.parseInt(holder.tvCount.getText().toString());
                int d = c+1;
                if(row == position){
                    holder.tvCount.setText(String.valueOf(d));
                }
                if(c<0 || d<0){
                    holder.tvCount.setText("0");
                }
              sqlHelper.updateData(list.get(position).getTitle(),d);
            }


        });

        holder.imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqlHelper sqlHelper = new SqlHelper(context);
                clickBook.onClickItemTru(list.get(position));
                row = position;
               int c =  Integer.parseInt(holder.tvCount.getText().toString());
                int d = c-1;
                if(row == position){
                    holder.tvCount.setText(String.valueOf(d));
                }
                if(c<0 || d<0){
                    holder.tvCount.setText("0");
                }
                sqlHelper.updateData(list.get(position).getTitle(),d);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBook.onClickDelete(list.get(position));
                list.remove(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnBuy,btupdate;
        ImageView imgBook, imgCong, imgTru;
        TextView tvTitle, tvPricebook, tvCount;
        RelativeLayout layout;
        TextView rate;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPricebook = itemView.findViewById(R.id.tvPriceBook);
            layout = itemView.findViewById(R.id.layout);
            rate = itemView.findViewById(R.id.rate);
            imgCong = itemView.findViewById(R.id.cong);
            imgTru = itemView.findViewById(R.id.tru);
            tvCount = itemView.findViewById(R.id.count);
            delete = itemView.findViewById(R.id.delete);
//            btnBuy =(Button) itemView.findViewById(R.id.btnBuy);
//            btupdate =(Button) itemView.findViewById(R.id.btupdate);


        }
    }
}
