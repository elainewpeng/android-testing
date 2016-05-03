package com.joydroid.testme.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import java.util.HashMap;

public class MyProvider extends ContentProvider {
  public static final String AUTHORITY = "com.joydroid.testme.provider";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  public enum ProviderUri {
    POJO("pojo", 1001);
    private final String path;
    final int code;

    ProviderUri(String path, int code) {
      this.code = code;
      this.path = path;
    }

    Uri uri() {
      return Uri.withAppendedPath(MyProvider.CONTENT_URI, path);
    }
  }

  private static final String DB_NAME = "testme.db";
  private static final int DB_VERSION = 1;

  private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH) {
    {
      addURI(ProviderUri.POJO);
    }

    private void addURI(ProviderUri providerUri) {
      addURI(AUTHORITY, providerUri.path, providerUri.code);
    }
  };

  private static final HashMap<String, String> PROJECTION_MAP = new HashMap<String, String>() {{
    put("_id", "_id");
    put("name", "name");
  }};

  private DatabaseHelper databaseHelper;

  @Override public int delete(Uri uri, String where, String[] whereArgs) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();

    if (URI_MATCHER.match(uri) == ProviderUri.POJO.code) {
      return db.delete("pojo", where, whereArgs);
    } else {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  @Override public String getType(Uri uri) {
    throw new UnsupportedOperationException("Not supported - not used in this example");
  }

  @Override public Uri insert(Uri uri, ContentValues values) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (URI_MATCHER.match(uri) == ProviderUri.POJO.code) {
      long rowId = insertAllowingEmptyContentValues(db, values, "pojo");
      return Uri.parse(uri + "/" + rowId);
    } else {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  private long insertAllowingEmptyContentValues(SQLiteDatabase db, ContentValues values, String table) {
    DatabaseUtils.InsertHelper insertHelper = null;
    try {
      insertHelper = new DatabaseUtils.InsertHelper(db, table);
      return insertHelper.insert(values);
    } finally {
      if (insertHelper != null) {
        insertHelper.close();
      }
    }
  }

  @Override public boolean onCreate() {
    Context context = getContext();
    databaseHelper = new DatabaseHelper(context);
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

    if (URI_MATCHER.match(uri) == ProviderUri.POJO.code) {
      qb.setTables("pojo");
      qb.setProjectionMap(PROJECTION_MAP);
    } else {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }

    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not supported - not used in this example");
  }

  static final class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
      db.execSQL("CREATE TABLE pojo (" +
          "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
          "name VARCHAR(255)" +
          ")");

      db.execSQL("INSERT INTO pojo VALUES (1, 'Donald')");
      db.execSQL("INSERT INTO pojo VALUES (2, 'Mickey')");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      throw new UnsupportedOperationException("Not supported - not used in this example");
    }
  }
}
