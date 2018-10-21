package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws Exception {
        List<Integer> times = new ArrayList<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split(":");
                times.add(Integer.valueOf(parts[0]) * 3600 + Integer.valueOf(parts[1]) * 60 + Integer.valueOf(parts[2]));
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
        Integer[] timesArray = new Integer[times.size()];
        for (int i = 0; i < times.size(); i++) {
            timesArray[i] = times.get(i);
        }
        Sorts.quickSort(timesArray);
        try (FileWriter fw = new FileWriter(outputName)) {
            for (int time : timesArray) {
                fw.write(String.format("%02d:%02d:%02d\n", time / 3600, (time % 3600) / 60, time % 60));
            }
            fw.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
    }     //Трудоемкость O(NlogN),где N- количество строчек в входном файле, так как используется quicksort, а также цикл на N элементов
    // Ресурсоемкость O(N), так как используется список и массив, размером N элементов

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        List<String> temperatures = new ArrayList<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                temperatures.add(scan.nextLine());
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }

        Double[] temperaturesArray = new Double[temperatures.size()];
        for (int i = 0; i < temperatures.size(); i++) {
            temperaturesArray[i] = Double.valueOf(temperatures.get(i));
        }
        Sorts.quickSort(temperaturesArray);
        try (FileWriter fw = new FileWriter(outputName)) {
            for (Double temperature : temperaturesArray) {
                fw.write(temperature.toString() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        List<Integer> numbers = new ArrayList<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                numbers.add(Integer.valueOf(scan.nextLine()));
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
        int max = 0;
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        Integer[] count = new Integer[max + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int number : numbers) {
            count[number]++;
        }
        int max2 = 0;
        for (int c : count) {
            if (c > max2) {
                max2 = c;
            }
        }
        List<Integer> mostCommon = new ArrayList<>();
        for (int i = 0; i < count.length; i++) {
            if (count[i] == max2) {
                mostCommon.add(i);
            }
        }
        Integer[] mostCommonArray = new Integer[mostCommon.size()];
        for (int i = 0; i < mostCommon.size(); i++) {
            mostCommonArray[i] = mostCommon.get(i);
        }
        Sorts.quickSort(mostCommonArray);
        try (FileWriter fw = new FileWriter(outputName)) {
            for (Integer number : numbers) {
                if (!number.equals(mostCommonArray[0])) {
                    fw.write(number.toString() + "\n");
                }
            }
            for (int i = 0; i < max2; i++) {
                fw.write(mostCommonArray[0].toString() + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        for (int i = first.length; i < second.length; i++) {
            int counter = i - first.length;
            int j = 0;
            while (second[i].compareTo(first[j]) > 0 && j < first.length - 1) {
                j++;
                counter++;
            }
            if (j == first.length - 1) {
                if (second[i].compareTo(first[j]) > 0) {
                    counter++;
                }
            }
            T swapSupport = second[i];
            second[i] = null;
            second[counter] = swapSupport;
        }
        int j = 0;
        for (int i = 0; i < second.length; i++) {
            if (second[i] == null) {
                second[i] = first[j];
                j++;
            }
        }
    }
}