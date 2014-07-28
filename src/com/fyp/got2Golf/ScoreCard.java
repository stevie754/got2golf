package com.fyp.got2Golf;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
//On turn landscape with ScoreCardIndividual
public class ScoreCard extends Activity implements View.OnClickListener {
	String userName, parArraySerialised, userHoleScoreArraySerialised, guest1HoleScoreArraySerialised, guest2HoleScoreArraySerialised, guest3HoleScoreArraySerialised ,userHoleGIRArraySerialised, userHoleRecoveryArraySerialised ,userHolePuttsArraySerialised;
	String [] parArray, userHoleScoreArray, guest1HoleScoreArray, guest2HoleScoreArray, guest3HoleScoreArray,userHoleGIRArray,userHoleRecoveryArray,userHolePuttsArray;
	int numPlayers, parFrontNine, parBackNine, parTotal, userTotal, guest1Total, guest2Total, guest3Total, user1Score, guest1Score, guest2Score, guest3Score;
	int uScore1, uScore2, uScore3, uScore4, uScore5, uScore6, uScore7, uScore8, uScore9, uScore10, uScore11, uScore12, uScore13, uScore14, uScore15, uScore16, uScore17, uScore18;
	int g1Score1, g1Score2, g1Score3, g1Score4, g1Score5, g1Score6, g1Score7, g1Score8, g1Score9, g1Score10, g1Score11, g1Score12, g1Score13, g1Score14, g1Score15, g1Score16, g1Score17, g1Score18;
	int g2Score1, g2Score2, g2Score3, g2Score4, g2Score5, g2Score6, g2Score7, g2Score8, g2Score9, g2Score10, g2Score11, g2Score12, g2Score13, g2Score14, g2Score15, g2Score16, g2Score17, g2Score18;
	int g3Score1, g3Score2, g3Score3, g3Score4, g3Score5, g3Score6, g3Score7, g3Score8, g3Score9, g3Score10, g3Score11, g3Score12, g3Score13, g3Score14, g3Score15, g3Score16, g3Score17, g3Score18;
	int userFrontNine, userBackNine, g1FrontNine, g1BackNine, g2FrontNine, g2BackNine, g3FrontNine, g3BackNine, userScoreTotal, g1ScoreTotal, g2ScoreTotal, g3ScoreTotal, parPlayedTotal;
	int user18th, g118th, g218th, g318th;
	int[] holePars = new int [18]; 
	//int[] userScoresArray = {uScore1, uScore2, uScore3, uScore4, uScore5, uScore6, uScore7, uScore8, uScore9, uScore10, uScore11, uScore12, uScore13, uScore14, uScore15, uScore16, uScore17, uScore18}; 
	int[] userScoresArray, guest1ScoresArray, guest2ScoresArray, guest3ScoresArray;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create instances of buttons and link with XML file
		setContentView(R.layout.scorecard);

		//Shared Preferences
		SharedPreferences settings = getSharedPreferences("myPrefs", MODE_PRIVATE);
		userName = settings.getString("UserName", null);
		numPlayers = settings.getInt("NoOfPlayers", 1);


		parArraySerialised = settings.getString("holePars", null);
		parFrontNine = settings.getInt("frontNine", 0);
		parBackNine = settings.getInt("backNine", 0);
		parTotal = parFrontNine + parBackNine;

		//recover all scores from shared pref
		userHoleScoreArraySerialised = settings.getString("userHoleScoreArray", null);
		guest1HoleScoreArraySerialised = settings.getString("guest1HoleScoreArray", null);
		guest2HoleScoreArraySerialised = settings.getString("guest2HoleScoreArray", null);
		guest3HoleScoreArraySerialised = settings.getString("guest3HoleScoreArray", null);
		userHoleGIRArraySerialised = settings.getString("userHoleGIRArray", null);
		userHoleRecoveryArraySerialised = settings.getString("userHoleRecoveryArray", null);
		userHolePuttsArraySerialised = settings.getString("userHolePuttsArray", null);

		//Toast.makeText(this, "Test User Scores: "+userHoleScoreArraySerialised, Toast.LENGTH_SHORT).show();
		user18th = settings.getInt("user18th", 0);
		g118th = settings.getInt("guest118th", 0);
		g218th = settings.getInt("guest218th", 0);
		g318th = settings.getInt("guest318th", 0);

		//User and guest scores
		userFrontNine = settings.getInt("UserFrontTotal", 0);
		userBackNine = settings.getInt("UserBackTotal", 0);
		g1FrontNine = settings.getInt("g1FrontTotal", 0);
		g1BackNine = settings.getInt("g1BackTotal", 0);
		g2FrontNine = settings.getInt("g2FrontTotal", 0);
		g2BackNine = settings.getInt("g2BackTotal", 0);
		g3FrontNine = settings.getInt("g3FrontTotal", 0);
		g3BackNine = settings.getInt("g3BackTotal", 0);
		parPlayedTotal = settings.getInt("parPlayedTotal", 0);

		userScoreTotal = userFrontNine + userBackNine;
		g1ScoreTotal = g1FrontNine + g1BackNine;
		g2ScoreTotal = g2FrontNine + g2BackNine;
		g3ScoreTotal = g3FrontNine + g3BackNine;

		//Par array for whole course
		parArray = parArraySerialised.split(",");
		//split all score and records array Strings into arrays
		userHoleScoreArray = userHoleScoreArraySerialised.split(",");
		guest1HoleScoreArray = guest1HoleScoreArraySerialised.split(",");
		guest2HoleScoreArray = guest2HoleScoreArraySerialised.split(",");
		guest3HoleScoreArray = guest3HoleScoreArraySerialised.split(",");
		userHoleGIRArray = userHoleGIRArraySerialised.split(",");
		userHoleRecoveryArray = userHoleRecoveryArraySerialised.split(",");
		userHolePuttsArray = userHolePuttsArraySerialised.split(",");


		for (int i=0; i<userHoleScoreArray.length; i++)
		{
			userScoresArray = new int[i];
			guest1ScoresArray = new int[i];
			guest2ScoresArray = new int[i];
			guest3ScoresArray = new int[i];

			//add calculation and to get score for each hole - assign this value to a variable and not each par.
			//Error with parsed integer values failing to save as int array
			if (i==0)
			{
				try{
					String[] strPar1 = parArray[i].split("ull");
					String[] strUserScore1 = userHoleScoreArray[i].split("ull");
					String[] strGuest1Score1 = guest1HoleScoreArray[i].split("ull");
					String[] strGuest2Score1 = guest2HoleScoreArray[i].split("ull");
					String[] strGuest3Score1 = guest3HoleScoreArray[i].split("ull");
					//Toast.makeText(this, "-0: "+par1[0]+ " -1: "+par1[1], Toast.LENGTH_SHORT).show();
					//Toast.makeText(this, "1 - OK   hole: "+n+ " par "+par1[1], Toast.LENGTH_SHORT).show();
					int par1 = Integer.parseInt(String.valueOf(strPar1[1]));
					int userScore = Integer.parseInt(String.valueOf(strUserScore1[1]));
					int guest1Score = Integer.parseInt(String.valueOf(strGuest1Score1[1]));
					int guest2Score = Integer.parseInt(String.valueOf(strGuest2Score1[1]));
					int guest3Score = Integer.parseInt(String.valueOf(strGuest3Score1[1]));

					/**userScoresArray[i] =  par1-userScore;
					guest1ScoresArray[i] = par1-guest1Score;
					guest2ScoresArray[i] = par1-guest2Score;
					guest3ScoresArray[i] = par1-guest3Score;*/
					//check these are saving to array
					uScore1 = par1-userScore;
					g1Score1 = par1-guest1Score;
					g2Score1 = par1-guest2Score;
					g3Score1 = par1-guest3Score;
				}
				catch (Exception ex1)
				{
					//Toast.makeText(this, "1 - Fail", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				try
				{
					int par = Integer.parseInt(String.valueOf(parArray[i]));
					int userScore = Integer.parseInt(String.valueOf(userHoleScoreArray[i]));
					int guest1Score = Integer.parseInt(String.valueOf(guest1HoleScoreArray[i]));
					int guest2Score = Integer.parseInt(String.valueOf(guest2HoleScoreArray[i]));
					int guest3Score = Integer.parseInt(String.valueOf(guest3HoleScoreArray[i]));

					/**userScoresArray[i] =  par-userScore;
					guest1ScoresArray[i] = par-guest1Score;
					guest2ScoresArray[i] = par-guest2Score;
					guest3ScoresArray[i] = par-guest3Score;*/
				}
				catch (Exception ex2)
				{
					//Toast.makeText(this, "1 - Fail", Toast.LENGTH_SHORT).show();
				}
			}
		}


		//Hide colums and rows of tables according to number of players
		//Table 1
		try{
			/**
				0 is for VISIBLE
				4 is for INVISIBLE 
				8 is for GONE 
			 */
			TableRow row1 = (TableRow)findViewById(R.id.tbl1Guest1Row);
			TableRow row2 = (TableRow)findViewById(R.id.tbl1Guest2Row);
			TableRow row3 = (TableRow)findViewById(R.id.tbl1Guest3Row);

			int tableSettings = numPlayers;
			if (tableSettings ==1)
			{
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
			//Toast.makeText(this, "Scorecard Table 1 Error", Toast.LENGTH_SHORT).show();
		}
		//Table 2
		try{
			/**
			0 is for VISIBLE
			4 is for INVISIBLE 
			8 is for GONE 
			 */
			TableLayout table2 = (TableLayout)findViewById(R.id.Table2);

			int tableSettings = numPlayers;
			if (tableSettings ==1)
			{
				table2.setColumnCollapsed(3, true);
				table2.setColumnCollapsed(4, true);
				table2.setColumnCollapsed(5, true);
			}
			else if (tableSettings ==2)
			{
				table2.setColumnCollapsed(3, false);
				table2.setColumnCollapsed(4, true);
				table2.setColumnCollapsed(5, true);
			}
			else if (tableSettings ==3)
			{
				table2.setColumnCollapsed(3, false);
				table2.setColumnCollapsed(4, false);
				table2.setColumnCollapsed(5, true);
			}
			else
			{
				table2.setColumnCollapsed(3, false);
				table2.setColumnCollapsed(4, false);
				table2.setColumnCollapsed(5, false);
			}
		}
		catch (Exception ex)
		{
			//Toast.makeText(this, "Scorecard Table 2 Error", Toast.LENGTH_SHORT).show();
		}

		//Define Total, Front and Back Textviews
		//Front Nine row
		TextView parFront9 = (TextView)findViewById(R.id.txtParFront9);
		TextView userFront9 = (TextView)findViewById(R.id.txtUserFront9);
		TextView guest1Front9 = (TextView)findViewById(R.id.txtGuest1Front9);
		TextView guest2Front9 = (TextView)findViewById(R.id.txtGuest2Front9);
		TextView guest3Front9 = (TextView)findViewById(R.id.txtGuest3Front9);

		//Back nine row
		TextView parBack9 = (TextView)findViewById(R.id.txtParBack9);
		TextView userBack9 = (TextView)findViewById(R.id.txtUserBack9);
		TextView guest1Back9 = (TextView)findViewById(R.id.txtGuest1Back9);
		TextView guest2Back9 = (TextView)findViewById(R.id.txtGuest2Back9);
		TextView guest3Back9 = (TextView)findViewById(R.id.txtGuest3Back9);
		//Bottom Total Row
		TextView txtParTotal = (TextView)findViewById(R.id.txtFooterPar);
		TextView txtUserTotal = (TextView)findViewById(R.id.txtFooterUser);
		TextView txtGuest1Total = (TextView)findViewById(R.id.txtFooterGuest1);
		TextView txtGuest2Total = (TextView)findViewById(R.id.txtFooterGuest2);
		TextView txtGuest3Total = (TextView)findViewById(R.id.txtFooterGuest3);

		parFront9.setText(String.valueOf(parFrontNine));
		parBack9.setText(String.valueOf(parBackNine));
		txtParTotal.setText(String.valueOf(parTotal));

		//Add user scores to textViews
		userFront9.setText(String.valueOf(userFrontNine));
		userBack9.setText(String.valueOf(userBackNine));
		txtUserTotal.setText(String.valueOf(userScoreTotal));

		guest1Front9.setText(String.valueOf(g1FrontNine));
		guest1Back9.setText(String.valueOf(g1BackNine));
		txtGuest1Total.setText(String.valueOf(g1ScoreTotal));

		guest2Front9.setText(String.valueOf(g2FrontNine));
		guest2Back9.setText(String.valueOf(g2BackNine));
		txtGuest2Total.setText(String.valueOf(g2ScoreTotal));

		guest3Front9.setText(String.valueOf(g3FrontNine));
		guest3Back9.setText(String.valueOf(g3BackNine));
		txtGuest3Total.setText(String.valueOf(g3ScoreTotal));

		//Set username into cells
		TextView tbl1UserName = (TextView)findViewById(R.id.tbl1UserRowName);
		TextView tbl2UserName = (TextView)findViewById(R.id.tbl2HeaderUser);

		//Table1 TextViews
		TextView tbl1UserRowCourse = (TextView)findViewById(R.id.tbl1UserRowCourse);
		TextView tbl1UserRowTotal = (TextView)findViewById(R.id.tbl1UserRowTotal);
		TextView tbl1UserRowScore = (TextView)findViewById(R.id.tbl1UserRowScore);
		TextView tbl1Guest1RowCourse = (TextView)findViewById(R.id.tbl1Guest1RowCourse);
		TextView tbl1Guest1RowTotal = (TextView)findViewById(R.id.tbl1Guest1RowTotal);
		TextView tbl1Guest1RowScore = (TextView)findViewById(R.id.tbl1Guest1RowScore);
		TextView tbl1Guest2RowCourse = (TextView)findViewById(R.id.tbl1Guest2RowCourse);
		TextView tbl1Guest2RowTotal = (TextView)findViewById(R.id.tbl1Guest2RowTotal);
		TextView tbl1Guest2RowScore = (TextView)findViewById(R.id.tbl1Guest2RowScore);
		TextView tbl1Guest3RowCourse = (TextView)findViewById(R.id.tbl1Guest3RowCourse);
		TextView tbl1Guest3RowTotal = (TextView)findViewById(R.id.tbl1Guest3RowTotal);
		TextView tbl1Guest3RowScore = (TextView)findViewById(R.id.tbl1Guest3RowScore);

		int g1Score = g1ScoreTotal-parTotal;
		int g2Score = g2ScoreTotal-parTotal;
		int g3Score = g3ScoreTotal-parTotal;
		int userScore = userScoreTotal-parTotal;

		try
		{
			tbl1UserRowTotal.setText(String.valueOf(userScoreTotal));
			tbl1Guest1RowTotal.setText(String.valueOf(g1ScoreTotal));
			tbl1Guest2RowTotal.setText(String.valueOf(g2ScoreTotal));
			tbl1Guest3RowTotal.setText(String.valueOf(g3ScoreTotal));

			tbl1UserRowScore.setText(String.valueOf(userScore));
			tbl1Guest1RowScore.setText(String.valueOf(g1Score));
			tbl1Guest2RowScore.setText(String.valueOf(g2Score));
			tbl1Guest3RowScore.setText(String.valueOf(g3Score));
		}
		catch (Exception ex)
		{

		}

		//Table2 TextViews
		//Par Column TextView
		TextView txtParRow1 = (TextView)findViewById(R.id.txtParRow1);
		TextView txtParRow2 = (TextView)findViewById(R.id.txtParRow2);
		TextView txtParRow3 = (TextView)findViewById(R.id.txtParRow3);
		TextView txtParRow4 = (TextView)findViewById(R.id.txtParRow4);
		TextView txtParRow5 = (TextView)findViewById(R.id.txtParRow5);
		TextView txtParRow6 = (TextView)findViewById(R.id.txtParRow6);
		TextView txtParRow7 = (TextView)findViewById(R.id.txtParRow7);
		TextView txtParRow8 = (TextView)findViewById(R.id.txtParRow8);
		TextView txtParRow9 = (TextView)findViewById(R.id.txtParRow9);
		TextView txtParRow10 = (TextView)findViewById(R.id.txtParRow10);
		TextView txtParRow11 = (TextView)findViewById(R.id.txtParRow11);
		TextView txtParRow12 = (TextView)findViewById(R.id.txtParRow12);
		TextView txtParRow13 = (TextView)findViewById(R.id.txtParRow13);
		TextView txtParRow14 = (TextView)findViewById(R.id.txtParRow14);
		TextView txtParRow15 = (TextView)findViewById(R.id.txtParRow15);
		TextView txtParRow16 = (TextView)findViewById(R.id.txtParRow16);
		TextView txtParRow17 = (TextView)findViewById(R.id.txtParRow17);
		TextView txtParRow18 = (TextView)findViewById(R.id.txtParRow18);
		//User scores column
		TextView txtUserRow1 = (TextView)findViewById(R.id.txtUserRow1);
		TextView txtUserRow2 = (TextView)findViewById(R.id.txtUserRow2);
		TextView txtUserRow3 = (TextView)findViewById(R.id.txtUserRow3);
		TextView txtUserRow4 = (TextView)findViewById(R.id.txtUserRow4);
		TextView txtUserRow5 = (TextView)findViewById(R.id.txtUserRow5);
		TextView txtUserRow6 = (TextView)findViewById(R.id.txtUserRow6);
		TextView txtUserRow7 = (TextView)findViewById(R.id.txtUserRow7);
		TextView txtUserRow8 = (TextView)findViewById(R.id.txtUserRow8);
		TextView txtUserRow9 = (TextView)findViewById(R.id.txtUserRow9);
		TextView txtUserRow10 = (TextView)findViewById(R.id.txtUserRow10);
		TextView txtUserRow11 = (TextView)findViewById(R.id.txtUserRow11);
		TextView txtUserRow12 = (TextView)findViewById(R.id.txtUserRow12);
		TextView txtUserRow13 = (TextView)findViewById(R.id.txtUserRow13);
		TextView txtUserRow14 = (TextView)findViewById(R.id.txtUserRow14);
		TextView txtUserRow15 = (TextView)findViewById(R.id.txtUserRow15);
		TextView txtUserRow16 = (TextView)findViewById(R.id.txtUserRow16);
		TextView txtUserRow17 = (TextView)findViewById(R.id.txtUserRow17);
		TextView txtUserRow18 = (TextView)findViewById(R.id.txtUserRow18);
		//Guest1 Scores Column
		TextView txtGuest1Row1 = (TextView)findViewById(R.id.txtGuest1Row1);
		TextView txtGuest1Row2 = (TextView)findViewById(R.id.txtGuest1Row2);
		TextView txtGuest1Row3 = (TextView)findViewById(R.id.txtGuest1Row3);
		TextView txtGuest1Row4 = (TextView)findViewById(R.id.txtGuest1Row4);
		TextView txtGuest1Row5 = (TextView)findViewById(R.id.txtGuest1Row5);
		TextView txtGuest1Row6 = (TextView)findViewById(R.id.txtGuest1Row6);
		TextView txtGuest1Row7 = (TextView)findViewById(R.id.txtGuest1Row7);
		TextView txtGuest1Row8 = (TextView)findViewById(R.id.txtGuest1Row8);
		TextView txtGuest1Row9 = (TextView)findViewById(R.id.txtGuest1Row9);
		TextView txtGuest1Row10 = (TextView)findViewById(R.id.txtGuest1Row10);
		TextView txtGuest1Row11 = (TextView)findViewById(R.id.txtGuest1Row11);
		TextView txtGuest1Row12 = (TextView)findViewById(R.id.txtGuest1Row12);
		TextView txtGuest1Row13 = (TextView)findViewById(R.id.txtGuest1Row13);
		TextView txtGuest1Row14 = (TextView)findViewById(R.id.txtGuest1Row14);
		TextView txtGuest1Row15 = (TextView)findViewById(R.id.txtGuest1Row15);
		TextView txtGuest1Row16 = (TextView)findViewById(R.id.txtGuest1Row16);
		TextView txtGuest1Row17 = (TextView)findViewById(R.id.txtGuest1Row17);
		TextView txtGuest1Row18 = (TextView)findViewById(R.id.txtGuest1Row18);
		//Guest2 Scores Column
		TextView txtGuest2Row1 = (TextView)findViewById(R.id.txtGuest2Row1);
		TextView txtGuest2Row2 = (TextView)findViewById(R.id.txtGuest2Row2);
		TextView txtGuest2Row3 = (TextView)findViewById(R.id.txtGuest2Row3);
		TextView txtGuest2Row4 = (TextView)findViewById(R.id.txtGuest2Row4);
		TextView txtGuest2Row5 = (TextView)findViewById(R.id.txtGuest2Row5);
		TextView txtGuest2Row6 = (TextView)findViewById(R.id.txtGuest2Row6);
		TextView txtGuest2Row7 = (TextView)findViewById(R.id.txtGuest2Row7);
		TextView txtGuest2Row8 = (TextView)findViewById(R.id.txtGuest2Row8);
		TextView txtGuest2Row9 = (TextView)findViewById(R.id.txtGuest2Row9);
		TextView txtGuest2Row10 = (TextView)findViewById(R.id.txtGuest2Row10);
		TextView txtGuest2Row11 = (TextView)findViewById(R.id.txtGuest2Row11);
		TextView txtGuest2Row12 = (TextView)findViewById(R.id.txtGuest2Row12);
		TextView txtGuest2Row13 = (TextView)findViewById(R.id.txtGuest2Row13);
		TextView txtGuest2Row14 = (TextView)findViewById(R.id.txtGuest2Row14);
		TextView txtGuest2Row15 = (TextView)findViewById(R.id.txtGuest2Row15);
		TextView txtGuest2Row16 = (TextView)findViewById(R.id.txtGuest2Row16);
		TextView txtGuest2Row17 = (TextView)findViewById(R.id.txtGuest2Row17);
		TextView txtGuest2Row18 = (TextView)findViewById(R.id.txtGuest2Row18);
		//Guest3 Scores Column
		TextView txtGuest3Row1 = (TextView)findViewById(R.id.txtGuest3Row1);
		TextView txtGuest3Row2 = (TextView)findViewById(R.id.txtGuest3Row2);
		TextView txtGuest3Row3 = (TextView)findViewById(R.id.txtGuest3Row3);
		TextView txtGuest3Row4 = (TextView)findViewById(R.id.txtGuest3Row4);
		TextView txtGuest3Row5 = (TextView)findViewById(R.id.txtGuest3Row5);
		TextView txtGuest3Row6 = (TextView)findViewById(R.id.txtGuest3Row6);
		TextView txtGuest3Row7 = (TextView)findViewById(R.id.txtGuest3Row7);
		TextView txtGuest3Row8 = (TextView)findViewById(R.id.txtGuest3Row8);
		TextView txtGuest3Row9 = (TextView)findViewById(R.id.txtGuest3Row9);
		TextView txtGuest3Row10 = (TextView)findViewById(R.id.txtGuest3Row10);
		TextView txtGuest3Row11 = (TextView)findViewById(R.id.txtGuest3Row11);
		TextView txtGuest3Row12 = (TextView)findViewById(R.id.txtGuest3Row12);
		TextView txtGuest3Row13 = (TextView)findViewById(R.id.txtGuest3Row13);
		TextView txtGuest3Row14 = (TextView)findViewById(R.id.txtGuest3Row14);
		TextView txtGuest3Row15 = (TextView)findViewById(R.id.txtGuest3Row15);
		TextView txtGuest3Row16 = (TextView)findViewById(R.id.txtGuest3Row16);
		TextView txtGuest3Row17 = (TextView)findViewById(R.id.txtGuest3Row17);
		TextView txtGuest3Row18 = (TextView)findViewById(R.id.txtGuest3Row18);

		//Try to set up table 1, if landscape this will be bypassed by the app
		try
		{
			//course par column
			tbl1UserName.setText(userName);
			tbl1UserRowCourse.setText(String.valueOf(parTotal));
			tbl1Guest1RowCourse.setText(String.valueOf(parTotal));
			tbl1Guest2RowCourse.setText(String.valueOf(parTotal));
			tbl1Guest3RowCourse.setText(String.valueOf(parTotal));
		}
		catch (Exception exTable1Setup)
		{

		}
		//Set up Table2
		tbl2UserName.setText(userName);
		//Assign values to par column
		String[] par1Temp = parArray[0].split("ull");
		txtParRow1.setText(par1Temp[1]);
		txtParRow2.setText(parArray[1]);
		txtParRow3.setText(parArray[2]);
		txtParRow4.setText(parArray[3]);
		txtParRow5.setText(parArray[4]);
		txtParRow6.setText(parArray[5]);
		txtParRow7.setText(parArray[6]);
		txtParRow8.setText(parArray[7]);
		txtParRow9.setText(parArray[8]);
		txtParRow10.setText(parArray[9]);
		txtParRow11.setText(parArray[10]);
		txtParRow12.setText(parArray[11]);
		txtParRow13.setText(parArray[12]);
		txtParRow14.setText(parArray[13]);
		txtParRow15.setText(parArray[14]);
		txtParRow16.setText(parArray[15]);
		txtParRow17.setText(parArray[16]);
		txtParRow18.setText(parArray[17]);
		//Assign Values to user score column
		try{
			//String[] userScoreTemp = userHoleScoreArray[0].split("ull");
			//txtUserRow1.setText(userScoreTemp[1]);
			txtUserRow1.setText(userHoleScoreArray[0]);
			txtUserRow2.setText(userHoleScoreArray[1]);
			//Toast.makeText(this, "1: " +userScoreTemp[1], Toast.LENGTH_LONG).show();
			//Toast.makeText(this, "2: " +userHoleScoreArray[1], Toast.LENGTH_LONG).show();
			txtUserRow3.setText(userHoleScoreArray[2]);
			txtUserRow4.setText(userHoleScoreArray[3]);
			txtUserRow5.setText(userHoleScoreArray[4]);
			txtUserRow6.setText(userHoleScoreArray[5]);
			txtUserRow7.setText(userHoleScoreArray[6]);
			txtUserRow8.setText(userHoleScoreArray[7]);
			txtUserRow9.setText(userHoleScoreArray[8]);
			txtUserRow10.setText(userHoleScoreArray[9]);
			txtUserRow11.setText(userHoleScoreArray[10]);
			txtUserRow12.setText(userHoleScoreArray[11]);
			txtUserRow13.setText(userHoleScoreArray[12]);
			txtUserRow14.setText(userHoleScoreArray[13]);
			txtUserRow15.setText(userHoleScoreArray[14]);
			txtUserRow16.setText(userHoleScoreArray[15]);
			txtUserRow17.setText(userHoleScoreArray[16]);
			txtUserRow18.setText(userHoleScoreArray[17]);
		}
		catch (Exception exUser)
		{

		}
		//Assign Values to guest 1 score column
		//String[] guest1ScoreTemp = guest1HoleScoreArray[0].split("ull");
		//txtGuest1Row1.setText(guest1ScoreTemp [1]);
		txtGuest1Row1.setText(guest1HoleScoreArray[0]);
		txtGuest1Row2.setText(guest1HoleScoreArray[1]);
		txtGuest1Row3.setText(guest1HoleScoreArray[2]);
		txtGuest1Row4.setText(guest1HoleScoreArray[3]);
		txtGuest1Row5.setText(guest1HoleScoreArray[4]);
		txtGuest1Row6.setText(guest1HoleScoreArray[5]);
		txtGuest1Row7.setText(guest1HoleScoreArray[6]);
		txtGuest1Row8.setText(guest1HoleScoreArray[7]);
		txtGuest1Row9.setText(guest1HoleScoreArray[8]);
		txtGuest1Row10.setText(guest1HoleScoreArray[9]);
		txtGuest1Row11.setText(guest1HoleScoreArray[10]);
		txtGuest1Row12.setText(guest1HoleScoreArray[11]);
		txtGuest1Row13.setText(guest1HoleScoreArray[12]);
		txtGuest1Row14.setText(guest1HoleScoreArray[13]);
		txtGuest1Row15.setText(guest1HoleScoreArray[14]);
		txtGuest1Row16.setText(guest1HoleScoreArray[15]);
		txtGuest1Row17.setText(guest1HoleScoreArray[16]);
		txtGuest1Row18.setText(guest1HoleScoreArray[17]);
		//Assign Values to guest 2 score column
		//String[] guest2ScoreTemp = guest2HoleScoreArray[0].split("ull");
		//txtGuest2Row1.setText(guest2ScoreTemp [1]);
		txtGuest2Row1.setText(guest2HoleScoreArray[0]);
		txtGuest2Row2.setText(guest2HoleScoreArray[1]);
		txtGuest2Row3.setText(guest2HoleScoreArray[2]);
		txtGuest2Row4.setText(guest2HoleScoreArray[3]);
		txtGuest2Row5.setText(guest2HoleScoreArray[4]);
		txtGuest2Row6.setText(guest2HoleScoreArray[5]);
		txtGuest2Row7.setText(guest2HoleScoreArray[6]);
		txtGuest2Row8.setText(guest2HoleScoreArray[7]);
		txtGuest2Row9.setText(guest2HoleScoreArray[8]);
		txtGuest2Row10.setText(guest2HoleScoreArray[9]);
		txtGuest2Row11.setText(guest2HoleScoreArray[10]);
		txtGuest2Row12.setText(guest2HoleScoreArray[11]);
		txtGuest2Row13.setText(guest2HoleScoreArray[12]);
		txtGuest2Row14.setText(guest2HoleScoreArray[13]);
		txtGuest2Row15.setText(guest2HoleScoreArray[14]);
		txtGuest2Row16.setText(guest2HoleScoreArray[15]);
		txtGuest2Row17.setText(guest2HoleScoreArray[16]);
		txtGuest2Row18.setText(guest2HoleScoreArray[17]);
		//Assign Values to guest 1 score column
		//String[] guest3ScoreTemp = guest3HoleScoreArray[0].split("ull");
		//txtGuest3Row1.setText(guest3ScoreTemp [1]);
		txtGuest3Row1.setText(guest3HoleScoreArray[0]);
		txtGuest3Row2.setText(guest3HoleScoreArray[1]);
		txtGuest3Row3.setText(guest3HoleScoreArray[2]);
		txtGuest3Row4.setText(guest3HoleScoreArray[3]);
		txtGuest3Row5.setText(guest3HoleScoreArray[4]);
		txtGuest3Row6.setText(guest3HoleScoreArray[5]);
		txtGuest3Row7.setText(guest3HoleScoreArray[6]);
		txtGuest3Row8.setText(guest3HoleScoreArray[7]);
		txtGuest3Row9.setText(guest3HoleScoreArray[8]);
		txtGuest3Row10.setText(guest3HoleScoreArray[9]);
		txtGuest3Row11.setText(guest3HoleScoreArray[10]);
		txtGuest3Row12.setText(guest3HoleScoreArray[11]);
		txtGuest3Row13.setText(guest3HoleScoreArray[12]);
		txtGuest3Row14.setText(guest3HoleScoreArray[13]);
		txtGuest3Row15.setText(guest3HoleScoreArray[14]);
		txtGuest3Row16.setText(guest3HoleScoreArray[15]);
		txtGuest3Row17.setText(guest3HoleScoreArray[16]);
		txtGuest3Row18.setText(guest3HoleScoreArray[17]);

		try
		{
			//Toast.makeText(this, "1 user 18th hole: " +user18th, Toast.LENGTH_LONG).show();
			txtUserRow18.setText(user18th);
			txtGuest1Row18.setText(g118th);
			txtGuest2Row18.setText(g118th);
			txtGuest3Row18.setText(g118th);
		}
		catch (Exception finalEx)
		{
			//Toast.makeText(this, "2 user 18th hole: " +user18th, Toast.LENGTH_LONG).show();
			txtUserRow18.setText(userHoleScoreArray[17]);
			txtGuest1Row18.setText(guest3HoleScoreArray[17]);
			txtGuest2Row18.setText(guest3HoleScoreArray[17]);
			txtGuest3Row18.setText(guest3HoleScoreArray[17]);
		}
		//Test Toasts
		//Toast.makeText(this, "front " +parFrontNine, Toast.LENGTH_LONG).show();
		//Toast.makeText(this, "back " +parBackNine, Toast.LENGTH_LONG).show();
		//Toast.makeText(this, "total " +parTotal, Toast.LENGTH_LONG).show();
		//Toast.makeText(this, "UserScore hole 1:  " +userScoresArray[0], Toast.LENGTH_LONG).show();
		//Toast.makeText(this, "UserScore hole 2:  " +userScoresArray[1], Toast.LENGTH_LONG).show();
		//userScoresArray
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
	}
}