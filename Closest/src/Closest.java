import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Closest {
	
	static double min = 0;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String input = "";
		
		String filePath = "C:/Users/user/Desktop/data04_closest.txt";
		
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//�о�� ������ �迭�� ����(\n����)
		String[] inputArray = input.split("\n");
		
		//����Ʈ���� ������ ����Ʈ�� �迭 ����
		Point[] array = new Point[inputArray.length];
		
		//��ǥ���� �� array
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array[i] = new Point(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
		}
		
		//X��ǥ �������� �������� ����
		xSort(array);
		
		//System.out.println(array[0].getX() + ", " + array[1].getX()  + ", " + array[2].getX()  + ", " + array[3].getX());
		
		//���ĵ� array�� Closest_pair���� ��� ���
		System.out.printf("��� : %.3f",closestPair(array));
	}

	private static double closestPair(Point[] array) {
		
		Point[] tmpArray = new Point[array.length];
		Point[] arrayForY;
		double[] tmpDistance;
		double[] realDistance;
		
		int count = 0;
		
		if(array.length <= 3) {
			
			switch(array.length) {
				case 2:
					//�� �� ���̰� ���� �ּڰ�
					min = Math.sqrt(Math.pow((array[0].getX() - array[1].getX()), 2) 
							+ Math.pow((array[0].getY() - array[1].getY()), 2));
					
					return min;
				case 3:
					tmpDistance = new double[array.length];
					
					//���� ������ �Ÿ�
					//0�� ° ���� 1�� �� ��
					tmpDistance[0] = Math.sqrt(Math.pow((array[0].getX() - array[1].getX()), 2) 
							+ Math.pow((array[0].getY() - array[1].getY()), 2));
					//1�� ° ���� 2�� ° ��
					tmpDistance[1] = Math.sqrt(Math.pow((array[1].getX() - array[2].getX()), 2) 
							+ Math.pow((array[1].getY() - array[2].getY()), 2));
					//2�� ° ���� 0�� �� ��
					tmpDistance[2] = Math.sqrt(Math.pow((array[2].getX() - array[0].getX()), 2) 
							+ Math.pow((array[2].getY() - array[0].getY()), 2));
					
					//�Ÿ��� �� �ּڰ��� ���ؼ� return
					min = tmpDistance[0];
					
					for(int i = 1; i < tmpDistance.length; i++) {
						if(tmpDistance[i] < min) {
							min = tmpDistance[i];
						}
					}
					return min;
				default:
					return min;
			}
		}
		//���� ������ 4�� �̻��� ��
		else {
			//�迭�� ������ �ɰ��� ���ʰ� ���������� ���� ��
			//���ʰ� �����ʿ��� ���� ���� �ּڰ� �߿��� �� ���� ���� ��ȯ��
			Point[] left = Arrays.copyOfRange(array, 0, array.length/2);
			Point[] right = Arrays.copyOfRange(array, array.length/2, array.length);
			min = min(closestPair(left), closestPair(right));
		}
		
		count = 0;
		
		//���ϰ� ���� �ּڰ����� ���� ������ �迭���� ����
		for(int i = 0; i < array.length; i++) {
			if((array.length/2 - min) < array[i].getX()) {
				tmpArray[count++] = array[i];
			}
		}
		
		arrayForY = Arrays.copyOfRange(tmpArray, 0, count);
		
		//arrayForY�� ������ sorting
		ySort(arrayForY);
	
		count = 0;
		
		//window ������ �ִ� �Ÿ��� ����
		tmpDistance = new double[arrayForY.length * arrayForY.length];
		
		for(int one = 0; one < arrayForY.length-1; one++) {
			for(int two = one + 1; two < arrayForY.length; two++) {
				tmpDistance[count++] = Math.sqrt(Math.pow((arrayForY[one].getX() - arrayForY[two].getX()), 2) 
						+ Math.pow((arrayForY[one].getY() - arrayForY[two].getY()), 2));
			}
		}
		
		realDistance = Arrays.copyOfRange(tmpDistance, 0, count);
		
		for(int i = 1; i < realDistance.length; i++) {
			if(tmpDistance[i] < min) {
				min = tmpDistance[i];
			}
		}
		
		return min;
	}

	//�� �� �� �� ���� ���� ���ϴ� �Լ�
	private static double min(double left, double right) {
		if(left <= right) {
			return left;
		}
		
		return right;
	}

	private static void xSort(Point[] array) {
		//�ι�°���� ����
		for(int cur = 1; cur < array.length; cur++) {
			Point curKey = array[cur];
			
			//���� cur�� ���� ���� ���Ҹ� ���� ���鼭 ������ ��ġ�� ã��
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur�� ���Һ��� pre�� ���Ұ� ū ��� ��ġ ����
				if(array[pre].getX() > curKey.getX()) {
					array[pre+1] = array[pre];
					array[pre] = curKey;
				}
			}
		}	
	}
	
	private static void ySort(Point[] arrayForY) {
		//�ι�°���� ����
		for(int cur = 1; cur < arrayForY.length; cur++) {
			Point curKey = arrayForY[cur];

			//���� cur�� ���� ���� ���Ҹ� ���� ���鼭 ������ ��ġ�� ã��
			for(int pre = cur-1; pre >= 0; pre--) {

				//cur�� ���Һ��� pre�� ���Ұ� ū ��� ��ġ ����
				if(arrayForY[pre].getY() > curKey.getY()) {
					arrayForY[pre+1] = arrayForY[pre];
					arrayForY[pre] = curKey;
				}
			}
		}	
		
	}

}

class Point{
	private double x;
	private double y;
	
	public Point(double newX, double newY) {
		this.x = newX;
		this.y = newY;
	}
	
	public void setX(double givenX) {
		this.x = givenX;
	}
	
	public void setY(double givenY) {
		this.y = givenY;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
}

