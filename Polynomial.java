public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {

    	this.coefficients = new double[coefficients.length];

    	for (int i = 0; i < coefficients.length; i++) {
        	this.coefficients[i] = coefficients[i];
    	}
    }

    public Polynomial add(Polynomial poly) {

    	int maxLength = Math.max(this.coefficients.length, poly.coefficients.length);

    	double[] result = new double[maxLength];

    	for (int i = 0; i < maxLength; i++) {

        	if (i < this.coefficients.length) {
            		result[i] += this.coefficients[i];
        	}
        	if (i < poly.coefficients.length) {
            		result[i] += poly.coefficients[i];
        	}
    	}
    	return new Polynomial(result);
   }


    public double evaluate(double x) {

        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }


    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) == 0;  
    }
}