import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;

public class Huffman_encoding {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String input = "";

		//읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data13_huffman.txt";

		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}

		//빈도수 측정 해시맵 생성
		HashMap<Character, Integer> frequency = new HashMap<>();
		//최소힙 생성
		List<Node> nodes = new ArrayList<>();
		//테이블 생성 해시맵
		HashMap<Character, String> table = new HashMap<>();

		//노드와 빈도수 측정
		for(int i = 0; i < input.length(); i++) {
			if(frequency.containsKey(input.charAt(i))) {
				//이미 있는 값일 경우 빈도수를 1 증가
				char currentChar = input.charAt(i);
				frequency.put(currentChar, frequency.get(currentChar) + 1);
			}
			else {
				//키를 생성해서 넣어줌
				frequency.put(input.charAt(i), 1);
			}
		}

		//최소 힙에 저장
		for(Character key : frequency.keySet()) 
			nodes.add(new Node(key, frequency.get(key), null, null));
				
		//최소힙으로 정렬
		buildMinHeap(nodes);

		//huffman tree를 만드는 함수 실행
		Node resultNode = huffman(nodes);

		//파일에 씀 (table)
		try(FileWriter fw = new FileWriter("201702034_table.txt")){
			makeTable(table, resultNode, "");
			for(Character key : table.keySet()) {
				//System.out.println(key + " : " + table.get(key));
				fw.write(key + " : " + table.get(key) + "\n");
			}
		}

		//파일에 씀 (encoded code)
		try(FileWriter fw = new FileWriter("201702034_encoded.txt")){
			String allString = "";
			
			for(int i = 0; i < input.length(); i++)
				allString += table.get(input.charAt(i));

			//System.out.println(allString);
			fw.write(allString);
		}
	}
	
	//테이블을 만드는 함수
	private static void makeTable(HashMap<Character, String> table, Node resultNode, String code) {
		//양쪽 다 차일드 노드가 없으면 리프노드이므로 table에 저장
		if(resultNode.leftNode == null && resultNode.rightNode == null) {
			table.put(resultNode.data, code);
		}
		else {
			makeTable(table, resultNode.leftNode, code+"0");
			makeTable(table, resultNode.rightNode, code+"1");
		}
	}

	private static Node huffman(List<Node> nodes) {
		int count = nodes.size()-1;

		//트리를 만들기 시작
		for(int i = 0; i < count; i++) {
			Node left = nodes.get(0); //왼쪽
			//첫 값 빼버리고 다시 힙 정렬
			rearrangement(nodes);

			Node right = nodes.get(0); //오른쪽
			//사용했던 첫값 빼버리고 다시 힙 정렬
			rearrangement(nodes);

			//새로 넣을 노드
			Node newNode = new Node('p', (left.frequency + right.frequency), left, right);

			//새 노드를 넣어줌
			nodes.add(newNode);
			
			//새 노드를 기준으로 다시 정렬
			sortForInput(nodes);
		}

		//다 돌고 나면 하나의 노드만 남음, 그 노드를 출력
		return nodes.get(0);

	}

	private static void sortForInput(List<Node> nodes) {
		int cur = nodes.size()-1;

		//부모노드가 0이 아니고 현재 노드가 부모노드보다 작을 경우
		//서로의 위치를 바꾼 다음
		//바꾼 위치와 바꾼 위치에서의 부모를 비교
		//작지 않을 때까지 위의 과정 반복
		while(cur/2 != 0 &&
				nodes.get(cur).frequency < nodes.get(cur/2).frequency) {
			Node tmp = nodes.get(cur/2);
			nodes.set(cur/2, nodes.get(cur));
			nodes.set(cur, tmp);

			cur = cur/2;
		}
	}

	private static void rearrangement(List<Node> nodes) {
		nodes.set(0, nodes.get(nodes.size()-1));
		nodes.remove(nodes.size()-1);
		
		if(nodes.size() == 0) { //node안에 들어있는 사이즈가 0개면 minHeapify하지 않아도 됨
			return;
		}
		
		minHeapify(nodes, 0);
	}

	private static void buildMinHeap(List<Node> nodes) {
		//자식을 가지는 마지막 노드부터 루트노드까지 순서대로 검사
		//현재 heapSize에는 사용하지 않는 0번째 노드가 포함되어있으므로  크기에서 -1을 해야함
		//전부 MaxHeap을 만들 수 있도록 검사
		for(int i = nodes.size()/2; i >= 0; i--) {
			minHeapify(nodes, i);
		}
	}

	private static void minHeapify(List<Node> nodes, int cur) {
		//최소 노드의 index
		int min = cur;

		//왼쪽 노드와 오른쪽 노드의 index
		int left = 0;
		int right = 0;

		//오른쪽 노드가 존재하는지 확인
		boolean isRight = false;

		//자식 노드가 존재하는지 확인
		//왼쪽 노드 오른쪽 노드 둘 다 존재
		if((cur*2)+1 < nodes.size()) {
			left = cur*2;
			right = (cur*2)+1;
			isRight = true;
		}
		//왼쪽 노드만 있는지 노드가 없는지 확인 필요
		else {
			if((cur*2) < nodes.size()) {
				left = cur*2;
			}
			else {
				//왼쪽노드마저 없으면 더 이상 볼 필요 없으므로 현재 함수 종료
				return;
			}
		}

		//현재노드의 값보다 왼쪽 자식노드의 값이 작다면 min의 값 변경
		if(nodes.get(left).frequency < nodes.get(min).frequency) {
			min = left;
		}

		//오른쪽 자식 노드가 존재하고 
		//현재 노드의 값보다 오른쪽 자식 노드의 값이 작다면 min값 변경
		if(isRight &&
				nodes.get(right).frequency < nodes.get(min).frequency) {
			min = right;
		}

		//cur값과 min의 값이 같지 않다면 자식노드를 확인할 필요가 있음
		//따라서 값의 위치를 바꾸고 최대크기인 자식 노드를 다시 minHeapify함
		if(min != cur) {
			Node tmpMin = nodes.get(min);
			nodes.set(min, nodes.get(cur));
			nodes.set(cur, tmpMin);
			minHeapify(nodes, min);
		}

	}

	static class Node{
		private char data;
		private int frequency;
		private Node leftNode;
		private Node rightNode;

		Node(char newData, int newFrequency, Node newLeftNode, Node newRightNode){
			this.data = newData;
			this.frequency = newFrequency;
			this.leftNode = newLeftNode;
			this.rightNode = newRightNode;
		}
	}
}
