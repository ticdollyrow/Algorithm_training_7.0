package com.algorithmTraining;

/**
 * Интернет
 */

import java.io.*;
import java.util.*;

public class TaskC {

    public static void main(String[] args) {
        String fileName = "input.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            PrintWriter out = null;
            try {
                out = new PrintWriter("output.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Чтение числа M
            int M = Integer.parseInt(reader.readLine());

            // Чтение второй строки с числами
            String line = reader.readLine();
            String[] tokens = line.split(" ");

            //сохранили самые дешевые карточки
            List<long[]> cards = new ArrayList<>();
            HashSet<Long> secondsSet = new HashSet<>();
            for (int i = 0; i < tokens.length; i++) {
                long seconds = Integer.parseInt(tokens[i]);
                if(! secondsSet.contains(seconds)) {
                    int cost = 1 << i;
                    secondsSet.add(seconds);
                    cards.add(new long[]{seconds, cost});
                }
            }

            // Сортируем по выгодности: cost/second — по возрастанию
            cards.sort(Comparator.comparingDouble(card -> (double) card[1] / card[0]));
            long totalCost = 0;
            long totalSec = 0;
            long totalSec1 = 0;
            long cost = Long.MAX_VALUE;

            for(int i = 0; i < cards.size(); i++){
                long[] card = cards.get(i);
                long count = (M - totalSec + card[0] - 1) / card[0];
                totalSec += count * card[0];
                totalCost += count * card[1];

                cost = Math.min(cost, totalCost);


                if(totalSec > M){
                    totalSec -= card[0];
                    totalCost -= card[1];
                }
            }

            cost = Math.min(cost, totalCost);
            out.println(cost);
            System.out.println(cost);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
