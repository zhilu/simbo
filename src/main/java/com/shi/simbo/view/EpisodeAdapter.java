package com.shi.simbo.view;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shi.simbo.R;
import com.shi.simbo.entity.Episode;
import com.shi.simbo.entity.SeriesDetail;

import java.util.List;

public class EpisodeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Episode> episodes;

    public EpisodeAdapter(Context context, Message msg) {
        this.context =context;
        this.layoutInflater = LayoutInflater.from(context);
        SeriesDetail detail = (SeriesDetail) msg.obj;
        this.episodes = detail.getEpisodes();
    }



    @Override
    public int getCount() {
        return episodes.size();
    }

    @Override
    public Object getItem(int position) {
        return episodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_episode,parent,false);
            holder = new ViewHolder();
            holder.gridTextView=convertView.findViewById(R.id.tv_episode_id);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Episode episode = episodes.get(position);
        holder.gridTextView.setText(episode.getName());
        return convertView;

    }

    static class ViewHolder{
        public TextView gridTextView;
    }
}
