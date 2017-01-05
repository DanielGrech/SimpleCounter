package com.gtecklabs.simplecounter.di;

import android.content.Context;
import com.gtecklabs.simplecounter.data.database.DbOpenHelper;
import com.gtecklabs.simplecounter.data.database.dao.DaoFactory;
import com.gtecklabs.simplecounter.di.annotations.ForApplicationContext;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import javax.inject.Singleton;

@Module(includes = {
    AndroidModule.class,
})
public class DataModule {

  @Provides
  @Singleton
  DaoFactory providesDaoFactory() {
    return new DaoFactory();
  }

  @Provides
  @Singleton
  DbOpenHelper providesDbOpenHelper(@ForApplicationContext Context context) {
    return new DbOpenHelper(context);
  }

  @Provides
  @Singleton
  SqlBrite providesSqlBrite() {
    return new SqlBrite.Builder()
        .logger(new SqlBrite.Logger() {
          @Override
          public void log(String message) {
            Timber.d(message);
          }
        })
        .build();
  }

  @Provides
  @Singleton
  BriteDatabase providesSqlBriteDatabase(SqlBrite sqlBrite, DbOpenHelper dbOpenHelper) {
    return sqlBrite.wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
  }
}
