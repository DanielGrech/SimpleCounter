package com.gtecklabs.simplecounter.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.gtecklabs.simplecounter.model.Count;

public class DaoFactory {

  private CountDao mCountDao;

  public <T> ContentValues convert(T obj) {
    return getDao((Class<T>) obj.getClass()).convert(obj);
  }

  public <T> T build(Class<T> cls, Cursor cursor) {
    return getDao(cls).build(cursor);
  }

  private <T> Dao<T> getDao(Class<T> cls) {
    if (Count.class.isAssignableFrom(cls)) {
      if (mCountDao == null) {
        mCountDao = new CountDao();
      }
      return (Dao<T>) mCountDao;
    }

    throw new IllegalArgumentException("No Dao for class: " + cls.getSimpleName());
  }
}