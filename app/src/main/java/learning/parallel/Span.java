package learning.parallel;

public class Span {

  private final long startTime;
  private final long endTime;

  public Span(long startTime, long endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public long duration() {
    return endTime- startTime ;
  }

}
