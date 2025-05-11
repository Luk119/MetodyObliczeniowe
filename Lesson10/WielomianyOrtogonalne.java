package Lesson10;
import java.util.Scanner;

public class WielomianyOrtogonalne {
    public static double f(double x) {
        return Math.sqrt(2 * Math.pow(x, 3) - x + 9);
    }

    public static double legendrePolynomial(int n, double x) {
        if (n == 0) {
            return 1.0;
        } else if (n == 1) {
            return x;
        } else {
            double pNMinus1 = x;
            double pNMinus2 = 1.0;
            double pN = 0.0;

            for (int currentN = 2; currentN <= n; currentN++) {
                double part1 = 1.0 / currentN;
                double part2 = (2.0 * (currentN - 1) + 1.0) * x * pNMinus1;
                double part3 = ((currentN - 1.0) / currentN) * pNMinus2;

                pN = part1 * (part2 - currentN * part3);

                pNMinus2 = pNMinus1;
                pNMinus1 = pN;
            }

            return pN;
        }
    }

    public static double metodaTrapezow2(double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += f(x);
        }

        return sum * h;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double a = -1.0;
        double b = 1.0;
        double x = 0.25;
        int numIntervals = 100;

        System.out.print("Podaj stopień wielomianu aproksymującego n: ");
        int n = scanner.nextInt();
        scanner.close();

        double[] coefficients = new double[n + 1];

        for (int i = 0; i <= n; i++) {
            double lambda_i = metodaTrapezow2(a, b, numIntervals);
            double integral = metodaTrapezow2(a, b, numIntervals);
            coefficients[i] = integral / lambda_i;
        }

        double approximation = 0.0;
        for (int i = 0; i <= n; i++) {
            approximation += coefficients[i] * legendrePolynomial(i, x);
        }

        double exactValue = f(x);

        System.out.println("\nWyniki aproksymacji dla n = " + n + ":");
        System.out.println("Współczynniki wielomianu aproksymującego:");
        for (int i = 0; i <= n; i++) {
            System.out.printf("C%d = %.6f\n", i, coefficients[i]);
        }

        System.out.println("\nWartość wielomianu aproksymującego w punkcie x = " + x + ": " + approximation);
        System.out.println("Dokładna wartość funkcji w punkcie x = " + x + ": " + exactValue);
        System.out.println("Błąd bezwzględny: " + Math.abs(exactValue - approximation));

        System.out.println("\nPostać wielomianu aproksymującego:");
        System.out.print("g(x) = " + coefficients[0]);
        for (int i = 1; i <= n; i++) {
            if (coefficients[i] >= 0) {
                System.out.print(" + " + coefficients[i] + " * P" + i + "(x)");
            } else {
                System.out.print(" - " + Math.abs(coefficients[i]) + " * P" + i + "(x)");
            }
        }
        System.out.println();
    }
}
