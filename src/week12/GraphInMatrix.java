package week12;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class GraphInMatrix {

	int[][] adjacentMatrix;
	int [] visited;
	
	protected void createGraph(int size) {
		adjacentMatrix = new int[size][size];
		for(int i=0; i<adjacentMatrix.length; i++) {
			for(int j=0; j<adjacentMatrix.length; j++) {
				adjacentMatrix[i][j]=0;
			}
		}
		visited = new int[size];
	}
	
	public boolean insertEdge(int u, int v) {
		if(u<adjacentMatrix.length&&v<adjacentMatrix.length) {
			adjacentMatrix[u][v] = adjacentMatrix[u][v]=1;
			return true;
		}else
			return false;
	}
	
	public boolean deleteEdge(int u, int v) {
		if(u<adjacentMatrix.length&&v<adjacentMatrix.length) {
			adjacentMatrix[u][v] = adjacentMatrix[u][v]=0;
			return true;
		}else
			return false;
	}
	
	public void showGraph() {
		for(int i=0; i< adjacentMatrix.length; i++) {
			System.out.println("adjacent list... from vertex number "+ i+" : ");
			for(int j=0; j< adjacentMatrix.length; j++) {
				System.out.println(" > " + adjacentMatrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public void BFS(int v) {
		if(v>=0) {
			Deque<Integer> que = new ArrayDeque<Integer>();
			visited = new int [adjacentMatrix.length];
			Arrays.fill(visited, 0);
			que.add(v);
			BFSRecursion(que);
			
		}else System.out.println("Wrong start-node");
	}

	private void BFSRecursion(Deque<Integer> que) {
		while(que.peek()!=null) { // peek�޼���� ���� ���� �� �Ʒ����� poll�� ������ �޼��� 
			int index = que.poll();
			if (visited[index]==1) { // �̹̹湮�ߴ����� ���� 
				return;
			}
			else {
				System.out.println(">>> Node " + index + "visited.");
				visited[index]=1;
				for(int childNode = adjacentMatrix.length-1; childNode<=0; childNode--) {
					if((adjacentMatrix[index][childNode] == 1) && (visited[childNode] == 0))
					{
						que.add(childNode);
					}
				}
				BFSRecursion(que);
			}
	}
}
	public void DFS(int v){		
	
		if (v>=0) {
			Stack<Integer> stack = new Stack<Integer>(); 
			visited = new int [adjacentMatrix.length];
			Arrays.fill(visited, 0);
			stack.push(v);
			DFSRecursion(stack);
		}
		else
			System.out.println("Worng start-node");
	}

	private void DFSRecursion(Stack<Integer> stack) {

		while (!stack.isEmpty()) {
			int index=stack.pop();
			if (visited[index]==1) { // �̹̹湮�ߴ����� ���� 
				return;
			}
			else {
			visited[index]= 1;  // �湮 ����� ���� 
			
			for(int childNode = adjacentMatrix.length-1; childNode >= 0; childNode--) {
				if((adjacentMatrix[index][childNode] == 1) && (visited[childNode] == 0))
				{
					stack.push(childNode);
					DFSRecursion(stack);
				}
			}
//			DFSRecursion(stack);
			System.out.println(">>> Node " + index + "visited."); // BFS�� DFS�� ���̴� Recursion call�� �տ� �ֳ� �ڿ� �ֳ� �����̴�.
		}
		}
	}
	
	public static void main(String[] args) {
		
		int numOfVertex = 8;
		 
		GraphInMatrix myG = new GraphInMatrix();
		myG.createGraph(numOfVertex);
		
		myG.insertEdge(0, 1);
		myG.insertEdge(1, 2);                                  
		myG.insertEdge(1, 3);
		myG.insertEdge(2, 4);
		myG.insertEdge(3, 4);
		myG.insertEdge(3, 5);
		myG.insertEdge(5, 7);
		myG.insertEdge(5, 6);
		myG.insertEdge(6, 0);
		myG.insertEdge(2, 3);
		
		myG.showGraph();
		
		System.out.println("\n*** BFS *** \n");
		myG.BFS(0);

		System.out.println("\n*** DFS *** \n");
		myG.DFS(0);
		
		System.out.println("\n*** BFS *** \n");
		myG.BFS(7);

		System.out.println("\n*** DFS *** \n");
		myG.DFS(7);
	}
	
	
}
