package com.fyp.got2Golf;
import java.text.DecimalFormat;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

//statistcics for various clubs
// select club from drop down list (DDL)
//Show top ten/five distances and mean for that club

//http://www.mkyong.com/android/android-spinner-drop-down-list-example/
public class Stats extends Activity implements View.OnClickListener, OnItemSelectedListener{
	View layout;
	private PopupWindow pw;
	String spinnerValue =null;
	final DBAdapter db = new DBAdapter(this);
	int totalPar, totalPar3, totalPar4, totalPar5;
	int sumOfScore, sumOfScorePar3, sumOfScorePar4, sumOfScorePar5;
	double aveSumOfScore, aveSumOfScorePar3, aveSumOfScorePar4, aveSumOfScorePar5;
	int sumOfPutts, sumOfPuttsPar3, sumOfPuttsPar4, sumOfPuttsPar5;
	double aveSumOfPutts, aveSumOfPuttsPar3, aveSumOfPuttsPar4, aveSumOfPuttsPar5;
	int sumOfPen, sumOfPenPar3, sumOfPenPar4, sumOfPenPar5;
	double aveSumOfPen, aveSumOfPenPar3, aveSumOfPenPar4, aveSumOfPenPar5;
	int sumOfGIR, sumOfGIRPar3, sumOfGIRPar4, sumOfGIRPar5;
	//take away sumGIR from total number of Par X for false values;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		totalPar3=0;
		totalPar4=0;
		totalPar5=0;

		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.stats);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoStats);
		infoBtn.setOnClickListener(this);

		Button graphBtn = (Button)findViewById(R.id.btnGraphs);
		graphBtn.setOnClickListener(this);
		
		Spinner spinnerClubStats = (Spinner) findViewById(R.id.spinnerClubStats);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.stats, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerClubStats.setAdapter(adapter);
		spinnerClubStats.setOnItemSelectedListener(this);

		shotDistances();
	}
//method to count the number of holes of the defined par that have been played
	private void parCountStats(int holePar) {
		//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		Cursor c = dbStats.CountPar(holePar);
		if (c.moveToFirst())
		{
			do {
				//display par method used by many of the database query methods
				//Display par will determine how to handle the information based on the second parameter passed
				if (holePar ==3 )
				{
					DisplayPar3(c, 1);
				}
				else if (holePar ==4 )
				{
					DisplayPar4(c, 1);
				}
				else if (holePar == 5)
				{
					DisplayPar5(c, 1);
				}

				else {}
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	private void parShotsStats(int holePar) {
		//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		Cursor c = dbStats.CountShotsPar(holePar);
		if (c.moveToFirst())
		{
			do {
				//display par method used by many of the database query methods
				//Display par will determine how to handle the information based on the second parameter passed
				if (holePar ==3 )
				{
					DisplayPar3(c, 2);
				}
				else if (holePar ==4 )
				{
					DisplayPar4(c, 2);
				}
				else if (holePar == 5)
				{
					DisplayPar5(c, 2);
				}

				else {}
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	private void parPuttStats(int holePar) {
		//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		Cursor c = dbStats.CountPuttsPar(holePar);
		if (c.moveToFirst())
		{
			do {
				//display par method used by many of the database query methods
				//Display par will determine how to handle the information based on the second parameter passed
				if (holePar ==3 )
				{
					DisplayPar3(c, 3);
				}
				else if (holePar ==4 )
				{
					DisplayPar4(c, 3);
				}
				else if (holePar == 5)
				{
					DisplayPar5(c, 3);
				}

				else {}
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	private void parPenStats(int holePar) {
		//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		Cursor c = dbStats.CountPenPar(holePar);
		if (c.moveToFirst())
		{
			do {
				//display par method used by many of the database query methods
				//Display par will determine how to handle the information based on the second parameter passed
				if (holePar ==3 )
				{
					DisplayPar3(c, 4);
				}
				else if (holePar ==4 )
				{
					DisplayPar4(c, 4);
				}
				else if (holePar == 5)
				{
					DisplayPar5(c, 4);
				}

				else {}
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	private void parGIRTrueStats(int holePar) {
		//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		//Cursor c = dbStats.CountPenPar(holePar);
		Cursor c = dbStats.CountTrueGIR(holePar);
		if (c.moveToFirst())
		{
			do {
				//display par method used by many of the database query methods
				//Display par will determine how to handle the information based on the second parameter passed
				if (holePar ==3 )
				{
					DisplayPar3(c, 5);
				}
				else if (holePar ==4 )
				{
					DisplayPar4(c, 5);
				}
				else if (holePar == 5)
				{
					DisplayPar5(c, 5);
				}

				else {}
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	
	
	private void parOverView(){
		DBAdapter dbStats = new DBAdapter(this);
		dbStats.open();
		Cursor c = dbStats.CountPar();
		if (c.moveToFirst())
		{
			do {
				DisplayOverview(c, 1);
			} while(c.moveToNext());
		}
		else
		{
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		}
		Cursor d = dbStats.CountShotsPar();
		if (d.moveToFirst())
		{
			do {
				DisplayOverview(d, 2);
			} while(d.moveToNext());
		}
		else
		{
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		}
		Cursor e = dbStats.CountPuttsPar();
		if (e.moveToFirst())
		{
			do {
				DisplayOverview(e, 3);
			} while(e.moveToNext());
		}
		else
		{
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		}
		Cursor f = dbStats.CountPenPar();
		if (f.moveToFirst())
		{
			do {
				DisplayOverview(f,4);
			} while(f.moveToNext());
		}
		else
		{
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		}
		Cursor g = dbStats.CountTrueGIR();
		if (g.moveToFirst())
		{
			do {
				DisplayOverview(g,5);
			} while(g.moveToNext());
		}
		else
		{
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		}
		dbStats.close();
	}
	//method to gather All stats
	private void DisplayOverview(Cursor c, int param) {
		TextView txtParAll = (TextView)findViewById(R.id.textAllStats);
		if (param ==1)
		{
			//count number of times hole of par X has been played
			String strStats = c.getString(0);
			totalPar = Integer.parseInt(strStats);
			txtParAll.setText("Total Played: \t" +  strStats);
		}
		else if (param ==2)
		{
			//count number of shots, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfScore = Integer.parseInt(strStats);
			double score = sumOfScore/totalPar;
			aveSumOfScore = roundTwoDecimals(score);

			String a = txtParAll.getText().toString();
			String temp = a +"\nShots: \t\t\t\t" +  aveSumOfScore;
			txtParAll.setText(temp);
		}
		else if (param ==3)
		{//count number of putts, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfPutts = Integer.parseInt(strStats);
			double score = sumOfPutts/totalPar;
			aveSumOfPutts = roundTwoDecimals(score);

			String a = txtParAll.getText().toString();
			String temp = a +"\nPutts: \t\t\t\t\t" +  aveSumOfPutts;
			txtParAll.setText(temp);
		}
		else if (param ==4)
		{//count number of penalty shots, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfPen = Integer.parseInt(strStats);
			double score = sumOfPen/totalPar;
			aveSumOfPen = roundTwoDecimals(score);

			String a = txtParAll.getText().toString();
			String temp = a +"\nPen: \t\t\t\t\t" +  aveSumOfPen;
			txtParAll.setText(temp);
		}
		else if (param ==5)
		{//count number of time user achieves GIR, for a hole of par X
			String strStats = c.getString(0);
			sumOfGIR = Integer.parseInt(strStats);
			String a = txtParAll.getText().toString();
			String temp = a +"\nGIR: \t\t\t\t\t" +  sumOfGIR + "/" + totalPar;
			txtParAll.setText(temp);
		}
		else{}
	}
	//Gather stats on par 3s
	private void DisplayPar3(Cursor c, int param) {
		
		TextView txtPar3Stats = (TextView)findViewById(R.id.textPar3);
		if (param ==1)
		{
			//count number of times hole of par X has been played
			String strStats = c.getString(0);
			totalPar3 = Integer.parseInt(strStats);
			txtPar3Stats.setText("Par 3: \t\t\t\t\t" +  strStats);
		}
		else if (param ==2)
		{
			//count number of shots, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfScorePar3 = Integer.parseInt(strStats);
			double score = sumOfScorePar3/totalPar3;
			aveSumOfScorePar3 = roundTwoDecimals(score);

			String a = txtPar3Stats.getText().toString();
			String temp = a +"\nShots: \t\t\t\t" +  aveSumOfScorePar3;
			txtPar3Stats.setText(temp);
		}
		else if (param ==3)
		{//count number of putts, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfPuttsPar3 = Integer.parseInt(strStats);
			double score = sumOfPuttsPar3/totalPar3;
			aveSumOfPuttsPar3 = roundTwoDecimals(score);

			String a = txtPar3Stats.getText().toString();
			String temp = a +"\nPutts: \t\t\t\t\t" +  aveSumOfPuttsPar3;
			txtPar3Stats.setText(temp);
		}
		else if (param ==4)
		{//count number of penalty shots, average that are required by the user for a hole of par X
			String strStats = c.getString(0);
			sumOfPenPar3 = Integer.parseInt(strStats);
			double score = sumOfPenPar3/totalPar3;
			aveSumOfPenPar3 = roundTwoDecimals(score);

			String a = txtPar3Stats.getText().toString();
			String temp = a +"\nPen: \t\t\t\t\t" +  aveSumOfPenPar3;
			txtPar3Stats.setText(temp);
		}
		else if (param ==5)
		{//count number of time user achieves GIR, for a hole of par X
			String strStats = c.getString(0);
			sumOfGIRPar3 = Integer.parseInt(strStats);

			String a = txtPar3Stats.getText().toString();
			String temp = a +"\n Test 3";
			//String temp = a +"\nGIR: \t\t\t\t\t" +  sumOfGIRPar3 + "/" + totalPar3;
			txtPar3Stats.setText(temp);
		}
		else{}
	}
	//Gather stats on par 4s
	private void DisplayPar4(Cursor c, int param) {
		TextView txtPar4Stats = (TextView)findViewById(R.id.textPar4);
		if (param ==1)
		{//same methods as used for par 3, just passing in 4 parameter to database queries
			String strStats = c.getString(0);
			totalPar4 = Integer.parseInt(strStats);
			txtPar4Stats.setText("Par 4: \t\t\t\t\t" +  strStats);
		}
		else if (param ==2)
		{
			String strStats = c.getString(0);
			sumOfScorePar4 = Integer.parseInt(strStats);
			double score = sumOfScorePar4/totalPar4;
			aveSumOfScorePar4 = roundTwoDecimals(score);

			String a = txtPar4Stats.getText().toString();
			String temp = a +"\nShots: \t\t\t\t" +  aveSumOfScorePar4;
			txtPar4Stats.setText(temp);
		}
		else if (param ==3)
		{
			String strStats = c.getString(0);
			sumOfPuttsPar4 = Integer.parseInt(strStats);
			double score = sumOfPuttsPar4/totalPar4;
			aveSumOfPuttsPar4 = roundTwoDecimals(score);

			String a = txtPar4Stats.getText().toString();
			String temp = a +"\nPutts: \t\t\t\t\t" +  aveSumOfPuttsPar4;
			txtPar4Stats.setText(temp);
		}
		else if (param ==4)
		{
			String strStats = c.getString(0);
			sumOfPenPar4 = Integer.parseInt(strStats);
			double score = sumOfPenPar4/totalPar4;
			aveSumOfPenPar4 = roundTwoDecimals(score);

			String a = txtPar4Stats.getText().toString();
			String temp = a +"\nPen: \t\t\t\t\t" +  aveSumOfPenPar4;
			txtPar4Stats.setText(temp);
		}
		else if (param ==5)
		{
			String strStats = c.getString(0);
			sumOfGIRPar4 = Integer.parseInt(strStats);

			String a = txtPar4Stats.getText().toString();
			String temp = a +"\n Test 4";
			//String temp = a +"\nGIR: \t\t\t\t\t" +  sumOfGIRPar4 + "/" + totalPar4;
			txtPar4Stats.setText(temp);
		}
		else{}
	}
	//Gather stats on par 5s
	private void DisplayPar5(Cursor c, int param) {
		TextView txtPar5Stats = (TextView)findViewById(R.id.textPar5);
		if (param ==1)
		{//same methods as used for par 3, just passing in 4 parameter to database queries
			String strStats = c.getString(0);
			totalPar5 = Integer.parseInt(strStats);
			txtPar5Stats.setText("Par 5: \t\t\t\t\t" +  strStats);
		}
		else if (param ==2)
		{
			String strStats = c.getString(0);
			sumOfScorePar5 = Integer.parseInt(strStats);
			double score = sumOfScorePar5/totalPar5;
			aveSumOfScorePar5 = roundTwoDecimals(score);

			String a = txtPar5Stats.getText().toString();
			String temp = a +"\nShots: \t\t\t\t" +  aveSumOfScorePar5;
			txtPar5Stats.setText(temp);
		}
		else if (param ==3)
		{
			String strStats = c.getString(0);
			sumOfPuttsPar5 = Integer.parseInt(strStats);
			double score = sumOfPuttsPar5/totalPar5;
			aveSumOfPuttsPar5 = roundTwoDecimals(score);

			String a = txtPar5Stats.getText().toString();
			String temp = a +"\nPutts: \t\t\t\t\t" +  aveSumOfPuttsPar5;
			txtPar5Stats.setText(temp);
		}
		else if (param ==4)
		{
			String strStats = c.getString(0);
			sumOfPenPar5 = Integer.parseInt(strStats);
			double score = sumOfPenPar5/totalPar5;
			aveSumOfPenPar5 = roundTwoDecimals(score);

			String a = txtPar5Stats.getText().toString();
			String temp = a +"\nPen: \t\t\t\t\t" +  aveSumOfPenPar5;
			txtPar5Stats.setText(temp);
		}
		else if (param ==5)
		{
			String strStats = c.getString(0);
			sumOfGIRPar5 = Integer.parseInt(strStats);
			//Toast.makeText(this, "5", Toast.LENGTH_LONG).show();
			String a = txtPar5Stats.getText().toString();
			String temp = a +"\n Test 5";
			//String temp = a +"\nGIR: \t\t\t\t\t" +  sumOfGIRPar5 + "/" + totalPar5;
			txtPar5Stats.setText(temp);
		}
		else{}

	}

	private void shotDistances() {
		DBAdapter dbStats = new DBAdapter(this);

		dbStats.open();
		Cursor c = dbStats.getAllShots();
		if (c.moveToFirst())
		{
			do {
				DisplayShots(c);
			} while(c.moveToNext());
		}
		else
			Toast.makeText(this, "No courses", Toast.LENGTH_LONG).show();
		dbStats.close();
	}
	private void DisplayShots(Cursor c) {
		TextView txtShotTracking = (TextView)findViewById(R.id.textShotTracking);
		String strStats = "Id: " + c.getString(0) + "   " + "Course: " + c.getString(1) + "   " + "Hole: " + c.getString(2)  + "   " + "Distance: " + c.getString(3)+"\n";
		String strTemp = (String) txtShotTracking.getText();
		txtShotTracking.setText(strTemp + "\n"+strStats);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.infoStats:
		{	
			initiateInfoWindow();
		}
		case R.id.btnGraphs:
		{	
			Toast.makeText(this, "Graph Functionality Limited", Toast.LENGTH_SHORT).show();
		}
		break;
		}
	}
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		int idSpinner = parent.getId();

		String strStats = parent.getItemAtPosition(pos).toString();

		switch (idSpinner) {
		case R.id.spinnerClubStats:
			setSpinnerValue(strStats);
			break;
		}
		try{
			/**
			0 is for VISIBLE
			4 is for INVISIBLE 
			8 is for GONE 
			 */
			TableRow rowST = (TableRow)findViewById(R.id.tblStatRow1);
			TableRow rowP3 = (TableRow)findViewById(R.id.tblStatRow2);
			TableRow rowP4 = (TableRow)findViewById(R.id.tblStatRow3);
			TableRow rowP5 = (TableRow)findViewById(R.id.tblStatRow4);
			TableRow rowAll = (TableRow)findViewById(R.id.tblStatRow5);

			String tableSettings = getSpinnerValue();
			//Depending on the spinner selection hide or show the appropriate stats view
			if (tableSettings.equals("Shot Tracking"))
			{
				rowST.setVisibility(0);
				rowP3.setVisibility(8);
				rowP4.setVisibility(8);
				rowP5.setVisibility(8);
				rowAll.setVisibility(8);
			}
			else if (tableSettings.equals("Par 3"))
			{
				rowST.setVisibility(8);
				rowP3.setVisibility(0);
				rowP4.setVisibility(8);
				rowP5.setVisibility(8);
				rowAll.setVisibility(8);
			}
			else if (tableSettings.equals("Par 4"))
			{
				rowST.setVisibility(8);
				rowP3.setVisibility(8);
				rowP4.setVisibility(0);
				rowP5.setVisibility(8);
				rowAll.setVisibility(8);
			}
			else if (tableSettings.equals("Par 5"))
			{
				rowST.setVisibility(8);
				rowP3.setVisibility(8);
				rowP4.setVisibility(8);
				rowP5.setVisibility(0);
				rowAll.setVisibility(8);
			}
			else if (tableSettings.equals("All"))
			{
				rowST.setVisibility(8);
				rowP3.setVisibility(8);
				rowP4.setVisibility(8);
				rowP5.setVisibility(8);
				rowAll.setVisibility(0);
			}
			else {
				Toast.makeText(this, "Error: "+tableSettings, Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception ex)
		{
			Toast.makeText(this, "Statistics Table Error", Toast.LENGTH_SHORT).show();
		}
		try
		{
			if (pos == 4){
				//Toast.makeText(this, "All", Toast.LENGTH_SHORT).show();
				parOverView();
			}
			if (pos == 3){
				//Toast.makeText(this, "Par 5", Toast.LENGTH_SHORT).show();
				parCountStats(5);
				parShotsStats(5);
				parPuttStats(5);
				parPenStats(5);
				parGIRTrueStats(5);
				//TextView txtPar5 = (TextView)findViewById(R.id.textPar5);
				//txtPar5.setText(totalPar5);
			}
			if (pos == 2){
				//Toast.makeText(this, "Par 4", Toast.LENGTH_SHORT).show();
				parCountStats(4);
				parShotsStats(4);
				parPuttStats(4);
				parPenStats(4);
				parGIRTrueStats(4);
				//TextView txtPar4 = (TextView)findViewById(R.id.textPar4);
				//txtPar4.setText(totalPar4);
			}
			if (pos == 1){
				//Toast.makeText(this, "Par 3", Toast.LENGTH_SHORT).show();
				parCountStats(3);
				parShotsStats(3);
				parPuttStats(3);
				parPenStats(3);
				parGIRTrueStats(3);
				//TextView txtPar3 = (TextView)findViewById(R.id.textPar3);
				//txtPar3.setText(totalPar3);
			}
			if (pos == 0){
				//Toast.makeText(this, "Shot Tracking", Toast.LENGTH_SHORT).show();
				TextView txtShots = (TextView)findViewById(R.id.textShotTracking);
				txtShots.setText("");
				shotDistances();
			}
		}
		catch (Exception noStatsEX)
		{
			//Toast.makeText(this, "No stats saved", Toast.LENGTH_SHORT).show();
		}
	}

	public String getSpinnerValue() {
		return spinnerValue;
	}
	public void setSpinnerValue(String spin){
		spinnerValue = spin;
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
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
			infoText.setText("Use the spinner to select which set of statistics you would like to see.\n\nPress the graph button at the bottom of the page to view a GIR graph.");

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
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}