package com.lamal.youtubelibsissues;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<VideoItem>{

	private Activity contxt;
	private int res;
	private List<VideoItem> mylist;
	public MyListAdapter(Activity context, int resource, List<VideoItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		contxt = context;
		res = resource;
		mylist = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 if(convertView == null){
                convertView = contxt.getLayoutInflater().inflate(res, parent, false);
            }
            ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
            TextView title = (TextView)convertView.findViewById(R.id.video_title);
            TextView description = (TextView)convertView.findViewById(R.id.video_description);
             
            VideoItem searchResult = mylist.get(position);
             
            Picasso.with(contxt).load(searchResult.getThumbnailURL()).into(thumbnail);
            title.setText(searchResult.getTitle());
            description.setText(searchResult.getDescription());
            return convertView;
	}
	


}
