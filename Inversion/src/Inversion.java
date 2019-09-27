import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Inversion {
	
	static int[] sortedList;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String input = "";

		//읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data04_inversion.txt";

		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		//읽어온 값 배열로 만듦
		String[] array = input.split(",");
		
		//숫자들이 정렬될 리스트를 초기화
		sortedList = new int[array.length];

		//Sort 실행과 동시에 실행의 결과 출력
		System.out.println(inversion(array, 0, array.length-1));


	}

	private static int inversion(String[] array, int start, int end) {
		// TODO Auto-generated method stub
		
		if(array.length == 1) {
			return 0;
		}
		else {
			int mid = (start + end) / 2;
			
			return inversion(array, start, mid)
			+ inversion(array, mid+1, end)
			+ mergeInversion(array, start, mid, end);
		}
	}

	private static int divideInversion(String[] array, int i, int end) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int mergeInversion(String[] array, int start, int mid, int end) {
		// TODO Auto-generated method stub
		return 0;
	}

}
