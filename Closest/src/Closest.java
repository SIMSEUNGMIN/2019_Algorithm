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
		
		//읽어온 값으로 배열을 만듦(\n기준)
		String[] inputArray = input.split("\n");
		
		//포인트들을 저장할 포인트의 배열 선언
		Point[] array = new Point[inputArray.length];
		
		//좌표값이 들어간 array
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array[i] = new Point(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
		}
		
		//X좌표 기준으로 오름차순 정렬
		xSort(array);
		
		//System.out.println(array[0].getX() + ", " + array[1].getX()  + ", " + array[2].getX()  + ", " + array[3].getX());
		
		//정렬된 array로 Closest_pair구한 결과 출력
		System.out.printf("결과 : %.3f",closestPair(array));
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
					//두 점 사이가 가장 최솟값
					min = Math.sqrt(Math.pow((array[0].getX() - array[1].getX()), 2) 
							+ Math.pow((array[0].getY() - array[1].getY()), 2));
					
					return min;
				case 3:
					tmpDistance = new double[array.length];
					
					//점들 사이의 거리
					//0번 째 값과 1번 쨰 값
					tmpDistance[0] = Math.sqrt(Math.pow((array[0].getX() - array[1].getX()), 2) 
							+ Math.pow((array[0].getY() - array[1].getY()), 2));
					//1번 째 값과 2번 째 값
					tmpDistance[1] = Math.sqrt(Math.pow((array[1].getX() - array[2].getX()), 2) 
							+ Math.pow((array[1].getY() - array[2].getY()), 2));
					//2번 째 값과 0번 쨰 값
					tmpDistance[2] = Math.sqrt(Math.pow((array[2].getX() - array[0].getX()), 2) 
							+ Math.pow((array[2].getY() - array[0].getY()), 2));
					
					//거리들 중 최솟값을 구해서 return
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
		//점의 개수가 4개 이상일 때
		else {
			//배열을 반으로 쪼개서 왼쪽과 오른쪽으로 나눈 뒤
			//왼쪽과 오른쪽에서 각각 나온 최솟값 중에서 더 작은 값을 반환함
			Point[] left = Arrays.copyOfRange(array, 0, array.length/2);
			Point[] right = Arrays.copyOfRange(array, array.length/2, array.length);
			min = min(closestPair(left), closestPair(right));
		}
		
		count = 0;
		
		//구하고 나서 최솟값보다 작은 점들을 배열에서 제외
		for(int i = 0; i < array.length; i++) {
			if((array.length/2 - min) < array[i].getX()) {
				tmpArray[count++] = array[i];
			}
		}
		
		arrayForY = Arrays.copyOfRange(tmpArray, 0, count);
		
		//arrayForY만 가지고 sorting
		ySort(arrayForY);
	
		count = 0;
		
		//window 내부의 최단 거리를 구함
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

	//두 점 중 더 작은 값을 구하는 함수
	private static double min(double left, double right) {
		if(left <= right) {
			return left;
		}
		
		return right;
	}

	private static void xSort(Point[] array) {
		//두번째부터 시작
		for(int cur = 1; cur < array.length; cur++) {
			Point curKey = array[cur];
			
			//현재 cur의 원소 이전 원소를 전부 돌면서 적절한 위치를 찾음
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur의 원소보다 pre의 원소가 큰 경우 위치 변경
				if(array[pre].getX() > curKey.getX()) {
					array[pre+1] = array[pre];
					array[pre] = curKey;
				}
			}
		}	
	}
	
	private static void ySort(Point[] arrayForY) {
		//두번째부터 시작
		for(int cur = 1; cur < arrayForY.length; cur++) {
			Point curKey = arrayForY[cur];

			//현재 cur의 원소 이전 원소를 전부 돌면서 적절한 위치를 찾음
			for(int pre = cur-1; pre >= 0; pre--) {

				//cur의 원소보다 pre의 원소가 큰 경우 위치 변경
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

