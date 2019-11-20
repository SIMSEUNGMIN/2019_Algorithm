import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BellmanFord {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String input1 = "";
		String input2 = "";

		// 읽어올 파일 경로
		String filePath1 = "C:/Users/user/Desktop/data11_bellman_ford_1.txt";
		String filePath2 = "C:/Users/user/Desktop/data11_bellman_ford_2.txt";

		// 파일1에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath1);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input1 = new String(readByte);
		}

		// 파일2에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath2);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input2 = new String(readByte);
		}

		//줄바꿈 기준으로 데이터 배열로 정렬
		String[] input1Array = input1.split("\\r?\\n");
		String[] input2Array = input2.split("\\r?\\n");

		//0번째 배열에서 vertex개수를 구함
		int input1NumV = (input1Array[0].split(",")).length;
		int input2NumV = (input2Array[0].split(",")).length;

		//그래프 리스트 생성
		List<PointNVertex> graph1 = new ArrayList<>();
		List<PointNVertex> graph2 = new ArrayList<>();

		//그래프에 값을 채움
		for(int i = 1; i < input1Array.length; i++) {
			String[] temp = input1Array[i].split(",");
			int start = Integer.parseInt(temp[0]);
			int dst = Integer.parseInt(temp[1]);
			int weight = Integer.parseInt(temp[2]);

			graph1.add(new PointNVertex(start, dst, weight));
		}

		for(int i = 1; i < input2Array.length; i++) {
			String[] temp = input2Array[i].split(",");
			int start = Integer.parseInt(temp[0]);
			int dst = Integer.parseInt(temp[1]);
			int weight = Integer.parseInt(temp[2]);

			graph2.add(new PointNVertex(start, dst, weight));
		}

		System.out.println("--------------------- 첫번 째 --------------------\n");
		BellmanFord(graph1, input1NumV);
		System.out.println("--------------------- 두번 째 --------------------\n");
		BellmanFord(graph2, input2NumV);


	}

	private static void BellmanFord(List<PointNVertex> graph, int numV) {
		//그래프의 vertex 개수만큼 최단 거리를 저장해둘 배열 생성
		int[] distance = new int[numV];
		boolean isChanged = true;

		//시작 지점의 값은 0으로 초기화
		distance[0] = 0;

		//거리를 무한대 값으로 초기화 (0번을 제외)
		for(int i = 1; i < distance.length; i++)
			distance[i] = Integer.MAX_VALUE;

		//vertex-1 만큼 relaxation
		for(int num = 0; num < numV-1; num++) {
			System.out.println("----------------- iteration " + num + " -----------------");

			for(int i = 0; i < graph.size(); i ++) {
				int start = graph.get(i).start;
				int dst = graph.get(i).dst;
				int weight = graph.get(i).weight;

				//현재 distance에 있는 값과 새로 구한 distance(시작점의 최소거리 + 도착점까지의 무게)
				int curDistance = distance[dst];
				int newDistance = distance[start] + weight;

				//만약 start점의 거리가 무한이 아니고 현재 distance가 새로 구한 distance가 더 작을 경우
				//경우를 출력, 그리고 현재 distance값에 새로 구한 distance값을 넣어준다.
				if(distance[start] != Integer.MAX_VALUE && newDistance < curDistance) {
					distance[dst] = newDistance;
					
					//만약 curDistance 값이 무한이면 값을 출력하는 대신 inf로 대체
					if(curDistance == Integer.MAX_VALUE)
						System.out.println("update distance of " + dst + " from inf to " + distance[dst]);
					else
						System.out.println("update distance of " + dst + " from " + curDistance + " to " + distance[dst]);
				}
			}

			System.out.println("Iteration " + num + " distance : " + Arrays.toString(distance) + "\n");

		}
		
		//마지막으로 한 번 더 relaxation 함 
		System.out.println("----------------- iteration " + (numV-1) + " -----------------");
		
		for(int i = 0; i < graph.size(); i ++) {
			int start = graph.get(i).start;
			int dst = graph.get(i).dst;
			int weight = graph.get(i).weight;

			//현재 distance에 있는 값과 새로 구한 distance(시작점의 최소거리 + 도착점까지의 무게)
			int curDistance = distance[dst];
			int newDistance = distance[start] + weight;

			//만약 start점의 거리가 무한이 아니고 현재 distance가 새로 구한 distance가 더 작을 경우
			//경우를 출력, 그리고 현재 distance값에 새로 구한 distance값을 넣어준다.
			if(distance[start] != Integer.MAX_VALUE && newDistance < curDistance) {
				distance[dst] = newDistance;
				
				//이때 curDistance와 newDistance 값이 다르다면 그래프가 바뀐 것인데 이것은 음수 싸이클을 의미함
				//따라서 isChanged를 true에서 fasle로 바꾼다
				if(curDistance != newDistance) {
					isChanged = false;
				}
				
				//만약 curDistance 값이 무한이면 값을 출력하는 대신 inf로 대체
				if(curDistance == Integer.MAX_VALUE)
					System.out.println("update distance of " + dst + " from inf to " + distance[dst]);
				else
					System.out.println("update distance of " + dst + " from " + curDistance + " to " + distance[dst]);
			}
		}
		
		System.out.println("Iteration " + (numV-1) + " distance : " + Arrays.toString(distance) + "\n");
		
		//마지막 relaxation의 결과 출력 후 값이 변경되었는지 확인
		//변경되었으면 음수 싸이클이 있다고 알리고 변경되지 않았으면 최소 거리 값을 출력
		if(!isChanged) {
			System.out.println("The graph has negative cycle");
		}
		else {
			System.out.println("Final distance : " + Arrays.toString(distance));
		}
		
		System.out.println();
	}

	static class PointNVertex{
		private int start;
		private int dst;
		private int weight;

		public PointNVertex(int start, int dst, int weight) {
			this.start = start;
			this.dst = dst;
			this.weight = weight;
		}
	}

}
