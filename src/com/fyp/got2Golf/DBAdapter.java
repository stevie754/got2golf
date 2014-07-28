package com.fyp.got2Golf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_HOLE1 = "hole1";
	public static final String KEY_HOLE2 = "hole2";
	public static final String KEY_HOLE3 = "hole3";
	public static final String KEY_HOLE4 = "hole4";
	public static final String KEY_HOLE5 = "hole5";
	public static final String KEY_HOLE6 = "hole6";
	public static final String KEY_HOLE7 = "hole7";
	public static final String KEY_HOLE8 = "hole8";
	public static final String KEY_HOLE9 = "hole9";
	public static final String KEY_HOLE10 = "hole10";
	public static final String KEY_HOLE11 = "hole11";
	public static final String KEY_HOLE12 = "hole12";
	public static final String KEY_HOLE13 = "hole13";
	public static final String KEY_HOLE14 = "hole14";
	public static final String KEY_HOLE15 = "hole15";
	public static final String KEY_HOLE16 = "hole16";
	public static final String KEY_HOLE17 = "hole17";
	public static final String KEY_HOLE18 = "hole18";
	//Stats Table
	public static final String KEY_COURSEPOS = "course_pos";
	public static final String KEY_HOLEID = "hole_id";
	public static final String KEY_HOLEPAR = "holepar";
	public static final String KEY_USERSHOTS = "usershots";
	public static final String KEY_GUEST1 = "guest1shots";
	public static final String KEY_GUEST2 = "guest2shots";
	public static final String KEY_GUEST3 = "guest3shots";
	public static final String KEY_GIR = "usergir";
	public static final String KEY_RECOVERY = "userrecovery";
	public static final String KEY_PUTTS = "userputts";

	public static final String KEY_TRACK = "shotDistance";

	public static final String KEY_AMOUNT = "amount";
	//DB Adapter
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "AppDatabase";
	private static final String DATABASE_COURSE_TABLE = "course";
	private static final String DATABASE_STAT_TABLE = "stat";
	private static final String DATABASE_TRACK_TABLE = "track";
	private static final int DATABASE_VERSION = 3;

	private static final String DATABASE_COURSE_CREATE =
			"create table course (_id integer primary key autoincrement, "
					+ "name text not null, hole1 integer not null, hole2 integer not null, " +
					"hole3 integer not null, hole4 integer not null, hole5 integer not null, " +
					"hole6 integer not null, hole7 integer not null, hole8 integer not null, " +
					"hole9 integer not null, hole10 integer not null, hole11 integer not null, " +
					"hole12 integer not null, hole13 integer not null, hole14 integer not null," +
					" hole15 integer not null, hole16 integer not null, hole17 integer not null, " +
					"hole18 integer not null);";

	private static final String DATABASE_STAT_CREATE =
			"create table stat (_id integer primary key autoincrement, "
					+ "course_pos integer not null, hole_id integer not null, "
					+ "holepar integer not null, usershots integer not null, "
					+ "guest1shots integer, guest2shots integer, guest3shots integer, "
					+ "usergir boolean, userrecovery integer, userputts integer);";

	private static final String DATABASE_TRACK_CREATE =
			"create table track (_id integer primary key autoincrement, "
					+ "course_pos integer not null, hole_id integer not null, "
					+ "shotDistance integer not null);";

	private final Context context;    

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) 
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			try {
				db.execSQL(DATABASE_COURSE_CREATE);	
				db.execSQL(DATABASE_STAT_CREATE);
				db.execSQL(DATABASE_TRACK_CREATE);	
				//db.execSQL(DATABASE_TEMPSTATS_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			//db.execSQL("DROP TABLE IF EXISTS stat");
			//db.execSQL("DROP TABLE IF EXISTS track");
			onCreate(db);
		}
	}    

	//---opens the database---
	public DBAdapter open() throws SQLException 
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}

	//---closes the database---    
	public void close() 
	{
		DBHelper.close();
	}
	//drop table to clear any data
	public void clearStatTable()
	{
		db.execSQL("DROP TABLE IF EXISTS stat");
		try {
			db.execSQL(DATABASE_STAT_CREATE);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//drop table to clear any data
	public void clearShotTable()
	{
		db.execSQL("DROP TABLE IF EXISTS track");
		try {
			db.execSQL(DATABASE_TRACK_CREATE);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//---insert a course into the database---
	public long insertCourse(String name, int hole1, int hole2, int hole3, int hole4, int hole5, int hole6, int hole7, int hole8, int hole9, int hole10, int hole11, int hole12, int hole13, int hole14, int hole15, int hole16, int hole17, int hole18) 
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_HOLE1, hole1);
		initialValues.put(KEY_HOLE2, hole2);
		initialValues.put(KEY_HOLE3, hole3);
		initialValues.put(KEY_HOLE4, hole4);
		initialValues.put(KEY_HOLE5, hole5);
		initialValues.put(KEY_HOLE6, hole6);
		initialValues.put(KEY_HOLE7, hole7);
		initialValues.put(KEY_HOLE8, hole8);
		initialValues.put(KEY_HOLE9, hole9);
		initialValues.put(KEY_HOLE10, hole10);
		initialValues.put(KEY_HOLE11, hole11);
		initialValues.put(KEY_HOLE12, hole12);
		initialValues.put(KEY_HOLE13, hole13);
		initialValues.put(KEY_HOLE14, hole14);
		initialValues.put(KEY_HOLE15, hole15);
		initialValues.put(KEY_HOLE16, hole16);
		initialValues.put(KEY_HOLE17, hole17);
		initialValues.put(KEY_HOLE18, hole18);
		return db.insert(DATABASE_COURSE_TABLE, null, initialValues);
	}
	//---deletes a particular course---
	public boolean deleteCourse(long rowId) 
	{
		return db.delete(DATABASE_COURSE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	//---retrieves all the courses---
	public Cursor getAllCourses() 
	{
		return db.query(DATABASE_COURSE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_HOLE1, KEY_HOLE2, KEY_HOLE3, KEY_HOLE4, KEY_HOLE5, KEY_HOLE6, KEY_HOLE7, KEY_HOLE8, KEY_HOLE9, KEY_HOLE10, KEY_HOLE11, KEY_HOLE12, KEY_HOLE13, KEY_HOLE14, KEY_HOLE15, KEY_HOLE16, KEY_HOLE17, KEY_HOLE18}, null, null, null, null, null);
	}

	//---retrieves course name---
	public String displayName() 
	{
		db.query(DATABASE_COURSE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_HOLE1, KEY_HOLE2, KEY_HOLE3, KEY_HOLE4, KEY_HOLE5, KEY_HOLE6, KEY_HOLE7, KEY_HOLE8, KEY_HOLE9, KEY_HOLE10, KEY_HOLE11, KEY_HOLE12, KEY_HOLE13, KEY_HOLE14, KEY_HOLE15, KEY_HOLE16, KEY_HOLE17, KEY_HOLE18}, null, null, null, null, null);
		return KEY_NAME;
	}
	//---retrieves a particular course---
	public Cursor getCourse(long rowId) throws SQLException 
	{
		Cursor mCursor =
				db.query(true, DATABASE_COURSE_TABLE, new String[] {KEY_ROWID,
						KEY_NAME, KEY_HOLE1, KEY_HOLE2, KEY_HOLE3, KEY_HOLE4, KEY_HOLE5, KEY_HOLE6, KEY_HOLE7, KEY_HOLE8, KEY_HOLE9, KEY_HOLE10, KEY_HOLE11, KEY_HOLE12, KEY_HOLE13, KEY_HOLE14, KEY_HOLE15, KEY_HOLE16, KEY_HOLE17, KEY_HOLE18}, KEY_ROWID + "=" + rowId, null,
						null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	//---updates a course---
	public boolean updateCourse(long rowId, String name, int hole1, int hole2, int hole3, int hole4, int hole5, int hole6, int hole7, int hole8, int hole9, int hole10, int hole11, int hole12, int hole13, int hole14, int hole15, int hole16, int hole17, int hole18 ) 
	{
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_HOLE1, hole1);
		args.put(KEY_HOLE2, hole2);
		args.put(KEY_HOLE3, hole3);
		args.put(KEY_HOLE4, hole4);
		args.put(KEY_HOLE5, hole5);
		args.put(KEY_HOLE6, hole6);
		args.put(KEY_HOLE7, hole7);
		args.put(KEY_HOLE8, hole8);
		args.put(KEY_HOLE9, hole9);
		args.put(KEY_HOLE10, hole10);
		args.put(KEY_HOLE11, hole11);
		args.put(KEY_HOLE12, hole12);
		args.put(KEY_HOLE13, hole13);
		args.put(KEY_HOLE14, hole14);
		args.put(KEY_HOLE15, hole15);
		args.put(KEY_HOLE16, hole16);
		args.put(KEY_HOLE17, hole17);
		args.put(KEY_HOLE18, hole18);

		return db.update(DATABASE_COURSE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	//---insert a stat into the database---
		public long insertStat(int courseID, int holeID, int holePar, int userShots, int guest1Shots, int guest2Shots, int guest3Shots, boolean userGIR, int userRecovery, int userPutts) 
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_COURSEPOS, courseID);
			initialValues.put(KEY_HOLEID, holeID);
			initialValues.put(KEY_HOLEPAR, holePar);
			initialValues.put(KEY_USERSHOTS, userShots);
			initialValues.put(KEY_GUEST1, guest1Shots);
			initialValues.put(KEY_GUEST2, guest2Shots);
			initialValues.put(KEY_GUEST3, guest3Shots);
			initialValues.put(KEY_GIR, userGIR);
			initialValues.put(KEY_RECOVERY, userRecovery);
			initialValues.put(KEY_PUTTS, userPutts);
			return db.insert(DATABASE_STAT_TABLE, null, initialValues);
		}
		//---deletes a particular stat---
		public boolean deleteTempStats(long rowId) 
		{
			return db.delete(DATABASE_STAT_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}

		//---retrieves all the stats---
		public Cursor getAllStats() 
		{
			return db.query(DATABASE_STAT_TABLE, new String[] {KEY_ROWID, KEY_COURSEPOS, KEY_HOLEID, KEY_HOLEPAR, KEY_USERSHOTS, KEY_GUEST1, KEY_GUEST2, KEY_GUEST3, KEY_GIR, KEY_RECOVERY, KEY_PUTTS}, null, null, null, null, null);
		}
		//---updates a temp stats---
		public boolean updateTempStats(long rowId, int courseID, int holeID, int holePar, int userShots, int guest1Shots, int guest2Shots, int guest3Shots, boolean userGIR, int userRecovery, int userPutts)  
		{
			ContentValues args = new ContentValues();
			args.put(KEY_COURSEPOS, courseID);
			args.put(KEY_HOLEID, holeID);
			args.put(KEY_HOLEPAR, holePar);
			args.put(KEY_USERSHOTS, userShots);
			args.put(KEY_GUEST1, guest1Shots);
			args.put(KEY_GUEST2, guest2Shots);
			args.put(KEY_GUEST3, guest3Shots);
			args.put(KEY_GIR, userGIR);
			args.put(KEY_RECOVERY, userRecovery);
			args.put(KEY_PUTTS, userPutts);

			return db.update(DATABASE_STAT_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
		}
		//---insert a stat into the database---
		public long insertTrack(int courseID, int holeID, int trackShot) 
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_COURSEPOS, courseID);
			initialValues.put(KEY_HOLEID, holeID);
			initialValues.put(KEY_TRACK, trackShot);
			return db.insert(DATABASE_TRACK_TABLE, null, initialValues);
		}
		
		//Queries against stats for display on the statistics page
		public Cursor getAllShots() 
		{
			return db.query(DATABASE_TRACK_TABLE, new String[] {KEY_ROWID, KEY_COURSEPOS, KEY_HOLEID, KEY_TRACK}, null, null, null, null, null);
		}

		public Cursor CountPar() {
			return db.rawQuery("select count(*) from stat", null);
		}
		public Cursor CountShotsPar() {
			return db.rawQuery("select sum(usershots) from stat", null);
		}
		public Cursor CountPuttsPar() {
			return db.rawQuery("select sum(userputts) from stat", null);
		}
		public Cursor CountPenPar() {
			return db.rawQuery("select sum(userrecovery)", null);
		}
		public Cursor CountTrueGIR() {
			return db.rawQuery("select sum(usergir) from stat where usergir = true", null);
		}
		public Cursor CountPar(int par) {
			return db.rawQuery("select count(*) from stat where holepar = "+par, null);
		}
		public Cursor CountShotsPar(int par) {
			return db.rawQuery("select sum(usershots) from stat where holepar = "+par, null);
		}
		public Cursor CountPuttsPar(int par) {
			return db.rawQuery("select sum(userputts) from stat where holepar = "+par, null);
		}
		public Cursor CountPenPar(int par) {
			return db.rawQuery("select sum(userrecovery) from stat where holepar = "+par, null);
		}
		public Cursor CountTrueGIR(int par) {
			return db.rawQuery("select sum(usergir) from stat where holepar = "+par +" and usergir = true", null);
		}
}
