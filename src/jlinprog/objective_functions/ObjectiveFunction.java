package jlinprog.objective_functions;

public class ObjectiveFunction {
  protected final int[] coefficients;

  public ObjectiveFunction(int[] coefficients) {
    this.coefficients = coefficients;
  }

  public double evaluate(double[] variableCoefficients) {
    double total = 0;
    for (int i = 0; i < coefficients.length; i++) {
      total += coefficients[i] * variableCoefficients[i];
    }
    return total;
  }

  public int[] getCoefficients() {
    return coefficients;
  }
}
