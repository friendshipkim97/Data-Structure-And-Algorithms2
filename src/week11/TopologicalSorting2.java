package week11;
import java.util.ArrayList;
import java.util.HashSet;

public class TopologicalSorting2<T> {
	
	private class GraphNode {
		T data;
		GraphNode link;
		
		public GraphNode(T input) {
			data = input;
			link = null;
		}
	} 
	
	ArrayList<GraphNode> adjacentList; // 앞 쪽을 만드는 것
	int[] visited = new int[6]; 
	ArrayList<T> resultArray = new ArrayList<T>();
	
	public void createGraph() {
		adjacentList = new ArrayList<GraphNode>(); 
	}

	public boolean insertVertex(T v) {
		int index = indexOf(v);
		if (index>=0) // index가 0보다 크다는 건 이미 존재한다는 것 
			return false; // Vertex already exist
		adjacentList.add(new GraphNode(v));
		return true;
	}

	public boolean insertEdge(T u, T v) { //  edge from u to v
		if (indexOf(u)<0 || indexOf(v)<0) // 엣지를 추가할땐 vertax가 반드시 있어야 하므로, 존재하지 않으면 false로 리턴 
			return false; 
		int index = indexOf(u);
		GraphNode newNode = new GraphNode(v);  
		newNode.link = adjacentList.get(index).link; // insert at the first position 첫번째거 바로 뒤에 붙이는 것이다. 서울->부산이라면 서울이가리키고 있던 것을 부산이 가리키도록함
		adjacentList.get(index).link = newNode; // 첫번째 것이 됨 
		// if undirected case, insertEdge(v, u) should be done!
		return true;
	}
	
	private int indexOf(T u) { // 이미 들어와 있는 정점인지 확인한다. 
		for (int i =0;i<adjacentList.size(); i++ ) {
			if ( adjacentList.get(i).data.equals(u))
				return i;
		}
		return -1;
	}

	public HashSet<T> adjacent(T v){
		HashSet<T> result= new HashSet<T>();
		System.out.println(">>> Adj. test :  "+ v) ;

		int index = indexOf(v);
		if (index==-1) {
			System.out.println(v +"  not found");
			return result; // Vertex not found;
		}
		GraphNode  p = adjacentList.get(index);
		System.out.println(">>> Adj. test : p "+ p.data) ;

		while (p.link!=null) {
			result.add(p.link.data);
			p=p.link;
		}
		return result;
	}

	public boolean deleteVertex(T v) {
		int index = indexOf(v);
		if (index<0)
			return false;  // Vertex is not found
		GraphNode p = adjacentList.get(index);
		
		if (p.link!=null) {
			GraphNode q = p.link;
			while (q!=null) {
				deleteEdge(q.data, v);
				q=q.link;
			}	
		}
		adjacentList.remove(index);
		return true;
	}

	public boolean deleteEdge(T u, T v) { //  edge from u to v
		int index = indexOf(u);
		if (index<0)
			return false;  // Tail-Vertex is not found
		GraphNode p = adjacentList.get(index);
		GraphNode q = p.link;
		while (!(q.data.equals(v)) && q.link!=null) { // 링크따라가서 지우는 것 
			p= p.link; q=q.link;
		}
		if (q.data.equals(v)) {
			p.link=q.link;
			// if undirected case, deleteEdge(v, u) should be done!
			return true;
		}
		return false;
	}
	
	public void showGraph() {
		System.out.println("[ Graph ]");
		
		for (int i=0; i<adjacentList.size(); i++) {
		    System.out.print(adjacentList.get(i).data + " ");
			GraphNode p = adjacentList.get(i).link;
			while(p!=null) {
			    System.out.print(" => "+p.data);
			    p=p.link;
			}
			System.out.println();
		}
	}
	
	private void initVisited() {
		for(int i=0; i<visited.length; i++) {
			visited[i]=0;
		}
		
	}
	
	public void topologicalSort() {
	    for (int i = 0; i < adjacentList.size(); i++) {
	        if (visited[i] == 0) {
	            DFS(i);
	        }
	    }
	    

	}

	public void DFS(int vertex) {
	    visited[vertex] = 1;

	        GraphNode p = adjacentList.get(vertex).link;
	        while(p!=null && visited[indexOf(p.data)] == 0) {
	        	
			    DFS(indexOf(p.data));
			    p=p.link;
	        	
			}

	    resultArray.add(adjacentList.get(vertex).data);
	}



	public static void main(String[] args) {
		String [] vertex = {"냄비에 물붓기", "점화", "라면봉지 뜯기", "라면 넣기",
							"수프넣기", "계란 풀어넣기"};
		TopologicalSorting2<String> myG = new TopologicalSorting2<>(); // Linked List 방식
		
		myG.createGraph();
		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]); 
		
		myG.insertEdge(vertex[0], vertex[1]);
		myG.insertEdge(vertex[1], vertex[3]);  
		myG.insertEdge(vertex[1], vertex[4]);  
		myG.insertEdge(vertex[1], vertex[5]);  
		myG.insertEdge(vertex[2], vertex[3]);  
		myG.insertEdge(vertex[2], vertex[4]);  
		myG.insertEdge(vertex[3], vertex[5]);  
		myG.insertEdge(vertex[4], vertex[5]); 

		System.out.println("*** Graph created ***");
		myG.showGraph();
		
		System.out.println("============================================");
		
		myG.initVisited();
		myG.topologicalSort();
		
		System.out.println();
		System.out.println("===================DFS topologicalSort2===================");
		
		for(int i=myG.resultArray.size()-1; i>=0; i--) {
			if(i==0) {
			System.out.print(myG.resultArray.get(i));
			}
			else {
				System.out.print(myG.resultArray.get(i) + " => ");
			}
			}


		}



	

	

}