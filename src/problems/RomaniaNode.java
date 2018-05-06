package problems;

import util.SimplifiedRoadMapOfPartOfRomania;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ali J on 4/6/2017.
 */
public class RomaniaNode extends Node {

    private String city;
    private Vector<RomaniaNode> path;
    private String destination;
    private Node parent;
    private int fCost;
    private int depth;
    private int hcost;
    private int gCost;
    private static SimplifiedRoadMapOfPartOfRomania simplifiedRoadMapOfPartOfRomania = new SimplifiedRoadMapOfPartOfRomania();

    public RomaniaNode(String city, String destination, RomaniaNode parent, Vector<RomaniaNode> path) {
        this.city = city;
        this.destination = destination;
        this.path = path;
        this.parent = parent;
        if (parent == null) gCost = 0;
        hcost = (int) Math.sqrt(Math.pow(simplifiedRoadMapOfPartOfRomania.getPosition(city).getX() - simplifiedRoadMapOfPartOfRomania.getPosition(destination).getX(), 2.0) + Math.pow(simplifiedRoadMapOfPartOfRomania.getPosition(city).getY() - simplifiedRoadMapOfPartOfRomania.getPosition(destination).getY(), 2));
        path.add(this);
        setDepth();
    }

    public int getDepth() {

        return this.depth;
    }

    private void setDepth() {
        if (parent == null)
            this.depth = 0;
        else
            this.depth = parent.getDepth() + 1;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    private void setFcost() {
        this.fCost = gCost + hcost;
    }


    public void setParent(Node last) {
        this.parent = last;
    }

    public Node getParent() {
        return this.parent;
    }

    public void print() {
        // TODO Auto-generated method stub
        System.out.println(this);

    }

    @Override
    public String toString() {
        return city;
    }

    public boolean isFinal() {
        if (!this.city.equalsIgnoreCase(destination)) return false;
        return true;
    }

    public int getFcost() {

        return this.fCost;
    }

    public Vector<Node> getChildren() {


        Vector<Node> children = new Vector<Node>();
        List<String> childrenNames = simplifiedRoadMapOfPartOfRomania.getPossibleNextLocations(this.toString());
        for (int i = 0; i < childrenNames.size(); i++) {
            RomaniaNode n = new RomaniaNode(childrenNames.get(i), this.destination, this, clonePath());
            n.setgCost(gCost + ((int) simplifiedRoadMapOfPartOfRomania.getDistance(this.city, childrenNames.get(i)).doubleValue()));
            children.add(n);
        }
        return children;
    }

    private Vector<RomaniaNode> clonePath() {
        Vector<RomaniaNode> output = new Vector<>();
        for (int i = 0; i < this.path.size(); i++) {
            output.add(this.path.get(i));
        }
        return output;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Vector<RomaniaNode> getPath() {
        return path;
    }

    public void setPath(Vector<RomaniaNode> path) {
        this.path = path;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isSame(Node elementAt) {
        if (!(elementAt instanceof RomaniaNode))
            return false;
        RomaniaNode s = (RomaniaNode) elementAt;
        if (path.size() != s.getPath().size())
            return false;
        for (int i = 0; i < path.size(); i++) {
            if (!path.get(i).toString().equalsIgnoreCase(s.getPath().get(i).toString()))
                return false;
        }
        return true;

    }


}
