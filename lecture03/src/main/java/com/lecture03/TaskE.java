package com.lecture03;

/**
 * E. Точки на плоскости
 * Точки с целочисленными координатами из 1-го квадранта помечаются числами0,1,2,… слева направо и снизу вверх таким
 * образом, что очередной точке приписывается минимальное число, отсутствующее в вертикали и горизонтали, проходящей
 * через точку. Первой помечается точка (0,0).
 * Допустим, мы хотим пометить точку (i,j). Это значит, что все точки, находящиеся ниже и левее относительно неё,
 * уже помечены. Тогда рассмотрим набор из чисел в i-й строке и j-м столбце (вместе). Отметкой точки (i,j) будет
 * минимальное неотрицательное число, которое не содержится в этом наборе.
 * Написать программу, которая:
 * По заданным координатам x и y, x≥0, y≥0, x,y — целые, определяет пометку точки.
 * По заданной координате x и пометке точки c, x≥0, y≥0, x, y — целые, определяет вторую координату точки.
 */

import java.io.*;

public class TaskE {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] coordinates = reader.readLine().split(" ");
        long x = Integer.parseInt(coordinates[0]);
        long y = Integer.parseInt(coordinates[1]);

        long mark = x ^ y;
        writer.write(String.valueOf(mark) + "\n");

        coordinates = reader.readLine().split(" ");
        x = Integer.parseInt(coordinates[0]);
        mark = Integer.parseInt(coordinates[1]);
        y = mark ^ x;
        writer.write(String.valueOf(y));

        reader.close();
        writer.close();
    }
}
