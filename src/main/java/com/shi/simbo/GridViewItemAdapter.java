package com.shi.simbo;

import android.content.Context;
import android.os.Message;
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
    private Message message;

    public GridViewItemAdapter(Context context,Message msg) {
        this.context =context;
        this.layoutInflater = LayoutInflater.from(context);
        this.message = msg;
        this.items = (List<SeriesItem>) msg.obj;
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_item,parent,false);
            holder = new ViewHolder();
            holder.gridImageView=convertView.findViewById(R.id.grid_image_view_id);
            holder.gridTextView=convertView.findViewById(R.id.grid_text_view_id);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SeriesItem item = items.get(position);
        holder.gridTextView.setText(item.getTitle());
        Glide.with(context).load(item.getImgSrc()).into(holder.gridImageView);
        return convertView;

    }

    static class ViewHolder{
        public ImageView gridImageView;
        public TextView gridTextView;
    }
}
