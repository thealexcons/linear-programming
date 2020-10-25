package test;

import main.constraints.Constraint;
import main.constraints.ConstraintSign;
import main.objective_functions.ObjectiveFunction;
import main.optimisers.OptimisationType;
import main.optimisers.OptimiserSimplex;

import main.other.Result;
import main.other.Vector;
import org.junit.Assert;
import org.junit.Test;

public class OptimiserSimplexTests {

  @Test
  // tutorial question page 110
  public void problem1() {
    OptimiserSimplex optimiser =
        new OptimiserSimplex(
            new ObjectiveFunction(new int[] {10, 12, 8}),
            OptimisationType.MAXIMISE,
            new Constraint[] {
              new Constraint(new int[] {2, 2, 0}, ConstraintSign.LESS_THAN_OR_EQUAL, 5),
              new Constraint(new int[] {5, 3, 4}, ConstraintSign.LESS_THAN_OR_EQUAL, 15),
            },
            false);

    Result<Vector, Double> solution = optimiser.solve();
    Assert.assertEquals(new Vector(new double[] {0, 2.5, 1.875, 0, 0}), solution.getFirst());
    Assert.assertEquals(45.0, solution.getSecond(), 0.0001);
  }

  @Test
  // next page (not working)
  public void problem2() {
    OptimiserSimplex optimiser =
            new OptimiserSimplex(
                    new ObjectiveFunction(new int[] {3, 4, -5}),
                    OptimisationType.MAXIMISE,
                    new Constraint[] {
                            new Constraint(new int[] {2, -3, 2}, ConstraintSign.LESS_THAN_OR_EQUAL, 4),
                            new Constraint(new int[] {1, 2, 4}, ConstraintSign.LESS_THAN_OR_EQUAL, 8),
                            new Constraint(new int[] {0, 1, -1}, ConstraintSign.LESS_THAN_OR_EQUAL, 6),
                    },
                    true);

    Result<Vector, Double> solution = optimiser.solve();
//    Assert.assertEquals(new Vector(new double[] {0, 2.5, 1.875, 0, 0}), solution.getFirst());
    Assert.assertEquals(144/7.0, solution.getSecond(), 0.0001);
  }

  @Test
  // page 113 exercise 4B-1
  public void problem3() {
    OptimiserSimplex optimiser =
            new OptimiserSimplex(
                    new ObjectiveFunction(new int[] {5, 6, 4}),
                    OptimisationType.MAXIMISE,
                    new Constraint[] {
                            new Constraint(new int[] {1, 2, 0}, ConstraintSign.LESS_THAN_OR_EQUAL, 6),
                            new Constraint(new int[] {5, 3, 3}, ConstraintSign.LESS_THAN_OR_EQUAL, 24),
                    },
                    false);

    Result<Vector, Double> solution = optimiser.solve();
    Assert.assertEquals(new Vector(new double[] {0, 3, 5, 0, 0}), solution.getFirst());
    Assert.assertEquals(38, solution.getSecond(), 0.0001);
  }

  @Test
  // page 113, exercise 4B-2
  public void problem4() {
    OptimiserSimplex optimiser =
            new OptimiserSimplex(
                    new ObjectiveFunction(new int[] {3, 4, 10}),
                    OptimisationType.MAXIMISE,
                    new Constraint[] {
                            new Constraint(new int[] {1, 2, 2}, ConstraintSign.LESS_THAN_OR_EQUAL, 100),
                            new Constraint(new int[] {1, 0, 4}, ConstraintSign.LESS_THAN_OR_EQUAL, 40),
                    },
                    false);

    Result<Vector, Double> solution = optimiser.solve();
    Assert.assertEquals(new Vector(new double[] {0, 40, 10, 0, 0}), solution.getFirst());
    Assert.assertEquals(260, solution.getSecond(), 0.0001);
  }

  @Test
  // page 113, exercise 4B-3
  public void problem5() {
    OptimiserSimplex optimiser =
            new OptimiserSimplex(
                    new ObjectiveFunction(new int[] {3, 5, 2}),
                    OptimisationType.MAXIMISE,
                    new Constraint[] {
                            new Constraint(new int[] {3, 4, 5}, ConstraintSign.LESS_THAN_OR_EQUAL, 10),
                            new Constraint(new int[] {1, 3, 10}, ConstraintSign.LESS_THAN_OR_EQUAL, 5),
                            new Constraint(new int[] {1, -2, 0}, ConstraintSign.LESS_THAN_OR_EQUAL, 1),
                    },
                    false);

    // failing whenever there are more than 3 constraints??

    Result<Vector, Double> solution = optimiser.solve();
    Assert.assertEquals(new Vector(new double[] {2, 1, 0, 0, 0, 1}), solution.getFirst());
    Assert.assertEquals(11, solution.getSecond(), 0.0001);
  }

  @Test
  // page 113, exercise 4B-4
  public void problem6() {
    OptimiserSimplex optimiser =
            new OptimiserSimplex(
                    new ObjectiveFunction(new int[] {3, 6, 32}),
                    OptimisationType.MAXIMISE,
                    new Constraint[] {
                            new Constraint(new int[] {1, 6, 21}, ConstraintSign.LESS_THAN_OR_EQUAL, 672),
                            new Constraint(new int[] {3, 1, 24}, ConstraintSign.LESS_THAN_OR_EQUAL, 336),
                            new Constraint(new int[] {1, 3, 16}, ConstraintSign.LESS_THAN_OR_EQUAL, 168),
                            new Constraint(new int[] {2, 3, 32}, ConstraintSign.LESS_THAN_OR_EQUAL, 352),
                    },
                    false);

    // failing whenever there are more than 3 constraints??

    Result<Vector, Double> solution = optimiser.solve();
    Assert.assertEquals(new Vector(new double[] {105, 21, 0, 441, 0, 0, 79}), solution.getFirst());
    Assert.assertEquals(441, solution.getSecond(), 0.0001);
  }
}
