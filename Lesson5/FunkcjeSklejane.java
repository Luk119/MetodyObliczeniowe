package Lesson5;

import java.util.Arrays;
import java.util.Scanner;

public class FunkcjeSklejane {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] x = {1, 3, 5, 7};
        double[] y = {1, 8, 9, 17};
        double fprimStart = 1;
        double fprimEnd = 1;

        System.out.print("Podaj liczbę wartości do obliczenia: ");
        int n = scanner.nextInt();
        double[] szukaneWartosci = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Podaj " + i+1 + " wartość x: ");
            szukaneWartosci[i] = scanner.nextDouble();
        }

        double[] a = Arrays.copyOf(y, y.length);
        double[] b = new double[y.length];
        double[] c = new double[y.length];
        double[] d = new double[y.length];

        liczSklejane(x, y, fprimStart, fprimEnd, a, b, c, d);

        for (double val : szukaneWartosci) {
            double wynik = sklejane(val, x, a, b, c, d);
            System.out.printf("S_3(%.1f) = %.3f\n", val, wynik);
        }

        scanner.close();
    }

    public static void liczSklejane(double[] x, double[] y, double fprimStart, double fprimEnd, double[] a, double[] b, double[] c, double[] d) {
        int n = x.length - 1;
        double[] h = new double[n];
        double[] alpha = new double[n + 1];

        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }

        alpha[0] = 3 * (y[1] - y[0]) / h[0] - 3 * fprimStart;
        alpha[n] = 3 * fprimEnd - 3 * (y[n] - y[n - 1]) / h[n - 1];

        for (int i = 1; i < n; i++) {
            alpha[i] = 3 / h[i] * (y[i + 1] - y[i]) - 3 / h[i - 1] * (y[i] - y[i - 1]);
        }

        double[] l = new double[n + 1];
        double[] mu = new double[n + 1];
        double[] z = new double[n + 1];

        l[0] = 2 * h[0];
        mu[0] = 0.5;
        z[0] = alpha[0] / l[0];

        for (int i = 1; i < n; i++) {
            l[i] = 2 * (x[i + 1] - x[i - 1]) - h[i - 1] * mu[i - 1];
            mu[i] = h[i] / l[i];
            z[i] = (alpha[i] - h[i - 1] * z[i - 1]) / l[i];
        }

        l[n] = h[n - 1] * (2 - mu[n - 1]);
        z[n] = (alpha[n] - h[n - 1] * z[n - 1]) / l[n];
        c[n] = z[n];

        for (int j = n - 1; j >= 0; j--) {
            c[j] = z[j] - mu[j] * c[j + 1];
            b[j] = (y[j + 1] - y[j]) / h[j] - h[j] * (c[j + 1] + 2 * c[j]) / 3;
            d[j] = (c[j + 1] - c[j]) / (3 * h[j]);
        }
    }

    public static double sklejane(double xVal, double[] x, double[] a, double[] b, double[] c, double[] d) {
        int i = x.length - 2;
        while (i >= 0 && xVal < x[i]) {
            i--;
        }
        double dx = xVal - x[i];
        return a[i] + b[i] * dx + c[i] * dx * dx + d[i] * dx * dx * dx;
    }
}
