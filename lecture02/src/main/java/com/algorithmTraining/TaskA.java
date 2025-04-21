package com.algorithmTraining;

/**
 * Количество максимумов на отрезке
 */
import java.util.Scanner;

public class TaskA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод N — количество элементов в массиве
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = scanner.nextInt();
        }

        int length = 1;
        while (length < n){
            length *= 2;
        }
        int treeLength = 2*length-1;
        Node[] tree = new Node[treeLength];
        int count = length - n;
        while (count > 0){
            tree[treeLength - count] = new Node(0,0);
            count--;
        }

        int j = treeLength - length;
        for (int i = 0; i < n; i++){
            tree[j] = new Node(arr[i] , 1);
            j++;
        }

        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = merge(tree[2 * i + 1], tree[2 * i + 2]);
        }


        int k = scanner.nextInt(); //количество запросов на вычисление максимума
        int l,r;             // номера левого и правого элементов отрезка
        for(int i = 0; i < k; i++){
            l = scanner.nextInt() - 1;
            r = scanner.nextInt() - 1;

            Node query = query(0, 0, length-1, l, r, tree);
            System.out.println(query.maxValue + " " + query.count);
        }
    }

    static class Node {
        int maxValue;
        int count;

        Node(int maxValue, int count) {
            this.maxValue = maxValue;
            this.count = count;
        }
    }

    static Node merge(Node a, Node b) {
        if (a.maxValue == b.maxValue) {
            return new Node(a.maxValue, a.count + b.count);
        } else if (a.maxValue > b.maxValue) {
            return a;
        } else {
            return b;
        }
    }


    static Node query(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree){
        if(leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return new Node(0,0);
        if (leftQueryIndex <= lefIndex && rightQueryIndex >= rightIndex)  return tree[nodeIndex];
        //Запрос может пересекать границу
        int middle = (lefIndex + rightIndex) / 2;
        Node queryLeft = query(2 * nodeIndex + 1, lefIndex, middle , leftQueryIndex, Math.min(middle,rightQueryIndex), tree);
        Node queryRight = query(2 * nodeIndex + 2, middle+ 1, rightIndex, Math.max(middle+1, leftQueryIndex), rightQueryIndex, tree);
        return merge(queryLeft,queryRight);
    }

}
