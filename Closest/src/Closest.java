import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		//읽어온 값으로 배열을 만듦(\n기준)
		String[] inputArray = input.split("\n");
		
		//포인터를 저장할 List선언
		List<Point> array = new ArrayList<>();
		
		//좌표값이 들어간 array
		for(int i = 0; i < inputArray.length; i++) {
			String[] tmp = inputArray[i].split(",");
			array.add(new Point(Float.parseFloat(tmp[0]), Float.parseFloat(tmp[1])));
		}
		
		//X좌표 기준으로 오름차순 정렬
		XSort(array);
		
	}

	private static void XSort(List<Point> array) {
		
		//두번째부터 시작
		for(int cur = 1; cur < array.size(); cur++) {
			Point curKey = array.get(cur);
			
			//현재 cur의 원소 이전 원소를 전부 돌면서 적절한 위치를 찾음
			for(int pre = cur-1; pre >= 0; pre--) {
				
				//cur의 원소보다 pre의 원소가 큰 경우 위치 변경
				if(array.get(pre).getX() > curKey.getX()) {
					array.set(pre+1, array.get(pre));
					array.set(pre, curKey);
				}
			}
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

