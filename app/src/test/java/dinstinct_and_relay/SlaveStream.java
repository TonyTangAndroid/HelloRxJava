package dinstinct_and_relay;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;

public class SlaveStream {

  private final Observable<Integer> observable;
  private final Relay<Integer> slaveRelay;

  public SlaveStream() {
    slaveRelay = PublishRelay.create();
    observable = slaveRelay.hide().replay(1).refCount();
  }

  public Observable<Integer> slave() {
    return observable;
  }

  public void accept(int item) {
    slaveRelay.accept(item);
  }
}
