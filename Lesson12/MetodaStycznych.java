package Lesson12;

import java.util.Scanner;

public class MetodaStycznych {

    public static double f(double x) {
        return x * x + 4.1 * x - 6.36;
    }

    public static double df(double x) {
        return 2 * x + 4.1;
    }

    public static double d2f(double x) {
        return 2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Podaj dolną granicę przedziłu(a):");
        double a = sc.nextDouble();

        System.out.println("Podaj górną granicę przedziłu(b):");
        double b = sc.nextDouble();

        System.out.println("Podaj wartość błędu(ε):");
        double epsilon = sc.nextDouble();

        if (f(a) * f(b) >= 0) {
            System.out.println("Błąd: warunek konieczny nie jest spełniony");
            return;
        }

        double x0;
        if (d2f(a) * f(a) > 0) {
            x0 = a;
        } else {
            x0 = b;
        }

        double x1;
        int iterations = 0;

        while (true) {
            x1 = x0 - f(x0) / df(x0);
            iterations++;

            if (Math.abs(f(x1)) < epsilon || Math.abs(x1 - x0) < epsilon) {
                break;
            }

            x0 = x1;
        }

        System.out.printf("Przybliżone rozwiązanie: x = %.5f%n", x1);
        System.out.printf("f(x) = %.5f%n", f(x1));
        System.out.println("Liczba iteracji: " + iterations);
    }
}
