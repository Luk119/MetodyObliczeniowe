package Lesson11;

import java.util.Scanner;

public class MetodaNajmniejszychKwadratow {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Podaj liczbę punktów: ");
        int nPkt = sc.nextInt();

        double[] tabX = new double[nPkt];
        double[] tabY = new double[nPkt];

        System.out.println("Podaj punkty x i y:");
        for (int i = 0; i < nPkt; i++) {
            System.out.print("x[" + i + "] = ");
            tabX[i] = sc.nextDouble();

            System.out.print("y[" + i + "] = ");
            tabY[i] = sc.nextDouble();
        }

        System.out.print("Podaj stopień wielomianu: ");
        int stWiel = sc.nextInt();

        System.out.print("Podaj punkt x do obliczenia: ");
        double xOblicz = sc.nextDouble();

        double[] wsp = obliczWspolczynniki(tabX, tabY, stWiel);

        System.out.print("Współczynniki wielomianu: ");
        for(double w : wsp) {
            System.out.print(w + " ");
        }
        System.out.println();

        double wynik = obliczWartosc(wsp, xOblicz);
        System.out.println("Wartość w punkcie " + xOblicz + ": " + wynik);

        sc.close();
    }

    public static double[] obliczWspolczynniki(double[] tabX, double[] tabY, int stWiel) {
        int n = tabX.length;
        double[][] S = new double[stWiel + 1][stWiel + 1];
        double[] T = new double[stWiel + 1];

        for (int k = 0; k <= stWiel; k++) {
            for (int j = 0; j <= stWiel; j++) {
                for (int i = 0; i < n; i++) {
                    S[k][j] += Math.pow(tabX[i], k + j);
                }
            }

            for (int i = 0; i < n; i++) {
                T[k] += Math.pow(tabX[i], k) * tabY[i];
            }
        }

        return gauss(S, T);
    }

    public static double[] gauss(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            double[] tmp = A[p];
            A[p] = A[max];
            A[max] = tmp;

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

    public static double obliczWartosc(double[] wsp, double x) {
        double wynik = 0;
        for (int i = 0; i < wsp.length; i++) {
            wynik += wsp[i] * Math.pow(x, i);
        }
        return wynik;
    }
}