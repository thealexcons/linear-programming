package main.other;

import java.util.Arrays;

public class Vector {

  private final double[] values;

  public Vector(double[] values) {
    this.values = values;
  }

  public double[] getValues() {
    return values;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Vector)) {
      return false;
    }
    return Arrays.equals(values, ((Vector) obj).getValues());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
