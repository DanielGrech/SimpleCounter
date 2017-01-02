package com.gtecklabs.simplecounter.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import java.util.concurrent.TimeUnit;

public class RxUtils {

  public static <T> Observable.Transformer<T, T> executeImmeadiately() {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> observable) {
        return observable
            .subscribeOn(Schedulers.immediate())
            .observeOn(Schedulers.immediate())
            .unsubscribeOn(Schedulers.immediate());
      }
    };
  }

  public static <T> Observable.Transformer<T, T> executeOnIoThread() {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> observable) {
        return observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io());
      }
    };
  }

  public static <T> Observable.Transformer<T, T> retry(final int retryCount, final long exponentialDelayBase) {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> observable) {
        return observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
          @Override
          public Observable<?> call(Observable<? extends Throwable> input) {
            return input.zipWith(
                Observable.range(1, retryCount),
                new Func2<Throwable, Integer, Integer>() {
                  @Override
                  public Integer call(Throwable throwable, Integer attemptNumber) {
                    return attemptNumber;
                  }
                }).flatMap(new Func1<Integer, Observable<?>>() {
              @Override
              public Observable<?> call(Integer attemptNumber) {
                final long seconds = (long) Math.pow(exponentialDelayBase, attemptNumber);
                Timber.e("Error saving push token. Retrying for the %s time in %s seconds", attemptNumber, seconds);
                return Observable.timer(seconds, TimeUnit.SECONDS);
              }
            });
          }
        });
      }
    };
  }
}
