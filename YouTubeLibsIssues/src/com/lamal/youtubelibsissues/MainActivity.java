package com.lamal.youtubelibsissues;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.lamal.youtubelibsissues.R.id;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;


@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	ListView myResultView;
	EditText mySearchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       myResultView = (ListView) findViewById(id.listView_youTubeResults);
        mySearchText = (EditText) findViewById(id.editText_searchKeyWord);
        new NetworkClass().execute("luissuarez");
        mySearchText.addTextChangedListener(myTextWatcher);
   
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    class NetworkClass extends AsyncTask<String, Void, List<VideoItem>>{

		@Override
		protected List<VideoItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			 JSONObject jsonObject = null;
				
					try {
						String service = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
						String para = "&type=video&key=AIzaSyAu0TouHlBpBz1YQO6R1TR7rputzjSwu24";
						String serchkeys = params[0];
						String original =service+serchkeys+para;
						HttpGet httpGet = new HttpGet(original);
						HttpClient client = new DefaultHttpClient();
						HttpResponse response;
						StringBuilder stringBuilder = new StringBuilder();

   
						    response = client.execute(httpGet);
						    HttpEntity entity = response.getEntity();
						    InputStream stream = entity.getContent();
						    int b;
						    while ((b = stream.read()) != -1) {
						        stringBuilder.append((char) b);
						    }
						    jsonObject = new JSONObject(stringBuilder.toString());
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
					List<VideoItem>  list =	new DataRetiver().parseJason(jsonObject);
			return list;
		}

		@Override
		protected void onPostExecute(List<VideoItem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			MyListAdapter adapter = new MyListAdapter(MainActivity.this, R.layout.video_item, result);
			myResultView.setAdapter(adapter);
		}    	
    }
    
    private TextWatcher myTextWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.toString().length()>3) {
				new NetworkClass().execute(s.toString().trim().replaceAll(" ", ""));
			}

		}
    };
}
