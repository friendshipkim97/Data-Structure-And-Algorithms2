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
	Object [] lastWriter; // 바꿀때 바꿔준 것을 검색하지 않으려고 array를 만듬 
	
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

			if (u==null) { // 위에서 못찾으면 잘못된 것이기 때문에 예외처리 
				System.out.println("Min-Vertex not found");
				break;
			}
			int iu=indexOf(u);
			selectedVertex.add(u);
			candidateVertex.remove(u);
			EdgeUnit tempEdge = findEdge((T)lastWriter[iu], u); // 간선 찾는 과정 
			if (tempEdge!=null) { // null이면 무시하고 가면 된다. 
				System.out.println(">>>> "+tempEdge+" added.");
				selectedEdge.add(tempEdge);

			}
			for (int i=0; i<adjacentArray.get(iu).adjList.size();i++) {
				T temp =adjacentArray.get(iu).adjList.get(i).destVertex;
				int w =adjacentArray.get(iu).adjList.get(i).weight;
				if (candidateVertex.contains(temp) // candidtaeVertex에 속해있어야 한다. 
						&& w<adjacentArray.get(indexOf(temp)).value) {

					adjacentArray.get(indexOf(temp)).value = w ;
					lastWriter[indexOf(temp)]=u;
					System.out.println("<<< "+u+" update "+temp+" "+w);

				}
			}
		}
	}
	
	private EdgeUnit findEdge(T u, T v) { // u에서 v로 가는 엣지를 찾는 것 
		if (u==null) // u가 없다는 것은 출발점이 없다는 것 
			return null;
		EdgeUnit retVal = null;
		for (int i=0; i<adjacentArray.get(indexOf(u)).adjList.size(); i++) { // u에 붙은 것들을 찾는 것 
			if (adjacentArray.get(indexOf(u)).adjList.get(i).destVertex==v) { // u에서 v로가는 간선이라는 뜻이다. 
				return adjacentArray.get(indexOf(u)).adjList.get(i); // 간선 반환 
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

	ArrayList<EdgeUnit> sortedEdge ; // 정렬
	DisjointSet<T> initDJS; // 하나하나를 SET으로 만들어주기 위해서만듬
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
		
		for (int i=0; i<adjacentArray.size(); i++) { // Vertex나열 
			krus.add(initDJS.makeSet(adjacentArray.get(i).data)); 
			for (int j=0; j<adjacentArray.get(i).adjList.size(); j++) {
				sortedEdge.add(adjacentArray.get(i).adjList.get(j)); // 아직 sorting은 안되었지만 간선들이 다모임 
			}
		}

		for (int i=sortedEdge.size()-1; i>=1;i--) // bubblesort, 간선을 weight기준으로 정렬하는 과정 
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
			if (krus.get(ui).findSet()!=krus.get(vi).findSet()) { // findSet은 대표원소가 어떤것인지 구함
				selectedEdge.add(e); // 이미 e가 쓸수 있는 것중에 가장 작은 것이므로 
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
		myG.primState(); // 상태체크 
		myG.prim(vertex[3]); // 부산 체크 
		myG.primState();
		System.out.println();

//		myG.initKruscal();
//		myG.kruscal(); 

	}

}
