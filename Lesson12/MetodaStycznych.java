package Lesson12;

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
        double a = -10;
        double b = -1;
        double epsilon = 0.0001;

        if (f(a) * f(b) >= 0) {
            System.out.println("Warunek konieczny nie jest spełniony. Nie można zastosować metody stycznych.");
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
