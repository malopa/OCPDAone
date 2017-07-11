package com.example.sasiboy.ocpda.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasiboy.ocpda.R;
import com.example.sasiboy.ocpda.adapter.DataInCart;
import com.example.sasiboy.ocpda.model.DataFromCart;

public class DataInCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private Menu _menu;
    private  int _totaPrice;
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_in_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        total = (TextView)findViewById(R.id.total);

        recyclerView.setAdapter(new DataInCart(this));


        _totaPrice = 0;
        int size = DataFromCart.getmIstance(this).getPrice().size();
        for (int i=0;
                i<size; ++i){
            _totaPrice = _totaPrice +
                    DataFromCart.getmIstance(this).getPrice().get(i);
        }

    total.setText("Total Price = " + _totaPrice);
//        setTotal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu= menu;
        getMenuInflater().inflate(R.menu.total_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }




    public  void setTotal(){
        MenuItem item = _menu.findItem(R.id.total);
        item.setTitle("Total = "+ _totaPrice);
    }



}
