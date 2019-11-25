import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String input = "";

		// 읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data12.txt";

		// 파일에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		// 줄바꿈 기준으로 데이터 배열로 정렬
		String[] inputArray = input.split("\\r?\\n");

		//정점 리스트 생성
		List<Vertex> vertexs = new ArrayList<>();

		//0번째에는 안 쓰는 값 넣어둠
		vertexs.add(new Vertex(' ', 0, -1));

		//정점들의 vertex이름과 번호, 거리를 넣음
		String[] vertexTemp = inputArray[0].split(",");

		for(int i = 0; i < vertexTemp.length; i++) {
			if(i == 0)
				vertexs.add(new Vertex((char)(65+i), i+1, 0));
			else vertexs.add(new Vertex((char)(65+i), i+1, Integer.MAX_VALUE));
		}

		//입력값에 의해 생성된 heap 배열을 minHeap으로 만듦
		buildMinHeap(vertexs);

		//그래프 이차원 배열 생성
		int[][] graph = new int[vertexs.size()][vertexs.size()];

		//이차원 배열 초기화
		for(int i = 1; i < vertexs.size(); i++) {
			for(int j = 1; j < vertexs.size(); j++) {
				if(i == j)
					graph[i][j] = 0;
				graph[i][j] = Integer.MAX_VALUE;
			}
		}

		//읽어온 데이터에서 점들과 점들의 무게를 찾아서 집어넣음
		for(int i = 1; i < inputArray.length; i++) {
			String[] temp = inputArray[i].split(",");

			int start = Integer.parseInt(temp[0]);
			int dst = Integer.parseInt(temp[1]);
			int weight = Integer.parseInt(temp[2]);

			graph[start][dst] = weight;
		}

		System.out.println("Dijkstra's algorithm으로 계산한 결과는 다음과 같습니다.");
		dijkstra(graph, vertexs);

	}

	private static void buildMinHeap(List<Vertex> vertexs) {
		//자식을 가지는 마지막 노드부터 루트노드까지 순서대로 검사
		//현재 heapSize에는 사용하지 않는 0번째 노드가 포함되어있으므로  크기에서 -1을 해야함
		//전부 MaxHeap을 만들 수 있도록 검사
		for(int i = (vertexs.size()-1)/2; i >= 1; i--) {
			minHeapify(vertexs, i);
		}


	}

	private static void minHeapify(List<Vertex> vertexs, int cur) {
		//최소 노드의 index
		int min = cur;

		//왼쪽 노드와 오른쪽 노드의 index
		int left = 0;
		int right = 0;

		//오른쪽 노드가 존재하는지 확인
		boolean isRight = false;

		//자식 노드가 존재하는지 확인
		//왼쪽 노드 오른쪽 노드 둘 다 존재
		if((cur*2)+1 < vertexs.size()) {
			left = cur*2;
			right = (cur*2)+1;
			isRight = true;
		}
		//왼쪽 노드만 있는지 노드가 없는지 확인 필요
		else {
			if((cur*2) < vertexs.size()) {
				left = cur*2;
			}
			else {
				//왼쪽노드마저 없으면 더 이상 볼 필요 없으므로 현재 함수 종료
				return;
			}
		}

		//현재노드의 값보다 왼쪽 자식노드의 값이 작다면 min의 값 변경
		if(vertexs.get(left).weight < vertexs.get(min).weight) {
			min = left;
		}

		//오른쪽 자식 노드가 존재하고 
		//현재 노드의 값보다 오른쪽 자식 노드의 값이 작다면 min값 변경
		if(isRight &&
				vertexs.get(right).weight < vertexs.get(min).weight) {
			min = right;
		}

		//cur값과 min의 값이 같지 않다면 자식노드를 확인할 필요가 있음
		//따라서 값의 위치를 바꾸고 최대크기인 자식 노드를 다시 minHeapify함
		if(min != cur) {
			Vertex tmpMin = vertexs.get(min);
			vertexs.set(min, vertexs.get(cur));
			vertexs.set(cur, tmpMin);
			minHeapify(vertexs, min);
		}

	}

	private static void dijkstra(int[][] graph, List<Vertex> vertexs) {
		int count = 0;
		
		while(vertexs.size() != 1) {
			System.out.println("-----------------------------------------------------");

			//현재 정점의 정보를 추출
			Vertex curVertex = vertexs.get(1);

			System.out.println("S[" + count + "] : d[" + curVertex.vertexName + "] = " + curVertex.weight);
			System.out.println("-----------------------------------------------------");

			//현재 정점의 번호와 연결된 점들을 구함
			int curNumber = curVertex.number;
			for(int i = 2; i < vertexs.size(); i++) {
				
				System.out.print("Q[" + (i-2) + "] : d[" + vertexs.get(i).vertexName + "] = " + vertexs.get(i).weight);
				
				//연결된 점의 index를 추출함
				int connectVertexIndex = findConnectVertex(vertexs, vertexs.get(i).vertexName);
				
				//만약 정수의 최댓값이 아니면 거리가 존재하는 것
				if(graph[curNumber][connectVertexIndex] < Integer.MAX_VALUE) {
					
					//저장된 거리 값과 새로 구한 거리 값을 구함
					int storedDistance = vertexs.get(i).weight;
					int newDistance = curVertex.weight + graph[curNumber][connectVertexIndex];
					
					if(newDistance < storedDistance) {
						System.out.println("  ->  " + "d[" + vertexs.get(i).vertexName + "] = " + newDistance);
						vertexs.get(i).weight = newDistance;
					}
					
				}
				else 
					System.out.println();
			}
			
			//최솟값 삭제 (가장 끝값을 위로 올림)
			vertexs.set(1, vertexs.get(vertexs.size()-1));
			vertexs.remove(vertexs.size()-1);
			//맨 위로 올린 값을 기준으로 heapify
			minHeapify(vertexs, 1);
			
			count++;
			
			System.out.println();
		}

	}

	private static int findConnectVertex(List<Vertex> vertexs, char cur) {
		for(int i = 1; i < vertexs.size(); i++) {
			if(vertexs.get(i).vertexName == cur) {
				return vertexs.get(i).number;
			}
		}
		
		return 0;
	}

	static class Vertex{
		private char vertexName;
		private int number;
		private int weight;

		Vertex(char newName, int newNumber, int newWeight){
			this.vertexName = newName;
			this.number = newNumber;
			this.weight = newWeight;
		}
	}

}
