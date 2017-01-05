package com.gtecklabs.simplecounter.data.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.gtecklabs.simplecounter.data.database.ContentValuesBuilder;
import com.gtecklabs.simplecounter.data.database.CursorColumnMap;
import com.gtecklabs.simplecounter.data.database.DbOpenHelper.Field;
import com.gtecklabs.simplecounter.model.Count;

public class CountDao implements Dao<Count> {

  @Override
  public Count build(Cursor cursor) {
    final CursorColumnMap cc = new CursorColumnMap(cursor);

    return Count.builder()
//        .id(cc.getLong(Field.ID, 0))
        .title(cc.getString(Field.TITLE))
        .description(cc.getString(Field.DESCRIPTION))
        .value(cc.getFloat(Field.VALUE, 0f))
        .build();
  }

  @Override
  public ContentValues convert(Count count) {
    return new ContentValuesBuilder()
//        .putIfPositive(Field.ID, count.id())
        .put(Field.TITLE, count.title())
        .put(Field.DESCRIPTION, count.description())
        .put(Field.VALUE, count.value())
        .build();
  }
}
