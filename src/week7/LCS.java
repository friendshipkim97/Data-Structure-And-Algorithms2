package week7;

public class LCS {
	
	int count;
	int maxN;
	
	String a ="acd";
	String b ="ace";
	
	int [][] memo;
	
	public LCS(int maxN) {
		this.maxN=maxN+1;
		
		this.memo = new int[this.maxN][this.maxN];
		
	}
	
	public void reset() {
		count =0;
		for(int i=0; i<this.maxN; i++)
			for(int j=0; j<this.maxN; j++)
				this.memo[i][j]=-1;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getMemo(int tempM, int tempN) {
		if(tempM==-1 || tempN==-1)
			return -1;
		else if(this.memo[tempM][tempN]==-1)
			return -1;
		else
			return this.memo[tempM][tempN];
		
	}
	
	public void setMemo(int tempM, int tempN, int tempC) {
		if(tempM==-1 || tempN==-1)
			return ;
		else
			this.memo[tempM][tempN] = tempC;
	}
	
	public int LCS(int m, int n) {
		count++;
		if(m==-1 || n==-1)
			return 0;
		else if(a.charAt(m)==b.charAt(n))
			return LCS(m-1, n-1)+1; // character 비교했을 때 같으므로 +1 해주는 것 
		else
			return Math.max(LCS(m-1, n), LCS(m, n-1));
	}
	
	public int LCSDP(int m, int n) {
		count++;
		if(m==-1 || n==-1)
			return 0;
		else if(a.charAt(m)==b.charAt(n)) {
			if(getMemo(m-1, n-1)==-1) {
			int tempC = LCSDP(m-1, n-1);
			setMemo(m-1, n-1, tempC);
			return tempC+1; // character 비교했을 때 같으므로 +1 해주는 것 
			}
			else
				return this.memo[m-1][n-1]+1;
		}
		else {
			int tempA, tempB;
			if(getMemo(m-1, n)==-1) {
				tempA=LCSDP(m-1, n);
			setMemo(m-1, n, tempA);
			}
			else tempA=this.memo[m-1][n];
			if(getMemo(m, n-1)==-1) {
				tempB=LCSDP(m, n-1);
			setMemo(m, n-1, tempB);
			}
			else tempB=this.memo[m][n-1];
			return Math.max(tempA, tempB);
			
		}
	}
	
	
	public static void main(String args[]) {
		LCS lcs = new LCS(2);
				
		System.out.println("Recursive Result : "+ lcs.LCS(2,2) + " Recursive Count : "+lcs.getCount());
		
		lcs.reset();
		
		System.out.println("RecursiveDP Result : "+ lcs.LCSDP(2,2) + " RecursiveDP Count : "+lcs.getCount());
	}

}
