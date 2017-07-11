package com.example.sasiboy.ocpda;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Blob;

/**
 * Created by sasiboy on 5/7/2017.
 */

public class BackGroundWorker extends AsyncTask<String, Void, String> {


    Context context;
    AlertDialog alertDialog;

    BackGroundWorker (Context ctx)
    {
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url= " http://192.168.137.1/ocpda/public/login.php";
        String register_url= " http://192.168.137.1/ocpda/public/Registration.php";
        if(type.equals("login"))
        {
            try {
                URL url=new URL(login_url);

                    String user_name=params[1];
                    String password=params[2];
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(    outputStream, "UTF-8"));
                    String post_data= URLEncoder.encode("user_name", "UTF-8") +"="+URLEncoder.encode(user_name, "UTF-8")+"&"
                            + URLEncoder.encode("password", "UTF-8") +"="+URLEncoder.encode(password, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result="";
                    String line;
                    while((line=bufferedReader.readLine())!=null)
                    {
                   result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;


                }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("Registration"))
        {
            try {
                String firstname=params[1];
                String lastname=params[2];
                String username=params[3];
                String password=params[4];
                String phone=params[5];
                String location=params[6];
                String status=params[7];

                    URL url=new URL(register_url);


                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(    outputStream, "UTF-8"));
                    String post_data= URLEncoder.encode("firstname", "UTF-8")+"="+URLEncoder.encode(firstname, "UTF-8")+"&"
                            + URLEncoder.encode("lastname", "UTF-8")+"="+URLEncoder.encode(lastname, "UTF-8")+"&"
                            + URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                            +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode( password, "UTF-8")+"&"
                            +URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode( phone, "UTF-8")+"&"
                            +URLEncoder.encode("location", "UTF-8")+"="+URLEncoder.encode( location, "UTF-8")+"&"
                            +URLEncoder.encode("status", "UTF-8")+"="+URLEncoder.encode( status, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result="";
                    String line;
                    while((line=bufferedReader.readLine())!=null)
                    {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;


                }catch (MalformedURLException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPreExecute() {

       alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");

    }

    @Override
    protected void onPostExecute(String result) {
     alertDialog.setMessage(result);
        alertDialog.show();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
