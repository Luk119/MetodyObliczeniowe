package Lesson13;
public class MetodaIteracjiProstych {

    public static void main(String[] args) {
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

        System.out.println("Rozwiązanie dla układu:");
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

        // Przekształcanie układu równań
        double[][] h = new double[n][n];
        double[] g = new double[n];

        System.out.println("\nPrzekształcanie układu równań:");

        for (int i = 0; i < n; i++) {
            double aii = a[i][i];

            // Obliczanie g_i = b_i / a_ii
            g[i] = b[i] / aii;
            System.out.printf("g_%d = %.3f / %.3f = %.10f%n", i+1, b[i], aii, g[i]);

            // Obliczanie h_ij
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    h[i][j] = 0.0;
                } else {
                    h[i][j] = -a[i][j] / aii;
                    System.out.printf("h_%d%d = -%.3f / %.3f = %.10f%n",
                            i+1, j+1, a[i][j], aii, h[i][j]);
                }
            }
        }

        System.out.println("\nPrzekształcony układ równań:");
        for (int i = 0; i < n; i++) {
            System.out.printf("x_%d = %.10f", i+1, g[i]);
            for (int j = 0; j < n; j++) {
                if (h[i][j] != 0) {
                    System.out.printf(" + %.10f * x_%d", h[i][j], j+1);
                }
            }
            System.out.println();
        }

        // Sprawdzanie warunku zbieżności
        double normaH = 0.0;

        System.out.println("\nSprawdzanie warunku zbieżności:");

        for (int i = 0; i < n; i++) {
            double suma = 0.0;
            for (int j = 0; j < n; j++) {
                suma += Math.abs(h[i][j]);
            }
            System.out.printf("Wiersz %d: suma = %.10f%n", i+1, suma);

            if (suma > normaH) {
                normaH = suma;
            }
        }

        System.out.printf("||H|| = %.10f%n", normaH);
        System.out.printf("Warunek zbieżności: %.10f < 1.0 = %s%n",
                normaH, normaH < 1.0 ? "true" : "false");

        if (normaH >= 1.0) {
            System.out.println("Warunek zbieżności nie jest spełniony");
            return;
        }

        // Proces iteracji
        double[] x = new double[n];
        double[] xPop = new double[n];

        System.out.println("\nProces iteracji:");
        System.out.println("Przybliżenie początkowe:");
        for (int i = 0; i < n; i++) {
            x[i] = 0.0;
            System.out.printf("x_%d^(0) = %.10f%n", i+1, x[i]);
        }

        int iter = 0;
        boolean sukces = false;

        for (iter = 1; iter <= maxIter; iter++) {
            System.out.printf("\nIteracja %d:%n", iter);

            // Kopiowanie bieżącego przybliżenia do poprzedniego
            for (int i = 0; i < n; i++) {
                xPop[i] = x[i];
            }

            // Obliczanie nowego przybliżenia
            for (int i = 0; i < n; i++) {
                x[i] = g[i];

                for (int j = 0; j < n; j++) {
                    if (h[i][j] != 0) {
                        x[i] += h[i][j] * xPop[j];
                    }
                }

                System.out.printf("x_%d^(%d) = %.10f", i+1, iter, g[i]);
                for (int j = 0; j < n; j++) {
                    if (h[i][j] != 0) {
                        System.out.printf(" + %.10f * %.10f", h[i][j], xPop[j]);
                    }
                }
                System.out.printf(" = %.10f%n", x[i]);
            }

            // Sprawdzenie warunku stopu
            double maxRoz = 0.0;
            for (int i = 0; i < n; i++) {
                double roz = Math.abs(x[i] - xPop[i]);
                if (roz > maxRoz) {
                    maxRoz = roz;
                }
            }

            System.out.printf("max|x_i^(%d) - x_i^(%d)| = %.10f%n", iter, iter-1, maxRoz);
            System.out.printf("Warunek: %.10f < %.10f = %s%n",
                    maxRoz, eps, maxRoz < eps ? "true" : "false");

            if (maxRoz < eps) {
                sukces = true;
                System.out.printf("Iteracje zakończone po %d krokach%n", iter);
                break;
            }
        }

        System.out.println("\nWyniki:");

        if (sukces) {
            System.out.printf("Liczba iteracji: %d%n", iter);

            System.out.println("Rozwiązanie:");
            for (int i = 0; i < n; i++) {
                System.out.printf("x_%d = %.10f%n", i+1, x[i]);
            }

            System.out.println("\nWeryfikacja:");
            for (int i = 0; i < n; i++) {
                double suma = 0.0;
                for (int j = 0; j < n; j++) {
                    suma += a[i][j] * x[j];
                }
                System.out.printf("Równanie %d: %.10f (oczekiwane: %.0f)%n",
                        i+1, suma, b[i]);
            }

        } else {
            System.out.println("Przekroczono maksymalną liczbę iteracji");
        }
    }
}