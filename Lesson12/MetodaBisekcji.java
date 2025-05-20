package Lesson12;

public class MetodaBisekcji {

    public static double f(double x) {
        return x * x + 4.1 * x - 6.36;
    }

    public static void main(String[] args) {
        double a = -10;
        double b = -1;
        double epsilon = 0.0001;

        if (f(a) * f(b) >= 0) {
            System.out.println("Warunek konieczny nie jest spełniony. Nie można użyć metody bisekcji.");
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
