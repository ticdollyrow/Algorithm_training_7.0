package com.algorithmTraining;

/**
 * Две стены
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class TaskG {
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

        int N = sc.nextInt(); //кирпичи
        int K = sc.nextInt(); //цвета

        HashMap<Integer, ArrayList<Block>> blocks = new HashMap<>();
        // у Пети не более 50 кирпичиков каждого цвета
        // Петя знает, что он может построить из кирпичиков прямоугольную стену толщиной 1 и высотой K
        //=> общая длина для каждого цвета одинаковая
        int maxLength = 0;
        for (int i = 0; i < N; i++) {
            int length = sc.nextInt();
            int color = sc.nextInt();
            if (color == K) maxLength += length;

            ArrayList<Block> list = blocks.getOrDefault(color, new ArrayList<Block>());
            list.add(new Block(i+1, length));
            blocks.put(color, list);
        }

        HashMap<Integer, int[]> dpMap = new HashMap<>();
        HashMap<Integer, int[][]> dpMapIndex = new HashMap<>();
        int[]dp = new int[maxLength + 1];


        for (Map.Entry<Integer, ArrayList<Block>> map : blocks.entrySet()) {
            ArrayList<Block> value = map.getValue();
            int[][] path = new int[maxLength + 1][];
            Arrays.fill(dp, -1);
            dp[0] = 0;
            path[0] = new int[0];

            for (int i = 0; i < value.size(); i++) {
                Block block = value.get(i);
                for (int j = maxLength - block.length; j >= 0; j--) {
                    if (dp[j] >= 0) {
                        if (dp[j + block.length] > 1 + dp[j] || dp[j + block.length] == -1) {
                            dp[j + block.length] = 1 + dp[j];

                            if((maxLength + 1) / 2 < j + block.length) continue;
                            int[] prevPath = path[j];
                            int[] newPath = Arrays.copyOf(prevPath, prevPath.length + 1);
                            newPath[prevPath.length] = block.index;
                            path[j + block.length] = newPath;
                        }
                    }
                }
            }

            dpMap.put(map.getKey(), dp.clone());
            dpMapIndex.put(map.getKey(), path);
        }

        boolean flag = false;
        int firstLength = 0;
        final int[]ints = dpMap.get(1); //считывает первый цвет
        for (int i = 0; i <= (maxLength + 1) / 2; i++) { // i - длина
            if (ints[i] > 0 && ints[maxLength - i] > 0) { //количество кирпичей
                //можно построить 2 полосы этого цвета
                //нужно проверить другие цвета
                flag = true;
                firstLength = i; // длина первой стены
                int k = 2;
                while (k <= K) {
                    final int[] ints1 = dpMap.get(k);
                    if (ints1[i] > 0
                            && ints1[maxLength - i] > 0) {
                        k++;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
        }

        String answer = flag ? "YES" : "NO";
        out.println(answer);
        
        if(flag) {
            for (Map.Entry<Integer, int[][]> map : dpMapIndex.entrySet()) {
                int[][] value = map.getValue();
                int[] list = value[firstLength];
                for (int i = 0; i < list.length; i++) {
                    out.print(list[i] + " ");
                }
            }
        }

        out.close();
    }


    static class Block {
        int index;
        int length;

        Block(int index, int length) {
            this.index = index;
            this.length = length;
        }
    }
}







