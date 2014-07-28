package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
//Will contain settings for the round
//ie. how many guests, shot tracking on/off etc..
//From CourseDetails
//To ScoreCard
public class CourseSettings extends Activity implements OnItemSelectedListener, View.OnClickListener{
	int spinnerValue =1;
	View layout;
	private PopupWindow pw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.coursesettings);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//instantiate and assign onclick listeners
		Spinner spnNoPlayers = (Spinner) findViewById(R.id.spnNoPlayers);
		Button btnAppSettings = (Button)findViewById(R.id.btnAppSettings);
		btnAppSettings.setOnClickListener(this);
		Button btnPlay = (Button)findViewById(R.id.btnStartNewGame);
		btnPlay.setOnClickListener(this);
		TextView txtUserName = (TextView)findViewById(R.id.txtUserName);
		TextView txtUserContact = (TextView)findViewById(R.id.txtUserContact);

		//Shared Preferences
		SharedPreferences settings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		String userName = settings.getString("UserName", null);
		String userContact = settings.getString("UserContact", null);

		//Using preferences recovered set users details to the screen
		txtUserName.setText(userName);
		txtUserContact.setText(userContact);

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.players, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spnNoPlayers.setAdapter(adapter);
		spnNoPlayers.setOnItemSelectedListener(this);

		spinnerValue= getSpinnerValue();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}
//menu bar onclick events
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
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		int idSpinner = parent.getId();

		String strPlayers = parent.getItemAtPosition(pos).toString();
		int spinnerValue = Integer.parseInt(strPlayers);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoCSettings);
		infoBtn.setOnClickListener(this);

		//Set no of players for game to shared pref
		//Set shared pref
		SharedPreferences courseSettings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = courseSettings.edit();
		//Store course position to shared pref
		prefEditor.putInt("NoOfPlayers", spinnerValue);
		prefEditor.commit();

		switch (idSpinner) {
		case R.id.spnNoPlayers:

			setSpinnerValue(spinnerValue);
			break;
		}
		try{
			/**
			0 is for VISIBLE
			4 is for INVISIBLE 
			8 is for GONE 
			 */
			//define variables for the entire table row
			TableRow row1 = (TableRow)findViewById(R.id.tblSetRow1);
			TableRow row2 = (TableRow)findViewById(R.id.tblSetRow2);
			TableRow row3 = (TableRow)findViewById(R.id.tblSetRow3);
			TableRow row4 = (TableRow)findViewById(R.id.tblSetRow4);
			//depending on nyumber of users selects hide or show correct number of rows
			int tableSettings = getSpinnerValue();
			if (tableSettings ==1)
			{
				row1.setVisibility(0);
				row2.setVisibility(8);
				row3.setVisibility(8);
				row4.setVisibility(8);
			}
			else if (tableSettings ==2)
			{
				row1.setVisibility(0);
				row2.setVisibility(0);
				row3.setVisibility(8);
				row4.setVisibility(8);
			}
			else if (tableSettings ==3)
			{
				row1.setVisibility(0);
				row2.setVisibility(0);
				row3.setVisibility(0);
				row4.setVisibility(8);
			}
			else
			{
				row1.setVisibility(0);
				row2.setVisibility(0);
				row3.setVisibility(0);
				row4.setVisibility(0);
			}
		}
		catch (Exception ex)
		{
			Toast.makeText(CourseSettings.this, "Adding Player Error", Toast.LENGTH_SHORT).show();
		}
	}

	public int getSpinnerValue() {
		return spinnerValue;
	}
	public void setSpinnerValue(int spin){
		spinnerValue = spin;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){

		case R.id.btnAppSettings:
		{
			// go to the application's settings page
			Intent settingsIntent = new Intent(this, Settings.class);
			startActivity(settingsIntent);
		}
		break;
		case R.id.infoCSettings:
		{	
			//inflate the information window
			initiateInfoWindow();
		}
		break;
		case R.id.btnStartNewGame:
		{
			//Need to save course position to recent shared preferences
			//Set shared pref
			SharedPreferences recentPlayed = getSharedPreferences("myPrefs", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = recentPlayed.edit();
			//Store course position to shared pref
			int coursePos = recentPlayed.getInt("coursePosition", 1);

			//recover all recents from shared pref
			String recentlyPlayed = recentPlayed.getString("recentlyPlayed", "");
			String updatedRecents =  recentlyPlayed+coursePos+",";
			prefEditor.putString("recentlyPlayed", updatedRecents);
			//split all recent String into array
			//String [] recentArray = recentlyPlayed.split(",");
			//Put code to sort array if over X in size, remove 0, move all x--, add new to position X
			EditText guest1 = (EditText)findViewById(R.id.edtGuestContact1);
			EditText guest2 = (EditText)findViewById(R.id.edtGuestContact2);
			EditText guest3 = (EditText)findViewById(R.id.edtGuestContact3);
			int numPlayers = recentPlayed.getInt("NoOfPlayers", 4 );
			String guest1Details, guest2Details, guest3Details = "";

			//guest details for all 3 possible guests are stored at the beginning of a game
			//depending on number of users playing the information stored may contain the data recieved from the EditTexts or be set to blank
			//The details for all 3 guests must be set to ensure that details for guests from previous games are not still saved in the shared preferences.
			if (numPlayers == 1)
			{
				guest1Details = "";
				guest2Details = guest1Details;
				guest3Details = guest1Details;
			}
			else if (numPlayers ==2)
			{
				guest1Details = guest1.getText().toString();
				guest2Details = "";
				guest3Details = guest2Details;
			}
			else if (numPlayers ==3)
			{
				guest1Details = guest1.getText().toString();
				guest2Details = guest2.getText().toString();
				guest3Details = "";
			}
			else if (numPlayers ==4)
			{
				guest1Details = guest1.getText().toString();
				guest2Details = guest2.getText().toString();
				guest3Details = guest3.getText().toString();
			}
			else
			{
				guest1Details = "";
				guest2Details = "";
				guest3Details = "";
			}

			prefEditor.putString("guest1Contact", guest1Details);
			prefEditor.putString("guest2Contact", guest2Details);
			prefEditor.putString("guest3Contact", guest3Details);
			prefEditor.commit();

			//Toast.makeText(CourseSettings.this, "Starting Game", Toast.LENGTH_SHORT).show();
			Intent playIntent = new Intent(this, Hole.class);
			startActivity(playIntent);
		}
		break;
		}
	}
	private void initiateInfoWindow() {
		try {

			//Instance of the LayoutInflater
			LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout = layoutInflater.inflate(R.layout.info,  (ViewGroup) findViewById(R.id.popup_info));
			//Inflate the view from a predefined XML layout
			// create a 350px width and 500px height PopupWindow
			pw = new PopupWindow(layout, 350, 500, true);
			// display the popup in the center
			pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
//set text to be displayed within the popup
			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("Select number of additional users playing the game.\nPlease also enter email addresses for the additional users so the scores may be shared when the game is complete\n\nApplication settings can be accessed, to personalise the UI while playing a game.\n\nPress the play button to confrim these settings and begin a game.");

			Button cancelButton = (Button) layout.findViewById(R.id.btnCancelInfo);
			cancelButton.setOnClickListener(infoClose);
		} 
		catch (Exception e) {
		}
	}
	//onclick listener to dismiss (close) the pop up window 
	private OnClickListener infoClose = new OnClickListener() {
		public void onClick(View v) {
			pw.dismiss();
		}
	};
}

