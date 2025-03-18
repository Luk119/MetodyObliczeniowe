package Lesson4;

import java.util.Scanner;

public class Interpolacja {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj ilość argumentów: ");
        int n = scan.nextInt();

        int[] x = new int[n];
        int[] y = new int[n];

        for(int i = 0; i < n; i++) {
            System.out.println("Podaj x" + i);
            x[i] = scan.nextInt();
            System.out.println("Podaj y" + i);
            y[i] = scan.nextInt();
        }

        double[][] iloraz = new double[n-1][n-1];

        double[] Irzad = new double[n-1];
        for(int i = n-1; i > 0; i--) {
            Irzad[i-1] = (double) (y[i] - y[i-1]) /(x[i] - x[i-1]);
            iloraz[0][i-1] = Irzad[i-1];
        }

        for(int i = 0; i < n-1; i++) {
            for(int j = 0; j < n-1; j++) {
                System.out.println(iloraz[i][j]);
            }
        }




//        for(int i = 0; i < n-1; i++) {
//            System.out.println(Irzad[i]);
//        }



//        for(int i = n-2; i > 0; i--) {
//            for(int j = 0; j < i; j++) {
//                System.out.print("*");
//            }
//            System.out.println();
//        }

    }
}
