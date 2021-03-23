package week4;

public class RBSearchTree<E extends Comparable<E>>{
	private class Node<E>{
		E key;
		String color;
		Node<E> parent;
		Node<E> left;
		Node<E> right;
		
		
		public Node(E k) {
			key = k;
			color="R";
		}
		
		public String toString() {
			String returnval = "";
			return returnval+key; 
		}
	}
	
	Node<E> root;
	Node<E> nilNode;
	int sum;
	
	public RBSearchTree() {
		nilNode = new Node<>(null);
		nilNode.color = "B";
		root = nilNode;
		sum=0;
	}
	public String toString() {
		// inorder traverse
		return inorder(root);
		
	}
	public String toString(Node<E> v) {
		// for printing subtree
		return inorder(v);
	}
	private String inorder(Node<E> v) {
		if (v==nilNode)
			return "";
		else 
			return inorder(v.left)+" "+v.key+" "+"("+v.color+")"+inorder(v.right);
		
	}
	public void rotateTest() {
		if(root!=nilNode) {
			innerTest(root);
			innerTest(root);
			innerTest(root);
		}
	}
	private void innerTest(Node<E> v) {
		if(v.right!=nilNode) rotateLeft(v);
		else if(v.left!=nilNode) rotateRight(v);
	}
	
	public Node<E> insert(E x) {
		Node<E> newNode = new Node<>(x);
		newNode.left=nilNode;
		newNode.right=nilNode;
		
		Node<E> inserted = insertNode(root, newNode);
		
		if(inserted==null) {// not nilNode 
			System.out.print(">> Key-duplication : Insert failed");
		    return root;
		}
		
		insertFixup(inserted);  // Satisfy RedBlack conditions...
		
		return root;
			
	}

	private Node<E> insertNode(Node<E> p, Node<E> x){
		if(p==nilNode) { // the only case  : root
			root=x;
			x.parent=nilNode;
			return root;
		}
		if(x.key.compareTo(p.key)<0) {
			if(p.left==nilNode) {
				p.left=x; x.parent=p; return x;
			}
			else return insertNode(p.left, x);
		}
		else if(x.key.compareTo(p.key)>0){
			if(p.right==nilNode) {
				p.right=x; x.parent=p; return x;
			}
			else return insertNode(p.right, x);
		}
		else return null; //key-duplication
		
	}
	
	private void insertFixup(Node<E> x) { //fixup�ʿ����� Į�� �ǵ��� 
		if(x==root) {
			x.color="B";
			return;
		}
		if(x.parent.color=="B") return;
		else { //x.parent.color="R", ���� x.parent.parent("B")����
			if(x.parent==x.parent.parent.left) {  //x.parent.parent�� ���ʿ� �پ�������
				if(x.parent.parent.right.color=="R") { //siblig is "R"
					x.parent.parent.color="R";
					x.parent.parent.right.color="B";
					x.parent.color="B";
					insertFixup(x.parent.parent);  // x.parent.parent ���� �ٽ� ��������� üũ 
				}
				else { // sibling x.parent.parent.right.color="B"
					if(x==x.parent.right) {
						x=x.parent;
						rotateLeft(x);
					}
					//x == x.parent.left or from above;;
					x.parent.color="B";
					x.parent.parent.color="R";
					rotateRight(x.parent.parent);
				}
			}
			else { //x.parent==x.parent.parent.right x.parent.parent�� �����ʿ� �پ������� ������ ��Ī�̴�. 
				if(x.parent.parent.left.color=="R") { //sibling is "R"
					x.parent.parent.color="R";
					x.parent.parent.left.color="B";
					x.parent.color="B";
					insertFixup(x.parent.parent);
				}
				else { //sibling x.parent.parent.left.color="B"
					if(x==x.parent.left) {
						x=x.parent;
						rotateRight(x);
					}
					// x==x.parent.left or from above..
					x.parent.color="B";
					x.parent.parent.color="R";
					rotateLeft(x.parent.parent);
				}
			}
			
		}
	}
//	private void rotateLeft(Node<E> v) {
//		Node<E> u = v.right;
//		u.parent = v.parent;
//		if(u.parent==null) { //v�� �θ� �������, 
//			root=u;
//		}
//		else {
//			if(v==v.parent.left) v.parent.left=u;
//			else v.parent.right=u;
//		}
//		v.parent=u;
//		v.right=u.left;
//		if(u.left!=null)
//			u.left.parent=v;
//		u.left=v;
//
//	}
//
//	private void rotateRight(Node<E> v) {
//		Node<E> u = v.left;
//		u.parent = v.parent;
//		if(u.parent==null) {
//			root=u;
//		}
//		else {
//			if(v==v.parent.left) v.parent.left=u;
//			else v.parent.right=u;
//		}
//		v.parent=u;
//		v.left=u.right;
//		if(u.right!=null)
//			u.right.parent=v;
//		u.right=v;
//
//	}
	
	private void rotateLeft(Node<E> v) {
		Node<E> u = v.right;
		u.parent = v.parent;
		if(u.parent==nilNode) { //v�� �θ� �������, 
			root=u;
		}
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right=u;
		}
		v.parent=u;
		v.right=u.left;
		if(u.left!=nilNode)
			u.left.parent=v;
		u.left=v;

	}

	private void rotateRight(Node<E> v) {
		Node<E> u = v.left;
		u.parent = v.parent;
		if(u.parent==nilNode) {
			root=u;
		}
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right=u;
		}
		v.parent=u;
		v.left=u.right;
		if(u.right!=nilNode)
			u.right.parent=v;
		u.right=v;

	}

	////////////////////////
	public Node<E> search(Node<E> startNode, E k) {
		Node<E> p = startNode;
		if (p==nilNode) return null ;
		else if (k.compareTo(p.key)<0) //compareTo�޼ҵ�� �ΰ��� ���ڿ��� ���ϰ� int���� ��ȯ, a�� b���� ũ�� ��� �ݴ� ���� ������ 0��ȯ
			return search(p.left, k);
		else if (k.compareTo(p.key)>0)
			return search(p.right, k);
		else //p�� key���� k�ΰ�� 
			return p;
	}
	
	public Node<E> delete(E key) {
		Node<E> x = search(root, key);
		if(x==null) { //������� �ϴ� ���� ��� �߿��� ������ 
			System.out.println(">> key "+key+" -Not Found : Delete failed.");
			return root;
		}
		//���⼭���� �޶����� �κ�
		Node<E> m = deleteNode(x);
		if(m!=nilNode) {
			x.key=m.key;
		}
		// x�� ��¥ �����Ǿ�� �ϴ� ���
		deleteNFixup(m);
		
		return root;

	}

	private Node<E> deleteNode(Node<E> x){
		if(x.left==nilNode && x.right==nilNode) return x; //�ڱ� �ڽ��� ���� 
		else if(x.left==nilNode && x.right!=nilNode) return successor(x);
		else if(x.left!=nilNode && x.right==nilNode) return predecessor(x);
		else return successor(x); // predecessor�������ϴ�. 
		
		
	}
	private void deleteNFixup(RBSearchTree<E>.Node<E> m) {
		//m�� �ִ� �ϳ��� child�� ���� <= successor �Ǵ� predecessor �̹Ƿ�.
		
		if(m==nilNode) {
			System.out.println(">> Something wrong...");
			return;
		}
		if(m.left==nilNode && m.right==nilNode) {
			executeDelete(m);
			return;
		}
		if(m.left!=nilNode) { //�ڽ��� �׻� �������� �ǵ��� ����
			m.right=m.left;
			m.left=nilNode;
		}
		if(m.color=="R") {
			executeDelete(m);
			return;
		}
		//Now, m.color="B"
		if(m.right.color=="R") {
			m.right.color="B";
			executeDelete(m);
			return;
		}
		
		Node<E> x = executeDelete(m); //m & m.right�� ��� "B"
		fixupBlackNode(x);
		
	}
	
	private void fixupBlackNode(Node<E> x) {
		if(x==x.parent.left) {
			Node<E> p = x.parent;
			Node<E> s = p.right;
			Node<E> l = s.left;
			Node<E> r = s.right;
			if(p.color=="R" && l.color=="B" && r.color=="B") {
				p.color="B"; s.color="R"; return;
			}
			if(p.color=="R" && r.color=="R") {//s�� ������ 
			rotateLeft(p);
			p.color="B"; s.color="R"; r.color="B"; return;
			}
			if(p.color=="R" && l.color=="R" && r.color=="B") {
				rotateRight(s);
				l.color="B"; s.color="R"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="B" && l.color=="B" && r.color=="B") {
				s.color="B"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="B" && r.color=="R") {
				rotateLeft(p);
				r.color="B"; return;
			}
			if(p.color=="B" && s.color=="B" && l.color=="R" && r.color=="B") {
				rotateRight(s);
				l.color="B"; s.color="R"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="R" && l.color=="R" && r.color=="B") {
				rotateLeft(p);
				p.color="R"; s.color="B"; 
				fixupBlackNode(x); return;
			}
			
		}
		else { //x==x.parent.right
			Node<E> p = x.parent;
			Node<E> s = p.left;
			Node<E> l = s.left;
			Node<E> r = s.right;
			if(p.color=="R" && l.color=="B" && r.color=="B") {
				p.color="B"; s.color="R"; return;
			}
			if(p.color=="R" && r.color=="R") {//s�� ������ 
				rotateRight(p);
				p.color="B"; s.color="R"; r.color="B"; return;
				}
			if(p.color=="R" && l.color=="R" && r.color=="B") {
				rotateLeft(s);
				l.color="B"; s.color="R"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="B" && l.color=="B" && r.color=="B") {
				s.color="B"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="B" && r.color=="R") {
				rotateRight(p);
				r.color="B"; return;
			}
			if(p.color=="B" && s.color=="B" && r.color=="R") {
				rotateRight(p);
				r.color="B"; return;
			}
			if(p.color=="B" && s.color=="B" && l.color=="R" && r.color=="B") {
				rotateLeft(s);
				l.color="B"; s.color="R"; 
				fixupBlackNode(x); return;
			}
			if(p.color=="B" && s.color=="R" && l.color=="R" && r.color=="B") {
				rotateRight(p);
				p.color="R"; s.color="B"; 
				fixupBlackNode(x); return;
			}
		}
	}
	
	private Node<E> executeDelete(Node<E> m){
		//������ child��, �Ǵ� �� ����
		if(m==root) {
			root=nilNode;
			return nilNode;
		}
		if(m==m.parent.left) {
			if(m.right!=nilNode) {
				m.parent.left=m.right;
				m.right.parent=m.parent;
				return m.right;
			}
			else { //no child
				m.parent.left = nilNode;
				return nilNode;
			}
		}
		else { //m==m.parent.right
			if(m.right!=nilNode) {
				m.parent.right=m.right;
				m.right.parent=m.parent;
				return m.right;
			}
			else { //no child
				m.parent.right = nilNode;
				return nilNode;
			}
		}
	}

	private Node<E> successor(Node<E> v) { //successor�� ���������� ���ٰ� ���� ������ ã�ư��� ���� ��, ������ ����Ʈ������ ���� ���� ���� ã�´�. 
		if (v==nilNode || v.right==nilNode)
			return nilNode;
		Node<E> p = v.right;
		while (p.left!=nilNode)
			p=p.left;
		return p;
	}
	private Node<E> predecessor(Node<E> v) { //predecessor�� �������� ���ٰ� ������ ������ ã�ư��� ���� ��, ���� ����Ʈ������ ���� ū ���� ã�´�. 
		if (v==nilNode || v.left==nilNode)
			return nilNode;
		Node<E> p = v.left;
		while (p.right!=nilNode)
			p=p.right;
		return p;
	}
	
////////////////////////////////////////////////////////////////////////
public int height() {
return evalMax(root,0);

}
private int evalMax(Node<E> p, int counter) {
	if(p==nilNode) {
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
public void IPL2(Node<E> root ,int ipltemp) { // Homework : �����Ͻÿ� !

	ipltemp = ipltemp + 1;
	sum = sum + ipltemp;

	if(root.left!=nilNode)
		IPL2(root.left, ipltemp);
	if(root.right!=nilNode)
		IPL2(root.right, ipltemp);


}
///////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		RBSearchTree<Integer> mybst = new RBSearchTree<>();
		for (int i=0; i<20; i++) {
			//mybst.insert((int)(100*Math.random()));
			mybst.insert(i);
			System.out.println(mybst.toString());
		}

		System.out.println(mybst.toString());
		System.out.println("Max. Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());	

//		mybst.rotateTest();
//		System.out.println(mybst.toString());
//		mybst.sum=0;
//		System.out.println("Max. Height : "+mybst.height());
//		System.out.println("IPL : "+mybst.IPL());	

		mybst.delete(3);
		mybst.delete(7);
		mybst.delete(11);
		mybst.delete(18);
		System.out.println(mybst.toString());
		mybst.sum=0;
		System.out.println("Max. Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());	
	}

}
