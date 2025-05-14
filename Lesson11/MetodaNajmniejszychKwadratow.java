package Lesson11;

import java.util.Scanner;
//not done
public class MetodaNajmniejszychKwadratow {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj liczbę punktów: ");
        int m = scanner.nextInt();

        double[] x = new double[m];
        double[] y = new double[m];

        System.out.println("Podaj punkty x i y:");
        for (int i = 0; i < m; i++) {
            System.out.print("x[" + i + "] = ");
            x[i] = scanner.nextDouble();

            System.out.print("y[" + i + "] = ");
            y[i] = scanner.nextDouble();
        }

        System.out.print("Podaj stopień wielomianu aproksymującego: ");
        int n = scanner.nextInt();

        System.out.print("Podaj punkt x, dla którego obliczyć wartość: ");
        double punkt = scanner.nextDouble();

        double[] wspolczynniki = obliczWspolczynniki(x, y, n);

        System.out.print("Współczynniki wielomianu: ");
        for(double wsp : wspolczynniki) System.out.print(wsp + " ");
        System.out.println();

        double wartosc = obliczWartosc(wspolczynniki, punkt);
        System.out.println("Wartość w punkcie " + punkt + ": " + wartosc);

        scanner.close();
    }

    public static double[] obliczWspolczynniki(double[] x, double[] y, int n) {
        int m = x.length;
        double[][] S = new double[n + 1][n + 1];
        double[] T = new double[n + 1];

        for (int k = 0; k <= n; k++) {
            for (int j = 0; j <= n; j++) {
                for (int i = 0; i < m; i++) {
                    S[k][j] += Math.pow(x[i], k + j);
                }
            }

            for (int i = 0; i < m; i++) {
                T[k] += Math.pow(x[i], k) * y[i];
            }
        }

        return gaussElimination(S, T);
    }

    public static double[] gaussElimination(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;

            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    public static double obliczWartosc(double[] wspolczynniki, double x) {
        double wynik = 0;
        for (int i = 0; i < wspolczynniki.length; i++) {
            wynik += wspolczynniki[i] * Math.pow(x, i);
        }
        return wynik;
    }
}
