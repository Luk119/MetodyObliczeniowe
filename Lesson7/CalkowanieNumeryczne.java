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
        try {
            if (n % 2 != 0) {
                throw new IllegalArgumentException("Error. Ilość przedziałów musi być parzysta");
            }

            double h = (b - a) / n;
            double sum = f(a) + f(b);

            for (int i = 1; i < n; i += 2) {
                double x = a + i * h;
                sum += 4 * f(x);
            }

            for (int i = 2; i < n; i += 2) {
                double x = a + i * h;
                sum += 2 * f(x);
            }
            return (sum * h) / 3;

        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        double a = 0.5;
        double b = 1.8;
        int n = 1000000;

        System.out.println("Calkowanie numeryczne cos(x² + 0.7)/(1.1 + sin(0.6x + 0.2)) od " + a + " do " + b);

        System.out.printf("Metodą Trapezów: %.20f%n", metodaTrapezow(a, b, n));
        System.out.printf("Metodą Simpsona: %.20f%n", metodaSimpsona(a, b, n));

    }
}