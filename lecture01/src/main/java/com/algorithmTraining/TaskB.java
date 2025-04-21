package com.algorithmTraining;

/**
 * Ни больше ни меньше
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class TaskB {
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


        int T = sc.nextInt();
        for(int t = 0; t < T; t++){
            int N = sc.nextInt();
            int count = 0;
            int length = 0;
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < N; i++){
                int num = sc.nextInt();
                if(count > 0 && length == count){
                    list.add(count);
                    count = 1;
                    length = num;
                    continue;
                }

                if(count == 0){
                    length = num;
                    count++;
                    continue;
                }

                if(length > num && count < num){
                    length = num;
                }else if(length > num ){
                    list.add(count);
                    count = 1;
                    length = num;
                    continue;
                }

                count++;
            }
            if(count > 0) {
                list.add(count);
            }

            out.println(list.size());
            for (int r : list) out.print(r + " ");
            out.println();

        }
        out.close();
    }
}

