package task_2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;


/*
 * Напишите программу, которая рассчитывает положение точки
 * относительно окружности.
 * Координаты центра окружности и его радиус считываются из файла1.
 * Пример:
 * 1 1
 * 5
 * Координаты точек считываются из файла2.
 * Пример:
 * 0 0
 * 1 6
 * 6 6
 * Файлы передаются программе в качестве аргументов. Файл с
 * координатами и радиусом
 * окружности - 1 аргумент, файл с координатами точек - 2 аргумент.
 * Координаты в диапазоне float.
 * Количество точек от 1 до 100.
 * Вывод каждого положения точки заканчивается символом новой строки.
 * Соответствия ответов:
 * 0 - точка лежит на окружности
 * 1 - точка внутри
 * 2 - точка снаружи
 */
public class Task2 {

    public static void main(String[] args) throws
            FileNotFoundException {

        int i;

        File f1 = new File(args[0]);

        Scanner s1 = new Scanner(f1);

        s1.useLocale(Locale.US);

        float x, y, r;


        while (s1.hasNextFloat()) {

            x = s1.nextFloat();
            y = s1.nextFloat();
            r = s1.nextFloat();
            File f2 = new File(args[1]);
            Scanner s2 = new Scanner(f2);
            s2.useLocale(Locale.US);


            for (i = 0; i <= 100; i++) {

                while (s2.hasNext()) {

                    String a1 = s2.next(), b1 = s2.next();
                    float a2 = Float.parseFloat(a1),
                            b2 = Float.parseFloat(b1);
                    float v = (a2 - x) * (a2 - x) +
                            (b2 - y) * (b2 - y);
                    if (v == r * r) {

                        System.out.println(i + " - точка внутри ");
                        i++;
                    } else if (v < r * r) {

                        System.out.println(i +
                                " - точка лежит на окружности ");
                        i++;
                    } else {

                        System.out.println(i + " - точка снаружи ");
                        i++;
                    }
                }
            }
        }
    }
}