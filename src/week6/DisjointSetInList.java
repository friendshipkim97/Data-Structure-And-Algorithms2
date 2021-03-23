package week6;

public class DisjointSetInList {

	class Node{
		int data;
		Node head;
		Node next;
		int weight;
		public Node(int i) {
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
	public DisjointSetInList() {
		tail=null;
	}
	
	public DisjointSetInList makeSet(int i) {
		Node newNode = new Node(i);
		DisjointSetInList newSet = new DisjointSetInList();
		newSet.tail=newNode;
		return newSet;
	}
	
	public Node findSet() {
		if(this.tail==this.tail.head) {
			System.out.println("Ȯ��");
			return this.tail;
		}
		else {
		
			return this.tail.head;
		}
	}
	
	public DisjointSetInList union(DisjointSetInList another) {
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
	public static void main(String[] args) {
		DisjointSetInList me = new DisjointSetInList();
		DisjointSetInList s1 = me.makeSet(1);
		DisjointSetInList s2 = me.makeSet(2);
		DisjointSetInList s3 = me.makeSet(3);
		DisjointSetInList s4 = me.makeSet(4);
		DisjointSetInList s5 = me.makeSet(5);
		
		System.out.println("Head of s5 :"+s5.findSet());
		
		System.out.println(s1);
		s1.union(s2);
		System.out.println(s1);
		System.out.println(s2);
		
		s1.union(s5);
		s3.union(s4);
		System.out.println(s5);
		System.out.println(s1);
		System.out.println(s3);
		
		System.out.println("Head of s5 :"+s5.findSet());
		System.out.println("Head of s4 :"+s4.findSet());
	
	}
}
