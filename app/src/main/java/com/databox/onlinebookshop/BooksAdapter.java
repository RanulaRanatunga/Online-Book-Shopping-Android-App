package com.databox.onlinebookshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BooksAdapter extends ArrayAdapter<String> {

    Context context;
    int[] thumbNail;
    String[] booksName;
    String[] booksPrice;

    public BooksAdapter( Context context, int[] booksThumbNail ,String[] booksName,String[] booksPrice) {
        super(context, R.layout.book_layout,R.id.bokThumbView, booksName);
        this.context = context;
        this.thumbNail = booksThumbNail;
        this.booksName = booksName;
        this.booksPrice = booksPrice;
    }

    public void onClick(View v) {

        Intent i = new Intent(context,bookListView.class);
        i.putExtra("bookName",booksName);
//        i.putExtra("bookname",BookNameList.get(position));
//
        context.startActivity(i);
    }
}
