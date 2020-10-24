package main.constraints;

import main.other.Coordinate;

public class Constraint2D extends Constraint {

  public Constraint2D(int xCoefficient, int yCoefficient, ConstraintSign sign, int constant) {
    super(new int[] {xCoefficient, yCoefficient}, sign, constant);
  }

  public int getXCoefficient() {
    return super.getCoefficients()[0];
  }

  public int getYCoefficient() {
    return super.getCoefficients()[1];
  }

  /* Checks if a Coordinate satisfies the constraint  */
  public boolean evaluate(Coordinate coord) {
    switch (getSign()) {
      case LESS_THAN:
        return getYCoefficient() * coord.getY() < - getXCoefficient() * coord.getX() + getConstant();
      case LESS_THAN_OR_EQUAL:
        return getYCoefficient() * coord.getY() <= - getXCoefficient() * coord.getX() + getConstant();
      case GREATER_THAN:
        return getYCoefficient() * coord.getY() > - getXCoefficient() * coord.getX() + getConstant();
      case GREATER_THAN_OR_EQUAL:
        return getYCoefficient() * coord.getY() >= - getXCoefficient() * coord.getX() + getConstant();
      case EQUAL:
        return getYCoefficient() * coord.getY() == - getXCoefficient() * coord.getX() + getConstant();
    }
    return false; // won't be reached
  }

  /* Returns coordinate of intersection with another constraint (treated as line) or null if they do not meet */
  public Coordinate intersectionWith(Constraint2D other) {
    int x1 = getXCoefficient(), x2 = other.getXCoefficient();
    int y1 = getYCoefficient(), y2 = other.getYCoefficient();

    // Check edge case where one of the functions is a vertical line, of the form x = k
    if (getYCoefficient() == 0) {
      return new Coordinate(getConstant(), other.evaluateAsLine(getConstant()));
    } else if (other.getYCoefficient() == 0) {
      return new Coordinate(other.getConstant(), evaluateAsLine(other.getConstant()));
    }

    try {
      double xCoeff = -(x1 / (double) y1) + (x2 / (double) y2);
      checkNotZeroDivisionError(xCoeff);

      double constVal = (getConstant() / (double) y1) - (other.getConstant() / (double) y2);
      checkNotZeroDivisionError(constVal);

      double xVal = - constVal / xCoeff;
      checkNotZeroDivisionError(xVal);

      double yVal = evaluateAsLine(xVal);
      checkNotZeroDivisionError(yVal);

      return xVal < 0 || yVal < 0 ? null : new Coordinate(xVal, yVal);
    } catch (ArithmeticException e) {
      return null;
    }
  }

  /* Internal helper function for evaluating the y value given an x, treated as a line */
  private double evaluateAsLine(double xVal) {
    assert getYCoefficient() != 0;
    return (-getXCoefficient() * xVal + getConstant()) / (double) getYCoefficient();
  }

  private void checkNotZeroDivisionError(double value) {
    if (Double.isInfinite(value) || Double.isNaN(value)) {
      throw new ArithmeticException();
    }
  }
}
