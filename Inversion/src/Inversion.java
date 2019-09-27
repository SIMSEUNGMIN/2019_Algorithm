import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Inversion {
	
	static int[] sortedList;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String input = "";

		//�о�� ���� ���
		String filePath = "C:/Users/user/Desktop/data04_inversion.txt";

		//���Ͽ��� �о��
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		//�о�� �� �迭�� ����
		String[] array = input.split(",");
		
		//���ڵ��� ���ĵ� ����Ʈ�� �ʱ�ȭ
		sortedList = new int[array.length];

		//Sort ����� ���ÿ� ������ ��� ���
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
