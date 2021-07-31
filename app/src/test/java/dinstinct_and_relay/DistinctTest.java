package dinstinct_and_relay;

import static com.google.common.truth.Truth.assertThat;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

public class DistinctTest {

  private final Relay<String> driveRelay = PublishRelay.create();
  private final Relay<Integer> slaveRelay = PublishRelay.create();

  @Test
  public void addition_isCorrect() {
    assertThat(2 + 2).isEqualTo(4);


    TestObserver<String> distinct = drive().withLatestFrom(slaveDistinctUntilChanged(), this::merge).test();
    TestObserver<String> raw = drive().withLatestFrom(slave(), this::merge).test();

    //This will NOT break the unit test
    slaveRelay.accept(1);


    raw.assertNoErrors();
    distinct.assertNoErrors();
    raw.assertNotComplete();
    distinct.assertNotComplete();
    raw.assertValueCount(0);
    distinct.assertValueCount(0);

    driveRelay.accept("tony");

    raw.assertValueCount(1);
    distinct.assertValueCount(1);
    raw.assertValue("tony:1");
    distinct.assertValue("tony:1");

    driveRelay.accept("tommy");
    raw.assertValueCount(2);
    distinct.assertValueCount(2);
    raw.assertValues("tony:1", "tommy:1");
    distinct.assertValues("tony:1", "tommy:1");

  }

  private String merge(String drive, int slave) {
    return drive + ":" + slave;
  }


  public Observable<String> drive() {
    return driveRelay.hide();
  }

  public Observable<Integer> slave() {
    return slaveRelay.hide().replay(1).refCount();
  }

  public Observable<Integer> slaveDistinctUntilChanged() {
    return slave().filter(index -> index % 2 == 1).distinctUntilChanged();
  }

}