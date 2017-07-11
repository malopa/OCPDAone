package com.example.sasiboy.ocpda.activity;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sasiboy.ocpda.R;
import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.ProductList;
import com.example.sasiboy.ocpda.model.SalerProduct;
import com.example.sasiboy.ocpda.model.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product2);


        productList=(ListView)findViewById(R.id.product_list);


        showProduct();
    }




    private void showProduct() {


        int id = SharedPreferenceManager.getmInstance(ProductActivity.this).getUserId();

            ApiOcpda product = ServiceGenerator.createService(ApiOcpda.class);
            Call<SalerProduct> call = product.getProduct(id);

            call.enqueue(new Callback<SalerProduct>() {
                @Override
                public void onResponse(Call<SalerProduct> call, Response<SalerProduct> response) {

                    List<ProductList> products = response.body().getData();

                    productList.setAdapter(new MyProduct(products));
                    Toast.makeText(ProductActivity.this, "Here  ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SalerProduct> call, Throwable throwable) {

                }
            });

    }


    private class MyProduct implements ListAdapter {

        List<ProductList> list = new ArrayList<>();
        public MyProduct( List<ProductList> products) {

            list = products;

        }



        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int position) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LinearLayout linear = new LinearLayout(ProductActivity.this);

            linear.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout inerLinear = new LinearLayout(ProductActivity.this);
            inerLinear.setOrientation(LinearLayout.VERTICAL);

            ImageView image = new ImageView(ProductActivity.this);
            TextView name = new TextView(ProductActivity.this);
            TextView price = new TextView(ProductActivity.this);
            TextView description = new TextView(ProductActivity.this);


            image.setPadding(10,10,40,10);

            name.setPadding(10,0,0,10);
            price.setPadding(10,0,0,10);
            description.setPadding(10,0,0,10);

            Glide.with(ProductActivity.this).load(list.get(position).getImage()).fitCenter().diskCacheStrategy(DiskCacheStrategy.RESULT).into(image);
            name.setText(list.get(position).getName());
            price.setText(""+list.get(position).getPrice()+"/=");
            description.setText(list.get(position).getDescription());

            linear.addView(image);
            inerLinear.addView(name);
            inerLinear.addView(price);
            inerLinear.addView(description);

            linear.addView(inerLinear);
            Toast.makeText(ProductActivity.this, "Here  ", Toast.LENGTH_SHORT).show();
            return linear;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return getCount() == 0;
        }
    }
}
