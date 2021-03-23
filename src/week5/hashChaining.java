package week5;

public class hashChaining {
	
	static int nOfHops =0; // 성능을 찾아나갈때 사용하는 변수 numberOfHops 몇번을 찾아나가야
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
	int numberOfItems;  // 적재되어 있는 key의 개수 
	
	public hashChaining(int n) {
		tableSize= n;
		numberOfItems=0;
		table = new HashNode[n];
		for (int i =0;i<tableSize;i++)  // 현재 table에 원소는 없으니 null값을 넣어준다.
			table[i]=null;
	}
	private int hashFunction(int d) { // key가 정수가 아니면 정수로 바꿔준다. 
    // 정수인 아닌 경우에는  hashcode() 정의 필요
		// 해시 함수에는 나누기 방법과 곱하기 방법 2가지가 있는 것
	// 나누기 방법
//		return (int)d%tableSize;
	// 곱하기 방법
		double temp = (double) (d*0.6180339887);
		int res = (int) Math.floor(temp);
		return res%tableSize;
	}

	public int hashInsert(int d) {
		int hashCode=hashFunction(d); // 값을 가져옴
		HashNode newNode = new HashNode(d);  // 현재는 중복 무시하고 넣는 과정
		newNode.next = table[hashCode]; // 현재 table[hashCode]는 null이거나 저장됐다면 마지막 저장된곳의 노드를 가리키고있다. 
		table[hashCode]=newNode; // newNode를 가리키게 한다.
		numberOfItems++; // 개수 추가
		loadFactor =(double)(numberOfItems/tableSize); // 적재율 계산, 하지만 chaining에서는 의미x
		nOfHops =1;
		return nOfHops; // nOfHops =1 always..
	}
	
	public int hashSearch(int d) {
		int hashCode=hashFunction(d); // 해쉬코드를 받아낸다. 
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
		return -nOfHops; // -nOfHops= 검색 실패시 조사 횟수, null이면  검색실패이므로 
	}

	public int hashDelete(int d) {
		int hashCode=hashFunction(d); // 해쉬코드값을 받아옴 
		HashNode p = table[hashCode]; // 삭제를 하기 위해선 2개의 포인터가 있어야 한다. single linked list에서는 그 다음 주소를 미리 알고있어야함.  
		HashNode q = p.next;
		
		nOfHops=1;
		if (p==null)
			return -nOfHops;
		else if(p.key==d) {
			nOfHops++;
			table[hashCode]=p.next;  // 삭제될 위치의 다음 주소를 가리키게 한다. table[hashCode]는 트리라면 루트, linked list라면 헤드이다. 
			numberOfItems--;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops;
		}
		while (q!=null) {  // 맨 앞이 key가 아닐 경우 linked list를 타고 안으로 들어가야 한다. 
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
		return -nOfHops; // -nOfHops= 검색 실패시 조사 횟수
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
