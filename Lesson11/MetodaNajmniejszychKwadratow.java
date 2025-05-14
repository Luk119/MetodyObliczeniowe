package Lesson11;

import Lesson9.Aproksymacja;

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
        return Aproksymacja.gauss(A, b);
    }

    public static double obliczWartosc(double[] wsp, double x) {
        return Aproksymacja.obliczW(wsp, x);
    }
}