package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends Activity  implements View.OnClickListener{
	boolean bolRecovery,bolGIR,bolPutt;
	String userName, userContact;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.settings);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//Shared pref
		SharedPreferences settings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();
		
		ToggleButton togTrackGIR = (ToggleButton)findViewById(R.id.btnTrackGIR);
		togTrackGIR.setOnClickListener(this);
		ToggleButton togTrackRecovery = (ToggleButton)findViewById(R.id.btnTrackRecovery);
		togTrackRecovery.setOnClickListener(this);
		ToggleButton togTrackPutts = (ToggleButton)findViewById(R.id.btnTrackPutts);
		togTrackPutts.setOnClickListener(this);
		Button btnClearFav = (Button)findViewById(R.id.btnClearFavourites);
		btnClearFav.setOnClickListener(this);
		Button btnClearStats = (Button)findViewById(R.id.btnClearStats);
		btnClearStats.setOnClickListener(this);
		Button btnClearShotStats = (Button)findViewById(R.id.btnClearShotStats);
		btnClearShotStats.setOnClickListener(this);
		Button btnClearRecent = (Button)findViewById(R.id.btnClearRecent);
		btnClearRecent.setOnClickListener(this);
		Button btnSave = (Button)findViewById(R.id.btnSaveSettings);
		btnSave.setOnClickListener(this);
		
		EditText editUserName = (EditText)findViewById(R.id.editUserNameText);
		EditText editUserContact = (EditText)findViewById(R.id.editUserEmailText);
		
		//Get current settings from shared pref
		//Shared Preferences
		userName = settings.getString("UserName", null);
		userContact = settings.getString("UserContact", null);
		bolRecovery = settings.getBoolean("RecoverySetting", true);
		bolGIR = settings.getBoolean("GIRSetting", true);
		bolPutt = settings.getBoolean("PuttSetting", true);
		//depending on current settings set the position of the check boxes on the UI
		if (userName !=null)
		{
			editUserName.setText(userName);
		}
		if (userContact !=null)
		{
			editUserContact.setText(userContact);
		}
		
		if (bolRecovery ==true)
		{
			togTrackRecovery.setChecked(true);
		}
		else
		{
			togTrackRecovery.setChecked(false);
		}	
		if (bolGIR ==true)
		{
			togTrackGIR.setChecked(true);
		}
		else
		{
			togTrackGIR.setChecked(false);
		}
		if (bolPutt ==true)
		{
			togTrackPutts.setChecked(true);
		}
		else
		{
			togTrackGIR.setChecked(false);
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
	@Override
	public void onClick(View v) {
		ToggleButton togTrackRecovery = (ToggleButton)findViewById(R.id.btnTrackRecovery);
		ToggleButton togTrackPutts = (ToggleButton)findViewById(R.id.btnTrackPutts);
		ToggleButton togTrackGIR = (ToggleButton)findViewById(R.id.btnTrackGIR);
		switch (v.getId())
		{
		case R.id.btnTrackGIR:
			if (bolGIR ==true)
			{
				bolGIR = false;
				togTrackGIR.setChecked(false);
			}
			else
			{
				bolGIR = true;
				togTrackGIR.setChecked(true);
			}
			break;
		case R.id.btnTrackRecovery:
			if (bolRecovery ==true)
			{
				bolRecovery = false;
				//Toast.makeText(Settings.this, "bolRecovery True Case SET= "+bolRecovery, Toast.LENGTH_SHORT).show();
				togTrackRecovery.setChecked(false);
			}
			else
			{
				//Toast.makeText(Settings.this, "bolRecovery else Case = "+bolRecovery, Toast.LENGTH_SHORT).show();
				bolRecovery = true;
				togTrackRecovery.setChecked(true);
			}
			break;
		case R.id.btnTrackPutts:
			if (bolPutt ==true)
			{
				bolPutt = false;
				togTrackPutts.setChecked(false);
			}
			else
			{
				bolPutt = true;
				togTrackPutts.setChecked(true);
			}
			break;
		case R.id.btnClearFavourites:
			clear(1);
			break;
		case R.id.btnClearRecent:
			clear(2);
			break;
		case R.id.btnClearStats:
			clear(3);
			break;
		case R.id.btnClearShotStats:
			clear(4);
			break;
		case R.id.btnSaveSettings:
			saveSettings();
		}
	}
	private void clear(int i) {
		// Depending on where the method was called a different parameter will be passed
		//this will enable the method to know which if statement to run 
		SharedPreferences stored = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = stored.edit();
		if (i==1)
		{
			Toast.makeText(Settings.this, "Favourites Cleared", Toast.LENGTH_SHORT).show();
			String updatedFavourites =  "";
			prefEditor.putString("favouritedCourses", updatedFavourites);
		}
		else if (i==2)
		{
			Toast.makeText(Settings.this, "Recent Courses Cleared", Toast.LENGTH_SHORT).show();
			String updatedRecents =  "";
			prefEditor.putString("recentlyPlayed", updatedRecents);
		}
		else if (i==3)
		{
			Toast.makeText(Settings.this, "Statistics Cleared", Toast.LENGTH_SHORT).show();
			final DBAdapter db = new DBAdapter(this);
			db.open();
			db.clearStatTable();
			db.close();
		}
		else if (i==4)
		{
			Toast.makeText(Settings.this, "Shot Tracking Records Cleared", Toast.LENGTH_SHORT).show();
			final DBAdapter db = new DBAdapter(this);
			db.open();
			db.clearShotTable();
			db.close();
		}
		else
		{
			Toast.makeText(Settings.this, "Error", Toast.LENGTH_SHORT).show();
		}
		prefEditor.commit();
	}
	private void saveSettings() {
		Toast.makeText(Settings.this, "Settings Saved", Toast.LENGTH_SHORT).show();
		
		EditText editUserName = (EditText)findViewById(R.id.editUserNameText);
		EditText editUserContact = (EditText)findViewById(R.id.editUserEmailText);
		userName = editUserName.getText().toString();
		userContact = editUserContact.getText().toString();
		
		//Shared pref
		//save all settings to the shared preferences when button is pressed
		SharedPreferences settings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();
		prefEditor.putString("UserName", userName);
		prefEditor.putString("UserContact", userContact);
		prefEditor.putBoolean("PuttSetting", bolPutt);
		prefEditor.putBoolean("RecoverySetting", bolRecovery);
		prefEditor.putBoolean("GIRSetting", bolGIR);
		prefEditor.commit();
		//fully close the settings on save 
		finish();
	}
}
