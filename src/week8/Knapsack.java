package week8;

public class Knapsack {
	
	int w[] = {20,100,60,130,100};
	int p[] = {30000, 200, 700, 2000, 3000};
	int [][] memo = new int[10000][10000];
	
	int count=0;
	int recursiveCount=0;

	public Knapsack() {
		
	}
	
	public void reset() {
		for(int i=0; i<10000; i++) {
			for(int j=0; j<10000; j++)
			memo[i][j]=0;
		}
	}
	
	public int dpUnboundedKS(int M, int i) {
	   count++;
		
		if(i==-1) {
			return 0;
		}
		if(M<0) {
			return 0;
		}

		if(memo[M][i]!=0) return memo[M][i];
		int temp1=0;
		if(M-w[i]>=0) {
			temp1=	(reUnboundedKS(M-w[i], i)+p[i]);
		}
		
		return memo[M][i]=Math.max(temp1, dpUnboundedKS(M, i-1));
	}
	
	public int reUnboundedKS(int M, int i) {
		recursiveCount++;
			if(i==-1) {
				return 0;
			}
			if(M<0) {
				return 0;
			}
			int temp1=0;
			if(M-w[i]>=0) {
			temp1=	(reUnboundedKS(M-w[i], i)+p[i]);
		}

			return Math.max(temp1, reUnboundedKS(M, i-1));
		}
	
	public static void main(String[] args) {
		
		Knapsack knapsack = new Knapsack();
		int dpResult = knapsack.dpUnboundedKS(150, knapsack.w.length-1);
		System.out.println("DP Unbounded Knapsack "+ dpResult + " Count :" + knapsack.count);
		knapsack.reset();
		
		int reResult = knapsack.reUnboundedKS(150, knapsack.w.length-1);	
		System.out.println("Recursive Unbounded Knapsack "+ reResult + " Count :" + knapsack.recursiveCount);
	}
}
