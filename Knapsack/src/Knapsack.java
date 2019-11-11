import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Knapsack {

	static int[] value;
	static int[] weight;
	static int[][] knapsackTable;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);

		String input = "";
		// 읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data10_knapsack.txt";
		String[] splitStringEnter;
		//물건 시작 위치
		int index;

		// 파일에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		// 줄 바꿈 기준으로 나눈 것
		splitStringEnter = input.split("\\r?\\n");
		// 가치
		value = new int[splitStringEnter.length + 1];
		// 무게
		weight = new int[splitStringEnter.length + 1];
		// 시작 위치
		index = splitStringEnter.length;
		
		System.out.print("배낭의 사이즈를 입력하세요(0 ~ 50) : ");
		// 가방 크기
		int capacity = scanner.nextInt();

		// knapsack 테이블
		knapsackTable = new int[splitStringEnter.length + 1][capacity + 1];

		// 가치 배열과 무게 배열을 채움
		for(int i = 1; i <splitStringEnter.length + 1; i++) {
			String[] temp = splitStringEnter[i-1].split(",");
			value[i] = Integer.parseInt(temp[1]);
			weight[i] = Integer.parseInt(temp[2]);
		}
		
		// 가치 최댓값 구함
		int max = knapsack(splitStringEnter.length + 1, capacity + 1);
		
		// 최댓값에 포함되는 아이템의 번호를 구함
		findItem(max, splitStringEnter.length + 1, capacity);
		
		System.out.println("\n결과 : " + knapsack(splitStringEnter.length + 1, capacity + 1) + "\n");
		
		// 전체 테이블 출력
		for(int i = 0; i < splitStringEnter.length + 1; i++) {
			for(int j = 0; j < capacity + 1; j++) {
				System.out.print(knapsackTable[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	// 최댓값에 해당하는 아이템의 번호들을 찾는 것
	private static void findItem(int max, int itemNum, int capacity) {
		// 최댓값에 해당하는 아이템의 번호 저장
		List<Integer> itemIndex = new ArrayList<Integer>();
		
		// 최댓값이 먼저 나오는 인덱스
		int maxIndex = 0;
		// item 합
		int sumItem = 0;
		
		for(int i = 1; i < itemNum; i++) {
			// 최댓값을 만족하는 최소 인덱스를 구함
			if(knapsackTable[i][capacity] == max) {
				maxIndex = i;
				break;
			}
		}
		
		// 최소 인덱스에서 1까지 반복문을 돌리면서
		// sumItem에 인덱스에 해당하는 value 값을 넣음
		// sumItem의 크기가 최대가치값(max)보다 클 경우 초과이므로 아이템의 가치를 다시 빼줌
		// sumItem의 크기가 max와 같을 경우 List에 저장하고 반복문 종료
		// sumItem의 크기가 max보다 작을 경우 List에 저장하고 반복문 계속 실행
		for(int i = maxIndex; i >= 1; i--) {
			sumItem += value[i];
			
			//더한 item은 들어가면 안되는 item이므로 다시 빼줘야한다.
			if(sumItem > max) {
				sumItem -= value[i];
			}
			
			if(sumItem == max) {
				itemIndex.add(0, i);
				break;
			}
			else if(sumItem < max) {
				itemIndex.add(0, i);
			}
		}
		
		//반복문이 다 끝나고 나면 list에 최대가치값에 해당하는 item의 번호들이 존재 -> 출력
		System.out.print("item : ");
		for(int i : itemIndex) {
			System.out.print(i + " ");
		}
		
	}

	private static int knapsack(int itemNum, int capacity) {
		int maxValue = 0;
		
		// 테이블 전체의 값을 채우기 위한 반복문
		for(int i = 1; i < itemNum; i++) {
			for(int j = 1; j < capacity; j++) {
				
				// 현재 아이템의 무게가 가방의 크기보다 작을 때
				// 현재 아이템의 값을 포함하는게 더 나은 선택인지 아닌지 확인
				if(weight[i] <= j) {
					maxValue = Math.max(knapsackTable[i - 1][j], 
							knapsackTable[i - 1][j - weight[i]] + value[i]);
					
					knapsackTable[i][j] = maxValue;
				}
				// 현재 아이템의 무게가 가방의 크기보다 클 때
				// 가방 안에 들어갈 수 없으므로 현재 아이템을 포함하지 않는 값을 넣음
				else {
					maxValue = knapsackTable[i-1][j];
					knapsackTable[i][j] = maxValue;
				}
			}
			
		}
		
		return maxValue;

	}

}
