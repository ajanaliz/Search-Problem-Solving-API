package algorithms;

import problems.Problem;

public class Genetic {

    Problem my_problem;

    public Genetic(Problem my_problem) {

        this.my_problem = my_problem;
        begin();
    }

    private void begin() {

        my_problem.setPopulation();
        int x = 0;

        while (!my_problem.found() && x < 2000) {
            x++;
            System.out.println("fitness of current individuals in generation  : " + x + " is : ");
            my_problem.getGenerationFitness();
            my_problem.setChildsOFGeneration();
            my_problem.mutateChilds();
            my_problem.selectParent();

        }

        //((EquationProblem)my_problem).print();
    }

    public static void main(String[] args) {

    }
}
