public class Main {
    public static void main() {
        while (true) {
            UI.printMenu();
            int choice = UI.readInt("Выбор: ");
            if (choice == 0) {
                System.out.println("Выход");
                return;
            }
            try {
                UI.handle(choice);
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
    }