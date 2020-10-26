package jlinprog.optimisers;

import jlinprog.constraints.Constraint2D;
import jlinprog.objective_functions.ObjectiveFunction2D;
import jlinprog.other.Coordinate2D;
import jlinprog.other.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Optimiser2D implements Optimiser {

  private final ObjectiveFunction2D objectiveFunction;
  private final OptimisationType optimisationType;
  private final Constraint2D[] constraints;
  private final boolean integerOnly;

  public Optimiser2D(ObjectiveFunction2D objectiveFunction, OptimisationType optimisationType,
                        Constraint2D[] constraints, boolean integerOnly) {
    this.objectiveFunction = objectiveFunction;
    this.optimisationType = optimisationType;
    this.constraints = constraints;
    this.integerOnly = integerOnly;
  }

  /* Solves a 2D linear programming problem, returning the optimal coordinate and optimal value */
  public Result<Coordinate2D, Double> solve() {

    // Find coordinates of all the intersections of the constraints as linear functions
    List<Coordinate2D> intersections = new ArrayList<>();
    for (int i = 0; i < constraints.length; i++) {
      for (int j = i + 1; j < constraints.length; j++) {
        Coordinate2D coord = constraints[i].intersectionWith(constraints[j]);
        if (coord != null) {
          intersections.add(coord);
        }
      }
    }

    // Filter all the coordinates that lie within the feasible region
    intersections =
        intersections.stream().filter(this::withinFeasibleRegion).collect(Collectors.toList());


    Result<Coordinate2D, Double> optimalSolution = getOptimalSolution(intersections);

    if (integerOnly) {
      return getOptimalIntegerSolution(optimalSolution.getFirst());
    }

    return optimalSolution;
  }

  /* Checks the four integer points around the given coordinate and returns the optimal one */
  private Result<Coordinate2D, Double> getOptimalIntegerSolution(Coordinate2D optimalCoordinate) {
    List<Coordinate2D> integerCoords =
        Arrays.asList(
            new Coordinate2D(
                Math.ceil(optimalCoordinate.getX()), Math.ceil(optimalCoordinate.getY())),
            new Coordinate2D(
                Math.ceil(optimalCoordinate.getX()), Math.floor(optimalCoordinate.getY())),
            new Coordinate2D(
                Math.floor(optimalCoordinate.getX()), Math.ceil(optimalCoordinate.getY())),
            new Coordinate2D(
                Math.floor(optimalCoordinate.getX()), Math.floor(optimalCoordinate.getY())));

    integerCoords =
        integerCoords.stream().filter(this::withinFeasibleRegion).collect(Collectors.toList());

    Result<Coordinate2D, Double> solution = getOptimalSolution(integerCoords);

    return new Result<>(solution.getFirst(), solution.getSecond());
  }

  /* Returns the optimal coordinate and value from a list of coordinates */
  private Result<Coordinate2D, Double> getOptimalSolution(List<Coordinate2D> coordinates) {
    Coordinate2D optimalCoordinate = null;
    double optimalValue;
    if (optimisationType == OptimisationType.MINIMISE) {
      optimalValue = Double.POSITIVE_INFINITY;
      for (Coordinate2D coord : coordinates) {
        double val = objectiveFunction.evaluate(coord);
        if (val < optimalValue) {
          optimalValue = val;
          optimalCoordinate = coord;
        }
      }
    } else {
      optimalValue = Double.NEGATIVE_INFINITY;
      for (Coordinate2D coord : coordinates) {
        double val = objectiveFunction.evaluate(coord);
        if (val > optimalValue) {
          optimalValue = val;
          optimalCoordinate = coord;
        }
      }
    }

    return new Result<>(optimalCoordinate, optimalValue);
  }

  /* Checks if a coordinate is within the feasible region defined by the constraints */
  private boolean withinFeasibleRegion(Coordinate2D coord) {
    for (Constraint2D constraint : constraints) {
      if (!constraint.evaluate(coord)) {
        return false;
      }
    }
    return true;
  }
}
