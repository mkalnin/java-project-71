# Gendiff

[![Actions Status](https://github.com/mkalnin/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/mkalnin/java-project-71/actions)
[![Java CI](https://github.com/mkalnin/java-project-71/actions/workflows/ci.yml/badge.svg)](https://github.com/mkalnin/java-project-71/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mkalnin_java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mkalnin_java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mkalnin_java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=mkalnin_java-project-71)

**Gendiff** — утилита командной строки для сравнения двух конфигурационных файлов и отображения различий между ними.

## Возможности

- Поддержка форматов **JSON** и **YAML**
- Три формата вывода: **stylish**, **plain**, **json**
- Сравнение вложенных структур (массивы и объекты)
- Смешанное сравнение (JSON vs YAML)
- Легкое расширение новыми парсерами и форматтерами (паттерн Фабрика)

## Требования

- Java 17+
- Gradle 7+

## Установка

```bash
# Клонирование репозитория
git clone https://github.com/mkalnin/java-project-71.git
cd java-project-71/app

# Сборка проекта (тесты + checkstyle)
make build

# Сборка исполняемого дистрибутива
./gradlew installDist
```

## Использование

```bash
# Сравнение двух файлов (формат вывода по умолчанию — stylish)
./build/install/app/bin/app file1.yml file2.yml

# Явное указание формата вывода: stylish, plain, json
./build/install/app/bin/app -f plain file1.json file2.json
./build/install/app/bin/app --format json file1.yml file2.yml
```

Пример вывода в формате `stylish`:

```
{
  - follow: false
    host: hexlet.io
  - proxy: 123.234.53.22
  - timeout: 50
  + timeout: 20
  + verbose: true
}
```

## Разработка

```bash
make test        # запуск тестов
make checkstyle  # проверка стиля кода
make jacoco      # отчёт о покрытии
make clean       # очистка сборки
```
