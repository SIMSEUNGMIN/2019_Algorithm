import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		
		//읽어온 값으로 배열을 만듦(\n기준)
		String[] inputArray = input.split("\n");
		
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

