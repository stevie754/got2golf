package com.fyp.got2Golf;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.content.Intent;
//import android.view.Menu;
//import android.content.SharedPreferences;
import android.widget.PopupWindow;

public class MainMenu extends Activity implements View.OnClickListener{
	//Links to CourseList, FavouriteCourses, PreviousRounds, Stats, **Guests**
	View layout;
	private PopupWindow pw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Create instances of buttons and link with XML file
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Button recentButton = (Button)findViewById(R.id.recentButton);
		recentButton.setOnClickListener(this);
		Button courseButton = (Button)findViewById(R.id.courseButton);
		courseButton.setOnClickListener(this);
		Button favouriteButton = (Button)findViewById(R.id.favouriteButton);
		favouriteButton.setOnClickListener(this);
		Button statsButton = (Button)findViewById(R.id.statsButton);
		statsButton.setOnClickListener(this);
		ImageButton playMainButton = (ImageButton)findViewById(R.id.golfImage);
		playMainButton.setOnClickListener(this);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoMain);
		infoBtn.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	//menu bar onclick listeners
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
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	//on click listeners for the buttons on the main page
	public void onClick(View v) {
		//Create a switch statement when a button is pressed the app will go to the relevant page
		switch(v.getId()){
		case R.id.recentButton:
		{
			Intent recentIntent = new Intent(this, RecentCourses.class);
			startActivity(recentIntent);
		}
		break;
		case R.id.courseButton:
		{
			Intent courseIntent = new Intent(this, Courses.class);
			startActivity(courseIntent);
		}
		break;
		case R.id.favouriteButton:
		{
			Intent favouriteIntent = new Intent(this, FavouriteCourses.class);
			startActivity(favouriteIntent);
		}
		break;
		case R.id.statsButton:
		{
			Intent statsIntent = new Intent(this, Stats.class);
			startActivity(statsIntent);
		}
		break;
		case R.id.golfImage:
		{
			Intent addIntent = new Intent(this, CoursesList.class);
			startActivity(addIntent);
		}
		break;
		case R.id.infoMain:
		{	
			initiateInfoWindow();
		}
		break;
		}
	}
	private void initiateInfoWindow() {
		try {
			//Create an instance of the LayoutInflater
			LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.info,  (ViewGroup) findViewById(R.id.popup_info));
			//Inflate the view from a predefined XML layout
			// create a 350px width and 500px height PopupWindow
			pw = new PopupWindow(layout, 350, 500, true);
			// display the popup in the center
			pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("From this page you can view recently played, favouritedor all courses, aswell as statistics from previous games" +
					"\n\nBy clicking on the image of the stick man golfer you can quicly navigate to a full list of all courses current;ly stored within the app.");

			Button cancelButton = (Button) layout.findViewById(R.id.btnCancelInfo);
			cancelButton.setOnClickListener(infoClose);
		} 
		catch (Exception e) {
		}
	}
	private OnClickListener infoClose = new OnClickListener() {
		public void onClick(View v) {
			pw.dismiss();
		}
	};
}
