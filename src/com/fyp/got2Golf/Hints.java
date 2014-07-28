package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Hints extends Activity implements View.OnClickListener{
	int hintNum =1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Create instances of buttons and link with XML file
		setContentView(R.layout.hints);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//create reference to buttons and assign onclick listener
		Button nextButton = (Button)findViewById(R.id.buttonNextHint);
		nextButton.setOnClickListener(this);
		Button backButton = (Button)findViewById(R.id.buttonBackHint);
		backButton.setOnClickListener(this);
		
		//Call to display the firs hint on the page loading
		displayHint(hintNum);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}
	//Menu bar onclick listeners
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.mainMenuButton:
			Intent mainMenuIntent = new Intent(this, MainMenu.class);
			startActivity(mainMenuIntent);
			return true;
		case R.id.hintsPageButton:
			Intent hintsIntent = new Intent(this, Hints.class);
			startActivity(hintsIntent);
			return true;
		case R.id.tipsPageButton:
			Intent tipsIntent = new Intent(this, Tips.class);
			startActivity(tipsIntent);
			return true;
		case R.id.settingsPageButton:
			Intent settingIntent = new Intent(this, Settings.class);
			startActivity(settingIntent);
			return true;
		case R.id.exitButton:
			//logOff();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	//onclick listeners for next and back buttons
	@Override
	public void onClick(View v) {
		int hintPos =getHintNum();
		switch(v.getId()){
		case R.id.buttonBackHint:
		{
			//if current hint is 1. sent user forward to hint 10 an ensure data stays within expected ranges
			if (hintPos==1)
			{
				int hint = 10;
				setHintNum(hint);
				displayHint(hint);
			}
			else 
			{
				//for hint between numbers 2 and 10
				int hint = hintPos-1;
				setHintNum(hint);
				displayHint(hint);
			}
			
		}
		break;
		case R.id.buttonNextHint:
		{
			////if current hint is 10. sent user back to hint 1 an ensure data stays within expected ranges
			if (hintPos==10)
			{
				int hint = 1;
				setHintNum(hint);
				displayHint(hint);
			}
			else 
			{
				int hint = hintPos+1;
				setHintNum(hint);
				displayHint(hint);
			}
		}
		break;
		}
	}
	private void setHintNum(int hint) {
		//used to set new value during onclick event
		hintNum = hint;		
	}
	private void displayHint(int hint) {
		TextView txtHintNum = (TextView)findViewById(R.id.textViewHintsNum);
		TextView txtHintHead = (TextView)findViewById(R.id.textViewHintHead);
		TextView txtHintBody = (TextView)findViewById(R.id.textViewHintBody);

		//Check for hint number and display the relevant header and body text for the tip in the textviewd
		//Depending on the vlue of the hint a different message will be displayed.
		if (hint == 1)
		{
			txtHintHead.setText(getString(R.string.hints_1));
			txtHintBody.setText(getString(R.string.hints_tip1));
		}
		else if (hint ==2)
		{
			txtHintHead.setText(getString(R.string.hints_2));
			txtHintBody.setText(getString(R.string.hints_tip2));
		}
		else if (hint ==3)
		{
			txtHintHead.setText(getString(R.string.hints_3));
			txtHintBody.setText(getString(R.string.hints_tip3));
		}
		else if (hint ==4)
		{
			txtHintHead.setText(getString(R.string.hints_4));
			txtHintBody.setText(getString(R.string.hints_tip4));
		}
		else if (hint ==5)
		{
			txtHintHead.setText(getString(R.string.hints_5));
			txtHintBody.setText(getString(R.string.hints_tip5));
		}
		else if (hint ==6)
		{
			txtHintHead.setText(getString(R.string.hints_6));
			txtHintBody.setText(getString(R.string.hints_tip6));
		}
		else if (hint ==7)
		{
			txtHintHead.setText(getString(R.string.hints_7));
			txtHintBody.setText(getString(R.string.hints_tip7));
		}
		else if (hint ==8)
		{
			txtHintHead.setText(getString(R.string.hints_8));
			txtHintBody.setText(getString(R.string.hints_tip8));
		}
		else if (hint ==9)
		{
			txtHintHead.setText(getString(R.string.hints_9));
			txtHintBody.setText(getString(R.string.hints_tip9));
		}
		else if (hint ==10)
		{
			txtHintHead.setText(getString(R.string.hints_10));
			txtHintBody.setText(getString(R.string.hints_tip10));
		}
		else{}
		String num = String.valueOf(hint);
		txtHintNum.setText(num);
	}
	public int getHintNum()
	{
		return hintNum;
	}
}