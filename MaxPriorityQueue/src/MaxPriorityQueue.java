import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MaxPriorityQueue {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<HeapNode> heap = new ArrayList<>();
		
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
		
		for(int i = 1; i < heap.size(); i++) {
			System.out.println("��ȣ : " + heap.get(i).getNumber() + " ��ȣ : " + heap.get(i).getName());
		}
		
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
