package com.example.sasiboy.ocpda.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sasiboy.ocpda.R;
import com.example.sasiboy.ocpda.activity.DataInCartActivity;
import com.example.sasiboy.ocpda.model.DataFromCart;


public class DataInCart extends RecyclerView.Adapter<DataInCart.MyViewHolder> {

    private Context _context;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price;
        Button remove;
        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            price=(TextView)itemView.findViewById(R.id.price);
            remove=(Button)itemView.findViewById(R.id.remove);
        }
    }


    public DataInCart(DataInCartActivity dataInCartActivity) {
        _context = dataInCartActivity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_data,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(_context).load(DataFromCart.getmIstance(_context).getImage_url().get(position)).into(holder.image);
        holder.price.setText(""+DataFromCart.getmIstance(_context).getPrice().get(position)+" /=");
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataFromCart.getmIstance(_context).getPrice().remove(getItemId(position));
                DataFromCart.getmIstance(_context).getImage_url().remove(getItemId(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataFromCart.getmIstance(_context).getPrice().size();
    }


}
