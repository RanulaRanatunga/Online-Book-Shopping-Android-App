package com.databox.onlinebookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class bookView extends AppCompatActivity {

    private TextView bookName , bookCategory, bookPrice , bookISBN;
    private ImageView bookImage;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        bookName = (TextView) findViewById(R.id.bookName);
        bookCategory = (TextView) findViewById(R.id.bokCategory);
        bookPrice = (TextView) findViewById(R.id.bokPrice);
        bookISBN = (TextView) findViewById(R.id.bokISBN);
        backButton = (Button) findViewById(R.id.backButton);

        Intent intent = getIntent();
        String bkName = intent.getStringExtra("bookName");
        String bkPrice = intent.getStringExtra("bookPrice");
        String bkISBN = intent.getStringExtra("bookISBN");
        String bkCategory = intent.getStringExtra("bookCategory");
        bookName.setText(bkName.toString());
        bookCategory.setText(bkCategory.toString());
        bookPrice.setText(bkPrice.toString());
        bookISBN.setText(bkISBN.toString());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bookListView.class));
            }
        });

    }

}