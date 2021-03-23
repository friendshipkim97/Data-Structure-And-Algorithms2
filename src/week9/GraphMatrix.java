package week9;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

	
public class GraphMatrix {
	
	public int[][] matrix = new int[8][8];
	int row=8;
	int column=8;
	int[] visited; // BFS, DFS에서 사용 
	
	String [] vertex = {"seoul", "daejeon", "daegu", "busan",
			"kwangju", "incheon", "ulsan", "jeju"};
   
	public void createGraph() {
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				matrix[i][j]=0;
			}
		}
		
	}
	
	private void insertEdge(int row, int column) {
		matrix[row][column]=1;
		
	}
	
	private void deleteEdge(int row, int column) {
		matrix[row][column]=0;
		
	}
	
	private int indexOf(int temp) {
		for (int i=0; i<row; i++ ) {
			if(temp==i) {
				return temp;
			}
		}
		return -1;
	}
	
	public void showGraph() {
		System.out.println(row);
		System.out.println("[ Graph ]");
		
		for (int i=0; i<row; i++) {
			 System.out.print(vertex[i] + "=>>");
		    for (int j=0; j<column; j++) {
		    	if(matrix[i][j]==1)
		    	 System.out.print(vertex[j]+ "=>>");
		    }
			System.out.println();
		}
	}
	
	public void BFS(int i){	
		int index = indexOf(i);

		if (index>=0) {
			Deque<Integer> que = new ArrayDeque<Integer>();
			visited = new int [row];
			Arrays.fill(visited, 0); // 방문하지 않은 노드는 0으로 표시 
			que.add(index);
			BFSRecursion(que);
		}
		else
			System.out.println("Starting vertex not found");
	}

	private void BFSRecursion(Deque<Integer> que) {

		while(que.peek()!=null) { // peek메서드는 간만 보는 것 아래에서 poll이 꺼내는 메서드 
			int index = que.poll(); // 맨 앞에 있는 요소를 꺼내고 queue에서 지움 
			System.out.println(">>> Polled out from Queue :"+index +"  "+vertex[index]+" visited :"+visited[index]);

			if (visited[index]==1) { // 이미방문했는지의 여부 
				return;
			}
			else {
				System.out.println(vertex[index]);
				visited[index]=1;

				for(int i=7; i>=0; i--) {
					if((matrix[index][i] == 1) && (visited[i] == 0))
					{
						que.add(i);
						System.out.println(">>> Added into Queue : " + i);
					}
				}
		
				BFSRecursion(que);
			}
		}

	}
	
	public void DFS(int i){		
		int index = indexOf(i);
		if (index>=0) {
			Stack<Integer> st = new Stack<Integer>(); 
			visited = new int [row];
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
			System.out.println(">>> Polled out from Queue :"+index +"  "+vertex[index]+" visited :"+visited[index]);

			if (visited[index]>0) {
				return;
			}
			visited[index]= 1;  // 방문 기록을 남김 
			
//			for(int i=7; i>=0; i--) {
			for(int i=0; i<=7; i++) {
				if((matrix[index][i] == 1) && (visited[i] == 0))
				{
					st.add(i);
					System.out.println(">>> Added into Queue : " + i);
					DFSRecursion(st);
				}
			}
			System.out.println(vertex[index]); // Recursion이 끝나고 나머지를 적어줌 
		}
	}

	public static void main(String[] args) {
		 
		GraphMatrix myG = new GraphMatrix();
		myG.createGraph();
		
		// --graph--
//		myG.insertEdge(0, 3);
//		myG.insertEdge(0, 7); 
//		                                       
//		myG.insertEdge(3, 1);
//		myG.insertEdge(3, 7);
//		myG.insertEdge(1, 4);
//		myG.insertEdge(1, 5);
//		myG.insertEdge(5, 2);
//		myG.insertEdge(5, 6);
//		myG.insertEdge(5, 3);
//		myG.insertEdge(4, 7);
//		myG.insertEdge(6, 2);
//		myG.insertEdge(6, 0);
//		myG.insertEdge(7, 0);
		
		
		//--tree--
		
		myG.insertEdge(0, 1);
		myG.insertEdge(0, 6);
		myG.insertEdge(0, 7);
		myG.insertEdge(1, 2);
		myG.insertEdge(1, 5);
		myG.insertEdge(2, 3);
		myG.insertEdge(2, 4);
		
		
		myG.showGraph();
		
		
		int i=0;
		
		System.out.println("\n*** BFS *** \n");
		myG.BFS(i);

		System.out.println("\n*** DFS *** \n");
		myG.DFS(i);
	}



	
}
