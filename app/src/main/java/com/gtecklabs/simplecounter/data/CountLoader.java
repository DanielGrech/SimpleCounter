package com.gtecklabs.simplecounter.data;

import android.database.Cursor;
import com.gtecklabs.simplecounter.data.database.DbOpenHelper;
import com.gtecklabs.simplecounter.data.database.dao.DaoFactory;
import com.gtecklabs.simplecounter.model.Count;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import rx.Observable;
import rx.functions.Func1;

import javax.inject.Inject;
import java.util.List;

public class CountLoader {

  private static final String COUNT_TABLE_NAME = DbOpenHelper.Table.COUNTS.getName();

  @Inject
  BriteDatabase mDatabase;

  @Inject
  DaoFactory mDaoFactory;

  @Inject
  CountLoader() {
    // For DI only
  }

  private final Func1<Cursor, Count> CURSOR_COUNT_MAPPING = new Func1<Cursor, Count>() {
    @Override
    public Count call(Cursor cursor) {
      return mDaoFactory.build(Count.class, cursor);
    }
  };

  public Observable<List<Count>> getAllCounts() {
    final QueryObservable query =
        mDatabase.createQuery(COUNT_TABLE_NAME, "SELECT * FROM ?", COUNT_TABLE_NAME);

    return query.mapToList(CURSOR_COUNT_MAPPING);
  }

  public Observable<Count> getCount(long id) {
    final QueryObservable query =
        mDatabase.createQuery(
            COUNT_TABLE_NAME,
            "SELECT * FROM ? WHERE ? = ?",
            COUNT_TABLE_NAME,
            DbOpenHelper.Field.ID.getName(),
            String.valueOf(id));
    return query.mapToOne(CURSOR_COUNT_MAPPING);
  }
}
