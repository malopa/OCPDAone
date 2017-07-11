package com.example.sasiboy.ocpda.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sasiboy.ocpda.R;
import com.example.sasiboy.ocpda.activity.DescriptionActivity;
import com.example.sasiboy.ocpda.model.DataR;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder>{

    public interface ItemAddedInCart{
        public void itemadded(int position);
        public void itemPrice(int price);
        public void itemImage(String image_url);
    }

    ItemAddedInCart itemListener = null;

    List<DataR> data = new ArrayList<>();

    Context _context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageItem;
        TextView price;
        Button addTpCart;
        CardView card ;
        public MyViewHolder(View itemView) {
            super(itemView);
            card=(CardView)itemView.findViewById(R.id.item_card);
            name = (TextView)itemView.findViewById(R.id.item_title);
            imageItem = (ImageView) itemView.findViewById(R.id.item_image);
            price = (TextView)itemView.findViewById(R.id.price);
            addTpCart=(Button)itemView.findViewById(R.id.btnAddToCart);
        }

    }

    public interface OnitemClick{
        public void ItemClicked(int position);
    }

//    OnitemClick itemListener = null;

    public DataAdapter(Context context, List<DataR> result){
        data =  result;
        _context = context;

        this.itemListener = (ItemAddedInCart) _context;

    }





    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_data,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(_context).load(data.get(position).getImage()).centerCrop().into(holder.imageItem);
        holder.name.setText(data.get(position).getName());
        holder.price.setText(" "+ (int) data.get(position).getPrice());

        holder.imageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent descriptionIntent =  new Intent(_context,DescriptionActivity.class);
                descriptionIntent.putExtra("item_id",position);
                _context.startActivity(descriptionIntent);
            }
        });

        holder.addTpCart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                itemListener.itemadded(data.get(position).getId());
                holder.addTpCart.setText("Added");
                itemListener.itemPrice((int) data.get(position).getPrice());
                itemListener.itemImage(data.get(position).getImage());
                holder.addTpCart.setClickable(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
