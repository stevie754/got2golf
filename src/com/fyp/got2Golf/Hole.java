package com.fyp.got2Golf;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Hole extends Activity implements OnItemSelectedListener, View.OnClickListener {
	//Spinner Value Variables
	int UserShotScore,Guest1ShotScore,Guest2ShotScore,Guest3ShotScore,UserRecovery,UserPutt;
	boolean UserGIR;
	//Page Variables
	private int holeNo =1,holeP,coursePosition;
	private int hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18;
	//private int[] holeParArray = {hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18};
	private String strCourseName,strCourseid, userHoleScoreArray, guest1HoleScoreArray, guest2HoleScoreArray, guest3HoleScoreArray, userHoleGIRArray, userHoleRecoveryArray, userHolePuttsArray;
	//If hole already played position in temp table
	int holePosition;
	int userFrontNine, userBackNine, g1FrontNine, g1BackNine, g2FrontNine, g2BackNine, g3FrontNine, g3BackNine, parTotal;
	double lat, lng, startLat, startLng, endLat, endLng, distance, rndDistance;
	LocationManager locationManager;
	LocationListener locationListener;
	//Settings
	boolean bolRecovery, bolPutt, bolGIR;
	//Popup window
	View layout;
	//Temp Data Arrays
	int[] TempHoleNo = new int [18]; 
	int[] TempHolePar = new int [18]; 
	int[] TempUserScore = new int [18];
	int[] TempGuest1Score = new int [18];
	int[] TempGuest2Score = new int [18];
	int[] TempGuest3Score = new int [18];
	boolean[] TempUserGIR = new boolean [18];
	int[] TempUserRecovery = new int [18];
	int[] TempUserPutts = new int [18];
	//Tracking PopUp Window
	private PopupWindow pw;
	//db adapter
	final DBAdapter db = new DBAdapter(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.hole);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//Clear Stat Table from previous Game
		try{
			clearTable();
		}
		catch (Exception ex)
		{
			Toast.makeText(Hole.this, "Error Clearing Previous Data", Toast.LENGTH_SHORT).show();
		}
		//location listener
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		String provider = LocationManager.GPS_PROVIDER;
		Location location = locationManager.getLastKnownLocation(provider);
		updateWithNewLocation(location);

		//Set up locationListener
		locationListener= new LocationListener()
		{
			public void onStatusChanged(String provider, int status, Bundle extras)
			{
			}

			@Override
			public void onLocationChanged(Location location) {
				updateWithNewLocation(location);
				//Toast.makeText(Hole.this, "Location Changed", Toast.LENGTH_LONG).show();
				distanceCalculation();
			}

			@Override
			public void onProviderDisabled(String provider) { 			
			}

			@Override
			public void onProviderEnabled(String provider) {
			}
		};
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);

		//Shared Preferences
		// Retrieve Shared Pref
		SharedPreferences courseSettings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		coursePosition = courseSettings.getInt("coursePosition", 1);
		int noPlayers = courseSettings.getInt("NoOfPlayers", 1);

		//Shared Settings Preferences
		bolRecovery = courseSettings.getBoolean("RecoverySetting", true);
		bolGIR = courseSettings.getBoolean("GIRSetting", true);
		bolPutt = courseSettings.getBoolean("PuttSetting", true);
		//settings additional stats tracking
		try{
			/**
					0 is for VISIBLE
					4 is for INVISIBLE 
					8 is for GONE 
			 */
			TableRow rowGIR = (TableRow)findViewById(R.id.tblUserGIR);
			TableRow rowRecover = (TableRow)findViewById(R.id.tblUserPenalty);
			TableRow rowPutts = (TableRow)findViewById(R.id.tblUserPutts);

			if (bolGIR ==true)
			{
				rowGIR.setVisibility(0);
			}
			else
			{
				rowGIR.setVisibility(8);
			}
			if (bolRecovery ==true)
			{
				rowRecover.setVisibility(0);
			}
			else
			{
				rowRecover.setVisibility(8);
			}
			if (bolPutt ==true)
			{
				rowPutts.setVisibility(0);
			}
			else
			{
				rowPutts.setVisibility(8);
			}
		}
		catch (Exception exSettingsHidden)
		{

		}
		//set guest score spinners to visible of gone
		try{
			/**
			0 is for VISIBLE
			4 is for INVISIBLE 
			8 is for GONE 
			 */
			TableRow row1 = (TableRow)findViewById(R.id.tblGuest1Row);
			TableRow row2 = (TableRow)findViewById(R.id.tblGuest2Row);
			TableRow row3 = (TableRow)findViewById(R.id.tblGuest3Row);

			if (noPlayers ==1)
			{
				row1.setVisibility(8);
				row2.setVisibility(8);
				row3.setVisibility(8);

			}
			else if (noPlayers ==2)
			{
				row1.setVisibility(0);
				row2.setVisibility(8);
				row3.setVisibility(8);
			}
			else if (noPlayers ==3)
			{
				row1.setVisibility(0);
				row2.setVisibility(0);
				row3.setVisibility(8);
			}
			else
			{
				row1.setVisibility(0);
				row2.setVisibility(0);
				row3.setVisibility(0);
			}
		}
		catch (Exception exHidden)
		{

		}
		//database
		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor c = db.getCourse(coursePosition); 
		try {
			strCourseid =  c.getString(0);
			strCourseName = c.getString(1);
			//assign all holes to a variable 
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
		}
		catch (Exception ex){
			Toast.makeText(Hole.this, "Error Loading Course Data", Toast.LENGTH_LONG).show();
		}

		TextView holeNum = (TextView)findViewById(R.id.txtHoleNum);
		holeNum.setText("Hole: 1");
		TextView holePar = (TextView)findViewById(R.id.txtPar);
		holePar.setText("Par: " + hole1);
		holeP = hole1;
		// Create instances of buttons and link with XML file0
		Button btnScoreCard = (Button)findViewById(R.id.scoreCardBtn);
		btnScoreCard.setOnClickListener(this);
		Button btnBack = (Button)findViewById(R.id.btnBackHole);
		btnBack.setOnClickListener(this);
		Button btnReset = (Button)findViewById(R.id.btnReset);
		btnReset.setOnClickListener(this);
		Button btnNext = (Button)findViewById(R.id.btnNextHole);
		btnNext.setOnClickListener(this);
		Button btnFinish = (Button)findViewById(R.id.btnFinish);
		btnFinish.setOnClickListener(this);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoHole);
		infoBtn.setOnClickListener(this);
		Button btnTrack = (Button)findViewById(R.id.btnStartTracking);
		btnTrack.setOnClickListener(this);
		btnBack.setClickable(false);
		btnScoreCard.setClickable(false);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.scores, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		//Create spinners
		Spinner spnUserShots = (Spinner) findViewById(R.id.spinnerShots);
		Spinner spnGuest1Shots = (Spinner) findViewById(R.id.spinnerGuest1Shots);
		Spinner spnGuest2Shots = (Spinner) findViewById(R.id.spinnerGuest2Shots);
		Spinner spnGuest3Shots = (Spinner) findViewById(R.id.spinnerGuest3Shots);
		Spinner spnRecovery = (Spinner) findViewById(R.id.spinnerPenalty);
		Spinner spnPutts = (Spinner) findViewById(R.id.spinnerPutts);
		//GIR Checkbox
		CheckBox chkGIR = (CheckBox)findViewById(R.id.checkBoxGIR);
		chkGIR.setChecked(true);
		// Apply the adapter to the spinners
		spnUserShots.setAdapter(adapter);
		spnUserShots.setOnItemSelectedListener(this);
		spnGuest1Shots.setAdapter(adapter);
		spnGuest1Shots.setOnItemSelectedListener(this);
		spnGuest2Shots.setAdapter(adapter);
		spnGuest2Shots.setOnItemSelectedListener(this);
		spnGuest3Shots.setAdapter(adapter);
		spnGuest3Shots.setOnItemSelectedListener(this);
		spnRecovery.setAdapter(adapter);
		spnRecovery.setOnItemSelectedListener(this);
		//spnPutts.setAdapter(adapter);
		spnPutts.setOnItemSelectedListener(this);


		UserShotScore= getSpinnerShots();
		Guest1ShotScore = getSpinnerGuest1Shots();
		Guest2ShotScore = getSpinnerGuest2Shots();
		Guest3ShotScore = getSpinnerGuest3Shots();
		UserPutt = getSpinnerUserPutt();
		UserRecovery = getSpinnerUserPutt();

		//set the default according to value
		spnUserShots.setSelection(hole1);
		spnGuest1Shots.setSelection(hole1);
		spnGuest2Shots.setSelection(hole1);
		spnGuest3Shots.setSelection(hole1);

		db.close();

		//Set GIR to true or false based on spinner values
		checkGIR(holeP, getSpinnerShots(), getSpinnerUserPutt(), getSpinnerUserRecovery());
		//Toast.makeText(Hole.this, "Checking GIR", Toast.LENGTH_SHORT).show();
	}
	private void stopLocationManager(){
		try{
			//location is stopped
			//no longer requests updates
			locationManager.removeUpdates(locationListener);

		}
		catch(Exception exLocation)
		{
			Toast.makeText(this, "Error: 404",Toast.LENGTH_SHORT).show();
		}
	}
	private void updateWithNewLocation(Location location) {
		if(location != null)
		{
			lat = location.getLatitude();
			lng = location.getLongitude();
			//latlongString = "Latitude: "+ lat + "\nLongitude: " + lng;

			//Toast.makeText(this, "Updated Location Found",Toast.LENGTH_SHORT).show();
		}
		else
		{
			//latlongString = "No Location Found";
			Toast.makeText(this, "No Location Found",Toast.LENGTH_SHORT).show();
		}

	}

	private void distanceCalculation() {
		//equation referenced from http://andrew.hedges.name/experiments/haversine/
		double dlon = lng - startLng;
		double dlat = lat - startLat; 
		double a = ((Math.sin(dlat/2))*Math.sin(dlat/2)) + Math.cos(startLat)* Math.cos(lat) * ((Math.sin(dlon/2))*(Math.sin(dlon/2))); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double R = 6373;
		//R = radius of the earth in km
		distance = R * c;
		//distance is rounded to prevent large numbers distorting popup screen layout
		rndDistance = Math.round(distance);
		//rounded distance is passed to methed which will update the textview
		updateTextView(rndDistance);

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
	@Override
	public void onClick(View v) {
		/** Parameters for setVisibility
		0 is for VISIBLE
		4 is for INVISIBLE 
		8 is for GONE 
		 */
		int parPos;
		int[] holeArray = {hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18};

		TextView holeNum = (TextView)findViewById(R.id.txtHoleNum);
		TextView holePar = (TextView)findViewById(R.id.txtPar);
		CheckBox chkGIR = (CheckBox)findViewById(R.id.checkBoxGIR);

		Button btnBack = (Button)findViewById(R.id.btnBackHole);
		Button btnHideNext = (Button)findViewById(R.id.btnNextHole);
		Button btnHideFinish = (Button)findViewById(R.id.btnFinish);
		Button btnScoreCard = (Button)findViewById(R.id.scoreCardBtn);

		int userScore, guest1Score, guest2Score, guest3Score, userRecovery, userPutts;
		boolean userGIR;

		userScore = getSpinnerShots();
		guest1Score = getSpinnerGuest1Shots();
		guest2Score = getSpinnerGuest2Shots(); 
		guest3Score = getSpinnerGuest3Shots(); 
		userGIR = chkGIR.isChecked();
		userRecovery = getSpinnerUserRecovery(); 
		int penaltyValue = userRecovery*2;
		userPutts = getSpinnerUserPutt();

		switch(v.getId()){
		case R.id.btnBackHole:
		{
			holeNo--;

			if(holeNo <=1)
			{
				btnBack.setClickable(false);
				btnScoreCard.setClickable(false);
			}

			//Toast.makeText(Hole.this, "Back " +holeNo, Toast.LENGTH_SHORT).show();
			holeNum.setText("Hole: " + holeNo);	
			/**try
			{
				parPos = holeNo-1;
				holeP = holeArray[0];
				holePar.setText("Par: " + holeP);
			}
			catch (Exception exPar)
			{
				Toast.makeText(Hole.this, "Error", Toast.LENGTH_SHORT).show();
			}*/
			if(holeNo <=17) 
			{
				//Show btnNext and hide btnFinish
				btnHideNext.setVisibility(0);
				btnHideFinish.setVisibility(8);
			}
			else 
			{
			}
			holeClear();
			//TODO create a fill data method to fill in data that was entered on previous hole FillData();

		}
		break;
		case R.id.btnReset:
		{
			holeClear();
			//Toast.makeText(Hole.this, "Hole Data Cleared", Toast.LENGTH_SHORT).show();
		}
		break;
		case R.id.btnNextHole:
		{
			holeNo++;
			//Toast.makeText(Hole.this, "Next " +holeNo, Toast.LENGTH_SHORT).show();
			holeNum.setText("Hole: " + holeNo);

			//reset user scores
			userFrontNine =0; 
			userBackNine =0; 
			g1FrontNine =0;
			g1BackNine =0;
			g2FrontNine =0;
			g2BackNine =0;
			g3FrontNine =0;
			g3BackNine =0;
			parTotal =0;

			playHole();

			//create shared pref for each array that will be saved everytime the next button is pressed
			//Create in seperate method so that it can be called when the finish button is also pressed
			//Check if hole has already been played
			SharedPreferences defaultscores = getSharedPreferences("myPrefs", MODE_PRIVATE);
			SharedPreferences.Editor prefScoreEditor = defaultscores.edit();
			prefScoreEditor.putInt("parPlayedTotal", 0);
			prefScoreEditor.commit();
			for (int n =1; n<holeNo;n++)
			{
				SharedPreferences scores = getSharedPreferences("myPrefs", MODE_PRIVATE);
				SharedPreferences.Editor prefEditor = scores.edit();
				parTotal = parTotal +holeP;
				prefEditor.putInt("parPlayedTotal", parTotal);
				prefEditor.commit();
			}
			saveScore();
		}
		break;
		case R.id.btnFinish:
		{
			//save everything from tempStats Table into Stats Table
			int finalParTotal = 0;
			int user18Score =0;
			int guest118Score =0;
			int guest218Score =0;
			int guest318Score =0;
			try
			{
				Toast.makeText(Hole.this, "Game Finished\nStatistics Saved", Toast.LENGTH_SHORT).show();

				db.open();
				//loop through all 18 holes of game
				//pass in parameters to arrays relating to hole number
				for (int i = 0; i<18; i++)
				{
					int n =i+1;
					//Toast.makeText(Hole.this, "Hole "+i+" Values Saved", Toast.LENGTH_SHORT).show();
					db.insertStat(coursePosition, TempHoleNo[n], TempHolePar[i], TempUserScore[i], TempGuest1Score[i], TempGuest2Score[i], TempGuest3Score[i], TempUserGIR[i], TempUserRecovery[i], TempUserPutts[i]);
					//finalParTotal = finalParTotal+TempHolePar[i];
				}
				db.close();
			}
			catch (Exception exTempData)
			{
				Toast.makeText(Hole.this, "Failed to save game data", Toast.LENGTH_SHORT).show();
			}

			SharedPreferences scores = getSharedPreferences("myPrefs", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = scores.edit();
			prefEditor.putInt("parPlayedTotal", finalParTotal);
			prefEditor.putInt("user18th", user18Score);
			prefEditor.putInt("guest118th", guest118Score);
			prefEditor.putInt("guest218th", guest218Score);
			prefEditor.putInt("guest318th", guest318Score);

			prefEditor.commit();

			Intent endIntent = new Intent(this, EndRound.class);
			startActivity(endIntent);
		}
		break;
		case R.id.btnStartTracking:
		{	
			initiatePopupWindow();
		}
		break;
		case R.id.infoHole:
		{	
			initiateInfoWindow();
		}
		break;
		case R.id.scoreCardBtn:
		{
			Intent scoreIntent = new Intent(this, ScoreCard.class);
			startActivity(scoreIntent);

			saveDatatoSharedPref();
		}
		break;
		}
		parPos = holeNo-1;
		holeP = holeArray[parPos];
		holePar.setText("Par: " + holeP);

		//set spinner value for user to par	
		Spinner spnUserShots = (Spinner) findViewById(R.id.spinnerShots);
		Spinner spnGuest1Shots = (Spinner) findViewById(R.id.spinnerGuest1Shots);
		Spinner spnGuest2Shots = (Spinner) findViewById(R.id.spinnerGuest2Shots);
		Spinner spnGuest3Shots = (Spinner) findViewById(R.id.spinnerGuest3Shots);
		//set the default according to value
		spnUserShots.setSelection(holeP);
		spnGuest1Shots.setSelection(holeP);
		spnGuest2Shots.setSelection(holeP);
		spnGuest3Shots.setSelection(holeP);
	}

	private void playHole() {
		int parPos;
		int[] holeArray = {hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9, hole10, hole11, hole12, hole13, hole14, hole15, hole16, hole17, hole18};

		TextView holeNum = (TextView)findViewById(R.id.txtHoleNum);
		TextView holePar = (TextView)findViewById(R.id.txtPar);
		CheckBox chkGIR = (CheckBox)findViewById(R.id.checkBoxGIR);

		Button btnBack = (Button)findViewById(R.id.btnBackHole);
		Button btnHideNext = (Button)findViewById(R.id.btnNextHole);
		Button btnHideFinish = (Button)findViewById(R.id.btnFinish);
		Button btnScoreCard = (Button)findViewById(R.id.scoreCardBtn);

		int userScore, guest1Score, guest2Score, guest3Score, userRecovery, userPutts;
		boolean userGIR;

		userScore = getSpinnerShots();
		guest1Score = getSpinnerGuest1Shots();
		guest2Score = getSpinnerGuest2Shots(); 
		guest3Score = getSpinnerGuest3Shots(); 
		userGIR = chkGIR.isChecked();
		userRecovery = getSpinnerUserRecovery(); 
		int penaltyValue = userRecovery*2;
		userPutts = getSpinnerUserPutt();

		if(holeNo >17) 
		{
			//Hide btnNext and show btnFinish
			btnHideNext.setVisibility(8);
			btnHideFinish.setVisibility(0);
		}
		else if(holeNo >=2)
		{

			btnBack.setClickable(true);
			btnScoreCard.setClickable(true);
		}
		else 
		{
		}
		int arrayPos = holeNo-2;
		int holeN = holeNo-1;
		int TempPar = holeArray[arrayPos];

		TempHoleNo[arrayPos] = holeN;
		TempHolePar[arrayPos] = TempPar;
		TempUserScore[arrayPos] = userScore;
		TempGuest1Score[arrayPos] = guest1Score;
		TempGuest2Score[arrayPos] = guest2Score;
		TempGuest3Score[arrayPos] = guest3Score;
		TempUserGIR[arrayPos] = userGIR;
		TempUserRecovery[arrayPos] = penaltyValue;
		TempUserPutts[arrayPos] = userPutts;

		//saveScore();
		holeClear();
	}


	private void saveScore() {
		for(int i = 0;i< TempUserScore.length; i++){
			{
				SharedPreferences scores = getSharedPreferences("myPrefs", MODE_PRIVATE);
				SharedPreferences.Editor prefEditor = scores.edit();
				String check = scores.getString("userHoleScoreArray", null);
				if (check!=null)
				{
					prefEditor.putString("userHoleScoreArray", null);
					prefEditor.commit();
				}
				//serialise int arrays to string for holes completed
				//parArray += holeArray+",";
				int userHoleScore = TempUserScore[i];
				int guest1HoleScore = TempGuest1Score[i];
				int guest2HoleScore = TempGuest2Score[i];
				int guest3HoleScore = TempGuest3Score[i];
				//boolean userHoleGIR = TempUserGIR[i];
				int userHoleRecovery = TempUserRecovery[i];
				int userHolePutts = TempUserPutts[i];

				if (i<9)
				{
					userFrontNine = userFrontNine +userHoleScore;
					g1FrontNine = g1FrontNine + guest1HoleScore;
					g2FrontNine = g2FrontNine + guest2HoleScore;
					g3FrontNine = g3FrontNine + guest3HoleScore;
				}
				else if (i>=9 && i<18)
				{
					userBackNine = userBackNine +userHoleScore;
					g1BackNine = g1BackNine + guest1HoleScore;
					g2BackNine = g2BackNine + guest2HoleScore;
					g3BackNine = g3BackNine + guest3HoleScore;
				}
				else
				{
					Toast.makeText(Hole.this, "Score Error", Toast.LENGTH_SHORT).show();
				}

				//Toast.makeText(Hole.this, "Check User Score\n"+userHoleScore, Toast.LENGTH_SHORT).show();

				String strUserHoleScore = Integer.toString(userHoleScore)+",";
				String strGuest1HoleScore = Integer.toString(guest1HoleScore)+",";
				String strGuest2HoleScore = Integer.toString(guest2HoleScore)+",";
				String strGuest3HoleScore = Integer.toString(guest3HoleScore)+",";
				//String strUserHoleGIR = Boolean.toString(userHoleGIR)+",";
				String strUserHoleRecovery = Integer.toString(userHoleRecovery)+",";
				String strUserHolePutts = Integer.toString(userHolePutts)+",";

				userHoleScoreArray = userHoleScoreArray+strUserHoleScore;
				guest1HoleScoreArray = guest1HoleScoreArray+strGuest1HoleScore;
				guest2HoleScoreArray = guest2HoleScoreArray+strGuest2HoleScore;
				guest3HoleScoreArray= guest3HoleScoreArray+strGuest3HoleScore;
				//userHoleGIRArray= userHoleGIRArray+strUserHoleGIR;
				userHoleRecoveryArray= userHoleRecoveryArray+strUserHoleRecovery;
				userHolePuttsArray=userHolePuttsArray+strUserHolePutts;

				//Store hole par values to shared pref
				//String shortUserScore = userHoleScoreArray.substring(userHoleScoreArray.length()-36);

				prefEditor.putString("userHoleScoreArray", userHoleScoreArray);
				prefEditor.putString("guest1HoleScoreArray", guest1HoleScoreArray);
				prefEditor.putString("guest2HoleScoreArray", guest2HoleScoreArray);
				prefEditor.putString("guest3HoleScoreArray", guest3HoleScoreArray);
				//prefEditor.putString("userHoleGIRArray", userHoleGIRArray);
				prefEditor.putString("userHoleRecoveryArray", userHoleRecoveryArray);
				prefEditor.putString("userHolePuttsArray", userHolePuttsArray);
				//Total Scores
				prefEditor.putInt("UserFrontTotal", userFrontNine);
				prefEditor.putInt("UserBackTotal", userBackNine);
				prefEditor.putInt("g1FrontTotal", g1FrontNine);
				prefEditor.putInt("g1BackTotal", g1BackNine);
				prefEditor.putInt("g2FrontTotal", g2FrontNine);
				prefEditor.putInt("g2BackTotal", g2BackNine);
				prefEditor.putInt("g3FrontTotal", g3FrontNine);
				prefEditor.putInt("g3BackTotal", g3BackNine);
				prefEditor.commit();
			}
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
			//Set text for popup userguide
			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("Please enter any information using the spinners provided\n\n" +
					"To track a shot press the track button in the middle of the screen." +
					"\n- Start - to begin tracking.\n- Cancel - to stop tracking the distance" +
					" of a shot without saving it to the statistics.\n Stop - to save a shot " +
					"to the application.\n\nUse buttons at the bottom of the screen to move between holes.");
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
	private void saveDatatoSharedPref() {
		//Store hole par values to shared pref
		SharedPreferences scores = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = scores.edit();

		String shortUserScore = userHoleScoreArray.substring(userHoleScoreArray.length()-36);
		String shortGuest1Score = guest1HoleScoreArray.substring(guest1HoleScoreArray.length()-36);
		String shortGuest2Score = guest2HoleScoreArray.substring(guest2HoleScoreArray.length()-36);
		String shortGuest3Score = guest3HoleScoreArray.substring(guest3HoleScoreArray.length()-36);
		//String shortUserGIR = userHoleGIRArray.substring(userHoleGIRArray.length()-36);
		String shortUserRecovery = userHoleRecoveryArray.substring(userHoleRecoveryArray.length()-36);
		String shortUserPutts = userHolePuttsArray.substring(userHolePuttsArray.length()-36);

		prefEditor.putString("userHoleScoreArray", shortUserScore);
		prefEditor.putString("guest1HoleScoreArray", shortGuest1Score);
		prefEditor.putString("guest2HoleScoreArray", shortGuest2Score);
		prefEditor.putString("guest3HoleScoreArray", shortGuest3Score);
		//prefEditor.putString("userHoleGIRArray", shortUserGIR);
		prefEditor.putString("userHoleRecoveryArray", shortUserRecovery);
		prefEditor.putString("userHolePuttsArray", shortUserPutts);
		prefEditor.commit();
	}
	private void SavePosition(Cursor c) {
		holePosition= c.getInt(0);
	}

	private void holeClear() {
		//reset all data entered for the current hole
		//Toast.makeText(Hole.this, "Hole Data Cleared", Toast.LENGTH_SHORT).show();
		//Create spinners
		Spinner spnUserShots = (Spinner) findViewById(R.id.spinnerShots);
		Spinner spnGuest1Shots = (Spinner) findViewById(R.id.spinnerGuest1Shots);
		Spinner spnGuest2Shots = (Spinner) findViewById(R.id.spinnerGuest2Shots);
		Spinner spnGuest3Shots = (Spinner) findViewById(R.id.spinnerGuest3Shots);
		Spinner spnRecovery = (Spinner) findViewById(R.id.spinnerPenalty);
		Spinner spnPutts = (Spinner) findViewById(R.id.spinnerPutts);

		spnUserShots.setSelection(0);
		spnGuest1Shots.setSelection(0);
		spnGuest2Shots.setSelection(0);
		spnGuest3Shots.setSelection(0); 
		spnRecovery.setSelection(0);
		spnPutts.setSelection(0);

		CheckBox chkGIR = (CheckBox)findViewById(R.id.checkBoxGIR);
		chkGIR.setChecked(false);
	}
	private void clearTable()
	{

		//db.deleteAllTempStats();
		Toast.makeText(Hole.this, "Previous Table Cleared", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		int idSpinner = parent.getId();

		String strSpinnerValue = parent.getItemAtPosition(pos).toString();
		int spinnerValue = Integer.parseInt(strSpinnerValue);
		//the following will create a sub array using the String score array stored in the string.xml file
		//the size of the sub array will be dependent on the selection made by the user 
		switch (idSpinner) {
		case R.id.spinnerShots:
			setSpinnerShots(spinnerValue);
			Spinner spnPutts = (Spinner) findViewById(R.id.spinnerPutts);
			List<String> s = Arrays.asList(getResources().getStringArray(R.array.scores));
			if (pos == 10) {
				ArrayAdapter<CharSequence> puttAdapter = ArrayAdapter.createFromResource(Hole.this,
						R.array.scores, android.R.layout.simple_spinner_item);
				spnPutts.setAdapter(puttAdapter);
			} else if (pos == 9) {
				s = s.subList(0,9);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 8) {
				s = s.subList(0,8);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 7) {
				s = s.subList(0,7);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 6) {
				s = s.subList(0,6);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 5) {
				s = s.subList(0,5);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 4) {
				s = s.subList(0,4);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 3) {
				s = s.subList(0,3);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else if (pos == 2) {
				s = s.subList(0,2);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}
			else {
				s = s.subList(0,9);                    
				ArrayAdapter<String> puttAdapter = new  ArrayAdapter<String>(Hole.this, android.R.layout.simple_spinner_item,s);
				spnPutts.setAdapter(puttAdapter);
			}

			break;
		case R.id.spinnerGuest1Shots:
			setSpinnerGuest1Shots(spinnerValue);
			break;
		case R.id.spinnerGuest2Shots:
			setSpinnerGuest2Shots(spinnerValue);
			break;
		case R.id.spinnerGuest3Shots:
			setSpinnerGuest3Shots(spinnerValue);
			break;
		case R.id.spinnerPenalty:
			setSpinnerUserRecovery(spinnerValue);
			break;
		case R.id.spinnerPutts:
			setSpinnerUserPutt(spinnerValue);
			break;
		}
		//Set GIR to true or false based on spinner values
		checkGIR(holeP, getSpinnerShots(), getSpinnerUserPutt(), getSpinnerUserRecovery());
		//Toast.makeText(Hole.this, "Checking GIR", Toast.LENGTH_SHORT).show();
	}

	private ArrayAdapter<CharSequence> ArrayAdapter(Hole hole,
			String[] updatedScores, int simpleSpinnerItem) {
		// Auto-generated method stub
		return null;
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//Auto-generated method stub
	}
	public int getSpinnerShots() {
		return UserShotScore;
	}
	public void setSpinnerShots(int spin){
		UserShotScore = spin;
	}
	public int getSpinnerGuest1Shots() {
		return Guest1ShotScore;
	}
	public void setSpinnerGuest1Shots(int spin){
		Guest1ShotScore = spin;
	}
	public int getSpinnerGuest2Shots() {
		return Guest2ShotScore;
	}
	public void setSpinnerGuest2Shots(int spin){
		Guest2ShotScore = spin;
	}
	public int getSpinnerGuest3Shots() {
		return Guest3ShotScore;
	}
	public void setSpinnerGuest3Shots(int spin){
		Guest3ShotScore = spin;
	}
	public int getSpinnerUserRecovery() {
		return UserRecovery;
	}
	public void setSpinnerUserRecovery(int spin){
		UserRecovery = spin;
	}
	public int getSpinnerUserPutt() {
		return UserPutt;
	}
	public void setSpinnerUserPutt(int spin){
		UserPutt = spin;
	}
	//Set GIR
	public void checkGIR(int par, int shots, int putts, int pen){
		CheckBox chkGIR = (CheckBox)findViewById(R.id.checkBoxGIR);
		//shots variable used to create an easier to read if statement
		//allows for GIR checkbox to be set automatically, usability requirement from customer duroing last meeting
		int shot = shots-putts+pen;
		int gir = par-2;
		if (shot<=gir)
		{
			chkGIR.setChecked(true);
		}
		else
		{
			chkGIR.setChecked(false);
		}
	}
	private void initiatePopupWindow() {
		try {
			//create shot tracking popup window
			LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.trackshot,  (ViewGroup) findViewById(R.id.popup_element));

			//ConfirmActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//Inflate the view from a predefined XML layout

			// create a 350px width and 500px height PopupWindow
			pw = new PopupWindow(layout, 350, 500, true);
			// display the popup in the center
			pw.showAtLocation(layout, Gravity.CENTER, 0, 0);


			//TextView trackDistance = ((TextView)pw.getContentView().findViewById(R.id.txtTrackDistance));
			Button startButton = (Button) layout.findViewById(R.id.btnTrackStart);
			Button stopButton = (Button) layout.findViewById(R.id.btnTrackStop);
			Button cancelButton = (Button) layout.findViewById(R.id.btnCancelTrack);

			//buttons within inflated window require seperate onclick listener (this) can not be used as it can not be accessed by the system
			cancelButton.setOnClickListener(shotTracking);
			startButton.setOnClickListener(shotTracking);
			stopButton.setOnClickListener(shotTracking);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void updateTextView(double distance) {
		LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = layoutInflater.inflate(R.layout.trackshot,  (ViewGroup) findViewById(R.id.popup_element));

		TextView trackDistance = ((TextView)pw.getContentView().findViewById(R.id.txtTrackDistance));

		trackDistance.setText("Distance: \n"+ distance+"m");


	}
	private OnClickListener shotTracking = new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnTrackStop:
				Toast.makeText(Hole.this, "Saving Distance", Toast.LENGTH_SHORT).show();
				endLat = lat;
				endLng = lng;
				stopLocationManager();
				//save finalDistance to database 
				int finalDistance = (int)Math.round(rndDistance);
				db.open();
				db.insertTrack(coursePosition, holeNo, finalDistance);
				db.close();
				Toast.makeText(Hole.this, "Saved "+finalDistance+" into Stats", Toast.LENGTH_SHORT).show();
				pw.dismiss();
				break;
			case R.id.btnTrackStart:
				Toast.makeText(Hole.this, "Starting Shot Tracking", Toast.LENGTH_SHORT).show();
				startLat = lat;
				startLng = lng;
				break;
			case R.id.btnCancelTrack:
				//stop the location manager from running (to prevent battery drain)
				//cancel button added to allow user to close shot tracking without saving to db
				stopLocationManager();
				Toast.makeText(Hole.this, "Shot Tracking Canceled No Data Saved", Toast.LENGTH_SHORT).show();
				pw.dismiss();
				break;
			}
		}
	};

	private void clearStatsTable()
	{
		//db.deleteCourse(tblStats, null, null);
	}
}
