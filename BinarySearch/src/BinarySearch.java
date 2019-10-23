import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BinarySearch {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner scanner = new Scanner(System.in);
		
		String input = "";

		//읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data07_a.txt";

		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		//읽어온 값 배열로 만듦
		String[] array = input.split(", ");
		
		System.out.print("찾고자 하는 값을 입력하시오 : ");
		int count = scanner.nextInt();
		
		int result = binarySearch(array,count);
		
		if(result == -1) {
			System.out.println("값을 찾지 못했습니다");
		}
		else {
			System.out.println("값을 찾았습니다 : " + result);
		}

	}

	private static int binarySearch(String[] array, int count) {
		//배열의 중간 인덱스(중간 값을 구함)
		int start = 0;
		int mid;
		int end = array.length;
		
		//end의 시작이 가장 마지막 인덱스의 수가 아니라 전체 길이의 수이므로
		//start는 항상 end보다 작을 수 밖에 없다
		while(start < end) {
			//중간값
			mid = (start + end) / 2;
			
			//만약 같을 경우 그대로 return
			if(Integer.parseInt(array[mid]) == count) {
				return Integer.parseInt(array[mid]);
			}
			//만약 count의 값이 작을 경우
			else if(Integer.parseInt(array[mid]) > count) {
				//작은 쪽으로 즉 start에서 mid 사이로 범위가 바뀌게 됨
				//mid의 값은 아니므로 1을 빼준다
				end = mid - 1; 
			}
			else {
				//count의 값이 클 경우
				//큰 쪽으로 즉 mid에서 end사이로 범위가 바뀌 됨
				//이때 mid의 값은 아니므로 1을 더해준다
				start = mid + 1;
			}
		}
		
		//만약 마지막까지 찾지 못했을 경우에는 -1을 출력한다
		return -1;
	}

}
