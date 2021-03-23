package week10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

import week10.GraphInArray.EdgeUnit;

public class MSTMatrix<T> extends GraphInMatrix{
	
	public MSTMatrix() {
		
	}
	
	HashSet<String> candidateVertex ;
	HashSet<String> selectedVertex ;
	HashSet<String> selectedEdge ;
	String [] lastWriter; // 바꿀때 바꿔준 것을 검색하지 않으려고 array를 만듬 
	
	public void initPrim() {
		candidateVertex = new HashSet<String>();
		selectedVertex = new HashSet<String>();
		selectedEdge = new HashSet<String>();
		lastWriter = new String [row];
		for (int i=0; i<row; i++) {
			candidateVertex.add(vertex.get(i)); 
			vertexValue[i] = 999;
		}
	}
	
	public void primState() {
		System.out.println("Candidate Set : "+candidateVertex);
		System.out.println("Selected Set : "+selectedVertex);
		System.out.println("Selected Edge Set : "+selectedEdge);
	}
	
	public void prim(int r) {
		
		int index = indexOf(r);
		vertexValue[index] = 0;
		
		while(candidateVertex.size() != 0) {
			String u = extractMin(candidateVertex);
			if (u==null) { // 위에서 못찾으면 잘못된 것이기 때문에 예외처리 
				System.out.println("Min-Vertex not found");
				break;
			}
			int iu=vertex.indexOf(u);
			selectedVertex.add(u);
			candidateVertex.remove(u);
			String tempEdge = findEdge(lastWriter[iu], u); // 간선 찾는 과정 
			if (tempEdge!=null) { // null이면 무시하고 가면 된다. 
				System.out.println(">>>> "+tempEdge+" added.");
				selectedEdge.add(tempEdge);

			}
			
			for(int i=0; i<column; i++) {
				if(matrix[iu][i]==1 && candidateVertex.contains(vertex.get(i)) && weight[iu][i]<vertexValue[i]) {
					vertexValue[i]=weight[iu][i];
					lastWriter[i]=u;
					System.out.println("<<< "+u+" update "+vertex.get(i)+" "+weight[iu][i]);
				}
			}
		}
	}
	
	private String findEdge(String u, String v) { // u에서 v로 가는 엣지를 찾는 것 
		int tempU = vertex.indexOf(u);
		int tempV = vertex.indexOf(v);
		if (u==null) // u가 없다는 것은 출발점이 없다는 것 
			return null;
		String retVal = null;

		if(matrix[tempU][tempV]==1)
			return vertex.get(tempU)+"("+weight[tempU][tempV]+")"+vertex.get(tempV);

		return retVal;
	}

	private String extractMin(HashSet<String> aSet) {
		
		int min = 999;
		String resultVertex=null;
		for (String u : aSet){
			if (vertexValue[vertex.indexOf(u)] < min) {
				min=vertexValue[vertex.indexOf(u)];
				resultVertex=vertex.get(vertex.indexOf(u));
			}
		}
		return resultVertex;
		
	}
	
	//********  Kruscal Algorithm 	**************************

		ArrayList<String> sortedEdge ; // 정렬
		DisjointSet<T> initDJS; // 하나하나를 SET으로 만들어주기 위해서만듬
		ArrayList<DisjointSet<T>> krus; 
		DisjointSet<T> candidateVertexK ;
		DisjointSet<T> selectedVertexK ;
		
		public void initKruscal() {
			krus = new ArrayList<>();
			sortedEdge = new ArrayList<>();
			selectedEdge = new HashSet<String>();
			candidateVertexK = new DisjointSet<>();
			selectedVertexK = new DisjointSet<>();
			initDJS = new DisjointSet<>();
			
			for (int i=0; i<row; i++) { // Vertex나열 
				krus.add(initDJS.makeSet((T) vertex.get(i))); 
				for (int j=0; j<column; j++) {
				if(matrix[i][j]==1) {
					sortedEdge.add(vertex.get(i)+"("+weight[i][j]+")"+vertex.get(j));
				}
				}
			}
			for (int a=sortedEdge.size()-1; a>=1;a--) // bubblesort, 간선을 weight기준으로 정렬하는 과정 
				for (int b=0; b<a;b++) {
					int cut1 =sortedEdge.get(a).indexOf("(");
					int cut2 =sortedEdge.get(a).indexOf(")");
					int tempA = Integer.parseInt(sortedEdge.get(a).substring(cut1+1, cut2));
					int cut3 =sortedEdge.get(b).indexOf("(");
					int cut4 =sortedEdge.get(b).indexOf(")");
					int tempB = Integer.parseInt(sortedEdge.get(b).substring(cut3+1, cut4));
					if (tempA<tempB) {
						String temp=sortedEdge.get(a);
						sortedEdge.set(a, sortedEdge.get(b));
						sortedEdge.set(b, temp);
					}
			} 
			System.out.println("Sorted Edges");
			for (int k=0; k<sortedEdge.size(); k++)
				System.out.println("---"+sortedEdge.get(k));
			System.out.println();

		}
		public void kruscal() {
			while(selectedEdge.size()<column-1) {
				String e  = sortedEdge.get(0);
				sortedEdge.remove(0);
				int cut1 =e.indexOf("(");
				int cut2 =e.indexOf(")");
				String tempA = e.substring(0, cut1);
				String tempB = e.substring(cut2+1, e.length());
				int tempI = vertex.indexOf(tempA);
				int tempJ = vertex.indexOf(tempB);
				if (krus.get(tempI).findSet()!=krus.get(tempJ).findSet()) { // findSet은 대표원소가 어떤것인지 구함
					selectedEdge.add(e); // 이미 e가 쓸수 있는 것중에 가장 작은 것이므로 
					System.out.println("----"+e+"  added.");
					krus.get(tempI).union(krus.get(tempJ));
					//System.out.println(krus);

				}
			}
			
		}
	
		
	
	public static void main(String[] args) {
//		String [] tempVertex = {"seoul", "daejeon", "daegu", "busan",
//				"kwangju", "incheon", "ulsan", "jeju"};
//		int [] vertexValue = new int[8];
//		
		MSTMatrix myG = new MSTMatrix<>();
		
		for(int i=0; i<tempVertex.length; i++) {
			myG.vertex.add(tempVertex[i]);
		}
		
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
	//	myG.insertEdge(7, 0, 10);

		System.out.println("*** Graph created ***");
		myG.showGraph();

		myG.initPrim();
		myG.primState(); // 상태체크 
		myG.prim(3); // 부산 체크 
		myG.primState();
		System.out.println();

		myG.initKruscal();
		myG.kruscal(); 

	}

}
