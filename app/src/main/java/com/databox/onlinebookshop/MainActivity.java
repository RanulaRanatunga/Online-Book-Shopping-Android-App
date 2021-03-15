package com.databox.onlinebookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnBookMenu , btnSaveBooks , buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBookMenu = (Button)findViewById(R.id.btnbookmenu);
        btnSaveBooks = (Button) findViewById(R.id.btnsaveBooks);
        buttonLogout = (Button) findViewById(R.id.btnLogout);


        btnSaveBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, saveBookItems.class);
                startActivity(intent);
            }
        });

        btnBookMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuBooks = new Intent(MainActivity.this, bookListView.class);
                startActivity(menuBooks);
            }
        });
    }

    public void logout (View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }
}