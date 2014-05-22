package com.evebit.wuda_baidu_interface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Bean_TianQi;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListActivity extends Activity {

	
	private String url="http://121.199.29.181/demo/wush/whss/index/getQuestionList";
	private ListView listView;
	private SimpleAdapter mAdapter; 
	private String test = "ListActivity";
	private String[] Content= new String[9999];
	private String[] Title = new String[9999];
	private String[] Questionid = new String[9999];
	private int temp = 0;
	private ArrayList<Hashtable<String, String>> list= new ArrayList<Hashtable<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		listView = (ListView)findViewById(R.id.List_view_list);
		Intent intent = getIntent();
		final String User_id = intent.getStringExtra("user_id");
		Log.v("医生ID", User_id);
		

		
	/*	for (int i = 0; i < 5; i++) {
			 HashMap<String, Object> map=new HashMap<String, Object>();
			 map.put("Title", "标题"+i);
		     map.put("Content", "内容"+i);
			
			 list.add(map);
		}*/
		
		DoingThread();
		
		
		
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v(test, " " + position);
				
				Intent intent;
				intent = new Intent(ListActivity.this,DetailedActivity.class);
				intent.putExtra("title", Title[position]);
				intent.putExtra("content", Content[position]);
				intent.putExtra("questionid", Questionid[position]);
				intent.putExtra("user_id", User_id);
				startActivity(intent);
			}
			
		});
		
		
		
		
		
		
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(ListActivity.this,
					MainActivity.class);

			
			startActivity(intent);
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);

	}


	private void listadapter(){
		mAdapter = new SimpleAdapter(ListActivity.this,
				list, R.layout.listview_cell, new String[] {"title"},
				new int[] { R.id.listview_Text_Title});
		listView.setAdapter(mAdapter);
		
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case 0:
				listadapter();
				break;

			default:
				break;
			}
			
			
		}
		
	};
	private void DoingThread()
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Test_Bean data;
	
					try {
					data = DataManeger.getTestData(url);
					ArrayList<Test_Model> datalist = data.getData();
					Log.v("------第一种------", datalist.toString());
					for (Test_Model test_Model : datalist) {					
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						hashtable.put("title", (test_Model.getTitle()==null? "": test_Model.getTitle()));
						hashtable.put("content", (test_Model.getContent()==null? "": test_Model.getContent()));
						Log.v("我是第一种数据的title", test_Model.getTitle());
						Title[temp] = test_Model.getTitle();
						Content[temp] = test_Model.getContent();
						Questionid[temp] = test_Model.getQuestionid();
						temp++;
						list.add(hashtable);
					}
					
					handler.sendEmptyMessage(0);
					}
										
						
					
					
				 catch (Y_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
