package dinstinct_and_relay;

import static com.google.common.truth.Truth.assertThat;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

public class DistinctTest {
  private final SlaveStream slaveStream = new SlaveStream();

  private final Relay<String> driveRelay = PublishRelay.create();

  @Test(expected = AssertionError.class)
  public void addition_isCorrect() {

    assertThat(2 + 2).isEqualTo(4);

    TestObserver<String> distinct =
        drive().withLatestFrom(slaveDistinctUntilChanged(), this::merge).test();

    // This will break the unit test as the initial subscriber disposed.
    distinct.dispose();
    slaveStream.accept(1);

    distinct = drive().withLatestFrom(slaveDistinctUntilChanged(), this::merge).test();
    TestObserver<String> raw = drive().withLatestFrom(slave(), this::merge).test();

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

  private Observable<String> drive() {
    return driveRelay.hide();
  }

  private Observable<Integer> slave() {
    return slaveStream.slave();
  }

  public Observable<Integer> slaveDistinctUntilChanged() {
    return slave().filter(index -> index % 2 == 1).distinctUntilChanged();
  }
}
