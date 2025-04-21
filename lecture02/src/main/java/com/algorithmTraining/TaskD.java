package com.algorithmTraining;

/**
 * Максимум на подотрезках с изменением элемента
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TaskD {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String fileName = "src/main/resources/lesson2/task4/input.txt";
//        // читаем из input.txt
//        try {
//            scanner = new Scanner(new File(fileName));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        // Ввод N — количество элементов в массиве
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int length = 1;
        while (length < n) {
            length *= 2;
        }
        int treeLength = 2 * length - 1;
        Node[] tree = new Node[treeLength];
        int count = length - n;
        while (count > 0) {
            tree[treeLength - count] = new Node(Integer.MIN_VALUE, -1);
            count--;
        }

        int j = treeLength - length;
        for (int i = 0; i < n; i++) {
            tree[j] = new Node(arr[i], i);
            j++;
        }

        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = merge(tree[2 * i + 1], tree[2 * i + 2]);
        }

        int k = scanner.nextInt(); //количество запросов на вычисление максимума
        int l, r;
        char q;
        // Сначала вводится одна буква, кодирующая вид запроса (s — вычислить максимум, u — обновить значение элемента).
        //Следом за s вводятся два числа — номера левой и правой границы отрезка.
        //Следом за u вводятся два числа — номер элемента и его новое значение.
        for (int i = 0; i < k; i++) {
            q = scanner.next().charAt(0);
            l = scanner.nextInt() - 1;
            r = scanner.nextInt();

            if (q == 's') {
                r -= 1;
                Node query = query(0, 0, length - 1, l, r, tree);
                System.out.print(query.maxValue + " ");
            }else{
                int indexInTree = treeLength - length + l;
                tree[indexInTree].maxValue = r;

                int parentIndex = (indexInTree-1) /2;
                while (parentIndex > 0){
                    tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
                    parentIndex = (parentIndex-1) /2;
                }
                tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
            }
        }
    }

    static class Node {
        int maxValue;
        int index;

        Node(int maxValue, int index) {
            this.maxValue = maxValue;
            this.index = index;
        }
    }

    static Node merge(Node a, Node b) {
        if (a.maxValue >= b.maxValue) {
            return a;
        } else {
            return b;
        }
    }


    static Node query(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree) {
        if (leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return new Node(Integer.MIN_VALUE, -1);
        if (leftQueryIndex <= lefIndex && rightQueryIndex >= rightIndex) return tree[nodeIndex];
        //Запрос может пересекать границу
        int middle = (lefIndex + rightIndex) / 2;
        Node queryLeft = query(2 * nodeIndex + 1, lefIndex, middle, leftQueryIndex, Math.min(middle, rightQueryIndex), tree);
        Node queryRight = query(2 * nodeIndex + 2, middle + 1, rightIndex, Math.max(middle + 1, leftQueryIndex), rightQueryIndex, tree);
        return merge(queryLeft, queryRight);
    }
}
