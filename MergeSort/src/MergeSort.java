import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String input = "";
		
		//읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data02.txt";
		
		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//읽어온 값을 배열로 만듦
		String[] array = input.split(",");
		
		//Sort 실행
		mergeSort(array, 0, array.length-1);
		
		//결과를 파일에 씀
		try(FileWriter fw = new FileWriter("result_MergeSort.txt")){
			fw.write(Arrays.toString(array));
		}
	}
	
	//배열을 반으로 쪼개고 반으로 쪼갠 배열 2개의 원소들을 오름차순으로 합한다.
	public static void mergeSort(String[] array, int start, int end) {
		int mid = (start + end) / 2;
		
		if(start < end) {
			mergeSort(array, start, mid);
			mergeSort(array, mid+1, end);
			merge(array, start, mid, end);
		}
	}
	
	public static void merge(String[] array, int start, int mid, int end) {
		
		int[] tmpArray = new int[end + 1];
		
		int tmpStart = start;
		int tmpMid = mid + 1;
		
		int count = start;
		
		//둘 중 하나가 끝에 도달할 때까지 크기를 비교하여 tmpArray에 오름차순으로 넣는다
		while((tmpStart <= mid) && (tmpMid <= end)) {
			//뒷 배열이 더 큰 경우(앞 배열이 tmp에 들어가야함)
			if(Integer.parseInt(array[tmpStart]) < Integer.parseInt(array[tmpMid])) {
				tmpArray[count] = Integer.parseInt(array[tmpStart]);
				count++;
				tmpStart++;
			}
			//앞 배열이 같거나 더 큰 경우 (뒷 배열이 tmp에 들어가야함)
			else {
				tmpArray[count] = Integer.parseInt(array[tmpMid]);
				count++;
				tmpMid++;
			}
		}
		
		//앞 배열이 남았을 경우 남은 앞 배열을 전부 tmpArray에 붙인다
		if(tmpStart <= mid) {
			for(int i = tmpStart; i <= mid; i++) {
				tmpArray[count] = Integer.parseInt(array[i]);
				count++;
			}
		}
		//뒷 배열이 남았을 경우 남은 뒷 배열을 전부 tmpArray에 붙인다
		else if(tmpMid <= end) {
			for(int i = tmpMid; i <= end; i++) {
				tmpArray[count] = Integer.parseInt(array[i]);
				count++;
			}
		}
		//딱맞을 경우
		else;
		
		//배열 옮기기
		for(int i = start; i <= end; i++) {
			array[i] = Integer.toString(tmpArray[i]);
		}
		
		//System.out.println("start : " + start + ", mid : " + mid + ", end : " + end + ", 배열 : " + Arrays.toString(array));
	}

}
