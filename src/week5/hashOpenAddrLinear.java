package week5;

public class hashOpenAddrLinear {

	static int nOfHops =0;
	static double loadFactor;
	static double threshold = 0.99; // 한계점 중간에 늘려버리면 성능이 얼마나 나빠지는지 알 수 없으므로 
	
	
	int [] table;
	int tableSize;
	int numberOfItems;
	
	public hashOpenAddrLinear(int n) {
		tableSize= n;
		table = new int[tableSize];
		for (int i=0; i<tableSize; i++)
			table[i]=-1; // -1 means null, -999=deleted 비어있거나 deleted면 거기에 넣고 
	}
	private int hashFunction(int d) { //chaining이랑 같음 
	    // 정수인 아닌 경우에는  hashcode() 정의 필요
		// 나누기 방법

			return (int)d%tableSize;
		
		// 곱하기 방법
//		double temp = (double)d * 0.6180339887;
//		int res = (int) Math.floor(temp);
//		return res%tableSize;
		}
	
	public int hashFunc1(int k) {
		return (int)k%tableSize;
	}
	public int hashFunc2(int k) {
		return ((int)k%509);
	}

	public int hashInsert(int d) {
		loadFactor =((double)numberOfItems)/tableSize;
		if (loadFactor>=threshold) {
			enlargeTable();
		}
		int hashCode=hashFunction(d); // 해쉬 코드 값 계산 
		nOfHops =1;
		int a = hashFunc1(d);
		int b = hashFunc2(d);
		
		if (table[hashCode]==-1) { //-1은 null이므로 값을 집어넣으면 된다. 
			table[hashCode]=d;
			numberOfItems++;
			return nOfHops;
		}
		else {
			// 1. Linear Probing
			int probeIndex=(hashCode+1)%tableSize; // 다음 칸을 찾는 과정
			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
				nOfHops++;
				probeIndex=(probeIndex+1)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			table[probeIndex]=d; // 찾은 곳에다 값을 넣는다. 
			numberOfItems++;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops; 	
			// 2. Quadratic Probing
//			int probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
//			table[probeIndex]=d; // 찾은 곳에다 값을 넣는다. 
//			numberOfItems++;
//			loadFactor =(double)(numberOfItems/tableSize);
//			return nOfHops; 
			// 3. Double Hashing table의 크기 512이므로 512에 가까운 소수 509를 사용한다. . 
	
//			int  probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex = ((a + (nOfHops*(b))) % tableSize);
//				
//				if (probeIndex==hashCode) {
//					return 0; // cannot happen..
//				}
//				
//			}
//			table[probeIndex]=d; // 찾은 곳에다 값을 넣는다. 
//			numberOfItems++;
//			loadFactor =(double)(numberOfItems/tableSize);
//			return nOfHops; 
			
		}
	}
	private void enlargeTable() {  // 테이블 하나 더 2배로 만들어서 old 테이블에 있는 걸 하나 하나 읽어서 새 테이블에 다시 집어넣음
		int oldSize=tableSize;
		tableSize=2*tableSize;
		int [] tempTable = new int [tableSize];
		for (int i=0; i<tableSize;i++)
			tempTable[i]=-1;
		for (int i=0; i<oldSize; i++) // rehashing
			if (table[i]>0)
				tempTable[hashFunction(table[i])]= table[i];
		table =tempTable;
	}
		
	public int hashSearch(int d) {
		int hashCode=hashFunction(d);
		nOfHops =1;
		int a = hashFunc1(d);
		int b = hashFunc2(d);
		if (table[hashCode]==d) {
			return nOfHops;
		}
		else {
			// 1. Linear Probing
			int probeIndex=(hashCode+1)%tableSize; // 1씩 증가시켜서 찾아나간다. 
			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // 값을 찾거나 null이 아니면 나옴 , deleted는 찾을 필요 없음 
				nOfHops++;
				probeIndex=(probeIndex+1)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			// 2. Quadratic Probing
//			int probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			// 3. Double Hashing table의 크기 512이므로 512에 가까운 소수 509를 사용한다.  
//			int  probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex = ((a + (nOfHops*(b))) % tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			if (table[probeIndex]==d) return nOfHops; // success
			else return -nOfHops; 	// failure
		}
		}

	public int hashDelete(int d) {
		int hashCode=hashFunction(d);
		nOfHops =1;
		int a = hashFunc1(d);
		int b = hashFunc2(d);
		if (table[hashCode]==d) {
			numberOfItems--;
			table[hashCode]=-999;
			return nOfHops;
		}
		else {
			// 1. Linear Probing
			int probeIndex=(hashCode+1)%tableSize;
			while(table[probeIndex]!=d && table[probeIndex]!=-1) { 
				nOfHops++;
				probeIndex=(probeIndex+1)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			// 2. Quadratic Probing
//			int probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			// 3. Double Hashing table의 크기 512이므로 512에 가까운 소수 509를 사용한다. 
//			int probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // 찾아 나가는 데가 빈칸이거나 deleted인 경우 while문을 나와서 넣는다. 
//				nOfHops++;
//				probeIndex = ((a + (nOfHops*(b))) % tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			if (table[probeIndex]==d) { // 찾으면 -999로 셋팅
				table[hashCode]=-999;
				numberOfItems--;
				return nOfHops; // success
			}
			else return -nOfHops; 	// failure
		}
	}
	
	public void showTable() {
		System.out.println("Current Hash Table : ");
		for (int i = 0; i<tableSize; i++)
			System.out.print(table[i]+"  ");
		System.out.println();
	}
	public double loadfactor() {
		loadFactor = ((double)numberOfItems/tableSize);
		return loadFactor;
	}
		

}
