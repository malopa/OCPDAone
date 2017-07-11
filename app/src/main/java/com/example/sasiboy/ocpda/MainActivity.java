package com.example.sasiboy.ocpda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.LoginResponse;
import com.example.sasiboy.ocpda.model.SharedPreferenceManager;
import com.example.sasiboy.ocpda.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etUserName, etPassword;
    Button  btnLogin;
    RadioButton btnUser, btnSaler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (SharedPreferenceManager.getmInstance(this).isLoggedIn()){
            String s = SharedPreferenceManager.getmInstance(this).getStatus().toLowerCase();
            if (s.equals("buyer")){
                startActivity(new Intent(this,UserViewActivity.class));
                finish();
            }else{
                startActivity(new Intent(this,Bprofile.class));
                finish();
            }
        }
        etUserName=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
//       btnUser=(RadioButton)findViewById(R.id.radioUser);
//        btnSaler=(RadioButton)findViewById(R.id.radioSeller);
        btnLogin=(Button)findViewById(R.id.btnLogin);
    }

    public void onClickLogin(View view) {
        String Username = etUserName.getText().toString();
        String Password = etPassword.getText().toString();
        if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError("Enter your UserName");
        } if(etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter your Password");
        } else {


            ApiOcpda clientLogin = ServiceGenerator.createService(ApiOcpda.class);
            Call<LoginResponse> call = clientLogin.login(Username,Password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse user = response.body();



                    if (!user.isError()) {
                        SharedPreferenceManager.getmInstance(MainActivity.this).userLogin(user.getUser());
                        String status = SharedPreferenceManager.getmInstance(MainActivity.this).getStatus().toLowerCase();

                        if (status.equals("buyer")) {
                            startActivity(new Intent(MainActivity.this, UserViewActivity.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, Bprofile.class));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),user.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                }
            });

//            String type = "login";

//            BackGroundWorker backGroundWorker = new BackGroundWorker(this);
//            backGroundWorker.execute(type, Username, Password);
//            if(btnUser.isChecked()) {
//                startActivity(new Intent(this, UserViewActivity.class));
//                etUserName.setText("");
//                etPassword.setText("");
//            }
//            else if(btnSaler.isChecked()) {
//                startActivity(new Intent(this, Bprofile.class));
//                etUserName.setText("");
//                etPassword.setText("");
//            }else{
//                Toast.makeText(this, "Check one of the radio button either User or Saler", Toast.LENGTH_SHORT).show();
//            }


        }

    }
       public boolean validate()
       {
           boolean valid=false;
           if(etUserName.getText().toString().isEmpty())
           {
           etUserName.setError("enter your UserName");
               valid=false;
           }
           if(etPassword.getText().toString().isEmpty())
           {
               etPassword.setError("enter your Password");
               valid=false;
           }
         return valid;
       }




    }

