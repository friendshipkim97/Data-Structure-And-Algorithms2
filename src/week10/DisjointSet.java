package week10;

public class DisjointSet<T> {

	class Node{
		
		T data;
		Node head;
		Node next;
		int weight;
		
		public Node(T i) {
			this.data=i;
			this.head=this;
			this.next=null;
			weight=1;
		}
		
		public String toString() {
			return ""+data+"("+weight+")";
		}
	}
	Node tail;
	public DisjointSet() {
		tail=null;
	}
	
	public DisjointSet<T> makeSet(T i) {
		Node newNode = new Node(i);
		DisjointSet<T> newSet = new DisjointSet<T>();
		newSet.tail=newNode;
		return newSet;
	}
	
//	public DisjointSet<T> makeSet(T i) {
//		Node newNode = new Node(i);
//		DisjointSet<T> newSet = new DisjointSet<T>();
//		newSet.tail=newNode;
//		return newSet;
//	}
	
	public Node findSet() {
		if(this.tail==this.tail.head) {
			return this.tail;
		}
		else {
		
			return this.tail.head;
		}
	}
	
	public DisjointSet<T> union(DisjointSet<T> another) {
		if(this.tail==null) return another; // s1.union(s2)일 경우 여기서 this는 s1을의미 
		if(another.tail==null) return this;
		Node x = this.tail.head; // ex) x=s1
		Node y = another.tail.head; // ex) y=s2
		if(x.weight>=y.weight) {
		Node temp = y;
		while(temp!=null) {  // y를 포함해 y에 연결된 노드들의 head가 x를 가리키도록 한다. 
			temp.head=x;
			temp=temp.next;
		}
		this.tail.next=y; // x의 tail의 next가 y를 가리키도록함 
		this.tail=another.tail; // 입력받은 집합의 tail이 크기가 큰 집합의 tail이 된다. 
		x.weight += y.weight;
		y.weight = 1;
		return this;
	}
		else {
			Node temp=x;
			while(temp!=null) {
				temp.head=y;
				temp=temp.next;
			}
			another.tail.next=x; // 크기가큰 another집합의 next가 x를 가리키도록한다. 
			another.tail=this.tail; // this.tail이 크기가 더작으므로 꼬리가된다. 
			return another;
		}
	}
	
	public String toString() {
		if(this.tail==null) return null;
		Node temp = tail.head;
		String retVal = ""+temp.toString();
		while(temp.next!=null) {
			temp=temp.next;
			retVal=retVal+"  ->  "+temp.toString();
		}
		return retVal;
	}
	
}