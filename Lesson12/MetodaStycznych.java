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

        System.out.print("Podaj dolną granicę przedziału(a): ");
        double a = sc.nextDouble();

        System.out.print("Podaj górną granicę przedziału(b): ");
        double b = sc.nextDouble();

        System.out.print("Podaj wartość błędu(ε): ");
        double epsilon = sc.nextDouble();

        double x0;
        if (d2f(a) * f(a) > 0) {
            x0 = a;
        } else {
            x0 = b;
        }

        double x1;
        int i = 0;

        while (true) {
            if (f(a) * f(b) >= 0) {
                System.out.println("Błąd: warunek konieczny nie jest spełniony");
                return;
            }

            x1 = x0 - f(x0) / df(x0);
            i++;

            if (Math.abs(f(x1)) < epsilon || Math.abs(x1 - x0) < epsilon) {
                break;
            }

            x0 = x1;
        }

        System.out.printf("Wynik: x = %.15f%n", x1);
        System.out.printf("f(x) = %.15f%n", f(x1));
        System.out.println("Wykonano " + i + " iteracji");
    }
}
