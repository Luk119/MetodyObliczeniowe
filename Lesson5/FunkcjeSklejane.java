package Lesson5;

public class FunkcjeSklejane {

    static double poly(double[] a, double x, boolean isDerivative) {
        double result = isDerivative ? a[1] : a[0];
        for (int i = 1; i < a.length; i++) {
            double term = isDerivative ? i * a[i] * Math.pow(x, i - 1) : a[i] * Math.pow(x, i);
            result += term;
        }
        return result;
    }

    static double alphaTerm(double x, double xi, double alpha, boolean isDerivative) {
        return isDerivative ? 3 * alpha * Math.pow(x - xi, 2) : alpha * Math.pow(x - xi, 3);
    }

    static double spline(double[] x, double[] y, double[] d, double atX) {
        int n = x.length;
        double[] a = new double[4];
        double[] alpha = new double[n - 2];

        double[][] A = new double[n + 2][a.length + alpha.length];
        double[] B = new double[n + 2];

        for (int i = 0; i < n; i++) {
            double[] row = new double[a.length + alpha.length];
            for (int j = 0; j < a.length; j++) {
                row[j] = Math.pow(x[i], j);
            }
            for (int j = 1; j < i; j++) {
                row[a.length + j - 1] = Math.pow(x[i] - x[j], 3);
            }
            A[i] = row;
            B[i] = y[i];
        }

        double[] rowStart = new double[a.length + alpha.length];
        rowStart[1] = 1;
        for (int i = 2; i < a.length; i++) {
            rowStart[i] = i * Math.pow(x[0], i - 1);
        }
        A[n] = rowStart;
        B[n] = d[0];

        double[] rowEnd = new double[a.length + alpha.length];
        rowEnd[1] = 1;
        for (int i = 2; i < a.length; i++) {
            rowEnd[i] = i * Math.pow(x[n - 1], i - 1);
        }
        for (int i = 0; i < alpha.length; i++) {
            rowEnd[a.length + i] = 3 * Math.pow(x[n - 1] - x[i + 1], 2);
        }
        A[n + 1] = rowEnd;
        B[n + 1] = d[1];

        double[] solution = solve(A, B);
        System.arraycopy(solution, 0, a, 0, a.length);
        System.arraycopy(solution, a.length, alpha, 0, alpha.length);

        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * Math.pow(atX, i);
        }
        for (int i = 0; i < alpha.length; i++) {
            if (atX > x[i + 1]) {
                result += alpha[i] * Math.pow(atX - x[i + 1], 3);
            } else {
                break;
            }
        }

        return result;
    }

    static double[] solve(double[][] A, double[] B) {
        int n = B.length;
        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i])) max = j;
            }

            double[] tempRow = A[i];
            A[i] = A[max];
            A[max] = tempRow;

            double tempVal = B[i];
            B[i] = B[max];
            B[max] = tempVal;

            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                B[j] -= factor * B[i];
                for (int k = i; k < A[0].length; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        double[] x = new double[A[0].length];
        for (int i = n - 1; i >= 0; i--) {
            double sum = B[i];
            for (int j = i + 1; j < A[0].length; j++) {
                sum -= A[i][j] * x[j];
            }
            x[i] = sum / A[i][i];
        }
        return x;
    }

    public static void main(String[] args) {
        double[] x = {-4, -2, 0, 2, 4};
        double[] y = {-618, -42, -2, -18, -378};
        double[] d = {598, -410};

        double result = spline(x, y, d, 3);
        System.out.println("Wartosc w punkcie 3 = " + result);
    }
}
