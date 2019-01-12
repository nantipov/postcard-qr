# postcard-qr

##  Build
```
./gradlew build
```

## Local database setup
Create an empty database and then run application to build the whole schema.

```postgresql
psql -h localhost -U postgres
postgres=# create database qrpostcard_db;
```
 ## Local run

```
java -jar build/libs/postcard-service-1.0.jar --spring.profiles.active=local
```

## Configuration for LIVE profile via environment variables
* `DB_URL` Database JDBC URL
* `DB_USERNAME` Database username
* `DB_PASSWORD` Database password
* `POSTCARD_ASSETS_DIR` Directory where message files are stored (target file path `<POSTCARD_ASSETS_DIR>/<message code>/file.gpg`)
* `APP_PORT` Server accepting port

```
java -jar build/libs/postcard-service-1.0.jar --spring.profiles.active=live
```
