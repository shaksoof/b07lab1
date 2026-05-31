import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class Polynomial{
    double[] coefficients;
    int[] exponents;

    public Polynomial(){
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }
    public Polynomial(double[] coeffs, int[] exponents){
        this.coefficients = coeffs;
        this.exponents = exponents;
    }
    public Polynomial(File f){
        try {
            Scanner scanner = new Scanner(f);
            if (!scanner.hasNextLine()){
                this.coefficients = new double[]{0};
                this.exponents = new int[]{0};
                scanner.close();
                return;
            }
            String line = scanner.nextLine();
            scanner.close();
            line = line.replace("-", "+-");
            String[] terms = line.split("\\+");
            // Count valid terms (in case the string started with a negative, the first split might be empty)
            int validTerms = 0;
            for (int i = 0; i < terms.length; i++) {
                if (!terms[i].isEmpty()) {
                    validTerms++;
                }
            }

            // Initialize our arrays
            this.coefficients = new double[validTerms];
            this.exponents = new int[validTerms];
            
            int index = 0;
            for (int i =0; i<terms.length; i++){
                if (terms[i].isEmpty()) continue;
                String[] term = terms[i].split("x");
                if (term.length == 1){ //coefficient
                    coefficients[index] = Double.parseDouble(term[0]);
                    exponents[index] = 0;
                }
                else{ //format 3x2 etc
                    coefficients[index] = Double.parseDouble(term[0]);
                    exponents[index] = Integer.parseInt(term[1]);
                }
                index++;
            }
            
        } catch (Exception e) {
            this.coefficients = new double[]{0};
            this.exponents = new int[]{0};
        }
        
    }
    public void saveToFile(String fname){
        try {
            PrintWriter pw = new PrintWriter(new File(fname));
            StringBuilder sb = new StringBuilder();   
            for (int i = 0; i < this.coefficients.length; i++) {
                double coeff = this.coefficients[i];
                int exp = this.exponents[i];
                if (i > 0 && coeff > 0) {
                    sb.append("+");
                }

                if (coeff == (long) coeff) {
                    sb.append((long) coeff);
                } else {
                    sb.append(coeff);
                }

                if (exp != 0) {
                    sb.append("x").append(exp);
                }
            }

            // Write the fully constructed string to the file and save it
            pw.print(sb.toString());
            pw.close();
        
        } catch (Exception e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
    
    public double evaluate(double num){
        double ans = 0;
        for (int i = 0; i<this.coefficients.length; i++){
            ans+=(this.coefficients[i]*Math.pow(num,exponents[i]));
            }
        return ans;
    }

    public Polynomial add(Polynomial p) {
        int maxPossibleLength = this.coefficients.length + p.coefficients.length;
        double[] tempCoeffs = new double[maxPossibleLength];
        int[] tempExps = new int[maxPossibleLength];
        int currentSize = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            tempCoeffs[currentSize] = this.coefficients[i];
            tempExps[currentSize] = this.exponents[i];
            currentSize++;
        }

        for (int i = 0; i < p.coefficients.length; i++) {
            boolean exponentFound = false;
            

            for (int j = 0; j < currentSize; j++) {
                if (tempExps[j] == p.exponents[i]) {
                    tempCoeffs[j] += p.coefficients[i]; // Add coefficients together
                    exponentFound = true;
                    break;
                }
            }
            
            if (!exponentFound) {
                tempCoeffs[currentSize] = p.coefficients[i];
                tempExps[currentSize] = p.exponents[i];
                currentSize++;
            }
        }
        int finalSize = 0;
        for (int i = 0; i < currentSize; i++) {
            if (tempCoeffs[i] != 0.0) {
                finalSize++;
            }
        }
        if (finalSize == 0) {
            return new Polynomial(new double[]{0}, new int[]{0});
        }

        double[] finalCoeffs = new double[finalSize];
        int[] finalExps = new int[finalSize];
        int index = 0;
        
        for (int i = 0; i < currentSize; i++) {
            if (tempCoeffs[i] != 0.0) {
                finalCoeffs[index] = tempCoeffs[i];
                finalExps[index] = tempExps[i];
                index++;
            }
        }

        return new Polynomial(finalCoeffs, finalExps);
    }
    public boolean hasRoot(double root){
        return (evaluate(root) == 0);
    }
    public int find(int arr[], int val, int curr_size){
        for(int i =0; i<curr_size; i++){
            if (arr[i] == val) return i;
        }
        return -1; //not found
    }

    public Polynomial multiply(Polynomial p){
        int maxLength = this.coefficients.length*p.coefficients.length;
        double[] newCoeffs = new double[maxLength];
        int[] newExps = new int[maxLength];
        int counter = 0;
        for(int i = 0; i<this.coefficients.length; i++){
            for(int j=0; j<p.coefficients.length; j++){
                int found_idx = find(newExps,this.exponents[i]+p.exponents[j],counter);
                if (found_idx != -1){
                    newCoeffs[found_idx]+=this.coefficients[i]*p.coefficients[j];
                }
                else{
                    newCoeffs[counter] = this.coefficients[i]*p.coefficients[j];
                    newExps[counter] = this.exponents[i]+p.exponents[j];
                    counter++;
                }
                
            }
        }
        int final_size = 0;
        for(int i = 0; i<counter; i++){
            if(newCoeffs[i] != 0.0){
                final_size++;
            }
        }
        if (final_size == 0){
            return new Polynomial(new double[]{0}, new int[]{0});
        }
        double[] final_coeffs = new double[final_size];
        int[] final_exps = new int[final_size];
        int idx = 0;
        for (int i =0; i<counter; i++){
            if(newCoeffs[i] != 0.0){
                final_coeffs[idx] = newCoeffs[i];
                final_exps[idx] = newExps[i];
                idx++;
            }
        }

        return new Polynomial(final_coeffs, final_exps);
    }
}
