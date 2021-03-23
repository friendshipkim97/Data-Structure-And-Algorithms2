package week5;

public class hashChaining {
	
	static int nOfHops =0; // ������ ã�Ƴ����� ����ϴ� ���� numberOfHops ����� ã�Ƴ�����
	static double loadFactor;	
	
	private class HashNode {
		int key;
		HashNode next;
		public HashNode(int i) {
			key = i;
			next=null;
		}
		public String toString() {
			String res = "";
			return res+" -> "+key;
		}
	}
		
	HashNode [] table;
	int tableSize;
	int numberOfItems;  // ����Ǿ� �ִ� key�� ���� 
	
	public hashChaining(int n) {
		tableSize= n;
		numberOfItems=0;
		table = new HashNode[n];
		for (int i =0;i<tableSize;i++)  // ���� table�� ���Ҵ� ������ null���� �־��ش�.
			table[i]=null;
	}
	private int hashFunction(int d) { // key�� ������ �ƴϸ� ������ �ٲ��ش�. 
    // ������ �ƴ� ��쿡��  hashcode() ���� �ʿ�
		// �ؽ� �Լ����� ������ ����� ���ϱ� ��� 2������ �ִ� ��
	// ������ ���
//		return (int)d%tableSize;
	// ���ϱ� ���
		double temp = (double) (d*0.6180339887);
		int res = (int) Math.floor(temp);
		return res%tableSize;
	}

	public int hashInsert(int d) {
		int hashCode=hashFunction(d); // ���� ������
		HashNode newNode = new HashNode(d);  // ����� �ߺ� �����ϰ� �ִ� ����
		newNode.next = table[hashCode]; // ���� table[hashCode]�� null�̰ų� ����ƴٸ� ������ ����Ȱ��� ��带 ����Ű���ִ�. 
		table[hashCode]=newNode; // newNode�� ����Ű�� �Ѵ�.
		numberOfItems++; // ���� �߰�
		loadFactor =(double)(numberOfItems/tableSize); // ������ ���, ������ chaining������ �ǹ�x
		nOfHops =1;
		return nOfHops; // nOfHops =1 always..
	}
	
	public int hashSearch(int d) {
		int hashCode=hashFunction(d); // �ؽ��ڵ带 �޾Ƴ���. 
		HashNode p = table[hashCode];
		nOfHops=1;
		while (p!=null) {
			if (p.key==d)
				return nOfHops;
			else {
				nOfHops++;
				p=p.next;
			}
		}
		return -nOfHops; // -nOfHops= �˻� ���н� ���� Ƚ��, null�̸�  �˻������̹Ƿ� 
	}

	public int hashDelete(int d) {
		int hashCode=hashFunction(d); // �ؽ��ڵ尪�� �޾ƿ� 
		HashNode p = table[hashCode]; // ������ �ϱ� ���ؼ� 2���� �����Ͱ� �־�� �Ѵ�. single linked list������ �� ���� �ּҸ� �̸� �˰��־����.  
		HashNode q = p.next;
		
		nOfHops=1;
		if (p==null)
			return -nOfHops;
		else if(p.key==d) {
			nOfHops++;
			table[hashCode]=p.next;  // ������ ��ġ�� ���� �ּҸ� ����Ű�� �Ѵ�. table[hashCode]�� Ʈ����� ��Ʈ, linked list��� ����̴�. 
			numberOfItems--;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops;
		}
		while (q!=null) {  // �� ���� key�� �ƴ� ��� linked list�� Ÿ�� ������ ���� �Ѵ�. 
			nOfHops++;
			if (q.key==d) {
				p.next = q.next;
				numberOfItems--;
				loadFactor =(double)(numberOfItems/tableSize);
				return nOfHops;
			}
			else {
				p=q;
				q=q.next;
			}
		}
		return -nOfHops; // -nOfHops= �˻� ���н� ���� Ƚ��
	}

	public void showTable() {
		System.out.println("<< Current Status of Table  >> ");
		for (int i=0; i<tableSize; i++) {
			HashNode p = table[i];
			if (p!=null) System.out.print("\n "+i+" : ");
			while (p!=null) {
			 System.out.print(p);
			 p=p.next;
			}

		}
	}
	public double loadfactor() {
		loadFactor = ((double)numberOfItems/tableSize);
		return loadFactor;
	}
		
}
