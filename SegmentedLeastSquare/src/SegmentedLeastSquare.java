import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegmentedLeastSquare {
	static double[][] a;
	static double[][] b;

	// 분할 기준
	static int[] segment;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String input = "";

		// 읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data08.txt";

		// 파일에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1)
				;
			fStream.close();
			input = new String(readByte);
		}

		String[] inputArray = input.split(",");

		// 점의 개수
		int pointNumber = Integer.parseInt(inputArray[0]);
		// cost 값
		int cost = Integer.parseInt(inputArray[inputArray.length - 1]);

		// 나머지 점들 가져옴
		Point[] points = new Point[pointNumber + 1];
		// 에러 값을 넣을 이차원 배열
		double[][] error = new double[pointNumber + 1][pointNumber + 1];

		// y = a * x + b의 a
		a = new double[pointNumber + 1][pointNumber + 1];

		// y = a * x + b의 b
		b = new double[pointNumber + 1][pointNumber + 1];

		//opt중에서 가장 작은 값을 가질 때의 상대 점 index
		segment = new int[pointNumber + 1];

		//분할된 구간을 저장하는 List
		List<Integer> segmentPart = new ArrayList<Integer>();

		for (int i = 1; i < 24; i += 2) {
			double newX = Double.parseDouble(inputArray[i]);
			double newY = Double.parseDouble(inputArray[i + 1]);
			Point newPoint = new Point(newX, newY);
			points[i / 2 + 1] = newPoint;
		}

		// SegmentedLeastSquare 실행
		System.out.format("Cost of the optimal solution : %.6f \n\n",
				SegmentedLeastSquare(pointNumber, points, error, cost));


		System.out.println("An optimal solution : ");

		//분할된 구간들을 리스트로 구함
		findSegment(segment, segmentPart);

		//리스트에 맞춰서 출력
		for(int i = 0; i < segmentPart.size()-1; i++) {
			int first = segmentPart.get(i);
			int second = segmentPart.get(i+1)-1;

			System.out.println("[Segment " + first + " - " + second + "] : "
					+ "y = " + (Math.round((a[first][second])*1000000)/1000000.0) + " * x + " + (Math.round((b[first] [second])*1000000)/1000000.0)
					+ "\t// square error : " + (Math.round((error[first][second])*1000000)/1000000.0));
		}
	}

	private static void findSegment(int[] segment, List<Integer> segmentPart) {
		//맨 마지막 점의 위치를 저장
		segmentPart.add(segment.length);

		//현재 배열이 가르키는 값
		int cur = segment[segment.length-1];

		//만약 마지막이 가리키는 숫자가 1이 아닐 경우에는 분할 된 경우
		//따라서 현재 값을 저장하고 다음 분할 위치를 찾으러 감 (1이 나올 때까지 되는 것)	
		while(cur != 1) {
			segmentPart.add(0, cur);
			cur = segment[cur];
		}

		//1인 경우 밖으로 나오게 된다
		segmentPart.add(0, cur);
	}

	private static double SegmentedLeastSquare(int pointNumber, Point[] points, double[][] error, int cost) {
		// opt 값
		double[] optCost = new double[pointNumber + 1];

		optCost[0] = 0;

		for (int j = 1; j <= pointNumber; j++) {

			for (int i = 1; i <= j; i++) {
				// 에러 값 계산(eij pi부터 pj까지)
				if (i == j) {
					error[i][j] = Double.MAX_VALUE;
					break;
				}

				error[i][j] = calculateError(points, i, j);
			}
		}

		for (int j = 1; j <= pointNumber; j++) {
			// optCost[j] = 0부터 i,j까지 eij 더하고 c 더하고 optCost[i-1] 더한 여러 값 중에 가장 작은 값
			double min = Double.MAX_VALUE;

			for (int i = 1; i <= j; i++) {
				double cur = error[i][j] + cost + optCost[i - 1];
				
				//각각에서 최솟값을 가지게 되는 상대점의 위치를 저장함
				if (cur < min) {
					min = cur;
					segment[j] = i;
				}
			}

			optCost[j] = min;
		}

		return optCost[pointNumber];
	}

	private static double calculateError(Point[] points, int i, int j) {
		// sse 공식을 위한 직선의 방정식에 필요한 a값과 b값을 계산해서 구해옴
		double a = calculateA(points, i, j);
		double b = calculateB(points, i, j, a);

		double sse = 0;

		// y-a*x-b를 제곱한 값을 더함
		for (int start = i; start <= j; start++) {
			double temp = points[start].getY() - (a * points[start].getX()) - b;
			temp = Math.pow(temp, 2);
			sse += temp;
		}

		return sse;
	}

	private static double calculateA(Point[] points, int i, int j) {
		// n = j - i + 1

		// n * x * y, n 곱하기 x좌표의 합 곱하기 y좌표의 합
		double nxy = 0;
		// x * y, x좌표의 합과 y좌표의 합을 곱함
		double xy = 0;
		// n * (x^2), x좌표 제곱의 합에 n을 곱함
		double nSquareX = 0;
		// (x)^2, x좌표 합의 제곱
		double squareX = 0;

		// 구하려는 a의 값
		double returnA = 0;

		// n 곱하기 x좌표의 합 곱하기 y좌표의 합 구함
		for (int start = i; start <= j; start++) {
			nxy += points[start].getX() * points[start].getY();
		}

		nxy *= (j - i + 1);

		// x좌표의 합과 y좌표의 합을 곱 값을 구함
		double tempX = 0;
		double tempY = 0;
		for (int start = i; start <= j; start++) {
			tempX += points[start].getX();
			tempY += points[start].getY();
		}

		xy = tempX * tempY;

		// x좌표 제곱의 합에 n을 곱한 값을 구함
		for (int start = i; start <= j; start++) {
			nSquareX += Math.pow(points[start].getX(), 2);
		}

		nSquareX *= (j - i + 1);

		// x좌표 합의 제곱 값을 구함
		for (int start = i; start <= j; start++) {
			squareX += points[start].getX();
		}

		squareX = Math.pow(squareX, 2);

		// a의 값을 구함
		returnA = (nxy - xy) / (nSquareX - squareX);
		
		// 각각의 기울기 값 저장
		a[i][j] = returnA;

		return returnA;
	}

	private static double calculateB(Point[] points, int i, int j, double a) {
		// X좌표와 Y좌표를 모두 합한 값
		double sumX = 0;
		double sumY = 0;

		// 반환할 b 값
		double returnB = 0;

		// i번쨰 점부터 j번째 점까지의 x좌표, y좌표 각각의 합을 구함
		for (int start = i; start <= j; start++) {
			sumX += points[start].getX();
			sumY += points[start].getY();
		}

		// b를 구하는 공식 계산
		returnB = (sumY - (a * sumX)) / (j - i + 1);
		
		// 각각의 b 값 저장
		b[i][j] = returnB;

		return returnB;
	}

	static class Point {
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

}
