package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

//From RecentCourses, FavouriteCourses, CourseList, CourseMap
//To CourseSettings
public class CourseDetails extends Activity implements View.OnClickListener {
	private DBAdapter mDbHelper;
	private String strCourseName;
	private int hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18;
	private int parTotal=0, parFront, parBack, par3Count, par4Count, par5Count;
	private String parArray;
	boolean check = false;
	
	View layout;
	private PopupWindow pw;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.coursedetails);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mDbHelper = new DBAdapter(this);
		mDbHelper.open();
		
		//instantiate buttons and set onClick listener 
		Button playButton = (Button)findViewById(R.id.btnPlayCourse);
		playButton.setOnClickListener(this);
		Button tagLocationButton = (Button)findViewById(R.id.buttonTagCourseLocation);
		tagLocationButton.setOnClickListener(this);
		ImageButton btnFavourite = (ImageButton)findViewById(R.id.favorite_button);
		btnFavourite.setOnClickListener(this);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoCDetails);
		infoBtn.setOnClickListener(this);
		//CourseDetails TextViews
		TextView courseName = (TextView)findViewById(R.id.courseName);
		TextView courseShotTotal = (TextView)findViewById(R.id.courseShotTotal);
		TextView coursePar3Total = (TextView)findViewById(R.id.coursePar3Total);
		TextView coursePar4Total = (TextView)findViewById(R.id.coursePar4Total);
		TextView coursePar5Total = (TextView)findViewById(R.id.coursePar5Total);
		//Shared Preferences
		// Retrieve Shared Pref
		//Get course position to be used against database query
		SharedPreferences courseSettings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = courseSettings.edit();
		int coursePosition = courseSettings.getInt("coursePosition", 1);

		//database
		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor c = db.getCourse(coursePosition); 
		try {
			//String strCourseid =  c.getString(0);
			strCourseName = c.getString(1);
			prefEditor.putString("courseName", strCourseName);
			//assign all holes to a variable for calculation
			String strhole1 = c.getString(2);
			String strhole2 = c.getString(3);
			String strhole3 = c.getString(4);
			String strhole4 = c.getString(5);
			String strhole5 = c.getString(6);
			String strhole6 = c.getString(7);
			String strhole7 = c.getString(8);
			String strhole8 = c.getString(9);
			String strhole9 = c.getString(10);
			String strhole10 = c.getString(11);
			String strhole11 = c.getString(12);
			String strhole12 = c.getString(13);
			String strhole13 = c.getString(14);
			String strhole14 = c.getString(15);
			String strhole15 = c.getString(16);
			String strhole16 = c.getString(17);
			String strhole17 = c.getString(18);
			String strhole18 = c.getString(19);
			
			//pasre String to integers so they can be used for calculations
			hole1 = Integer.parseInt(strhole1);
			hole2 = Integer.parseInt(strhole2);
			hole3 = Integer.parseInt(strhole3);
			hole4 = Integer.parseInt(strhole4);
			hole5 = Integer.parseInt(strhole5);
			hole6 = Integer.parseInt(strhole6);
			hole7 = Integer.parseInt(strhole7);
			hole8 = Integer.parseInt(strhole8);
			hole9 = Integer.parseInt(strhole9);
			hole10 = Integer.parseInt(strhole10);
			hole11 = Integer.parseInt(strhole11);
			hole12 = Integer.parseInt(strhole12);
			hole13 = Integer.parseInt(strhole13);
			hole14 = Integer.parseInt(strhole14);
			hole15 = Integer.parseInt(strhole15);
			hole16 = Integer.parseInt(strhole16);
			hole17 = Integer.parseInt(strhole17);
			hole18 = Integer.parseInt(strhole18);
			
			//create array of hole data
			int holeArray[] = {hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18};
			//loop through array adding count to relevant variable
			for(int i = 0;i< 9; i++){
				int hole = holeArray[i];
				String strPar = Integer.toString(hole)+",";
				//count number of holes of each par, for the front nine holes 
				parArray = parArray+strPar;
				{
					if (holeArray[i] == 3)
					{
						par3Count++;
					}
					else if (holeArray[i] == 4)
					{
						par4Count++;
					}
					else {
						par5Count++;
					}
					//total shots for course
					parFront =parFront +holeArray[i];
					parTotal = parTotal + holeArray[i];
				}
				}
			for(int i = 9;i< holeArray.length; i++){
				int hole = holeArray[i];
				String strPar = Integer.toString(hole)+",";
				parArray = parArray+strPar;
				//count number of holes of each par, for the back nine holes
				{
					if (holeArray[i] == 3)
					{
						par3Count++;
					}
					else if (holeArray[i] == 4)
					{
						par4Count++;
					}
					else {
						par5Count++;
					}
					//total shots for course
					parBack =parBack +holeArray[i];
					parTotal = parTotal + holeArray[i];
				}
			}
			//Store hole par values to shared pref
			prefEditor.putString("holePars", parArray);
			prefEditor.putInt("frontNine", parFront);
			prefEditor.putInt("backNine", parBack);
			prefEditor.putInt("currentCoursePar", parTotal);
			prefEditor.commit();
			
			//assign variables to relevant textViews
			courseName.setText(strCourseName);
			courseShotTotal.setText("Total: "+ Integer.toString(parTotal));
			coursePar3Total.setText("Par3: "+ Integer.toString(par3Count));
			coursePar4Total.setText("Par4: "+ Integer.toString(par4Count));
			coursePar5Total.setText("Par5: "+ Integer.toString(par5Count));
			}
		catch (Exception ex){
			Toast.makeText(CourseDetails.this, "Error Loading Course Data", Toast.LENGTH_LONG).show();
		}
		db.close();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}
//menu bar OnClick intents
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
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnPlayCourse:
		{
			//Toast.makeText(CourseDetails.this, "Play", Toast.LENGTH_LONG).show();
			Intent playIntent = new Intent(this, CourseSettings.class);
			startActivity(playIntent);
		}
		break;
		case R.id.buttonTagCourseLocation:
		{
			Toast.makeText(CourseDetails.this, "Location Stored", Toast.LENGTH_LONG).show();
			//TODO Future Requirement - allow for coursemap view 
			//No implmentation of storing data
			// Integrate Google Play Services required to display maps within Android application
			//putting in this extra code without implmenting would have caused service requests that would cause drain on battery unnecessarily
		}
		break;
		case R.id.infoCDetails:
		{	
			//Call method to display the pop up window
			initiateInfoWindow();
		}
		break;
		case R.id.favorite_button:
		{
			//Toast.makeText(CourseDetails.this, "Course Favourited", Toast.LENGTH_LONG).show();
			SharedPreferences favourited = getSharedPreferences("myPrefs", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = favourited.edit();
			//Course Position for Current Course
			int coursePos = favourited.getInt("coursePosition", 1);
			//Serialised string of fav courses array
			String strFavouriteArray = favourited.getString("favouritedCourses", "");
			//split string into string array
			try
			{
			String[] favArray = strFavouriteArray.split(",");
			//will flag if course is already added to favourites
			check = false;
			int arraySize = favArray.length;

			for(int i =0; i<arraySize; i++)
			{
				//Stick in boolean to flag if course at position has been found during loop.
				String strFavCoursePos = favArray[i];
				if (strFavCoursePos == "")
				{
					//Toast.makeText(this, "0 Skipped position: "+strFavCoursePos+"!!", Toast.LENGTH_LONG).show();
				}
				else
				{
				int currentPosition =  Integer.parseInt(strFavCoursePos);
				//int FavCourse = strFavCoursePos.toString();
				if (coursePos == currentPosition)
				{
					//course has been found and boolean will be set to true
					Toast.makeText(CourseDetails.this, "Course Already Favourited", Toast.LENGTH_LONG).show();
					check = true;
				}
				}
			}
			//after loop if course already added //leave as is
			if (check == true)
			{
				//Toast.makeText(this, "2 Course Already in Favourites", Toast.LENGTH_LONG).show();
				//TODO remove course from favourites list
			}
			//if the course is not already in the favourites array, add it
			else 
			{
				//Add current course position to the end of the array

				String strCoursePos = String.valueOf(coursePos);
				//favArray[arraySize] = strCoursePos;
				strFavouriteArray = strFavouriteArray+strCoursePos+",";
				
				Toast.makeText(CourseDetails.this, "Course Added to Favourites", Toast.LENGTH_LONG).show();
				
				prefEditor.putString("favouritedCourses", strFavouriteArray);
				prefEditor.commit();
			}			
			}
			catch (Exception exFavCourses)
			{
				//Toast.makeText(this, "4 Something broke adding course to Favourites", Toast.LENGTH_LONG).show();
				Toast.makeText(this, "Error Adding Course to Favourites", Toast.LENGTH_LONG).show();
				exFavCourses.printStackTrace();
				String strErr = exFavCourses.toString();
				Log.println(1, "ErrorFavourites", strErr);
			}
			Toast.makeText(this, "FavouriteArray:"+strFavouriteArray, Toast.LENGTH_LONG).show();
		}
		break;
		}
	}
	private void initiateInfoWindow() {
		try {

			//We need to get the instance of the LayoutInflater, use the context of this activity
			LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.info,  (ViewGroup) findViewById(R.id.popup_info));
			//Inflate the view from a predefined XML layout
			// create a 350px width and 500px height PopupWindow
			pw = new PopupWindow(layout, 350, 500, true);
			// display the popup in the center
			pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("This page displays information on the course you have selected to play.\n\n " +
					"The course can be saved to users favourites by pressing the star button.\n" +
					"(Favourites can be accessed through the Favourite button on the main page)" +
					"\n\nPlease press the play button to continue. ");

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