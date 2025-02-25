## autoservice-quote-neti

#### Leírás:
//TODO !!

#### Előfeltétel:
A fordításhoz Gradle szükséges. 
A konténerizáció Dockert használ.
(A project SpringBoot 3.4-es framework-kel és Java 21-gyel készült.)


#### Indítás:
A project letöltése után a root mappában a `./gradlew clean build --refresh-dependencies` parancsot adjuk ki.
Sikeres futtatása után a `docker-compose build --no-cache; docker-compose up -d` parancsot adjuk ki, szintén a root mappában.

#### Tesztelés:
A `http://localhost:8080/swagger-ui/index.html` oldalt használhatjuk, vagy töltsük le az xml-to-java-client alkalmazást a `https://github.com/gtulipan/autoservice-quote-neti-java_client` oldalról.