package problems;

import algorithms.BFS;
import algorithms.DFS;

/**
 * Created by Ali J on 4/4/2017.
 */
public class MissionaryVSCannibalProblem extends Problem {

    public MissionaryVSCannibalProblem(){
        DFS dfs = new DFS(this,-1,"");
//        BFS bfs = new BFS(this,"");
    }

    public Node getFirstNode() {
        Node n = new MissionaryVSCannibalNode(3,3,Position.LEFT,0,0, null);
        return n;
    }


    public static void main(String[] args) {
        MissionaryVSCannibalProblem problem = new MissionaryVSCannibalProblem();
    }
}
