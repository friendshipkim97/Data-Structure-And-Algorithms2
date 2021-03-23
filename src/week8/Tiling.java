package week8;

public class Tiling {
	
	int [] tiling = new int[100];
	int count=0;
	int recursiveCount=0;
	
	public void reset() {
		this.count=0;
		this.recursiveCount=0;
		for(int i=0; i<100; i++) {
			tiling[i]=0;
		}
	}
	
	public int resursiveTiling1(int n) {
		recursiveCount++;
		if(n==1) return 1;
		if(n==2) return 2;
		
		return resursiveTiling1(n-1)+resursiveTiling1(n-2);
		
	}
	
	public int dpTiling1(int n) {
		count++;
		if(n==1) return 1;
		if(n==2) return 2;
		
		if(tiling[n] != 0) return tiling[n];
		
		return tiling[n]=dpTiling1(n-1)+dpTiling1(n-2);
		
	}
	
	public int resursiveTiling2(int n) { 
		recursiveCount++;
		if(n==1) return 1;
		if(n==2) return 3;
		
		return resursiveTiling2(n-1)+2*resursiveTiling2(n-2);
		
	}
	
	public int dpTiling2(int n) { 
		count++;
		if(n==1) return 1;
		if(n==2) return 3;
		
		if(tiling[n] != 0) return tiling[n];

		return tiling[n]=dpTiling2(n-1)+2*dpTiling2(n-2);

	}
	
	public int recursiveTiling3(int n) { 
		recursiveCount++;
		if(n%2==1)
             return recursiveTiling3(n-1);
		if(n==0) return 1;
		if(n==1) return 0;
		if(n==2) return 3;

		int result = 0;
		if(n>=4) {
		for(int i=4; i<=n; i=i+2) {
			result = result+2*recursiveTiling3(n-i);
		}
		}
		return 3*recursiveTiling3(n-2)+result;

	}
	
	public int dpTiling3(int n) { 
		count++;
		if(tiling[n] != 0) return tiling[n];
		if(n%2==1) 
             return tiling[n]=dpTiling3(n-1);
		
		if(n==0) return 1;
		if(n==1) return 0;
		if(n==2) return 3;
		
		int result = 0;
		if(n>=4) {
		for(int i=4; i<=n; i=i+2) {
			result = result+2*dpTiling3(n-i);
		}
		}
		return tiling[n]=3*dpTiling3(n-2)+result;

	}
	
	public int recursiveTiling4(int n) { 
		recursiveCount++;
		if(n==0) return 1;
		if(n==1) return 2;
		if(n==2) return 7;

		int result = 0;
		if(n>=3) {
		for(int i=3; i<=n; i++) {
			result = result+2*recursiveTiling4(n-i);
		}
		}
		return 2*recursiveTiling4(n-1)+3*recursiveTiling4(n-2)+result;

	}
	
	public int dpTiling4(int n) { 
        count++;
		if(n==0) return 1;
		if(n==1) return 2;
		if(n==2) return 7;

		if(tiling[n] != 0) return tiling[n];
		
		int result = 0;
		if(n>=3) {
		for(int i=3; i<=n; i++) {
			result = result+2*dpTiling4(n-i);
		}
		}
		return tiling[n] = 2*dpTiling4(n-1)+3*dpTiling4(n-2)+result;

	}
	
	public int getCount() {
		return count;
	}
	public int getRecursiveCount() {
		return recursiveCount;
	}
	
	public static void main(String[] args) {
		Tiling tiling = new Tiling();
		tiling.reset();
		System.out.println("2*N면적, 크기 1*2타일을 놓는 방법의 수 >>> "+"N : 5 "+ "경우의 수 : "+tiling.dpTiling1(5)+"가지" + "  DP Count : "+tiling.getCount());
		System.out.println("2*N면적, 크기 1*2타일을 놓는 방법의 수 >>> "+"N : 5 "+ "경우의 수 : "+tiling.resursiveTiling1(5)+"가지" + "  Recursive Count : "+tiling.getRecursiveCount());
		System.out.println();
		
		tiling.reset();
		System.out.println("2*N면적, 크기 1*2타일, 2*2타일을 놓는 방법의 수 >>> "+"N : 7 "+ "경우의 수 : "+tiling.dpTiling2(7)+"가지" + " DP Count : "+tiling.getCount());
		System.out.println("2*N면적, 크기 1*2타일, 2*2타일을 놓는 방법의 수 >>> "+"N : 7 "+ "경우의 수 : "+tiling.resursiveTiling2(7)+"가지" + " Recursive Count : "+tiling.getRecursiveCount());
		System.out.println();
		
		tiling.reset();
		System.out.println("3*N면적, 크기 1*2타일을 놓는 방법의 수 >>>"+"N : 10 "+ "경우의 수 : "+
		tiling.dpTiling3(10)+"가지" + " DP Count : "+tiling.getCount());
		System.out.println("3*N면적, 크기 1*2타일을 놓는 방법의 수 >>>"+"N : 10 "+ "경우의 수 : "+
		tiling.recursiveTiling3(10)+"가지" + " Recursive Count : "+tiling.getRecursiveCount());
		System.out.println();
		
		tiling.reset();
		System.out.println("2*N면적, 크기 1*1타일, 1*2타일을 놓는 방법의 수 >>>"+"N : 6 "+ "경우의 수 : "+
		tiling.dpTiling4(6)+"가지" + " DP Count : "+tiling.getCount());
		System.out.println("2*N면적, 크기 1*1타일, 1*2타일을 놓는 방법의 수 >>>"+"N : 6 "+ "경우의 수 : "+
		tiling.recursiveTiling4(6)+"가지" + " Recursive Count : "+tiling.getRecursiveCount());
	}
}
