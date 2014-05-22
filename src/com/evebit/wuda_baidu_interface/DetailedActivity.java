package com.evebit.wuda_baidu_interface;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedActivity extends Activity {

	private TextView textView1,textView2;
	private Button answerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed);
		Intent intent = getIntent();
		String Title = intent.getStringExtra("title");
		String Content = intent.getStringExtra("content");
		final String Questionid = intent.getStringExtra("questionid"); 
		final String User_id = intent.getStringExtra("user_id");
		textView1 = (TextView)findViewById(R.id.Detailed_Text_Title);
		textView1.setText(Title.toString());
		textView2 = (TextView)findViewById(R.id.Detailed_Text_Answer);
		textView2.setMovementMethod(ScrollingMovementMethod.getInstance());//…Ë÷√ƒ⁄»›πˆ∂Ø
		answerButton = (Button)findViewById(R.id.bt_answer);
		textView2.setText(Content.toString());
		Log.v("Questionid", Questionid);
		Log.v("User_id", User_id);
		answerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailedActivity.this,AnswerActivity.class);
				intent.putExtra("questionid", Questionid);
				intent.putExtra("user_id", User_id);
				startActivity(intent);
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
				
				break;

			default:
				break;
			}
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
