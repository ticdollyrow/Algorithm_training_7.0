package com.algorithmTraining;

/**
 * Нолики
 */
import java.io.IOException;
import java.io.InputStream;


public class TaskG {
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
        int count;
        int suffix;
        int prefix;
        int countEl;

        Node(int count, int suffix, int prefix, int countEl) {
            this.count = count;
            this.suffix = suffix;
            this.prefix = prefix;
            this.countEl = countEl;
        }

        static Node merge(Node left, Node right) {
            int maxCount = Math.max(left.count, right.count);
            maxCount = Math.max(maxCount, left.suffix + right.prefix);
            int suffix = right.suffix;
            if (right.suffix == right.countEl) {
                suffix += left.suffix;
            }
            int prefix = left.prefix;
            if (left.countEl == left.prefix) {
                prefix += right.prefix;
            }
            int countEl = left.countEl + right.countEl;
            return new Node(maxCount, suffix, prefix, countEl);
        }
    }


    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

//        Scanner fs;
//        String fileName = "src/main/resources/lesson2/task7/input3.txt";
//        // читаем из input.txt
//        try {
//            fs = new Scanner(new File(fileName));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        //N - количество учеников
        int n = fs.nextInt();
        //рейтинги
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
        int val = 0;
        for (int i = 0; i < n; i++) {
            val = arr[i] == 0 ? 1 : 0;
            tree[treeLength - length + i] = new Node(val, val, val, 1);
        }
        for (int i = n; i < length; i++) {
            tree[treeLength - length + i] = new Node(0, 0, 0,1);
        }
        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = Node.merge(tree[2 * i + 1], tree[2 * i + 2]);
        }


        //количество запросов
        int m = fs.nextInt();
        for (int i = 0; i < m; i++) {
            String operation = fs.next();
            if (operation.equals("QUERY")) {
                int l = fs.nextInt()-1;
                int r = fs.nextInt()-1;
                Node query = query(0, 0, length - 1, l, r, tree);
                System.out.println(query.count);
            } else { //присвоить новое значение элементу массива
                int ind = fs.nextInt()-1;
                int x = fs.nextInt();
                int indexInTree = treeLength - length + ind;
                val = x == 0 ? 1 : 0;
                tree[indexInTree] = new Node(val, val, val, 1);

                for (int parentIndex = (indexInTree - 1) / 2; parentIndex > 0; ) {
                    int left = 2 * parentIndex + 1;
                    tree[parentIndex] = Node.merge(tree[left], tree[left + 1]);
                    parentIndex = (parentIndex - 1) / 2;
                }
                if(n > 1){
                    tree[0] = Node.merge(tree[1], tree[2]);
                }
            }
        }
    }


    static Node query(int nodeIndex, int lefIndex, int rightIndex, int leftQueryIndex, int rightQueryIndex, Node[] tree) {
        if (leftQueryIndex > rightIndex || rightQueryIndex < lefIndex) return new Node(0, 0,0,0);
        if (leftQueryIndex <= lefIndex && rightQueryIndex >= rightIndex) return tree[nodeIndex];
        //Запрос может пересекать границу
        int middle = (lefIndex + rightIndex) / 2;
        Node queryLeft = query(2 * nodeIndex + 1, lefIndex, middle, leftQueryIndex, Math.min(middle, rightQueryIndex), tree);
        Node queryRight = query(2 * nodeIndex + 2, middle + 1, rightIndex, Math.max(middle + 1, leftQueryIndex), rightQueryIndex, tree);
        return Node.merge(queryLeft, queryRight);
    }
}
