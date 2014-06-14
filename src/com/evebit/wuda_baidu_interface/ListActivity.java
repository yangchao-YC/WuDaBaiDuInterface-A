package com.evebit.wuda_baidu_interface;

import java.util.ArrayList;
import java.util.Hashtable;
import com.evebit.json.DataManeger;
import com.evebit.json.Test_Bean;
import com.evebit.json.Test_Model;
import com.evebit.json.Y_Exception;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

public class ListActivity extends TabActivity {

	
	private String url="http://121.199.29.181/demo/wush/whss/index/getQuestionList";
	private ListView listView;
	private ListView listView_old;
	private SimpleAdapter mAdapter; 
	private SimpleAdapter nAdapter;
	private String test = "ListActivity";
	private String[] Content= new String[9999];
	private String[] Title = new String[9999];
	private String[] Questionid = new String[9999];
	private String[] Answer = new String[9999];
	private String[] Content_old= new String[9999];
	private String[] Title_old = new String[9999];
	private String[] Questionid_old = new String[9999];
	private String[] Answer_old = new String[9999];
	private Integer[] Questionflag = new Integer[9999];
	private int temp,m,n = 0;
	private ProgressDialog progressDialog;
	private ArrayList<Hashtable<String, String>> list= new ArrayList<Hashtable<String,String>>();
	private ArrayList<Hashtable<String, String>> list_old= new ArrayList<Hashtable<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		TabHost tabHost = getTabHost();
		TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator("未回答").setContent(R.id.tab1);
		tabHost.addTab(tab1);
		TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator("已回答").setContent(R.id.tab2);
		tabHost.addTab(tab2);
		
		listView = (ListView)findViewById(R.id.List_view_list);
		listView_old = (ListView)findViewById(R.id.List_view_list_old);
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
		updateTab(tabHost);
		progressDialog = ProgressDialog.show(ListActivity.this, "", getResources().getString(R.string.loading), true, false);
		progressDialog.setCancelable(true);
		progressDialog.show();
		

		
		
		
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
				intent.putExtra("answer", Answer[position]);
				startActivity(intent);
			}
			
		});
		
		listView_old.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v(test, " " + position);
				
				Intent intent;
				intent = new Intent(ListActivity.this,AnsweredDetail.class);
				intent.putExtra("title", Title_old[position]);
				intent.putExtra("content", Content_old[position]);
				intent.putExtra("questionid", Questionid_old[position]);
				intent.putExtra("user_id", User_id);
				intent.putExtra("answer", Answer_old[position]);
				startActivity(intent);
			}
			
		});
		
		
		
		
		
		
	}
	
	private void updateTab(final TabHost tabHost){
		for(int i = 0 ;i < tabHost.getTabWidget().getChildCount(); i++){
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(16);
            
                tv.setTextColor(this.getResources().getColorStateList(  
                        android.R.color.white));  
            }
           
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
		nAdapter = new SimpleAdapter(ListActivity.this,
				list_old, R.layout.listview_cell, new String[] {"title"},
				new int[] { R.id.listview_Text_Title});
		listView_old.setAdapter(nAdapter);
		
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case 0:
				listadapter();
				progressDialog.dismiss();

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
			//			hashtable.put("content", (test_Model.getContent()==null? "": test_Model.getContent()));
						Log.v("我是第一种数据的title", test_Model.getTitle());
						
						Questionflag[temp] = Integer.valueOf(test_Model.getStatus());
						
					
						if(Questionflag[temp]==0)  { 
							Title[m] = test_Model.getTitle();
							Content[m] = test_Model.getContent();
							Questionid[m] = test_Model.getQuestionid();
							Answer[m] = test_Model.getAnswer();
							hashtable.put("title", (test_Model.getTitle()==null? "": test_Model.getTitle()));
							list.add(hashtable);
							m++;
						}
						else{
							Title_old[n] = test_Model.getTitle();
							Content_old[n] = test_Model.getContent();
							Questionid_old[n] = test_Model.getQuestionid();
							Answer_old[n] = test_Model.getAnswer();
							hashtable.put("title", (test_Model.getTitle()==null? "": test_Model.getTitle()));
							list_old.add(hashtable);
							n++;
						}
						
						temp++;
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
