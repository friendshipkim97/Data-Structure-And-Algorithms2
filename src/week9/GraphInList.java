package week9;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Stack;

public class GraphInList<T> {
	
	private class GraphNode {
		T data;
		GraphNode link;
		
		public GraphNode(T input) {
			data = input;
			link = null;
		}
	} 
	
	ArrayList<GraphNode> adjacentList; // 앞 쪽을 만드는 것
	int[] visited; // BFS, DFS에서 사용 
	
	public void createGraph() {
		adjacentList = new ArrayList<GraphNode>(); // 행렬방식이라면 여기서 초기화 시켜줘야 함 
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

	public void BFS(T v){	
		int index = indexOf(v);

		if (index>=0) {
			Deque<Integer> que = new ArrayDeque<Integer>();
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0); // 방문하지 않은 노드는 0으로 표시 
			que.add(index);
			BFSRecursion(que);
		}
		else
			System.out.println("Starting vertex not found");

	}

	private void BFSRecursion(Deque<Integer> que) {
	
		while(que.peek()!=null) { // peek메서드는 간만 보는 것 아래에서 poll이 꺼내는 메서드 
			int index = que.poll();
			System.out.println(">>> Polled out from Queue :"+index +"  "+adjacentList.get(index).data+" visited :"+visited[index]);

			if (visited[index]==1) { // 이미방문했는지의 여부 
				return;
			}
			else {
				System.out.println(adjacentList.get(index).data);
				visited[index]=1;
				GraphNode temp= adjacentList.get(index).link;
		
				while(temp!=null) {
					que.add(indexOf(temp.data)); // que에 넣어줌
					System.out.println(">>> Added into Queue :"+indexOf(temp.data)+"  "+temp.data);
					temp=temp.link; // 하나의 노드에 여러개 연결된 것을 찾아나가기 위해서 
				}
				
				BFSRecursion(que);
			}
		}
		return;
	}
	
	public void DFS(T v){		
		int index = indexOf(v);
		if (index>=0) {
			Stack<Integer> st = new Stack<Integer>(); 
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0);
			st.push(index);
			DFSRecursion(st);
		}
		else
			System.out.println("Starting vertex not found");

	}

	private void DFSRecursion(Stack<Integer> st) {

		if (st.size()>0) {
			int index=st.pop();
			System.out.println(">>> Popped out from Stack :"+index +"  "+adjacentList.get(index).data+" visited :"+visited[index]);

			if (visited[index]>0) {
				return;
			}
			visited[index]= 1;  // 방문 기록을 남김 
			
			GraphNode temp= adjacentList.get(index).link;
			while(temp!=null) {
					st.push(indexOf(temp.data));
					System.out.println(">>> Pushed into Stack :"+indexOf(temp.data)+"  "+temp.data);
					DFSRecursion(st); // BFS는 DFS와 다르게 루프 안쪽에 있다. 
					temp=temp.link;
			}
			System.out.println(adjacentList.get(index).data); // Recursion이 끝나고 나머지를 적어줌 
		}
	}


	public static void main(String[] args) {
		String [] vertex = {"seoul", "daejeon", "daegu", "busan",
							"kwangju", "incheon", "ulsan", "jeju"};
		GraphInList<String> myG = new GraphInList<>(); // Linked List 방식
		
		myG.createGraph();
		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]); // seoul daejeon을 차례로 넣음. 
		
		myG.insertEdge(vertex[0], vertex[3]);
		myG.insertEdge(vertex[0], vertex[7]);  // 이 프로그램에서는 seoul노드의 링크에 부산을 연결하고 그 후 추가로 간선을 만들경우
		                                       // seoul과 부산사이에 jeju를 연결한다. 
		myG.insertEdge(vertex[3], vertex[1]);
		myG.insertEdge(vertex[3], vertex[7]);
		myG.insertEdge(vertex[1], vertex[4]);
		myG.insertEdge(vertex[1], vertex[5]);
		myG.insertEdge(vertex[5], vertex[2]);
		myG.insertEdge(vertex[5], vertex[6]);
		myG.insertEdge(vertex[5], vertex[3]);
		myG.insertEdge(vertex[5], vertex[7]);
		myG.insertEdge(vertex[6], vertex[2]);
		myG.insertEdge(vertex[6], vertex[0]);
		
		
		
//		myG.insertEdge(vertex[7], vertex[0]);
		
//		myG.insertEdge(vertex[0], vertex[1]);
//		myG.insertEdge(vertex[0], vertex[6]);
//		myG.insertEdge(vertex[0], vertex[7]);
//		myG.insertEdge(vertex[1], vertex[2]);
//		myG.insertEdge(vertex[1], vertex[5]);
//		myG.insertEdge(vertex[2], vertex[3]);
//		myG.insertEdge(vertex[2], vertex[4]);

		System.out.println("*** Graph created ***");
		myG.showGraph();
		
		System.out.println("--- Adjacent Vertices to : "+vertex[5]);
		HashSet<String> adj = myG.adjacent(vertex[5]);
		System.out.println(adj);
		System.out.println("--- Adjacent Vertices to : "+vertex[0]);
		adj = myG.adjacent(vertex[0]);
		System.out.println(adj);

		
		int i=0;
		System.out.println("--- BFS & DFS Test --- Start from : "+ vertex[i]);

		System.out.println("\n*** BFS *** \n");
		myG.BFS(vertex[i]);

		System.out.println("\n*** DFS *** \n");
		myG.DFS(vertex[i]);

		}

}