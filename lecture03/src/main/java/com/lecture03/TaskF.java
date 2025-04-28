package com.lecture03;

/**
 * F. Трехмерные ладьи
 * Игра в трёхмерные шахматы ведется на кубическом поле N×N×N. Трёхмерная ладья может ходить на любое число клеток
 * по прямой в любом из шести направлений (в любую сторону в каждом из трёх направлений).
 * На таком поле расставлены K ладей. Напишите программу, которая определит, бьют они всё поле или нет.
 *
 * Формат вывода
 * Выведите в выходной файл слово YES, если эти ладьи бьют весь куб, и слово NO в противном случае.
 * В случае NO выведите во второй строке координаты какой-нибудь клетки, которая не бьется ни одной из ладей.
 */

import java.io.*;

public class TaskF {
    static final class FastScanner {
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;
        private final InputStream in;

        FastScanner(InputStream in) {
            this.in = in;
        }

        int nextInt() throws IOException {
            int c, sign = 1, num = 0;
            do {
                c = read();
            } while (c <= ' ');
            if (c == '-') {
                sign = -1;
                c = read();
            }
            while (c > ' ') {
                num = num * 10 + (c - '0');
                c = read();
            }
            return num * sign;
        }


        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len < 0) return -1;
            }
            return buf[ptr++];
        }
    }


    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int k = fs.nextInt();

        boolean[] xyCovered = new boolean[n * n];
        boolean[] yzCovered = new boolean[n * n];
        boolean[] xzCovered = new boolean[n * n];

        int col = 0;

        for (int i = 0; i < k; i++) {
            int x = fs.nextInt() - 1;
            int y = fs.nextInt() - 1;
            int z = fs.nextInt() - 1;

            xyCovered[y * n + z] = true;
            yzCovered[x * n + z] = true;
            xzCovered[x * n + y] = true;

            if (x == y && y == z) {
                col++;
            }
        }

        if (col == n) {
            System.out.println("YES");
            return;
        }

        for (int y = 0; y < n; y++) {
            for (int z = 0; z < n; z++) {
                if (xyCovered[y * n + z]) {
                    continue; // Пропускаем, если точка уже покрыта
                }

                // Проверяем, не покрыты ли плоскости YZ и XZ для текущей точки
                for (int x = 0; x < n; x++) {
                    if (!yzCovered[x * n + z] && !xzCovered[x * n + y]) {
                        System.out.println(("NO"));
                        System.out.println((x + 1) + " " + (y + 1) + " " + (z + 1));
                        return;
                    }
                }
            }
        }

        System.out.println("YES");
    }
}
