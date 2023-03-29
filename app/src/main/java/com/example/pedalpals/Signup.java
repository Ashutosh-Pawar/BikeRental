package com.example.pedalpals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import java.util.regex.Pattern;


public class Signup extends AppCompatActivity {

    Database db;
    // Regular expression for valid username (alphanumeric characters and underscore)
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");

    // Regular expression for valid name (letters only)
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]{2,30}$");

    // Regular expression for valid mobile number (10 digits)
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");

    EditText first_name, last_name, email, hall, room, username, password, re_password, mobile_number;
    TextView incorrect;
    Button signUp, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        db = new Database(this);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        hall = findViewById(R.id.hall);
        room = findViewById(R.id.room);
        username = findViewById(R.id.username);
        mobile_number = findViewById(R.id.mobile_number);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        signUp = findViewById(R.id.signup_button);
        login = findViewById(R.id.goto_login_button);
        incorrect = findViewById(R.id.incorrect_details);

//        db.insertData_Transaction("lovish","sarthak","12","2020-04-06","2020-04-07",3);

        AddData();
        gotoLogin();
    }

    private void AddData(){
        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(first_name.getText().toString().trim().equals("") || username.getText().toString().trim().equals("") ||
                                email.getText().toString().trim().equals("") || mobile_number.getText().toString().trim().equals("") ||
                                password.getText().toString().equals("") || re_password.getText().toString().equals("")){
                            incorrect.setText("Fields cannot be left blank.");
                            return;
                        }
                        else if(!isFormValid(username.getText().toString(),first_name.getText().toString(),last_name.getText().toString(),mobile_number.getText().toString())){
                            incorrect.setText("Please Enter valid information!!!.");
                            return;
                        }
                        Cursor res;
                        res = db.getData_User_username(username.getText().toString().trim());
                        if(res.getCount()!=0){
                            incorrect.setText("Username is already in use.");
                            return;
                        }

                        res = db.getData_User_username(username.getText().toString().trim());
                        if(res.getCount()!=0){
                            incorrect.setText("Username is already in use.");
                            return;
                        }

                        res = db.getData_User_email_id(email.getText().toString().trim());
                        if(res.getCount()!=0){
                            incorrect.setText("Email-ID is already in use.");
                            return;
                        }

                        res = db.getData_User_mobile_number(mobile_number.getText().toString().trim());
                        if(res.getCount()!=0){
                            incorrect.setText("Mobile Number is already in use.");
                            return;
                        }

                        try {
                            Long i = Long.parseLong(mobile_number.getText().toString().trim());
                        } catch (NumberFormatException nfe) {
                            incorrect.setText("Mobile Number is incorrect.");
                            return;
                        }

                        if(password.getText().toString().equals(re_password.getText().toString())){
                            boolean isInserted = db.insertData_User(first_name.getText().toString().trim(),
                                    last_name.getText().toString().trim(),
                                    email.getText().toString().trim(),
                                    "None",
                                    "None",
                                    username.getText().toString().trim(),
                                    password.getText().toString(),
                                    mobile_number.getText().toString().trim());

                            if(isInserted) {
                                incorrect.setText("");
                                Toast.makeText(Signup.this, "User Registered", Toast.LENGTH_SHORT).show();
                                login.setVisibility(View.VISIBLE);
                                first_name.getText().clear();
                                last_name.getText().clear();
                                email.getText().clear();
                                hall.getText().clear();
                                room.getText().clear();
                                username.getText().clear();
                                password.getText().clear();
                                re_password.getText().clear();
                                mobile_number.getText().clear();
                            }
                            else
                                Toast.makeText(Signup.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(Signup.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                            password.getText().clear();
                            re_password.getText().clear();
                        }

                    }
                }
        );
    }

    private void gotoLogin() {
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Signup.this, UserLogin.class);
                        startActivity(i);
                        finish();
                    }
                }
        );
    }

    // Function to validate username
    public boolean isUsernameValid(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    // Function to validate first and last name
    public boolean isNameValid(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    // Function to validate mobile number
    public boolean isMobileValid(String mobile) {
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

    // Function to validate all form fields
    public boolean isFormValid(String username, String firstName, String lastName, String mobile) {
        return isUsernameValid(username) && isNameValid(firstName) && isNameValid(lastName) && isMobileValid(mobile);
    }
}
