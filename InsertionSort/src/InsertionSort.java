import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class InsertionSort {

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
		
		//읽어온 값 배열로 만듦
		String[] array = input.split(",");
		
		//Sort 실행
		insertionSort(array);
		
		//파일에 씀
		try(FileWriter fw = new FileWriter("result_InsertionSort.txt")){
			fw.write(Arrays.toString(array));
		}
				
	}
	
	public static void insertionSort(String[] array) {
		
		//두번 쨰 원소부터 반복문을 돈다
		for(int cur = 1; cur < array.length; cur++) {
			
			int key = Integer.parseInt(array[cur]);
			
			//현재 cur의 원소 이전원소를 전부 돌면서 적절한 위치를 찾는다
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur의 원소보다 큰 경우 위치를 변경한다
				if(Integer.parseInt(array[pre]) > key) {
					array[pre + 1] = array[pre];
					array[pre] = Integer.toString(key);
				}
			}
		}
	}

}
