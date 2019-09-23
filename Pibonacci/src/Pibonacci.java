import java.util.Scanner;

public class Pibonacci {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("측정할 수를 입력하시오 : ");
	
		int input = scanner.nextInt();
		
		System.out.print("1. Recursion\n" 
				+ "2. Bottom-up\n" 
				+ "3.Recursive Squaring\n"
				+ "어떤 방법을 사용하시겠습니까?(종료하려면 목록을 제외한 번호를 입력하세요) ");
		
		int menu = scanner.nextInt();
		
		switch (menu) {
			case 1 :
				long result_recursively;
				
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0)
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime();
					result_recursively = pibonacci_recursively(i);
					long end = System.nanoTime();
					System.out.print("f(" + i + ") = "+ result_recursively + "                       ");
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0));
				}
				
				break;
			case 2 :
				long result_array;
				
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0)
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime();
					result_array = pibonacci_array(i);
					long end = System.nanoTime();
					System.out.print("f(" + i + ") = "+ result_array + "                       ");
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0));
				}
				
				break;
			case 3 :
				long result_recursive_squaring;
				
				for(int i = 0; i <= input; i++) {

					if(i%10 == 0)
						System.out.println("-------------------------------------------------------");

					long start = System.nanoTime();
					result_recursive_squaring = pibonacci_recursive_squaring(i);
					long end = System.nanoTime();
					System.out.print("f(" + i + ") = "+ result_recursive_squaring + "                       ");
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0));
				}
				
				break;
			default:
				return;
		}
		
		
	}
	
	private static long pibonacci_recursively(int input) {
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
	
	private static long pibonacci_array(int input) {
		
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
			
			for(int i = 2; i <= input; i++) {
				result[i] = result[i-1] + result[i-2];
			}
			
			return result[input];
		}
	}

	private static long pibonacci_recursive_squaring(int input) {
		
		long[][] A = {{1,1}, {1,0}};
		
		if (input < 2) {
			return input;
		}
		else {
			return pow(A,input-1)[0][0];
		}
	}

	private static long[][] pow(long[][] a, int input) {
		if(input == 1) return a;
		
		if(input % 2 == 0) { //짝수일 때
			return mul(pow(a, input/2) , pow(a, input/2));
		}
		else { //홀수일 때
			return mul( mul(pow(a, (input-1)/2), pow(a, (input-1)/2)), a);
		}
	}

	private static long[][] mul(long[][] a, long[][] b) {
		
		long[][] result = new long[2][2];
		
		result[0][0] = (a[0][0] * b[0][0]) + (a[0][1] * b[1][0]);
		result[0][1] = (a[0][0] * b[0][1]) + (a[0][1] * b[1][1]);
		result[1][0] = (a[1][0] * b[0][0]) + (a[1][1] * b[1][0]);
		result[1][1] = (a[1][0] * b[0][1]) + (a[1][1] * b[1][1]);
		
		return result;
		
	}

}
