package com.databox.onlinebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class saveBookItems extends AppCompatActivity {

    private EditText bookName , bookCategory , bookISBN , bookPrice , bookQuanity;
    private ImageView bokImage;
    private Button saveBookList , bookChooseImage ,btnUpload;
    FirebaseDatabase database;
    DatabaseReference bookDBReference;
    StorageReference bookStorageReference;
    int Image_Request_Code = 7;
    Uri FilePathUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_book_items);

        bookName = (EditText) findViewById(R.id.txtEnterName);
        bookCategory = (EditText) findViewById(R.id.txtBookCategory);
        bookISBN = (EditText) findViewById(R.id.txtISBN);
        bookPrice = (EditText) findViewById(R.id.txtPrice);
        bookQuanity = (EditText) findViewById(R.id.txtQuantity);
        saveBookList = (Button) findViewById(R.id.buttonSaveBookList);
        bookChooseImage = (Button) findViewById(R.id.buttonChooseFile);
        bokImage = (ImageView)findViewById(R.id.bookThumbView);
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        database = FirebaseDatabase.getInstance();

        bookStorageReference = FirebaseStorage.getInstance().getReference().child("Books");
        bookDBReference = FirebaseDatabase.getInstance().getReference().child("Books");

        saveBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                saveBookListData();
                bookName.setText(null);
                bookCategory.setText(null);
                bookISBN.setText(null);
                bookPrice.setText(null);

               // bookChooseImage.setId(null);

//                bookStorageReference.putFile(FilePathUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Map<String,Object> data = new HashMap<>();
//                                String newURI = uri.toString();
//                                data.put("bookImgURL",newURI);
//                                bookDBReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                      Toast.makeText(getApplicationContext(),"image stored",Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });


            }
        });

        bookChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                bokImage.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }


        }
    }

    // file extension
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }


    private void saveBookListData() {
        String bookImgID;
//        bookImgID = System.currentTimeMillis()+ ".jpg";
        bookImgID = "";
//         bookImgID =FilePathUri.getLastPathSegment() + ".jpg";
        String bokName = bookName.getText().toString();
        String bokCategory =  bookCategory.getText().toString();
        String bokISBN = bookISBN.getText().toString();
        String bokPrice = bookPrice.getText().toString();

       Books books = new Books(bookImgID,bokName,bokCategory,bokISBN,bokPrice);

       bookDBReference.push().setValue(books);
       //bookDBReference.child("images/" +books);
       Toast.makeText(saveBookItems.this, "Save Books!",Toast.LENGTH_SHORT).show();
    }


    private void uploadImage(){
        if (FilePathUri != null){

           StorageReference fileReference = bookStorageReference.child(System.currentTimeMillis()+ "."+ GetFileExtension(FilePathUri));
            fileReference.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Toast.makeText(saveBookItems.this , "Image upload successfully!", Toast.LENGTH_SHORT).show();
                            Books books = new Books(bookName.getText().toString().trim() , taskSnapshot.getStorage().getDownloadUrl().toString());
                            String imgURL = bookDBReference.push().getKey();
                            bookDBReference.child(imgURL).setValue(books);

//                           fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                               @Override
//                               public void onComplete(@NonNull Task<Uri> task) {
//                                   String bookImgURL = task.getResult().toString();
//                                   Log.i("URL",bookImgURL);
//                               }
//                           });
//
//                            Map<String,Object> data = new HashMap<>();
//                            data.put("bookImage" ,FilePathUri);
//                            bookDBReference.updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(getApplicationContext(),"Image Store",Toast.LENGTH_SHORT).show();
//                                }
//                            });
                        }
                    }). addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(saveBookItems.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this,"No File Selected" , Toast.LENGTH_SHORT).show();
        }
    }

//    private void uploadImage(){
//
//        StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Books").child(FilePathUri.getLastPathSegment() + ".jpg");
//        //StorageReference fileReference = bookStorageReference.child(System.currentTimeMillis()+ "."+ GetFileExtension(FilePathUri));
//
//        UploadTask uploadTask = filePath.putFile(FilePathUri);
//
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//
//                }
//
//                // Continue with the task to get the download URL
//                return filePath.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
//        });
//
//    }


}