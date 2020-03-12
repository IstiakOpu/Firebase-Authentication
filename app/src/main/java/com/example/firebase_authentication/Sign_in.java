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

import org.w3c.dom.Text;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {
    Button signInbtn;
    EditText signInemail,signInpassword;
    TextView signUpTextview;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.setTitle("Sign In");
        mAuth = FirebaseAuth.getInstance();
        signInbtn=(Button)findViewById(R.id.signInid);
        signInemail=(EditText)findViewById(R.id.Signinemailid);
        signInpassword=(EditText)findViewById(R.id.Signinpasswordid);
        signUpTextview=(TextView)findViewById(R.id.signuptextviewid);
        progressBar=(ProgressBar)findViewById(R.id.signipprogrssbarid);

        signInbtn.setOnClickListener(this);
        signUpTextview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signInid:

                userLogin();

                 break;

            case R.id.signuptextviewid:

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);


                break;
        }


    }

    private void userLogin() {


        String useremail=signInemail.getText().toString().trim();
        String userpassword=signInpassword.getText().toString().trim();

        if(useremail.isEmpty())
        {

            signInemail.setError("Enter an E-mail Address");
            signInemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches())
        {

            signInemail.setError("Enter a valid E-mail Address");
            signInemail.requestFocus();
            return;
        }

        if(userpassword.isEmpty())
        {
            signInpassword.setError("Enter a Password");
            signInpassword.requestFocus();
            return;

        }
        if(userpassword.length()<6)
        {
            signInpassword.setError("Minimum Password Should be:6");
            signInpassword.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
               if(task.isSuccessful())
               {

                   Toast.makeText(getApplicationContext(),"Sign In Success",Toast.LENGTH_SHORT).show();

                   Intent intent=new Intent(getApplicationContext(),Home.class);
                   startActivity(intent);
               }
               else
               {
                   Toast.makeText(getApplicationContext(),"Sign In Failed",Toast.LENGTH_SHORT).show();
               }


            }
        });

    }
}
