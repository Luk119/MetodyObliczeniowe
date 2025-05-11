package Lesson10;
import java.util.Scanner;
// done
public class WielomianyOrtogonalne {
    public static double funkcja(double x) {
        return Math.sqrt(2 * Math.pow(x, 3) - x + 9);
    }

    public static double wielomianLegendre(int stopien, double x) {
        if (stopien == 0) {
            return 1.0;
        } else if (stopien == 1) {
            return x;
        } else {
            double poprzedni1 = x;
            double poprzedni2 = 1.0;
            double aktualny = 0.0;

            for (int i = 2; i <= stopien; i++) {
                double czesc1 = 1.0 / i;
                double czesc2 = (2.0 * (i - 1) + 1.0) * x * poprzedni1;
                double czesc3 = ((i - 1.0) / i) * poprzedni2;

                aktualny = czesc1 * (czesc2 - i * czesc3);

                poprzedni2 = poprzedni1;
                poprzedni1 = aktualny;
            }

            return aktualny;
        }
    }

    public static double metodaTrapezow2(double a, double b, int n, int indeks, boolean jestLambda) {
        double h = (b - a) / n;
        double suma = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double legendre = wielomianLegendre(indeks, x);
            double wartosc = jestLambda ? legendre * legendre : legendre * funkcja(x);

            if (i == 0 || i == n) {
                suma += 0.5 * wartosc;
            } else {
                suma += wartosc;
            }
        }

        return suma * h;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int przedzialy = 1000;

        System.out.print("Podaj stopień n: ");
        int n = scanner.nextInt();

        System.out.print("Podaj przedział dolny a: ");
        double a = scanner.nextDouble();

        System.out.print("Podaj przedział górny b: ");
        double b = scanner.nextDouble();

        System.out.print("Podaj punkt x: ");
        double x = scanner.nextDouble();
        scanner.close();

        double[] wspolczynniki = new double[n + 1];

        for (int i = 0; i <= n; i++) {
            double lambda = metodaTrapezow2(a, b, przedzialy, i, true);
            double calka = metodaTrapezow2(a, b, przedzialy, i, false);
            wspolczynniki[i] = calka / lambda;
        }

        double aproksymacja = 0.0;
        for (int i = 0; i <= n; i++) {
            aproksymacja += wspolczynniki[i] * wielomianLegendre(i, x);
        }

        System.out.println("\nWspółczynniki wielomianu:");
        for (int i = 0; i <= n; i++) {
            System.out.printf("C%d = %.6f\n", i, wspolczynniki[i]);
        }

        System.out.println("\nWartość wielomianu w punkcie x = " + x + ": " + aproksymacja);
        System.out.println("Dokładna wartość funkcji w punkcie x = " + x + ": " + funkcja(x));
        System.out.println("Błąd bezwzględny: " + (aproksymacja - funkcja(x)));
    }
}