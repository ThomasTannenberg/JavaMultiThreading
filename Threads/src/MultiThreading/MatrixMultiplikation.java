package MultiThreading;

import java.util.Random;

public class MatrixMultiplikation extends AbstraktesProgramm {

    private final Random random;
    private final GUI gui;
    private final int dimension;

    public MatrixMultiplikation(int programmNummer, ErgebnisListener listener, GUI gui, int dimension) {
        super(programmNummer, listener);
        this.random = new Random();
        this.gui = gui;
        this.dimension = dimension;
    }

    @Override
    protected String berechneAlgorithmus() {
        int[][] matrixA = generiereZufaelligeMatrix(dimension, dimension);
        int[][] matrixB = generiereZufaelligeMatrix(dimension, dimension);
        int[][] matrixC = multipliziereMatrizen(matrixA, matrixB);

        return "Matrix A: \n" + matrixAlsString(matrixA) +
                "\nMatrix B: \n" + matrixAlsString(matrixB) +
                "\nMatrix C (A*B):\n" + matrixAlsString(matrixC);
    }

    private int[][] generiereZufaelligeMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }
        return matrix;
    }

    private int[][] multipliziereMatrizen(int[][] a, int[][] b) {
        int[][] result = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    private String matrixAlsString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int val : row) {
                sb.append(val).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }

    @Override
    protected String formatiereErgebnis(String ergebnis) {
        return String.format("Programm %d - Ergebnis einer Matrixmultiplikation:\n%s", programmNummer, ergebnis);
    }
}

