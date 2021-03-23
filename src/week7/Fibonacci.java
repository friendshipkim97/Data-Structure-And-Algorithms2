package week7;

public class Fibonacci {
	
	int [] fibo = new int[50];
	int counter;
	
	public void reset() {
		for(int i=0; i<50; i++)
			fibo[i]=0;
		counter=0;
	}
	
	int getCounter() {
		return counter;
	}
	
	int fiboRecursive(int n) {
		counter++;
		if(n<=2)
			return 1;
		else
			return fiboRecursive(n-1)+fiboRecursive(n-2);
	}
	
	int fiboIter(int n) {  // �⺻���� �ݺ� 
		fibo[1]=1; fibo[2]=1;
		for(int i=3; i<=n; i++) {
			fibo[i]=fibo[i-1]+fibo[i-2];
		}
		return fibo[n];
	}
	
	int fiboMemo(int n) { // �ѹ� ���Ȱ� ���̻� ��� x
		counter++;
		if(n<=2) 
			return 1;
		else if(fibo[n]>0) // fibo[n]�� 0���� ũ�ٴ� �� ���� ���� �ִ���?
			return fibo[n];
		else {
			fibo[n-1]=fiboMemo(n-1);
			fibo[n-2]=fiboMemo(n-2);
			return fibo[n-1]+fibo[n-2];
		}
		
	}

	public static void main(String[] args) {
		Fibonacci f = new Fibonacci();
		
		for(int n=3; n<30; n++) {
			f.reset();
			System.out.println("Value= "+f.fiboRecursive(n)+"  "+"Count= "+f.getCounter());
			f.reset();
			f.fiboMemo(n);
			System.out.println("  ==> Memoization Counts= "+f.getCounter());
		}

	}

}
