package com.shi.simbo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shi.simbo.entity.SeriesItem;

import java.util.List;

public class GridViewItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<SeriesItem> items;

    public GridViewItemAdapter(Context context) {
        this.context =context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_item,parent,false);
            holder = new ViewHolder();
            holder.gridImageView=convertView.findViewById(R.id.grid_IV_Id);
            holder.gridTextView=convertView.findViewById(R.id.grid_TV_Id);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.gridTextView.setText("汽车");
        Glide.with(context).load("https://pic.2hanju.com/pic/2420.jpg").into(holder.gridImageView);
        return convertView;

    }

    static class ViewHolder{
        public ImageView gridImageView;
        public TextView gridTextView;
    }
}
