package com.fyp.got2Golf;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;

public class Splash extends Activity { 
	//To Login
	protected boolean isActive = true;
	protected int splashTime = 2000;  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//Shared pref
		SharedPreferences settings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settings.edit();
		boolean upgradeFlag = settings.getBoolean("upgrade", true);
	
		//bug fix to create tables as method in DB fails to create on upgrade
	if (upgradeFlag ==true)
	{
		//Clear stats on upgrade
			final DBAdapter db = new DBAdapter(this);
			db.open();
			db.clearStatTable();
			db.clearShotTable();
			db.close();
			
			prefEditor.putBoolean("upgrade", false);
			prefEditor.commit();
	}
	else{}	
		Thread splashTread = new Thread(){
			@Override
			public void run(){

				try{
					int waited = 0;
					//splash time variable - wait 2 seconds
					while (isActive && (waited < splashTime)) {
						sleep(100);
						if(isActive){
							waited+=100;
						}

					}
				}catch(InterruptedException e){

				}finally {
					finish();
					Intent i = new Intent();
					i.setClassName("com.fyp.got2Golf", "com.fyp.got2Golf.MainMenu");
					startActivity(i);

				}

			}
		};

		splashTread.start();
	}      


}
