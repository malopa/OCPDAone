package com.example.sasiboy.ocpda;

import android.content.Intent;
import android.support.v4.app.ServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.LoginResponse;
import com.example.sasiboy.ocpda.model.SharedPreferenceManager;
import com.example.sasiboy.ocpda.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    EditText FirstName, LastName, UserName, Password, Cpassword, Location, Phone, Status;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirstName=(EditText)findViewById(R.id.etfirstname);
       LastName=(EditText)findViewById(R.id.etLastName);
       UserName=(EditText)findViewById(R.id.etUserName);
       Password=(EditText)findViewById(R.id.etPassword);
      Cpassword=(EditText)findViewById(R.id.etCPassword);
       Phone=(EditText)findViewById(R.id.etPhone);
      Location=(EditText)findViewById(R.id.etLocation);
       Status=(EditText)findViewById(R.id.etStatus);
        btnRegister=(Button)findViewById(R.id.btnRegister);

    }

    public void onClickRegister(View view)
    {
if(!validate())
{
 Toast.makeText(this, "Make sure all fields are correctly filled", Toast.LENGTH_SHORT ).show();
}else {


    String str_firstname = FirstName.getText().toString();
    String str_lastname = LastName.getText().toString();
    String str_username = UserName.getText().toString();
    String str_password = Password.getText().toString();
    String str_cpassword = Cpassword.getText().toString();
    String str_phone = Phone.getText().toString();
    String str_location = Location.getText().toString();
    String str_status = Status.getText().toString();


//    String type = "Registration";
//    BackGroundWorker backGroundWorker = new BackGroundWorker(this);
//    backGroundWorker.execute(type, str_firstname, str_lastname, str_username, str_password, str_phone, str_location, str_status);
//    FirstName.setText("");
//    LastName.setText("");
//    UserName.setText("");
//    Password.setText("");
//    Cpassword.setText("");
//    Phone.setText("");
//    Location.setText("");
//    Status.setText("");

    User user = new User(str_firstname,str_lastname,str_username,str_phone,str_password,str_location,str_status);

    ApiOcpda clientRegistration = ServiceGenerator.createService(ApiOcpda.class);
    Call<LoginResponse> call = clientRegistration.register(
            user.getFname(),
            user.getLname(),
            user.getUsername(),
            user.getPhone(),
            user.getPassword(),
            user.getLocation(),
            user.getStatus()
            );


    call.enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            LoginResponse res = response.body();

            SharedPreferenceManager.getmInstance(Registration.this).userLogin(res.getRegistered_user());

            Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
            String status = SharedPreferenceManager.getmInstance(Registration.this).getStatus().toLowerCase();

            if(status.equals("buyer")) {
                startActivity(new Intent(Registration.this, UserViewActivity.class));
                finish();
            }else{
                startActivity(new Intent(Registration.this, Bprofile.class));
                finish();
            }


        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {

        }
    });
}
    }


    public boolean validate() {
        boolean valid = true;

        if (FirstName.getText().toString().isEmpty()) {
           FirstName.setError(" Enter your FirstName");
            valid = false;
        }
        if (LastName.getText().toString().isEmpty()) {
            LastName.setError("Enter your LastName");
            valid = false;
        }

        if (UserName.getText().toString().isEmpty()) {
            UserName.setError("Enter your UserName");
            valid = false;
        }
        if (Password.getText().toString().isEmpty()) {
           Password.setError("Enter your Password");
            valid = false;
        }
        if ((Cpassword.getText().toString().isEmpty())&&(!Password.getText().toString().equals(Cpassword.getText().toString())))
        {
            Cpassword.setError("password don't match");
            valid=false;
        }
        if(Location.getText().toString().isEmpty())
        {
            Location.setError("Please enter Valid Email Address");
            valid=false;
        }
        if(Phone.getText().toString().isEmpty())
        {
            Phone.setError("Enter the valid Phone Number");
            valid=false;
        }
        if(Status.getText().toString().isEmpty())
        {
         Status.setError("Enter your location");
            valid=false;
        }


        return valid;
    }

    }


