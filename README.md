# RockPaperScissors Rest API

## Elias Smeds

Ett rest api som hanterar logiken för spelet sten sax påse

### Vad du behöver för att kunna testa

* Maven för att kunna bygga och köra api:et

* HTTP-klient t ex. Postman för att kunna testa api:et

### Köra

Klona/ladda ner projektet,
öppna terminalen och
navigera till projektmappen,
Kör med maven, genom: mvn spring-boot:run.
Exempel: 

```
cd rockpaperscissors
```
```
mvn spring-boot:run
```

Nu kör api:et och är redo att testas med Postman.

När api:et testas lokalt ligger det på port 8080. 

#### För att skapa ett nytt spel:

Gör en POST förfrågan till adressen: http://localhost:8080/api/games 
med ett JSON-objekt med namn på player1 i requestbodyn.
RequestBody-exempel:
```
{
	"name":"Elias"
}
```
En lyckad förfrågan returnerar ett nytt JSON-objekt som innehåller ett spel-id samt statuskoden 200 OK.

Om namnet inte angivits eller om JSON-objektet är felaktigt uppbyggt, returneras statuskoden 
400 BAD REQUEST med meddelandet: "no name provided".

#### För att gå med i redan startat spel:

Gör en POST förfrågan till adressen: http://localhost:8080/api/games/id/join,
(där "id" byts ut mot spel-id:t som returnerades när spelet startade.)
med ett JSON-objekt med namn på player2 i requestbodyn (som i exempel ovan), 
Exempel på URL med spel-id.
```
http://localhost:8080/api/games/f91718b7-ee1c-4852-a933-9b73d8e0330d/join
```
En lyckad förfrågan ger statuskod 200 OK.

Obeservera att spelarnamnen i ett spel måste vara unika.

Om namnet inte angivits eller om JSON-objektet är felkonstruerat returneras statuskoden 400 BAD REQUEST med meddelandet: "no name provided".

Om namnet är identiskt med spelare1:s namn så returneras statuskoden 409 CONFLICT med
meddelande "name already in use".

#### För att göra ett drag:

Gör en POST förfrågan till adressen  http://localhost:8080/api/games/id/move (där id byts ut mot spel-id)
med ett JSON-objekt med namn på spelaren som gör draget och vilket drag hen väljer att göra se exempel nedan.
```
{
	"name":"Elias",
	"move":"Rock"
}
```
Möjliga drag är: Rock, Paper, Scissors

Observera att dragen måste skrivas exakt som i exemplet ovan, med inledande versal följt av gemener.

En lyckad förfrågan ger statuskod 200 OK.

Om namn inte angivits eller om JSON-objektet är felkonstruerat returneras statuskoden
400 BAD REQUEST med meddelande "No name provided".

Om move inte angivits eller om JSON-objektet är felkonstruerat returneras statuskoden
400 BAD REQUEST med meddelande "No move provided".

Om ett namn som inte finns i spelet anges, returneras statuskoden 404 Not FOUND med meddelandet
"Player not found".

Om en spelare försöker göra om sitt drag, returneras statuskoden 400 BAD REQUEST med 
meddelandet "Move already made".

Om spelare1 försöker göra sitt drag utan att någon gått med i spelet returneras statuskoden 
400 BAD REQUEST med meddelande "Game is not full".

#### För att hämta tillståndet på spel och se resultatet

Gör en GET förfrågan till adressen http://localhost:8080/api/games/id (där id byts ut mot spel-id).

En lyckad förfråga returnerar ett JSON-objekt med spelets tillstånd och statuskod 200 OK.

Om spelet inte är över, dvs om alla spelare inte gjort sitt drag än, innehåller returobjektet
namn på spelarna som är med i spelet samt spel-id. Detta för att minska risken för fusk.

Om spelet är över, innehåller returobjektet namn på spelarna som är med i spelet, spel-id,
namn på spelaren som vann och spelarnas drag.

Exempel på returnerat tillstånd när spelet är över:
```
{
    "game": "c53ce1dd-ac9d-4bee-8ca6-57045ae17cb5",
    "winner": "E",
    "player1": "elias",
    "player2": "E",
    "player1Move": "Scissors",
    "player2Move": "Rock"
}
```

Om ogiltigt spel-id anges i URL:en så returneras statuskoden 404 NOT FOUND med meddelandet
"Game not found"

Om ogiltigt spel-id-format anges i URL:en så returneras statuskoden 400 BAD REQUEST med 
meddelandet "Illegal gameid format"


### Byggt med hjälp av

* Spring Boot

* Maven

### Kodstil

* Api:t är byggt med IntelliJ:s default kodstil.
