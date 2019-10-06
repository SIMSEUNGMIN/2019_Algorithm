import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MaxPriorityQueue {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<HeapNode> heap = new ArrayList<>();
		
		String input = "";
		
		//파일을 읽어올 경로
		String filePath = "C:/Users/user/Desktop/data05.txt";
		
		//파일에서 읽어옴
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//읽어온 값을 번호와 이름쌍으로 분리
		String[] tmpArray = input.split("\\r?\\n");
		
		//HaepNode에 넣어서 heap배열에 넣음
		for(int i = 0; i < tmpArray.length; i++) {
			String[] tmp = tmpArray[i].split(", ");
			heap.add(new HeapNode(Integer.parseInt(tmp[0]), tmp[1]));
		}
		
		//index 0번 값은 사용하지 않을 것이기 때문에 0에 쓸모 없는 값을 집어넣음
		heap.add(0, new HeapNode(-1, "사용 안 함"));
		
		//입력값에 의해 생성된 heap 배열을 MaxHeap으로 만듦
		buildMaxHeap(heap);
		
		for(int i = 1; i < heap.size(); i++) {
			System.out.println("번호 : " + heap.get(i).getNumber() + " 번호 : " + heap.get(i).getName());
		}
		
	}

	private static void buildMaxHeap(ArrayList<HeapNode> heap) {
		//자식을 가지는 마지막 노드부터 루트노드까지 순서대로 검사
		//현재 heapSize에는 사용하지 않는 0번째 노드가 포함되어있으므로  크기에서 -1을 해야함
		//전부 MaxHeap을 만들 수 있도록 검사
		for(int i = (heap.size()-1)/2; i >= 1; i--) {
			maxHeapify(heap, i);
		}
		
	}

	private static void maxHeapify(ArrayList<HeapNode> heap, int cur) {
		
		//최대 노드의 index
		int max = cur;
		
		//왼쪽 노드와 오른쪽 노드의 index
		int left = 0;
		int right = 0;

		//오른쪽 노드가 존재하는지 확인
		boolean isRight = false;
		
		//자식 노드가 존재하는지 확인
		//왼쪽 노드 오른쪽 노드 둘 다 존재
		if((cur*2)+1 < heap.size()) {
			left = cur*2;
			right = (cur*2)+1;
			isRight = true;
		}
		//왼쪽 노드만 있는지 노드가 없는지 확인 필요
		else {
			if((cur*2) < heap.size()) {
				left = cur*2;
			}
			else {
				//왼쪽노드마저 없으면 더 이상 볼 필요 없으므로 현재 함수 종료
				return;
			}
		}
		
		//현재노드의 값보다 왼쪽 자식노드의 값이 크다면 max의 값 변경
		if(heap.get(left).getNumber() > heap.get(max).getNumber()) {
			max = left;
		}
		
		//오른쪽 자식 노드가 존재하고 
		//현재 노드의 값보다 오른쪽 자식 노드의 값이 크다면 max값 변경
		if(isRight &&
				heap.get(right).getNumber() > heap.get(max).getNumber()) {
			max = right;
		}
		
		//cur값과 max의 값이 같지 않다면 자식노드를 확인할 필요가 있음
		//따라서 값의 위치를 바꾸고 최대크기인 자식 노드를 다시 maxHeapify함
		if(max != cur) {
			HeapNode tmpMax = heap.get(max);
			heap.set(max, heap.get(cur));
			heap.set(cur, tmpMax);
			maxHeapify(heap, max);
		}
		
	}

}

class HeapNode {
	private int number;
	private String name;
	
	public HeapNode(int givenNumber, String givenName) {
		this.number = givenNumber;
		this.name = givenName;
	}
	
	public void setNumber(int newNumber) {
		this.number = newNumber;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String getName() {
		return this.name;
	}
}
