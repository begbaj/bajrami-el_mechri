# You and me, Umidity! ![Umidity Icon](img/icon32.png)

Umidity permette all'utente di selezionare diverse città per le quali viusalizzare l'umidità corrente e, se desiderato, salvarne i dati periodicamente per generare poi statistiche sull'umidità.


### Overview
Umidity si presenta con due modalità di utilizzo:
1. il primo (funzionante) è tramite una interfaccia grafica;
2. il secondo (in via di sviluppo), tramite linea di comando.


#### Getting started: GUI
L'interfaccia è estremamente compatta ed intuitiva.


![immagine gui - primo avvio](img/umidity_gui_empty.png)


Come prima cosa, inserire il nome di una città (per evitare ambiguità, è consigliato inserire anche [stato](#state-code)
 o [codice postale](#zip-code) ) e premere "Search".
 **è possibile fare anche una ricerca per solo codice postale. In tal caso, sarà necessario indicare anche il codice di stato poichè Openweather utilizzerà il formato nord americano se non indicato diversamente!**
 
 
![immagine gui - ricerca effettuata](img/umidity_gui_searched.png)


Sulla destra compariranno i risultati della ricerca che mostrano umidità e temperatura corrente, ma anche previsioni dei
prossimi 5 giorni, una ogni 3 ore.


A questo punto possiamo decidere di inserire quest'ultima città nella lista delle "[città salvate](#saved-cities)" per le quali verranno
salvate localmente e periodicamente le informazioni meteo sulle quali, poi, si potranno calcolare [statistiche](#statistics) sul massimo, minimo,
media e varianza dell'umidità. Infatti, si potranno calcolare statistiche SOLO sulle città contrasegnate come "[città salvate](#saved-cities)".

![immagine gui - statistiche disponibili](img/umidity_gui_statistics_enabled.png)


#### Getting started: CLI



## Project
Il programma nasce come progetto d'esame, perciò il nostro obiettivo è stato quello di soddisfare tutte le richieste finendo, poi, per implementare anche funzioni aggiuntive.
La consegna prevede, in origine, che il programma implementi un servizio meteo dove, data una città, permetta di visualizzare l'umidità attuale e archiviare il dato periodicamente (una volta all'ora) per permettere il calcolo di alcune semplici statistiche con la possibilità di selezionare su quale periodo calcolarle.
Alla consegna originale, abbiamo aggiunto anche la possibilità di fare richieste per le previsioni dei prossimi 5 giorni e richieste dei 5 giorni precedenti, oltre che visualizzare anche la temperatura.

I dati riguardanti l'umidità sono ottenuti attraverso le API di OpenWeather e poi salvati come oggetti JSON su un "[database](#database)" (ovvero una directory sul file system).

### Diagrams and Designing


#### Use case diagram


#### Class diagram


#### Sequence diagram


#### Some ideas
[lista di idee scritte in progettazione coon spunta su quelle implementate]


### Source documentation
Scrivere una documentazione dettagliata del codice sorgente occuperebbe troppo spazio in un semplice file README.md come questo (per una documentazione dettagliata andare al seguente [link](doc) ), perciò di seguito vederemo solo le parti che consideriamo di notevole importanza:


#### API



#### GUI
La GUI è stata sviluppata utilizzando Java Swing. I componenti utilizzati provengono dalla libreria Swing,
ad eccezione di quelli utilizzati per la selezione delle date [LIB. jDatePicker, LINK ALLE DEPENDENCIES SOTTO],
e di quelli utilizzati per la creazioni di grafici [LIB. jFree, LINK ALLE DEPENDENCIES SOTTO].


[MOSTRA JDATEPICKER][MOSTRA GRAFICI]


E' stata utilizzata una libreria esterna anche per la personalizzazione del tema dell'interfaccia.
[LIB. FlatLaf, LINK ALLE DEPENDENCIES SOTTO]


[MOSTRA LIGHT][MOSTRA DARK][GIF CAMBIAMENTO DA SETTINGS?]


Per l'aggiornamento sincrono delle città salvate è stata implementata un'interfaccia, utilizzata per lanciare
degli eventi che aggiornassero lo stato della City List su entrambi i Frame, qualora ci siano cambiamenti.


[GIF MAIN+SETTINGS AGGIORNAMENTO CITTA'???]


##### Area Selection
<a name="state-code"></a>
Nota Bene: lo stato va indicato tramite lo "State Code"
<a name="saved-cities"></a>


#### CLI
#### Database Manager
Il Database Manager si occupa di salvare su file, come oggetti JSON, le impostazioni e la lista delle città salvate 
con relativi dati acquisiti. 
Per la serializzazione/deserializzazione di questi è stata utilizzata una libreria esterna[LIB. Jackson, LINK DEPENDENCIES]

[MOSTRA CODICE SERIALIZZAZIONE/DESERIALLIZZAZIONE]
[MOSTRA IL CONTENUTO DI TUTTI I FILE]



Città salvate:
#### Statistics
### Tests
## Resources
### Software
### Dependencies

# Contributors

Name | Email | GitHub | Contributing | LinkedIn 
--- | --- | --- | --- |--- 
Began Bajrami | beganbajrami@outlook.it |  [begbaj](https://github.com/begbaj) /  [dyrem](https://github.com/dyremm)| <p align="center">1/2<p align="center"> | https://www.linkedin.com/in/begbaj/ 
Rahmi El Mechri | rahmmi.elmechri@gmail.com |  [OT-Rax](https://github.com/OT-Rax) | <p align="center">1/2<p align="center">|https://www.linkedin.com/in/rahmi-el-mechri-7891701a1

# Disclaimer
Umidity è un programma sviluppato ai fini del progetto d'esame del corso di "Programmazione ad Oggetti" A/A 2020-2021 al
Università Politecnica delle Marche, non lo si deve considerare come un programma commerciale e pertanto non garantisce
il suo corretto funzionamento. Inoltre, non è consentito l'uso del programma e delle parti di codice ad esso correlato per
scopi commerciali.
