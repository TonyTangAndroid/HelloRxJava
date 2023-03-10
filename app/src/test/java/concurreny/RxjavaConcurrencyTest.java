package concurreny;

import static com.google.common.truth.Truth.assertThat;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import dinstinct_and_relay.SlaveStream;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RxjavaConcurrencyTest {
  private static final Logger log = LogManager.getLogger(RxjavaConcurrencyTest.class);

  @Test
  public void addition_isCorrect() {
    Flowable<UUID> ids = Flowable
        .fromCallable(UUID::randomUUID)
        .repeat()
        .take(100);

    Flowable<Person> people = ids
        .subscribeOn(Schedulers.io())
        .map(id -> slowLoadBy(id));

//    ids.subscribe(this::slowLoadBy);

  }

  @Test
  public void broken4() {
    Flowable<UUID> ids = Flowable
        .fromCallable(UUID::randomUUID)
        .repeat()
        .take(100);

    Flowable<Person> people = ids
        .subscribeOn(Schedulers.io())
        .flatMap(id -> asyncLoadBy(id)); //BROKEN

  }

  @Test
  public void broken5() {
    log.info("Setup");
    Flowable<String> blocking = Flowable
        .fromCallable(() -> {
          log.info("Customized thread name 2:"+ Thread.currentThread().getName());
          log.info("Starting");
          TimeUnit.SECONDS.sleep(1);
          log.info("Done");
          return "Hello, world!";
        }).subscribeOn(Schedulers.io());
    log.info("Created");
    log.info("Customized thread name 1:"+ Thread.currentThread().getName());
    blocking.subscribe(s -> log.info("Received {}", s));
    log.info("Done");

  }
  Flowable<Person> asyncLoadBy(UUID id) {
    return Flowable.fromCallable(() -> slowLoadBy(id));
  }
  Person slowLoadBy(UUID id) {
    return new Person(id.toString());
  }


}