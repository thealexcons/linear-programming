package test;

import main.constraints.Constraint2D;
import main.constraints.ConstraintSign;
import main.objective_functions.ObjectiveFunction2D;
import main.optimisers.OptimisationType;
import main.optimisers.Optimiser2D;
import main.other.Coordinate2D;
import main.other.Result;
import org.junit.Assert;
import org.junit.Test;

public class Optimiser2DTests {

  @Test
  public void problem1() {
    Optimiser2D optimiser =
        new Optimiser2D(
            new ObjectiveFunction2D(3, 2),
            OptimisationType.MAXIMISE,
            new Constraint2D[] {
              new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 2),
              new Constraint2D(1, 2, ConstraintSign.LESS_THAN_OR_EQUAL, 25),
              new Constraint2D(-2, 4, ConstraintSign.GREATER_THAN_OR_EQUAL, -8),
              new Constraint2D(-2, 1, ConstraintSign.LESS_THAN_OR_EQUAL, -5),
              new Constraint2D(1, 0, ConstraintSign.GREATER_THAN_OR_EQUAL, 5),
            },
            false);

    Result<Coordinate2D, Double> solution = optimiser.solve();
    Assert.assertEquals(new Coordinate2D(14.5, 5.25), solution.getFirst());
    Assert.assertEquals(54.0, solution.getSecond(), 0.0001);
  }

  @Test
  public void problem1IntegerOnly() {
    Optimiser2D optimiser =
            new Optimiser2D(
                    new ObjectiveFunction2D(3, 2),
                    OptimisationType.MAXIMISE,
                    new Constraint2D[] {
                            new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 2),
                            new Constraint2D(1, 2, ConstraintSign.LESS_THAN_OR_EQUAL, 25),
                            new Constraint2D(-2, 4, ConstraintSign.GREATER_THAN_OR_EQUAL, -8),
                            new Constraint2D(-2, 1, ConstraintSign.LESS_THAN_OR_EQUAL, -5),
                            new Constraint2D(1, 0, ConstraintSign.GREATER_THAN_OR_EQUAL, 5),
                    },
                    true);

    Result<Coordinate2D, Double> solution = optimiser.solve();
    Assert.assertEquals(new Coordinate2D(14, 5), solution.getFirst());
    Assert.assertEquals(52, solution.getSecond(), 0.0001);
  }

  @Test
  public void problem1MinimiseIntegerOnly() {
    Optimiser2D optimiser =
            new Optimiser2D(
                    new ObjectiveFunction2D(3, 2),
                    OptimisationType.MINIMISE,
                    new Constraint2D[] {
                            new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 2),
                            new Constraint2D(1, 2, ConstraintSign.LESS_THAN_OR_EQUAL, 25),
                            new Constraint2D(-2, 4, ConstraintSign.GREATER_THAN_OR_EQUAL, -8),
                            new Constraint2D(-2, 1, ConstraintSign.LESS_THAN_OR_EQUAL, -5),
                    },
                    true);

    Result<Coordinate2D, Double> solution = optimiser.solve();
    Assert.assertEquals(new Coordinate2D(4, 2), solution.getFirst());
    Assert.assertEquals(16, solution.getSecond(), 0.0001);
  }


  @Test
  public void problem2() {
    Optimiser2D optimiser =
            new Optimiser2D(
                    new ObjectiveFunction2D(1, 3),
                    OptimisationType.MINIMISE,
                    new Constraint2D[] {
                            new Constraint2D(-1, 1, ConstraintSign.LESS_THAN_OR_EQUAL, 0),
                            new Constraint2D(3, 5, ConstraintSign.GREATER_THAN_OR_EQUAL, 60),
                            new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 5),
                            new Constraint2D(1, 0, ConstraintSign.LESS_THAN_OR_EQUAL, 13),
                    },
                    false);

    Result<Coordinate2D, Double> solution = optimiser.solve();
    Assert.assertEquals(26 + 2/3.0, solution.getSecond(), 0.0001);
  }

  @Test
  public void problem2Maximise() {
    Optimiser2D optimiser =
            new Optimiser2D(
                    new ObjectiveFunction2D(1, 3),
                    OptimisationType.MAXIMISE,
                    new Constraint2D[] {
                            new Constraint2D(-1, 1, ConstraintSign.LESS_THAN_OR_EQUAL, 0),
                            new Constraint2D(3, 5, ConstraintSign.GREATER_THAN_OR_EQUAL, 60),
                            new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 5),
                            new Constraint2D(1, 0, ConstraintSign.LESS_THAN_OR_EQUAL, 13),
                    },
                    false);

    Result<Coordinate2D, Double> solution = optimiser.solve();
    Assert.assertEquals(new Coordinate2D(13, 13), solution.getFirst());
    Assert.assertEquals(52, solution.getSecond(), 0.0001);
  }


}
