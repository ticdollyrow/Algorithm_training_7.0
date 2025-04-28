package com.lecture03;

/**
 * C. Переупорядочивание с XOR
 * Задано несколько целых чисел a₁, a₂, ..., aₙ. Запишем их в двоичной системе счисления,
 * дополним меньшие из них ведущими нулями так, чтобы количество цифр в них стало таким же, как в
 * максимальном числе. Требуется переупорядочить биты в них, получив новые числа
 * b₁, b₂, …, bₙ, что b₁ ⨁ b₂ ⨁ … ⨁ bₙ = 0. Операция ⨁ обозначает побитовое исключающее или (xor).
 */


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskC {
    static final String IMPOSSIBLE = "impossible";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());
        String[] binaryStrings = Arrays.stream(reader.readLine().split(" ")).map(Long::parseLong).map(Long::toBinaryString).toArray(String[]::new);
        int maxBits = 0;
        for (int i = 0; i < n; i++) {
            maxBits = Math.max(maxBits, binaryStrings[i].length());
        }

        char[][] newBits = new char[n][maxBits];
        for (int i = 0; i < n; i++) {
            Arrays.fill(newBits[i], '0');
            char[] bits = binaryStrings[i].toCharArray();
            int size = maxBits - 1;
            for (int j = bits.length - 1; j >= 0; j--) {
                newBits[i][size--] = bits[j];
            }
        }

        ArrayList<Integer> ones = new ArrayList<>();
        int countOfOne = 0; // считаем количество единичных битов
        int bitOne = 0;
        int[] resXOR = new int[maxBits];
        for (int col = 0; col < maxBits; col++) {
            bitOne = 0;
            for (int i = 0; i < n; i++) {
                if (newBits[i][col] == '1') countOfOne++;
                bitOne ^= newBits[i][col] - '0';
            }
            resXOR[col] = bitOne;
        }

        if(countOfOne % 2 != 0 || ( n % 2 != 0 && countOfOne > n*maxBits-maxBits) ){
            writer.write(IMPOSSIBLE);
            reader.close();
            writer.close();
            return;
        }

        boolean swith = false;
        for(int col = 0; col < maxBits; col++){
            if(resXOR[col] == 0) continue;

            swith = false;
            for(int i = 0; i < n; i++){
                for(int j = col+1; j < maxBits; j++){
                    if(newBits[i][col] != newBits[i][j]){
                        char tmp = newBits[i][col];
                        newBits[i][col] = newBits[i][j];
                        newBits[i][j] = tmp;
                        swith = true;
                        resXOR[col] ^= 1;
                        resXOR[j] ^= 1;
                        break;
                    }
                }
                if(swith) break;
            }
        }




        for(int i = 0; i < n; i++){
            writer.write(  String.valueOf(Long.parseLong(new String(newBits[i]), 2)) + " ");
        }

        reader.close();
        writer.close();
    }
}
