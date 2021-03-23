package week11;

import java.util.HashSet;

public class Dijkstra<T> extends GraphInArray<T>{

//********  Prim Algorithm 	**************************
	HashSet<T> candidateVertex ;
	HashSet<T> selectedVertex ;
	HashSet<EdgeUnit> selectedEdge ;
	Object [] lastWriter; // �ٲܶ� �ٲ��� ���� �˻����� �������� array�� ���� 
	
	public void initDijkstra() {
		candidateVertex = new HashSet<T>();
		selectedVertex = new HashSet<T>();
		selectedEdge = new HashSet<EdgeUnit>();
		lastWriter = new Object [adjacentArray.size()];
		for (int i=0; i<adjacentArray.size(); i++) {
			candidateVertex.add(adjacentArray.get(i).data); 
			adjacentArray.get(i).value = 999;
		}
	}
	
	public void dijkstraState() {
		System.out.println("Candidate Set : "+candidateVertex);
		System.out.println("Selected Set : "+selectedVertex);
		System.out.println("Selected Edge Set : "+selectedEdge);
	}
	
	public void dijkstra(T r) {
		
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
						&& w+adjacentArray.get(iu).value<adjacentArray.get(indexOf(temp)).value) {

					adjacentArray.get(indexOf(temp)).value = w + adjacentArray.get(iu).value;
					lastWriter[indexOf(temp)]=u;
					System.out.println("<<< "+u+" update "+temp+" "+ adjacentArray.get(indexOf(temp)).value);

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

	//********************************************************
	public static void main(String[] args) {
		String [] vertex = {"a", "b", "c", "d",
				"e", "f", "g", "h"};
		Dijkstra<String> myG = new Dijkstra<>();

		myG.createGraph();

		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]);

		myG.insertEdge(vertex[0], vertex[1], 8);
		myG.insertEdge(vertex[0], vertex[2], 9);
		myG.insertEdge(vertex[0], vertex[3], 11);
		
		myG.insertEdge(vertex[1], vertex[4], 10);
		
		myG.insertEdge(vertex[2], vertex[1], 6);
		myG.insertEdge(vertex[2], vertex[3], 3);
		myG.insertEdge(vertex[2], vertex[4], 1); 
		
		myG.insertEdge(vertex[3], vertex[5], 8);
		myG.insertEdge(vertex[3], vertex[6], 8);
		
		myG.insertEdge(vertex[4], vertex[7], 2);
		
		myG.insertEdge(vertex[5], vertex[6], 7);
		
		myG.insertEdge(vertex[6], vertex[2], 12);
		myG.insertEdge(vertex[6], vertex[7], 5);
		
		myG.insertEdge(vertex[7], vertex[5], 4);

		System.out.println("*** Graph created ***");
		myG.showGraph();

		myG.initDijkstra();
		myG.dijkstraState(); // ����üũ 
		myG.dijkstra(vertex[0]); 
		myG.dijkstraState();
		System.out.println();
		
		for(int i=0; i<myG.adjacentArray.size(); i++) {
		System.out.println(vertex[i]+ " " + myG.adjacentArray.get(i).value);
		}


	}

}
