package Lesson12;
import java.util.Scanner;

public class MetodaSiecznych {

    public static double f(double x) {
        return x * x + 4.1 * x - 6.36;
    }

    public static double d2f(double x) {
        return 2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj dolną granicę przedziłu(a): ");
        double a = sc.nextDouble();

        System.out.print("Podaj górną granicę przedziłu(b): ");
        double b = sc.nextDouble();

        System.out.print("Podaj wartość błędu(ε): ");
        double epsilon = sc.nextDouble();

        if (f(a) * f(b) >= 0) {
            System.out.println("Błąd: warunek konieczny nie jest spełniony");
            return;
        }

        double x0;
        boolean fixedA = f(a) * d2f(a) > 0;

        if (fixedA) {
            x0 = b;
        } else {
            x0 = a;
        }

        double x1;
        int i = 0;

        while (true) {
            i++;

            if (fixedA) {
                x1 = x0 - f(x0) * (x0 - a) / (f(x0) - f(a));
            } else {
                x1 = x0 - f(x0) * (b - x0) / (f(b) - f(x0));
            }

            if (Math.abs(f(x1)) < epsilon || Math.abs(x1 - x0) < epsilon) {
                break;
            }

            x0 = x1;
        }

        System.out.printf("Wynik: x = %.15f%n", x1);
        System.out.printf("f(x) = %.15f%n", f(x1));
        System.out.println("Wykonano iteracji: " + i);
    }
}

