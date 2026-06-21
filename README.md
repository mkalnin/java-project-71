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
cd java-project-71

# Сборка проекта
make build

# Локальная установка
make install