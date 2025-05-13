package Lesson9;
// mój przykład 17
// wyswietlic x i pierwiastek jeden pod drugim
//funkcja wejsciowa, przedział, x w którym szukam rozwiazania
import java.util.Scanner;

public class AproksymacjaSredniokwadratowa {

    //√(2x³ - x + 9)
    public static double f(double x) {
        return Math.sqrt(2 * Math.pow(x, 3) - x + 9);
    }

    // Metoda trapezów do całkowania numerycznego dla funkcji f(x)
    public static double metodaTrapezowF(double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += f(x);
        }
        return sum * h;
    }

    // Metoda trapezów do całkowania numerycznego dla x^i
    public static double metodaTrapezowXi(int i, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (Math.pow(a, i) + Math.pow(b, i));

        for (int k = 1; k < n; k++) {
            double x = a + k * h;
            sum += Math.pow(x, i);
        }

        return sum * h;
    }

    // Metoda trapezów do całkowania numerycznego dla x^i * f(x)
    public static double metodaTrapezowXiF(int i, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (Math.pow(a, i) * f(a) + Math.pow(b, i) * f(b));

        for (int k = 1; k < n; k++) {
            double x = a + k * h;
            sum += Math.pow(x, i) * f(x);
        }

        return sum * h;
    }

    // Metoda trapezów do całkowania numerycznego dla x^(i+j)
    public static double metodaTrapezowXiXj(int i, int j, double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (Math.pow(a, i+j) + Math.pow(b, i+j));

        for (int k = 1; k < n; k++) {
            double x = a + k * h;
            sum += Math.pow(x, i+j);
        }

        return sum * h;
    }

    // Rozwiązanie układu równań metodą Gaussa
    public static double[] gauss(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {
            // Znajdź maksymalny element w kolumnie
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            // Zamień wiersze
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // Eliminacja
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // Rozwiązanie układu
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

    // Obliczenie wartości wielomianu aproksymującego w punkcie x
    public static double obliczW(double[] a, double x) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * Math.pow(x, i);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double a = -1.0;
        double b = 1.0;
        double x = 0.25;

        System.out.print("Podaj stopień wielomianu aproksymującego: ");
        int stopienWielomianu = scanner.nextInt();

        int rozmiar = stopienWielomianu + 1;
        double[][] A = new double[rozmiar][rozmiar];
        double[] B = new double[rozmiar];

        // Wypełnianie macierzy A i wektora B
        for (int i = 0; i < rozmiar; i++) {
            for (int j = 0; j < rozmiar; j++) {
                A[i][j] = metodaTrapezowXiXj(i, j, a, b, 50);
            }
            B[i] = metodaTrapezowXiF(i, a, b, 50);
        }

        double[] wspolczynniki = gauss(A, B);

        // Wyświetlenie współczynników wielomianu aproksymującego
        System.out.println("\nWspółczynniki wielomianu aproksymującego:");
        for (int i = 0; i < wspolczynniki.length; i++) {
            System.out.printf("a%d = %.6f%n", i, wspolczynniki[i]);
        }

        // Obliczenie wartości aproksymacji w punkcie x
        double wartoscAproksymacji = obliczW(wspolczynniki, x);
        System.out.println("\nWartość aproksymacji w x=" + x + ": " + wartoscAproksymacji);
        System.out.println("Wartość dokładna funkcji w x=" + x + ": " + f(x));
        System.out.printf("Różnica: %.6f%n", wartoscAproksymacji - f(x));

        scanner.close();
    }
}