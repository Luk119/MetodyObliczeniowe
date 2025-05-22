package Lesson12;
import java.util.Scanner;

public class MetodaBisekcji {

    public static double f(double x) {
        return x * x + 4.1 * x - 6.36;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj dolną granicę przedziału(a): ");
        double a = sc.nextDouble();

        System.out.print("Podaj górną granicę przedziału(b): ");
        double b = sc.nextDouble();

        System.out.print("Podaj wartość błędu(ε): ");
        double epsilon = sc.nextDouble();

        double xsr;
        int iteracje = 0;

        do {
            xsr = (a + b) / 2;
            iteracje++;

            if (f(a) * f(b) >= 0) {
                System.out.println("Warunek konieczny nie jest spełniony");
                return;
            }

            if (f(xsr) == 0) {
                break;
            }

            if (f(a) * f(xsr) < 0) {
                b = xsr;
            } else {
                a = xsr;
            }

        } while (Math.abs(f(xsr)) >= epsilon);

        System.out.printf("Przybliżone rozwiązanie: x = %.15f%n", xsr);
        System.out.printf("f(x) = %.15f%n", f(xsr));
        System.out.println("Wykonano iteracji: " + iteracje);
        sc.close();
    }
}