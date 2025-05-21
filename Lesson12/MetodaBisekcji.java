package Lesson12;
import java.util.Scanner;

public class MetodaBisekcji {

    public static double f(double x) {
        return x * x + 4.1 * x - 6.36;
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

        double c = a;
        int iterations = 0;

        while ((b - a) >= epsilon) {
            c = (a + b) / 2;
            iterations++;

            if (f(c) == 0) {
                break;
            } else if (f(c) * f(a) < 0) {
                b = c;
            } else {
                a = c;
            }
        }

        System.out.printf("Przybliżone rozwiązanie: x = %.5f%n", c);
        System.out.printf("f(x) = %.5f%n", f(c));
        System.out.println("Liczba iteracji: " + iterations);
    }
}
