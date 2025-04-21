package com.algorithmTraining;

/**
 *  Рюкзак: наибольший вес
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskD {
    public static void main(String[] args) {
        Scanner sc;
        // читаем из input.txt
        try {
            sc = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int N = sc.nextInt(); //количество золотых слитков
        int M = sc.nextInt(); //вес

        int[] dp = new int[M+1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for(int i = 0; i < N; i++){
            int m = sc.nextInt();
            int weight = M - m;
            for(int j = weight; j >= 0; j--){
                if(dp[j]>= 0){
                    dp[j+m] = 1;
                }
            }
        }

        for(int i = M; i >= 0; i--){
            if(dp[i] > 0){
                out.println(i);
                break;
            }
        }
        out.close();
    }
}

