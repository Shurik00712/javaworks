public class UI {
    public static final java.util.Scanner SC = new java.util.Scanner(System.in);

    public static ComplexMatrix a;
    public static ComplexMatrix b;
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Не целое число");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return Double.parseDouble(line.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Не число");
            }
        }
    }
    public static Complex readComplex() {
        double re = readDouble("  re: ");
        double im = readDouble("  im: ");
        return new Complex(re, im);
    }

    public static ComplexMatrix readMatrix(String name) {
        int rows = readInt("Строк у " + name + ": ");
        int cols = readInt("Столбцов у " + name + ": ");

        Complex[][] data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("Элемент [" + i + "][" + j + "]:");
                data[i][j] = readComplex();
            }
        }
        return new ComplexMatrix(data);
    }
    public static void printMenu() {
        System.out.println();
        System.out.println("=== Меню ===");
        System.out.println("1. Создать матрицу A");
        System.out.println("2. Создать матрицу B");
        System.out.println("3. Показать A");
        System.out.println("4. Показать B");
        System.out.println("5. A + B");
        System.out.println("6. A - B");
        System.out.println("7. A * B");
        System.out.println("8. A / B");
        System.out.println("9. Транспонировать A");
        System.out.println("10. Детерменировать A");
        System.out.println("11. Транспонировать B");
        System.out.println("12. Детерменировать B");
        System.out.println("0. Выход");
    }

    public static void handle(int choice) {
        switch (choice) {
            case 1: {
                a = readMatrix("A");
                break;
            }
            case 2: {
                b = readMatrix("B");
                break;
            }
            case 3: {
                printMatrix(a, "A");
                break;
            }
            case 4: {
                printMatrix(b, "B");
                break;
            }
            case 5: {
                printResult("A + B", requireA().add(requireB()));
                break;
            }
            case 6: {
                printResult("A - B", requireA().sub(requireB()));
                break;
            }
            case 7: {
                printResult("A * B", requireA().mul(requireB()));
                break;
            }
            case 8: {
                printResult("A / B", requireA().div(requireB()));
                break;
            }
            case 9: {
                printResult("Aᵀ", requireA().transpose());
                break;
            }
            case 10: {
                System.out.println("det(A) = " + requireA().determinant());
                break;
            }
            case 11: {
                printResult("Bᵀ", requireB().transpose());
                break;
            }
            case 12: {
                System.out.println("det(B) = " + requireB().determinant());
                break;
            }
            default: {
                System.out.println("Нет такого пункта");
                break;
            }
        }
    }
    public static ComplexMatrix requireA() {
        if (a == null) throw new IllegalStateException("Матрица A не создана");
        return a;
    }

    public static ComplexMatrix requireB() {
        if (b == null) throw new IllegalStateException("Матрица B не создана");
        return b;
    }

    public static void printMatrix(ComplexMatrix m, String name) {
        if (m == null) {
            System.out.println(name + " не создана");
        } else {
            System.out.println(name + " =");
            System.out.println(m);
        }
    }

    public static void printResult(String title, ComplexMatrix m) {
        System.out.println(title + " =");
        System.out.println(m);
    }
    
}
