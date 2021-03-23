package week5;

public class hashOpenAddrLinear {

	static int nOfHops =0;
	static double loadFactor;
	static double threshold = 0.99; // �Ѱ��� �߰��� �÷������� ������ �󸶳� ���������� �� �� �����Ƿ� 
	
	
	int [] table;
	int tableSize;
	int numberOfItems;
	
	public hashOpenAddrLinear(int n) {
		tableSize= n;
		table = new int[tableSize];
		for (int i=0; i<tableSize; i++)
			table[i]=-1; // -1 means null, -999=deleted ����ְų� deleted�� �ű⿡ �ְ� 
	}
	private int hashFunction(int d) { //chaining�̶� ���� 
	    // ������ �ƴ� ��쿡��  hashcode() ���� �ʿ�
		// ������ ���

			return (int)d%tableSize;
		
		// ���ϱ� ���
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
		int hashCode=hashFunction(d); // �ؽ� �ڵ� �� ��� 
		nOfHops =1;
		int a = hashFunc1(d);
		int b = hashFunc2(d);
		
		if (table[hashCode]==-1) { //-1�� null�̹Ƿ� ���� ��������� �ȴ�. 
			table[hashCode]=d;
			numberOfItems++;
			return nOfHops;
		}
		else {
			// 1. Linear Probing
			int probeIndex=(hashCode+1)%tableSize; // ���� ĭ�� ã�� ����
			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
				nOfHops++;
				probeIndex=(probeIndex+1)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			table[probeIndex]=d; // ã�� ������ ���� �ִ´�. 
			numberOfItems++;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops; 	
			// 2. Quadratic Probing
//			int probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
//			table[probeIndex]=d; // ã�� ������ ���� �ִ´�. 
//			numberOfItems++;
//			loadFactor =(double)(numberOfItems/tableSize);
//			return nOfHops; 
			// 3. Double Hashing table�� ũ�� 512�̹Ƿ� 512�� ����� �Ҽ� 509�� ����Ѵ�. . 
	
//			int  probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
//				nOfHops++;
//				probeIndex = ((a + (nOfHops*(b))) % tableSize);
//				
//				if (probeIndex==hashCode) {
//					return 0; // cannot happen..
//				}
//				
//			}
//			table[probeIndex]=d; // ã�� ������ ���� �ִ´�. 
//			numberOfItems++;
//			loadFactor =(double)(numberOfItems/tableSize);
//			return nOfHops; 
			
		}
	}
	private void enlargeTable() {  // ���̺� �ϳ� �� 2��� ���� old ���̺� �ִ� �� �ϳ� �ϳ� �о �� ���̺� �ٽ� �������
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
			int probeIndex=(hashCode+1)%tableSize; // 1�� �������Ѽ� ã�Ƴ�����. 
			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // ���� ã�ų� null�� �ƴϸ� ���� , deleted�� ã�� �ʿ� ���� 
				nOfHops++;
				probeIndex=(probeIndex+1)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			// 2. Quadratic Probing
//			int probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			// 3. Double Hashing table�� ũ�� 512�̹Ƿ� 512�� ����� �Ҽ� 509�� ����Ѵ�.  
//			int  probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
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
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
//				nOfHops++;
//				probeIndex=((hashCode+(nOfHops*nOfHops))%tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			// 3. Double Hashing table�� ũ�� 512�̹Ƿ� 512�� ����� �Ҽ� 509�� ����Ѵ�. 
//			int probeIndex = ((a + (nOfHops*(b))) % tableSize);
//			while(table[probeIndex]!=d && table[probeIndex]!=-1) { // ã�� ������ ���� ��ĭ�̰ų� deleted�� ��� while���� ���ͼ� �ִ´�. 
//				nOfHops++;
//				probeIndex = ((a + (nOfHops*(b))) % tableSize);
//				if (probeIndex==hashCode)
//					return 0; // cannot happen..
//			}
			if (table[probeIndex]==d) { // ã���� -999�� ����
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
