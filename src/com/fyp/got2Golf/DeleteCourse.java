package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteCourse extends Activity implements View.OnClickListener{

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deletecourse);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//Create a new instance of the db adapter
		final DBAdapter db = new DBAdapter(this);
		//instantiate the buttons and their onclick listener
		final Button getinfoButton = (Button) findViewById(R.id.btnGetCourse); 
		getinfoButton.setOnClickListener(this);
		final Button deleteButton = (Button) findViewById(R.id.btnDelete); 
		deleteButton.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

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
		//Toast popup to allow users to view deatils on the course that matches the ID entered
		Toast.makeText(DeleteCourse.this, "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1) , Toast.LENGTH_LONG).show();
	}
	@Override
	public void onClick(View v) {
		final DBAdapter db = new DBAdapter(this);
		//Create a switch statement when a button is pressed the app will go to the relevant page
		switch(v.getId()){	
		case R.id.btnGetCourse:
		{
			try{
				//Get the number that has been entered into the EditText, 
				EditText strEditID = (EditText)findViewById(R.id.editCourseId);
				String strID = strEditID.getText().toString();
				long  intID = Integer.parseInt(strID);
				// open the database and query for the course with the id matching that entered by the user
				db.open();
				Cursor c = db.getCourse(intID);

				if (c.moveToFirst())
					//display details for the course if it is found
					DisplayCourse(c);
				else
					//Prompts showing user that no course was found/deleted
					Toast.makeText(DeleteCourse.this, "No Course Found. ", Toast.LENGTH_LONG).show();
				db.close();
			}
			catch (Exception e) {
				//Prompts showing user that no course was found/deleted
				Toast.makeText(DeleteCourse.this, "No Course Found. ", Toast.LENGTH_LONG).show();
			}
		}
		break;
		case R.id.btnDelete:
		{
			try{
				EditText strEditID = (EditText)findViewById(R.id.editCourseId);
				String strID = strEditID.getText().toString();
				long  intID = Integer.parseInt(strID);
				//similar to getCourse button, retreives the number entered by the user
				//used this number the query the database for the chosen course
				//runs a SQL method to delete the selected row (if it is found)
				db.open();
				if (db.deleteCourse(intID))
					Toast.makeText(DeleteCourse.this, "Delete Successful. ", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(DeleteCourse.this, "Delete Failed. ", Toast.LENGTH_LONG).show();
				db.close();
			}
			catch (Exception e) {
				Toast.makeText(DeleteCourse.this, "Delete Failed. ", Toast.LENGTH_LONG).show();
			}
			Intent mainIntent = new Intent(this, MainMenu.class);
			//Will return the user back to the main page
			finish();
			startActivity(mainIntent);			
		}
		break;
		}
	}
}

