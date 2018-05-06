package problems;

import java.util.Vector;

/**
 * Created by Ali J on 4/4/2017.
 */
enum Position {
    RIGHT, LEFT
}

public class MissionaryVSCannibalNode extends Node {

    private Node Parent;
    private int cannibalLeft;
    private int missionaryLeft;
    private int cannibalRight;
    private int missionaryRight;
    private Position boat;
    private int depth;

    public MissionaryVSCannibalNode(int cannibalLeft, int missionaryLeft, Position boat,
                                    int cannibalRight, int missionaryRight, Node Parent) {
        this.cannibalLeft = cannibalLeft;
        this.missionaryLeft = missionaryLeft;
        this.boat = boat;
        this.Parent = Parent;
        this.cannibalRight = cannibalRight;
        this.missionaryRight = missionaryRight;
        setDepth();
    }

    public boolean isFinal() {
        return cannibalLeft == 0 && missionaryLeft == 0;
    }

    public void print() {
        System.out.println(toString());
    }


    public void setParent(Node last) {

        this.Parent=last;

    }

    public Node getParent() {

        return this.Parent;
    }

    public int getDepth() {

        return this.depth;
    }
    private void setDepth() {
        if(Parent==null)
            this.depth=0;
        else
            this.depth= Parent.getDepth()+1;
    }

    public boolean isValid() {
        if (missionaryLeft >= 0 && missionaryRight >= 0 && cannibalLeft >= 0 && cannibalRight >= 0
                && (missionaryLeft == 0 || missionaryLeft >= cannibalLeft)
                && (missionaryRight == 0 || missionaryRight >= cannibalRight)) {
            return true;
        }
        return false;
    }

    private void testAndAdd(Vector<Node> children, MissionaryVSCannibalNode newState) {
        if (newState.isValid()) {
            newState.setParent(this);
            children.add(newState);
        }
    }

    @Override
    public Vector<Node> getChildren() {
        Vector<Node> children = new Vector<>();
        if (boat == Position.LEFT) {
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft, missionaryLeft - 2, Position.RIGHT,
                    cannibalRight, missionaryRight + 2,this)); // Two missionaries cross left to right.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft - 2, missionaryLeft, Position.RIGHT,
                    cannibalRight + 2, missionaryRight,this)); // Two cannibals cross left to right.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft - 1, missionaryLeft - 1, Position.RIGHT,
                    cannibalRight + 1, missionaryRight + 1, this)); // One missionary and one cannibal cross left to right.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft, missionaryLeft - 1, Position.RIGHT,
                    cannibalRight, missionaryRight + 1, this)); // One missionary crosses left to right.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft - 1, missionaryLeft, Position.RIGHT,
                    cannibalRight + 1, missionaryRight, this)); // One cannibal crosses left to right.
        } else {
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft, missionaryLeft + 2, Position.LEFT,
                    cannibalRight, missionaryRight - 2, this)); // Two missionaries cross right to left.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft + 2, missionaryLeft, Position.LEFT,
                    cannibalRight - 2, missionaryRight, this)); // Two cannibals cross right to left.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft + 1, missionaryLeft + 1, Position.LEFT,
                    cannibalRight - 1, missionaryRight - 1, this)); // One missionary and one cannibal cross right to left.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft, missionaryLeft + 1, Position.LEFT,
                    cannibalRight, missionaryRight - 1, this)); // One missionary crosses right to left.
            testAndAdd(children, new MissionaryVSCannibalNode(cannibalLeft + 1, missionaryLeft, Position.LEFT,
                    cannibalRight - 1, missionaryRight, this)); // One cannibal crosses right to left.
        }
        return children;
    }

    @Override
    public String toString() {
        if (boat == Position.LEFT) {
            return "(" + cannibalLeft + "," + missionaryLeft + ",L,"
                    + cannibalRight + "," + missionaryRight + ")";
        } else {
            return "(" + cannibalLeft + "," + missionaryLeft + ",R,"
                    + cannibalRight + "," + missionaryRight + ")";
        }
    }

    public boolean isSame(Node elementAt) {
        if (!(elementAt instanceof MissionaryVSCannibalNode)) {
            return false;
        }
        MissionaryVSCannibalNode s = (MissionaryVSCannibalNode) elementAt;
        return (s.cannibalLeft == cannibalLeft && s.missionaryLeft == missionaryLeft
                && s.boat == boat && s.cannibalRight == cannibalRight
                && s.missionaryRight == missionaryRight);
    }

}
