package Lesson2;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Enter 'n' value: ");
        int n = scanner.nextInt();

        double[] xTab = new double[n];
        double[] yTab = new double[n];

        for(int i=0; i<n; i++){
            System.out.print("Enter x" + i + ": ");
            xTab[i] = scanner.nextDouble();

            System.out.print("Enter y" + i + ": ");
            yTab[i] = scanner.nextDouble();
        }

        System.out.print("Enter 'x' that you want me resolve: ");
        double x = scanner.nextDouble();
        scanner.close();

        System.out.println("For x = " + x + ", y = " + interpolacja(n, xTab, yTab, x));
    }

    static double interpolacja(int n, double[] xTab, double[] yTab, double x){

        double y=0.0;
        for(int i=0; i<n; i++){
            double numerator=1.0;
            double denominator=1.0;

            for(int j=0; j<n; j++){
                if(j != i) {
                    numerator *= (x - xTab[j]);
                    denominator *= (xTab[i] - xTab[j]);
                }
            }
            if (denominator == 0){
                throw new ArithmeticException("Division by 0 Error");
            }
            y += yTab[i] * numerator / denominator;
        }


        return y;
    }
}
