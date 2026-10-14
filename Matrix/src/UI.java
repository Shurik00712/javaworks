public class UI {
    public static final java.util.Scanner SC = new java.util.Scanner(System.in);

    public static ComplexMatrix a;
    public static ComplexMatrix b;
    public static ComplexMatrix r;

    public static String readLine(String request) {
        System.out.print(request);
        return SC.nextLine();
    }

    public static int readInt(String request) {
        while (true) {
            String line = readLine(request).trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: нужно целое число. Повторите ввод.");
            }
        }
    }

    public static double readDouble(String request) {
        while (true) {
            String line = readLine(request).trim().replace(',', '.');
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: нужно число. Повторите ввод.");
            }
        }
    }

    public static Complex readComplex() {
        double re = readDouble("    действительная часть: ");
        double im = readDouble("    мнимая часть:        ");
        return new Complex(re, im);
    }

    public static ComplexMatrix readMatrix(String name) {
        System.out.println();
        System.out.println("--- Ввод матрицы " + name + " ---");
        int rows = readInt("  Строк: ");
        while (rows <= 0) {
            System.out.println("  Ошибка: число строк должно быть > 0.");
            rows = readInt("  Строк: ");
        }
        int cols = readInt("  Столбцов: ");
        while (cols <= 0) {
            System.out.println("  Ошибка: число столбцов должно быть > 0.");
            cols = readInt("  Столбцов: ");
        }

        Complex[][] data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("  Элемент [" + i + "][" + j + "]:");
                data[i][j] = readComplex();
            }
        }
        ComplexMatrix m = new ComplexMatrix(data);
        System.out.println("  Матрица " + name + " создана.");
        return m;
    }

    public static void printMenu() {
        System.out.println();
        System.out.println("==================== МЕНЮ ====================");
        System.out.println("  Создание и просмотр");
        System.out.println("    1. Создать матрицу A");
        System.out.println("    2. Создать матрицу B");
        System.out.println("    3. Показать A");
        System.out.println("    4. Показать B");
        System.out.println("    5. Показать результат R");
        System.out.println("  Арифметика A и B (сохраняется в R)");
        System.out.println("    6. A + B");
        System.out.println("    7. A - B");
        System.out.println("    8. A * B");
        System.out.println("    9. A / B");
        System.out.println("  Унарные операции (сохраняется в R)");
        System.out.println("   10. Транспонировать A");
        System.out.println("   11. Транспонировать B");
        System.out.println("   12. Умножить A на скаляр");
        System.out.println("   13. Умножить B на скаляр");
        System.out.println("   14. Определитель A");
        System.out.println("   15. Определитель B");
        System.out.println("  Работа с R");
        System.out.println("   16. Скопировать R в A");
        System.out.println("   17. Скопировать R в B");
        System.out.println("   18. Скопировать A в R");
        System.out.println("   19. Скопировать B в R");
        System.out.println("   20. Поменять A и B местами");
        System.out.println("   21. R + B");
        System.out.println("   22. A * R");
        System.out.println("   23. R * R");
        System.out.println("   24. Транспонировать R");
        System.out.println("    0. Выход");
        System.out.println("==============================================");
    }

    public static void handle(int choice) {
        switch (choice) {
            case 1:
                a = readMatrix("A");
                break;
            case 2:
                b = readMatrix("B");
                break;
            case 3:
                printMatrix(a, "A");
                break;
            case 4:
                printMatrix(b, "B");
                break;
            case 5:
                printMatrix(r, "R");
                break;
            case 6:
                store("A + B", requireA().add(requireB()));
                break;
            case 7:
                store("A - B", requireA().sub(requireB()));
                break;
            case 8:
                store("A * B", requireA().mul(requireB()));
                break;
            case 9:
                store("A / B", requireA().div(requireB()));
                break;
            case 10:
                store("Транспонированная A", requireA().transpose());
                break;
            case 11:
                store("Транспонированная B", requireB().transpose());
                break;
            case 12: {
                System.out.println("Введите скаляр k:");
                Complex k = readComplex();
                store("Произведение k и A", requireA().scalarMul(k));
                break;
            }
            case 13: {
                System.out.println("Введите скаляр k:");
                Complex k = readComplex();
                store("Произведение k и B", requireB().scalarMul(k));
                break;
            }
            case 14:
                System.out.println("det(A) = " + requireA().determinant());
                break;
            case 15:
                System.out.println("det(B) = " + requireB().determinant());
                break;
            case 16:
                a = requireR().copy();
                break;
            case 17:
                b = requireR().copy();
                break;
            case 18:
                r = requireA().copy();
                break;
            case 19:
                r = requireB().copy();
                break;
            case 20: {
                ComplexMatrix tmp = a;
                a = b;
                b = tmp;
                System.out.println("Матрицы A и B поменяны местами.");
                break;
            }
            case 21:
                store("R + B", requireR().add(requireB()));
                break;
            case 22:
                store("A * R", requireA().mul(requireR()));
                break;
            case 23:
                store("R * R", requireR().mul(requireR()));
                break;
            case 24:
                store("Транспонированная R", requireR().transpose());
                break;
            case 0:
                System.out.println("Выход.");
                break;
            default:
                System.out.println("Нет такого пункта меню.");
                break;
        }
    }

    private static void store(String title, ComplexMatrix m) {
        r = m;
        System.out.println();
        System.out.println("Результат операции \"" + title + "\" сохранён в R:");
        System.out.println(m);
    }

    public static ComplexMatrix requireA() {
        if (a == null) throw new IllegalStateException("матрица A не создана");
        return a;
    }

    public static ComplexMatrix requireB() {
        if (b == null) throw new IllegalStateException("матрица B не создана");
        return b;
    }

    public static ComplexMatrix requireR() {
        if (r == null) throw new IllegalStateException("матрица R не создана");
        return r;
    }

    public static void printMatrix(ComplexMatrix m, String name) {
        if (m == null) {
            System.out.println("Матрица " + name + " не создана.");
        } else {
            System.out.println();
            System.out.println(name + " =");
            System.out.println(m);
        }
    }
}
