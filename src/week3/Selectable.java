package week3;

public class Selectable {
	
	public static int selectRecursiveCount =0;
	
	public int select(int [] data, int p, int r, int i) {
		
		selectRecursiveCount++;  //��͹ݺ�ī��Ʈ 
		
		if(p>r) {  //�Է� �߸����� ��� 
			System.out.println("invalid range...");
			return -1;
		}
		if(p==r) return data[p]; // �迭��ũ�Ⱑ 1�ΰ�� 
		
		
		int q= partition(data, p, r); 
		int k= q-p;
		if(i<k) return select(data, p, q-1, i);
		else if (i==k) return data[q];
		else return select(data, q+1, r, i+p-q-1);
		
	}
	
	private int partition(int[] data, int p, int r) {  //����Ʈ�� ��� partition���� 
		int pivot=r;
		int left=p;
		int right=r;
		
		while(left<right) {
			while(data[left]<data[pivot] && left<right) left++;
			while(data[right]>=data[pivot] && left<right) right--;
			if (left<right) swapData(data, left, right);
			
		}
		swapData(data, pivot, right);  //������ �ٲ� 
		
		return right;
	}
	
	private void swapData(int[] data, int i, int j) {  //��ȯ 
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	public int linearSelect(int[] data, int p, int r, int i) {
		
		selectRecursiveCount++;
		
		if(p>r) {
			System.out.println("invalid range...");
			return -1;
		}
		if(p==r) return data[p];
		
		int q = linearPartition(data, p, r);
		
		int k = q-p;
		if(i<k) return linearSelect(data, p, q-1, i);
		else if (i==k) return data[q];
		else return linearSelect(data, q+1, r, i+p-q-1);
		
	}
	

	private int linearPartition(int[] data, int p, int r) {
		
		int pValue = median(data, p, r); //���������� �߰����� �߰����� ���ϴ� ���� 
		int pivotIn = indexOf(data, p, r, pValue);  //������ ���� �߰����� �߰��� index�� ���ϴ� ���� 
		swapData(data, pivotIn, r); //�Ʒ��� partition������ �Ǻ��� ���� �����ʿ� �����Ƿ� ���ذ��� �Ǻ��� ��ġ���ٲ۴�. 
		
		int pivot=r;
		int left=p;
		int right=r;
		
		while(left<right) {
			while(data[left]<data[pivot] && left<right) left++;
			while(data[right]>=data[pivot] && left<right) right--;
			if (left<right) swapData(data, left, right);
			
		}
		swapData(data, pivot, right);
		
		return right;
	}

	private int indexOf(int[] data, int p, int r, int pValue) {  
		for(int i=p; i<=r; i++) {
			if(data[i]==pValue) return i;
		}
		return -1;
	}

	private int median(int[] data, int p, int r) {
		if(r-p+1<=5)
			return median5(data, p, r);
		float f=(r-p+1);
		int arrSize= (int) Math.ceil(f/5); //Math.ceil�� �ø������ϱ� �޼����̴�. 
	    int [] medianArray = new int[arrSize];
	    
	    for(int i=0; i<arrSize; i++) {
	    	medianArray[i]=median5(data, p+5*i, (int)Math.min(p+5*i+4, r)); //Math.min�� �ϴ� ������ ������ �迭���� 5���� ���� ������ �� �����ͳ��� �������ؼ�, 
	    	                                                                //5�� ������ �߰����� medianArray�迭�� �߰� 
	    }
		
					return median(medianArray, 0, arrSize-1); //����Լ��� ����ϰ� �߰����� ���� medianArray�迭�� 5�� �ʰ��� �ٽùݺ�, �ƴҽ� median5�޼���  
	}

	private int median5(int[] data, int p, int r) {  
		if(p==r) return data[p];
		sort5(data, p, r); //���� 
		return data[p+(int)((r-p+1)/2)]; //�߰��� ��ȯ 
	}

	private void sort5(int[] data, int p, int r) { //���� ���� 
		for(int i=p+1; i<=r; i++) {
			int j=p;
			
			while((data[j])<=data[i]&&(j<i)) j++;
			if(j<i) {
				int temp= data[i];
				for(int k=i-1; k<=j; k--) {
					data[k+1]=data[k];
					data[j]=temp;
				}
			}
		}
		
	}

	//========================================================
	
	public static void main(String[] args) {
		//int [] data = {4, 5, 1, 10, 3, 6, 7, 8, 12, 60, 40}; //���ľȵ� ��� 
		
		int [] data = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};  //���ĵ� ���
		
		Selectable s = new Selectable();
		for(int i=0; i<data.length; i++)
		     System.out.print(s.select(data, 0, data.length-1, i)+"  ");
		System.out.println();
		System.out.println("# of Recursive Calls of Select = "+selectRecursiveCount);
		
		selectRecursiveCount=0; //���� �ʱ�ȭ 
		for(int i=0; i<data.length; i++)
		     System.out.print(s.linearSelect(data, 0, data.length-1, i)+"  ");
		System.out.println();
		System.out.println("# of Recursive Calls of LinearSelect = "+selectRecursiveCount);
	}

}
