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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {

    private EditText textName , textEmail , textPassword , textRePassword;
    private Button buttonSignUp , buttonAleadyHvAccount;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textName =(EditText) findViewById(R.id.txtFullName);
        textEmail = (EditText) findViewById(R.id.txtEmailSignUp);
        textPassword = (EditText) findViewById(R.id.txtPasswordSignUp);
        textRePassword = (EditText) findViewById(R.id.txtRePasswordSignUp);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonAleadyHvAccount = (Button) findViewById(R.id.alreadyAccount);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),login.class));
            finish();
        }

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                checkField(textName);
                checkField(textEmail);
                checkField(textPassword);
                checkField(textRePassword);

                if (TextUtils.isEmpty(email)) {
                    textEmail.setError("Email is Required!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    textPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 7 ) {
                    textPassword.setError("Password must be at least 7 characters");
                    return;
                }

                if (password.length() <7){
                    textRePassword.setError("Password must be at least 7 Characters");
                    return;
                }

//                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                        }else {
//                            Toast.makeText(registration.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });

                if (valid){
                    // start user registration process
                    fAuth.createUserWithEmailAndPassword(textEmail.getText().toString(),textPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(registration.this,"Account hast been Created!",Toast.LENGTH_SHORT).show();
                            DocumentReference dF = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Full Name", textName.getText().toString());
                            userInfo.put("Email",textEmail.getText().toString());
                            userInfo.put("isUser" ,"1");
                            dF.set(userInfo);
                            userRoll(authResult.getUser().getUid());
                            startActivity(new Intent(getApplicationContext(),login.class));
                            progressBar.setVisibility(View.GONE);
                            textName.setText(null);
                            textEmail.setText(null);
                            textPassword.setText(null);
                            textPassword.setText(null);
                            progressBar.setVisibility(View.VISIBLE);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registration.this,"Failed to Create an Account", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

        buttonAleadyHvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));

            }
        });
    }


    private void userRoll(String uid) {
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

    public boolean checkField (EditText textField)
    {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("ERROR");
            valid = false;
        } else {
            valid = true;
        }
        return true;
    }

}