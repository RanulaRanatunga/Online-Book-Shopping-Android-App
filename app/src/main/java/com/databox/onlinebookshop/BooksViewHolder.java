package com.databox.onlinebookshop;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksViewHolder {

    ImageView bookImage;
    TextView bookHeaderName;
    TextView bookPrice;

    BooksViewHolder( View v){
        bookImage = v.findViewById(R.id.bokThumbView);
        bookHeaderName = v.findViewById(R.id.bookName);
        bookPrice = v.findViewById(R.id.bookPrice);
    }
}