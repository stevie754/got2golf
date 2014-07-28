package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Tips extends Activity implements View.OnClickListener{
	View layout;
	private PopupWindow pw;
	
	public OnLongClickListener longClickListner;
	LinearLayout instructionsPanel,equipmentPanel,proGolfPanel,newsPanel,extraPanel;
	TextView instructionsPanelText,equipmentPanelText,proGolfPanelText,newsPanelText,extraPanelText;
	View openLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Create instances of buttons and link with XML file
		setContentView(R.layout.tips);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ImageButton infoBtn = (ImageButton)findViewById(R.id.infoTips);
		infoBtn.setOnClickListener(this);
		
		instructionsPanel = (LinearLayout) findViewById(R.id.instructionsPanel);
		equipmentPanel = (LinearLayout) findViewById(R.id.equipmentPanel);
		proGolfPanel = (LinearLayout) findViewById(R.id.proGolfPanel);
		newsPanel = (LinearLayout) findViewById(R.id.newsPanel);

		//Set the headings/text on each of the main accordion panels
		instructionsPanelText = (TextView) findViewById(R.id.instructionsPanelText);
		equipmentPanelText = (TextView) findViewById(R.id.equipmentPanelText);
		proGolfPanelText = (TextView) findViewById(R.id.proGolfPanelText);
		newsPanelText = (TextView) findViewById(R.id.newsPanelText);

		//Set up the on click listeners for the various panels
		instructionsPanelText.setOnClickListener(this);
		equipmentPanelText.setOnClickListener(this);
		proGolfPanelText.setOnClickListener(this);
		newsPanelText.setOnClickListener(this);

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
		switch(v.getId()){
		case R.id.infoTips:
		{	
			initiateInfoWindow();
		}
		break;
		}
		hideOthers(v);
	}
	private void hideOthers(View layoutView)
	{

		{
			//creates animations so that if a certain panel is clicked it becomes visible while hiding the other previously opened panel at the same time
			int v;
			if(layoutView.getId() == R.id.instructionsPanelText)
			{
				v = instructionsPanel.getVisibility();
				hideThemAll(); //calls method hideThemAll()
				if(v != View.VISIBLE)//if the panel is not visible the animation is run and the panel becomes visible to the user
				{
					//animates to show 
					instructionsPanel.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, instructionsPanel, true));
				}
			}
			else if(layoutView.getId() == R.id.equipmentPanelText)
			{
				v = equipmentPanel.getVisibility();
				hideThemAll();
				if(v != View.VISIBLE)//if the panel is not visible the animation is run and the panel becomes visible to the user
				{
					equipmentPanel.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, equipmentPanel, true));
				}
			}
			else if(layoutView.getId() == R.id.proGolfPanelText)
			{
				v = proGolfPanel.getVisibility();
				hideThemAll();
				if(v != View.VISIBLE)//if the panel is not visible the animation is run and the panel becomes visible to the user
				{
					proGolfPanel.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, proGolfPanel, true));
				}
			}
			else if(layoutView.getId() == R.id.newsPanelText)
			{
				v = newsPanel.getVisibility();
				hideThemAll();
				if(v != View.VISIBLE)//if the panel is not visible the animation is run and the panel becomes visible to the user
				{
					newsPanel.startAnimation(new ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, newsPanel, true));
				}
			}
		}
	}
	private void hideThemAll()
	{
		//Creates the hide animation for all the panels
		if(openLayout == null) return;
		if(openLayout == instructionsPanel)
			instructionsPanel.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, instructionsPanel, true));
		if(openLayout == equipmentPanel)
			equipmentPanel.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, equipmentPanel, true));
		if(openLayout == proGolfPanel)
			proGolfPanel.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, proGolfPanel, true));
		if(openLayout == newsPanel)
			newsPanel.startAnimation(new ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, newsPanel, true));
		}
	public class ScaleAnimToHide extends ScaleAnimation
	{
		//instantiate variables
		private View mView;
		private LayoutParams mLayoutParams;
		private int mMarginBottomFromY, mMarginBottomToY;
		private boolean mVanishAfter = false;

		//animation method used to animate the shrinking/hiding of the panels
		public ScaleAnimToHide(float fromX, float toX, float fromY, float toY, int duration, View view,boolean vanishAfter)
		{
			super(fromX, toX, fromY, toY);
			setDuration(duration);
			openLayout = null;
			mView = view;
			mVanishAfter = vanishAfter;
			mLayoutParams = (LayoutParams) view.getLayoutParams();
			int height = mView.getHeight();
			mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
			mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;

			Log.v("CZ","height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t)
		{
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f)
			{
				int newMarginBottom = mMarginBottomFromY + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
				mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
				mView.getParent().requestLayout();
				//Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
			}
			else if (mVanishAfter)
			{
				mView.setVisibility(View.GONE);
			}
		}
	}
	public class ScaleAnimToShow extends ScaleAnimation
	{

		private View mView;

		private LayoutParams mLayoutParams;

		private int mMarginBottomFromY, mMarginBottomToY;

		private boolean mVanishAfter = false;

		public ScaleAnimToShow(float toX, float fromX, float toY, float fromY, int duration, View view,boolean vanishAfter)
		{
			super(fromX, toX, fromY, toY);
			openLayout = view;
			setDuration(duration);
			mView = view;
			mVanishAfter = vanishAfter;
			mLayoutParams = (LayoutParams) view.getLayoutParams();
			mView.setVisibility(View.VISIBLE);
			int height = mView.getHeight();
			//mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin + height;
			//mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) + height;

			mMarginBottomFromY = 0;
			mMarginBottomToY = height;

			Log.v("CZ",".................height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY  + " , mMarginBottomToY.." +mMarginBottomToY);
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t)
		{
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f)
			{
				int newMarginBottom = (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime) - mMarginBottomToY;
				mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin,mLayoutParams.rightMargin, newMarginBottom);
				mView.getParent().requestLayout();
				//Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
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

			TextView infoText = (TextView) layout.findViewById(R.id.infoTextView);
			infoText.setText("Select a bar to expand it and see options available." +
					"\n\nActive internet access is required to view these links as they are all external websites.");

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
	//Methods are declared inside the pages XML file for the click listener for each of the textViews
	private void goToUrl (String url) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}
	public void goToI1 (View view) {
		goToUrl ( "http://golf.about.com/od/beginners/Golf_for_Beginners.htm");
	}
	//TODO put in relevant websites
	public void goToI2 (View view) {
		goToUrl ( "http://www.projam.biz/home-2/tri-service-golf-instruction/intermediate-skills-course-content/#&slider1=1");
	}
	public void goToI3 (View view) {
		goToUrl ( "http://www.advancedgolfskills.com/");
	}

	//Equipment links
	public void goToE1 (View view) {
		goToUrl ( "http://www.direct-golf.co.uk/golf_clubs/");
	}
	public void goToE2 (View view) {
		goToUrl ( "http://www.americangolf.co.uk/Golf-Clubs/CLUBS,en_GB,sc.html");
	}
	public void goToE3 (View view) {
		goToUrl ( "http://www.discountgolfstore.co.uk/g/11/Men-s-Golf-Clubs.html");
	}

	//Pro Golf links
	public void goToPG1 (View view) {
		goToUrl ( "http://www.tourprogolfclubs.com/tournaments");
	}
	public void goToPG2 (View view) {
		goToUrl ( "http://www.pgae.com/");
	}
	public void goToPG3 (View view) {
		goToUrl ( "http://www.pga.com/home/.htm");
	}

	//News Links
	public void goToNews1 (View view) {
		goToUrl ( "http://www.bbc.co.uk/sport/0/golf/");
	}
	public void goToNews2 (View view) {
		goToUrl ( "http://www1.skysports.com/golf/");
	}
}
