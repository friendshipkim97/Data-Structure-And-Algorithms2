package week7;

public class MatrixPath {
	
	int [] p;
	
	int count;
	
	int [][] table = {  // 임의의 값을 넣어서 테이블을 만들었다. 
			{6,7,12,5,5},
			{8,10,14,9,10},
			{11,12,7,4,8},
			{3,10,7,5,8},
			{8,3,1,4,20}};
	
	int [][] table2 = {  // 임의의 값을 넣어서 테이블을 만들었다. 
			{6,7},
			{8,10},
			};
	
	
	public MatrixPath() {
	}
	
	void reset() {
		count =0;
	}
	
	int getCount() {
		return count;
	}
	
	public int matrixPath(int i, int j) {  // Recursive
		count++;
		if(i==-1 || j==-1)
			return 0;
		else
			return table[i][j]+Math.max(matrixPath(i-1, j), matrixPath(i, j-1));
	}
	
	public int matrixPathDP(int i, int j) {
		count++;
		if(i==-1 || j==-1)
			return 0;
		else if(i==0)
			return table[0][j]+matrixPathDP(0, j-1);
		else if(j==0)
			return table[i][0]+matrixPathDP(i-1, 0);
		else
			return table[i][j]+Math.max(matrixPathDP(i-1, j), matrixPathDP(i, j-1));
	}
	
	
	public static void main(String args[]) {
		
		MatrixPath mp = new MatrixPath();
		System.out.println("Recursive Result : "+mp.matrixPath(4,4)+ " Recursive Count : "+mp.getCount());
		mp.reset();
		System.out.println("RecursiveDP Result : "+mp.matrixPathDP(4,4)+ " RecursiveDP Count : "+mp.getCount());
	}

}
