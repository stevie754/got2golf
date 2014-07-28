package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
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

//From MainMenu, CoursesMap
//To Course Details, CoursesMap
//list of all courses available.
//possible functionality to allow for all courses to be veiwed and data to be downloaded as required
public class Courses extends Activity implements View.OnClickListener {
	View layout;
	private PopupWindow pw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.courses);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//TextView
		//Set up Database of existing courses
		DBAdapter db = new DBAdapter(this);

		//loop through all courses currently saved and call the DisplayCourses method for each.
		db.open();
		Cursor c = db.getAllCourses();
		if (c.moveToFirst())
		{
			do {
				DisplayCourse(c);
			} while(c.moveToNext());
		}
		else
			Toast.makeText(Courses.this, "No courses", Toast.LENGTH_LONG).show();
		db.close();

		//Set up button onclick
		Button addButton = (Button)findViewById(R.id.buttonAddCourse);
		addButton.setOnClickListener(this);
		Button editButton = (Button)findViewById(R.id.buttonEditCourse);
		editButton.setOnClickListener(this);
		Button deleteButton = (Button)findViewById(R.id.buttonDeleteCourse);
		deleteButton.setOnClickListener(this);
		Button playButton = (Button)findViewById(R.id.buttonPlayCourse);
		playButton.setOnClickListener(this);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoCourses);
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
	//menu bar onclicks
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
	public void DisplayCourse(Cursor c)
	{
		//the list view will allow for the user to interact with the app by clicking to select from a list of courses
		//pass id to the app which will be used to navigate to the relevant course details page.
		TextView viewCourses = (TextView)findViewById(R.id.txtViewCourses);
		String strCourse = "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) + "\n";
		String strTemp = (String) viewCourses.getText();
		viewCourses.setText(strTemp + "\n"+strCourse);
	}

	@Override
	public void onClick(View v) {
		//Create a switch statement when a button is pressed the app will go to the relevant page
		switch(v.getId()){
		//onclcik to navigate to relevant page within app
		case R.id.buttonAddCourse:
		{
			Intent addIntent = new Intent(this, AddCourse.class);
			startActivity(addIntent);
		}
		break;
		case R.id.buttonEditCourse:
		{
			Intent editIntent = new Intent(this, EditCourse.class);
			startActivity(editIntent);
		}
		break;
		case R.id.buttonDeleteCourse:
		{
			Intent deleteIntent = new Intent(this, DeleteCourse.class);
			startActivity(deleteIntent);
		}
		break;
		case R.id.buttonPlayCourse:
		{
			Intent addIntent = new Intent(this, CoursesList.class);
			startActivity(addIntent);
		}
		break;
		case R.id.infoCourses:
		{	
			initiateInfoWindow();
		}
		break;
		}
	}
	private void initiateInfoWindow() {
		try {
			LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.info,  (ViewGroup) findViewById(R.id.popup_info));
			//Inflate the view
			// create a 350px width and 500px height PopupWindow
			pw = new PopupWindow(layout, 350, 500, true);
			// display the popup in the center
			pw.showAtLocation(layout, Gravity.CENTER, 0, 0);

			//set the text that will be displayed within the user guide popup
			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("Play - Brings user to a list of courses available to play.\n\nAdd - Add new courses to the application.\n\nEdit - Edit cpourses that are currently stored within the applicaiton.\n\nDelete - Delete courses from the application memory\n\n");

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