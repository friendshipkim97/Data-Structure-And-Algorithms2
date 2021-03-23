package week6;

public class dSetTree {
	
	class Node{
		int data;
		Node parent;
		public Node(int i) {
			this.data=i;
			this.parent=null;
		}
		
		public String toString() {
			return ""+data;
		}
		
	}
	Node root;
	int count;
	dSetTree temp = null;
	
	public dSetTree() {
		root=null;
	}
	
	public dSetTree makeSet(int i) {
		Node newNode = new Node(i);
		dSetTree newSet = new dSetTree();
		newSet.root=newNode;
		return newSet;
	}
	public Node findSet(Node root) {
		
		if(root.parent==null) {
			System.out.println("find out count:" +count);
			return root;
		}
		else {
			count++;
			return findSet(root.parent);
			
		}
	}
	
	public dSetTree union(dSetTree another) {
		if(this.root==null) return another;
		else if(another.root==null) return this;
		else {
			
			another.root.parent=this.root;
			return this;
		}
		
	}
	
	public String toString() {
	 Node temp =root;
	 String retVal = ""+temp.toString();
		while(temp.parent!=null) {
			temp=temp.parent;
			retVal=retVal+"  ->  "+temp.toString();
		}
		return retVal;
	
	}
	public static void main(String[] args) {
		dSetTree me = new dSetTree();
		dSetTree s1 = me.makeSet(1);
		dSetTree s2 = me.makeSet(2);
		dSetTree s3 = me.makeSet(3);
		dSetTree s4 = me.makeSet(4);
		dSetTree s5 = me.makeSet(5);
		dSetTree s6 = me.makeSet(6);
		
		s1.union(s2);
		s2.union(s3);
		
		s1.union(s4);
		
		s5.union(s6);
		
		System.out.println(s3); // 루트에서 내려갈 방법이 없으므로 아래의 노드부터 시작해 루트까지 찾아간다. 
		System.out.println(s4);
		System.out.println(s6);
		
		System.out.println("노드 2가 속한 집합의 루트노드 :" + s3.findSet(s2.root));

		System.out.println(s3);
	}
}
