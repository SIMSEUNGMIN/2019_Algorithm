import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Prim {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String input = "";

		// 읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data13_prim.txt";

		// 파일에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		// 줄바꿈 기준으로 데이터 배열로 정렬
		String[] inputArray = input.split("\\r?\\n");

		//정점 개수
		int vertexCount = inputArray[0].split(",").length;

		//간선들의 리스트를 생성
		List<Edge> edges = new LinkedList<Edge>();

		//간선용 이차원 배열
		int[][] weights = new int[vertexCount][vertexCount];

		//처음은 전부 Integer.maxValue로 초기화
		for(int i = 0; i < vertexCount; i++) {
			for(int j = 0; j < vertexCount; j++) {
				weights[i][j] = Integer.MAX_VALUE;
			}
		}

		//읽어온 데이터에서 점들과 점들의 무게를 찾아서 집어넣음
		for(int i = 1; i < inputArray.length; i++) {
			String[] temp = inputArray[i].split(",");

			int start = (temp[0].charAt(0) - 97);
			int dst = (temp[1].charAt(0) - 97);
			int weight = Integer.parseInt(temp[2]);

			weights[start][dst] = weight;
			weights[dst][start] = weight;
		}

		System.out.println("Prim's algorithm으로 계산한 결과는 다음과 같습니다.");

		Prim(edges, vertexCount, weights);

	}

	private static void Prim(List<Edge> edges, int vertexCount, int[][] weights) {
		int mstWeight = 0;

		List<Integer> selectedVertexs = new ArrayList<Integer>();

		//시작점을 먼저 넣어줌 (a를 시작점으로 잡음)
		selectedVertexs.add(0);

		//초기에 넣어준 값을 시작점으로 하는 간선들을 넣고 minHeap을 생성
		addEdge(edges, weights, vertexCount, 0);
		buildMinHeap(edges);

		//시작점에 대한 출력
		System.out.println("w< ,a> = 0");

		//edge에 들어있는 원소가 하나도 없을 때에 반복문을 종료하도록 함 
		//edge는 minHeap로 정렬됨
		while(selectedVertexs.size() < vertexCount) {
			//있다면 바로 출력
			System.out.println("w<" + (char)(edges.get(0).start + 97) + ","
					+ (char)(edges.get(0).end + 97) + "> = " + edges.get(0).weight);

			//mst 값 더해줌
			mstWeight += edges.get(0).weight;

			//이어지는 다른 vertex를 selectedVertex에 넣어줌
			selectedVertexs.add(edges.get(0).end);

			//새로 넣은 vertex에 관한 edge 추가
			addEdge(edges, weights, vertexCount, edges.get(0).end);

			//현재 간선들 중에서 시작점과 끝점 모두 selectedVertex안에 있을 경우 제거하는 함수
			removeEdge(edges, selectedVertexs);

			//새로 정렬
			buildMinHeap(edges);
		}

		System.out.println("w<MST> = " + mstWeight);
	}
	
	//사이클을 만드는 간선들은 삭제하는 함수
	private static void removeEdge(List<Edge> edges, List<Integer> selectedVertexs) {
		for(int i = 0; i < edges.size(); i++) {
			if(selectedVertexs.contains(edges.get(i).start)) {
				if(selectedVertexs.contains(edges.get(i).end)) {
					edges.remove(i);
					i--;
				}
			}
		}

	}

	//start점에 해당하는 간선들을 집어넣어주는 함수
	private static void addEdge(List<Edge> edges, int[][] weights, int vertexCount, int start) {
		for(int j = 0; j < vertexCount; j++) {
			//중복된 값이 있는지 없는지 확인
			if(weights[start][j] != Integer.MAX_VALUE && !isSameEdge(edges, start, j)) {
				edges.add(new Edge(start, j, weights[start][j]));
			}
		}

	}

	//간선 리스트 안에 중복 간선이 들어가지 않도록 조정
	private static boolean isSameEdge(List<Edge> edges, int start, int j) {
		for(int i = 0; i < edges.size(); i++) {
			if(edges.get(i).start == j) {
				if(edges.get(i).end == start) {
					return true;
				}
			}
		}

		return false;
	}

	private static void buildMinHeap(List<Edge> edges) {
		//자식을 가지는 마지막 노드부터 루트노드까지 순서대로 검사
		//현재 heapSize에는 사용하지 않는 0번째 노드가 포함되어있으므로  크기에서 -1을 해야함
		//전부 MaxHeap을 만들 수 있도록 검사
		for(int i = edges.size()/2; i >= 0; i--) {
			minHeapify(edges, i);
		}
	}

	private static void minHeapify(List<Edge> edges, int cur) {
		//최소 노드의 index
		int min = cur;

		//왼쪽 노드와 오른쪽 노드의 index
		int left = 0;
		int right = 0;

		//오른쪽 노드가 존재하는지 확인
		boolean isRight = false;

		//자식 노드가 존재하는지 확인
		//왼쪽 노드 오른쪽 노드 둘 다 존재
		if((cur*2)+1 < edges.size()) {
			left = cur*2;
			right = (cur*2)+1;
			isRight = true;
		}
		//왼쪽 노드만 있는지 노드가 없는지 확인 필요
		else {
			if((cur*2) < edges.size()) {
				left = cur*2;
			}
			else {
				//왼쪽노드마저 없으면 더 이상 볼 필요 없으므로 현재 함수 종료
				return;
			}
		}

		//현재노드의 값보다 왼쪽 자식노드의 값이 작다면 min의 값 변경
		if(edges.get(left).weight < edges.get(min).weight) {
			min = left;
		}

		//오른쪽 자식 노드가 존재하고 
		//현재 노드의 값보다 오른쪽 자식 노드의 값이 작다면 min값 변경
		if(isRight &&
				edges.get(right).weight < edges.get(min).weight) {
			min = right;
		}

		//cur값과 min의 값이 같지 않다면 자식노드를 확인할 필요가 있음
		//따라서 값의 위치를 바꾸고 최대크기인 자식 노드를 다시 minHeapify함
		if(min != cur) {
			Edge tmpMin = edges.get(min);
			edges.set(min, edges.get(cur));
			edges.set(cur, tmpMin);
			minHeapify(edges, min);
		}

	}

	static class Edge{
		private int start;
		private int end;
		private int weight;

		Edge(int newStart, int newEnd, int newWeight){
			this.start = newStart;
			this.end = newEnd;
			this.weight = newWeight;
		}
	}

}