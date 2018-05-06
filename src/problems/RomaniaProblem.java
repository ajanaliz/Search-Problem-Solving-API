package problems;

import algorithms.A_Star;
import algorithms.BFS;
import algorithms.DFS;
import com.sun.org.apache.bcel.internal.generic.NEW;
import util.SimplifiedRoadMapOfPartOfRomania;

import java.util.Vector;

/**
 * Created by Ali J on 4/6/2017.
 */
public class RomaniaProblem extends Problem {


    public RomaniaProblem(){
//        BFS bfs = new BFS(this,"");
//        A_Star astar = new A_Star(this, "");
        DFS dfs = new DFS(this,8,"");
    }

    public Node getFirstNode() {
        Vector<RomaniaNode> path = new Vector<RomaniaNode>();
        Node n = new RomaniaNode(SimplifiedRoadMapOfPartOfRomania.ARAD, SimplifiedRoadMapOfPartOfRomania.VASLUI, null ,path );
        return n;

    }

    public static void main(String arg0[]) {
        RomaniaProblem qp = new RomaniaProblem();
    }



}
