import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxPriorityQueue {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//원소를 담을 arrayList
		ArrayList<HeapNode> heap = new ArrayList<>();
		
		//메뉴에 대한 번호
		int select = 0;
		
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
		
		//현재 힢의 모습을 출력
		showHeap(heap);
		
		//메뉴 보여주기
		showMenu();
		select = scanner.nextInt();
		
		while(select != 6) {
			
			switch(select) {
			case 1: //insert
				insert(heap);
				break;
			case 2: //max
				printMax(heap);
				break;
			case 3: //extract_max
				extractMax(heap);
				break;
			case 4: //increase_key
				increaseKey(heap);
				break;
			case 5: //delete
				delete(heap);
				break;
			default:
				break;
			}
			
			showHeap(heap);
			showMenu();
			select = scanner.nextInt();
		}
		
		
	}

	private static void insert(ArrayList<HeapNode> heap) {
		System.out.print("새로 추가할 값을 입력하시오 : ");
		int newNumber = scanner.nextInt();
		scanner.nextLine();
		System.out.print("새로 추가할 값의 이름을 입력하시오 : ");
		String newName = scanner.nextLine();
		
		HeapNode newNode = new HeapNode(newNumber, newName);
		
		//가장 마지막에 새로운 노드를 삽입
		heap.add(newNode);
		
		//현재 노드의 index
		int cur = heap.size()-1;
		
		//부모노드가 0이 아니고 현재 노드가 부모노드보다 클 경우
		//서로의 위치를 바꾼 다음
		//바꾼 위치와 바꾼 위치에서의 부모를 비교
		//크지 않을 때까지 위의 과정 반복
		while(cur/2 != 0 &&
				heap.get(cur).getNumber() > heap.get(cur/2).getNumber()) {
			HeapNode tmp = heap.get(cur/2);
			heap.set(cur/2, heap.get(cur));
			heap.set(cur, tmp);
			
			cur = cur/2;
		}
		
	}

	private static void printMax(ArrayList<HeapNode> heap) {
		//힢에서 제일 최대값을 출력(삭제 x)
		//index 0번은 사용하지 않음
		System.out.println("번호 : " + heap.get(1).getNumber() + ", 이름 : " + heap.get(1).getName());
	}
	
	private static void extractMax(ArrayList<HeapNode> heap) {
		//root노드를 꺼낸 다음 맨 마지막에 있는 노드를 루트 노드로 옮김
		//그리고 루트를 기준으로 maxHeapify수행
		System.out.println("추출한 노드 - " + heap.get(1).getNumber() + "\t" + heap.get(1).getName());
		
		//루트로 가장 마지막 노드 이동
		heap.set(1, heap.get(heap.size()-1));
		//맨 마지막에 있는 노드 삭제
		heap.remove(heap.size()-1);
		
		//루트를 기준으로 maxHeapify수행
		maxHeapify(heap, 1);
	}
	
	private static void increaseKey(ArrayList<HeapNode> heap) {
		System.out.print("값을 바꾸기 원하는 index의 번호를 입력하시오 : ");
		int index = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("현재 키 값: " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		System.out.print("변경할 키의 값을 입력하시오(변경 전 키값보다는 커야한다) : ");
		int value = scanner.nextInt();
		scanner.nextLine();
		
		//해당하는 index의 키 값을 변경
		heap.get(index).setNumber(value);
		
		System.out.println("변경된 키 값 : " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		//값이 변경되었기 때문에 원래 자리를 찾아줘야 함
		//현재 노드의 index
		int cur = index;

		//부모노드가 0이 아니고 현재 노드가 부모노드보다 클 경우
		//서로의 위치를 바꾼 다음
		//바꾼 위치와 바꾼 위치에서의 부모를 비교
		//크지 않을 때까지 위의 과정 반복
		while(cur/2 != 0 &&
				heap.get(cur).getNumber() > heap.get(cur/2).getNumber()) {
			HeapNode tmp = heap.get(cur/2);
			heap.set(cur/2, heap.get(cur));
			heap.set(cur, tmp);

			cur = cur/2;
		}
	}
	
	private static void delete(ArrayList<HeapNode> heap) {
		System.out.print("삭제할 노드의 index값을 입력하시오 : ");
		int index = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("삭제할 키 값 : " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		//삭제할 index위치에 마지막 노드를 넣음
		heap.set(index, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		
		//값을 삭제한 index를 기준으로 maxHeapify실행
		maxHeapify(heap, index);
		
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
	
	private static void showHeap(ArrayList<HeapNode> heap) {
		System.out.println("**********현재 우선 순위 큐에 저장된 작업 대기 목록은 다음과 같습니다.**********");

		for(int i = 1; i < heap.size(); i++)
			System.out.println(heap.get(i).getNumber() + "\t" + heap.get(i).getName());
	}
	
	private static void showMenu() {
		System.out.println("----------------------------------------------------");
		System.out.println("1. 작업 추가\t 2. 최대값\t 3. 최대 우선순위 작업 처리\n"
				+ "4. 원소 키값 증가\t 5. 작업 제거\t 6. 종료");
		System.out.println("----------------------------------------------------");
		System.out.print("원하시는 작업을 선택하시오. : ");	
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
