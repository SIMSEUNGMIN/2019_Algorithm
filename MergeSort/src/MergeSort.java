import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class MergeSort {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String input = "";
		
		//�о�� ���� ���
		String filePath = "C:/Users/user/Desktop/data02.txt";
		
		//���Ͽ��� �о��
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//�о�� ���� �迭�� ����
		String[] array = input.split(",");
		
		//Sort ����
		mergeSort(array, 0, array.length-1);
		
		//����� ���Ͽ� ��
		try(FileWriter fw = new FileWriter("result_MergeSort.txt")){
			fw.write(Arrays.toString(array));
		}
	}
	
	//�迭�� ������ �ɰ��� ������ �ɰ� �迭 2���� ���ҵ��� ������������ ���Ѵ�.
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
		
		//�� �� �ϳ��� ���� ������ ������ ũ�⸦ ���Ͽ� tmpArray�� ������������ �ִ´�
		while((tmpStart <= mid) && (tmpMid <= end)) {
			//�� �迭�� �� ū ���(�� �迭�� tmp�� ������)
			if(Integer.parseInt(array[tmpStart]) < Integer.parseInt(array[tmpMid])) {
				tmpArray[count] = Integer.parseInt(array[tmpStart]);
				count++;
				tmpStart++;
			}
			//�� �迭�� ���ų� �� ū ��� (�� �迭�� tmp�� ������)
			else {
				tmpArray[count] = Integer.parseInt(array[tmpMid]);
				count++;
				tmpMid++;
			}
		}
		
		//�� �迭�� ������ ��� ���� �� �迭�� ���� tmpArray�� ���δ�
		if(tmpStart <= mid) {
			for(int i = tmpStart; i <= mid; i++) {
				tmpArray[count] = Integer.parseInt(array[i]);
				count++;
			}
		}
		//�� �迭�� ������ ��� ���� �� �迭�� ���� tmpArray�� ���δ�
		else if(tmpMid <= end) {
			for(int i = tmpMid; i <= end; i++) {
				tmpArray[count] = Integer.parseInt(array[i]);
				count++;
			}
		}
		//������ ���
		else;
		
		//�迭 �ű��
		for(int i = start; i <= end; i++) {
			array[i] = Integer.toString(tmpArray[i]);
		}
		
		//System.out.println("start : " + start + ", mid : " + mid + ", end : " + end + ", �迭 : " + Arrays.toString(array));
	}

}
