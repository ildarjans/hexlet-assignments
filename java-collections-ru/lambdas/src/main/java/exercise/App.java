package exercise;

import java.util.Arrays;

// BEGIN
class App {
    public static void main(String[] args) {}

    public static String[][] enlargeArrayImage(String[][] arr) {
        int rowsLen = arr.length * 2;
        int columnsLen = arr[0].length * 2;
        String[][] result = new String[rowsLen][columnsLen];

        for (int row = 0; row < rowsLen; row++) {
            int i = (int)Math.floor(row / 2);
            for (int col = 0; col < columnsLen; col++) {
                int j = (int)Math.floor(col / 2);

                result[row][col] = arr[i][j];
            }
        }

        return result;
    }
}

// END
