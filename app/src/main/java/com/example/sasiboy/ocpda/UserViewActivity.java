package com.example.sasiboy.ocpda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sasiboy.ocpda.activity.DataInCartActivity;
import com.example.sasiboy.ocpda.activity.DescriptionActivity;
import com.example.sasiboy.ocpda.adapter.DataAdapter;
import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.CartData;
import com.example.sasiboy.ocpda.model.DataFromCart;
import com.example.sasiboy.ocpda.model.DataR;
import com.example.sasiboy.ocpda.model.ResponseCart;
import com.example.sasiboy.ocpda.model.ResultData;
import com.example.sasiboy.ocpda.model.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewActivity extends AppCompatActivity implements DataAdapter.OnitemClick,DataAdapter.ItemAddedInCart{
    private ViewStub stubGrid;
    private ViewStub stubList;
    private RecyclerView listView;
    private GridView gridView;
//    private ListViewAdapter listViewAdapter;
//    private GridViewAdapter gridViewAdapter;
//    private List<Product> productList;
    private int currentViewMode=0;
    private ImageView _image;
    private int id = 0;
    Menu _menu;
    List<Integer> dataSize;
    List<Integer> dataPrice;
    List<String> dataImage;

    private List<ResultData> _retrievedData = new ArrayList<>();

    static final int VIEW_MODE_LISTVIEW=0;
    static final int VIEW_MODE_GRIDVIEW=1;


    private  DataAdapter myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (RecyclerView) findViewById(R.id.dataList);
        if (!SharedPreferenceManager.getmInstance(this).isLoggedIn())
                startActivity(new Intent(this,MainActivity.class));
        listView.setAdapter(myListAdapter);

        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linear);
        listView.setHasFixedSize(true);

        dataSize=new ArrayList<>();
        dataPrice=new ArrayList<>();
        dataImage=new ArrayList<>();


        ApiOcpda client = ServiceGenerator.createService(ApiOcpda.class);
        Call<ResultData> call = client.retriveData();
        call.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                List<DataR> datazote =  response.body().getData();

                listView.setAdapter(new DataAdapter(UserViewActivity.this,datazote));
            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



        gridView = (GridView) findViewById(R.id.mygridview);
//        getProductList();

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//default is viewlist
    }



    private void switchView()
    {
        if(VIEW_MODE_LISTVIEW==currentViewMode)
        {
        }
        else{
            //display stubgrid
            stubGrid.setVisibility(View.VISIBLE);
            //hide stublist
            stubList.setVisibility(View.GONE);
        }
        setAdapters();
    }
    private void setAdapters()
    {
        if(VIEW_MODE_LISTVIEW==currentViewMode)
        {

        }
        else{
//            gridViewAdapter=new GridViewAdapter(this,R.layout.grid_item, productList);
//            gridView.setAdapter(gridViewAdapter);
        }
    }
//    public List <Product> getProductList()
//    {
//        productList=new ArrayList<>();
//        final Button btn=new Button(this);
//        btn.setText("Add To Cart");
//
//        return productList;
//    }
//    AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getApplicationContext(),productList.get(position).getTitle()+"-" +productList.get(position).getDescription(),Toast.LENGTH_SHORT).show();
//        }
//
//    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        _menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.SwitchView:
                if(VIEW_MODE_LISTVIEW==currentViewMode)
                {
                    currentViewMode=VIEW_MODE_GRIDVIEW;
                }
                else{
                    currentViewMode=VIEW_MODE_LISTVIEW;
                }

                switchView();
                SharedPreferences sharedPreferences=getSharedPreferences("ViewMode",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("currentViewMode",currentViewMode);
                editor.commit();
                break;
            case R.id.cart:
                    populateData();
                break;


            case R.id.logout1:
                SharedPreferenceManager.getmInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void ItemClicked(int position) {
        Intent intentDescription = new Intent(UserViewActivity.this,DescriptionActivity.class);
         intentDescription.putExtra("description_id",position);
        this.startActivity(intentDescription);
    }


    @Override
    public void itemadded(int position) {
        id = position;
            dataSize.add(id);
            int size = dataSize.size();
            MenuItem item = _menu.findItem(R.id.add);
            item.setTitle("" +size );

    }

    @Override
    public void itemPrice(int price) {
        dataPrice.add(price);
        DataFromCart.getmIstance(UserViewActivity.this).setPrice(dataPrice);
    }

    @Override
    public void itemImage(String image_url) {
        dataImage.add(image_url);
        DataFromCart.getmIstance(UserViewActivity.this).setImage_url(dataImage);
    }

    public void populateData() {

        startActivity(new Intent(UserViewActivity.this, DataInCartActivity.class));
////        Toast.makeText(UserViewActivity.this, ""+dataSize.size(), Toast.LENGTH_SHORT).show();
//
//        ApiOcpda clietCart = ServiceGenerator.createService(ApiOcpda.class);
//        Call<ResponseCart> call = clietCart.cartData(dataSize);
//
//        call.enqueue(new Callback<ResponseCart>() {
//            @Override
//            public void onResponse(Call<ResponseCart> call, Response<ResponseCart> response) {
//                ResponseCart data = response.body();
//
//                Toast.makeText(UserViewActivity.this, ""+data.getData_in_cart().size(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseCart> call, Throwable t) {
//
//            }
//        });
    }


}

