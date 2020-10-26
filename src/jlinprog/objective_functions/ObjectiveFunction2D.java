package jlinprog.objective_functions;

import jlinprog.other.Coordinate2D;

public class ObjectiveFunction2D extends ObjectiveFunction {

  public ObjectiveFunction2D(int xCoefficient, int yCoefficient) {
      super(new int[]{xCoefficient, yCoefficient});
  }

  public double evaluate(Coordinate2D coord) {
    return coefficients[0] * coord.getX() + coefficients[1] * coord.getY();
  }
}
