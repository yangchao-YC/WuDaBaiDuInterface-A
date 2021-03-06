package com.evebit.wuda_baidu_interface;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import com.evebit.json.Y_Exception;
import com.evebit.json.HttpUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerActivity extends Activity {

	private TextView textView;
	private Button button;
	private String answerStr;
	private String User_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		textView = (TextView)findViewById(R.id.answer_content);
		button = (Button)findViewById(R.id.bt_submit);
		button.setText(R.string.submit);
		Intent intent = getIntent();
		final String Questionid = intent.getStringExtra("questionid"); 
		 User_id = intent.getStringExtra("user_id");
		Log.v("Questionid", Questionid);
		Log.v("User_id", User_id);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String answerString = textView.getText().toString();
				if(answerString.equals(""))
					handler.sendEmptyMessage(1);
				else DoingThread(Questionid,User_id,answerString);
			
				
			}
		});
	}

	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what) {
			case 0:
				recall();
				break;
			case 1:
				Toast.makeText(AnswerActivity.this, "请填写答案后再提交", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

		private void recall() {
			Intent intent ;
			intent = new Intent(AnswerActivity.this,ListActivity.class);
			intent.putExtra("user_id", User_id);
			startActivity(intent);
			
		}
		
	};
	
	
	private void DoingThread(final String Questionid, final String User_id,final String answerString)
	{
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
			List<NameValuePair> list = new ArrayList<NameValuePair>();
	          list.add(new BasicNameValuePair("questionid", Questionid));
	          list.add(new BasicNameValuePair("answer", answerString));
	          list.add(new BasicNameValuePair("doctorid", User_id));
	try { 
		answerStr = HttpUtil.httpPost(null, "http://121.199.29.181/demo/wush/whss/index/postAnswerFromMoblie", list);
		Log.v("接收到的值", answerStr);
		handler.sendEmptyMessage(0);
	} catch (Y_Exception e) {
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
