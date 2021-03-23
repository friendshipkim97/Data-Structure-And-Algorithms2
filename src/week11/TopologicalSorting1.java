package week11;
import java.util.ArrayList;
import java.util.HashSet;

public class TopologicalSorting1<T> {
	
	private class GraphNode {
		T data;
		GraphNode link;
		
		public GraphNode(T input) {
			data = input;
			link = null;
		}
	} 
	
	ArrayList<GraphNode> adjacentList; // �� ���� ����� ��
	int [] targetCount = new int[6];
	ArrayList<T> zeroArray = new ArrayList<T>();
	
	public void createGraph() {
		adjacentList = new ArrayList<GraphNode>(); 
	}

	public boolean insertVertex(T v) {
		int index = indexOf(v);
		if (index>=0) // index�� 0���� ũ�ٴ� �� �̹� �����Ѵٴ� �� 
			return false; // Vertex already exist
		adjacentList.add(new GraphNode(v));
		return true;
	}

	public boolean insertEdge(T u, T v) { //  edge from u to v
		if (indexOf(u)<0 || indexOf(v)<0) // ������ �߰��Ҷ� vertax�� �ݵ�� �־�� �ϹǷ�, �������� ������ false�� ���� 
			return false; 
		int index = indexOf(u);
		GraphNode newNode = new GraphNode(v);  
		newNode.link = adjacentList.get(index).link; // insert at the first position ù��°�� �ٷ� �ڿ� ���̴� ���̴�. ����->�λ��̶�� �����̰���Ű�� �ִ� ���� �λ��� ����Ű������
		adjacentList.get(index).link = newNode; // ù��° ���� �� 
		// if undirected case, insertEdge(v, u) should be done!
		targetCount[indexOf(newNode.data)]++;
		return true;
	}
	
	private int indexOf(T u) { // �̹� ���� �ִ� �������� Ȯ���Ѵ�. 
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
		while (!(q.data.equals(v)) && q.link!=null) { // ��ũ���󰡼� ����� �� 
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
	
	public int getTargetCount(int i) {
		
		System.out.println(adjacentList.get(i).data + " => ���԰��� "+ targetCount[i] + "��");
		return targetCount[i];
	}
	
	private void makeList() {
		for(int i=0; i<targetCount.length; i++) {
			if(targetCount[i]==0) {
				zeroArray.add(adjacentList.get(i).data);	
			}
			}
	}
	
	private void topologicalSort() {
		
		for(int i=0; i<6; i++) {
			if(targetCount[i]==0) {
				GraphNode p = adjacentList.get(i).link;
				while(p!=null) {
					deleteEdge(adjacentList.get(i).data, p.data);
					targetCount[indexOf(p.data)]--;
					if(targetCount[indexOf(p.data)]==0) {
						zeroArray.add(p.data);
					}
				    p=p.link;
				}
			}
		}
		
	}

	public static void main(String[] args) {
		String [] vertex = {"���� ���ױ�", "��ȭ", "������ ���", "��� �ֱ�",
							"�����ֱ�", "��� Ǯ��ֱ�"};
		TopologicalSorting1<String> myG = new TopologicalSorting1<>(); // Linked List ���
		
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
		
		for(int i=0; i<myG.targetCount.length; i++) {
		myG.getTargetCount(i);
		}
		
		myG.makeList();
		
		myG.topologicalSort();
		
		System.out.println(myG.zeroArray);
		


		}

	

	

}