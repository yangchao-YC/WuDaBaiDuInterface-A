package com.evebit.wuda_baidu_interface;

import java.util.ArrayList;
import java.util.Hashtable;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String url;
	private EditText nameEditText,pwdEditText;
	private Button loginButton;
	private String name;
	private String pwd;
	public static String user_id;
	private long exitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		nameEditText = (EditText)findViewById(R.id.Main_edit_name);
		pwdEditText = (EditText)findViewById(R.id.Main_edit_pwd);
		loginButton = (Button)findViewById(R.id.Main_button_login);
		
		
		
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = nameEditText.getText().toString();
				pwd = pwdEditText.getText().toString();
				if(name.equals("") || pwd.equals(""))
				handler.sendEmptyMessage(1);
				else {
					url = "http://121.199.29.181/demo/wush/whss/index/adminLogin?username="+name +"&password=" +pwd;
					
					DoingThread();
				}
			}
		});
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
		Intent home = new Intent(Intent.ACTION_MAIN);   
		home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
		home.addCategory(Intent.CATEGORY_HOME);   
		startActivity(home); 
		}
		
		return true;
	}

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			Intent intent;
			switch (msg.what) {
			case 0:
				intent = new Intent(MainActivity.this,ListActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				break;
			case 1:
				Toast.makeText(MainActivity.this, "账号或密码为空", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
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
				Test_Bean_TianQi data;
	
					try {
					data = DataManeger.getTestData_TianQi(url);
					Test_Model datalist = data.getData();
						Log.v("我是第一种数据的ID", datalist.getUser_id());
						user_id = datalist.getUser_id();
						if(user_id.equals(""))
							handler.sendEmptyMessage(2);
						else
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
