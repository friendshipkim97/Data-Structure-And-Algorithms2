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
		HashSet<Integer> rdata = new HashSet<Integer>(); // 중복되지 않으려고 HashSet사용, 중복을 제거해주는 클래스 
		
		while (rdata.size()<dataSize) {
			rdata.add((int)(Math.random()*maxKeyValue));
		}
		int k=0;
		for (int d : rdata) { // 그대로 저장, chaining과 open addressing에서 사용하려고 
			data[k]=d; k++;
		}
		
		int sumOfInvestSuccess=0; // 찾아서 성공했을 때의 개수의 합
		int sumOfInvestFailure=0; // 실패했을 때 검색을 몇번했는지 횟수
		int delBegin = dataSize/3; // delete begin 중간에 테스트하기 위함
		int delEnd = 2* dataSize/3; // delete end
		
		System.out.println(">>> Chaining");
		hashChaining myHash = new hashChaining(chainingTableSize);
		int repeat = dataSize/interval;
		for (int j=0; j<repeat; j++) { // 0~49까지 50부터 99까지...
 			int start = j*interval;
			int end =(j+1)*interval;
			sumOfInvestSuccess=0;
			sumOfInvestFailure=0;
			int maxCount=0;
			for (int i=start; i<end; i++) {
				int count= myHash.hashInsert(data[i]); // 몇번만에 인서트한것인지
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
			    +sumOfInvestSuccess +"  Max. Hop Count = "+maxCount); // 특별히 길어진 경우가 있는지 확인하려고 Max Hop Count 사용
			System.out.println("  Load Factor = "+myHash.loadfactor()
			    +"  Average Hop Count = "+((double)sumOfInvestSuccess/interval));
		}
		sumOfInvestSuccess=0;
		sumOfInvestFailure=0;
		int successCount=0, failCount=0;
		for (int j=0; j<dataSize; j++) { // 실패한 경우 체크 
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

			for (int i=delBegin; i<delEnd; i++) { // 3분의1을 수행하려고 
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
					int count= myHash2.hashInsert(data[i]); // 처음엔 0부터 49까지 인덱스의 data를 대입
			
				}
				for (int i=start; i<end; i++) {
					double count= myHash2.hashSearch(data[i]);
					if (count>=0) {
						sumOfInvestSuccess += count;  //검색에 성공했을 때 검색성공카운트합
						if (count>maxCount) maxCount = count;
					}
					else sumOfInvestFailure += count;
				}
				System.out.print(" Number of investigation : Success ( ~ "+ (j+1)+" * "+interval +") = "
				    +sumOfInvestSuccess +"  Max. Hop Count = "+maxCount); // max hop count는 1차 군집을 의미한다.
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
				myHash2.hashDelete(data[i]); // 500/3부터 2*500/3까지 자름 
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
