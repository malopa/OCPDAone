package com.example.sasiboy.ocpda.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sasiboy.ocpda.R;

/**
 * Created by sasiboy on 5/8/2017.
 */
    public class CustomAdapter extends PagerAdapter{

        private int[] imgs={R.drawable.pad3,R.drawable.pad1,R.drawable.pad2, R.drawable.pad4,R.drawable.pad5,R.drawable.pad6 };

        private LayoutInflater inflater;
        private Context ctx;

        public CustomAdapter(Context ctx){
            this.ctx=ctx;
        }
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==(LinearLayout)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.swipe, container,false );
            ImageView img=(ImageView)v.findViewById( R.id.imageView);
            TextView tv=(TextView)v.findViewById(R.id.textView);
            img.setImageResource(imgs[position]);
            tv.setText("Image:" +position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.invalidate();
        }
    }


