# Progetto Automata

## Feature

- Creazione di un Automata
  
- Modifica di un Automata
  
- Caricare un Automata da un file
  
- Salvare l'Automata in un file
  
- Simulare l'Automata con una parola di test
  
- Salvare l'immagine che rappresenta l'automata
  

## Dipendenze

L'applicazione richiede che il programma [Graphviz](https://graphviz.org) si trova nel PATH del sistema operativo. Per installare Graphviz, seguire le istruzioni riportate su questo link: [Download | Graphviz](https://graphviz.org/download/).

## Compatibilità

L'applicazione è stata testata sia con sistemi operativi basati su Ubuntu sia su sistemi operativi basati su Arch. Compatibilità con Windows è ancora da implementare. Non si prevede la compatibilità con MacOS.

## Breve tutorial

L'applicazione è composta da una finestra principale, contenente sei pulsanti per modifcare l'automata, una casella di testo con sotto un pulsante simula e, nella parte superiore dell'applicazione, un menù a tendina.

Premendo in uno dei sei pulsanti si è in grado di modificare l'automata.

Limitazioni:

- Esiste solo un nodo iniziale, non c'è limite al numero di nodi intermedi e finali
  
- I nomi dei pesi e degli archi possono contenere solo caratteri dalla A alla Z, dalla a alla z e da 0 a 9. è possibile inserire nomi estremamente lunghi, ma questo è sconsigliato visto che porta a problemi di visualizzazione dell'automata
  

Cliccando sul sottomenu file è possibile caricare e salvare sul disco un'automa, salvare l'immagine dell'automa su disco (è possibile salvare l'immagine solo con estensione PNG) ed è possibile creare un nuovo automata. ATTENZIONE: mentre per nuovo automata il programma chiede se si desidera salvare l'automata attualmente presente nell'applicazione, questo non succede con la voce carica automata.

Cliccando su help - about è possibile aprire la pagina github dell'applicazione

La simulazione di una parola di test avviene inserendo una parola qualsiasi dentro la text field presente nella finestra principale e cliccando sul pulsante simula.

Per sapere di più su come funzionano gli automi sono consigliati i seguenti link:

- [Automata theory - Wikipedia](https://en.wikipedia.org/wiki/Automata_theory) (in inglese)
