package Lesson5;

import java.util.Scanner;

public class FunkcjeSklejane2_0 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double[] xValues = {-4, -2, 0, 2, 4};
        double[] yValues = {-618, -42, -2, -18, -378};
        double[] derivatives = {598, -410};

        System.out.print("Podaj wartość x: ");
        double x = scanner.nextDouble();

        System.out.println("S(" + x + ") = " + splineFunction(xValues, yValues, derivatives, x));

        scanner.close();
    }

    private static double exprW(double[] a, double x, boolean poch) {
        double result = poch ? a[1] : a[0];
        int start = poch ? 2 : 1;
        for (int i = start; i < a.length; i++) {
            if (poch) {
                result += i * a[i] * Math.pow(x, i - 1);
            } else {
                result += a[i] * Math.pow(x, i);
            }
        }
        return result;
    }

    private static double alphaS(double x, double xs, double alpha, boolean poch) {
        return poch ? 3 * alpha * Math.pow(x - xs, 2) : alpha * Math.pow(x - xs, 3);
    }

    private static double splineFunction(double[] xValues, double[] yValues, double[] derivatives, double lookingFor) {
        int n = xValues.length;
        int numA = 4;
        int numAlpha = n - 2;
        int totalSymbols = numA + numAlpha;

        double[][] A = new double[totalSymbols][totalSymbols];
        double[] B = new double[totalSymbols];

        for (int i = 0; i < n; i++) {
            double x = xValues[i];

            for (int j = 0; j < numA; j++) {
                A[i][j] = Math.pow(x, j);
            }

            for (int j = 1; j < i; j++) {
                if (j - 1 < numAlpha) {
                    A[i][numA + (j - 1)] = Math.pow(x - xValues[j], 3);
                }
            }

            B[i] = yValues[i];
        }

        int row = n;
        double x = xValues[0];
        for (int j = 0; j < numA; j++) {
            A[row][j] = (j == 0) ? 0 : j * Math.pow(x, j - 1);
        }

        B[row] = derivatives[0];
        row++;
        x = xValues[n - 1];
        for (int j = 0; j < numA; j++) {
            A[row][j] = (j == 0) ? 0 : j * Math.pow(x, j - 1);
        }
        for (int j = 1; j < n; j++) {
            if (j - 1 < numAlpha) {
                A[row][numA + (j - 1)] = 3 * Math.pow(x - xValues[j], 2);
            }
        }
        B[row] = derivatives[1];

        double[] solution = solveLinearSystem(A, B);

        double[] a = new double[numA];
        double[] alpha = new double[numAlpha];
        System.arraycopy(solution, 0, a, 0, numA);
        System.arraycopy(solution, numA, alpha, 0, numAlpha);

        double result = exprW(a, lookingFor, false);
        for (int i = 0; i < numAlpha; i++) {
            if (lookingFor > xValues[i + 1]) {
                result += alphaS(lookingFor, xValues[i + 1], alpha[i], false);
            } else {
                break;
            }
        }

        return result;
    }

    private static double[] solveLinearSystem(double[][] A, double[] B) {
        int n = B.length;

        for (int p = 0; p < n; p++) {
            int maxRow = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[maxRow][p])) {
                    maxRow = i;
                }
            }

            if (maxRow != p) {
                double[] tempRow = A[p];
                A[p] = A[maxRow];
                A[maxRow] = tempRow;

                double tempVal = B[p];
                B[p] = B[maxRow];
                B[maxRow] = tempVal;
            }

            for (int i = p + 1; i < n; i++) {
                double factor = A[i][p] / A[p][p];
                B[i] -= factor * B[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= factor * A[p][j];
                }
            }
        }

        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * solution[j];
            }
            solution[i] = (B[i] - sum) / A[i][i];
        }

        return solution;
    }
}