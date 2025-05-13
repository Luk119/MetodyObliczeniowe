package Lesson9;
//done
import java.util.Scanner;

public class Aproksymacja {

    public static double f(double x) {
        return Math.sqrt(2 * Math.pow(x, 3) - x + 9);
    }

    public static double metodaTrapezow(double a, double b, int n, int exponent1, int exponent2) {
        double h = (b - a) / n;
        double sum = 0.5 * (Math.pow(a, exponent1 + exponent2) + Math.pow(b, exponent1 + exponent2));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += Math.pow(x, exponent1 + exponent2);
        }
        return sum * h;
    }

    public static double metodaTrapezowDlaFunkcji(double a, double b, int n, int exponent) {
        double h = (b - a) / n;
        double sum = 0.5 * (Math.pow(a, exponent) * f(a) + Math.pow(b, exponent) * f(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += Math.pow(x, exponent) * f(x);
        }
        return sum * h;
    }

    public static double[] gauss(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    public static double obliczW(double[] a, double x) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * Math.pow(x, i);
        }
        return result;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = 100;

        System.out.print("Podaj stopień n: ");
        int stopienWielomianu = scanner.nextInt();

        int rozmiar = stopienWielomianu + 1;
        double[][] A = new double[rozmiar][rozmiar];
        double[] B = new double[rozmiar];

        System.out.print("Podaj przedział dolny a: ");
        double a = scanner.nextDouble();

        System.out.print("Podaj przedział górny b: ");
        double b = scanner.nextDouble();

        System.out.print("Podaj punkt x: ");
        double x = scanner.nextDouble();


        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                A[i][j] = metodaTrapezow(a, b, n, i, j);
            }
            B[i] = metodaTrapezowDlaFunkcji(a, b, n, i);
        }

        double[] wspolczynniki = gauss(A, B);

        System.out.println("Współczynniki wielomianu aproksymującego:");
        for (int i = 0; i < wspolczynniki.length; i++) {
            System.out.printf("a%d = %.8f%n", i, wspolczynniki[i]);
        }

        double wartoscAproksymacji = obliczW(wspolczynniki, x);
        double wartoscDokladna = f(x);

        System.out.printf("%nWartość aproksymacji w x=%.2f: %.8f%n", x, wartoscAproksymacji);
        System.out.printf("Wartość dokładna funkcji w x=%.2f: %.8f%n", x, wartoscDokladna);
        System.out.printf("Różnica: %.8f%n", Math.abs(wartoscAproksymacji - wartoscDokladna));

        scanner.close();
    }
}
