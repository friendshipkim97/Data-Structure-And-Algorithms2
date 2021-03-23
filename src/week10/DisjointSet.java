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
		if(this.tail==null) return another; // s1.union(s2)�� ��� ���⼭ this�� s1���ǹ� 
		if(another.tail==null) return this;
		Node x = this.tail.head; // ex) x=s1
		Node y = another.tail.head; // ex) y=s2
		if(x.weight>=y.weight) {
		Node temp = y;
		while(temp!=null) {  // y�� ������ y�� ����� ������ head�� x�� ����Ű���� �Ѵ�. 
			temp.head=x;
			temp=temp.next;
		}
		this.tail.next=y; // x�� tail�� next�� y�� ����Ű������ 
		this.tail=another.tail; // �Է¹��� ������ tail�� ũ�Ⱑ ū ������ tail�� �ȴ�. 
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
			another.tail.next=x; // ũ�Ⱑū another������ next�� x�� ����Ű�����Ѵ�. 
			another.tail=this.tail; // this.tail�� ũ�Ⱑ �������Ƿ� �������ȴ�. 
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