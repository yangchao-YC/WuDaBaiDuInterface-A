package com.evebit.wuda_baidu_interface;



import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class AnsweredActivity extends Activity {

	private TextView textView;
	private Button button;
	private String User_id;
	private String answerString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		textView = (TextView)findViewById(R.id.answer_content);
		textView.setKeyListener(null);
		button = (Button)findViewById(R.id.bt_submit);
		button.setText(R.string.back);
		Intent intent = getIntent();
		final String Questionid = intent.getStringExtra("questionid"); 
		 User_id = intent.getStringExtra("user_id");
		 answerString = intent.getStringExtra("answer");
		 textView.setText(answerString);
		 
		Log.v("Questionid", Questionid);
		Log.v("User_id", User_id);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent ;
				intent = new Intent(AnsweredActivity.this,ListActivity.class);
				intent.putExtra("user_id", User_id);
				startActivity(intent);
			
				
			}
		});
	}

	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
