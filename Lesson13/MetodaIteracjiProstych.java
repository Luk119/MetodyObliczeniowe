package Lesson13;

public class MetodaIteracjiProstych {

    public static void main(String[] args) {
        double[][] a = {
                {10.0, 1.0, 1.0},
                {2.0, 8.0, 1.0},
                {1.0, 2.0, 6.0}
        };
        double[] b = {12.0, 11.0, 9.0};
        double eps = 0.001;
        int maxIter = 100;

        double[] solution = solve(a, b, eps, maxIter);

        System.out.println("\nRozwiązanie:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x_%d = %.6f%n", i + 1, solution[i]);
        }
    }

    public static double[] solve(double[][] a, double[] b, double eps, int maxIter) {
        int n = b.length;
        double[][] h = new double[n][n];
        double[] g = new double[n];

        for (int i = 0; i < n; i++) {
            double aii = a[i][i];
            g[i] = b[i] / aii;
            for (int j = 0; j < n; j++) {
                h[i][j] = (i == j) ? 0.0 : -a[i][j] / aii;
            }
        }

        double normaH = 0.0;
        for (int i = 0; i < n; i++) {
            double suma = 0.0;
            for (int j = 0; j < n; j++) {
                suma += Math.abs(h[i][j]);
            }
            if (suma > normaH) {
                normaH = suma;
            }
        }

        if (normaH >= 1.0) {
            System.out.println("Warunek zbieżności nie jest spełniony (||H|| = " + normaH + ").");
            return null;
        }

        double[] x = new double[n];
        double[] xPop = new double[n];
        int iter = 0;
        boolean sukces = false;

        for (int i = 0; i < n; i++) {
            x[i] = 0.0;
        }

        for (iter = 1; iter <= maxIter; iter++) {
            System.arraycopy(x, 0, xPop, 0, n);

            for (int i = 0; i < n; i++) {
                x[i] = g[i];
                for (int j = 0; j < n; j++) {
                    x[i] += h[i][j] * xPop[j];
                }
            }

            double maxRoz = 0.0;
            for (int i = 0; i < n; i++) {
                double roz = Math.abs(x[i] - xPop[i]);
                if (roz > maxRoz) {
                    maxRoz = roz;
                }
            }

            if (maxRoz < eps) {
                sukces = true;
                break;
            }
        }

        if (sukces) {
            System.out.println("Iteracje zakończone po " + iter + " iteracjach");
            return x;
        } else {
            System.out.println("Przekroczono maksymalną liczbę iteracji");
            return null;
        }
    }
}