package com.example.aeiys.photoblog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText reg_email_field , reg_pass_field , reg_confirm_pass_field;
    Button reg_btn , reg_login_btn;
    ProgressBar reg_progress;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_email_field = (EditText)findViewById(R.id.reg_email);
        reg_pass_field = (EditText)findViewById(R.id.reg_pssword);
        reg_confirm_pass_field = (EditText)findViewById(R.id.reg_confirem_password);

        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_login_btn = (Button) findViewById(R.id.reg_login_btn);

        reg_progress = (ProgressBar)findViewById(R.id.reg_progress);

        mAuth = FirebaseAuth.getInstance();

        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = reg_email_field.getText().toString();
                String pass = reg_pass_field.getText().toString();
                String confirm_pass = reg_confirm_pass_field.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)){

                    if (pass.equals(confirm_pass)){

                        reg_progress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    sendToSetup();

                                }else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : "+ errorMessage, Toast.LENGTH_SHORT).show();
                                }

                                reg_progress.setVisibility(View.INVISIBLE);

                            }
                        });

                    }else{
                        Toast.makeText(RegisterActivity.this, "Confirm Password and Pssword are not match ", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            sendToMain();
        }

    }

    private void sendToMain() {

        Intent mainInent = new Intent(RegisterActivity.this ,MainActivity.class);
        startActivity(mainInent);
        finish();
    }

    private void sendToSetup(){
        Intent setupIntent = new Intent(RegisterActivity.this,SetupActivity.class);
        startActivity(setupIntent);
        finish();

    }
}
