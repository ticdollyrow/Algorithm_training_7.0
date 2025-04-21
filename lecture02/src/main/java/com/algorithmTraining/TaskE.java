package com.algorithmTraining;

/**
 * K-й ноль
 */
import java.util.Scanner;

public class TaskE {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String fileName = "src/main/resources/lesson2/task5/input4.txt";
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
        while ( n > 1 && length < n) {
            length *= 2;
        }
        int treeLength = 2 * length - 1;
        Node[] tree = new Node[treeLength];
        int count = length - n;
        while (count > 0) {
            tree[treeLength - count] = new Node(Integer.MIN_VALUE, 0);
            count--;
        }

        int j = treeLength - length;
        for (int i = 0; i < n; i++) {
            tree[j] = new Node(arr[i], arr[i] == 0 ? 1 : 0);
            j++;
        }

        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = merge(tree[2 * i + 1], tree[2 * i + 2]);
        }

        int k = scanner.nextInt(); //количество запросов на вычисление максимума
        int l, r;
        char q;
        int numOfNull;
        // Сначала вводится одна буква, кодирующая вид запроса (s — вычислить индекс k-го нуля, u — обновить значение элемента)
        //Следом за s вводится три числа — левый и правый концы отрезка и число k
        //Следом за u вводятся два числа — номер элемента и его новое значение.
        for (int i = 0; i < k; i++) {
            q = scanner.next().charAt(0);
            if (q == 's') {
                l = scanner.nextInt() - 1;
                r = scanner.nextInt() - 1;
                numOfNull = scanner.nextInt();

                //ищем количество 0 на отрезке до l-1 включительно (M)
                //и тогда ищем (M+numOfNull)
                Node query = query(0, 0, length - 1, 0, l - 1, tree);
                numOfNull += query.count;

                int index = find(0, 0, length - 1, l, r, tree, numOfNull);
                if(index >= 0) {
                    index -= treeLength / 2 - 1;
                }
                System.out.print(index + " ");

            } else {
                l = scanner.nextInt() - 1;
                r = scanner.nextInt();
                int indexInTree = treeLength - length + l;
                tree[indexInTree].maxValue = r;
                if(r == 0){
                    tree[indexInTree].count = 1;
                }else{
                    tree[indexInTree].count = 0;
                }

                int parentIndex = (indexInTree - 1) / 2;
                while (parentIndex > 0 && parentIndex < treeLength) {
                    tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
                    parentIndex = (parentIndex - 1) / 2;
                }
                if( (2 * parentIndex + 2) < treeLength ) {
                    tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
                }

            }
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
        if (a.maxValue == b.maxValue && a.maxValue == 0) {
            return new Node(a.maxValue, a.count + b.count);
        } else if (a.maxValue == 0) {
            return a;
        } else if (b.maxValue == 0) {
            return b;
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

    static int find(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree, int numNull) {
        if (leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return -1;
        if (tree[nodeIndex].count < numNull) return -1;
        if(numNull == 1 && tree[nodeIndex].count == numNull && nodeIndex >= tree.length /2 ) return nodeIndex;


        int middle = (lefIndex + rightIndex) / 2;
        int leftChild = 2 * nodeIndex + 1;
        int rightChild = 2 * nodeIndex + 2;
        int left = -1;
        int right = -1;
        if(leftChild < tree.length && tree[leftChild].count >= numNull){
            left = find(leftChild, lefIndex, middle, leftQueryIndex, Math.min(middle, rightQueryIndex), tree, numNull);
        }else if(rightChild < tree.length){
            numNull -= tree[leftChild].count;
            right = find(rightChild, middle + 1, rightIndex, Math.max(middle + 1, leftQueryIndex), rightQueryIndex, tree, numNull);
        }

        return Math.max(left, right);
    }
}
