## Spring Boot + Angular app with a PostgreSQL database
1. Baza danych: PostgreSQL, PgAdmin 4
Utwórz bazę PostgreSQL w PgAdmin i wykonaj w niej skrypt.sql

2. Backend i połączenie z bazą danych: InteliIJ, Java 17 
Otwórz folder backend w wybranym przez siebie IDE. Zdefiniuj jako zmienne środowiskowe: 
 - url - pod którym będziemy się łączyć z bazą. Jeśli zrobiłeś domyślną konfigurację w PostgreSQl, deklaracja zmiennej będzie wyglądać, dla bazy nazwanej "tree" tak:
   url=jdbc:postgresql://localhost:5432/tree
- nazwa użytkownika postgres oraz hasło - definiowałeś je przy pierwszym włączeniu PgAdmin. Deklaracja zmiennych dla nazwy użykownika i hasła:
username=twoja_nazwa_uzytkownika;password=twoje_haslo

Zmienne środowiskowe możesz również zahardkodować w application.properties (niezalecane).

Następnie uruchom funkcję main w klasie NodeApplication. Aplikacja będzie działać pod adresem: localhost:8080/nodes


3. Frontend: Angular CLI: 16.1.1, Node: 16.17.0
W terminalu wejdź do folderu frontend i wpisz komendę ng serve. Aplikacja będzie działać pod adresem: localhost:4200/nodes
