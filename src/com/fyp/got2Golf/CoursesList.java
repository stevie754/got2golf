package com.fyp.got2Golf;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CoursesList extends ListActivity{
	//Android listview object
	ListView listViewCourses;
	private DBAdapter mDbHelper;
	public static final int INSERT_ID = Menu.FIRST;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.courseslist);

		mDbHelper = new DBAdapter(this);
		mDbHelper.open();
		fillData();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return result;
	}

	@Override
	//menu bar displays 1 choice, to add an item, this will navigate the user to the add course page
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent addItemIntent = new Intent(this, AddCourse.class);
		startActivity(addItemIntent);
		return super.onOptionsItemSelected(item);
	}

	private void fillData() {
		// Get all of the courses from the database and create the item list
		Cursor mDbCourseCursor = mDbHelper.getAllCourses();
		startManagingCursor(mDbCourseCursor);

		String[] from = new String[] { DBAdapter.KEY_NAME };
		int[] to = new int[] { R.id.text1 };

		// create an array adapter and set it to display using row
		SimpleCursorAdapter notes =
				new SimpleCursorAdapter(this, R.layout.each_course, mDbCourseCursor, from, to);
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
			//toast shown to confirm course selection 
			String strCourse = "id: " + c.getString(0) + "\n" + "Name: " + c.getString(1);
			Toast.makeText(CoursesList.this, strCourse, Toast.LENGTH_LONG).show();
		}
		catch (Exception ex){
			Toast.makeText(CoursesList.this, "Error", Toast.LENGTH_LONG).show();
		}
		db.close();

		Intent detailsIntent = new Intent(this, CourseDetails.class);
		startActivity(detailsIntent);
		//Toast.makeText(CoursesList.this, "Item Selected", Toast.LENGTH_LONG).show();
	}
}
