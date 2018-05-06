package problems;

import algorithms.HillClimbing;
import algorithms.SimulatedAnnealing;
import util.City;
import util.TourManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by J on 5/22/2017.
 */
public class TSPProblem extends Problem {
    private double[][] distances;
    private double best_cost;
    private int[] best_path;
    private ArrayList<City> cities;


    /**
     * Constructs a new Solver and initializes distances array
     *
     * @param cities An ArrayList of City's
     */
    public TSPProblem( ArrayList<City> cities) {
        this.cities = cities;
        distances = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            for (int ii = 0; ii < cities.size(); ii++)
                distances[i][ii] = cities.get(i).distance(cities.get(ii));
        }


    }

    /**
     * Calculates the shortest (non-repeating) path between a series of nodes
     *
     * @return An array with the locations of the best path
     */
    public int[] calculate() {
        HashSet<Integer> location_set = new HashSet<Integer>(distances.length);
        for (int i = 0; i < distances.length; i++)
            location_set.add(i);

        best_cost = findGreedyCost(0, location_set, distances);

        int[] active_set = new int[distances.length];
        for (int i = 0; i < active_set.length; i++)
            active_set[i] = i;

        Node root = new TSPNode(null, 0, distances, active_set, 0);
        traverse(root);

        return best_path;
    }

    /**
     * Get current path cost
     *
     * @return The cost
     */
    public double getCost() {
        return best_cost;
    }

    /**
     * Find the greedy cost for a set of locations
     *
     * @param i            The current location
     * @param location_set Set of all remaining locations
     * @param distances    The 2D array containing point distances
     * @return The greedy cost
     */
    private double findGreedyCost(int i, HashSet<Integer> location_set, double[][] distances) {
        if (location_set.isEmpty())
            return distances[0][i];

        location_set.remove(i);

        double lowest = Double.MAX_VALUE;
        int closest = 0;
        for (int location : location_set) {
            double cost = distances[i][location];
            if (cost < lowest) {
                lowest = cost;
                closest = location;
            }
        }

        return lowest + findGreedyCost(closest, location_set, distances);
    }

    /**
     * Recursive method to go through the tree finding and pruning paths
     *
     * @param parent The root/parent node
     */
    private void traverse(Node parent) {
        Vector<Node> children = parent.getChildren();

        for (Node child : children) {
            TSPNode myChild = (TSPNode) child;
            if (child.isFinal()) {
                double cost = myChild.getPathCost();
                if (cost < best_cost) {
                    best_cost = cost;
                    best_path = myChild.getPath();
                }
            } else if (myChild.getEval() <= best_cost) {
                traverse(child);
            }
        }
    }



    /**this function returns the first node that is to be traversed during the search*/
    public Node getFirstNode() {
        int[] active_set = new int[distances.length];
        for(int i = 0; i < active_set.length; i++)
            active_set[i] = i;
        Node n = new TSPNode(null,0,distances,active_set,0);
        // Node n = new QueenNode(board,0,0 ,null);
        return n;

    }

    /**this function returns the first node that is to be traversed during the search*/
    public Node getRandomNode() {

        int randomIndex = (int) Math.floor(Math.random() * cities.size());
        City randomCity = cities.get(randomIndex);
        int[] active_set = new int[distances.length];
        for(int i = 0; i < active_set.length; i++)
            active_set[i] = i;
        Node randomNode = new TSPNode(null,0,distances,active_set,randomIndex);
        return randomNode;
    }

    public static void main(String[] args) {
        File file;
        if(args.length > 0) {
            file = new File(args[0]);
        }
        else {
            JFileChooser chooser = new JFileChooser();

            int response = chooser.showOpenDialog(null);
            if(response != JFileChooser.APPROVE_OPTION)
                return;

            file = chooser.getSelectedFile();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch(IOException e) {
            alert("Error loading file " + e);
            System.exit(1);
        }

        int dimension = 0;
        try {
            String line;
            while(!(line = reader.readLine()).equals("NODE_COORD_SECTION")) {
                String[] entry = line.split(": ", 1);
                switch(entry[0].trim()) {
                    case "TYPE":
                        if(!entry[1].trim().equals("TSP"))
                            throw new Exception("File not in TSP format");
                        break;
                    case "DIMENSION":
                        dimension = Integer.parseInt(entry[1]);
                        break;
                }
            }
        }
        catch(Exception e) {
            alert("Error parsing header " + e);
            System.exit(1);
        }

        ArrayList<City> cities = new ArrayList<City>(dimension);

        try {
            String line;
            while((line = reader.readLine()) != null && !line.equals("EOF")) {
                String[] entry = line.split(" ");
                cities.add(new City(entry[0], Double.parseDouble(entry[1]), Double.parseDouble(entry[2])));
            }

            reader.close();
        }
        catch(Exception e) {
            alert("Error parsing data " + e);
            System.exit(1);
        }
        TourManager.destinationCities = cities;
        TSPProblem problem = new TSPProblem(cities);
        System.out.println(cities.size());
        System.out.println(Arrays.deepToString(problem.distances));
//        SimulatedAnnealing sm = new SimulatedAnnealing(problem);
        HillClimbing hc = new HillClimbing(problem);
    }


    private static void alert(String message) {
        if (GraphicsEnvironment.isHeadless())
            System.out.println(message);
        else
            JOptionPane.showMessageDialog(null, message);
    }
}
