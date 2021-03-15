package com.databox.onlinebookshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<BookListAdapter> booksArrayList;

    public RecyclerAdapter(Context mContext, ArrayList<BookListAdapter> booksArrayList) {
        this.mContext = mContext;
        this.booksArrayList = booksArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bokNameView.setText(booksArrayList.get(position).getBookName());
        holder.bokpriceTextView.setText(booksArrayList.get(position).getBookPrice());
        Glide.with(mContext).load(booksArrayList.get(position).getBookImage())
                .into(holder.bokimageView);
    }

    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView bokimageView;
        TextView  bokNameView;
        TextView bokpriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bokimageView = itemView.findViewById(R.id.bokThumbView);
            //bokNameView = itemView.findViewById(R.id.bokName);
            bokpriceTextView = itemView.findViewById(R.id.bookPrice);

        }
    }
}
