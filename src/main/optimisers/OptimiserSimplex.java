package main.optimisers;

import main.constraints.Constraint;
import main.constraints.ConstraintSign;
import main.objective_functions.ObjectiveFunction;
import main.other.Result;
import main.other.Vector;

import java.util.Arrays;

public class OptimiserSimplex implements Optimiser {

  private final ObjectiveFunction objectiveFunction;
//  private final OptimisationType optimisationType;
  private final Constraint[] constraints;
  private final boolean showProcess;

  private final double[][] table;
  private final double[] thetaValues;
  private final double[] basicVariables;

  public OptimiserSimplex(
      ObjectiveFunction objectiveFunction,
      OptimisationType optimisationType,
      Constraint[] constraints,
      boolean showProcess) {
    this.objectiveFunction = objectiveFunction;
//    this.optimisationType = optimisationType;
    this.constraints = constraints;
    this.showProcess = showProcess;
    assert optimisationType == OptimisationType.MAXIMISE; // simplex method only solves max problems

    // Check that all constraints have the same number of coefficients
    int coefficientsLength = constraints[0].getCoefficients().length;
    for (Constraint c : constraints) {
      assert c.getCoefficients().length == coefficientsLength
          : "Please provide constraints with the same number of coefficients";
    }

    this.table = generateInitialTable();
    this.thetaValues = new double[constraints.length]; // one theta value for each constraint
    this.basicVariables = new double[constraints.length]; // represents each coefficient in order
  }

  public Result<Vector, Double> solve() {
    int rows = table.length;
    int cols = table[0].length;

    int iter = 1;
    while (!containsNegativeValues(thetaValues) && iter < 3) {

        if (showProcess) {
            System.out.println("----- ITERATION " + iter + " -----");
            System.out.println("Table:");
            printTableau(table, rows, cols);
        }

      // Find the next pivot column, given by the smallest value in the objective row
      int pivotCol = findMinIndex(table[table.length - 1], false);

      if (showProcess) System.out.println("Pivot column is " + pivotCol);

      // Calculate theta values for each constraint and find the next pivot row
      for (int r = 0; r < thetaValues.length; r++) {
        thetaValues[r] = table[r][cols - 1] / table[r][pivotCol];
      }
      int pivotRow = findMinIndex(thetaValues, true);

      if (showProcess) {
          System.out.print("Theta values are: ");
          System.out.println(Arrays.toString(thetaValues));
          System.out.print("Pivot row is " + pivotRow);
      }

      // Get the pivot entry and divide the pivot row by it
      double pivot = table[pivotRow][pivotCol];
      for (int c = 0; c < cols; c++) {
        table[pivotRow][c] /= pivot;
      }

      // Swap the basic variable
      basicVariables[pivotRow] = pivotCol;

      // Eliminate the pivot's variable from other rows
      for (int r = 0; r < rows; r++) {
        if (r == pivotRow) {
          continue;
        }
        double factor = table[r][pivotCol];
        for (int c = 0; c < cols; c++) {
          table[r][c] -= factor * table[pivotRow][c];
        }
      }

      if(showProcess) System.out.println("\n----------------------\n");
      iter++;
    }

    double[] resultValues = new double[cols - 1];
    for (int i = 0; i < basicVariables.length; i++) {
      resultValues[(int) basicVariables[i]] = table[i][cols - 1];
    }

    for (int i = 0; i < resultValues.length; i++) {
      String varType = (i < constraints[0].getCoefficients().length ? "" : "(slack) ");
      System.out.println("Value of " + varType + "variable no. " + (i + 1) + " is " + resultValues[i]);
    }
    System.out.println("\nOptimal objective function value is " + table[rows - 1][cols - 1]);

    return new Result<>(new Vector(resultValues), table[rows - 1][cols - 1]);
  }

  /* Finds the index of the smallest value in the array */
  private int findMinIndex(double[] array, boolean shouldBePositive) {
    int index = 0;
    double min = Double.MAX_VALUE;

    for (int i = 0; i < array.length; i++) {
      if (shouldBePositive) {
        if (array[i] > 0 && array[i] < min) {
          min = array[i];
          index = i;
        }
      } else {
        if (array[i] < min) {
          min = array[i];
          index = i;
        }
      }
    }
    return index;
  }

  private boolean containsNegativeValues(double[] array) {
    for (double val : array) {
      if (val < 0) {
        return true;
      }
    }
    return false;
  }

  /*
   * Create a table with one row for each constraint and one row (bottom row) for the objective function
   * One column for each variable (including slack vars) and a value column
   * */
  private double[][] generateInitialTable() {

    int rows = constraints.length + 1;
    int cols = constraints[0].getCoefficients().length + constraints.length + 1;
    double[][] table = new double[rows][cols];

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (c < constraints[0].getCoefficients().length) {
          if (r < rows - 1) {
            table[r][c] = constraints[r].getCoefficients()[c]; // variable coeffs
          } else {
            table[r][c] = -objectiveFunction.getCoefficients()[c]; // function coeffs (negative)
          }
        } else if (c < cols - 1) {
          if (r < rows - 1) {
            table[r][c] =
                c - constraints[0].getCoefficients().length == r ? 1 : 0; // slack variable coeffs
            if (table[r][c] == 1
                && constraints[r].getSign() == ConstraintSign.GREATER_THAN_OR_EQUAL) {
              table[r][c] = -1; // if it is a >= constraint, then rearrange and becomes negative
            }
          }
        } else {
          if (r < rows - 1) {
            table[r][c] = constraints[r].getConstant(); // constraint constant
          }
        }
      }
    }
    return table;
  }

  /* Print the table (for debugging) */
  private void printTableau(double[][] table, int rows, int cols) {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        System.out.print(table[r][c] + " ");
      }
      System.out.println();
    }
  }

}
