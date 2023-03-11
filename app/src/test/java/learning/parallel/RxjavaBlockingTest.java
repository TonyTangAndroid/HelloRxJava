package learning.parallel;

import androidx.annotation.NonNull;
import com.google.common.truth.Truth;
import io.reactivex.Maybe;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RxjavaBlockingTest {

  private static final int MIN_TIMEOUT = 2000;

  @Rule
  public Timeout timeout =
      new Timeout(MIN_TIMEOUT, TimeUnit.MILLISECONDS) {
        @NonNull
        public Statement apply(Statement base, Description description) {
          return getFailOnTimeout(base);
        }
      };

  private FailOnTimeout getFailOnTimeout(Statement base) {
    //noinspection deprecation
    return new FailOnTimeout(base, MIN_TIMEOUT) {
      @Override
      public void evaluate() throws Throwable {
        try {
          super.evaluate();
          throw new TimeoutException();
        } catch (Exception e) {
        }
      }
    };
  }

  @Test(timeout = MIN_TIMEOUT)
  public void observableNeverWithTimeout3SecondsBlockingGet_willTake3Seconds() {
    long start = System.currentTimeMillis();
    Maybe<Object> timeout = Maybe.never().timeout(1500, TimeUnit.MILLISECONDS);
    timeout.onErrorComplete().ignoreElement().blockingAwait();
    long end = System.currentTimeMillis();
    Truth.assertThat((end - start)).isAtLeast(1500);
  }

  @Test(expected = TimeoutException.class)
  public void observableNeverWithoutTimeoutBlockingGet_willTimeout() {
    long start = System.currentTimeMillis();
    Maybe<Object> timeout = Maybe.never();
    timeout.onErrorComplete().ignoreElement().blockingAwait();
    long end = System.currentTimeMillis();
    Truth.assertThat((end - start)).isAtLeast(MIN_TIMEOUT);
  }
}
