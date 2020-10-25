package main.constraints;

public class Constraint {

    protected final ConstraintSign sign;
    protected final int constant;
    protected final int[] coefficients;

    public Constraint(int[] coefficients, ConstraintSign sign, int constant) {
        this.sign = sign;
        this.constant = constant;
        this.coefficients = coefficients;
    }

    public ConstraintSign getSign() {
        return sign;
    }

    public int[] getCoefficients() {
        return coefficients;
    }

    public int getConstant() {
        return constant;
    }
}
