package com.lecture03;
/**
 * B. Миссия джедая Ивана
 * Юный джедай Иван был заброшен на Звезду Смерти с заданием уничтожить её. Для того, чтобы
 * уничтожить Звезду Смерти, ему требуется массив неотрицательных целых чисел a
 * длины N. К сожалению, у Ивана нет этого массива, но есть секретный документ с
 * требованиями к этому массиву, который ему передал его старый друг Дарт Вейдер.
 * В этом документе содержится квадратная матрица m размера N, где элемент в
 * i-й строке в j-м равен побитовому "И" чисел a[i] и a[j]. Для повышения безопасности главная
 * диагональ матрицы была уничтожена и вместо чисел на ней были записаны нули. Помогите Ивану
 * восстановить массив a и выполнить свою миссию.
 * Гарантируется, что решение всегда существует. Если решений несколько, выведите любое.
 * <p>
 * Формат вывода
 * В единственной строке выведите N целых неотрицательных чисел, не превышающих
 * 100 — требуемый массив a.
 */

import java.io.*;

public class TaskB {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


        int n = Integer.parseInt(reader.readLine()); //размер матрицы
        int[][] m = new int[n][n];
        int[] a = new int[n];
        //Каждая из последующих N строк содержит по N целых чисел
        for (int i = 0; i < n; i++) {
            String[] parts = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                m[i][j] = Integer.parseInt(parts[j]);
            }
        }
//        каждое число a[i] должно быть побитовым ИЛИ (OR) всех m[i][j], j ≠ i
        for (int i = 0; i < n; i++) {
            int or = 0;
            for (int j = 0; j < n; j++) {
                if(i != j) {    //элементы диагонали не учитываем
                    or = or | m[i][j];
                }
            }
            a[i] = or;

            writer.write(String.valueOf(or) + " ");
        }

        reader.close();
        writer.close();
    }
}
