## Uruchamianie projektu

### 1. Uruchomienie przy użyciu Javy:
Jeśli chcesz uruchomić projekt bez Dockera, wystarczy uruchomić aplikację standardowo w Javie. Jedyną rzeczą, która będzie uruchomiona w Dockerze, jest baza danych, aby łatwiej było wprowadzać zmiany w kodzie. 
Dzięki temu, po zmianach w aplikacji Java, nie trzeba przebudowywać obrazu Dockera – zmiany w kodzie są uwzględniane lokalnie.


### 2. Uruchomienie backendu za pomocą Dockera:
Jeśli chcesz uruchomić cały backend za pomocą Dockera, trzeba skorzystać z profilu `java-backend` (inaczej sama baza danych będzie uruchomiona):

```bash
docker compose --profile=java-backend up
```

Jeśli były jakieś zmiany w javie:
```bash
docker compose --profile=java-backend up --build
```

Przed uruchomieniem upewnij się, że PostgreSQL nie działa lokalnie, ponieważ może dojść do konfliktu portów. 
Jeśli najpierw uruchomiłeś Docker, a potem próbujesz uruchomić aplikację lokalnie w Javie, sprawdź, czy poprzedni kontener Docker jest wyłączony.
