package com.example.training1.database;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Adapts the database to deal with the front end.
 */
public class DatabaseAdapter {

	private static final String DB_NAME = "issueDB";

	private static final String DB_TABLE = "create table Wishlist (id integer primary key autoincrement, "
			+ "title text not null, desc text not null, catid integer not null, subcatid integer not null);";

	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private static String DB_PATH;
	public final static String USER_TABLE = "USER_TABLE";
	private final static String DATABASE_CREATE_USER_TABLE = "create table if not exists "
			+ USER_TABLE + "( email text primary key, name text not null)";

	public static final String COL_NAME = "name";
	public static final String COL_EMAIL = "email";

	/**
	 * The adapter constructor.
	 * 
	 * @param context
	 */
	public DatabaseAdapter(Context context) {
		DatabaseAdapter.DB_PATH = context.getApplicationInfo().dataDir
				+ "/databases/";
		dbHelper = new DatabaseHelper(context);
	}

	/**
	 * Creates the database helper and gets the database.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DatabaseAdapter open() throws SQLException {

		return this;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		Log.i("error message", "creating database");
		boolean dbExist = checkDataBase();
		Log.i("error message", "database checked");

		if (!dbExist) {
			dbHelper.getWritableDatabase();
			openDataBase();
			Log.i("error message", "database opened");
			database.execSQL(DB_TABLE);
			database.execSQL(DATABASE_CREATE_USER_TABLE);
			Log.i("error message", "table copied");
			close();
		} else {
			Log.i("error message", "database exists already");
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		Log.i("error message", "checking database");
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE
							| SQLiteDatabase.NO_LOCALIZED_COLLATORS);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * 
	 * Opens the database
	 * 
	 * @throws SQLException
	 */
	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		database = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE
						| SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	}

	/**
	 * Closes the database.
	 */
	public void close() {
		dbHelper.close();
	}

	/**
	 * Retrieves the details of all the users stored in the user table.
	 * 
	 * @return cursor with the content values id and category name inside the
	 *         category table
	 */
	public Cursor fetchAllUsers() {
		return database.query(USER_TABLE, new String[] { COL_NAME, COL_EMAIL },
				null, null, null, null, null);
	}

	public long createUser(String name, String email) {
		ContentValues initialValues = createUserTableContentValues(email, name);
		return database.insert(USER_TABLE, null, initialValues);
	}

	private ContentValues createUserTableContentValues(String email, String name) {
		ContentValues values = new ContentValues();
		values.put(COL_EMAIL, email);
		values.put(COL_NAME, name);
		return values;
	}

	public Boolean ifUserAlreadyExists(String email) {
		Cursor c = database.query("USER_TABLE", new String[] { "name" },
				"email=?", new String[] { email }, null, null, null);
		if (c.getCount() > 0) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}
	}
}
