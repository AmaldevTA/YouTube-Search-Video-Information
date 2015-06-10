package com.lamal.youtubelibsissues;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataRetiver {
	
	public List<VideoItem> parseJason(JSONObject initialObject){
		List<VideoItem> myReturnValue = null;
		
		try {
			JSONArray items = initialObject.getJSONArray("items");
			myReturnValue =  new ArrayList<VideoItem>();
			for (int i = 0; i < items.length(); i++) {
				JSONObject units = items.getJSONObject(i);
				JSONObject snippet = units.getJSONObject("snippet");
				String id = units.getJSONObject("id").getString("videoId");
				String title = snippet.getString("title");
				String description = snippet.getString("description");
				String thumbnail = snippet.getJSONObject("thumbnails").getJSONObject("default").getString("url");
				VideoItem vi = new VideoItem();
				vi.setId(id);
				vi.setTitle(title);
				vi.setDescription(description);
				vi.setThumbnailURL(thumbnail);
				myReturnValue.add(vi);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return myReturnValue;
	}

}
