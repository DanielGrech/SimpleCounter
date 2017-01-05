package com.gtecklabs.simplecounter.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Converts objects between their java and
 * database representations (as well as visa versa)
 */
public interface Dao<T> {

  /**
   * Convert the given content values into a java object
   *
   * @param cursor Cursor holding the object data
   * @return An object constructed from <code>values</code>
   */
  T build(Cursor cursor);

  /**
   * Convert the given object into its database representation
   *
   * @param object The object to convert
   * @return A {@link ContentValues} instance representing the object
   */
  ContentValues convert(T object);
}