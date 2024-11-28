#!/bin/sh

# Якщо немає параметрів, запускаємо програму без змін
if [ "$#" -eq 0 ]; then
    echo "No parameters provided. Starting the application without parameter replacement."
    java -jar ./build/libs/Inforce_Task-1.0.jar
    exit 0
fi

# Якщо не рівно 3 аргументи, виводимо помилку
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 DB_URL DB_USERNAME DB_PASSWORD"
    exit 1
fi

# Шлях до persistence.xml
PERSISTENCE_FILE="src/main/resources/META-INF/persistence.xml"

# Зчитуємо аргументи
DB_URL="$1"
DB_USERNAME="$2"
DB_PASSWORD="$3"

# Замінюємо параметри в файлі persistence.xml
sed -i "s|\${DB_URL}|$DB_URL|g" "$PERSISTENCE_FILE"
sed -i "s|\${DB_USERNAME}|$DB_USERNAME|g" "$PERSISTENCE_FILE"
sed -i "s|\${DB_PASSWORD}|$DB_PASSWORD|g" "$PERSISTENCE_FILE"

echo "Parameters replaced in $PERSISTENCE_FILE"

# Запускаємо програму
java -jar ./build/libs/Inforce_Task-1.0.jar