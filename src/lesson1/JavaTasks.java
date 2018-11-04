package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

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
    static public void sortTimes(String inputName, String outputName) {
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
    }
    //Трудоемкость - O(NlogN), так как самой трудозатратной является сортировка, ресурсоемкость - O(N), так как в программе
    //создаются массивы и списки, длина наибольшего из которых равна N, а количество массивов и списков от N е зависит.
    //N - количество строк в входном файле

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
        List<Double> temperaturesInput = new ArrayList<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                temperaturesInput.add(Double.valueOf(scan.nextLine()));
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
        Map<Double, Integer> temperatures = new HashMap<>();
        for (Double i = -2730.0; i <= 5000.0; i = i + 1) {
            temperatures.put(i, 0);
        }
        for (Double temperature : temperaturesInput) {
            temperatures.put(temperature*10, temperatures.get(temperature*10) + 1);
        }
        try (FileWriter fw = new FileWriter(outputName)) {
            for (Double i = -2730.0; i <= 5000.0; i = i + 1) {
                for (int j = 0; j < temperatures.get(i); j++) {
                    fw.write(i/10 + "\n");
                }
            }
            fw.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
    }
    //Трудоемкость - O(N), ресурсоемкость - O(1), так как в размер temperatures не зависити от N
    //N - количество строк в входном файле

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
        Map<Integer, Integer> count = new HashMap<>();
        for (int number : numbers) {
            if (count.get(number) == null) {
                count.put(number, 1);
            } else {
                count.put(number, count.get(number) + 1);
            }
        }
        int max = 0;
        int maxNumber = 0;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() >= max) {
                if (entry.getValue() == max && entry.getKey() > maxNumber) {
                    continue;
                }
                max = entry.getValue();
                maxNumber = entry.getKey();
            }
        }
        try (FileWriter fw = new FileWriter(outputName)) {
            for (Integer number : numbers) {
                if (number != maxNumber) {
                    fw.write(number.toString() + "\n");
                }
            }
            for (int i = 0; i < max; i++) {
                fw.write(maxNumber + "\n");
            }
            fw.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
    }
    //Трудоемкость - O(N), ресурсоемкость - O(N), так как в программе
    //создаются массивы и списки, длина наибольшего из которых равна N, а количество массивов и списков от N не зависит.
    //N - количество строк в входном файле

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
        int firstIndex = 0;
        int secondIndex = first.length;
        for (int i = 0; i < second.length; i++) {
            if (firstIndex < first.length && secondIndex < second.length) {
                if (first[firstIndex].compareTo(second[secondIndex]) < 0) {
                    second[i] = first[firstIndex];
                    firstIndex++;
                } else {
                    second[i] = second[secondIndex];
                    secondIndex++;
                }
            } else {
                if (firstIndex < first.length) {
                    second[i] = first[firstIndex];
                    firstIndex++;
                } else {
                    break;
                }
            }
        }
    }
    //Трудоемкость - O(N), где N - длина second, ресурсоемкость - O(1)
}