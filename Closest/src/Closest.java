import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Closest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		String input = "";
		
		String filePath = "C:/Users/user/Desktop/data04_closest.txt";
		
		try(FileInputStream fStream = new FileInputStream(filePath);){
			byte[] readByte = new byte[fStream.available()];
			while(fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//�о�� ������ �迭�� ����(\n����)
		String[] inputArray = input.split("\n");
		
		//�����͸� ������ List����
		List<Point> array = new LinkedList<>();
		
		//��ǥ���� �� array
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array.add(new Point(Float.parseFloat(tmp[0]), Float.parseFloat(tmp[1])));
		}
	}

}

class Point{
	private float x;
	private float y;
	
	public Point(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}
	
	public void setX(float givenX) {
		this.x = givenX;
	}
	
	public void setY(float givenY) {
		this.y = givenY;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
}

