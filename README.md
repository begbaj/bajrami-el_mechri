#You and me, Umidity!
Umidity permette all'utente di selezionare diverse città per le quali viusalizzare l'umidità corrente e, se desiderato,
salvarne i dati periodicamente per generare poi statistiche sull'umidità.
### Overview
Umidity si presenta con due modalità di utilizzo: il primo (funzionante) è tramite una interfaccia grafica; il secondo
(in via di sviluppo), tramite linea di comando.
###### Getting started: GUI
L'interfaccia è estremamente compatta ed intuitiva.
[immagine gui]
Come prima cosa, inserire il nome di una città (per evitare ambiguità, è consigliato inserire anche stato [link al paragrafo sotto]
 e codice postale [Link al paragrafo sotto] ) e premere "Search".
[immagine gui con precedenti passaggi effettuati]
Sulla destra compariranno i risultati della ricerca che mostrano umidità e temperatura corrente, ma anche previsioni dei
prossimi 5 giorni, una ogni 3 ore.

A questo punto possiamo decidere di inserire quest'ultima città nella lista delle "città salvate" per le quali verranno
salvate localmente e periodicamente le informazioni meteo sulle quali, poi, si potranno calcolare statistiche sul massimo, minimo,
media e varianza dell'umidità. Infatti, si potranno calcolare statistiche SOLO sulle città contrasegnate come "città salvate".
[immagine gui con spunta sulla città salvata e evidenziamento delle statistiche]

###### Getting started: CLI


## Project
Il programma nasce come progetto d'esame, perciò il nostro obiettivo è stato quello di soddisfare tutte le richieste, implementando funzioni aggiuntive a nostro piacimento.
La consegna prevede che il programma implementi un servizio meteo che, data una città, permetta di visualizzare tutte le informazioni relative all'umidità attuale,
a cui abbiamo aggiunto anche una funzione di forecasting(previsioni meteo).
Inoltre il progetto richiede che il servizio salvi le informazioni ogni ora,
quindi generare delle statistiche su quest'ultime, permettendo anche di decidere su che lasso di tempo calcolarle.
I dati riguardanti l'umidità sono ottenuti attraverso richieste effettuate alle API di OpenWeather, e poi salvati come oggetti JSON su un database.
### Diagrams
### Source documentation
#### API
#### GUI
La GUI è stata sviluppata 
##### Area Selection
Nota Bene: lo stato va indicato tramite lo "State Code"
#### CLI
#### Database Manager
Città salvate:
#### Statistics
### Tests
## Resources
### Software
### Dependencies
#Contributors

#Disclaimer
Umidity è un programma sviluppato ai fini del progetto d'esame del corso di "Programmazione ad Oggetti" A/A 2020-2021 al
Università Politecnica delle Marche, non lo si deve considerare come un programma commerciale e pertanto non garantisce
il suo corretto funzionamento. Inoltre, non è consentito l'uso del programma e delle parti di codice ad esso correlato per
scopi commerciali.
