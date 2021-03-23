package week4;

public class BinarySearchTree<E extends Comparable<E>> {  // binary search tree
	private class Node<E> {
		E key;
		Node<E> parent;  // double linked list
		Node<E> left;
		Node<E> right;
		public Node(E k) {
			key=k;
			parent = null;
			left = null;
			right = null;
		}
		public String toString() {
			String returnval = "";
			return returnval+key; 
		}
	}
	
	Node<E> root;
	int ipl;
	int sum;
	int ipltemp;
	
	public BinarySearchTree() {
		root = null;
		ipl=0;
		sum=0;
		
	}
	
	public Node<E> insert(E x) {
		Node<E> newNode = new Node<>(x);
		Node<E> inserted = insertNode(root, newNode);
		
		if(inserted==null) 
			System.out.print(">> Key-duplication : Insert failed");
		return root;
			
	}

	private Node<E> insertNode(Node<E> p, Node<E> x){
		if(p==null) { // the only case  : root
			root=x;
			return root;
		}
		if(x.key.compareTo(p.key)<0) {
			if(p.left==null) {
				p.left=x; x.parent=p; return x;
			}
			else return insertNode(p.left, x);
		}
		else if(x.key.compareTo(p.key)>0){
			if(p.right==null) {
				p.right=x; x.parent=p; return x;
			}
			else return insertNode(p.right, x);
		}
		else return null; //key-duplication
		
	}
	

	public Node<E> search(Node<E> startNode, E k) {
		Node<E> p = startNode;
		if (p==null) return null ;
		else if (k.compareTo(p.key)<0) //compareTo메소드는 두개의 문자열을 비교하고 int형을 반환, a가 b보다 크면 양수 반대 음수 같으면 0반환
			return search(p.left, k);
		else if (k.compareTo(p.key)>0)
			return search(p.right, k);
		else //p의 key값이 k인경우 
			return p;
	}
	
	public Node<E> delete(E key) {
		Node<E> x = search(root, key);
		if(x==null) { //지우고자 하는 값이 노드 중에서 없을때 
			System.out.println(">> key "+key+" -Not Found : Delete failed.");
			return root;
		}
		if(x==root) { 
			Node<E> r2 = deleteNode(x);
			if(r2==null) {
				root=null; return root;
			}
			if(r2.key.compareTo(root.key)<0) {
				r2.right=root.right;
				root=r2;
			}
			else if(r2.key.compareTo(root.key)>0) {
				r2.left=root.left;
				root=r2;
			}
			else {
				System.out.println(">> Wrong Situation.");
			}
		}
		
		else if (x==x.parent.left) x.parent.left = deleteNode(x);
		else x.parent.right = deleteNode(x);
		return root;

	}
	
	private Node<E> deleteNode(Node<E> x){
		if(x.left==null && x.right==null) return null;
		else if(x.left==null && x.right!=null) return successor(x);
		else if(x.left!=null && x.right==null) return predecessor(x);
		else return successor(x); // predecessor도가능하다. 
		
		
	}

	private Node<E> successor(Node<E> v) { //successor는 오른쪽으로 갔다가 왼쪽 끝까지 찾아가는 과정 즉, 오른쪽 서브트리에서 가장 작은 값을 찾는다. 
		if (v==null || v.right==null)
			return null;
		Node<E> p = v.right;
		while (p.left!=null)
			p=p.left;
		return p;
	}
	private Node<E> predecessor(Node<E> v) { //predecessor는 왼쪽으로 갔다가 오른쪽 끝까지 찾아가는 과정 즉, 왼쪽 서브트리에서 가장 큰 값을 찾는다. 
		if (v==null || v.left==null)
			return null;
		Node<E> p = v.left;
		while (p.right!=null)
			p=p.right;
		return p;
	}
/////////////////////////////////////////////////////////////////////
	
	public String toString() {
		// inorder traverse
		return inorder(root);
		
	}
	public String toString(Node<E> v) {
		// for printing subtree
		return inorder(v);
	}
	private String inorder(Node<E> v) {
		if (v==null)
			return "";
		else 
			return inorder(v.left)+" "+v.key+" "+inorder(v.right);
		
	}
	private String preorder(Node<E> v) {
		if (v==null)
			return "";
		else
			return v.key+" "+preorder(v.left)+" "+preorder(v.right);
	}
	private String postorder(Node<E> v) {
		if (v==null)
			return "";
		else
			return postorder(v.left)+" "+postorder(v.right)+" "+v.key;
	}
/////////////////////////////////////////////////////////////////////
	
	public void rotateTest() {
		if(root!=null) {
			innerTest(root);
			innerTest(root);
			innerTest(root);
		}
	}
	private void innerTest(Node<E> v) {
		if(v.right!=null) rotateLeft(v);
		else if(v.left!=null) rotateRight(v);
	}
	private void rotateLeft(Node<E> v) {
		Node<E> u = v.right;
		u.parent = v.parent;
		if(u.parent==null) { //v의 부모가 없을경우, 
			root=u;
		}
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right=u;
		}
		v.parent=u;
		v.right=u.left;
		if(u.left!=null)
			u.left.parent=v;
		u.left=v;

	}

	private void rotateRight(Node<E> v) {
		Node<E> u = v.left;
		u.parent = v.parent;
		if(u.parent==null) {
			root=u;
		}
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right=u;
		}
		v.parent=u;
		v.left=u.right;
		if(u.right!=null)
			u.right.parent=v;
		u.right=v;

	}
////////////////////////////////////////////////////////////////////////
	public int height() {
		return evalMax(root,0);

	}
	private int evalMax(Node<E> p, int counter) {
		if(p==null) {
			return counter;
		}
		counter++;
		counter += Math.max(evalMax(p.left,0), evalMax(p.right, 0));
		return counter;
	}
///////////////////////////////////////////////////////////////////////
	
	public int IPL() {
		IPL2(root, 0);
		return sum;
	}
	public void IPL2(Node<E> root ,int ipltemp) { // Homework : 구현하시오 !

	    ipltemp = ipltemp + 1;
	    sum = sum + ipltemp;
	    
	    if(root.left!=null)
	    	IPL2(root.left, ipltemp);
	    if(root.right!=null)
	    	IPL2(root.right, ipltemp);
	    
		
	}
	
///////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		BinarySearchTree<Integer> mybst = new BinarySearchTree<>();
		for (int i=0; i<20; i++) {
//			mybst.insert((int)(100*Math.random()));
			mybst.insert(i);
			System.out.println(mybst.toString());
		}
		
		System.out.println(mybst.toString());
		System.out.println("Max. Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());	
		
		mybst.rotateTest();
		System.out.println(mybst.toString());
		mybst.sum=0;
		System.out.println("Max. Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());	
		
		mybst.delete(3);
		System.out.println(mybst.toString());
		mybst.sum=0;
		System.out.println("Max. Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());	
	}
}
