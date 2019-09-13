import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] array = {6, 7, 3, 2, 67, 34, 23, 77, 77, 87, 10, 21};
		
		mergeSort(array, 0, array.length-1);
		
		System.out.println(Arrays.toString(array));
	}
	
	public static void mergeSort(int[] array, int start, int end) {
		int mid = (start + end) / 2;
		
		if(start < end) {
			mergeSort(array, start, mid);
			mergeSort(array, mid+1, end);
			merge(array, start, mid, end);
		}
	}
	
	public static void merge(int[] array, int start, int mid, int end) {
		
		int[] tmpArray = new int[end + 1];
		
		int tmpStart = start;
		int tmpMid = mid + 1;
		
		int count = start;
		
		while((tmpStart <= mid) && (tmpMid <= end)) {
			//�� �迭�� �� ū ���
			if(array[tmpStart] > array[tmpMid]) {
				tmpArray[count] = array[tmpMid];
				count++;
				tmpMid++;
			}
			//�� �迭�� ���ų� �� ū ���
			else {
				tmpArray[count] = array[tmpStart];
				count++;
				tmpStart++;
			}
		}
		
		//�� �迭�� ������ ���
		if(tmpStart <= mid) {
			for(int i = tmpStart; i <= mid; i++) {
				tmpArray[count] = array[i];
				count++;
			}
		}
		//�� �迭�� ������ ���
		else if(tmpMid <= end) {
			for(int i = tmpMid; i <= end; i++) {
				tmpArray[count] = array[i];
				count++;
			}
		}
		//������ ���
		else;
		
		//�迭 �ű��
		for(int i = start; i <= end; i++) {
			array[i] = tmpArray[i];
		}
		
		//System.out.println("start : " + start + ", mid : " + mid + ", end : " + end + ", �迭 : " + Arrays.toString(array));
	}

}
