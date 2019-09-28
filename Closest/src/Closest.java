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
		
		//읽어온 값으로 배열을 만듦(\n기준)
		String[] inputArray = input.split("\n");
		
		//포인터를 저장할 List선언
		List<Point> array = new ArrayList<>();
		
		//좌표값이 들어간 array
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array.add(new Point(Float.parseFloat(tmp[0]), Float.parseFloat(tmp[1])));
		}
		
		//X좌표 기준으로 오름차순 정렬
		xSort(array);
		
		//정렬된 array로 Closest_pair구한 결과 출력
		System.out.println(closestPair(array));
		
	}

	private static double closestPair(List<Point> array) {
		
		if(array.size() <= 3) {
			
			switch(array.size()) {
				case 2:
					//두 점 사이가 가장 최솟값
					return Math.sqrt(Math.pow((array.get(0).getX() - array.get(1).getX()), 2) 
							+ Math.pow((array.get(0).getY() - array.get(1).getY()), 2));
				case 3:
					double[] tmpDistance = new double[array.size()];
					Point array0 = array.get(0);
					Point array1 = array.get(1);
					Point array2 = array.get(2);
					
					double minValue;
					
					//점들 사이의 거리
					//0번 째 값과 1번 쨰 값
					tmpDistance[0] = Math.sqrt(Math.pow((array0.getX() - array1.getX()), 2) 
							+ Math.pow((array0.getY() - array0.getY()), 2));
					//1번 째 값과 2번 째 값
					tmpDistance[1] = Math.sqrt(Math.pow((array1.getX() - array2.getX()), 2) 
							+ Math.pow((array1.getY() - array2.getY()), 2));
					//2번 째 값과 0번 쨰 값
					tmpDistance[2] = Math.sqrt(Math.pow((array2.getX() - array0.getX()), 2) 
							+ Math.pow((array2.getY() - array0.getY()), 2));
					
					//거리들 중 최솟값을 구해서 return
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
		//점의 개수가 4개 이상일 때
		else {
//			List<Point> left = array.add
//			List<Point> right = Arrays.;
//			return min();
		}
		
		return 0;
	}

	//최솟값을 구하는 함수
//	private static float min(float left, float right) {
//		if(left <= right) {
//			return left;
//		}
//		
//		return right;
//	}

	private static void xSort(List<Point> array) {
		//두번째부터 시작
		for(int cur = 1; cur < array.size(); cur++) {
			Point curKey = array.get(cur);
			
			//현재 cur의 원소 이전 원소를 전부 돌면서 적절한 위치를 찾음
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur의 원소보다 pre의 원소가 큰 경우 위치 변경
				if(array.get(pre).getX() > curKey.getX()) {
					array.set(pre+1, array.get(pre));
					array.set(pre, curKey);
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

