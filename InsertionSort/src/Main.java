import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {

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
		
		for(int cur = 1; cur < array.length; cur++) {
			
			int key = Integer.parseInt(array[cur]);
			
			for(int pre = cur-1; pre >= 0; pre--) {
				
				if(Integer.parseInt(array[pre]) > key) {
					array[pre + 1] = array[pre];
					array[pre] = Integer.toString(key);
				}
			}
		}
	}

}
