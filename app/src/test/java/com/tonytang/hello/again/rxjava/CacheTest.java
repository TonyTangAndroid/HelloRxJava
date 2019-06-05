package com.tonytang.hello.again.rxjava;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CacheTest {

  private Footprint footPrint;
  private Throwable throwable;

  @Before
  public void setup() {
    footPrint = mock(Footprint.class);
    throwable = mock(Throwable.class);
  }

  @Test
  public void without_cache_would_execute_footprint_three_times() {
    Single<String> source = Single.just("Hello").doOnSuccess(this::onSuccess);
    subscribe(source);
    verify(footPrint, times(3)).success("Hello");
  }

  @Test
  public void with_correct_placed_cache_would_execute_footprint_single_time() {
    Single<String> source = Single.just("Hello").doOnSuccess(this::onSuccess).cache();
    subscribe(source);
    verify(footPrint, times(1)).success("Hello");
  }

  @Test
  public void with_incorrect_placed_cache_would_still_execute_footprint_three_times() {
    Single<String> source = Single.just("Hello").cache().doOnSuccess(this::onSuccess);
    subscribe(source);
    verify(footPrint, times(3)).success("Hello");
  }

  @Test
  public void with_correct_placed_cache_would_execute_footprint_onError_single_time() {
    Single<Object> source = Single.error(throwable).doOnError(this::onError).cache();
    subscribeError(source);
    verify(footPrint, times(1)).error(throwable);
  }

  @Test
  public void with_incorrect_placed_cache_would_execute_footprint_onError_three_time() {
    Single<Object> source = Single.error(throwable).cache().doOnError(this::onError);
    subscribeError(source);
    verify(footPrint, times(3)).error(throwable);
  }

  private void subscribeError(Single<Object> source) {
    {
      TestObserver<Object> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertError(throwable);
    }

    {
      TestObserver<Object> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertError(throwable);
    }

    {
      TestObserver<Object> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertError(throwable);
    }
  }

  private void onError(Throwable throwable) {
    footPrint.error(throwable);
  }

  private void onSuccess(String value) {
    footPrint.success(value);
  }

  private void subscribe(Single<String> source) {
    {
      TestObserver<String> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertValues("Hello");
    }

    {
      TestObserver<String> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertValues("Hello");
    }

    {
      TestObserver<String> result = new TestObserver<>();
      source.subscribeWith(result);
      result.assertValues("Hello");
    }
  }
}