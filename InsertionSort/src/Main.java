import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] array = {5, 4, 3, 56, 45, 34, 2, 32, 22, 21, 67, 56};
		
		insertionSort(array);
		
		System.out.println(Arrays.toString(array));
		

	}
	
	public static void insertionSort(int[] array) {
		
		for(int cur = 1; cur < array.length; cur++) {
			
			int key = array[cur];
			
			for(int pre = cur-1; pre >= 0; pre--) {
				
				if(array[pre] > key) {
					array[pre + 1] = array[pre];
					array[pre] = key;
				}
			}
		}
	}

}
