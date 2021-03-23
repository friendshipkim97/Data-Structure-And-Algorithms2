package week6;

public class dSetTreeRank {
		
		class Node{
			int data;
			Node parent;
			int rank;
			public Node(int i) {
				this.data=i;
				this.parent=null;
				rank=0;
			}
			
			public String toString() {
				return "data:"+data+" rank:"+rank;
			}
			
		}
		Node root;
		int count;
		public dSetTreeRank() {
			root=null;
		}
		
		public dSetTreeRank makeSet(int i) {
			Node newNode = new Node(i);
			dSetTreeRank newSet = new dSetTreeRank();
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
		
		public dSetTreeRank union(dSetTreeRank another) {
			if(this.root==null) return another;
			else if(another.root==null) return this;
			else {	
				if(this.root.rank>another.root.rank) {
					another.root.parent=this.root;
					return this;
				}
				else if(this.root.rank==another.root.rank) { // ��ĥ �� ������ ��ũ�� Ŀ���� ���� �� ������ ��ũ�� ������ ��� 
					this.root.rank++;
					another.root.parent=this.root;			
					Node x =this.root;
					while(x.parent!=null) {
						x.parent.rank++;
						x=x.parent;
					}
					return this;
				}
			    else if(this.root.rank<another.root.rank) {
			    	this.root.parent=another.root.parent;
			    	return another;
				
			    }
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
			dSetTreeRank me = new dSetTreeRank();
			dSetTreeRank s1 = me.makeSet(1);
			dSetTreeRank s2 = me.makeSet(2);
			dSetTreeRank s3 = me.makeSet(3);
			dSetTreeRank s4 = me.makeSet(4);
			dSetTreeRank s5 = me.makeSet(5);
			dSetTreeRank s6 = me.makeSet(6);
			dSetTreeRank s7 = me.makeSet(7);
			dSetTreeRank s8 = me.makeSet(8);
			
			s1.union(s2);		
			s3.union(s4);
			
			s1.union(s3);			

			s5.union(s6);		
			s7.union(s8);
			
			s5.union(s7);
			
			s1.union(s5);
			
			dSetTreeRank sA = me.makeSet(30);
			dSetTreeRank sB = me.makeSet(31);
			dSetTreeRank sC = me.makeSet(32);
			dSetTreeRank sD = me.makeSet(33);
			dSetTreeRank sE = me.makeSet(34);
			
			System.out.println(s8); // ��� 8���� ��Ʈ 1���� ã�ư���. ��Ʈ 1�� rank�� 3�̴�. 
			
			System.out.println("��� 8�� ���� ������ ��Ʈ��� :" + s8.findSet(s8.root));
			System.out.println("��� 2�� ���� ������ ��Ʈ��� :" + s2.findSet(s2.root));
			
			System.out.println(s8);
			
			sA.union(sB);
			sB.union(sC);
			sC.union(sD);
			sB.union(sE);

			System.out.println(sD);
			System.out.println(sE);
		}
	}

