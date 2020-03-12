package com.bridgelabzs.register;

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

import com.bridgelabzs.fundooapp.R;
import com.bridgelabzs.login.LoginActivity;
import com.bridgelabzs.login.ViewModel;
import com.bridgelabzs.login.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword;
    Button registerButton;
    private ViewModel viewModel;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        initViews();

    }

    private void initViews(){
        handleFirstName();
        handleLastName();
        handleEmail();
        handlePassword();
        etConfirmPassword = findViewById(R.id.confirmPassword);
        etPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
    }

    private void handleFirstName(){
        etFirstName = findViewById(R.id.firstName);
        etFirstName.setError("Field can't be empty");
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etFirstName.setError(UserUtil.getInstance().isNameValid(s.toString()));
            }
        });
    }

    private void handleLastName() {
        etLastName = findViewById(R.id.lastName);
        etLastName.setError("Field can't be empty");
        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etLastName.setError(UserUtil.getInstance().isNameValid(s.toString()));
            }
        });
    }

    private void handleEmail(){
        etEmail = findViewById(R.id.registerEmail);
        etEmail.setError("Field can't be empty");
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etEmail.setError(UserUtil.getInstance().isEmailValid(s.toString()));
            }
        });
    }

    private void handlePassword(){
        etPassword = findViewById(R.id.registerPassword);
        etPassword.setError("Field can't be empty");
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPassword.setError(UserUtil.getInstance().isPasswordValid(s.toString()));
            }
        });
    }

    private boolean isPasswordMatches(){
        final String confirmPasswordInput = etConfirmPassword.getText().toString().trim();
        final String passwordInput = etPassword.getText().toString().trim();


        if(confirmPasswordInput.isEmpty()){
            etConfirmPassword.setError("Field can't be empty");
            return false;
        }else if(!confirmPasswordInput.equals(passwordInput)){
            etConfirmPassword.setError("Password does not match");
            return false;
        }else{
            etConfirmPassword.setError(null);
            return true;
        }
    }


//    public void doSignUpWithFirebase(String email, String password) {
//        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(RegisterActivity.this, "login successful", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }

    public void onClickRegister(View view){
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        Log.e("fName",firstName);
        Log.e("lName",lastName);
        Log.e("email",email);
        Log.e("password",password);
        Log.e("cpassword",confirmPassword);

        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
//                Toast.makeText(RegisterActivity.this, "Inside auth", Toast.LENGTH_LONG).show();
                if(task.isSuccessful()){
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        User user = new User(firstName,lastName,email,password);
        viewModel =new ViewModel(this);
        viewModel.addUser(user);

        if (etFirstName.getError() == null && etLastName.getError() == null &&
                etEmail.getError() == null && etPassword.getError() == null
                && isPasswordMatches()) {
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Please enter valid information to register",Toast.LENGTH_SHORT).show();
        }
    }
}
