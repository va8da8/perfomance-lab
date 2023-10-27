package task_4;


import java.io.*;


/*
 * Дан массив целых чисел nums. Напишите программу, выводящую
 * минимальное количество ходов, требуемых для приведения всех
 * элементов к одному числу.
 * За один ход можно уменьшить или увеличить число массива на 1.
 * Пример:
 * nums = [1, 2, 3]
 * Решение: [1, 2, 3] => [2, 2, 3] => [2, 2, 2]
 * Минимальное количество ходов: 2
 * Элементы массива читаются из файла, переданного в качестве аргумента
 * командной строки.
 * Пример:
 * На вход подаётся файл с содержимым:
 * 1
 * 10
 * 2
 * 9
 * Вывод в консоль:
 * 16
 * */
public class Task4 {


    public static void main(String[] args) {

        int[] arr = null;

        try (BufferedReader in =
                     new BufferedReader(new FileReader(args[0]))) {

            arr = in.lines().mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {

            System.err.println(e);
        }

        int len = arr.length;
        int i, mean, counter = 0, sum = 0;


        for (i = 0; i < len; i++) {

            sum += arr[i];
        }
        mean = sum / len;
        for (i = 0; i < len; i++) {


            while (arr[i] < mean) {

                arr[i]++;
                counter++;
            }
            while (arr[i] > mean) {

                arr[i]--;
                counter++;
            }
        }
        System.out.println(counter);
    }
}