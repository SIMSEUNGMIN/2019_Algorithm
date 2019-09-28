import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Closest {

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
		System.out.println(closestPair(array));
		
	}

	private static double closestPair(Point[] array) {
		
		if(array.length <= 3) {
			
			switch(array.length) {
				case 2:
					//�� �� ���̰� ���� �ּڰ�
					return Math.sqrt(Math.pow((array[0].getX() - array[1].getX()), 2) 
							+ Math.pow((array[0].getY() - array[1].getY()), 2));
				case 3:
					double[] tmpDistance = new double[array.length];
					double minValue;
					
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
					minValue = tmpDistance[0];
					
					for(int i = 1; i < tmpDistance.length; i++) {
						if(tmpDistance[i] < minValue) {
							minValue = tmpDistance[i];
						}
					}
					
					return minValue;
				default:
					return 0;	
			}
		}
		//���� ������ 4�� �̻��� ��
		else {
//			List<Point> left = array.add
//			List<Point> right = Arrays.;
//			return min();
		}
		
		return 0;
	}

	//�ּڰ��� ���ϴ� �Լ�
//	private static float min(float left, float right) {
//		if(left <= right) {
//			return left;
//		}
//		
//		return right;
//	}

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

