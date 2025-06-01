import java.io.*;
import java.util.*;


public class Polynomial {
    double[] coefficients;
    int[] exponents;


    public Polynomial() {
        this.coefficients = new double[0];
	this.exponents = new int[0];
    }


    public Polynomial(double[] denseCoefficients) {

        int count = 0;

        for (int i = 0; i < denseCoefficients.length; i++) {
    		double coef = denseCoefficients[i];

            	if (coef != 0){
			count++;
		}
        }

        coefficients = new double[count];
        exponents = new int[count];

        int index = 0;

        for (int i = 0; i < denseCoefficients.length; i++) {
            if (denseCoefficients[i] != 0) {

                coefficients[index] = denseCoefficients[i];

                exponents[index] = i;
                index++;
            }
        }

        if (count == 0) { 

            coefficients = new double[0];
            exponents = new int[0];

        }

    }

public Polynomial(File file) throws IOException {

    List<Double> coefList = new ArrayList<>();
    List<Integer> expList = new ArrayList<>();

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = br.readLine();
    br.close();

    if (line == null || line.isEmpty()) {

        this.coefficients = new double[0];
        this.exponents = new int[0];

        return;
    }

    line = line.replace("-", "+-");
    String[] terms = line.split("\\+");

    for (String term : terms) {

        if (term.isEmpty()){
		continue;
	}
        double coef;
        int exp;

        if (term.contains("x")) {
            String[] parts = term.split("x");

            if (parts[0].equals("") || parts[0].equals("+")) {
                coef = 1;
            } else if (parts[0].equals("-")) {
                coef = -1;
            } 
	    else {
                coef = Double.parseDouble(parts[0]);
            }

            exp = Integer.parseInt(parts[1]);

        } else {
            coef = Double.parseDouble(term);
            exp = 0;
        }

        coefList.add(coef);
        expList.add(exp);
    }

    int n = coefList.size();
    this.coefficients = new double[n];

    this.exponents = new int[n];

    for (int i = 0; i < n; i++) {

        this.coefficients[i] = coefList.get(i);
        this.exponents[i] = expList.get(i);
    }
}



    public Polynomial add(Polynomial poly) {

        int i = 0, j = 0;

    	List<Double> coefList = new ArrayList<>();
    	List<Integer> expList = new ArrayList<>();


    	while (i < this.coefficients.length && j < poly.coefficients.length) {
        	int exp1 = this.exponents[i];
        	int exp2 = poly.exponents[j];

        	if (exp1 == exp2) {
            		double sum = this.coefficients[i] + poly.coefficients[j];
            		if (sum != 0) {
                		coefList.add(sum);
                		expList.add(exp1);
            		}
            		i++;
            		j++;

        	} 
		else if (exp1 < exp2){
            		coefList.add(this.coefficients[i]);
            		expList.add(exp1);
            		i++;

        	} else{

            		coefList.add(poly.coefficients[j]);
            		expList.add(exp2);
            		j++;
        }
    }


    	while (i < this.coefficients.length) {

        	coefList.add(this.coefficients[i]);
        	expList.add(this.exponents[i]);
        	i++;
    	}

    	while (j < poly.coefficients.length) {

        	coefList.add(poly.coefficients[j]);
        	expList.add(poly.exponents[j]);
        	j++;
    	}


    	int n = coefList.size();
    	double[] resultCoefficients = new double[n];
    	int[] resultExponents = new int[n];

    	for (int k = 0; k < n; k++) {

        	resultCoefficients[k] = coefList.get(k);
        	resultExponents[k] = expList.get(k);
    	}

    	Polynomial result = new Polynomial();
    	result.coefficients = resultCoefficients;
   	result.exponents = resultExponents;

   	return result;
    	
    }


    public double evaluate(double x) {

        double result = 0;

        for (int i = 0; i < coefficients.length; i++) {

            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }


    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) == 0.0; 
    }



    public Polynomial multiply(Polynomial poly) {

    	List<Double> tempCoefficients = new ArrayList<>();
    	List<Integer> tempExponents = new ArrayList<>();


    	for (int i = 0; i < this.coefficients.length; i++) {

        	for (int j = 0; j < poly.coefficients.length; j++) {

            		double productCoef = this.coefficients[i] * poly.coefficients[j];
            		int productExp = this.exponents[i] + poly.exponents[j];

            		tempCoefficients.add(productCoef);
            		tempExponents.add(productExp);
        	}
    	}


    	List<Double> finalCoefficients = new ArrayList<>();
    	List<Integer> finalExponents = new ArrayList<>();

    	for (int i = 0; i < tempCoefficients.size(); i++) {

        	double coef = tempCoefficients.get(i);
        	int exp = tempExponents.get(i);

        	boolean found = false;

        	for (int k = 0; k < finalExponents.size(); k++) {
            		if (finalExponents.get(k) == exp) {

                		finalCoefficients.set(k, finalCoefficients.get(k) + coef);
                		found = true;
                		break;
            		}
        	}

        	if (!found) {
            		finalCoefficients.add(coef);
            		finalExponents.add(exp);
        	}
    	}


    	List<Double> cleanedCoefficients = new ArrayList<>();
    	List<Integer> cleanedExponents = new ArrayList<>();

    	for (int i = 0; i < finalCoefficients.size(); i++) {
        	if (finalCoefficients.get(i) != 0.0) {

            		cleanedCoefficients.add(finalCoefficients.get(i));
            		cleanedExponents.add(finalExponents.get(i));
        	}
    	}


    	int n = cleanedCoefficients.size();
    	double[] resultCoefficients = new double[n];
    	int[] resultExponents = new int[n];

    	for (int i = 0; i < n; i++) {

        	int minIndex = i;
        	for (int j = i + 1; j < n; j++) {
            		if (cleanedExponents.get(j) < cleanedExponents.get(minIndex)) {
                		minIndex = j;
            		}
        	}
        

		double tempCoef = cleanedCoefficients.get(minIndex);
		int tempExp = cleanedExponents.get(minIndex);


		cleanedCoefficients.set(minIndex, cleanedCoefficients.get(i));
		cleanedExponents.set(minIndex, cleanedExponents.get(i));

		cleanedCoefficients.set(i, tempCoef);
		cleanedExponents.set(i, tempExp);


		resultCoefficients[i] = tempCoef;
		resultExponents[i] = tempExp;

    	}


    	Polynomial result = new Polynomial();
    	result.coefficients = resultCoefficients;
    	result.exponents = resultExponents;

    	return result;
}


public void saveToFile(String filename) {

    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(this.toString());
        writer.close();

    } 
    catch (IOException e){
        e.printStackTrace();
    }
}

@Override
public String toString() {

    if (coefficients.length == 0){ 
	return "0"; 
    }	

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < coefficients.length; i++) {

        double coef = coefficients[i];
        int exp = exponents[i];

        if (coef == 0) continue;


        if (i > 0) {
    		if (coef > 0) {
        		sb.append("+");
    		}
	}



        if (exp == 0) {

            sb.append(coef);
        } else {
            if (coef == 1){
		sb.append("x").append(exp);
		}
            else if (coef == -1){
		sb.append("-x").append(exp);
		}
            else {
		sb.append(coef).append("x").append(exp);
		}
        }
    }

    return sb.toString();
}


}