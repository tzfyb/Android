package com.example.biosearchengineclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ResultPage extends ActionBarActivity implements
		AsyncTaskCompleteListener<JSONObject> {
	ListView list;

	String inputKeyWord = null;
	String datasource = null;
	
	ArrayList<String> pubmedTitles = new ArrayList<String>();
	ArrayList<String> pubmedAbstract = new ArrayList<String>();
	ArrayList<String> ieeeTitles = new ArrayList<String>();
	ArrayList<String> ieeeAbstract = new ArrayList<String>();
	ArrayList<String> newsTitles = new ArrayList<String>();
	ArrayList<String> newsAbstract = new ArrayList<String>();
	ArrayList<String> googlescholarTitles = new ArrayList<String>();
	ArrayList<String> googlescholarAbstract = new ArrayList<String>();

	JSONArray pubmedResultArray = null;
	JSONArray ieeeResultArray = null;
	JSONArray newsResultArray = null;
	JSONArray googlescholarResultArray = null;
	
	ArrayList<JSONObject> resultList = new ArrayList();
	
	int currentPage = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_page);
		if(!getIntent().hasExtra("keyWord")){
			JSONObject finalResult = ((MyApplication) this.getApplication()).getFinalResult();
			onTaskComplete(finalResult);
		}
		else{
			inputKeyWord = getIntent().getStringExtra("keyWord");
			datasource = getIntent().getStringExtra("datasource");
			((MyApplication) this.getApplication()).setFinalResult(null);
			((MyApplication) this.getApplication()).setCurrentPage(-1);
			

			// Using HttpJson class
			String uri = "http://52.10.26.4/muresearch2/json_manager.php";
			JSONObject requestJson = new JSONObject();
			try {
				requestJson.put("command", "search");
				requestJson.put("key", inputKeyWord);
				requestJson.put("datasource", datasource);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpJson searchJson = new HttpJson(uri, requestJson, ResultPage.this,this);

			searchJson.jsonRequest();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_page, menu);
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

	private String abstractDealer(String _abstract){
		String newAbstract = null;
		if(_abstract.length() > 300){
			newAbstract = _abstract.substring(0, 297);
			newAbstract += "...";
			return newAbstract;
		}
		
		return _abstract;
	}
	
	
	@Override
	public void onTaskComplete(JSONObject result) {
		// TODO Auto-generated method stub
		((MyApplication) this.getApplication()).setFinalResult(result);
		currentPage = ((MyApplication) this.getApplication()).getCurrentPage();
		if( currentPage == -1){
			currentPage = 0;
			((MyApplication) this.getApplication()).setCurrentPage(0);
		}
		if (result != null) {
			try {
				pubmedResultArray = result.getJSONArray("pubmed");
				ieeeResultArray = result.getJSONArray("ieee");
				newsResultArray = result.getJSONArray("news");
				googlescholarResultArray = result.getJSONArray("googlescholar");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (pubmedResultArray != null) {
				for (int i = currentPage*10; i < currentPage*10 + 10 && i < pubmedResultArray.length(); i++) {
					JSONObject singleItem;
					try {
						singleItem = pubmedResultArray.getJSONObject(i);
						pubmedTitles.add(singleItem.getString("title"));
						pubmedAbstract.add(abstractDealer(singleItem.getString("abstract")));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				list = (ListView) findViewById(R.id.listView);
				ResultAdapter adapter = new ResultAdapter(this, pubmedTitles, pubmedAbstract);
				list.setAdapter(adapter);
			}
		}

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Intent intent = new Intent(getBaseContext(), InfoActivity.class);
				String jsonStr = null;;
				try {
					jsonStr = pubmedResultArray.get(position).toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				intent.putExtra("json", jsonStr);
				startActivity(intent);
			}
		});
	}

}

class ResultAdapter extends ArrayAdapter<String> {
	Context context;
	ArrayList<String> titleArray;
	ArrayList<String> descriptionArray;

	public ResultAdapter(Context context, ArrayList<String> titles, ArrayList<String> descs) {
		super(context, R.layout.single_row, R.id.resultTitle, titles);
		this.context = context;
		this.titleArray = titles;
		this.descriptionArray = descs;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.single_row, parent, false);
		TextView resultTitle = (TextView) row.findViewById(R.id.resultTitle);
		TextView resultDes = (TextView) row
				.findViewById(R.id.resultDescription);

		resultTitle.setText(titleArray.get(position));
		resultDes.setText(descriptionArray.get(position));
		return row;
	}
}
