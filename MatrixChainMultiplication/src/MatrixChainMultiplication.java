import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixChainMultiplication {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String input = "";
		// 읽어올 파일 경로
		String filePath = "C:/Users/user/Desktop/data11_matrix_chain.txt";
		
		// 파일에서 읽어옴
		try (FileInputStream fStream = new FileInputStream(filePath);) {
			byte[] readByte = new byte[fStream.available()];
			while (fStream.read(readByte) != -1);
			fStream.close();
			input = new String(readByte);
		}
		
		//줄내림 기준으로 나눔
		String[] inputArray = input.split("\\r?\\n");
		
		// , 기준으로 나눠서 List에 저장
		List<Matrix> matrixArray = new ArrayList<>();
		
		matrixArray.add(new Matrix(0,0));
		
		for(int i = 0; i < inputArray.length; i++) {
			String[] temp = inputArray[i].split(",");
			matrixArray.add(i + 1, new Matrix(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
		}
		
		//함수 실행 -> 결과 테이블 생성
		int[][] resultTable = MatrixChainOrder(matrixArray);
		
		//테이블 출력
		for(int i = 1; i <= inputArray.length; i++) {
			for(int j = 1; j <= inputArray.length; j++) {
				System.out.print(resultTable[i][j] + "\t");		
			}
			System.out.println();
		}
	}
	
	
	private static int[][] MatrixChainOrder(List<Matrix> matrixArray) {
		// TODO Auto-generated method stub
		int tableSize = matrixArray.size() - 1;
		int[][] result = new int[tableSize + 1][tableSize + 1];
		
		for(int i = 1; i <= tableSize; i++)
			result[i][i] = 0;
		
		for(int l = 2; l <= tableSize; l++) {
			for(int i = 1; i <= tableSize - l + 1; i++) {
				int j = i + l - 1;
				result[i][j] = Integer.MAX_VALUE;
				
				for(int k = i; k <= j-1; k++) {
					int q = result[i][k] + result[k+1][j] + 
							(matrixArray.get(i).getRow() * matrixArray.get(k).getCol() * matrixArray.get(j).getCol());
					
					if(q < result[i][j]) {
						result[i][j] = q;
					}
				}
			}
		}
		
		return result;
	}


	static class Matrix{
		private int row;
		private int col;
		
		public Matrix(int givenRow, int givenCol) {
			this.row = givenRow;
			this.col = givenCol;
		}
		
		public void setRow(int newRow) {
			this.row = newRow;
		}
		
		public void setCol(int newCol) {
			this.col = newCol;
		}
		
		public int getRow() {
			return this.row;
		}
		
		public int getCol() {
			return this.col;
		}
	}
}
