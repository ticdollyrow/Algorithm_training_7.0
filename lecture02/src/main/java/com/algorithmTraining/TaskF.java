package com.algorithmTraining;

/**
 * Ближайшее большее число справа
 */
import java.io.IOException;
import java.io.InputStream;

public class TaskF {


    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();

        int length = 1;
        if(n > 1) {
            length = Integer.highestOneBit(n);
            if (length != n) {
                length <<= 1;
            }
        }
        int treeLength = 2 * length - 1;
        int[] tree = new int[treeLength];

        int addSize = treeLength - length;
        for (int i = 0; i < n; i++) {
            tree[treeLength - length + i] = fs.nextInt();
        }
        for (int i = n; i < length; i++) {
            tree[treeLength - length + i] = Integer.MIN_VALUE; // нейтральный
        }
        // Заполнение внутренних узлов
        for (int i = length - 2; i >= 0; i--) {
            tree[i] = Math.max(tree[2 * i + 1], tree[2 * i + 2]);
        }

        //запросы
        for (int i = 0; i < m; i++){
            int t = fs.nextInt(); //t=0 означает запрос типа set,t=1соответствует запросу типа get
            int ind = fs.nextInt() - 1;
            int x = fs.nextInt();

            if(t == 0){ //присвоить новое значение элементу массива
                int indexInTree = treeLength - length + ind;
                tree[indexInTree] = x;

                for( int parentIndex = (indexInTree-1)/2; parentIndex > 0; ){
                    int left = 2 * parentIndex + 1;
                    tree[parentIndex] = Math.max(tree[left], tree[left+ 1]);
                    parentIndex = (parentIndex - 1) / 2;
                }

            }else{
                int index = query(0, 0, length - 1, tree, ind, x);
                if(index >= 0) {
                    index -= treeLength / 2 - 1;
                }
                System.out.println(index);
            }
        }
    }

    static int query(int currentIndex, int leftIndex, int rightIndex, int[] tree, int queryIndex, int searchElem){
        if(rightIndex < queryIndex || tree[currentIndex] < searchElem ) return -1;
        if (leftIndex == rightIndex) return currentIndex;

        int middle = (leftIndex + rightIndex) /2;
        int leftChild = 2 * currentIndex + 1;
        int rightChild = leftChild + 1;
        int leftMin = query(leftChild, leftIndex, middle, tree, queryIndex, searchElem);
        if(leftMin != -1) return leftMin;
        return query(rightChild, middle + 1, rightIndex, tree, queryIndex, searchElem);
    }


    static final class FastScanner {
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;
        private final InputStream in;
        FastScanner(InputStream in) { this.in = in; }
        int nextInt() throws IOException {
            int c, sign = 1, num = 0;
            do { c = read(); } while (c <= ' ');
            if (c == '-') { sign = -1; c = read(); }
            while (c > ' ') { num = num * 10 + (c - '0'); c = read(); }
            return num * sign;
        }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf); ptr = 0;
                if (len < 0) return -1;
            }
            return buf[ptr++];
        }
    }
}

