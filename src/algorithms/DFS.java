package algorithms;

import java.util.Vector;

import problems.Node;
import problems.Problem;

public class DFS {
	private Vector<Node> closed_list;

	private Vector<Node> open_list;
	private Node first;
	private Problem my_problem;
	private int depth;
	private Integer memory;
	private Integer expand;
	private Integer seen_nodes;
	private String type;
	private boolean found ;
	public DFS(Problem my_problem, int depth, String type) {

		this.depth = depth;
		this.my_problem = my_problem;
		this.type = type;
		closed_list = new Vector<Node>();
		open_list = new Vector<Node>();
		memory = 0;
		expand = 0;
		seen_nodes = 0;
		found=false;
		first = my_problem.getFirstNode();
		open_list.add(first);
		DFS_search();

	}

	private void DFS_search() {
		// TODO Auto-generated method stub
		if(depth==-1){
			depth=0;
			while (!found){
				depth ++;
				System.out.println("algorithm is searching in depth:  "+ depth);
				start_DFS_search();
				open_list.removeAllElements();
				open_list.add(my_problem.getFirstNode());
				closed_list.removeAllElements();
			}
		}

		else start_DFS_search();
	}

	public void start_DFS_search() {

		System.out.println("came");

		while (!open_list.isEmpty()) {


			if (open_list.lastElement().isFinal()) {
				showResult_g(open_list.lastElement());
				found=true;
				return;
			}

			Node last = open_list.lastElement();
			if(type.equalsIgnoreCase("graph")){
				closed_list.add(last);
			}
			System.out.println("last ones depth :"+ last.getDepth());
			open_list.remove(last);
			expand++;

			for (int i = 0; i < last.getChildren().size(); i++) {

				boolean what = true;

				for (int j = 0; j < closed_list.size(); j++) {

					if (closed_list.elementAt(j).isSame(
							last.getChildren().elementAt(i))){

						what = false;
					}
				}

				if (what) {

					if(depth==0)
						open_list.add(last.getChildren().elementAt(i));
					else if ( last.getChildren().elementAt(i).getDepth() <= depth
							)
						open_list
								.add(last.getChildren().elementAt(i));
					seen_nodes++;
				}

			}

			if (open_list.size()+closed_list.size() > memory) {
				memory = open_list.size()+closed_list.size();
			}

		}
	}

	private void showResult_g(Node node) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("This is DFS search ("+type+") with depth : "+depth);
		System.out.println("this is count of expanded nodes : " + expand);
		System.out.println("this is count of observed nodes : " + seen_nodes);
		System.out.println("and the memory usage : " + memory);

		while (node != null) {
			(node).print();
			node = node.getParent();
			System.out.println("**********");
		}

	}

}
