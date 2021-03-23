package week7;

public class MatrixChain {
	int maxN;
	int [] p;
	
	int count;
	int [][] memo;
	
	void reset() {
		count =0;
		for(int i=0; i<maxN+1; i++)
			for(int j=0; j<maxN+1; j++)
				memo[i][j]=-1;
	}
	int getCount() {
		return count;
	}
	
	public MatrixChain(int nOfMatrix, int [] dimension) {
		maxN = nOfMatrix;
		p = new int[maxN+1];
		p = dimension;
		memo = new int[maxN+1][maxN+1];
	}
	
	int sequentialMult(int i, int j) { // ������� ���ϴ� ��
		int retVal;
		if(i==j) return 0;
		else retVal =1;
		for(int k=0; k<=j; k++)
			retVal *= p[k]; // p[k]�� �״�� �� �����ָ� �ȴ�.
		return retVal;
	}
	
	int rMatrixChain(int i, int j) {
		count++;
		if(i==j) return 0;
		int min=99999999;
		for(int k=i; k<j; k++) { // i���� j���� 
			int q = rMatrixChain(i,k)+rMatrixChain(k+1,j)+p[i-1]*p[k]*p[j];
			if(q<min) min=q;
		}
		return min;
		
	}
	
	public int matrixChain1(int i, int j) {
		count++;
		if(i==j) {
			memo[i][j]=0;
			return memo[i][j];
		}
		int min = 99999999;
		for(int k=i;k<j;k++) {
			int q1, q2;
			if(memo[i][k]==-1) q1=matrixChain1(i,k); // �޸� ���� �ѹ��� ��������� �ʾ����� 
			else q1=memo[i][k];
			if(memo[k+1][j]==-1) q2=matrixChain1(k+1,j); // �޸� ���� �ѹ��� ��������� �ʾ����� 
			else q2=memo[k+1][j];
			int q=q1+q2+p[i-1]*p[k]*p[j];
			if(q<min) min=q;
			}
		return min;
	}
	
	public int matrixChain2(int i, int j) { // �밢���� ������ ������ �θ����� 
		count++;
		if(i==j) {
			memo[i][j]=0;
			return memo[i][j];
		}
		for(int k=i; k<=j; k++) {
			for(int l=1; l<=j-k; l++) {
				memo[k+1-i][k+l]=matrixChain1(k+l-1,k+1);
			}
			
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		int numOfMatrix = 15;
		int [] dimension = {2,3,4,3,5,3,4,5,3,2,4,6,5,4,3,4}; // numOfMatrix+1
		
		MatrixChain mm = new MatrixChain(numOfMatrix, dimension);
	    System.out.println(mm.p.length);
	    for(int i=1; i<=numOfMatrix; i++) {
	    System.out.println("Seq: "+mm.sequentialMult(1,i));
	    }
//		for(int i=1; i<=numOfMatrix; i++) {
//			mm.reset();
//			System.out.println("Seq: "+mm.sequentialMult(1,i));
//			mm.reset();
//			System.out.print("Recursion: "+mm.rMatrixChain(1,i)+"    Count="+mm.getCount());
//			
//			mm.reset();
//			System.out.println("===> DP1 : "+mm.matrixChain1(1,i)+"    Count="+mm.getCount());
//			mm.reset();
//			mm.matrixChain2(1, i);
//			System.out.println("===> DP2 : "+"  Count="+mm.getCount());
//		}
	}

}
