package week6;

public class dSetTreeCompression {
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
	public dSetTreeCompression() {
		root=null;
	}
	
	public dSetTreeCompression makeSet(int i) {
		Node newNode = new Node(i);
		dSetTreeCompression newSet = new dSetTreeCompression();
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
			return root.parent=findSet(root.parent);
			
		}
	}
	
	
	public dSetTreeCompression union(dSetTreeCompression another) {
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
		dSetTreeCompression me = new dSetTreeCompression();
		dSetTreeCompression s1 = me.makeSet(1);
		dSetTreeCompression s2 = me.makeSet(2);
		dSetTreeCompression s3 = me.makeSet(3);
		dSetTreeCompression s4 = me.makeSet(4);
		dSetTreeCompression s5 = me.makeSet(5);
		dSetTreeCompression s6 = me.makeSet(6);
		
		s1.union(s2);
		s3.union(s4);
		
		s1.union(s3);
		
		s4.union(s5);
		s4.union(s6);
		
		System.out.println(s2); // 루트에서 내려갈 방법이 없으므로 아래의 노드부터 시작해 루트까지 찾아간다. 
		System.out.println(s5);
		System.out.println(s6);
		
		System.out.println("노드 6이 속한 집합의 루트노드 :" + s6.findSet(s6.root));
		
		System.out.println("-----------findset 진행후-----------");
		System.out.println(s6);
		System.out.println(s4);
		System.out.println(s5);
		System.out.println(s3);
	
	}
}
