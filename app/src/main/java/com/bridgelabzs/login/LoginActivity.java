package com.bridgelabzs.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bridgelabzs.DatabaseHelper.SQLiteDatabaseHelper;
import com.bridgelabzs.common.SharedPreferencesManager;
import com.bridgelabzs.dashboard.DashboardActivity;
import com.bridgelabzs.forgotpassword.ForgotPasswordActivity;
import com.bridgelabzs.fundooapp.R;
import com.bridgelabzs.login.model.UserDatabaseManager;
import com.bridgelabzs.login.model.UserLoginModel;
import com.bridgelabzs.register.RegisterActivity;
import com.bridgelabzs.register.UserUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity.class";
    EditText etEmailValidate;
    EditText etPasswordValidate;
    Button login;
    Button register;
    String emailPattern;
    String passwordPattern;
    String emailInput;
    private ViewModel viewModel;
    SQLiteDatabaseHelper sqLiteDatabaseHelper ;
    SharedPreferencesManager sharedPreferencesManager;
    UserDatabaseManager userDatabaseManager = new UserDatabaseManager(this);
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        initViews();

//        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
//                if(mFirebaseUser != null){
//                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(LoginActivity.this,"Please Log In",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        };

    }

    protected void onStart() {

        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    private void initViews(){

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        etEmailValidate = findViewById(R.id.username);
        etPasswordValidate = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerHere);
        handleEmail();
        handlePassword();

    }

    private void handleEmail(){
        etEmailValidate = findViewById(R.id.username);
        emailInput = etEmailValidate.getText().toString().trim();
        Log.e("emailInput",emailInput);
        etEmailValidate.setError("Field can't be empty");
        etEmailValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etEmailValidate.setError(UserUtil.getInstance().isEmailValid(s.toString()));
            }
        });
    }

    private void handlePassword(){
        etPasswordValidate = findViewById(R.id.password);
        etPasswordValidate.setError("Field can't be empty");
        etPasswordValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPasswordValidate.setError(UserUtil.getInstance().isPasswordValid(s.toString()));
            }
        });
    }

    public void onClickLogin(View view){
        final String email = etEmailValidate.getText().toString().trim();
        final String password = etPasswordValidate.getText().toString().trim();
        sharedPreferencesManager.saveLoginDetails(email, password);
        Log.e("EmailValue",email);
        Log.e("emailtag",emailInput);
        UserLoginModel loginModel = new UserLoginModel(email, password);
        String emailId = loginModel.getEmail();
        String autPassword = loginModel.getPassword();
        viewModel = new ViewModel(this);
        viewModel.authenticateUser(emailId,autPassword);
        doSignInWithFirebase(email,password);

        if(etEmailValidate.getError() == null && etPasswordValidate.getError() == null && viewModel.authenticateUser(emailId,autPassword)){
//            doSignInWithFirebase(email,password);
            Intent loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(loginIntent);
        }
        else{
            Toast.makeText(getApplicationContext()," you are not register Please Register Here",Toast.LENGTH_SHORT).show();
        }

    }

    public void doSignInWithFirebase(String email, String password){
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText( LoginActivity.this ,"login successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText( LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void openRegister(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void openForgotPassword(View view){
        Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordIntent);
    }

}
