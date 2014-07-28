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
import android.widget.TableRow;
import android.widget.TextView;
//From ScoreCard 
//To MainMenu
public class EndRound extends Activity implements View.OnClickListener {
	EditText address, subject, emailtext;
	String g1Email, g2Email, g3Email, userEmail, emailContacts;
	String emailMessage = "Name: \t Course Par: \t User Shots: \t Score: \n";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.endround);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Button send=(Button) findViewById(R.id.emailsendbutton);
		send.setOnClickListener(this);
		Button finish =(Button) findViewById(R.id.emailfinishbutton);
		finish.setOnClickListener(this);
		address=(EditText) findViewById(R.id.emailAddressEdit);
		subject=(EditText) findViewById(R.id.emailSubjectEdit);
		emailtext=(EditText) findViewById(R.id.emailtext);

		// Retrieve Shared Pref
		SharedPreferences courseSettings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		int noPlayers = courseSettings.getInt("NoOfPlayers", 1);
		String userName = courseSettings.getString("UserName", null);
		g1Email = courseSettings.getString("guest1Contact", null);
		g2Email = courseSettings.getString("guest2Contact", null);
		g3Email = courseSettings.getString("guest3Contact", null);
		userEmail = courseSettings.getString("UserContact", null);
		//Hide columns and rows of tables according to number of players
		try{
			/**
						0 is for VISIBLE
						4 is for INVISIBLE 
						8 is for GONE 
			 */
			TableRow row1 = (TableRow)findViewById(R.id.endGuest1Row);
			TableRow row2 = (TableRow)findViewById(R.id.endGuest2Row);
			TableRow row3 = (TableRow)findViewById(R.id.endGuest3Row);

			//display appropriate rows of the score table depending on number of players
			int tableSettings = noPlayers;
			if (tableSettings ==1)
			{
				//visibility 8 = gone
				// 0 = visible
				//gone removes all reference to the widget from the layout, 
				//means the invisible widget no longer takes up or requires layout space
				row1.setVisibility(8);
				row2.setVisibility(8);
				row3.setVisibility(8);
			}
			else if (tableSettings ==2)
			{
				row1.setVisibility(0);
				row2.setVisibility(8);
				row3.setVisibility(8);
			}
			else if (tableSettings ==3)
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
		catch (Exception ex)
		{
		}

		//Table1 TextViews
		TextView endUserRowName = (TextView)findViewById(R.id.endUserRowName);
		TextView endUserRowCourse = (TextView)findViewById(R.id.endUserRowCourse);
		TextView endUserRowTotal = (TextView)findViewById(R.id.endUserRowTotal);
		TextView endUserRowScore = (TextView)findViewById(R.id.endUserRowScore);
		TextView endGuest1RowCourse = (TextView)findViewById(R.id.endGuest1RowCourse);
		TextView endGuest1RowTotal = (TextView)findViewById(R.id.endGuest1RowTotal);
		TextView endGuest1RowScore = (TextView)findViewById(R.id.endGuest1RowScore);
		TextView endGuest2RowCourse = (TextView)findViewById(R.id.endGuest2RowCourse);
		TextView endGuest2RowTotal = (TextView)findViewById(R.id.endGuest2RowTotal);
		TextView endGuest2RowScore = (TextView)findViewById(R.id.endGuest2RowScore);
		TextView endGuest3RowCourse = (TextView)findViewById(R.id.endGuest3RowCourse);
		TextView endGuest3RowTotal = (TextView)findViewById(R.id.endGuest3RowTotal);
		TextView endGuest3RowScore = (TextView)findViewById(R.id.endGuest3RowScore);

		//User and guest scores
		int userFrontNine = courseSettings.getInt("UserFrontTotal", 0);
		int userBackNine = courseSettings.getInt("UserBackTotal", 0);
		int g1FrontNine = courseSettings.getInt("g1FrontTotal", 0);
		int g1BackNine = courseSettings.getInt("g1BackTotal", 0);
		int g2FrontNine = courseSettings.getInt("g2FrontTotal", 0);
		int g2BackNine = courseSettings.getInt("g2BackTotal", 0);
		int g3FrontNine = courseSettings.getInt("g3FrontTotal", 0);
		int g3BackNine = courseSettings.getInt("g3BackTotal", 0);
		int parPlayedTotal = courseSettings.getInt("currentCoursePar", 0);

		int userScoreTotal = userFrontNine + userBackNine;
		int g1ScoreTotal = g1FrontNine + g1BackNine;
		int g2ScoreTotal = g2FrontNine + g2BackNine;
		int g3ScoreTotal = g3FrontNine + g3BackNine;
				
		int g1Score = g1ScoreTotal-parPlayedTotal;
		int g2Score = g2ScoreTotal-parPlayedTotal;
		int g3Score = g3ScoreTotal-parPlayedTotal;
		int userScore = userScoreTotal-parPlayedTotal;
		
		try
		{
			//course par column
			endUserRowName.setText(userName);
			endUserRowCourse.setText(String.valueOf(parPlayedTotal));
			endGuest1RowCourse.setText(String.valueOf(parPlayedTotal));
			endGuest2RowCourse.setText(String.valueOf(parPlayedTotal));
			endGuest3RowCourse.setText(String.valueOf(parPlayedTotal));

			endUserRowTotal.setText(String.valueOf(userScoreTotal));
			endGuest1RowTotal.setText(String.valueOf(g1ScoreTotal));
			endGuest2RowTotal.setText(String.valueOf(g2ScoreTotal));
			endGuest3RowTotal.setText(String.valueOf(g3ScoreTotal));

			endUserRowScore.setText(String.valueOf(userScore));
			endGuest1RowScore.setText(String.valueOf(g1Score));
			endGuest2RowScore.setText(String.valueOf(g2Score));
			endGuest3RowScore.setText(String.valueOf(g3Score));
		}
		catch (Exception ex)
		{
		}
		//personalise email message body according to the number of users.
		if (noPlayers ==1)
		{
			emailMessage = emailMessage + userName +"\t" + parPlayedTotal + "\t" + userScoreTotal + "\t" + userScore + "\n";
			emailContacts = userEmail;
			address.setText(emailContacts);
			emailtext.setText(emailMessage);
		}
		else if (noPlayers ==2)
		{
			emailMessage = emailMessage + userName +"\t" + parPlayedTotal + "\t" + userScoreTotal + "\t" + userScore + "\n";
			emailMessage = emailMessage + "Guest 1 \t" + parPlayedTotal + "\t" + g1ScoreTotal + "\t" + g1Score + "\n";
			emailContacts = userEmail + ", " + g1Email;
			address.setText(emailContacts);
			emailtext.setText(emailMessage);
		}
		else if (noPlayers ==3)
		{
			emailMessage = emailMessage + userName +"\t" + parPlayedTotal + "\t" + userScoreTotal + "\t" + userScore + "\n";
			emailMessage = emailMessage + "Guest 1 \t" + parPlayedTotal + "\t" + g1ScoreTotal + "\t" + g1Score + "\n";
			emailMessage = emailMessage + "Guest 2 \t" + parPlayedTotal + "\t" + g2ScoreTotal + "\t" + g2Score + "\n";
			emailContacts = userEmail + ", " + g1Email + ", " + g2Email;
			address.setText(emailContacts);
			emailtext.setText(emailMessage);
		}
		else {
			emailMessage = emailMessage + userName +"\t" + parPlayedTotal + "\t" + userScoreTotal + "\t" + userScore + "\n";
			emailMessage = emailMessage + "Guest 1 \t" + parPlayedTotal + "\t" + g1ScoreTotal + "\t" + g1Score + "\n";
			emailMessage = emailMessage + "Guest 2 \t" + parPlayedTotal + "\t" + g2ScoreTotal + "\t" + g2Score + "\n";
			emailMessage = emailMessage + "Guest 3 \t" + parPlayedTotal + "\t" + g3ScoreTotal + "\t" + g3Score + "\n";
			emailContacts = userEmail + ", " + g1Email + ", " + g2Email + ", " + g3Email;
			address.setText(emailContacts);
			emailtext.setText(emailMessage);
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
//menu bar navigation - onclick listeners
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
		{
			//Create a switch statement when a button is pressed the app will go to the relevant page
			switch(v.getId()){
			case R.id.emailsendbutton:
			{
				//was initially to be used to exit page and return user to main, due to how android implements 
				//sending an email user is forced to use back button to return to the app, thereby canceling the navigation
				//Intent mainIntent = new Intent(this, MainMenu.class);
				//startActivity(mainIntent);
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.setType("plain/text");

				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});

				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());

				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());

				EndRound.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			}
			case R.id.emailfinishbutton:
			{
				//navigate to the main page
				//close (finish) the current game of golf to prevent user from backing up into game
				Intent mainIntent = new Intent(this, MainMenu.class);
				finish();
				startActivity(mainIntent);	
			}
			break;
			}
		}
	}
}