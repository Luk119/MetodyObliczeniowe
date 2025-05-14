package Lesson11;
//not done
import java.util.Arrays;

public class MetodaNajmniejszychKwadratow {

    public static void main(String[] args) {
        // Przykład z obrazka: sqrt(2x^3 - x + 9), punkty x = {-1, -0.5, 0, 0.5, 1}
        double[] x = {-1, -0.5, 0, 0.5, 1};
        double[] y = {
                Math.sqrt(2*Math.pow(-1,3) - (-1) + 9),
                Math.sqrt(2*Math.pow(-0.5,3) - (-0.5) + 9),
                Math.sqrt(2*Math.pow(0,3) - 0 + 9),
                Math.sqrt(2*Math.pow(0.5,3) - 0.5 + 9),
                Math.sqrt(2*Math.pow(1,3) - 1 + 9)
        };

        int n = 2;
        double punkt = 0.25;

        double[] wspolczynniki = obliczWspolczynniki(x, y, n);
        System.out.println("Współczynniki wielomianu: " + Arrays.toString(wspolczynniki));

        double wartosc = obliczWartosc(wspolczynniki, punkt);
        System.out.println("Wartość w punkcie " + punkt + ": " + wartosc);
    }

    public static double[] obliczWspolczynniki(double[] x, double[] y, int n) {
        int m = x.length;
        double[][] S = new double[n+1][n+1];
        double[] T = new double[n+1];

        // Obliczanie macierzy S i wektora T
        for (int k = 0; k <= n; k++) {
            for (int j = 0; j <= n; j++) {
                S[k][j] = 0;
                for (int i = 0; i < m; i++) {
                    S[k][j] += Math.pow(x[i], k+j);
                }
            }

            T[k] = 0;
            for (int i = 0; i < m; i++) {
                T[k] += Math.pow(x[i], k) * y[i];
            }
        }

        // Rozwiązanie układu równań metodą eliminacji Gaussa
        return gaussElimination(S, T);
    }

    public static double[] gaussElimination(double[][] A, double[] b) {
        int n = b.length;

        // Eliminacja współczynników
        for (int p = 0; p < n; p++) {
            // Znajdź wiersz z maksymalnym elementem w kolumnie p
            int max = p;
            for (int i = p+1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            // Zamień wiersze
            double[] temp = A[p];
            A[p] = A[max];
            A[max] = temp;
            double t = b[p];
            b[p] = b[max];
            b[max] = t;

            // Jeśli element diagonalny jest bliski zeru, pomiń tę kolumnę
            if (Math.abs(A[p][p]) <= 1e-10) {
                continue;
            }

            // Eliminacja
            for (int i = p+1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // Podstawienie wsteczne
        double[] x = new double[n];
        for (int i = n-1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i+1; j < n; j++) {
                sum += A[i][j] * x[j];
            }

            if (Math.abs(A[i][i]) > 1e-10) {
                x[i] = (b[i] - sum) / A[i][i];
            } else {
                // Jeśli element diagonalny jest zerowy, ustaw współczynnik na 0
                x[i] = 0;
            }
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