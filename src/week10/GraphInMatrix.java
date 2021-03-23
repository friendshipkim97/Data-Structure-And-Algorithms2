package week10;

import java.util.ArrayList;

import week10.GraphInArray.VertexUnit;

public class GraphInMatrix {
	
	public int[][] matrix = new int[8][8];
	public int[][] weight = new int[8][8];
	int row=8;
	int column=8;
	
	static String [] tempVertex = {"seoul", "daejeon", "daegu", "busan",
			"kwangju", "incheon", "ulsan", "jeju"};
	static int [] vertexValue = new int[8];
	
	ArrayList<String> vertex = new ArrayList<String>();
	
	public void createGraph() {
		
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				matrix[i][j]=0;
			}
		}
		
	}
	
	public void insertEdge(int row, int column, int weight) {
		this.matrix[row][column]=1;
		this.weight[row][column]=weight;
		this.matrix[column][row]=1;
		this.weight[column][row]=weight;
		
	}
	
	public void deleteEdge(int row, int column) {
		matrix[row][column]=0;
		
	}
	
	public int indexOf(int temp) {
		for (int i=0; i<row; i++ ) {
			if(temp==i) {
				return temp;
			}
		}
		return -1;
	}
	
	public void showGraph() {
		
		System.out.println("[ Graph ]");
		
		for (int i=0; i<row; i++) {
			 System.out.print(tempVertex[i] + "=>>");
		    for (int j=0; j<column; j++) {
		    	if(matrix[i][j]==1)
		    	 System.out.print(tempVertex[j]+ "=>>");
		    }
			System.out.println();
		}
		
	}

	public static void main(String[] args) {
		 
		GraphInMatrix myG = new GraphInMatrix();
		myG.createGraph();
		
		myG.insertEdge(0, 3, 13);
		myG.insertEdge(0, 7, 4);                
		myG.insertEdge(3, 1, 2);
		myG.insertEdge(3, 7, 6);
		myG.insertEdge(1, 4, 14);
		
		myG.insertEdge(1, 5, 7);
		myG.insertEdge(5, 2, 3);
		myG.insertEdge(5, 6, 9);
		myG.insertEdge(5, 3, 1);
		myG.insertEdge(5, 7, 11);
		
		myG.insertEdge(6, 2, 23);
		myG.insertEdge(6, 0, 8);
		//myG.insertEdge(7, 0, 10);
		
		myG.showGraph();
		
	
	}

	



	
}