package Lesson1;

import java.util.Scanner;

public class GaussElimination {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj rozmiar macierzy (n): ");
        int n = scanner.nextInt();

        double[][] A = new double[n][n + 1];
        System.out.println("Podaj elementy rozszerzonej macierzy (współczynniki równań i wartości prawej strony):");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                A[i][j] = scanner.nextDouble();
            }
        }

        gaussianElimination(A, n);

        scanner.close();
    }

    public static void gaussianElimination(double[][] A, int n) {
        // Eliminacja wierszami
        for (int i = 0; i < n; i++) {
            // Szukanie maksymalnego elementu dla stabilności numerycznej
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(A[k][i]) > Math.abs(A[maxRow][i])) {
                    maxRow = k;
                }
            }
            // Zamiana wierszy
            double[] temp = A[i];
            A[i] = A[maxRow];
            A[maxRow] = temp;

            // Upewnienie się, że główny element nie jest zerem
            if (A[i][i] == 0) {
                System.out.println("Brak unikalnego rozwiązania.");
                return;
            }

            // Eliminacja
            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                for (int k = i; k <= n; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        // Rozwiązanie układu równań przez podstawianie wstecz
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = A[i][n] / A[i][i];
            for (int j = i - 1; j >= 0; j--) {
                A[j][n] -= A[j][i] * x[i];
            }
        }

        // Wyświetlenie wyniku
        System.out.println("Rozwiązanie układu:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.6f\n", i, x[i]);
        }
    }
}

