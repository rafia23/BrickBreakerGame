//Player.java

package ProjectCode;

public class Player {

private static String name;
	
	public Player(String s) {
		name = s;
	}
	
	public static void setName(String s) {
		name = s;
	}
	
	public static String getName() {
		return name;
	}
}


