import java.util.Scanner;

public class Pibonacci {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("������ ���� �Է��Ͻÿ� : ");
	
		int input = scanner.nextInt();
		
		System.out.print("1. Recursion\n" 
				+ "2. Bottom-up\n" 
				+ "3. Recursive Squaring\n"
				+ "� ����� ����Ͻðڽ��ϱ�?(�����Ϸ��� ����� ������ ��ȣ�� �Է��ϼ���) ");
		
		int menu = scanner.nextInt();
		
		//menu �Է¿� ���� switch���� ����
		switch (menu) {
			case 1 :
				long result_recursively;
				
				//f(0)���� f(n)���� recursion���� �Ǻ���ġ�� ����
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0) //10�� ������ ���´�
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime(); //���� �ð� ����
					result_recursively = pibonacci_recursively(i);
					long end = System.nanoTime(); //���� �ð� ����
					System.out.print("f(" + i + ") = "+ result_recursively + "                       "); //����� ���
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //�ҿ� �ð� ���
				}
				
				break;
			case 2 :
				long result_array;
				
				//f(0)���� f(n)���� bottom-up���� �Ǻ���ġ�� ����
				for(int i = 0; i <= input; i++) {
					
					if(i%10 == 0) //10�� ������ ���´�
						System.out.println("-------------------------------------------------------");
					
					long start = System.nanoTime(); //���� �ð� ����
					result_array = pibonacci_array(i);
					long end = System.nanoTime(); //���� �ð� ����
					System.out.print("f(" + i + ") = "+ result_array + "                       "); //����� ���
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //�ҿ� �ð� ���
				}
				
				break;
			case 3 :
				long result_recursive_squaring;
				
				//f(0)���� f(n)���� recursive-squaring���� �Ǻ���ġ�� ����
				for(int i = 0; i <= input; i++) {

					if(i%10 == 0) //10�� ������ ���´�
						System.out.println("-------------------------------------------------------");

					long start = System.nanoTime(); //���� �ð� ����
					result_recursive_squaring = pibonacci_recursive_squaring(i);
					long end = System.nanoTime(); //���� �ð� ����
					System.out.print("f(" + i + ") = "+ result_recursive_squaring + "                       "); //����� ���
					System.out.format("%.9f sec\n", ((end-start)/1000000000.0)); //�ҿ� �ð� ���
				}
				
				break;
			default:
				return;
		}
		
		
	}
	
	//��ͷ� �Ǻ���ġ�� ����
	private static long pibonacci_recursively(int input) {
		//input == 0�� �� 0�� ���
		//input == 1�� �� 1�� ���
		//input�� �� ���̸� ��͸� ���� input-1���� input-2���� ���Ͽ� ����Ѵ�
	
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
	
	//�迭�� input������ ���� �迭�� input-1, input-2�� ������ ��� 
	private static long pibonacci_array(int input) {
		//input == 0�̸� 0
		//input == 1�̸� 1
		//�� ���� ���� input�� ��� input������ 1�� ū �迭�� ����
		//���� �� ���� ����Ͽ� 2,3,4...input���� ���ؼ� �ش� ���� ������
		
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
			
			//���� �� ���� ����Ͽ� 2���� �迭�� ä�������� ����
			for(int i = 2; i <= input; i++) {
				result[i] = result[i-1] + result[i-2];
			}
			
			return result[input];
		}
	}
	
	//powering number�� ���ϴ� �Լ��� ����� ���� ���ϴ� �Լ��� �̿��Ͽ�
	//��͸� ���� ���� �Ǻ���ġ ���� ����
	private static long pibonacci_recursive_squaring(int input) {
		
		long[][] A = {{1,1}, {1,0}};
		
		//2���� ���� ��� 1�̸� 1�̰� 0�̸� 0�̴�
		if (input < 2) {
			return input;
		}
		else {
			//2 �̻��� ��� A����� n������ ���� ����� (0,0)�� �ش��ϴ� ���� ��ȯ
			//A����� n�������ִ� �Լ��� pow
			return pow(A,input-1)[0][0];
		}
	}
	
	//�Էµ� ����� n �����ϴ� �Լ�
	private static long[][] pow(long[][] a, int input) {
		//input == 1�̸� ���� ��İ� ���� ����� ������ ��
		if(input == 1) return a;
		
		if(input % 2 == 0) { //¦���� ��
			return mul(pow(a, input/2) , pow(a, input/2));
		}
		else { //Ȧ���� ��
			return mul( mul(pow(a, (input-1)/2), pow(a, (input-1)/2)), a);
		}
	}
	
	//���� �� ����� ����
	private static long[][] mul(long[][] a, long[][] b) {
		
		long[][] result = new long[2][2];
		
		//��� 4�� ������ ���� ���Ͽ� ��Ŀ� �־��� ���� ��ȯ�Ѵ�
		result[0][0] = (a[0][0] * b[0][0]) + (a[0][1] * b[1][0]);
		result[0][1] = (a[0][0] * b[0][1]) + (a[0][1] * b[1][1]);
		result[1][0] = (a[1][0] * b[0][0]) + (a[1][1] * b[1][0]);
		result[1][1] = (a[1][0] * b[0][1]) + (a[1][1] * b[1][1]);
		
		return result;
		
	}

}
