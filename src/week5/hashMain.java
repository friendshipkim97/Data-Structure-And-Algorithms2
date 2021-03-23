package week5;

import java.util.HashSet;
public class hashMain {

	static int dataSize = 400;
	static int chainingTableSize = 512;
	static int openTableSize = 523;
	static int interval = 50;
	static int maxKeyValue = 100000;

	

	public static void main(String[] args) {
		int [] data = new int[dataSize];
		HashSet<Integer> rdata = new HashSet<Integer>(); // �ߺ����� �������� HashSet���, �ߺ��� �������ִ� Ŭ���� 
		
		while (rdata.size()<dataSize) {
			rdata.add((int)(Math.random()*maxKeyValue));
		}
		int k=0;
		for (int d : rdata) { // �״�� ����, chaining�� open addressing���� ����Ϸ��� 
			data[k]=d; k++;
		}
		
		int sumOfInvestSuccess=0; // ã�Ƽ� �������� ���� ������ ��
		int sumOfInvestFailure=0; // �������� �� �˻��� ����ߴ��� Ƚ��
		int delBegin = dataSize/3; // delete begin �߰��� �׽�Ʈ�ϱ� ����
		int delEnd = 2* dataSize/3; // delete end
		
		System.out.println(">>> Chaining");
		hashChaining myHash = new hashChaining(chainingTableSize);
		int repeat = dataSize/interval;
		for (int j=0; j<repeat; j++) { // 0~49���� 50���� 99����...
 			int start = j*interval;
			int end =(j+1)*interval;
			sumOfInvestSuccess=0;
			sumOfInvestFailure=0;
			int maxCount=0;
			for (int i=start; i<end; i++) {
				int count= myHash.hashInsert(data[i]); // ������� �μ�Ʈ�Ѱ�����
			}
			for (int i=start; i<end; i++) {
				int count= myHash.hashSearch(data[i]); 
				if (count>=0) {
					sumOfInvestSuccess += count;
					if (count>maxCount) maxCount = count;
				}
				else sumOfInvestFailure += count;
			}
			System.out.print(" Number of investigation : Success ( ~ "+ (j+1)+" * "+interval +") = "
			    +sumOfInvestSuccess +"  Max. Hop Count = "+maxCount); // Ư���� ����� ��찡 �ִ��� Ȯ���Ϸ��� Max Hop Count ���
			System.out.println("  Load Factor = "+myHash.loadfactor()
			    +"  Average Hop Count = "+((double)sumOfInvestSuccess/interval));
		}
		sumOfInvestSuccess=0;
		sumOfInvestFailure=0;
		int successCount=0, failCount=0;
		for (int j=0; j<dataSize; j++) { // ������ ��� üũ 
			int count= myHash.hashSearch((int)(Math.random()*maxKeyValue));
			if (count>=0) {
				sumOfInvestSuccess += count;
				successCount++;
			}
			else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}
		System.out.println("\n Average number of investigation : Success = "+sumOfInvestSuccess
				+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfInvestSuccess/successCount)
				+"  Failure = "+(-sumOfInvestFailure)+"("+failCount+")"
				+"  Average Hop Count = "+((double)-sumOfInvestFailure/failCount));

			for (int i=delBegin; i<delEnd; i++) { // 3����1�� �����Ϸ��� 
				myHash.hashDelete(data[i]);
			}
			System.out.println("\n  < After Deleting 1/3 keys...> ");

			sumOfInvestSuccess=0;
			sumOfInvestFailure=0;
			successCount=0;
			failCount=0;
			for (int j=0; j<dataSize; j++) {
				int count= myHash.hashSearch((int)(Math.random()*maxKeyValue));
				if (count>=0) {
					sumOfInvestSuccess += count;
					successCount++;
				}
				else {
					sumOfInvestFailure += count;
					failCount++;
				}
			}
			System.out.println("\n Average number of investigation : Success = "+sumOfInvestSuccess
					+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfInvestSuccess/successCount)
					+"  Failure = "+(-sumOfInvestFailure)+"("+failCount+")"
					+"  Average Hop Count = "+((double)-sumOfInvestFailure/failCount));
			
/////////////////////////////////////////////////////////////////////////////////////////////////////			
			System.out.println("\n\n>>> Open-Addressing : Linear Probing ");
		//	System.out.println("\n\n>>> Open-Addressing : Quadratic Probing ");
//			System.out.println("\n\n>>> Open-Addressing : Double Hashing ");
			hashOpenAddrLinear myHash2 = new hashOpenAddrLinear(chainingTableSize);
			for (int j=0; j<repeat; j++) { //repeat =10
				int start = j*interval;
				int end =(j+1)*interval;
				sumOfInvestSuccess=0;
				sumOfInvestFailure=0;
				double maxCount=0;
				for (int i=start; i<end; i++) {
					int count= myHash2.hashInsert(data[i]); // ó���� 0���� 49���� �ε����� data�� ����
			
				}
				for (int i=start; i<end; i++) {
					double count= myHash2.hashSearch(data[i]);
					if (count>=0) {
						sumOfInvestSuccess += count;  //�˻��� �������� �� �˻�����ī��Ʈ��
						if (count>maxCount) maxCount = count;
					}
					else sumOfInvestFailure += count;
				}
				System.out.print(" Number of investigation : Success ( ~ "+ (j+1)+" * "+interval +") = "
				    +sumOfInvestSuccess +"  Max. Hop Count = "+maxCount); // max hop count�� 1�� ������ �ǹ��Ѵ�.
				System.out.println("  Load Factor = "+myHash2.loadfactor()
			    +"  Average Hop Count = "+((double)sumOfInvestSuccess/interval));			
			}
				sumOfInvestSuccess=0;
				sumOfInvestFailure=0;
				successCount=0;
				failCount=0;
				for (int j=0; j<dataSize; j++) {
					double count= myHash2.hashSearch((int)(Math.random()*maxKeyValue));
					if (count>=0) {
						sumOfInvestSuccess += count;
						successCount++;
					}
					else {
						sumOfInvestFailure += count;
						failCount++;
					} 
				}

			System.out.println("\n Average number of investigation : Success = "+sumOfInvestSuccess
					+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfInvestSuccess/successCount)
					+"  Failure = "+(-sumOfInvestFailure)+"("+failCount+")"
					+"  Average Hop Count = "+((double)-sumOfInvestFailure/failCount));

			for (int i=delBegin; i<delEnd; i++) {
				myHash2.hashDelete(data[i]); // 500/3���� 2*500/3���� �ڸ� 
			}
			System.out.println("\n  < After Deleting 200 keys...> ");
			sumOfInvestSuccess=0;
			sumOfInvestFailure=0;
			successCount=0;
			failCount=0;
			for (int j=0; j<dataSize; j++) {
				double count= myHash2.hashSearch((int)(Math.random()*maxKeyValue));
				if (count>=0) {
					sumOfInvestSuccess += count;
					successCount++;
				}
				else {
					sumOfInvestFailure += count;
					failCount++;
				}
			}

			System.out.println("\n Average number of investigation : Success = "+sumOfInvestSuccess
					+"("+successCount+")"+"  Average Hop Count = "+((double)sumOfInvestSuccess/successCount)
					+"  Failure = "+(-sumOfInvestFailure)+"("+failCount+")"
					+"  Average Hop Count = "+((double)-sumOfInvestFailure/failCount));

			
		}

	}
