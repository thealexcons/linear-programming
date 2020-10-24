package main.optimisers;

import main.constraints.Constraint2D;
import main.objective_functions.ObjectiveFunction2D;
import main.other.Coordinate;
import main.other.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Optimiser2D {

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
  public Pair<Coordinate, Double> solve() {

    // Find coordinates of all the intersections of the constraints as linear functions
    List<Coordinate> intersections = new ArrayList<>();
    for (int i = 0; i < constraints.length; i++) {
      for (int j = i + 1; j < constraints.length; j++) {
        Coordinate coord = constraints[i].intersectionWith(constraints[j]);
        if (coord != null) {
          intersections.add(coord);
        }
      }
    }

    // Filter all the coordinates that lie within the feasible region
    intersections =
        intersections.stream().filter(this::withinFeasibleRegion).collect(Collectors.toList());

    System.out.println(intersections);

    Pair<Coordinate, Double> optimalSolution = getOptimalSolution(intersections);

    if (integerOnly) {
      return getOptimalIntegerSolution(optimalSolution.getFirst());
    }

    return optimalSolution;
  }

  /* Checks the four integer points around the given coordinate and returns the optimal one */
  private Pair<Coordinate, Double> getOptimalIntegerSolution(Coordinate optimalCoordinate) {
    List<Coordinate> integerCoords =
        Arrays.asList(
            new Coordinate(
                Math.ceil(optimalCoordinate.getX()), Math.ceil(optimalCoordinate.getY())),
            new Coordinate(
                Math.ceil(optimalCoordinate.getX()), Math.floor(optimalCoordinate.getY())),
            new Coordinate(
                Math.floor(optimalCoordinate.getX()), Math.ceil(optimalCoordinate.getY())),
            new Coordinate(
                Math.floor(optimalCoordinate.getX()), Math.floor(optimalCoordinate.getY())));

    integerCoords =
        integerCoords.stream().filter(this::withinFeasibleRegion).collect(Collectors.toList());

    Pair<Coordinate, Double> solution = getOptimalSolution(integerCoords);

    return new Pair<>(solution.getFirst(), solution.getSecond());
  }

  private Pair<Coordinate, Double> getOptimalSolution(List<Coordinate> coordinates) {
    Coordinate optimalCoordinate = null;
    double optimalValue;
    if (optimisationType == OptimisationType.MINIMISE) {
      optimalValue = Double.POSITIVE_INFINITY;
      for (Coordinate coord : coordinates) {
        double val = objectiveFunction.evaluate(coord);
        if (val < optimalValue) {
          optimalValue = val;
          optimalCoordinate = coord;
        }
      }
    } else {
      optimalValue = Double.NEGATIVE_INFINITY;
      for (Coordinate coord : coordinates) {
        double val = objectiveFunction.evaluate(coord);
        if (val > optimalValue) {
          optimalValue = val;
          optimalCoordinate = coord;
        }
      }
    }

    return new Pair<>(optimalCoordinate, optimalValue);
  }

  private boolean withinFeasibleRegion(Coordinate coord) {
    for (Constraint2D constraint : constraints) {
      if (!constraint.evaluate(coord)) {
        return false;
      }
    }
    return true;
  }
}
