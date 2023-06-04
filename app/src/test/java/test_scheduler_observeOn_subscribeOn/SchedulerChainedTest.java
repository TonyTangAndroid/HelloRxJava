package test_scheduler_observeOn_subscribeOn;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.Test;

/**
 * This is just an exhaustive unit test to assert the side effects of {@link
 * TestScheduler#triggerActions()} applied on {@link TestObserver} in different orders.
 *
 * <p>There are two observedOn and two subscribeOn of {@link TestScheduler}. In total, there are 24
 * combinations on the order when {@link TestScheduler#triggerActions()} is executed.
 *
 * <p>And interestingly, there are only one correct order, which is 3124, which comes to me as a
 * surprise for two reasons. 1, To start with, I do not how to interpret on my own why 3124 is able
 * to receive the item. 2, If 3124 is passed, then I would assume that 3142 should also be good.
 * Then to my suprise, 3124 is the only one combination.
 *
 * <p>Why am I spending my time and countless try on this topic?
 *
 * <p>{@link TestScheduler} indeed comes in handy in asserting RxJava behaviors in a deterministic
 * way until {@link Observable#subscribeOn} and {@link Observable#observeOn} is introduced. As it is
 * demonstrated in this unit test suite, there are only one way to write the unit test to receive
 * the item for this sample example. And it is mysterious enough. Imagine now we have to write test
 * code to cover the PROD code.
 */
public class SchedulerChainedTest {

  private final TestScheduler subscribeOn1 = new TestScheduler();
  private final TestScheduler observeOn2 = new TestScheduler();
  private final TestScheduler subscribeOn3 = new TestScheduler();
  private final TestScheduler observeOn4 = new TestScheduler();

  /**
   * A simple observer that is chained with two observedOn and two subscribeOn for assertion
   * purpose.
   */
  private final TestObserver<Long> interleaved =
      Observable.fromCallable(() -> 1L)
          .subscribeOn(subscribeOn1)
          .observeOn(observeOn2)
          .subscribeOn(subscribeOn3)
          .observeOn(observeOn4)
          .test();

  @Test
  public void interleaved_1234_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_1243_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  /**
   * Even reversing the order of subscribers will result in failure of receiving the expected item.
   */
  @Test
  public void interleaved_1324_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_1342_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_1423_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_1432_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2314_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2134_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2143_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2341_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2413_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_2431_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_3214_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(0);
  }

  /**
   * This turns out to be the only right away to trigger the scheduler's action to receive the item.
   */
  @Test
  public void interleaved_3124_SchedulerActionsTriggeredInRightOrder_WillReceiveItem() {
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    interleaved.assertValueCount(1);
  }

  /** Even adjust the order of observeOn will result in failure of receiving the expected item. */
  @Test
  public void interleaved_3142_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_3241_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_3412_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_3421_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    subscribeOn3.triggerActions();
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4213_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4123_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4132_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    subscribeOn1.triggerActions();
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4231_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    observeOn2.triggerActions();
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4312_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    subscribeOn1.triggerActions();
    observeOn2.triggerActions();
    interleaved.assertValueCount(0);
  }

  @Test
  public void interleaved_4321_SchedulerActionsTriggeredInRightOrder_WillNotReceiveItem() {
    observeOn4.triggerActions();
    subscribeOn3.triggerActions();
    observeOn2.triggerActions();
    subscribeOn1.triggerActions();
    interleaved.assertValueCount(0);
  }
}
