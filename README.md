## autoservice-quote-neti

#### Üzleti igény:
Az egyedi ajánlatkészítést a következő folyamat szerint kell implementálni.
Amikor egy ügyfél beadja az autóját a szervízbe, akkor egy átvizsgálás/hibafelmérés, majd egy egyedi
ajánlatadás történik. Ez egy ingyenes szolgáltatás. Az autószerelő megvizsgálja a hibákat, és a pénzügyi
iroda számára átadja a szükséges alkatrészek listáját beszerzési árakkal együtt, ahol elkészítik az
ügyfélnek az ajánlatot.
Az ügyfelek lehetnek:
- vagy nem regisztrált ügyfelek
- vagy törzsvásárlói kártyás ügyfelek
- vagy VIP ügyfelek
  
A munkadíj a beszerelendő alkatrészek beszerzési árának 30%-a, de legalább 10.000 Forint.
Az alkatrészeket az ajánlatban 12%-os haszonnal növelten kell beszámítani.
A törzsvásárlói kártyás ügyfelek 5%-os végösszegkedvezményt kapnak. 
A VIP ügyfelek esetén az alkatrészekre csak 10% hasznot kell számolni, és
- 200.000 Forint feletti végösszegű ajánlat esetén 12.000 Forint
- 500.000 Forint feletti végösszegű ajánlat esetén 50.000 Forint
- 1.000.000 Forint feletti végösszegű ajánlat esetén 120.000 Forint
  kedvezményt kell nyújtani az alkatrészekre számított haszonból.

#### Előfeltétel:
A fordításhoz Gradle szükséges.
A konténerizáció Dockert használ.
(A project SpringBoot 3.4-es framework-kel és Java 21-gyel készült.)


#### Indítás:
A project letöltése után a root mappában a `./gradlew clean build --refresh-dependencies` parancsot adjuk ki.
Sikeres futtatása után a `docker-compose build --no-cache; docker-compose up -d` parancsot adjuk ki, szintén a root mappában.

#### Tesztelés:
A `http://localhost:8080/swagger-ui/index.html` oldalt használhatjuk, vagy töltsük le az xml-to-java-client alkalmazást a `https://github.com/gtulipan/autoservice-quote-neti-java_client` oldalról.

Swagger-es tesztelés esetén a felkínált Json-t kell módosítani/bővíteni.

Teszt kliens használatakor kérem kövesse a konzolon megjelenő lépéseket.