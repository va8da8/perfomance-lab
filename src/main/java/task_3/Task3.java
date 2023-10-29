package task_3;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/*
 * На вход в качестве аргументов программы поступают два файла:
 * tests.json и values.json (в приложении к заданию находятся
 * примеры этих файлов)
 * • values.json содержит результаты прохождения тестов с
 *  уникальными id
 * • tests.json содержит структуру для построения отчёта на основе
 * прошедших тестов (вложенность может быть большей, чем в примере)
 * Напишите программу, которая формирует файл report.json с
 * заполненными
 * полями value для структуры tests.json на основании values.json.
 * Пример:
 * Часть структуры tests.json:
 * {"id": 122, "title": "Security test", "value": "", "values":
 * [{"id": 5321, "title": "Confidentiality", "value": ""},
 * {"id": 5322, "title": "Integrity", "value": ""}]}
 * После заполнения в соответствии с values.json:
 * {"values": [{"id": 122, "value": "failed"},
 * {"id": 5321,"value": "passed"}, {"id": 5322,"value": "failed"}]}
 * Будет иметь следующий вид в файле report.json:
 * {"id": 122, "title": "Security test", "value": "failed", "values":
 * [{"id": 5321, "title": "Confidentiality", "value": "passed"},
 * {"id": 5322, "title": "Integrity", "value": "failed"}]}
 */
public class Task3 {


    public static void main(String[] args) throws
            IOException, JSONException {

        // Чтение содержимого файлов и преобразование в JSON объекты
        String testsJsonString =
                new String(Files.readAllBytes(Paths.
                        get(args[0])));
        JSONObject testsJsonObject =
                new JSONObject(testsJsonString);

        String valuesJsonString =
                new String(Files.readAllBytes(Paths
                        .get(args[1])));
        JSONObject valuesJsonObject =
                new JSONObject(valuesJsonString);

        // Получение массивов tests и values из JSON объектов
        JSONArray testsArray =
                testsJsonObject.getJSONArray("tests");
        JSONArray valuesArray =
                valuesJsonObject.getJSONArray("values");

        // Создание списка для хранения отчетов
        List<JSONObject> reportList = new ArrayList<>();

        // Проход по всем элементам массива tests
        for (int i = 0; i < testsArray.length(); i++) {

            JSONObject testObj = testsArray.getJSONObject(i);
            int testId = testObj.getInt("id");

            // Поиск соответствующего значения в массиве values
            String testValue = findValue(testId, valuesArray);

            // Создание нового объекта отчета и его добавление в
            // список
            JSONObject reportObj = new JSONObject();
            reportObj.put("id", testId);
            reportObj.put("title", testObj.getString("title"));
            reportObj.put("value", testValue);

            // Добавление объектов values, если они есть
            if (testObj.has("values")) {
                JSONArray valuesObjArray = testObj
                        .getJSONArray("values");
                JSONArray reportValuesArray = new JSONArray();

                // Проход по объектам values внутри данного теста
                for (int j = 0; j < valuesObjArray.length(); j++) {

                    JSONObject valueObj =
                            valuesObjArray.getJSONObject(j);
                    int valueId = valueObj.getInt("id");
                    String valueTitle =
                            valueObj.getString("title");

                    // Поиск соответствующего значения в массиве
                    // values
                    String valueValue =
                            findValue(valueId, valuesArray);

                    // Создание нового объекта value и его
                    // добавление в массив
                    JSONObject reportValueObj = new JSONObject();
                    reportValueObj.put("id", valueId);
                    reportValueObj.put("title", valueTitle);
                    reportValueObj.put("value", valueValue);

                    reportValuesArray.put(reportValueObj);
                }

                reportObj.put("values", reportValuesArray);
            }

            reportList.add(reportObj);
        }

        // Создание JSON объекта отчета
        JSONObject reportJsonObject = new JSONObject();
        reportJsonObject.put("reports", reportList);

        // Запись JSON объекта в файл report.json
        FileWriter file = new FileWriter("report.json");
        file.write(reportJsonObject.toString());
        file.close();
    }

    // Метод для поиска значения по id в массиве values
    private static String findValue(int id, JSONArray valuesArray) {
        for (int i = 0; i < valuesArray.length(); i++) {
            JSONObject valueObj = valuesArray.getJSONObject(i);
            if (valueObj.getInt("id") == id) {
                return valueObj.getString("value");
            }
        }
        return "";
    }
}