package problems;

import java.util.Vector;

import algorithms.A_Star;
import algorithms.BidirectionalSearch;
import algorithms.DFS;
import algorithms.UniformCost;

public class EightPuzzleProblem extends Problem{

	private Vector <Integer> walls;
	private EightPuzzleNode my_node ;
	private EightPuzzleNode my_last_node;
	
	
	public EightPuzzleProblem() {
		
		Vector<Vector<Integer>> b = new Vector<Vector<Integer>>();
		Vector<Integer>b1= new Vector<Integer>();
		b1.add(1);
		b1.add(6);
		b1.add(2);
		Vector<Integer>b2= new Vector<Integer>();
		b2.add(5);
		b2.add(3);
		b2.add(0);
	
		Vector<Integer>b3= new Vector<Integer>();
		b3.add(4);
		b3.add(7);
		b3.add(8);
		b.add(b1);
		b.add(b2);
		b.add(b3);
		
		 my_node = new EightPuzzleNode(1, 2, 0, null, this, b,0) ;
		 
		 
		 Vector<Vector<Integer>> bb = new Vector<Vector<Integer>>();
			
		 Vector<Integer>bb1= new Vector<Integer>();
			bb1.add(4);
			bb1.add(5);
			bb1.add(2);
			Vector<Integer>bb2= new Vector<Integer>();
			bb2.add(1);
			bb2.add(7);
			bb2.add(3);
		
			Vector<Integer>bb3= new Vector<Integer>();
			bb3.add(0);
			bb3.add(6);
			bb3.add(8);
			
			bb.add(bb1);
			bb.add(bb2);
			bb.add(bb3);
			
			my_last_node = new EightPuzzleNode(2, 2, 0, null, this, bb,0) ;
		 
//		UniformCost un = new UniformCost(this,"graph");
		A_Star un2 = new A_Star(this , "graph");
//		 BidirectionalSearch bd= new BidirectionalSearch(this ,"graph");
//		DFS df= new DFS(this,10000000,"graph");
	}
	
	public Node getFinalNode() {
		// TODO Auto-generated method stub
		return my_last_node;
		
	}

	
	public Node getFirstNode() {
		return my_node;
	}
	
	public static void main(String arg0[]) {
		EightPuzzleProblem qp = new EightPuzzleProblem();
	}
}
