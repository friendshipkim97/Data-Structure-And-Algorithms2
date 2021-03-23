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
	String [] lastWriter; // �ٲܶ� �ٲ��� ���� �˻����� �������� array�� ���� 
	
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
			if (u==null) { // ������ ��ã���� �߸��� ���̱� ������ ����ó�� 
				System.out.println("Min-Vertex not found");
				break;
			}
			int iu=vertex.indexOf(u);
			selectedVertex.add(u);
			candidateVertex.remove(u);
			String tempEdge = findEdge(lastWriter[iu], u); // ���� ã�� ���� 
			if (tempEdge!=null) { // null�̸� �����ϰ� ���� �ȴ�. 
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
	
	private String findEdge(String u, String v) { // u���� v�� ���� ������ ã�� �� 
		int tempU = vertex.indexOf(u);
		int tempV = vertex.indexOf(v);
		if (u==null) // u�� ���ٴ� ���� ������� ���ٴ� �� 
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

		ArrayList<String> sortedEdge ; // ����
		DisjointSet<T> initDJS; // �ϳ��ϳ��� SET���� ������ֱ� ���ؼ�����
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
			
			for (int i=0; i<row; i++) { // Vertex���� 
				krus.add(initDJS.makeSet((T) vertex.get(i))); 
				for (int j=0; j<column; j++) {
				if(matrix[i][j]==1) {
					sortedEdge.add(vertex.get(i)+"("+weight[i][j]+")"+vertex.get(j));
				}
				}
			}
			for (int a=sortedEdge.size()-1; a>=1;a--) // bubblesort, ������ weight�������� �����ϴ� ���� 
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
				if (krus.get(tempI).findSet()!=krus.get(tempJ).findSet()) { // findSet�� ��ǥ���Ұ� ������� ����
					selectedEdge.add(e); // �̹� e�� ���� �ִ� ���߿� ���� ���� ���̹Ƿ� 
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
		myG.primState(); // ����üũ 
		myG.prim(3); // �λ� üũ 
		myG.primState();
		System.out.println();

		myG.initKruscal();
		myG.kruscal(); 

	}

}
