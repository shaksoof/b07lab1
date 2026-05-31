import java.io.File;

public class Driver {
    public static void main(String [] args) {
        System.out.println("Reading polynomial data from data.txt ...");
        File f = new File("data.txt");
        Polynomial p = new Polynomial(f);
        System.out.println("Evaluating polynomial at x = 2:");
        System.out.println(p.evaluate(2));
        double[] coeffs = {1,2,4};
        int [] exps = {0,1,2};
        Polynomial p2 = new Polynomial(coeffs,exps);
        Polynomial p3 = p.multiply(p2);
        p3.saveToFile("multiplied_data.txt");
    }
}