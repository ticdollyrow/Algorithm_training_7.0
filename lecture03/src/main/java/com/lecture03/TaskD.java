package com.lecture03;

/**
 * D. Забавная игра
 * Легендарный учитель математики Юрий Петрович придумал забавную игру с числами. А именно, взяв произвольное целое
 * число, он переводит его в двоичную систему счисления, получая некоторую последовательность из нулей и единиц,
 * начинающуюся с единицы.
 * Затем учитель начинает сдвигать цифры полученного двоичного числа по циклу так, что последняя цифра становится
 * первой, а все остальные сдвигаются на одну позицию вправо. Выписывая образующиеся при этом последовательности из
 * нулей и единиц в столбик — он подметил, что независимо от выбора исходного числа получающиеся последовательности
 * начинают с некоторого момента повторяться.
 * И, наконец, Юрий Петрович отыскивает максимальное из выписанных чисел и переводит его обратно в десятичную систему
 * счисления, считая это число результатом проделанных манипуляций. Так, для числа 19 список последовательностей будет
 * таким:
 * 10011
 * 11001
 * 11100
 * 01110
 * 00111
 * 10011
 * .....
 * и результатом игры, следовательно, окажется число 1×2⁴ + 1×2³ + 1×2² + 0×2¹ + 0×2⁰ = 16 + 8 + 4 + 0 + 0 = 28
 * Поскольку придуманная игра с числами всё больше занимает воображение учителя, отвлекая тем самым его от работы с
 * ну очень одарёнными школьниками, вас просят написать программу, которая помогла бы Юрию Петровичу получать результат
 * игры без утомительных ручных вычислений.
 */

import java.io.*;


public class TaskD {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


        int num = Integer.parseInt(reader.readLine());
        final String binaryString = Integer.toBinaryString(num);
        final char[] charArray = binaryString.toCharArray();

        int index = 0;
        int max = num;
        int j = charArray.length;
        char tmp;
        while (j-- > 0) {
            tmp = charArray[0];
            for (int i = 1; i <= charArray.length; i++) {
                index = i;
                if (index == charArray.length) index = 0;
                char tmp1 = charArray[index];
                charArray[index] = tmp;
                tmp = tmp1;
            }
            int i1 = Integer.parseInt(new String(charArray), 2);
            max = Math.max(max, i1);
        }

        writer.write(String.valueOf(max));
        reader.close();
        writer.close();
    }
}
