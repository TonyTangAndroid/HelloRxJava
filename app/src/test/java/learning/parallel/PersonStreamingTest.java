package learning.parallel;

import com.google.common.truth.Truth;
import io.reactivex.Single;
import org.junit.Test;

public class PersonStreamingTest {

  @Test
  public void whenResolvedInSequence_willTakeLongerThan4000Milliseconds() {

    Single<ResolveResult> resolve =
        PersonStreaming.resolve(ResolveAction.create(ResolveType.SEQUENCE));
    ResolveResult resolveResult = resolve.blockingGet();
    Truth.assertThat(resolveResult.span.duration()).isAtLeast(4000);
  }

  @Test
  public void whenResolvedInParallel_willTakeShortThan2000Milliseconds() {

    Single<ResolveResult> resolve =
        PersonStreaming.resolve(ResolveAction.create(ResolveType.PARALLEL));
    ResolveResult resolveResult = resolve.blockingGet();
    Truth.assertThat(resolveResult.span.duration()).isAtMost(2000);
  }
}
