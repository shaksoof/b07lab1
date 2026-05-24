class Polynomial{
    double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[]{0};
    }
    public Polynomial(double[] arr){
        this.coefficients = arr;
    }
    public double evaluate(double num){
        double ans = 0;
        for (int i = 0; i<this.coefficients.length; i++){
            if (i == 0){
                ans+=this.coefficients[0];
            }
            else{
                ans+=this.coefficients[i]*Math.pow(num,i);
            }

        }
        return ans;
    }
    public Polynomial add(Polynomial p) {
        int maxLength = Math.max(this.coefficients.length, p.coefficients.length);
        double[] newCoeffs = new double[maxLength];
        for (int i = 0; i < maxLength; i++) {
            double val1 = 0;
            double val2 = 0;
            if (i < this.coefficients.length) {
                val1 = this.coefficients[i];
            }
            if (i < p.coefficients.length) {
                val2 = p.coefficients[i];
            }
            newCoeffs[i] = val1 + val2;
        }
        return new Polynomial(newCoeffs);
    }
    public boolean hasRoot(double root){
        return (evaluate(root) == 0);
    }
}
