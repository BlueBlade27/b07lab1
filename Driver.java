import java.io.*;

public class Driver {
    public static void main(String[] args) {
        // Test 1: Default Constructor
        Polynomial p = new Polynomial();
        System.out.println("Empty Polynomial evaluate at 3: " + p.evaluate(3));
        System.out.println("Empty Polynomial: " + p);

        // Test 2: Dense Constructor
        double[] c1 = {6, 0, 0, 5}; // 6 + 5x^3
        Polynomial p1 = new Polynomial(c1);
        System.out.println("p1: " + p1);

        double[] c2 = {0, -2, 0, 0, -9}; // -2x + -9x^4
        Polynomial p2 = new Polynomial(c2);
        System.out.println("p2: " + p2);

        // Test 3: Addition
        Polynomial s = p1.add(p2);
        System.out.println("s = p1 + p2: " + s);
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // Test 4: Root Checking
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        // Test 5: Multiplication
        Polynomial m = p1.multiply(p2);
        System.out.println("m = p1 * p2: " + m);
        System.out.println("m(1.5) = " + m.evaluate(1.5));

        // Test 6: Saving to File
        String filename = "poly_output.txt";
        m.saveToFile(filename);
        System.out.println("Saved polynomial m to file: " + filename);

        // Test 7: File Constructor
	try {
    		Polynomial fromFile = new Polynomial(new File(filename));
    		System.out.println("Polynomial loaded from file: " + fromFile);
    		System.out.println("fromFile(1.5) = " + fromFile.evaluate(1.5));
	} catch (IOException e) {
    		System.out.println("Error reading from file: " + e.getMessage());
	}

    }
}
