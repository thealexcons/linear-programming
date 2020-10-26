package jlinprog.other;

public class Result<E, T> {
  private final E first;
  private final T second;

  public Result(E first, T second) {
    this.first = first;
    this.second = second;
  }

  public E getFirst() {
    return first;
  }

  public T getSecond() {
    return second;
  }
}
