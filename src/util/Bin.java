package util;

import java.awt.geom.Point2D;
/**
 * Created by J on 5/23/2017.
 */
public class Bin {

    private String name    = "";
    private int weight     = 0;
    private int value      = 0;
    private boolean inKnapsack; // the pieces of item in solution



    public Bin(String _name, int _weight, int _value) {
        setName(_name);
        setWeight(_weight);
        setValue(_value);
        inKnapsack = false;
    }


    public void setName(String _name) {name = _name;}
    public void setWeight(int _weight) {weight = Math.max(_weight, 0);}
    public void setValue(int _value) {value = Math.max(_value, 0);}

    public void setInKnapsack(boolean _inKnapsack) {
        inKnapsack = _inKnapsack;
    }


    public void checkMembers() {
        setWeight(weight);
        setValue(value);
        setInKnapsack(inKnapsack);
    }

    public String getName() {return name;}
    public int getWeight() {return weight;}
    public int getValue() {return value;}
    public boolean getInKnapsack() {return inKnapsack;}

}

