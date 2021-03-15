package com.databox.onlinebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bookListView extends AppCompatActivity {

    ListView bookListView;
    FirebaseDatabase database;
    StorageReference bookStorageReference;
    DatabaseReference bookDB;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    BookListAdapter bookListAdapter;
    Button backButton;
    //Context mContext;
    FirebaseListAdapter firebaseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_view);;

        bookListAdapter = new BookListAdapter();
        bookListView = findViewById(R.id.book_list);
        backButton = findViewById(R.id.buttonBackButton);
        database = FirebaseDatabase.getInstance();
        bookDB = database.getReference("Books");
       Query query = FirebaseDatabase.getInstance().getReference().child("Books");
       StorageReference bookStorageReference = FirebaseStorage.getInstance().getReference().child("Books/").child("1611730213670.jpg");
        FirebaseListOptions<Books> options = new FirebaseListOptions.Builder<Books>()
                .setLayout(R.layout.book_layout)
                .setLifecycleOwner(bookListView.this)
                .setQuery(query,Books.class)
                .build();
        firebaseListAdapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView bkName = v.findViewById(R.id.bokName);
                TextView bkPrice = v.findViewById(R.id.bookPrice);

                Books bk = (Books) model;
                bkName.setText("BookName:" +bk.getBookName().toString());
                bkPrice.setText("Price:"+bk.getBookPrice().toString());


//                bookStorageReference.child("Books/1611730213670.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        // Got the download URL for 'users/me/profile.png'
//                        System.out.println(uri.toString());
//
//                        Uri downloadUri = uri;
//                    Picasso.get().load(downloadUri.toString()).into(bkThumb);
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle any errors
//                    }
//                });

//                Glide.with(getApplicationContext())
//                        .load(bookStorageReference)
//                        .into(bkThumb );


            }
        };

        bookListView.setAdapter(firebaseListAdapter);


//      list = new ArrayList<>();
//       adapter = new ArrayAdapter<String>(this , R.layout.book_layout, R.id.bokName, list);
//        bookDB.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               for (DataSnapshot ds:snapshot.getChildren())
//               {
//
//                   bookShowAdapter = ds.getValue(BookShowAdapter.class);
//                   list.add(bookShowAdapter.getBookName().toString()+ " " +bookShowAdapter.getBookPrice().toString());
//               }
//               bookListView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Books books = (Books) firebaseListAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(),bookView.class);
                intent.putExtra("bookName" , books.getBookName().toString());
                intent.putExtra("bookPrice", books.getBookPrice().toString());
                intent.putExtra("bookCategory" , books.getBookCategory().toString());
                intent.putExtra("bookISBN" , books.getBookISBN().toString());
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseListAdapter.stopListening();
    }
}