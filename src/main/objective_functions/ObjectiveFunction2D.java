package main.objective_functions;

import main.other.Coordinate;

public class ObjectiveFunction2D extends ObjectiveFunction {

  public ObjectiveFunction2D(int xCoefficient, int yCoefficient) {
      super(new int[]{xCoefficient, yCoefficient});
  }

  public double evaluate(Coordinate coord) {
    return coefficients[0] * coord.getX() + coefficients[1] * coord.getY();
  }
}
