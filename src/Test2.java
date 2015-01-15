/**
 * @(#)Test2.java
 *
 *
 * @author
 * @version 1.00 2010/5/26
 */

public class Test2 {

    /**
     * Creates a new instance of <code>Test2</code>.
     */
    public Test2() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int a = 84;
		a >>>= 7;
		System.out.println(String.valueOf(a));

		System.out.println(Integer.parseInt("D4",16));
		System.out.println(212 & 127);
    }
}
