package problems;

import util.TourManager;

import java.util.Vector;

/**
 * Created by J on 5/22/2017.
 */
public class TSPNode extends Node {
    public Node parent;
    private double parent_cost;

    private int depth;
    private double[][] distances;
    private int[] active_set;

    public int index;

    /**
     * parent = parent of current node
     * parent_cost = evaluation of the parent of the current node
     * distances = 2D adjacency matrix of given graph of locations
     * active_set = an array by the length of the number of cities in the map which shows the path this node represents
     * index = shows the city this node represents in the 2D matrix
     * */

    public TSPNode(TSPNode parent, double parent_cost, double[][] distances, int[] active_set, int index){
        this.parent = parent;
        this.parent_cost = parent_cost;
        this.distances = distances;
        this.active_set = active_set;
        this.index = index;
    }


    public int getDepth() {

        return this.depth;
    }
    private void setDepth() {
        if(parent==null)
            this.depth=0;
        else
            this.depth= parent.getDepth()+1;
    }


    public boolean isFinal(){
        return active_set.length == 1;
    }



    /**this function generates all the children nodes from the current node.
     * this goes by finding all the connected nodes to the current nodes and
     * adding them into a vector and returning the resulting vector in the end.*/
    public Vector<Node> getChildren() {
        Vector<Node> children = new Vector<Node>(active_set.length - 1);
        int[] new_set = new int[active_set.length - 1];
        int i = 0;
        for(int location : active_set) {
            if(location == index)
                continue;
            new_set[i] = location;
            i++;
        }
        TSPNode child;
        for(int j = 0; j < active_set.length - 1; j++) {
            child = new TSPNode(this, distances[index][new_set[j]], distances, new_set, new_set[j]);
            children.add(child);
        }

        return children;
    }

    /**
     * Get the path array up to this point
     *
     * @return The path
     */
    public int[] getPath() {
        int depth = distances.length - active_set.length + 1;
        int[] path = new int[depth];
        getPathIndex(path, depth - 1);
        return path;
    }

    /**
     * Get the lower bound cost of this node
     *
     * @return Lower bound cost
     */
    public double getEval() {
        int cur = active_set[0];
        double cost = 0;
        for (int i = 1; i < active_set.length; i++)
        {
            cost += distances[cur][active_set[i]];
            cur = active_set[i];
        }
        return cost + distances[active_set[active_set.length-1]][cur];
    }


    /**
     * Recursive method to fill in a path array from this point
     *
     * @param path The path array
     * @param i The index of this node
     */
    public void getPathIndex(int[] path, int i) {
        path[i] = index;
        TSPNode myParent = (TSPNode) parent;
        if(myParent != null)
            myParent.getPathIndex(path, i - 1);
    }
    /**
     * Get the cost of the entire path up to this point
     *
     * @return Cost of path including return
     */
    public double getPathCost() {
        return distances[0][index] + getParentCost();
    }

    /**
     * Get the cost up to the parent at this point
     *
     * @return Cost of path
     */
    public double getParentCost() {
        if(parent == null)
            return 0;
        TSPNode myParent = (TSPNode) parent;
        return parent_cost + myParent.getParentCost();
    }


    public void setParent(Node last) {

        this.parent=last;

    }

    public Node getParent() {

        return this.parent;
    }


    /**visual representation of the current state.*/
    public void print() {
        System.out.println(toString());

    }

    @Override
    public String toString() {
        String message = TourManager.getCity(getPath()[0]).getName();
        for(int i = 1; i < getPath().length; i++) {
            message += " to " + TourManager.getCity(getPath()[i]).getName();
        }
        return message;
    }
}
