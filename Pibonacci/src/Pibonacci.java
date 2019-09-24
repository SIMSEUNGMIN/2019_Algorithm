import java.util.Scanner;

public class Pibonacci {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("측정할 수를 입력하시오 : ");
	
		int input = scanner.nextInt();
		
		System.out.print("1. Recursion\n" 
				+ "2. Bottom-up\n" 
				+ "3. Recursive Squaring\n"
				+ "어떤 방법을 사용하시겠습니까?(종료하려면 목록을 제외한 번호를 입력하세요) ");
		
		int menu = scanner.nextInt();
		
		//menu 입력에 맞춰 switch문을 돌림
		switch (menu) {
			case 1 :
				long result_recursively;
				
				//f(0)부터 f(n)까지 recursion으로 피보나치를 구함
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0) //10개 단위로 끊는다
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime(); //시작 시간 측정
					result_recursively = pibonacci_recursively(i);
					long end = System.nanoTime(); //종료 시간 측정
					System.out.print("f(" + i + ") = "+ result_recursively + "                       "); //결과값 출력
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //소요 시간 출력
				}
				
				break;
			case 2 :
				long result_array;
				
				//f(0)부터 f(n)까지 bottom-up으로 피보나치를 구함
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0) //10개 단위로 끊는다
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime(); //시작 시간 측정
					result_array = pibonacci_array(i);
					long end = System.nanoTime(); //종료 시간 측정
					System.out.print("f(" + i + ") = "+ result_array + "                       "); //결과값 출력
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //소요 시간 출력
				}
				
				break;
			case 3 :
				long result_recursive_squaring;
				
				//f(0)부터 f(n)까지 recursive-squaring으로 피보나치를 구함
				for(int i = 0; i <= input; i++) {

					if(i%10 == 0) //10개 단위로 끊는다
						System.out.println("-------------------------------------------------------");

					long start = System.nanoTime(); //시작 시간 측정
					result_recursive_squaring = pibonacci_recursive_squaring(i);
					long end = System.nanoTime(); //종료 시간 측정
					System.out.print("f(" + i + ") = "+ result_recursive_squaring + "                       "); //결과값 출력
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //소요 시간 출력
				}
				
				break;
			default:
				return;
		}
		
		
	}
	
	//재귀로 피보나치를 구함
	private static long pibonacci_recursively(int input) {
		//input == 0일 떄 0을 출력
		//input == 1일 때 1을 출력
		//input이 그 외이면 재귀를 돌려 input-1값과 input-2값을 구하여 출력한다
	
		if(input == 0) {
			return 0;
		}
		else if (input == 1) {
			return 1;
		}
		else { 
			return pibonacci_recursively(input-1) * (pibonacci_recursively(input-2));
		}
	}
	
	//배열로 input까지의 값을 배열의 input-1, input-2의 값으로 계산 
	private static long pibonacci_array(int input) {
		//input == 0이면 0
		//input == 1이면 1
		//그 외의 값이 input인 경우 input값보다 1이 큰 배열을 선언
		//앞의 두 값을 사용하여 2,3,4...input값을 구해서 해당 값을 리턴함
		
		if(input == 0) {
			return 0;
		}
		else if(input == 1) {
			return 1;
		}
		else {
			long[] result = new long[input + 1];
			
			result[0] = 0;
			result[1] = 1;
			
			//앞의 두 값을 사용하여 2부터 배열을 채워나가기 시작
			for(int i = 2; i <= input; i++) {
				result[i] = result[i-1] + result[i-2];
			}
			
			return result[input];
		}
	}
	
	//powering number을 구하는 함수와 행렬의 곱을 구하는 함수를 이용하여
	//재귀를 통해 값을 피보나치 값을 구함
	private static long pibonacci_recursive_squaring(int input) {
		
		long[][] A = {{1,1}, {1,0}};
		
		//2보다 작은 경우 1이면 1이고 0이면 0이다
		if (input < 2) {
			return input;
		}
		else {
			//2 이상일 경우 A행렬을 n제곱한 다음 행렬의 (0,0)에 해당하는 값을 반환
			//A행렬을 n제곱해주는 함수가 pow
			return pow(A,input-1)[0][0];
		}
	}
	
	//입력된 행렬을 n 제곱하는 함수
	private static long[][] pow(long[][] a, int input) {
		//input == 1이면 원래 행렬과 같은 행렬이 나오게 됨
		if(input == 1) return a;
		
		if(input % 2 == 0) { //짝수일 때
			return mul(pow(a, input/2) , pow(a, input/2));
		}
		else { //홀수일 때
			return mul( mul(pow(a, (input-1)/2), pow(a, (input-1)/2)), a);
		}
	}
	
	//들어온 두 행렬을 곱함
	private static long[][] mul(long[][] a, long[][] b) {
		
		long[][] result = new long[2][2];
		
		//행렬 4개 원소의 값을 더하여 행렬에 넣어준 다음 반환한다
		result[0][0] = (a[0][0] * b[0][0]) + (a[0][1] * b[1][0]);
		result[0][1] = (a[0][0] * b[0][1]) + (a[0][1] * b[1][1]);
		result[1][0] = (a[1][0] * b[0][0]) + (a[1][1] * b[1][0]);
		result[1][1] = (a[1][0] * b[0][1]) + (a[1][1] * b[1][1]);
		
		return result;
		
	}

}
