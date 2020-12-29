# You and me, Umidity! ![Umidity Icon](img/icon64.png)

Umidity permette all'utente di selezionare diverse città per le quali viusalizzare l'umidità corrente e, se desiderato, salvarne i dati periodicamente per generare poi statistiche sull'umidità.


### Overview
Umidity si presenta con due modalità di utilizzo:
1. il primo (funzionante) è tramite una interfaccia grafica;
2. il secondo (in via di sviluppo), tramite linea di comando.


###### Getting started: GUI
L'interfaccia è estremamente compatta ed intuitiva.


![immagine gui]()


Come prima cosa, inserire il nome di una città (per evitare ambiguità, è consigliato inserire anche [stato](#state-code)
 e [codice postale](#zip-code) ) e premere "Search".
 
 
![immagine gui con precedenti passaggi effettuati]()


Sulla destra compariranno i risultati della ricerca che mostrano umidità e temperatura corrente, ma anche previsioni dei
prossimi 5 giorni, una ogni 3 ore.


A questo punto possiamo decidere di inserire quest'ultima città nella lista delle "[città salvate](#saved-cities)" per le quali verranno
salvate localmente e periodicamente le informazioni meteo sulle quali, poi, si potranno calcolare [statistiche](#statistics) sul massimo, minimo,
media e varianza dell'umidità. Infatti, si potranno calcolare statistiche SOLO sulle città contrasegnate come "[città salvate](#saved-cities)".

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

#Contributors
[Formattato come DDC dagli un occhio begannio] [Email studente o personale?]

Name | Email | GitHub | Contributing | LinkedIn 
--- | --- | --- | --- |--- 
Began Bajrami | // |  [begbaj](https://github.com/begbaj)| <p align="center">1/2<p align="center"> | // 
Rahmi El Mechri | rahmmi.elmechri@gmail.com |  [OT-Rax](https://github.com/OT-Rax) | <p align="center">1/2<p align="center">|https://www.linkedin.com/in/rahmi-el-mechri-7891701a1

#Disclaimer
Umidity è un programma sviluppato ai fini del progetto d'esame del corso di "Programmazione ad Oggetti" A/A 2020-2021 al
Università Politecnica delle Marche, non lo si deve considerare come un programma commerciale e pertanto non garantisce
il suo corretto funzionamento. Inoltre, non è consentito l'uso del programma e delle parti di codice ad esso correlato per
scopi commerciali.
