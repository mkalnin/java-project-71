package hexlet.code;

public class Differ {

    /**
     * API метод для сравнения двух конфигурационных файлов
     * @param filepath1 путь к первому файлу
     * @param filepath2 путь ко второму файлу
     * @return разница между файлами в формате stylish
     * @throws Exception при ошибках чтения файлов
     */
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    /**
     * API метод для сравнения двух конфигурационных файлов с указанием формата
     * @param filepath1 путь к первому файлу
     * @param filepath2 путь ко второму файлу
     * @param format формат вывода (stylish, plain, json)
     * @return разница между файлами в указанном формате
     * @throws Exception при ошибках чтения файлов или неизвестном формате
     */
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        // TODO: реализовать логику сравнения
        // Пока временная реализация для проверки
        return String.format("Difference between %s and %s in %s format",
                filepath1, filepath2, format);
    }
}