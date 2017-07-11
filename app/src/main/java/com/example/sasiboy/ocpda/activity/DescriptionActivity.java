package com.example.sasiboy.ocpda.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sasiboy.ocpda.R;
import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.DataR;
import com.example.sasiboy.ocpda.model.ResultData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionActivity extends AppCompatActivity {

    private TextView item_description;
    private  int data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        item_description = (TextView)findViewById(R.id.item_description);

        data = getIntent().getIntExtra("item_id",0);
//        Toast.makeText(this," " + data,Toast.LENGTH_LONG).show();


        ApiOcpda client = ServiceGenerator.createService(ApiOcpda.class);

        Call<ResultData> call = client.retriveData();
        call.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                ArrayList<DataR> dataRs = (ArrayList<DataR>) response.body().getData();
                item_description.setText(dataRs.get(data).getDescription());
            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {

            }
        });


    }
}
