package com.algorithmTraining;

/**
 * Максимум на подотрезках с добавлением на отрезке
 */
import java.io.IOException;
import java.io.InputStream;


public class TaskI {
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

        String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');
            StringBuilder sb = new StringBuilder();
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int[] nextIntArray(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
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


    static class Node {
        long maxValue;
        long add;

        Node(long maxValue) {
            this.maxValue = maxValue;
            this.add = 0;
        }

        Node(long maxValue, long add) {
            this.maxValue = maxValue;
            this.add = add;
        }
    }

    static Node merge(Node a, Node b) {
        long max = Math.max(a.maxValue, b.maxValue);
        return new Node(max);
    }

    static Node query(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree) {
        if (leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return new Node(Integer.MIN_VALUE);
        if(tree[nodeIndex].add != 0 && (2 * nodeIndex + 2) < tree.length){
            tree[2 * nodeIndex + 1].maxValue += tree[nodeIndex].add;
            tree[2 * nodeIndex + 1].add += tree[nodeIndex].add;
            tree[2 * nodeIndex + 2].maxValue += tree[nodeIndex].add;
            tree[2 * nodeIndex + 2].add += tree[nodeIndex].add;
            tree[nodeIndex].add = 0;
        }

        if (leftQueryIndex <= lefIndex && rightQueryIndex >= rightIndex) return tree[nodeIndex];
        //Запрос может пересекать границу
        int middle = (lefIndex + rightIndex) / 2;
        Node queryLeft = query(2 * nodeIndex + 1, lefIndex, middle, leftQueryIndex, Math.min(middle, rightQueryIndex), tree);
        Node queryRight = query(2 * nodeIndex + 2, middle + 1, rightIndex, Math.max(middle + 1, leftQueryIndex), rightQueryIndex, tree);
        return merge(queryLeft, queryRight);
    }

    static void swear(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree, int add){
        if (leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return;
        if (leftQueryIndex <= lefIndex && rightQueryIndex >= rightIndex){
            tree[nodeIndex].maxValue += add;
            tree[nodeIndex].add += add;

            int parentIndex = (nodeIndex-1) /2;
            while (parentIndex > 0){
                long parentAdd = tree[parentIndex].add;
                tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
                tree[parentIndex].maxValue += parentAdd;
                tree[parentIndex].add = parentAdd;
                parentIndex = (parentIndex-1) /2;
            }
            long parentAdd = tree[parentIndex].add;
            tree[parentIndex] = merge(tree[2 * parentIndex + 1], tree[2 * parentIndex + 2]);
            tree[parentIndex].maxValue += parentAdd;
            tree[parentIndex].add = parentAdd;

            return;
        }
        int middle = (lefIndex + rightIndex) / 2;
        swear(2 * nodeIndex + 1, lefIndex, middle, leftQueryIndex, Math.min(middle, rightQueryIndex), tree, add);
        swear(2 * nodeIndex + 2, middle + 1, rightIndex, Math.max(middle + 1, leftQueryIndex), rightQueryIndex, tree, add);
    }


    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

//        Scanner fs;
//        String fileName = "src/main/resources/lesson2/task9/input.txt";
//        // читаем из input.txt
//        try {
//            fs = new Scanner(new File(fileName));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        //количество чисел в массиве
        int n = fs.nextInt();
        //элементы массива
        int[] arr = fs.nextIntArray(n);
//        int[] arr = new int[n];
//        for (int i = 0; i < n; i++) {
//            arr[i] = fs.nextInt();
//        }

        int length = 1;
        if (n > 1) {
            length = Integer.highestOneBit(n);
            if (length != n) {
                length <<= 1;
            }
        }
        int treeLength = 2 * length - 1;
        Node[] tree = new Node[treeLength];
        for (int i = 0; i < n; i++) {
            tree[treeLength - length + i] = new Node(arr[i]);
        }
        for (int i = n; i < length; i++) {
            tree[treeLength - length + i] = new Node(Integer.MIN_VALUE);
        }
        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = merge(tree[2 * i + 1], tree[2 * i + 2]);
        }

        //количество запросов
        int m = fs.nextInt();
        for(int i = 0; i < m; i++){
            char operation = fs.next().charAt(0);
            int l = fs.nextInt() - 1;
            int r = fs.nextInt() - 1;
            if(operation == 'a'){ //увеличить все элементы на отрезке
                int add = fs.nextInt();
                swear(0,0,length-1, l,r, tree, add);
            }else{ //найти максимум
                Node query = query(0, 0, length - 1, l, r, tree);
                System.out.print(query.maxValue + " ");
            }
        }
    }
}

