package com.example.firebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText emailId,passwordId,confirmpasswordId;
    TextView signin;
    Button signup;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth=FirebaseAuth.getInstance();

        emailId=findViewById(R.id.txtEmailSignUp);
        passwordId=findViewById(R.id.txtPasswordSignUp);
        confirmpasswordId=findViewById(R.id.txtConfirmPasswordSignUp);
        signin=findViewById(R.id.txtSignIn);
        signup=findViewById(R.id.btnSignIn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailId.getText().toString();
                String password=passwordId.getText().toString();
                String confirmpassword=confirmpasswordId.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Email is Empty",Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Password is Empty",Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if (!confirmpassword.equals(password)){
                    Toast.makeText(SignUpActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
                }
                else if (confirmpassword.length()<6 && password.length()<6){
                    Toast.makeText(SignUpActivity.this,"Password must be more than 6 letters",Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(SignUpActivity.this,"Sign Un",Toast.LENGTH_SHORT).show();
                       }
                       else{
                            startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
                       }
                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}