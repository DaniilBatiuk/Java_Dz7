package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String getFilePathFromUser(int fileNumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу " + fileNumber + ": ");
        return scanner.nextLine();
    }

    private static String getFilePathFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        return scanner.nextLine();
    }
    private static String findLongestLine(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String longestLine = "";
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.length() > longestLine.length()) {
                longestLine = line;
            }
        }

        reader.close();
        return longestLine;
    }

    private static int[][] readArraysFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int[][] arrays = new int[0][];

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            int[] array = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                array[i] = Integer.parseInt(tokens[i]);
            }

            int[][] newArrays = new int[arrays.length + 1][];
            System.arraycopy(arrays, 0, newArrays, 0, arrays.length);
            newArrays[arrays.length] = array;
            arrays = newArrays;
        }

        reader.close();
        return arrays;
    }

    private static String arrayToString(int[] array) {
        StringBuilder builder = new StringBuilder();
        for (int num : array) {
            builder.append(num).append(" ");
        }
        return builder.toString().trim();
    }

    private static int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private static int findMin(int[] array) {
        int min = array[0];
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    private static int findSum(int[] array) {
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    private static int findTotalSum(int[][] arrays) {
        int totalSum = 0;
        for (int[] array : arrays) {
            totalSum += findSum(array);
        }
        return totalSum;
    }

    private static int[] getInputArrayFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть розмір масиву: ");
        int size = scanner.nextInt();

        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            System.out.print("Введіть елемент масиву [" + i + "]: ");
            array[i] = scanner.nextInt();
        }

        return array;
    }

    private static void writeDataToFile(String filePath, int[] inputArray) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writeArray(writer, inputArray);

            int[] evenNumbers = filterEvenNumbers(inputArray);
            writeArray(writer, evenNumbers);

            int[] oddNumbers = filterOddNumbers(inputArray);
            writeArray(writer, oddNumbers);

            int[] reversedArray = reverseArray(inputArray);
            writeArray(writer, reversedArray);
        }
    }

    private static void writeArray(BufferedWriter writer, int[] array) throws IOException {
        for (int num : array) {
            writer.write(num + " ");
        }
        writer.newLine();
    }

    private static int[] filterEvenNumbers(int[] array) {
        int count = 0;
        for (int num : array) {
            if (num % 2 == 0) {
                count++;
            }
        }

        int[] evenNumbers = new int[count];
        int index = 0;
        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers[index++] = num;
            }
        }

        return evenNumbers;
    }

    private static int[] filterOddNumbers(int[] array) {
        int count = 0;
        for (int num : array) {
            if (num % 2 != 0) {
                count++;
            }
        }

        int[] oddNumbers = new int[count];
        int index = 0;
        for (int num : array) {
            if (num % 2 != 0) {
                oddNumbers[index++] = num;
            }
        }

        return oddNumbers;
    }

    private static int[] reverseArray(int[] array) {
        int[] reversedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }
        return reversedArray;
    }
    private static void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String lastName = parts[0].trim();
                    String firstName = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    employees.add(new Employee(lastName, firstName, age));
                }
            }
            System.out.println("Дані співробітників завантажено із файлу.");
        } catch (IOException e) {
            System.out.println("Не вдалося завантажити дані із файлу.");
        }
    }

    private static void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee employee : employees) {
                writer.write(employee.getLastName() + ", " + employee.getFirstName() + ", " + employee.getAge());
                writer.newLine();
            }
            System.out.println("Дані співробітників збережено у файлі.");
        } catch (IOException e) {
            System.out.println("Не вдалося зберегти дані у файлі.");
        }
    }

    private static void addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть прізвище: ");
        String lastName = scanner.nextLine();
        System.out.print("Введіть ім'я: ");
        String firstName = scanner.nextLine();
        System.out.print("Введіть вік: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Employee employee = new Employee(lastName, firstName, age);
        employees.add(employee);
        System.out.println("Співробітник доданий: " + employee);
    }

    private static void editEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть прізвище співробітника для редагування: ");
        String lastName = scanner.nextLine();
        boolean found = false;

        for (Employee employee : employees) {
            if (employee.getLastName().equalsIgnoreCase(lastName)) {
                System.out.print("Введіть нове прізвище: ");
                String newLastName = scanner.nextLine();
                System.out.print("Введіть нове ім'я: ");
                String newFirstName = scanner.nextLine();
                System.out.print("Введіть новий вік: ");
                int newAge = scanner.nextInt();
                scanner.nextLine();

                employee.setLastName(newLastName);
                employee.setFirstName(newFirstName);
                employee.setAge(newAge);
                System.out.println("Дані співробітника відредаговані: " + employee);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Співробітник з прізвищем " + lastName + " не знайдений.");
        }
    }

    private static void deleteEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть прізвище співробітника для видалення: ");
        String lastName = scanner.nextLine();
        boolean removed = employees.removeIf(employee -> employee.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            System.out.println("Співробітник з прізвищем " + lastName + " видалений.");
        } else {
            System.out.println("Співробітник з прізвищем " + lastName + " не знайдений.");
        }
    }

    private static void searchEmployeeByLastName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть прізвище співробітника для пошуку: ");
        String lastName = scanner.nextLine();
        boolean found = false;

        for (Employee employee : employees) {
            if (employee.getLastName().equalsIgnoreCase(lastName)) {
                System.out.println("Знайдений співробітник: " + employee);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Співробітник з прізвищем " + lastName + " не знайдений.");
        }
    }

    private static void displayAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static final String FILE_NAME = "employees.txt";
    private static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {

        //Task1
        System.out.println("\nTask1");

        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(getFilePathFromUser(1)));
            BufferedReader reader2 = new BufferedReader(new FileReader(getFilePathFromUser(2)));
            String line1, line2;
            int lineNumber = 1;

            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
                if (!line1.equals(line2)) {
                    System.out.println("Рядок " + lineNumber + " не збігається:");
                    System.out.println("Файл 1: " + line1);
                    System.out.println("Файл 2: " + line2);
                }
                lineNumber++;
            }

            reader1.close();
            reader2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Task2
        System.out.println("\nTask2");

        try {
            String filePath = getFilePathFromUser();
            String longestLine = findLongestLine(filePath);

            System.out.println("Довжина найбільшого рядка: " + longestLine.length());
            System.out.println("Найбільший рядок: " + longestLine);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Task3
        System.out.println("\nTask3");

        try {
            String filePath = getFilePathFromUser();
            int[][] arrays = readArraysFromFile(filePath);

            for (int i = 0; i < arrays.length; i++) {
                int[] array = arrays[i];
                System.out.println("Масив " + (i + 1) + ": " + arrayToString(array));
                System.out.println("Максимум: " + findMax(array));
                System.out.println("Мінімум: " + findMin(array));
                System.out.println("Сума: " + findSum(array));
                System.out.println();
            }

            System.out.println("Загальна сума всіх масивів: " + findTotalSum(arrays));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Task4
        System.out.println("\nTask4");

        try {
            String filePath = getFilePathFromUser();
            int[] inputArray = getInputArrayFromUser();

            writeDataToFile(filePath, inputArray);
            System.out.println("Дані успішно записані у файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Task5
        System.out.println("\nTask5");

        loadEmployeesFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати співробітника");
            System.out.println("2. Редагувати дані співробітника");
            System.out.println("3. Видалити співробітника");
            System.out.println("4. Пошук співробітника за прізвищем");
            System.out.println("5. Вивести інформацію про всіх співробітників");
            System.out.println("6. Зберегти дані у файл і вийти");
            System.out.print("Оберіть опцію (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    editEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    searchEmployeeByLastName();
                    break;
                case 5:
                    displayAllEmployees();
                    break;
                case 6:
                    saveEmployeesToFile();
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }


}