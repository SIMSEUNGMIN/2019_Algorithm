import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Huffman_decoding {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String tableString = "";
		String encodedString = "";

		//읽어올 파일 경로
		String filePath1 = "C:/Users/user/Desktop/data13_table.txt";
		String filePath2 = "C:/Users/user/Desktop/data13_encoded.txt";

		//파일에서 읽어옴
		try(FileInputStream fStream1 = new FileInputStream(filePath1);
				FileInputStream fStream2 = new FileInputStream(filePath2);){
			byte[] readByte1 = new byte[fStream1.available()];
			byte[] readByte2 = new byte[fStream2.available()];
			while(fStream1.read(readByte1) != -1);
			while(fStream2.read(readByte2) != -1);
			fStream1.close();
			fStream2.close();
			tableString = new String(readByte1);
			encodedString = new String(readByte2);
		}

		String[] tableArray = tableString.split("\\r?\\n");

		//테이블을 정리하는 해시맵 생성
		HashMap<Character, String> table = new HashMap<>();

		//해시맵에 값 넣기
		for(int i = 0; i < tableArray.length; i++) {
			String[] temp = tableArray[i].split(",");
			table.put(temp[0].charAt(0), temp[1]);
		}

		//주어진 파일의 입력으로 huffman tree를 만듦
		Node tree = makeHuffmanTree(table);


		//파일에 씀 (디코딩 결과)
		try(FileWriter fw = new FileWriter("201702034_decoded.txt")){
			String allString = decode(encodedString, tree);
			fw.write(allString);
		}

	}

	private static String decode(String encodedString, Node tree) {
		//반환할 문자열
		String returnString = "";
		
		Node start = tree; //시작은 루트부터
		
		//문자열 전체를 돌면서 리프 노드가 나올때만 출력
		for(int i = 0; i < encodedString.length(); i++) {
			//문자가 0인 경우 왼쪽 노드로 가야함
			if(encodedString.charAt(i) == '0')
				start = start.leftNode;
			else start = start.rightNode; //1인 경우 오른쪽 노드로 가야함
			
			//노드로 움직이고 나서 이 노드가 리프 노드인지 확인
			if(start.leftNode == null && start.rightNode == null) {
				//루프 노드가 맞으면 반환할 문자열에 추가하고 다시 처음부터 돎
				returnString += start.data;
				start = tree;
			}
		}
		
		return returnString;
	}

	private static Node makeHuffmanTree(HashMap<Character, String> table) {
		//table의 원소 개수만큼 돌면서 허프만 트리를 만들어감
		
		//루트 노드 만듦
		Node root = new Node('r', null, null);
		
		//전체 set을 돌면서 key에 해당하는 노드 위치 찾음
		for(Character key : table.keySet()) {
			Node cur = root; //시작은 항상 root부터

			String code = table.get(key);
			
			//key에 해당하는 code를 돌면서 자신의 노드 위치를 찾아감
			for(int i = 0; i < code.length(); i++) {

				//문자의 값이 0인 경우
				if(code.charAt(i) == '0') {
					//왼쪽으로 내려가야 함
					if(cur.leftNode == null) // 없으면 만들어주고 내려감
						cur.leftNode = new Node('l', null, null);
					cur = cur.leftNode;
				}
				else { //문자의 값이 1인 경우
					//오른쪽으로 내려가야 함
					if(cur.rightNode == null) //없으면 만들어주고 내려감
						cur.rightNode = new Node('r', null, null);
					cur = cur.rightNode;
				}

			}

			//다 돌고 나면 현재 key가 들어가야 할 위치 나옴
			cur.data = key;
		}

		return root;
	}

	static class Node{
		private char data;
		private Node leftNode;
		private Node rightNode;

		Node(char newData, Node newLeftNode, Node newRightNode){
			this.data = newData;
			this.leftNode = newLeftNode;
			this.rightNode = newRightNode;
		}
	}

}
