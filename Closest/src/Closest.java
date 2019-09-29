import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Closest {
	
	static double min = 0;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String input = "";
		
		//파일을 받아올 경로
		String filePath = "C:/Users/user/Desktop/data04_closest.txt";
		
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//읽어온 값으로 배열을 만듦(\n기준)
		String[] inputArray = input.split("\n");
		
		//포인트들을 저장할 포인트의 ArrayList 선언
		List<Point> array = new ArrayList<>();
		
		//좌표값이 들어간 arrayList
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array.add(new Point(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1])));
		}
		
		//X좌표 기준으로 오름차순 정렬
		Collections.sort(array, new PointComparatorX());
		
		//정렬된 array로 Closest_pair구한 결과 출력
		System.out.printf("결과 : %.3f",closestPair(array, 0, array.size()));
	}

	private static double closestPair(List<Point> array, int start, int end) {
		
		// window 안에 들어갈 점을 모아둘 ArrayList선언
		List<Point> tmpArray = new ArrayList<>();
		
		//start가 시작부분, end가 끝부분이기 때문에 빼면 배열에서 검사하고 싶은 원소의 개수를 알 수 있음
		switch(end-start) {
		//2일 경우 두 점의 사이가 가장 최솟값
		case 2:
			//두 점 사이가 가장 최솟값
			return distance(array.get(start), array.get(start+1));
		//3일 경우 세개를 번갈아 구한 값 중에서 가장 작은 값이 최솟값
		case 3:
			double[] tmpDistance = new double[3];

			//점들 사이의 거리
			//0번 째 값과 1번 째 값
			tmpDistance[0] = distance(array.get(start), array.get(start+1));
			//1번 째 값과 2번 째 값
			tmpDistance[1] = distance(array.get(start+1), array.get(start+2));
			//2번 째 값과 0번 째 값
			tmpDistance[2] = distance(array.get(start), array.get(start+2));

			//거리를 오름차순으로 정렬
			Arrays.sort(tmpDistance);
			return tmpDistance[0];
		}
		
		//점의 개수가 4개 이상일 때
		//배열을 반으로 쪼개서 왼쪽과 오른쪽으로 나눈 뒤
		//왼쪽과 오른쪽에서 각각 나온 최솟값 중에서 더 작은 값을 반환함
		int mid = (start + end) /2;
		double min = Math.min(closestPair(array, start, mid), closestPair(array, mid, end));

		//구하고 나서 최솟값보다 작은 점들을 배열에서 제외
		for(int i = start; i < end; i++) {
			double window = array.get(mid).getX() - array.get(i).getX(); //한쪽 window의 크기(절댓값의 경우)
			if((window * window) < (min * min)) {
				tmpArray.add(array.get(i));
			}
		}
		
		//Y 오름차순 기준 sorting
		Collections.sort(tmpArray, new PointComparatorY());

		//window 내부의 최단 거리를 구함
		for(int one = 0; one < tmpArray.size()-1; one++) {
			for(int two = one + 1; two < tmpArray.size(); two++) {
				double tmp = tmpArray.get(two).getY() - tmpArray.get(one).getY();
				
				//만약 one의 점과 two의 각각 y 좌표를 뺀 값이 최솟값보다 크다면 
				//그 자신을 포함한 그 뒤의 two 값들은 전부 볼 필요가 없다
				//왜냐하면 x값을 더해서 거리를 구하기 전에 이미 최솟값을 넘었기 때문
				if((tmp*tmp) < (min*min)) {
					double tmpD = distance(tmpArray.get(one), tmpArray.get(two));
					if(tmpD < min) {
						min = tmpD;
					}
				}
				else {
					break;
				}
			}
		}

		return min;
	}
	
	//두 점의 거리를 구하는 함수
	private static double distance(Point left, Point right) {
		double XValue = left.getX() - right.getX();
		double YValue = left.getY() - right.getY();
		
		return Math.sqrt((XValue * XValue) + (YValue * YValue));
	}
}

class PointComparatorX implements Comparator<Point>{
	
	@Override
	public int compare(Point left, Point right) {
		// X에 대해서 오름 차순
		if(left.getX() > right.getY()) {
			return 1;
		}
		else if(left.getX() == right.getX()){
			return (int)(left.getY() - right.getY());
		}
		return -1;
	}
}

class PointComparatorY implements Comparator<Point>{
	
	@Override
	public int compare(Point left, Point right) {
		// Y에 대해서 오름 차순
		if(left.getY() >= right.getY()) {
			return 1;
		}
		return -1;
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

