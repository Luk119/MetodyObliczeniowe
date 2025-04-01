package Lesson6;

public class Main {
    public static void main(String[] args) {
        double[] x = {-4, -2, 0, 2, 4};
        double[] y = {-618, -42, -2, -18, -378};
        double p = 3;

        double result = interpolacjaNewtona(x, y, p);
        System.out.println("Wartość w twoim punkcie = " + result);
    }

    public static double interpolacjaNewtona(double[] x, double[] y, double p) {
        int n = x.length;
        double m = x[1] - x[0];
        double[][] delta = new double[n][n];

        //Pierwsza kolumna = różnice wartości funkcji
        for (int i = 0; i < n; i++) {
            delta[i][0] = y[i];
        }

        //Różnice progresywne
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                delta[i][j] = delta[i + 1][j - 1] - delta[i][j - 1];
            }
        }

        //Wielomian interpolacyjny
        double sum = y[0];
        double term;
        double product = 1;
        for (int k = 1; k < n; k++) {
            product *= (p - x[k - 1]);
            term = delta[0][k] / (silnia(k) * Math.pow(m, k));
            sum += term * product;
        }

        return sum;
    }

    private static int silnia(int num) {
        if(num == 0 || num == 1) {
            return 1;
        }
            return silnia(num - 1) * num;
    }
}
