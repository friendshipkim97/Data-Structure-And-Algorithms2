package week10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MST<T> extends GraphInArray<T>{

//********  Prim Algorithm 	**************************
	HashSet<T> candidateVertex ;
	HashSet<T> selectedVertex ;
	HashSet<EdgeUnit> selectedEdge ;
	Object [] lastWriter; // �ٲܶ� �ٲ��� ���� �˻����� �������� array�� ���� 
	
	public void initPrim() {
		candidateVertex = new HashSet<T>();
		selectedVertex = new HashSet<T>();
		selectedEdge = new HashSet<EdgeUnit>();
		lastWriter = new Object [adjacentArray.size()];
		for (int i=0; i<adjacentArray.size(); i++) {
			candidateVertex.add(adjacentArray.get(i).data); 
			adjacentArray.get(i).value = 999;
		}
	}
	
	public void primState() {
		System.out.println("Candidate Set : "+candidateVertex);
		System.out.println("Selected Set : "+selectedVertex);
		System.out.println("Selected Edge Set : "+selectedEdge);
	}
	
	public void prim(T r) {
		
		int index = indexOf(r);
		adjacentArray.get(index).value = 0;
		
		while(candidateVertex.size() != 0) {
			T u = extractMin(candidateVertex);

			if (u==null) { // ������ ��ã���� �߸��� ���̱� ������ ����ó�� 
				System.out.println("Min-Vertex not found");
				break;
			}
			int iu=indexOf(u);
			selectedVertex.add(u);
			candidateVertex.remove(u);
			EdgeUnit tempEdge = findEdge((T)lastWriter[iu], u); // ���� ã�� ���� 
			if (tempEdge!=null) { // null�̸� �����ϰ� ���� �ȴ�. 
				System.out.println(">>>> "+tempEdge+" added.");
				selectedEdge.add(tempEdge);

			}
			for (int i=0; i<adjacentArray.get(iu).adjList.size();i++) {
				T temp =adjacentArray.get(iu).adjList.get(i).destVertex;
				int w =adjacentArray.get(iu).adjList.get(i).weight;
				if (candidateVertex.contains(temp) // candidtaeVertex�� �����־�� �Ѵ�. 
						&& w<adjacentArray.get(indexOf(temp)).value) {

					adjacentArray.get(indexOf(temp)).value = w ;
					lastWriter[indexOf(temp)]=u;
					System.out.println("<<< "+u+" update "+temp+" "+w);

				}
			}
		}
	}
	
	private EdgeUnit findEdge(T u, T v) { // u���� v�� ���� ������ ã�� �� 
		if (u==null) // u�� ���ٴ� ���� ������� ���ٴ� �� 
			return null;
		EdgeUnit retVal = null;
		for (int i=0; i<adjacentArray.get(indexOf(u)).adjList.size(); i++) { // u�� ���� �͵��� ã�� �� 
			if (adjacentArray.get(indexOf(u)).adjList.get(i).destVertex==v) { // u���� v�ΰ��� �����̶�� ���̴�. 
				return adjacentArray.get(indexOf(u)).adjList.get(i); // ���� ��ȯ 
			}
		}
		return retVal;
	}

	private T extractMin(HashSet<T> aSet) {
		
		int min = 999;
		T resultVertex=null;
		for (T u : aSet){

			if (adjacentArray.get(indexOf(u)).value < min) {
				min=adjacentArray.get(indexOf(u)).value;
				resultVertex=adjacentArray.get(indexOf(u)).data;
			}
		}
		return resultVertex;
	}
//********  Kruscal Algorithm 	**************************

	ArrayList<EdgeUnit> sortedEdge ; // ����
	DisjointSet<T> initDJS; // �ϳ��ϳ��� SET���� ������ֱ� ���ؼ�����
	ArrayList<DisjointSet<T>> krus; 
	DisjointSet<T> candidateVertexK ;
	DisjointSet<T> selectedVertexK ;
	
	public void initKruscal() {
		krus = new ArrayList<>();
		sortedEdge = new ArrayList<>();
		selectedEdge = new HashSet<EdgeUnit>();
		candidateVertexK = new DisjointSet<>();
		selectedVertexK = new DisjointSet<>();
		initDJS = new DisjointSet<>();
		
		for (int i=0; i<adjacentArray.size(); i++) { // Vertex���� 
			krus.add(initDJS.makeSet(adjacentArray.get(i).data)); 
			for (int j=0; j<adjacentArray.get(i).adjList.size(); j++) {
				sortedEdge.add(adjacentArray.get(i).adjList.get(j)); // ���� sorting�� �ȵǾ����� �������� �ٸ��� 
			}
		}

		for (int i=sortedEdge.size()-1; i>=1;i--) // bubblesort, ������ weight�������� �����ϴ� ���� 
			for (int j=0; j<i;j++) {
				if (sortedEdge.get(i).weight<sortedEdge.get(j).weight) {
					EdgeUnit temp=sortedEdge.get(i);
					sortedEdge.set(i, sortedEdge.get(j));
					sortedEdge.set(j, temp);
				}
		} 
		System.out.println("Sorted Edges");
		for (int k=0; k<sortedEdge.size(); k++)
		{
			System.out.println("---"+sortedEdge.get(k));
		}
		System.out.println();

	}
	public void kruscal() {
		while(selectedEdge.size()<adjacentArray.size()-1) {
			EdgeUnit e  = sortedEdge.get(0);
			sortedEdge.remove(0);
			int ui = indexOf(e.sourceVertex);
			int vi = indexOf(e.destVertex);
			if (krus.get(ui).findSet()!=krus.get(vi).findSet()) { // findSet�� ��ǥ���Ұ� ������� ����
				selectedEdge.add(e); // �̹� e�� ���� �ִ� ���߿� ���� ���� ���̹Ƿ� 
				System.out.println("----"+e+"  added.");
				krus.get(ui).union(krus.get(vi));
				//System.out.println(krus);

			}
		}
	}
	
	//********************************************************
	public static void main(String[] args) {
		String [] vertex = {"seoul", "daejeon", "daegu", "busan",
				"kwangju", "incheon", "ulsan", "jeju"};
		MST<String> myG = new MST<>();

		myG.createGraph();

		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]);

		myG.insertEdge(vertex[0], vertex[3], 13);
		myG.insertEdge(vertex[0], vertex[7], 4);
		myG.insertEdge(vertex[3], vertex[1], 2);
		myG.insertEdge(vertex[3], vertex[7], 6);
		myG.insertEdge(vertex[1], vertex[4], 14);
		
		myG.insertEdge(vertex[1], vertex[5], 7);
		myG.insertEdge(vertex[5], vertex[2], 3); 
		myG.insertEdge(vertex[5], vertex[6], 9);
		myG.insertEdge(vertex[5], vertex[3], 1); 
		myG.insertEdge(vertex[5], vertex[7], 11);
		
		myG.insertEdge(vertex[6], vertex[2], 23);
		myG.insertEdge(vertex[6], vertex[0], 8);
	//	myG.insertEdge(vertex[7], vertex[0], 10);

		System.out.println("*** Graph created ***");
		myG.showGraph();

		myG.initPrim();
		myG.primState(); // ����üũ 
		myG.prim(vertex[3]); // �λ� üũ 
		myG.primState();
		System.out.println();

//		myG.initKruscal();
//		myG.kruscal(); 

	}

}
