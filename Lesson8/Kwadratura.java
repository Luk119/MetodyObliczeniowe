package Lesson8;

import java.util.Scanner;

public class Kwadratura {

    public static double f(double x) {
        return (x - 1) / (x * x + x);
    }

    public static double integrate(double a, double b, int n) {
        double[] t, w;

        switch (n) {
            case 2:
                t = new double[]{-0.5773502692, 0.5773502692};
                w = new double[]{1.0, 1.0};
                break;
            case 4:
                t = new double[]{-0.8611363116, -0.3399810436, 0.3399810436, 0.8611363116};
                w = new double[]{0.3478548451, 0.6521451549, 0.6521451549, 0.3478548451};
                break;
            case 6:
                t = new double[]{-0.9324695142, -0.6612093865, -0.2386191861,
                        0.2386191861,  0.6612093865,  0.9324695142};
                w = new double[]{0.1713244924, 0.3607615730, 0.4679139346,
                        0.4679139346, 0.3607615730, 0.1713244924};
                break;
            case 8:
                t = new double[]{-0.9602898565, -0.7966664774, -0.5255324099, -0.1834346425,
                        0.1834346425,  0.5255324099,  0.7966664774,  0.9602898565};
                w = new double[]{0.1012285363, 0.2223810345, 0.3137066459, 0.3626837834,
                        0.3626837834, 0.3137066459, 0.2223810345, 0.1012285363};
                break;
            case 10:
                t = new double[]{-0.9739065285, -0.8650633666, -0.6794095682, -0.4333953941, -0.1488743389,
                        0.1488743389,  0.4333953941,  0.6794095682,  0.8650633666,  0.9739065285};
                w = new double[]{0.0666713443, 0.1494513491, 0.2190863625, 0.2692667193, 0.2955242247,
                        0.2955242247, 0.2692667193, 0.2190863625, 0.1494513491, 0.0666713443};
                break;
            case 16:
                t = new double[]{-0.9894009349, -0.9445750230, -0.8656312024, -0.7554044083,
                        -0.6178762444, -0.4580167776, -0.2816035507, -0.0950125098,
                        0.0950125098,  0.2816035507,  0.4580167776,  0.6178762444,
                        0.7554044083,  0.8656312024,  0.9445750230,  0.9894009349};
                w = new double[]{0.0271524594, 0.0622535239, 0.0951585116, 0.1246289713,
                        0.1495959888, 0.1691565193, 0.1826034150, 0.1894506104,
                        0.1894506104, 0.1826034150, 0.1691565193, 0.1495959888,
                        0.1246289713, 0.0951585116, 0.0622535239, 0.0271524594};
                break;
            default:
                throw new IllegalArgumentException("Dozwolone wartości n to: 2, 4, 6, 8, 10, 16");
        }

        double result = 0.0;
        for (int i = 0; i < n; i++) {
            double x = ((b - a) / 2.0) * t[i] + (b + a) / 2.0;
            result += w[i] * f(x);
        }

        result *= (b - a) / 2.0;
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj dolną granicę całkowania a: ");
        double a = scanner.nextDouble();

        System.out.print("Podaj górną granicę całkowania b: ");
        double b = scanner.nextDouble();

        System.out.print("Podaj liczbę węzłów n (2, 4, 6, 8, 10, 16): ");
        int n = scanner.nextInt();

        try {
            double result = integrate(a, b, n);
            System.out.printf("Przybliżona wartość całki: %.8f\n", result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
