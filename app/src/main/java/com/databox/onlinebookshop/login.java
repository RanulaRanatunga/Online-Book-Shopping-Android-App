package com.databox.onlinebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    private EditText editTextEmail , editTextPassword;
    private Button buttonSignIn ,buttonNotHavingAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private ProgressBar progressBar;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.txtSignInEmail);
        editTextPassword = (EditText) findViewById(R.id.txtPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonNotHavingAccount = (Button) findViewById(R.id.btnSignUp);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                checkField(editTextEmail);
                checkField(editTextPassword);

                if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 7 ) {
                    editTextPassword.setError("Password must be at least 7 characters");
                    return;
                }

                if (valid) {
                  fAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                      @Override
                      public void onSuccess(AuthResult authResult) {
                        Toast.makeText(login.this, "Login Successfully" ,Toast.LENGTH_SHORT).show();
                        checkIsAdmin(authResult.getUser().getUid());
                         progressBar.setVisibility(View.VISIBLE);
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(login.this, "Error Occur" ,Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.GONE);
                      }
                  });
                }
            }
        });


        buttonNotHavingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registration.class));
            }
        });

    }

    private void checkIsAdmin(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","On Success" + documentSnapshot.getData());
                if (documentSnapshot.getString("isAdmin") != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else if (documentSnapshot.getString("isUser") != null){
                    startActivity(new Intent(getApplicationContext(),bookListView.class));
                    finish();
                }
            }
        });
    }

    public boolean checkField(EditText textField)
    {
        if(textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else  {
            valid = true;
        }
        return  valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}