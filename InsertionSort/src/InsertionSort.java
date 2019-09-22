import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class InsertionSort {

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
		
		//�о�� �� �迭�� ����
		String[] array = input.split(",");
		
		//Sort ����
		insertionSort(array);
		
		//���Ͽ� ��
		try(FileWriter fw = new FileWriter("result_InsertionSort.txt")){
			fw.write(Arrays.toString(array));
		}
				
	}
	
	public static void insertionSort(String[] array) {
		
		//�ι� �� ���Һ��� �ݺ����� ����
		for(int cur = 1; cur < array.length; cur++) {
			
			int key = Integer.parseInt(array[cur]);
			
			//���� cur�� ���� �������Ҹ� ���� ���鼭 ������ ��ġ�� ã�´�
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur�� ���Һ��� ū ��� ��ġ�� �����Ѵ�
				if(Integer.parseInt(array[pre]) > key) {
					array[pre + 1] = array[pre];
					array[pre] = Integer.toString(key);
				}
			}
		}
	}

}
