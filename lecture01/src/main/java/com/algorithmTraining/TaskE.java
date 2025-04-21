package com.algorithmTraining;

/**
 * Рюкзак: наибольшая стоимость
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskE {
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

        int N = sc.nextInt(); //количество предметов
        int M = sc.nextInt(); //вес, который выдерживает рюкзак

        int[] weights = new int[N];
        int[] costs = new int[N];
        //вес предметов
        for(int i = 0; i < N; i++){
            weights[i] = sc.nextInt();
        }
        //цена предметов
        for(int i = 0; i < N; i++){
            costs[i] = sc.nextInt();
        }

        int[] dp = new int[M+1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        int maxCost = 0;

        for(int i = 0; i < N; i++){
            int weight = weights[i];
            int cost = costs[i];

            int w = M - weight;
            for( ;w >= 0; w--){
                if(dp[w] >= 0){
                    dp[w+weight] = Math.max(dp[w+weight], cost + dp[w]);
                    maxCost = Math.max(maxCost, dp[w+weight]);
                }
            }
        }
        out.println(maxCost);
        out.close();
    }
}

