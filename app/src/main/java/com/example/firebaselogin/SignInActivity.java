package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    TextInputEditText emailId,passwordId;
    Button signin;
    TextView signup;
    FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mFirebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        emailId=findViewById(R.id.txtEmailSignIn);
        passwordId=findViewById(R.id.txtPasswordSignIn);
        signin=findViewById(R.id.btnSignIn);
        signup=findViewById(R.id.txtSignUp);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser !=null ){
                    Toast.makeText(SignInActivity.this,"success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                }
                else{
                    //Toast.makeText(SignInActivity.this,"",Toast.LENGTH_SHORT).show();
                }

            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String password=passwordId.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(SignInActivity.this,"Email is Empty",Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(SignInActivity.this,"Password is Empty",Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignInActivity.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                   mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()){
                          Toast.makeText(SignInActivity.this,"Login Error , please try again",Toast.LENGTH_SHORT).show();
                      }
                      else{
                          startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                      }
                       }
                   });
                }
                else{
                    Toast.makeText(SignInActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}