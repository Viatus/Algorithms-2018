package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        List<Integer> prices = new ArrayList<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                prices.add(Integer.valueOf(scan.nextLine()));
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
        Pair<Integer, Integer> maxPair = new Pair<>(1, 2);
        Integer lowestPriceIndex = 0;
        int max = 0;
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) - prices.get(lowestPriceIndex) > max) {
                maxPair = new Pair<>(lowestPriceIndex + 1, i + 1);
                max = prices.get(i) - prices.get(lowestPriceIndex);
            } else {
                if (prices.get(i) < prices.get(lowestPriceIndex)) {
                    lowestPriceIndex = i;
                }
            }
        }
        return maxPair;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        Men startingMen = new Men(1);
        Men men = startingMen;
        for (int i = 2; i <= menNumber; i++) {
            men = (men.next = new Men(i));
        }
        men.next = startingMen;
        while (men != men.next) {
            for (int i = 1; i < choiceInterval; i++) {
                men = men.next;
            }
            men.next = men.next.next;
        }
        return men.number;
    }

    static public class Men {
        int number;
        Men next;

        Men(int n) {
            number = n;
        }
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        Integer[][] matrix = new Integer[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.substring(i, i + 1).equals(second.substring(j, j + 1))) {
                    if (i >= 1 && j >= 1) {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    } else {
                        matrix[i][j] = 1;
                    }
                }
            }
        }
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    maxIndex = i + 1;
                }
            }
        }
        return first.substring(maxIndex - max, maxIndex);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) {
            return 0;
        }
        int n = limit / 2;
        boolean[] primes = new boolean[limit];
        for (int i = 1; i < n; i++) {
            for (int j = i; j <= (n - i) / (2 * i + 1); j++) {
                primes[i + j + 2 * i * j] = true;
            }
        }
        int count;
        if (limit == 2) {
            count = 1;
        } else {
            count = 2;
        }
        for (int i = 2; i < primes.length / 2; i++) {
            if (!primes[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
