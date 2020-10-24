package test;

import main.constraints.Constraint2D;
import main.constraints.ConstraintSign;
import main.objective_functions.ObjectiveFunction2D;
import main.optimisers.OptimisationType;
import main.optimisers.Optimiser2D;
import main.other.Coordinate;
import main.other.Pair;

public class Main {

    public static void main(String[] args){
        Optimiser2D optimiser = new Optimiser2D(new ObjectiveFunction2D(3, 2),
                OptimisationType.MINIMISE, new Constraint2D[]{
                new Constraint2D(0, 1, ConstraintSign.GREATER_THAN_OR_EQUAL, 2),
                new Constraint2D(1, 2, ConstraintSign.LESS_THAN_OR_EQUAL, 25),
                new Constraint2D(-2, 4, ConstraintSign.GREATER_THAN_OR_EQUAL, -8),
                new Constraint2D(-2, 1, ConstraintSign.LESS_THAN_OR_EQUAL, -5),
                new Constraint2D(1, 0, ConstraintSign.GREATER_THAN_OR_EQUAL, 5), // strict relations fuck it up
        }, false);


        Pair<Coordinate, Double> solution = optimiser.solve();
        System.out.println("Optimal value: " + solution.getSecond().toString());
        System.out.println("Optimal coordinate: " + solution.getFirst().toString());
    }

}
