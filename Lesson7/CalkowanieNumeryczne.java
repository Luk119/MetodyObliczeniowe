package Lesson7;
// Moja calka: 19)
public class CalkowanieNumeryczne {

    public static double f(double x) {
        double numerator = Math.cos(Math.pow(x, 2) + 0.7);
        double denominator = 1.1 + Math.sin(0.6 * x + 0.2);
        return numerator / denominator;
    }

    public static double metodaTrapezow(double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));

        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += f(x);
        }

        return sum * h;
    }

    public static double metodaSimpsona(double a, double b, int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n musi być parzyste");
        }

        double h = (b - a) / n;
        double sum = f(a) + f(b);

        // Sum for odd terms (4*)
        for (int i = 1; i < n; i += 2) {
            double x = a + i * h;
            sum += 4 * f(x);
        }

        // Sum for even terms (2*)
        for (int i = 2; i < n; i += 2) {
            double x = a + i * h;
            sum += 2 * f(x);
        }

        return sum * h / 3;
    }

    public static void main(String[] args) {
        double a = 0.5;
        double b = 1.8;
        int n = 1000; // Number of intervals

        System.out.println("Ca of cos(x² + 0.7)/(1.1 + sin(0.6x + 0.2)) from " + a + " to " + b);
        System.out.println("Using " + n + " intervals:");

        double trapezoidalResult = metodaTrapezow(a, b, n);
        System.out.println("Metoda Trapezów: " + trapezoidalResult);

        double simpsonsResult = metodaSimpsona(a, b, n);
        System.out.println("Metoda Sipmsona: " + simpsonsResult);
    }
}