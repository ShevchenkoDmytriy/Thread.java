class Matrix {
    private int[][] data;

    public Matrix(int rows, int cols) {
        data = new int[rows][cols];
    }

    public void fillRandom() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (int) (Math.random() * 10);
            }
        }
    }

    public int getRows() {
        return data.length;
    }

    public int getCols() {
        return data[0].length;
    }

    public int getValue(int row, int col) {
        return data[row][col];
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }

    public void display() {
        for (int[] row : data) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}
class MatrixOperation implements Runnable {
    private Matrix matrix1;
    private Matrix matrix2;
    private Matrix result;
    private int row;
    private int col;

    public MatrixOperation(Matrix matrix1, Matrix matrix2, Matrix result, int row, int col) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.result = result;
        this.row = row;
        this.col = col;
    }

    public void run() {
        int sum = 0;
        for (int k = 0; k < matrix1.getCols(); k++) {
            sum += matrix1.getValue(row, k) * matrix2.getValue(k, col);
        }
        result.setValue(row, col, sum);
    }
}


public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(3, 3);
        Matrix matrix2 = new Matrix(3, 3);
        Matrix result = new Matrix(3, 3);

        matrix1.fillRandom();
        matrix2.fillRandom();

        System.out.println("Matrix 1:");
        matrix1.display();
        System.out.println("Matrix 2:");
        matrix2.display();

        int rows = matrix1.getRows();
        int cols = matrix2.getCols();
        Thread[][] threads = new Thread[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                threads[i][j] = new Thread(new MatrixOperation(matrix1, matrix2, result, i, j));
                threads[i][j].start();
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                try {
                    threads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Result Matrix:");
        result.display();
    }
}
