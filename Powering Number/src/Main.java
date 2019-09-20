import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);
		
		int a = scanner.nextInt();
		
		int n = scanner.nextInt();
		
		System.out.println(poweringNumber(a,n));
	}
	
	public static int poweringNumber(int a, int n) {
		if(n == 0) {
			return 1;
		}
		else if(n == 1) {
			return a;
		}
		else if(n%2 != 0) {
			return poweringNumber(a, (n-1)/2) * poweringNumber(a, (n-1)/2) * a;
		}
		else if(n%2 == 0) {
			return poweringNumber(a, n/2) * poweringNumber(a, n/2);
		}
		
		return -1;
	}
}
