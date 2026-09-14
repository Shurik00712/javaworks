public class ComplexMatrix {
    private Complex[][] data;
    private final int rows;
    private final int cols;
    private static final Complex ZERO = new Complex(0, 0);

    public boolean isSquare() {
        return rows == cols;
    }
    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = ZERO;
            }
        }
    }
    ComplexMatrix(Complex[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Complex get(int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IndexOutOfBoundsException("Вышел за границы");
        }
        return data[i][j];
    }
    public void set(int i, int j, Complex value) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IndexOutOfBoundsException("Вышел за границы");
        }
        data[i][j] = value;
    }
    @Override
    public String toString() {
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            String[] line = new String[cols];
            for (int j = 0; j < cols; j++) {
                line[j] = String.format("%12s", data[i][j].toString());
            }
            lines[i] = String.join("", line);
        }
        return String.join("\n", lines)+"\n";
    }
    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].add(other.data[i][j]));
            }
        }
        return result;
    }
    public ComplexMatrix sub(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].sub(other.data[i][j]));
            }
        }
        return result;
    }
    public ComplexMatrix mul(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Несогласованные размеры матриц");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int t = 0; t < this.cols; t++) {
                    sum = sum.add(this.data[i][t].mul(other.data[t][j]));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }
    public ComplexMatrix scalarMul(Complex k) {
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, data[i][j].mul(k));
            }
        }
        return result;
    }
    public ComplexMatrix div(ComplexMatrix other) {
        if (!other.isSquare()) {
            throw new IllegalArgumentException("Делитель не квадрат");
        }
        return this.mul(other.inverse());
    }
    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, data[i][j]);
            }
        }
        return result;
    }
    public ComplexMatrix inverse() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Матрица не квадратная");
        }
        int n = rows;
        Complex[][] aug = new Complex[n][2 * n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(data[i], 0, aug[i], 0, n);
            for (int j = n; j < 2*n; j++) {
                aug[i][j] = (i == j-n) ? new Complex(1, 0) : new Complex(0, 0);
            }
        }

        final double EPS = 1e-12;

        for (int col = 0; col < n; col++) {
            int pivotRow = col;
            double maxAbs = aug[col][col].abs();
            for (int r = col + 1; r < n; r++) {
                double a = aug[r][col].abs();
                if (a > maxAbs) {
                    maxAbs = a;
                    pivotRow = r;
                }
            }
            if (maxAbs < EPS) {
                throw new ArithmeticException("Матрица вырождена");
            }
            if (pivotRow != col) {
                Complex[] tmp = aug[col];
                aug[col] = aug[pivotRow];
                aug[pivotRow] = tmp;
            }

            Complex pivot = aug[col][col];
            for (int j = 0; j < 2 * n; j++) {
                aug[col][j] = aug[col][j].div(pivot);
            }

            for (int r = 0; r < n; r++) {
                if (r == col) continue;
                Complex factor = aug[r][col];
                if (factor.isZero()) continue;
                for (int j = 0; j < 2 * n; j++) {
                    aug[r][j] = aug[r][j].sub(factor.mul(aug[col][j]));
                }
            }
        }

        Complex[][] inv = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(aug[i], n, inv[i], 0, n);
        }
        return new ComplexMatrix(inv);
    }
    public Complex determinant() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Матрица не квадратная");
        }
        int n = rows;

        Complex[][] a = new Complex[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(data[i], 0, a[i], 0, n);
        }

        final double EPS = 1e-12;
        int sign = 1;

        for (int col = 0; col < n; col++) {

            int pivotRow = col;
            double maxAbs = a[col][col].abs();
            for (int r = col + 1; r < n; r++) {
                double cur = a[r][col].abs();
                if (cur > maxAbs) {
                    maxAbs = cur;
                    pivotRow = r;
                }
            }
            if (maxAbs < EPS) {
                return new Complex(0, 0);
            }
            if (pivotRow != col) {
                Complex[] tmp = a[col];
                a[col] = a[pivotRow];
                a[pivotRow] = tmp;
                sign = -sign;
            }
            Complex pivot = a[col][col];
            for (int r = col + 1; r < n; r++) {
                Complex factor = a[r][col].div(pivot);
                for (int j = col; j < n; j++) {
                    a[r][j] = a[r][j].sub(factor.mul(a[col][j]));
                }
            }
        }

        Complex det = new Complex(1, 0);
        for (int i = 0; i < n; i++) {
            det = det.mul(a[i][i]);
        }
        if (sign < 0) {
            det = det.mul(new Complex(-1, 0));
        }
        return det;
    }
}