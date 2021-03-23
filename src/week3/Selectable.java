package week3;

public class Selectable {
	
	public static int selectRecursiveCount =0;
	
	public int select(int [] data, int p, int r, int i) {
		
		selectRecursiveCount++;  //재귀반복카운트 
		
		if(p>r) {  //입력 잘못됐을 경우 
			System.out.println("invalid range...");
			return -1;
		}
		if(p==r) return data[p]; // 배열의크기가 1인경우 
		
		
		int q= partition(data, p, r); 
		int k= q-p;
		if(i<k) return select(data, p, q-1, i);
		else if (i==k) return data[q];
		else return select(data, q+1, r, i+p-q-1);
		
	}
	
	private int partition(int[] data, int p, int r) {  //퀵소트때 썼던 partition과정 
		int pivot=r;
		int left=p;
		int right=r;
		
		while(left<right) {
			while(data[left]<data[pivot] && left<right) left++;
			while(data[right]>=data[pivot] && left<right) right--;
			if (left<right) swapData(data, left, right);
			
		}
		swapData(data, pivot, right);  //무조건 바꿈 
		
		return right;
	}
	
	private void swapData(int[] data, int i, int j) {  //교환 
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
		
		int pValue = median(data, p, r); //최종적으로 중간값의 중간값을 구하는 과정 
		int pivotIn = indexOf(data, p, r, pValue);  //위에서 구한 중간값의 중간값 index를 구하는 과정 
		swapData(data, pivotIn, r); //아래의 partition과정은 피봇이 가장 오른쪽에 있으므로 기준값과 피봇의 위치를바꾼다. 
		
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
		int arrSize= (int) Math.ceil(f/5); //Math.ceil은 올림값구하기 메서드이다. 
	    int [] medianArray = new int[arrSize];
	    
	    for(int i=0; i<arrSize; i++) {
	    	medianArray[i]=median5(data, p+5*i, (int)Math.min(p+5*i+4, r)); //Math.min을 하는 이유는 마지막 배열에서 5개가 되지 못했을 때 남은것끼리 묶기위해서, 
	    	                                                                //5개 묶음의 중간값을 medianArray배열에 추가 
	    }
		
					return median(medianArray, 0, arrSize-1); //재귀함수를 사용하고 중간값을 담은 medianArray배열이 5개 초과면 다시반복, 아닐시 median5메서드  
	}

	private int median5(int[] data, int p, int r) {  
		if(p==r) return data[p];
		sort5(data, p, r); //정렬 
		return data[p+(int)((r-p+1)/2)]; //중간값 반환 
	}

	private void sort5(int[] data, int p, int r) { //삽입 정렬 
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
		//int [] data = {4, 5, 1, 10, 3, 6, 7, 8, 12, 60, 40}; //정렬안된 경우 
		
		int [] data = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};  //정렬된 경우
		
		Selectable s = new Selectable();
		for(int i=0; i<data.length; i++)
		     System.out.print(s.select(data, 0, data.length-1, i)+"  ");
		System.out.println();
		System.out.println("# of Recursive Calls of Select = "+selectRecursiveCount);
		
		selectRecursiveCount=0; //변수 초기화 
		for(int i=0; i<data.length; i++)
		     System.out.print(s.linearSelect(data, 0, data.length-1, i)+"  ");
		System.out.println();
		System.out.println("# of Recursive Calls of LinearSelect = "+selectRecursiveCount);
	}

}
