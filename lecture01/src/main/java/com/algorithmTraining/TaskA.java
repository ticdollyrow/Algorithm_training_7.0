package com.algorithmTraining;
/**
 * Каждому по компьютеру
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class TaskA {
    public static void main(String[] args) {
        Scanner sc;
        // читаем из input.txt
        try {
            sc = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int N = sc.nextInt();
        int M = sc.nextInt();

        int[][] groups = new int[N][2];
        int[][] rooms = new int[M][2];

        for (int i = 0; i < N; i++) {
            groups[i][0] = i;
            groups[i][1] = sc.nextInt() + 1;
        }

        for (int i = 0; i < M; i++) {
            rooms[i][0] = i;
            rooms[i][1] = sc.nextInt();
        }
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o2[1], o1[1]);
            }
        };

        Arrays.sort(groups,comparator);
        Arrays.sort(rooms,comparator);

        int i =0;
        int j = 0;
        int count = 0;
        int[] result = new int[N];
        while ( i < N && j < M){
            if(groups[i][1] <= rooms[j][1]){
                count++;
                result[groups[i][0]] = rooms[j][0]+1;
                i++;
                j++;
            }else{
                i++;
            }
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        out.println(count);
        for (int r : result) out.print(r + " ");
        out.println();
        out.close();


    }
}

