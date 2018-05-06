package problems;

import algorithms.Genetic;
import util.Bin;
import util.City;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * Created by J on 5/22/2017.
 */
public class KnapsackProblem extends Problem {

    private ArrayList<Bin> bins;
    private int capacity;
    private Vector<Node> individuals;
    private Vector<Node> childs;
    private int myrand = -1;
    private Random rand;
    private Vector<Double> min_fitness;
    private Vector<Double> max_fitness;
    private Vector<Double> mean_fitness;
    private int population;
    private int generated_childs;
    private int n;


    public KnapsackProblem(ArrayList<Bin> bins, int capacity, int population, int generated_childs, int n) {
        this.bins = bins;
        this.capacity = capacity;
        mean_fitness = new Vector<Double>();
        max_fitness = new Vector<Double>();
        min_fitness = new Vector<Double>();
        rand = new Random();
        this.population = population;
        this.generated_childs = generated_childs;
        childs = new Vector<Node>();
        individuals = new Vector<Node>();
        this.n = n;
    }

    private ArrayList<Bin> cloneBins(ArrayList<Bin> mybins) {
        ArrayList<Bin> binsClone = new ArrayList<>();
        Bin temp;
        for (int i = 0; i < mybins.size(); i++) {
            temp = new Bin(mybins.get(i).getName(), mybins.get(i).getWeight(), mybins.get(i).getValue());
            binsClone.add(temp);
        }
        return binsClone;
    }


    private boolean isInVector(Vector<ArrayList<Bin>> vector, ArrayList<Bin> input) {
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < vector.get(i).size(); j++) {
                if (!vector.get(i).get(j).getName().equals(j < input.size() ? input.get(j).getName() : input.get(input.size() - j + 5).getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * generate a population given the population size and the input bin list.
     * */

    public Vector<Node> setPopulation() {

        Vector<ArrayList<Bin>> vec = new Vector<>();
        while (vec.size() + individuals.size() < population) {
            int j = rand.nextInt(bins.size());
            ArrayList<Bin> newBins = cloneBins(this.bins);
            newBins.get(j).setInKnapsack(true);
            if (isInVector(vec, newBins))
                vec.add(newBins);

        }
        for (int i = 0; i < population; i++) {
            KnapsackNode eq = new KnapsackNode(vec.elementAt(i), capacity);
            individuals.add(eq);
        }
        return individuals;

    }

    /**
     * find children of the current population
     * */

    public Vector<Node> setChildsOFGeneration() {

        childs.removeAllElements();
        myrand++;
        Vector<ArrayList<Bin>> vec = new Vector<>();

        while (vec.size() < generated_childs) {
            int j = rand.nextInt(population);
            KnapsackNode temp = (KnapsackNode) individuals.get(j);
            j = rand.nextInt(bins.size());
            ArrayList<Bin> newBins = cloneBins(temp.getBins());
            newBins.get(j).setInKnapsack(!newBins.get(j).getInKnapsack());
            if (isInVector(vec, newBins))
                vec.add(newBins);

        }

        for (int i = 0; i < generated_childs; i++) {
            KnapsackNode eq = new KnapsackNode(vec.elementAt(i), capacity);
            childs.add(eq);
        }

        return childs;


    }


    /**
     *  calculates the fitness of the entire child set of the current population.
     * */

    public double getGenerationFitness() {

        double temp = 0;
        double min = capacity;
        double max = 0;

        for (int i = 0; i < population; i++) {
            if (individuals.elementAt(i).getFitness() > max)
                max = individuals.elementAt(i).getFitness();
            if (individuals.elementAt(i).getFitness() < min)
                min = individuals.elementAt(i).getFitness();
            temp += individuals.elementAt(i).getFitness();
        }

        min_fitness.add(min);
        max_fitness.add(max);
        mean_fitness.add(temp / population);

        System.out.println("min : " + min + " max is: " + max + "average is :" + temp / population);
        System.out.println();
        System.out.println();
        return (temp / population);
    }

    /**
     * prints out the fitness of the chromosome with the minimum fitness, the fitness of the chromosome with the
     * highest fitness and the mean of all the chromosomes in the population
     * */

    public void print() {
        System.out.println("this is min");

        for (int i = 0; i < min_fitness.size(); i++) {
            System.out.println(min_fitness.elementAt(i));
        }

        System.out.println("max");
        for (int i = 0; i < max_fitness.size(); i++) {
            System.out.println(max_fitness.elementAt(i));
        }

        System.out.println("mean");
        for (int i = 0; i < mean_fitness.size(); i++) {
            System.out.println(mean_fitness.elementAt(i));
        }
    }

    /** returns true if the solution that fills up our capacity exactly is found.
     * this method is to indicate early convergance.
     * */

    public boolean found() {

        for (int i = 0; i < population; i++) {
            if (individuals.elementAt(i).getFitness() == capacity) {
                System.out.println();
                System.out.println(" answer found : " + individuals.elementAt(i).getvalue());
                return true;
            }
        }
        return false;

    }

    /**
     * mutate all children in the children array of the population
     * */

    public void mutateChilds() {

        for (int i = 0; i < generated_childs; i++) {
            childs.elementAt(i).mutation();
        }
        //System.out.println("finished");
    }

    /**
     * finds two individuals in population with best fitness, does n
     * point crossover with given parameter n in the constructor and
     * then replaces the two worst
     * individuals in the population with the two new chromosomes.*/

    public void selectParent() {
        KnapsackNode first, second;
        int firstIND = 0, secondIND = 0;
        double high1 = Double.MIN_VALUE;
        double high2 = Double.MIN_VALUE;
        for (int i = 0; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() > high1) {
                high2 = high1;
                secondIND = firstIND;
                high1 = individuals.get(i).getFitness();
                firstIND = i;
            } else if (individuals.get(i).getFitness() > high2) {
                high2 = individuals.get(i).getFitness();
                secondIND = i;
            }

        }
        first = (KnapsackNode) individuals.get(firstIND);
        second = (KnapsackNode) individuals.get(secondIND);
        if (n > bins.size()) {
            n = bins.size() - 2;
        }
        final ArrayList<Bin> firstParent = first.getBins();
        final ArrayList<Bin> secondParent = second.getBins();
        ArrayList<Bin> firstChild = new ArrayList<>(firstParent.size());
        ArrayList<Bin> secondChild = new ArrayList<>(firstParent.size());
        int remainingPoints = n;
        int lastIndex = 0;
        for (int i = 0; i < n; i++, remainingPoints--) {
            // select the next crossover point at random
            final int crossoverIndex = 1 + lastIndex + rand.nextInt(bins.size() - lastIndex - remainingPoints);

            // copy the current segment
            for (int j = lastIndex; j < crossoverIndex; j++) {
                firstChild.add(firstParent.get(j));
                secondChild.add(secondParent.get(j));
            }

            // swap the children for the next segment
            ArrayList<Bin> tmp = firstChild;
            firstChild = secondChild;
            secondChild = tmp;

            lastIndex = n;


        }

        for (int j = lastIndex; j < bins.size(); j++) {
            firstChild.add(firstParent.get(j));
            secondChild.add(secondParent.get(j));
        }

        Node newChild1 = new KnapsackNode(firstChild, capacity);
        Node newChild2 = new KnapsackNode(secondChild, capacity);

        KnapsackNode firstReplace, secondReplace;
        int firstMinIND = 0, secondMinIND = 0;
        double low1 = Double.MAX_VALUE;
        double low2 = Double.MAX_VALUE;
        for (int i = 0; i < individuals.size(); i++) {
            if (individuals.get(i).getFitness() < low1) {
                low2 = low1;
                secondMinIND = firstMinIND;
                low1 = individuals.get(i).getFitness();
                firstMinIND = i;
            } else if (individuals.get(i).getFitness() < low2) {
                low2 = individuals.get(i).getFitness();
                secondMinIND = i;
            }

        }
        firstReplace = (KnapsackNode) individuals.get(firstMinIND);
        secondReplace = (KnapsackNode) individuals.get(secondMinIND);

        individuals.remove(firstReplace);
        individuals.remove(secondReplace);
        individuals.add(newChild1);
        individuals.add(newChild2);

    }



    public static void main(String[] args) {
        File file;
        if (args.length > 0) {
            file = new File(args[0]);
        } else {
            JFileChooser chooser = new JFileChooser();

            int response = chooser.showOpenDialog(null);
            if (response != JFileChooser.APPROVE_OPTION)
                return;

            file = chooser.getSelectedFile();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            alert("Error loading file " + e);
            System.exit(1);
        }

        int dimension = 0;
        try {
            String line;
            while (!(line = reader.readLine()).equals("NODE_COORD_SECTION")) {
                String[] entry = line.split(": ", 1);
                switch (entry[0].trim()) {
                    case "TYPE":
                        if (!entry[1].trim().equals("KP"))
                            throw new Exception("File not in KP format");
                        break;
                    case "DIMENSION":
                        dimension = Integer.parseInt(entry[1]);
                        break;
                }
            }
        } catch (Exception e) {
            alert("Error parsing header " + e);
            System.exit(1);
        }

        ArrayList<Bin> bins = new ArrayList<Bin>(dimension);

        try {
            String line;
            while ((line = reader.readLine()) != null && !line.equals("EOF")) {
                String[] entry = line.split(" ");
                bins.add(new Bin(entry[0], Integer.parseInt(entry[1]), Integer.parseInt(entry[2])));
            }

            reader.close();
        } catch (Exception e) {
            alert("Error parsing data " + e);
            System.exit(1);
        }
        System.out.println(bins.size());
        KnapsackProblem kp = new KnapsackProblem(bins, 250, 45, 15, 10);
        Genetic g = new Genetic(kp);

    }


    private static void alert(String message) {
        if (GraphicsEnvironment.isHeadless())
            System.out.println(message);
        else
            JOptionPane.showMessageDialog(null, message);
    }

}
