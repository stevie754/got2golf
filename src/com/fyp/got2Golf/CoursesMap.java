package com.fyp.got2Golf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

//From Course List
//To CourseList and CourseDetails
public class CoursesMap extends Activity implements View.OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.coursesmap);
		//as yet unimplmented, was to be part of a desirable requirement implmented within the application
	}

	@Override
	public void onClick(View v) {
	}
}