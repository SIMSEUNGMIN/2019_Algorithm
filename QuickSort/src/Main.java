import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws IOException {

		String input = "";

		//읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data06.txt";

		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		//읽어온 값 배열로 만듦
		String[] array = input.split(",");
		//복사해서 Random에 넣을 배열을 만듦
		String[] arrayRandom = new String[array.length];

		System.arraycopy(array, 0, arrayRandom, 0, array.length);

		//QuickSort 실행
		quickSort(array, 0, array.length-1);

		//QuickRandomSort 실행
		quickSort_withRandom(arrayRandom, 0 , arrayRandom.length-1);


		//파일에 씀
		try(FileWriter fw = new FileWriter("data6_quick.txt")){
			for(int i = 0; i < array.length; i ++) {
				if(i == array.length-1) {
					//마지막일 경우에는 , 없이 쓴다
					fw.write(array[i]);
				}
				else {
					//마지막이 아닐 경우에는 , 포함
					fw.write(array[i] +  ",");
				}
			}
		}

		//RandomQuick의 결과를 파일에 씀
		try(FileWriter fw = new FileWriter("data6_quickRandom.txt")){
			for(int i = 0; i < arrayRandom.length; i ++) {
				if(i == arrayRandom.length-1) {
					//마지막일 경우에는 , 없이 쓴다
					fw.write(array[i]);
				}
				else {
					//마지막이 아닐 경우에는 , 포함
					fw.write(array[i] +  ",");
				}
			}
		}
	}

	private static void quickSort(String[] array, int start, int end) {
		if(start < end) {
			
			//구간을 나눌 값을 partition으로 가져옴
			int q = partition(array, start, end);

			quickSort(array, start, q-1);
			quickSort(array, q+1, end);
		}
	}

	private static void quickSort_withRandom(String[] arrayRandom, int start, int end) {
		if(start < end) {
			
			//구간을 나눌 값을 randomizedPartition로 가져옴
			int q = randomizedPartition(arrayRandom, start, end);

			quickSort_withRandom(arrayRandom, start, q-1);
			quickSort_withRandom(arrayRandom, q+1, end);
		}
	}

	private static int randomizedPartition(String[] arrayRandom, int start, int end) {

		Random random = new Random();

		//int 배열을 만들어 배열 안에서 random index를 3개 뽑는다.
		int[] randomArray = new int[3];

		for(int i = 0; i < 3; i++) {
			randomArray[i] = random.nextInt(end);
		}

		//오름차순으로 인덱스 정렬
		Arrays.sort(randomArray);

		//중간 인덱스를 사용함(배열에서 이 인덱스에 해당하는 끝값으로 사용할것)
		int q = randomArray[1];
		
		//끝값과 나온 값을 switching
		String tmp = arrayRandom[end];
		arrayRandom[end] = arrayRandom[q];
		arrayRandom[q] = tmp;
		
		//바꾼 끝값을 가지고 partition
		return partition(arrayRandom, start, end);
	}

	private static int partition(String[] array, int start, int end) {
		//마지막 값을 잡음
		int x = Integer.parseInt(array[end]);
		int i = start - 1;
		
		//마지막 배열을 제외하고 배열 전체를 돎
		for(int j = start; j <= end-1; j++) {
			//만약 현재 j index의 값이 x 보다 작으면
			if(Integer.parseInt(array[j]) <= x) {
				//i를 늘리고
				//j랑 i를 바꿔야 함
				i++;
				String tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
			}
		}
		
		//마지막에 i를 늘리고 마지막 i의 값과 현재 기준값을 바꾼다.
		i++;
		String tmp = array[i];
		array[i] = array[end];
		array[end] = tmp;

		return i;
	}

}
