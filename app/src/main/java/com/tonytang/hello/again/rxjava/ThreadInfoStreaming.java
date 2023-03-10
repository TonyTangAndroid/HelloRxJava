package com.tonytang.hello.again.rxjava;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;

public class ThreadInfoStreaming {

  private ThreadInfoStreaming() {
  }

  static Observable<ThreadInfo> eventStreaming() {
    return Observable.merge(throttleFirst(), throttleLast());
  }

  private static Observable<ThreadInfo> throttleFirst() {
    return rawStream()
        .throttleFirst(1, TimeUnit.SECONDS)
        .map(iteration -> new ThreadInfo(iteration, Source.THROTTLE_FIRST,
            currentThreadName()));
  }

  private static Observable<ThreadInfo> throttleLast() {
    return rawStream()
        .throttleLast(1, TimeUnit.SECONDS)
        .map(iteration -> new ThreadInfo(iteration, Source.THROTTLE_LAST,
            currentThreadName()));
  }

  private static String currentThreadName() {
    return Thread.currentThread().getName();
  }

  private static Observable<Long> rawStream() {
    return Observable.interval(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread());
  }
}
