package problems;

import util.Bin;
import util.TourManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by J on 5/22/2017.
 */
public class KnapsackNode extends Node {
    private double fitness;
    private ArrayList<Bin> bins;
    private Random random;

    public ArrayList<Bin> getBins() {
        return bins;
    }

    private int profit;
    private boolean calculated;
    private int maxWeight;


    /**bins is a list of items that can be picked up, the class Bin has a boolean value InKnapsack which
     * says if that item is in the knapsack or not, by maintaining a list of all the items and representing
     * them being in the list or not with a boolean value, we can have a nice and readable representation of
     * the current knapsack*/

    public KnapsackNode(ArrayList<Bin> bins, int maxWeight) {
        this.bins = bins;
        this.maxWeight = maxWeight;
        calculated = false;
        profit = 0;
        setFitness();
        random = new Random();
    }


    /**
     * set the fitness variable for the current item list. it is equal to the
     * sum of the values of all the items picked up. if the sum of the weights
     * of the items we picked up is greater than the total weight we can carry,
     * i set the fitness to be a minimum value, so that it gets removed from
     * the list during the next iteration.
     */
    public void setFitness() {
        int tempCap = 0;
        for (int i = 0; i < bins.size(); i++) {
            if (bins.get(i).getInKnapsack()) {
                tempCap += bins.get(i).getWeight();
                fitness += bins.get(i).getValue();
                if (maxWeight < tempCap) fitness = Double.MIN_VALUE;
            }
        }

    }

    public double getFitness() {

        return this.fitness;
    }


    /**
     * i mutate the current chromosome with probability  1-1/bins.size() where bins.size()
     * is the total number of items we can pick up.
     */
    public void mutation() {
        for (int i = 0; i < bins.size(); i++) {
            if (random.nextDouble() > (1 - 1 / bins.size())) {
                bins.get(i).setInKnapsack(!bins.get(i).getInKnapsack());
            }
        }
        setFitness();
    }

    /**
     * visual representation of the current state.
     */
    public void print() {
        System.out.println(toString());

    }

    @Override
    public String toString() {
        String message = "";
        for (int i = 1; i < bins.size(); i++) {
            if (bins.get(i).getInKnapsack())
                message += bins.get(i).getName() + " ,";
        }
        return message;
    }

}
