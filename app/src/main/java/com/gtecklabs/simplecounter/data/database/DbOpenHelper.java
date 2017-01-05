package com.gtecklabs.simplecounter.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import timber.log.Timber;

import java.lang.reflect.Modifier;

public class DbOpenHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "wua.db";
  private static final int DB_VERSION = 1;

  private static final Object[] LOCK = new Object[0];

  public DbOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(final SQLiteDatabase db) {
    runForEachTable(new TableRunnable() {
      @Override
      public void run(final DbTable table) {
        db.execSQL(table.getCreateSql());

        final String[] postCreateSql = table.getPostCreateSql();
        if (postCreateSql != null) {
          for (String sql : postCreateSql) {
            db.execSQL(sql);
          }
        }
      }
    });
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //TODO: Support db upgrade...
    throw new IllegalStateException("Havent implemented db upgrades yet!");
  }

  @Override
  public SQLiteDatabase getReadableDatabase() {
    synchronized (LOCK) {
      return super.getReadableDatabase();
    }
  }

  @Override
  public SQLiteDatabase getWritableDatabase() {
    synchronized (LOCK) {
      return super.getWritableDatabase();
    }
  }

  /**
   * Execute a given against each table in the database
   *
   * @param runnable The task to perform
   */
  private void runForEachTable(TableRunnable runnable) {
    java.lang.reflect.Field[] declaredFields = Table.class.getDeclaredFields();
    for (java.lang.reflect.Field field : declaredFields) {
      if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(DbTable.class)) {
        try {
          runnable.run((DbTable) field.get(null));
        } catch (IllegalAccessException e) {
          Timber.e(e, "Error executing table runnable: " + field.getName());
        }
      }
    }
  }

  /**
   * Encapsulates a task to be run against a table
   */
  private interface TableRunnable {
    /**
     * Execute the request task
     *
     * @param table The table to execute the task on
     */
    void run(DbTable table);
  }

  public static class Field {
    public static final DbField ID = new DbField("_id", "integer", "primary key");
    public static final DbField TITLE = new DbField("title", "text");
    public static final DbField DESCRIPTION = new DbField("description", "text");
    public static final DbField VALUE = new DbField("value", "real");
  }

  /**
   * Application database tables
   */
  public static class Table {
    public static DbTable COUNTS = DbTable.with("friends")
        .columns(
            Field.ID,
            Field.TITLE,
            Field.DESCRIPTION,
            Field.VALUE
        )
        .create();
  }
}
