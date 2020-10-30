package misc;

public class MyService {

	public String myService(Object o, String s) {
		return s + ": " + o.getClass().getSimpleName();
	}
}
