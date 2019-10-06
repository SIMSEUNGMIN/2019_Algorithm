import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxPriorityQueue {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//���Ҹ� ���� arrayList
		ArrayList<HeapNode> heap = new ArrayList<>();
		
		//�޴��� ���� ��ȣ
		int select = 0;
		
		String input = "";
		
		//������ �о�� ���
		String filePath = "C:/Users/user/Desktop/data05.txt";
		
		//���Ͽ��� �о��
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//�о�� ���� ��ȣ�� �̸������� �и�
		String[] tmpArray = input.split("\\r?\\n");
		
		//HaepNode�� �־ heap�迭�� ����
		for(int i = 0; i < tmpArray.length; i++) {
			String[] tmp = tmpArray[i].split(", ");
			heap.add(new HeapNode(Integer.parseInt(tmp[0]), tmp[1]));
		}
		
		//index 0�� ���� ������� ���� ���̱� ������ 0�� ���� ���� ���� �������
		heap.add(0, new HeapNode(-1, "��� �� ��"));
		
		//�Է°��� ���� ������ heap �迭�� MaxHeap���� ����
		buildMaxHeap(heap);
		
		//���� �Q�� ����� ���
		showHeap(heap);
		
		//�޴� �����ֱ�
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
		System.out.print("���� �߰��� ���� �Է��Ͻÿ� : ");
		int newNumber = scanner.nextInt();
		scanner.nextLine();
		System.out.print("���� �߰��� ���� �̸��� �Է��Ͻÿ� : ");
		String newName = scanner.nextLine();
		
		HeapNode newNode = new HeapNode(newNumber, newName);
		
		//���� �������� ���ο� ��带 ����
		heap.add(newNode);
		
		//���� ����� index
		int cur = heap.size()-1;
		
		//�θ��尡 0�� �ƴϰ� ���� ��尡 �θ��庸�� Ŭ ���
		//������ ��ġ�� �ٲ� ����
		//�ٲ� ��ġ�� �ٲ� ��ġ������ �θ� ��
		//ũ�� ���� ������ ���� ���� �ݺ�
		while(cur/2 != 0 &&
				heap.get(cur).getNumber() > heap.get(cur/2).getNumber()) {
			HeapNode tmp = heap.get(cur/2);
			heap.set(cur/2, heap.get(cur));
			heap.set(cur, tmp);
			
			cur = cur/2;
		}
		
	}

	private static void printMax(ArrayList<HeapNode> heap) {
		//�Q���� ���� �ִ밪�� ���(���� x)
		//index 0���� ������� ����
		System.out.println("��ȣ : " + heap.get(1).getNumber() + ", �̸� : " + heap.get(1).getName());
	}
	
	private static void extractMax(ArrayList<HeapNode> heap) {
		//root��带 ���� ���� �� �������� �ִ� ��带 ��Ʈ ���� �ű�
		//�׸��� ��Ʈ�� �������� maxHeapify����
		System.out.println("������ ��� - " + heap.get(1).getNumber() + "\t" + heap.get(1).getName());
		
		//��Ʈ�� ���� ������ ��� �̵�
		heap.set(1, heap.get(heap.size()-1));
		//�� �������� �ִ� ��� ����
		heap.remove(heap.size()-1);
		
		//��Ʈ�� �������� maxHeapify����
		maxHeapify(heap, 1);
	}
	
	private static void increaseKey(ArrayList<HeapNode> heap) {
		System.out.print("���� �ٲٱ� ���ϴ� index�� ��ȣ�� �Է��Ͻÿ� : ");
		int index = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("���� Ű ��: " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		System.out.print("������ Ű�� ���� �Է��Ͻÿ�(���� �� Ű�����ٴ� Ŀ���Ѵ�) : ");
		int value = scanner.nextInt();
		scanner.nextLine();
		
		//�ش��ϴ� index�� Ű ���� ����
		heap.get(index).setNumber(value);
		
		System.out.println("����� Ű �� : " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		//���� ����Ǿ��� ������ ���� �ڸ��� ã����� ��
		//���� ����� index
		int cur = index;

		//�θ��尡 0�� �ƴϰ� ���� ��尡 �θ��庸�� Ŭ ���
		//������ ��ġ�� �ٲ� ����
		//�ٲ� ��ġ�� �ٲ� ��ġ������ �θ� ��
		//ũ�� ���� ������ ���� ���� �ݺ�
		while(cur/2 != 0 &&
				heap.get(cur).getNumber() > heap.get(cur/2).getNumber()) {
			HeapNode tmp = heap.get(cur/2);
			heap.set(cur/2, heap.get(cur));
			heap.set(cur, tmp);

			cur = cur/2;
		}
	}
	
	private static void delete(ArrayList<HeapNode> heap) {
		System.out.print("������ ����� index���� �Է��Ͻÿ� : ");
		int index = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("������ Ű �� : " + heap.get(index).getNumber() + "\t" + heap.get(index).getName());
		
		//������ index��ġ�� ������ ��带 ����
		heap.set(index, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		
		//���� ������ index�� �������� maxHeapify����
		maxHeapify(heap, index);
		
	}

	private static void buildMaxHeap(ArrayList<HeapNode> heap) {
		//�ڽ��� ������ ������ ������ ��Ʈ������ ������� �˻�
		//���� heapSize���� ������� �ʴ� 0��° ��尡 ���ԵǾ������Ƿ�  ũ�⿡�� -1�� �ؾ���
		//���� MaxHeap�� ���� �� �ֵ��� �˻�
		for(int i = (heap.size()-1)/2; i >= 1; i--) {
			maxHeapify(heap, i);
		}
		
	}

	private static void maxHeapify(ArrayList<HeapNode> heap, int cur) {
		
		//�ִ� ����� index
		int max = cur;
		
		//���� ���� ������ ����� index
		int left = 0;
		int right = 0;

		//������ ��尡 �����ϴ��� Ȯ��
		boolean isRight = false;
		
		//�ڽ� ��尡 �����ϴ��� Ȯ��
		//���� ��� ������ ��� �� �� ����
		if((cur*2)+1 < heap.size()) {
			left = cur*2;
			right = (cur*2)+1;
			isRight = true;
		}
		//���� ��常 �ִ��� ��尡 ������ Ȯ�� �ʿ�
		else {
			if((cur*2) < heap.size()) {
				left = cur*2;
			}
			else {
				//���ʳ�帶�� ������ �� �̻� �� �ʿ� �����Ƿ� ���� �Լ� ����
				return;
			}
		}
		
		//�������� ������ ���� �ڽĳ���� ���� ũ�ٸ� max�� �� ����
		if(heap.get(left).getNumber() > heap.get(max).getNumber()) {
			max = left;
		}
		
		//������ �ڽ� ��尡 �����ϰ� 
		//���� ����� ������ ������ �ڽ� ����� ���� ũ�ٸ� max�� ����
		if(isRight &&
				heap.get(right).getNumber() > heap.get(max).getNumber()) {
			max = right;
		}
		
		//cur���� max�� ���� ���� �ʴٸ� �ڽĳ�带 Ȯ���� �ʿ䰡 ����
		//���� ���� ��ġ�� �ٲٰ� �ִ�ũ���� �ڽ� ��带 �ٽ� maxHeapify��
		if(max != cur) {
			HeapNode tmpMax = heap.get(max);
			heap.set(max, heap.get(cur));
			heap.set(cur, tmpMax);
			maxHeapify(heap, max);
		}
		
	}
	
	private static void showHeap(ArrayList<HeapNode> heap) {
		System.out.println("**********���� �켱 ���� ť�� ����� �۾� ��� ����� ������ �����ϴ�.**********");

		for(int i = 1; i < heap.size(); i++)
			System.out.println(heap.get(i).getNumber() + "\t" + heap.get(i).getName());
	}
	
	private static void showMenu() {
		System.out.println("----------------------------------------------------");
		System.out.println("1. �۾� �߰�\t 2. �ִ밪\t 3. �ִ� �켱���� �۾� ó��\n"
				+ "4. ���� Ű�� ����\t 5. �۾� ����\t 6. ����");
		System.out.println("----------------------------------------------------");
		System.out.print("���Ͻô� �۾��� �����Ͻÿ�. : ");	
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
