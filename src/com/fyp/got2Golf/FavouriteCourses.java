package com.fyp.got2Golf;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
//list of favourite courses selected by the user
//From Main
//To CourseDetails
public class FavouriteCourses extends ListActivity {
	String [] favouriteArray;
	
	ListView listViewCourses;
	private DBAdapter mDbHelper;
	public static final int INSERT_ID = Menu.FIRST;
	String favourited;
	MergeCursor mergerCursorNotes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.favouritecourses);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//get favourite array and retrieve only those files.
		//Set shared pref
				SharedPreferences favCourses = getSharedPreferences("myPrefs", MODE_PRIVATE);
				//recover all recents from shared pref
				favourited = favCourses.getString("favouritedCourses", "");
				//split all recent String into array
				favouriteArray = favourited.split(",");
		
		mDbHelper = new DBAdapter(this);
		mDbHelper.open();
		
		for (int i =0; i<favouriteArray.length; i++)
		{
			try{
			int favParameter = Integer.parseInt(favouriteArray[i]);
			fillData(favParameter);
			}
			catch (Exception ex)
			{
				
			}
		}
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
	private void fillData(int favParameter) {
		// Get all of the notes from the database and create the item list
		//create query to only find courses that match the correct id.
		int paramFav = favParameter;
		
		Cursor mDbCourseCursor = mDbHelper.getCourse(paramFav);
		startManagingCursor(mDbCourseCursor);

		String[] from = new String[] { DBAdapter.KEY_NAME };
		int[] to = new int[] { R.id.text1 };

		// Now create an array adapter and set it to display using our row
		SimpleCursorAdapter notes =
				new SimpleCursorAdapter(this, R.layout.each_course, mDbCourseCursor, from, to);
		
		//Cursor[] cursors = { mergerCursorNotes, (Cursor) notes };
		//Cursor mergerCursorNotes = new MergeCursor(cursors);
		setListAdapter(notes);
	}
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
      //Set up Database of existing courses
      		DBAdapter db = new DBAdapter(this);

      		//create variable for course position to save to sharedPref
      		int coursePosition = position+1;
      	//Set shared pref
			SharedPreferences courseSettings = getSharedPreferences("myPrefs", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = courseSettings.edit();
			//Store course position to shared pref
			prefEditor.putInt("coursePosition", coursePosition);
			prefEditor.commit();
			
      		db.open();
      		Cursor c = db.getCourse(position+1); 
      		try {
      			//String strCourseid =  c.getString(0);
      			//String strCourseName = c.getString(1);
      			//Toast.makeText(CoursesList.this, strCourseName, Toast.LENGTH_LONG).show();
      			
      			String strCourse = "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1);
      			Toast.makeText(this, strCourse, Toast.LENGTH_LONG).show();
    			//String courseName = db.displayName();
    			//Toast.makeText(CoursesList.this, courseName, Toast.LENGTH_LONG).show();
    			}
    		catch (Exception ex){
    			Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    		}
    		db.close();

    		Intent detailsIntent = new Intent(this, CourseDetails.class);
			startActivity(detailsIntent);
        //Toast.makeText(CoursesList.this, "Item Selected", Toast.LENGTH_LONG).show();
    }
}