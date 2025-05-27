package Lesson13;
public class MetodaIteracjiProstych {

    public static void main(String[] args) {
        System.out.println("METODA ITERACJI PROSTYCH - PRZYKŁAD Z PDF");
        System.out.println("=".repeat(80));

        // Dane z przykładu:
        // 10*x1 + 1*x2 + 1*x3 = 12
        //  2*x1 + 8*x2 + 1*x3 = 11
        //  1*x1 + 2*x2 + 6*x3 = 9

        double[][] a = {
                {10.0, 1.0, 1.0},
                {2.0, 8.0, 1.0},
                {1.0, 2.0, 6.0}
        };

        double[] b = {12.0, 11.0, 9.0};
        double eps = 0.001;
        int maxIter = 100;
        int n = 3;

        // Wyświetlenie oryginalnego układu
        System.out.println("Oryginalny układ równań:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j > 0 && a[i][j] >= 0) {
                    System.out.print(" + ");
                } else if (j > 0) {
                    System.out.print(" ");
                }
                System.out.printf("%.0f*x_%d", a[i][j], j+1);
            }
            System.out.printf(" = %.0f%n", b[i]);
        }
        System.out.println();

        // Przekształcanie układu równań
        double[][] h = new double[n][n];
        double[] g = new double[n];

        System.out.println("PRZEKSZTAŁCANIE UKŁADU RÓWNAŃ:");
        System.out.println("Dzielimy każde równanie przez odpowiedni współczynnik diagonalny:");

        for (int i = 0; i < n; i++) {
            double aii = a[i][i];

            // Obliczanie g_i = b_i / a_ii
            g[i] = b[i] / aii;
            System.out.printf("g_%d = b_%d / a_%d%d = %.3f / %.3f = %.6f%n",
                    i+1, i+1, i+1, i+1, b[i], aii, g[i]);

            // Obliczanie h_ij
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    h[i][j] = 0.0; // h_ii = 0
                } else {
                    h[i][j] = -a[i][j] / aii;
                    System.out.printf("h_%d%d = -a_%d%d / a_%d%d = -%.3f / %.3f = %.6f%n",
                            i+1, j+1, i+1, j+1, i+1, i+1,
                            a[i][j], aii, h[i][j]);
                }
            }
            System.out.println();
        }

        System.out.println("Przekształcony układ równań w postaci x = g + H*x:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x_%d = %.6f", i+1, g[i]);
            for (int j = 0; j < n; j++) {
                if (h[i][j] != 0) {
                    System.out.printf(" + %.6f * x_%d", h[i][j], j+1);
                }
            }
            System.out.println();
        }
        System.out.println();

        // Sprawdzanie warunku zbieżności
        double normaH = 0.0;

        System.out.println("SPRAWDZANIE WARUNKU ZBIEŻNOŚCI:");
        System.out.println("Obliczanie normy macierzy H (norma maksymalna):");

        for (int i = 0; i < n; i++) {
            double suma = 0.0;
            System.out.printf("Wiersz %d: ", i+1);
            for (int j = 0; j < n; j++) {
                double abs = Math.abs(h[i][j]);
                suma += abs;
                System.out.printf("|%.6f| ", h[i][j]);
            }
            System.out.printf("= %.6f%n", suma);

            if (suma > normaH) {
                normaH = suma;
            }
        }

        System.out.printf("||H|| = max{sumy wierszy} = %.6f%n", normaH);

        boolean zbiezny = normaH < 1.0;
        System.out.printf("Warunek zbieżności ||H|| < 1: %.6f < 1.0 = %s%n%n",
                normaH, zbiezny ? "TRUE" : "FALSE");

        if (!zbiezny) {
            System.out.println("Warunek zbieżności nie jest spełniony!");
            return;
        }

        // Proces iteracji
        double[] x = new double[n];  // bieżące przybliżenie
        double[] xPop = new double[n];  // poprzednie przybliżenie

        System.out.println("PROCES ITERACJI:");
        System.out.println("Przybliżenie początkowe:");
        for (int i = 0; i < n; i++) {
            x[i] = 0.0;
            System.out.printf("x_%d^(0) = %.6f%n", i+1, x[i]);
        }
        System.out.println();

        int iter = 0;
        boolean sukces = false;

        // Pętla iteracji
        for (iter = 1; iter <= maxIter; iter++) {
            System.out.printf("ITERACJA %d:%n", iter);

            // Kopiowanie bieżącego przybliżenia do poprzedniego
            for (int i = 0; i < n; i++) {
                xPop[i] = x[i];
            }

            // Obliczanie nowego przybliżenia: x^(k+1) = g + H * x^(k)
            for (int i = 0; i < n; i++) {
                x[i] = g[i];

                System.out.printf("x_%d^(%d) = g_%d", i+1, iter, i+1);

                for (int j = 0; j < n; j++) {
                    if (h[i][j] != 0) {
                        x[i] += h[i][j] * xPop[j];
                        System.out.printf(" + h_%d%d * x_%d^(%d)", i+1, j+1, j+1, iter-1);
                    }
                }

                System.out.printf("%n");
                System.out.printf("x_%d^(%d) = %.6f", i+1, iter, g[i]);

                for (int j = 0; j < n; j++) {
                    if (h[i][j] != 0) {
                        System.out.printf(" + %.6f * %.6f", h[i][j], xPop[j]);
                    }
                }

                System.out.printf(" = %.6f%n", x[i]);
            }

            // Sprawdzenie warunku stopu
            double maxRoz = 0.0;
            for (int i = 0; i < n; i++) {
                double roz = Math.abs(x[i] - xPop[i]);
                if (roz > maxRoz) {
                    maxRoz = roz;
                }
            }

            System.out.printf("%nSprawdzanie warunku stopu:%n");
            System.out.printf("max|x_i^(%d) - x_i^(%d)| = %.8f%n",
                    iter, iter-1, maxRoz);
            System.out.printf("Warunek: %.8f < %.8f = %s%n",
                    maxRoz, eps, maxRoz < eps ? "TRUE" : "FALSE");

            if (maxRoz < eps) {
                sukces = true;
                System.out.printf("%nIteracje zakończone po %d krokach.%n", iter);
                break;
            }

            System.out.println("-".repeat(40));
        }

        // Wyniki
        System.out.println("\n" + "=".repeat(80));
        System.out.println("WYNIKI:");
        System.out.println("=".repeat(80));

        if (sukces) {
            System.out.println("✓ Rozwiązanie znalezione");
            System.out.printf("Liczba wykonanych iteracji: %d%n%n", iter);

            System.out.println("ROZWIĄZANIE:");
            for (int i = 0; i < n; i++) {
                System.out.printf("x_%d = %.6f%n", i+1, x[i]);
            }

            System.out.println("\nRozwiązanie dokładne (podane w PDF): x₁ = 1, x₂ = 1, x₃ = 1");

            // Weryfikacja rozwiązania
            System.out.println("\nWERYFIKACJA ROZWIĄZANIA:");

            for (int i = 0; i < n; i++) {
                double suma = 0.0;
                System.out.printf("Równanie %d: ", i+1);
                for (int j = 0; j < n; j++) {
                    suma += a[i][j] * x[j];
                    if (j > 0) System.out.print(" + ");
                    System.out.printf("%.0f*%.6f", a[i][j], x[j]);
                }
                System.out.printf(" = %.6f (oczekiwane: %.0f, błąd: %.6f)%n",
                        suma, b[i], Math.abs(suma - b[i]));
            }

        } else {
            System.out.println("✗ Przekroczono maksymalną liczbę iteracji");
        }
    }
}