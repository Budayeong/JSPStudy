package test;

public class HelloJava {
//	static -> 클래스명으로 호출가능
	public static int myFn() {
		int sum =0;
		for(int i=1 ; i<=10 ; i++) {
			sum+=i;
		}
		return sum;
	}
	
//	일반 멤버 -> 인스턴스 생성 후 호출해야함
	public int myFn(int s, int e) {
		int sum =0;
		for(int i=1 ; i<=e ; i++) {
			sum+=i;
		}
		return sum;
	}
	
	
	public static void main(String[] args) {
		System.out.println("hello world~");
	}

}
