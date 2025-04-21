package com.algorithmTraining;

/**
 * Рюкзак: наибольшая стоимость с восстановлением ответа
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskF {
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
        for (int i = 0; i < N; i++) {
            weights[i] = sc.nextInt();
        }
        //цена предметов
        for (int i = 0; i < N; i++) {
            costs[i] = sc.nextInt();
        }

        int[][] dp = new int[2][M + 1];
        Arrays.fill(dp[0], -1);
        Arrays.fill(dp[1], -1);
        dp[0][0] = 0; //цена
        dp[1][0] = -1; //индекс элемента

        List<int[][]> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int weight = weights[i];
            int cost = costs[i];
            for (int j = M - weight; j >= 0; j--) {
                if (dp[0][j] >= 0) {
                    if (dp[0][j + weight] < cost + dp[0][j]) {
                        dp[0][j + weight] = cost + dp[0][j];
                        dp[1][j + weight] = i;
                    }
                }
            }

            int[][] deepCopy = new int[dp.length][];
            for (int k = 0; k < dp.length; k++) {
                deepCopy[k] = Arrays.copyOf(dp[k], dp[k].length);
            }
            list.add(deepCopy);
        }

        int maxCost = 0;
        int index = -1;
        int weight = 0;
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i <= M; i++) {
            if (dp[0][i] > maxCost) {
                maxCost = dp[0][i];
                index = dp[1][i];
                weight = i - weights[index];
            }
        }
        if(index>=0) {
            result.add(index + 1);
        }

        while (weight > 0) {
            for (int i = index - 1; i >= 0; i--) {
                dp = list.get(i);
                if (dp[0][weight] >= 0) {
                    index = dp[1][weight];
                    weight -= weights[index];
                    break;
                }
            }
            result.add(index + 1);
        }

        for(int m:result.reversed()){
            out.println(m);
        }

        out.close();
    }
}


