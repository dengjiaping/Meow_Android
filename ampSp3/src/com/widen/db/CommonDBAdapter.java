package com.widen.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

public class CommonDBAdapter {
	private Context mContext = null;
	private static CommonDBAdapter instance;
	private CommonDBHelper commonDBHelper;
	private SQLiteDatabase mDb;
	public final static String DB_NAME = "goldcat.db";
	public final static int VERSION = 1;
	private static final Object DB_LOCK = new Object();

	/*public static final String TABLE_PRODUCTION = "production";
	public static final String TABLE_BANK = "bank";*/
	
	
	public static final String TABLE_RESULT = "result";
	
	public static final String TABLE_I_PRODUCTION = "production";
	public static final String TABLE_I_BANK = "bank";
	public static final String TABLE_I_BELONGS = "belongs";


	private CommonDBAdapter(Context ctx) {
		this.mContext = ctx;
	}

	public static CommonDBAdapter getInstance(Context con) {
		if (instance == null) {
			synchronized (CommonDBAdapter.class) {
				if (instance == null) {
					instance = new CommonDBAdapter(con.getApplicationContext());
				}
			}
		}
		return instance;
	}
	


	public void execSQL(String sql, Object[] selectionArgs) {	
		SQLiteDatabase db = commonDBHelper.getWritableDatabase();
		db.beginTransaction();
		try{
			db.execSQL(sql,selectionArgs);			
			db.setTransactionSuccessful();		
		}finally{
			db.endTransaction();
		}

	}
	
	public Cursor selectSQL(String sql, String[] selectionArgs) {
		SQLiteDatabase db = commonDBHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		return cursor;

	}

	public void initDataBase() {
		if (commonDBHelper != null) {
			mDb = commonDBHelper.getReadableDatabase();
		}
	}

	public CommonDBAdapter open() throws SQLException {
		synchronized (DB_LOCK) {
			if (commonDBHelper == null) {
				commonDBHelper = new CommonDBHelper(mContext);
			}
		}
		return this;
	}

	public void close() {
		synchronized (DB_LOCK) {
			if (commonDBHelper != null) {
				commonDBHelper.close();
				commonDBHelper = null;
			}
		}
	}

	public long insert(String table, ContentValues cv) {
		mDb = commonDBHelper.getWritableDatabase();
		return mDb.insert(table, null, cv);
	}

	public void bulkInsert(String table, List<ContentValues> cvs) {
		mDb = commonDBHelper.getWritableDatabase();
		mDb.beginTransaction();
		try {
			for (ContentValues cv : cvs) {
				mDb.insert(table, null, cv);
			}
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
		} finally {
			mDb.endTransaction();
		}
		mDb.close();
	}

	public void delete(String table, String whereClause, String[] whereArgs) {
		mDb = commonDBHelper.getWritableDatabase();
		mDb.delete(table, whereClause, whereArgs);
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		mDb = commonDBHelper.getReadableDatabase();
		return mDb.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	private class CommonDBHelper extends SQLiteOpenHelper {

		public CommonDBHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
		
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
		
			try {
				// ...
			
			} finally {
				db.endTransaction();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}
