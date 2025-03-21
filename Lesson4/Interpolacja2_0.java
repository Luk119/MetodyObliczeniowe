package Lesson4;

import java.util.Locale;
import java.util.Scanner;

public class Interpolacja2_0 {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj ilość argumentów: ");
        int n = scan.nextInt();
        double[][] iloraz = new double[n][n+1];

        for(int i = 0; i < n; i++) {
            System.out.println("Podaj x" + i);
            iloraz[i][0] = scan.nextInt();
            System.out.println("Podaj y" + i);
            iloraz[i][1] = scan.nextDouble();
        }

        for(int i = 2; i < n+1; i++){
            for(int j = n-i; j >= 0; j--){
                iloraz[j][i] = (iloraz[j+1][i-1] - iloraz[j][i-1]) / (iloraz[j+i-1][0] - iloraz[j][0]);

            }
        }
        System.out.print("|  xi  | f(xi)");
        for(int i=1; i<n; i++){
            System.out.print("|Rząd " + i);
        }
        System.out.print("|\n");
        System.out.println("  - - - - - - - - - - - - - - - - - - - - ");

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n+1-i; j++) {
                if(iloraz[i][j] < 0){
                    System.out.print("|" + String.format(Locale.US, "%.2f", iloraz[i][j]) + " ");
                }
                else {
                    System.out.print("| " + String.format(Locale.US, "%.2f", iloraz[i][j]) + " ");
                }
            }
            System.out.println("|");
        }

    }
}
