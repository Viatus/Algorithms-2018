package lesson2;

import kotlin.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    //Трудоемкость - O(N), так как наиболее трудозатратным является цикл перебора N элементов
    //Ресурсоемкость - O(N), так как создается список размера N
    //N - количество строк в входном файле

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
        Man startingMan = new Man(1);
        Man currentMan = startingMan;
        for (int i = 2; i <= menNumber; i++) {
            currentMan = (currentMan.next = new Man(i));
        }
        currentMan.next = startingMan;
        while (currentMan != currentMan.next) {
            for (int i = 1; i < choiceInterval; i++) {
                currentMan = currentMan.next;
            }
            currentMan.next = currentMan.next.next;
        }
        return currentMan.number;
    }
    //Трудоемкость - O(menNumber * choiceInterval), ресурсоемкость - O(menNumber)

    static private class Man {
        int number;
        Man next;

        Man(int n) {
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
                if (first.charAt(i) == (second.charAt(j))) {
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
    //Трудоемкость - O(n*m), где n - длина первого слова, а m - второго
    //Ресурсоемкость - O(m*n), так как создается массив m на n элементов

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
    //Трудоемкость - O(N^2), где N - limit/2, ресурсоемкость - O(limit)

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
        List<List<String>> letters = new ArrayList<>();
        Set<String> foundWords = new HashSet<>();
        try (FileReader fr = new FileReader(inputName)) {
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split(" ");
                letters.add(Arrays.asList(parts));
            }
            fr.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Неверный форамт входного файла");
        }
        for (int i = 0; i < letters.size(); i++) {
            for (int j = 0; j < letters.get(i).size(); j++) {
                for (String word : words) {
                    if (!foundWords.contains(word)) {
                        if (letters.get(i).get(j).substring(0, 1).equalsIgnoreCase(word.substring(0, 1))) {
                            if (isWordFound(letters, i, j, 1, word, -1, -1)) {
                                foundWords.add(word);
                            }
                        }
                    }
                }
            }
        }
        return foundWords;
    }
    //Трудоемкость - (n*m*w*l), ресурсоемкость - O(n*m + w), где n- длина матрицы, m - ее ширина, w- количество искомых слов,
    //а l - наибольшее количество совпавших подряд букв

    static private List<Character> nearbyLetters(List<List<String>> letters, int line, int number, int previousLetterLine, int previousLetterNumber) {
        List<Character> list = new ArrayList<>();
        if (line > 0 && (line - 1 != previousLetterLine || number != previousLetterNumber)) {
            list.add(letters.get(line - 1).get(number).charAt(0));
        } else {
            list.add('-');
        }
        if (number < letters.get(line).size() - 1 && (line != previousLetterLine || number + 1 != previousLetterNumber)) {
            list.add(letters.get(line).get(number + 1).charAt(0));
        } else {
            list.add('-');
        }
        if (line < letters.size() - 1 && (line + 1 != previousLetterLine || number != previousLetterNumber)) {
            list.add(letters.get(line + 1).get(number).charAt(0));
        } else {
            list.add('-');
        }
        if (number > 0 && (line != previousLetterLine || number - 1 != previousLetterNumber)) {
            list.add(letters.get(line).get(number - 1).charAt(0));
        } else {
            list.add('-');
        }
        return list;
    }

    static private boolean isWordFound(List<List<String>> letters, int line, int number, int numberInWord, String word, int previousLetterLine, int previousLetterNumber) {
        if (numberInWord == word.length()) {
            return true;
        }
        List<Character> list = nearbyLetters(letters, line, number, previousLetterLine, previousLetterNumber);
        if (list.contains(word.charAt(numberInWord))) {
            boolean firstCase = false, secondCase = false, thirdCase = false, fourthCase = false;
            if (list.get(0) == word.charAt(numberInWord)) {
                firstCase = isWordFound(letters, line - 1, number, numberInWord + 1, word, line, number);
            }
            if (list.get(1) == word.charAt(numberInWord)) {

                secondCase = isWordFound(letters, line, number + 1, numberInWord + 1, word, line, number);
            }
            if (list.get(2) == word.charAt(numberInWord)) {
                thirdCase = isWordFound(letters, line + 1, number, numberInWord + 1, word, line, number);
            }
            if (list.get(3) == word.charAt(numberInWord)) {
                fourthCase = isWordFound(letters, line, number - 1, numberInWord + 1, word, line, number);
            }
            return firstCase || secondCase || thirdCase || fourthCase;
        }
        return false;
    }

}
