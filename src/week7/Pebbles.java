package week7;

public class Pebbles {
	
	int [][] table = {  // 임의의 값을 넣어서 테이블을 만들었다. 
			{0,6,7,12,-5,5,3,11,3,7,-2,5,4},
			{0,-8,10,14,9,7,13,8,5,6,1,3,9},
			{0,11,12,7,4,8,-2,9,4,-4,3,7,10} };
	
	
	int [][] pattern = { 
			{0,0,0},
			{2,3,0}, // 패턴1
			{1,3,4}, // 패턴2
			{1,2,0}, // 패턴3
			{2,0,0} }; // 패턴4
	
	int [][] memo = new int [5][20]; // 0은 안쓰려고 앞에 5로만듬 패턴1~4
	int count;
	
	void reset() {
		count = 0;
		for(int mi=0; mi<5; mi++)
			for(int mj=0; mj<20; mj++)
				memo[mi][mj]=-999;
	}
	
	int getCount() {
		return count;
	}
	
	int pebbleMemo(int i, int p) {  // i=열, p=패턴
		count++;
		if(i==1) {
			memo[p][i]=pebbleSum(i, p);
			return memo[p][i];
		}
		else {
			int max = -999;
			int k=0;
			while(k<3 && pattern[p][k]!=0) {
				int q = pattern[p][k];
				if(memo[q][i-1]<=-999) // reset할 때 -999가 되므로 
					memo[q][i-1]=pebbleMemo(i-1, q);
				max = Math.max(memo[q][i-1],max);
				k++;
			}
			memo[p][i]=max+pebbleSum(i, p);
			return memo[p][i];
		}
	}
	
	int pebble(int i, int p) { // i는 열 p는 패턴이다. 
		count++;
		if(i==1)
			return pebbleSum(i, p);
		else {
			int max=-999;
			int k=0;
			while(k<3 && pattern[p][k]!=0) { // 패턴의 열은 총 3개이므로 k<3, 열이 3개인 이유는 패턴이 될 수 있는 최대 경우의 수가 3가지이다. 
				int q = pattern[p][k]; // 패턴3이면 패턴 1, 2만 가능하므로 
				max = Math.max(pebble(i-1,q), max); // 최대값 구하기 , 여기서 재귀가 이루어짐 
				k++;
			}
			return max+pebbleSum(i,p);
		}
	}
	
	private int pebbleSum(int i, int p) {
		int retVal = 0;
		switch(p) {
		case 1: retVal = table[0][i]; // i열 0행
		break;
		case 2: retVal = table[1][i]; // i열 1행
		break;
		case 3: retVal = table[2][i]; // i열 2행
		break;
		case 4: retVal = table[0][i]+table[2][i]; // i열 0행 + i열 2행
		break;
		}
		return retVal;
	}
	
	public static void main(String[] args) {
		Pebbles myPeb = new Pebbles();
		
		for(int j=1; j<=4; j++) { // 패턴이 총 4가지이므로 
			for(int i=1; i<=12; i++) {
				myPeb.reset();
				System.out.print(">>> "+i+","+j+" : "
				       +myPeb.pebble(i, j)+",  Count = "+myPeb.getCount());
				myPeb.reset();
				System.out.println(">>> "+i+","+j+" : "
					       +myPeb.pebbleMemo(i, j)+",  Count = "+myPeb.getCount());
				       
			}
		}
	}

}
