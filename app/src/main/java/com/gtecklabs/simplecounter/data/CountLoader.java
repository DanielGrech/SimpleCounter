package com.gtecklabs.simplecounter.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gtecklabs.simplecounter.data.database.DbOpenHelper;
import com.gtecklabs.simplecounter.data.database.dao.DaoFactory;
import com.gtecklabs.simplecounter.model.Count;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;

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
    final QueryObservable query = mDatabase.createQuery(COUNT_TABLE_NAME, null);
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

  public Observable<Count> saveCount(final Count count) {
    return Observable.fromCallable(new Callable<Count>() {
      @Override
      public Count call() throws Exception {
        final long newId = mDatabase.insert(
            COUNT_TABLE_NAME,
            mDaoFactory.convert(count),
            SQLiteDatabase.CONFLICT_REPLACE);
        if (newId == count.id()) {
          return count;
        } else {
          return count.toBuilder().id(newId).build();
        }
      }
    });
  }

}
