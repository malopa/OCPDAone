package com.example.sasiboy.ocpda;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sasiboy.ocpda.activity.ProductActivity;
import com.example.sasiboy.ocpda.config.ApiOcpda;
import com.example.sasiboy.ocpda.config.ServiceGenerator;
import com.example.sasiboy.ocpda.model.ResponseResult;
import com.example.sasiboy.ocpda.model.SharedPreferenceManager;
import com.ipaulpro.afilechooser.utils.FileUtils;

import org.apache.http.params.HttpParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Attributes;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bprofile extends AppCompatActivity {
    private ImageButton ImgBtnCamera, ImgBtnGallery, ImgBtnUpload;
    private EditText etName, etPrice,_description;
    private ImageView ImgShow;
    private Uri filePath;
    private Bitmap bitmap;
    private String _imageUri;
    private Uri _imageFile;

    public static final String UPLOAD_URL = "http://192.168.101.1/image.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    private static final int PICK_IMAGE_REQUEST = 11;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bprofile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImgShow = (ImageView) findViewById(R.id.IvView);
        ImgBtnCamera = (ImageButton) findViewById(R.id.ImBtnCamera);
        ImgBtnGallery = (ImageButton) findViewById(R.id.ImgBtnGallery);
        ImgBtnUpload = (ImageButton) findViewById(R.id.ImgBtnUpload);
        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        _description = (EditText)findViewById(R.id.description);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.refresh:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    return true;
                }
                break;

            case R.id.orders:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    startActivity(new Intent(this, Orders.class));
                    return true;
                }

            case R.id.products:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    startActivity(new Intent(Bprofile.this, ProductActivity.class));
                    return true;
                }

            case R.id.logout1:
                SharedPreferenceManager.getmInstance(getApplicationContext()).logout();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            default:
        }
        return super.onOptionsItemSelected(item);

    }

    private void showCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



   public void onClickCamera(View view) {
        showCamera();
    }

    public void onClickGallery(View view) {
        new Thread()
        {
            @Override
            public void run() {
                super.run();
                Intent uploadIntent  = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                uploadIntent.setType("image/*");
                uploadIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(uploadIntent,PICK_IMAGE_REQUEST);
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null)
        {
            Uri imageFile = data.getData();
            Glide.with(Bprofile.this).load(imageFile).override(700,400).centerCrop().into(ImgShow);
            String[] fileColumns = {MediaStore.Images.Media.DATA};

            _imageFile = imageFile;
            Cursor cursor = getApplicationContext().getContentResolver().query(imageFile,fileColumns,null,null,null);

            if(cursor!= null){
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(fileColumns[0]);
                _imageUri = cursor.getString(columnIndex);



            }
        }
    }

    public void onClickUpload(View view){
        uploadData();
    }

    public void uploadData(){
        String picName = etName.getText().toString();
        String price = etPrice.getText().toString();
        String description = _description.getText().toString();

        String _id = ""+SharedPreferenceManager.getmInstance(Bprofile.this).getUserId();

        RequestBody picNameFile = RequestBody.create(MultipartBody.FORM,picName);
        RequestBody priceFile = RequestBody.create(MultipartBody.FORM,price);
        RequestBody descriptionFile = RequestBody.create(MultipartBody.FORM,description);
        RequestBody ids = RequestBody.create(MultipartBody.FORM, _id);


        File originalFile = FileUtils.getFile(Bprofile.this,_imageFile);

        RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(_imageFile)),originalFile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo",originalFile.getName(),filePart);


        ApiOcpda client = ServiceGenerator.createService(ApiOcpda.class);
        Call<ResponseResult> call = client.uploadProfile(picNameFile,priceFile,descriptionFile,ids,file);

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}






//private class Upload extends AsyncTask<Void, Void, Void>
//{
//    Bitmap image;
//    String Name;
//    int Price;
//
//    public Upload(Bitmap image, String name, int price) {
//        this.image = image;
//        Name = name;
//        Price = price;
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        String encodeImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
//
//       List<Pair<String, String>> params = new ArrayList<>();
//        params.add(new Pair<>("image", encodedImage));
//        params.add(new Pair<>("Name", Name));
//
//
//
////        ArrayList<NameValuePair> dataTosand=new ArrayList<>();
////        dataTosand.add(new BasicNameValuePair("image", encodeImage));
////        dataTosand.add(new BasicNameValuePair("Name", Name));
////        dataTosand.add(new BasicNameValuePair("Price", Price));
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
//        super.onPostExecute(aVoid);
//    }
//}

