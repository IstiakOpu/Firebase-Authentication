package com.example.firebase_authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

Button signUpbtn;
EditText signUpemail,signUppassword;
TextView signinTextview;
ProgressBar progressBar;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Sign Up");
        mAuth=FirebaseAuth.getInstance();

        signUpbtn=(Button)findViewById(R.id.signupidbtn);
        signUpemail=(EditText)findViewById(R.id.emailid);
        signUppassword=(EditText)findViewById(R.id.passwordid);
        signinTextview=(TextView)findViewById(R.id.signintextviewid);
        progressBar=(ProgressBar)findViewById(R.id.signupprogrssbarid);

        signUpbtn.setOnClickListener(this);
        signinTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.signupidbtn:

                userReg();

                break;

            case R.id.signintextviewid:
                finish();
                Intent intent=new Intent(getApplicationContext(),Sign_in.class);
                startActivity(intent);
        }
    }

    private void userReg() {

        String useremail=signUpemail.getText().toString().trim();
        String userpassword=signUppassword.getText().toString().trim();

        if(useremail.isEmpty())
        {

            signUpemail.setError("Enter an E-mail Address");
            signUpemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches())
        {

            signUpemail.setError("Enter a valid E-mail Address");
            signUpemail.requestFocus();
            return;
        }

        if(userpassword.isEmpty())
        {
            signUppassword.setError("Enter a Password");
            signUppassword.requestFocus();
            return;

        }
        if(userpassword.length()<6)
        {
            signUppassword.setError("Minimum Password Should be:6");
            signUppassword.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
               if(task.isSuccessful())
               {

                   Toast.makeText(getApplicationContext(),"Registration is Successfull",Toast.LENGTH_SHORT).show();
                   finish();
                   Intent intent=new Intent(getApplicationContext(),Home.class);
                   startActivity(intent);

               }
               else
               {
                   if(task.getException() instanceof FirebaseAuthUserCollisionException)
                   {
                       Toast.makeText(getApplicationContext(),"User is Already Regestred",Toast.LENGTH_SHORT).show();
                   }
                   else {
                       Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });


    }
}
