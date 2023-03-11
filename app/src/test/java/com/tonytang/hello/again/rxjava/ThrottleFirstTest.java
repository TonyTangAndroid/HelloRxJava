package com.tonytang.hello.again.rxjava;

import com.google.common.truth.Truth;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class ThrottleFirstTest {

  @Test
  public void without_cache_would_execute_footprint_three_times() {

    TestObserver<Integer> test =
        Observable.just(1).throttleLatest(1, TimeUnit.SECONDS, Schedulers.computation()).test();
    Thread thread = test.lastThread();
    Truth.assertThat(thread.getName()).isEqualTo("Test worker");
  }
}
