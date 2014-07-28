package com.fyp.got2Golf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditCourse extends Activity implements OnItemSelectedListener, View.OnClickListener{
	int spinnerValue1 =4;
	int spinnerValue2 =4;
	int spinnerValue3 =4;
	int spinnerValue4 =4;
	int spinnerValue5 =4;
	int spinnerValue6 =4;
	int spinnerValue7 =4;
	int spinnerValue8 =4;
	int spinnerValue9 =4;
	int spinnerValue10 =4;
	int spinnerValue11 =4;
	int spinnerValue12 =4;
	int spinnerValue13 =4;
	int spinnerValue14 =4;
	int spinnerValue15 =4;
	int spinnerValue16 =4;
	int spinnerValue17 =4;
	int spinnerValue18 =4; 

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editcourse);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//instantiate all spinners on the page
		Spinner spinner1 = (Spinner) findViewById(R.id.spinnerEdit1);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinnerEdit2);
		Spinner spinner3 = (Spinner) findViewById(R.id.spinnerEdit3);
		Spinner spinner4 = (Spinner) findViewById(R.id.spinnerEdit4);
		Spinner spinner5 = (Spinner) findViewById(R.id.spinnerEdit5);
		Spinner spinner6 = (Spinner) findViewById(R.id.spinnerEdit6);
		Spinner spinner7 = (Spinner) findViewById(R.id.spinnerEdit7);
		Spinner spinner8 = (Spinner) findViewById(R.id.spinnerEdit8);
		Spinner spinner9 = (Spinner) findViewById(R.id.spinnerEdit9);
		Spinner spinner10 = (Spinner) findViewById(R.id.spinnerEdit10);
		Spinner spinner11 = (Spinner) findViewById(R.id.spinnerEdit11);
		Spinner spinner12 = (Spinner) findViewById(R.id.spinnerEdit12);
		Spinner spinner13 = (Spinner) findViewById(R.id.spinnerEdit13);
		Spinner spinner14 = (Spinner) findViewById(R.id.spinnerEdit14);
		Spinner spinner15 = (Spinner) findViewById(R.id.spinnerEdit15);
		Spinner spinner16 = (Spinner) findViewById(R.id.spinnerEdit16);
		Spinner spinner17 = (Spinner) findViewById(R.id.spinnerEdit17);
		Spinner spinner18 = (Spinner) findViewById(R.id.spinnerEdit18);

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.par, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(this);
		spinner3.setAdapter(adapter);
		spinner3.setOnItemSelectedListener(this);
		spinner4.setAdapter(adapter);
		spinner4.setOnItemSelectedListener(this);
		spinner5.setAdapter(adapter);
		spinner5.setOnItemSelectedListener(this);
		spinner6.setAdapter(adapter);
		spinner6.setOnItemSelectedListener(this);
		spinner7.setAdapter(adapter);
		spinner7.setOnItemSelectedListener(this);
		spinner8.setAdapter(adapter);
		spinner8.setOnItemSelectedListener(this);
		spinner9.setAdapter(adapter);
		spinner9.setOnItemSelectedListener(this);
		spinner10.setAdapter(adapter);
		spinner10.setOnItemSelectedListener(this);
		spinner11.setAdapter(adapter);
		spinner11.setOnItemSelectedListener(this);
		spinner12.setAdapter(adapter);
		spinner12.setOnItemSelectedListener(this);
		spinner13.setAdapter(adapter);
		spinner13.setOnItemSelectedListener(this);
		spinner14.setAdapter(adapter);
		spinner14.setOnItemSelectedListener(this);
		spinner15.setAdapter(adapter);
		spinner15.setOnItemSelectedListener(this);
		spinner16.setAdapter(adapter);
		spinner16.setOnItemSelectedListener(this);
		spinner17.setAdapter(adapter);
		spinner17.setOnItemSelectedListener(this);
		spinner18.setAdapter(adapter);
		spinner18.setOnItemSelectedListener(this);

		//Set Default spinner values to 4
		//parameter 1 passed as array spiiners reading from 4 is the second value
		spinner1.setSelection(1);
		spinner2.setSelection(1);
		spinner3.setSelection(1);
		spinner4.setSelection(1);
		spinner5.setSelection(1);
		spinner6.setSelection(1);
		spinner7.setSelection(1);
		spinner8.setSelection(1);
		spinner9.setSelection(1);
		spinner10.setSelection(1);
		spinner11.setSelection(1);
		spinner12.setSelection(1);
		spinner13.setSelection(1);
		spinner14.setSelection(1);
		spinner15.setSelection(1);
		spinner16.setSelection(1);
		spinner17.setSelection(1);
		spinner18.setSelection(1);

		//assign onclick method to the update button
		final Button updateButton = (Button) findViewById(R.id.btnUpdate); 
		updateButton.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.main_menu, menu);
		inflater.inflate(R.layout.menu, menu);
		return true;
	}
	//menu bar onclick listeners
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
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		// An item was selected. You can retrieve the selected item using
		// parent.getItemAtPosition(pos)

		//Read from spinner and set as global variable for insert to db
		int idSpinner = parent.getId();

		String strHolePar = parent.getItemAtPosition(pos).toString();
		int spinnerValue = Integer.parseInt(strHolePar);
		//will determine which spinner has been used and set a new value to the varaiable 
		switch (idSpinner) {
		case R.id.spinner1:
			setSpinnerValue1(spinnerValue);
			break;
		case R.id.spinner2:
			setSpinnerValue2(spinnerValue);
			break;
		case R.id.spinner3:
			setSpinnerValue3(spinnerValue);
			break;
		case R.id.spinner4:
			setSpinnerValue4(spinnerValue);
			break;
		case R.id.spinner5:
			setSpinnerValue5(spinnerValue);
			break;
		case R.id.spinner6:
			setSpinnerValue6(spinnerValue);
			break;
		case R.id.spinner7:
			setSpinnerValue7(spinnerValue);
			break;
		case R.id.spinner8:
			setSpinnerValue8(spinnerValue);
			break;
		case R.id.spinner9:
			setSpinnerValue9(spinnerValue);
			break;
		case R.id.spinner10:
			setSpinnerValue10(spinnerValue);
			break;
		case R.id.spinner11:
			setSpinnerValue11(spinnerValue);
			break;
		case R.id.spinner12:
			setSpinnerValue12(spinnerValue);
			break;
		case R.id.spinner13:
			setSpinnerValue13(spinnerValue);
			break;
		case R.id.spinner14:
			setSpinnerValue14(spinnerValue);
			break;
		case R.id.spinner15:
			setSpinnerValue15(spinnerValue);
			break;
		case R.id.spinner16:
			setSpinnerValue16(spinnerValue);
			break;
		case R.id.spinner17:
			setSpinnerValue17(spinnerValue);
			break;
		case R.id.spinner18:
			setSpinnerValue18(spinnerValue);
			break;
		}

	}
	//spinner value getters and setters
	//methods used to return the values stored within each spinner
	public int getSpinnerValue1() {
		return spinnerValue1;
	}
	public void setSpinnerValue1(int spin){
		spinnerValue1 = spin;
	}
	public int getSpinnerValue2() {
		return spinnerValue2;
	}
	public void setSpinnerValue2(int spin){
		spinnerValue2 = spin;
	}
	public int getSpinnerValue3() {
		return spinnerValue3;
	}
	public void setSpinnerValue3(int spin){
		spinnerValue3 = spin;
	}
	public int getSpinnerValue4() {
		return spinnerValue4;
	}
	public void setSpinnerValue4(int spin){
		spinnerValue4 = spin;
	}
	public int getSpinnerValue5() {
		return spinnerValue5;
	}
	public void setSpinnerValue5(int spin){
		spinnerValue5 = spin;
	}
	public int getSpinnerValue6() {
		return spinnerValue6;
	}
	public void setSpinnerValue6(int spin){
		spinnerValue6 = spin;
	}
	public int getSpinnerValue7() {
		return spinnerValue7;
	}
	public void setSpinnerValue7(int spin){
		spinnerValue7 = spin;
	}
	public int getSpinnerValue8() {
		return spinnerValue8;
	}
	public void setSpinnerValue8(int spin){
		spinnerValue8 = spin;
	}
	public int getSpinnerValue9() {
		return spinnerValue9;
	}
	public void setSpinnerValue9(int spin){
		spinnerValue9 = spin;
	}
	public int getSpinnerValue10() {
		return spinnerValue10;
	}
	public void setSpinnerValue10(int spin){
		spinnerValue10 = spin;
	}
	public int getSpinnerValue11() {
		return spinnerValue11;
	}
	public void setSpinnerValue11(int spin){
		spinnerValue11 = spin;
	}
	public int getSpinnerValue12() {
		return spinnerValue12;
	}
	public void setSpinnerValue12(int spin){
		spinnerValue12 = spin;
	}
	public int getSpinnerValue13() {
		return spinnerValue13;
	}
	public void setSpinnerValue13(int spin){
		spinnerValue13 = spin;
	}
	public int getSpinnerValue14() {
		return spinnerValue14;
	}
	public void setSpinnerValue14(int spin){
		spinnerValue14 = spin;
	}
	public int getSpinnerValue15() {
		return spinnerValue15;
	}
	public void setSpinnerValue15(int spin){
		spinnerValue15 = spin;
	}
	public int getSpinnerValue16() {
		return spinnerValue16;
	}
	public void setSpinnerValue16(int spin){
		spinnerValue16 = spin;
	}
	public int getSpinnerValue17() {
		return spinnerValue17;
	}
	public void setSpinnerValue17(int spin){
		spinnerValue17 = spin;
	}
	public int getSpinnerValue18() {
		return spinnerValue18;
	}
	public void setSpinnerValue18(int spin){
		spinnerValue18 = spin;
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
	@Override
	public void onClick(View v) {
		//create reference to db
		final DBAdapter db = new DBAdapter(this);
		//Course Name
		String strName = null;
		long intID;
		try{
			//get the number entered by the user
			EditText strEditID = (EditText)findViewById(R.id.editCourseId);
			String strID = strEditID.getText().toString();
			intID = Integer.parseInt(strID);
		}
		catch (Exception exID)
		{
			//additional validation to ensure only numbers are entered.
			//should be handled by input validation within XML
			intID = 0;
			Toast.makeText(EditCourse.this, "ID Error", Toast.LENGTH_SHORT).show();
		}
		try{
			//Course Name
			EditText newName = (EditText)findViewById(R.id.editName);
			strName = newName.getText().toString();
		}
		catch (Exception exName)
		{
			strName = "Updated Course";
			Toast.makeText(EditCourse.this, "Course Name Error", Toast.LENGTH_SHORT).show();
		}
		//assign the hole par for each of the spinners to its own unique variable
		int holePar1 = getSpinnerValue1();
		int holePar2 = getSpinnerValue2();
		int holePar3 = getSpinnerValue3();
		int holePar4 = getSpinnerValue4();
		int holePar5 = getSpinnerValue5();
		int holePar6 = getSpinnerValue6();
		int holePar7 = getSpinnerValue7();
		int holePar8 = getSpinnerValue8();
		int holePar9 = getSpinnerValue9();
		int holePar10 = getSpinnerValue10();
		int holePar11 = getSpinnerValue11();
		int holePar12 = getSpinnerValue12();
		int holePar13 = getSpinnerValue13();
		int holePar14 = getSpinnerValue14();
		int holePar15 = getSpinnerValue15();
		int holePar16 = getSpinnerValue16();
		int holePar17 = getSpinnerValue17();
		int holePar18 = getSpinnerValue18();
		try{
			db.open();
			//calls update method from database,
			//passes parameters to be saved to db
			//possible usability change would be to implment button to retrieve course details and fill spinners and Edittext accordingly - means user only has to change what they need and not re enter hole courses data. 
			db.updateCourse(intID, strName, holePar1, holePar2, holePar3, holePar4, holePar5, holePar6, holePar7, holePar8, holePar9, holePar10, holePar11, holePar12, holePar13, holePar14, holePar15, holePar16, holePar17, holePar18);
			db.close();

			Toast.makeText(EditCourse.this, "Name: " + strName + "\n " + "\nCourse Updated", Toast.LENGTH_SHORT).show();
		}
		catch (Exception e) {
			Toast.makeText(EditCourse.this, "Failed to update course", Toast.LENGTH_SHORT).show();
		}
		//once the user has selected to edit a a course they will be returned to the main page
		Intent mainIntent = new Intent(this, MainMenu.class);
		finish();
		startActivity(mainIntent);	
	}

}
