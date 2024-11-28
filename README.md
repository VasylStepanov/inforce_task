# Inforce test task

The task was to get data from https://books.toscrape.com and store it in database.
In this project I used PostgreSQL DB and Hibernate ORM, JSoup to get html pages and parse them.

## Run Program in Linux
The next command changes DB arguments in persistence.xml and run JAR file
````
sh ./start.sh "jdbc:postgresql://localhost:5432/mydb" "postgres" "postgres"
````
Run JAR file when data is already changed
````
sh ./start.sh 
````

## Run Program in Windows
The next command changes DB arguments in persistence.xml and run JAR file
````
./start.bat "jdbc:postgresql://localhost:5432/mydb" "postgres" "postgres"
````
Run JAR file when data is already changed
````
./start.bat
````