package com.example.sasiboy.ocpda;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.sasiboy.ocpda.adapter.CustomAdapter;

public class HomeActivity extends AppCompatActivity {
    Button btnSignIn;
    TextView tvNewUser;

    CustomAdapter adapter;
    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;


    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        adapter=new CustomAdapter(this);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        tvNewUser=(TextView)findViewById(R.id.tvNewUser);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);

        viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);
        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
//sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
    }
    public void onClickSignIn(View view)
    {
     startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }
  public void onClickSignUp(View view)
  {
      startActivity(new Intent(HomeActivity.this, Registration.class));
  }
}
