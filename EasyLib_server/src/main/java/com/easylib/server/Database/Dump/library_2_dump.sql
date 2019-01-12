CREATE DATABASE  IF NOT EXISTS `library_2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `library_2`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library_2
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `identifier` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `pageCount` int(11) DEFAULT NULL,
  `printType` varchar(30) DEFAULT NULL,
  `readingModes` varchar(255) DEFAULT NULL,
  `imageLinks` varchar(1000) DEFAULT NULL,
  `canonicalVolumeLink` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  `infoLink` varchar(1000) DEFAULT NULL,
  `previewLink` varchar(255) DEFAULT NULL,
  `averageRating` float DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `ratingsCount` int(11) DEFAULT NULL,
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `language` varchar(10) DEFAULT NULL,
  `allowAnonLogging` varchar(15) DEFAULT NULL,
  `maturityRating` varchar(100) DEFAULT NULL,
  `contentVersion` varchar(255) DEFAULT NULL,
  `publishedDate` varchar(255) DEFAULT NULL,
  `panelizationSummary` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_1` varchar(400) DEFAULT NULL,
  `author_2` varchar(255) DEFAULT NULL,
  `author_3` varchar(255) DEFAULT NULL,
  `author_4` varchar(255) DEFAULT NULL,
  `category_1` varchar(255) DEFAULT NULL,
  `category_2` varchar(255) DEFAULT NULL,
  `category_3` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `reserved` tinyint(1) NOT NULL DEFAULT '0',
  `wating_list` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`identifier`),
  UNIQUE KEY `books_Id_uindex` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('1626366543','ISBN_10',328,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=aAonAgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=aAonAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://books.google.com/books/about/Maradona.html?hl=&id=aAonAgAAQBAJ','“Sometimes I think that my whole life is on film, that my whole life is in print. But it’s not like that. There are things which are only in my heart—that no one knows. At last I have decided to tell everything.”—Diego Maradona A poor boy from a Buenos Aires shanty town, Diego Maradona became a genius with the soccer ball, kicking his way to the heights of South American, European, and world soccer, yet his struggles with the pressures of life inside and outside the game repeatedly threatened to tear him and his legend down. Hero or villain, one thing about Maradona is certain: He was the greatest soccer player of his generation—and perhaps of all-time. Never before has the legendary Maradona given us his extraordinary story in his own words—until now. From his poverty-stricken origins to his greatest glories on the field, Maradona recounts, with astonishing frankness and brilliant insight, the pivotal moments of his life—the pressures of being a child prodigy, the infamous semi-final game against England in the 1986 World Cup, an incredible turn-around and the dream-turned-sour at Napoli, and the shame and disgrace of his positive drug test at USA 1994. In this amazingly honest autobiography, we see inside the mind of one of the most talented, controversial, and complex sportsmen of our times—a man torn between the demands of corporate club bosses, the fans, the media, and his own tempestuous personal life. With a new epilogue exclusive to this paperback edition that brings Maradona’s remarkable story up-to-date and more than eighty wonderful photographs, Maradona is a confessional, a revelation, an apology, and a celebration.','http://books.google.it/books?id=aAonAgAAQBAJ&dq=Maradona&hl=&source=gbs_api','http://books.google.it/books?id=aAonAgAAQBAJ&printsec=frontcover&dq=Maradona&hl=&cd=2&source=gbs_api',NULL,'Skyhorse Publishing, Inc.',NULL,2,'en','true','NOT_MATURE','1.1.1.0.preview.3','2011-02-08','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}','The Autobiography of Soccer\'s Greatest and Most Controversial Star','Maradona','Diego Armando Maradona',NULL,NULL,NULL,'Sports & Recreation',NULL,NULL,NULL,0,0),('8822712951','ISBN_10',384,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=Y94uDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=Y94uDwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-Y94uDwAAQBAJ','Enigmatico come Il nome della rosa Magnetico come La cattedrale del mare Un grande thriller Palermo. Un antiquario, proprietario di una libreria di testi antichi, viene brutalmente assassinato. Il colpo mortale è inferto da un pugnale che pare risalire al 1500. Dopo qualche giorno, nella tranquilla Firenze, viene ucciso un collezionista di libri d’epoca. Lo strano dettaglio è che l’arma del delitto è un pugnale identico a quello dell’omicidio siciliano. Stessa sorte tocca a un operaio che si occupa dei lavori di restauro nella basilica di San Domenico a Palermo: il suo corpo viene rinvenuto privo di vita. Le indagini portano ben presto a una scoperta sconcertante: l’esistenza di una setta, nata in Italia alla fine del 1400, ma sopravvissuta fino ai giorni nostri, quella dei Frateschi. I suoi adepti sono fedeli all’insegnamento di Girolamo Savonarola, il frate predicatore, e cercano da secoli il testamento che egli affidò ad alcuni discepoli prima di essere arrestato. E se tutti i delitti fossero legati a quel manoscritto? La scia di sangue, intanto, sembra destinata a non arrestarsi. Chi muove i fili dell’intricato complotto che affonda le sue radici in tempi lontanissimi? Una serie di oscuri delitti Una setta misteriosa in cerca di vendetta Un testamento scomparso che lascia dietro di sé una scia di sangue Un esordio sorprendente Il caso editoriale dell’anno Carmelo Nicolosi De LucaÈ nato a Catania, ma vive a Palermo, dove scrive per il «Giornale di Sicilia». Ha lavorato 23 anni per il «Corriere della Sera». Ha curato inchieste e servizi da Europa, Asia, Africa, Medio Oriente, Sudafrica, Americhe, intervistando molti personaggi che hanno fatto la storia mondiale, tra cui Nelson Mandela. Si è dedicato solo al giornalismo fino a pochi anni fa, quando è ritornato alla vecchia passione di scrittore, pubblicando L’Italia degli inganni. Il genere che preferisce, però, è il thriller. L’autore è stato insignito, nella sua carriera, di numerosi riconoscimenti nazionali e internazionali.','https://play.google.com/store/books/details?id=Y94uDwAAQBAJ&source=gbs_api','http://books.google.it/books?id=Y94uDwAAQBAJ&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=9&source=gbs_api',NULL,'Newton Compton Editori',NULL,19,'it','true','NOT_MATURE','1.2.2.0.preview.3','2017-09-28','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La congiura dei monaci maledetti','Carmelo Nicolosi De Luca',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8830430714','ISBN_10',650,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=xjDz1Y6AbH8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=xjDz1Y6AbH8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-xjDz1Y6AbH8C','UN BESTSELLER INTERNAZIONALE OGGI UN GRANDE SUCCESSO IN TV SU NETFLIX Barcellona, XIV secolo. Nel cuore dell’umile quartiere della Ribera gli occhi curiosi del piccolo Arnau sono catturati dalle maestose mura di una grande chiesa in costruzione. Un incontro decisivo, poiché la storia di Santa Maria del Mar sarà il cardine delle tormentate vicende della sua esistenza. Figlio di un servo fuggiasco, nella capitale catalana Arnau trova rifugio e quella sospirata libertà che a tutt’oggi incarna lo spirito di Barcellona, all’epoca in pieno fermento: i vecchi istituti feudali sono al tramonto mentre mercanti e banchieri sono in ascesa, sempre più influenti nel determinare le sorti della città, impegnata in aspre battaglie per il controllo dei mari. Intanto l’azione dell’Inquisizione minaccia la non facile convivenza fra cristiani, musulmani ed ebrei... Personaggio di inusuale tempra e umanità, Arnau non esita a dedicarsi con entusiasmo al grande progetto della «cattedrale del popolo». E all’ombra di quelle torri gotiche dovrà lottare contro fame, ingiustizie e tradimenti, ataviche barriere religiose, guerre, peste, commerci ignobili e indomabili passioni, ma soprattutto per un amore che i pregiudizi del tempo vorrebbero condannare alle brume del sogno... Un’opera in cui avventura e sentimento si uniscono al romanzo di una città, protagonista anch’essa di una straordinaria vicenda corale, restituita nella drammaticità dei suoi momenti cruciali così come nella sua vivacissima quotidianità, in un’ambientazione capace di ricreare, con limpidezza superiore alla penna di uno storico, luci e ombre di un Medioevo di ineguagliabile fascino.','https://play.google.com/store/books/details?id=xjDz1Y6AbH8C&source=gbs_api','http://books.google.it/books?id=xjDz1Y6AbH8C&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=1&source=gbs_api',4.5,'Longanesi',5,11,'it','true','NOT_MATURE','2.55.50.0.preview.3','2010-12-31T00:00:00+01:00','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La cattedrale del mare','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8830430730','ISBN_10',924,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=EX3RhtD7UrcC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=EX3RhtD7UrcC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-EX3RhtD7UrcC','Nei villaggi delle Alpujarras è esploso il grido della ribellione. Stanchi di ingiustizie e umiliazioni, i moriscos si battono contro i cristiani che li hanno costretti alla conversione. È il 1568. Tra i rivoltosi musulmani spicca un ragazzo di quattordici anni dagli occhi incredibilmente azzurri. Il suo nome è Hernando. Nato da un vile atto di brutalità – la madre morisca fu stuprata da un prete cristiano –, il giovane dal sangue misto subisce il rifiuto della sua gente. La rivolta è la sua occasione di riscatto: grazie alla sua generosità e al coraggio, conquista la stima di compagni più o meno potenti. Ma c’è anche chi, mosso dall’invidia, trama contro di lui. E quando nell’inferno degli scontri conosce Fatima, una ragazzina dagli immensi occhi neri a mandorla che porta un neonato in braccio, deve fare di tutto per impedire al patrigno di sottrargliela. Inizia così la lunga storia d’amore tra Fatima ed Hernando, un amore ostacolato da mille traversie e scandito da un continuo perdersi e ritrovarsi. Ma con l’immagine della mamma bambina impressa nella memoria, Hernando continuerà a lottare per il proprio destino e quello del suo popolo. Anche quando si affaccerà nella sua vita la giovane cattolica Isabel... Storia attualissima di uno scontro fra religioni calato nell’epopea di un intero popolo, La mano di Fatima consacra Ildefonso Falcones maestro del thriller storico.','https://play.google.com/store/books/details?id=EX3RhtD7UrcC&source=gbs_api','http://books.google.it/books?id=EX3RhtD7UrcC&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=7&source=gbs_api',NULL,'Longanesi',NULL,17,'it','true','NOT_MATURE','2.44.39.0.preview.3','2010-11-02T00:00:00+01:00','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La mano di Fatima','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8830439142','ISBN_10',704,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=HYu3AAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=HYu3AAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-HYu3AAAAQBAJ','DOPO IL BESTSELLER LA CATTEDRALE DEL MARE UN SUCCESSO INTERNAZIONALE DA 7 MILIONI DI LETTORI Da Siviglia a Madrid, l’avventura di due donne che lottano per l’amicizia, l’amore e la libertà. Siviglia, gennaio 1748. Una giovane donna con la pelle nera come l’ebano cammina lungo le strade della città andalusa. Il suo nome è Caridad; si è lasciata alle spalle un passato di schiavitù nella lontana colonia di Cuba, ma il paese sconosciuto in cui si ritrova inaspettatamente libera le appare persino più spaventoso delle catene. Il suo destino sembra ormai segnato quando incrocia i passi di Melchor, un gitano rude ma affascinante. Accolta nel borgo di Triana, dove il ritmo dei martelli nelle fucine dei fabbri fa da sottofondo al cante flamenco e alle sensuali movenze delle danze gitane, Caridad conosce Milagros, la bella nipote di Melchor, e tra le due donne nasce un’amicizia profonda. E mentre la gitana, nelle cui vene scorre il sangue della ribellione, confessa il proprio amore per l’arrogante Pedro García, dal quale la separano le antiche faide tra la famiglia del ragazzo e la sua, Caridad lotta per nascondere il sentimento che, ogni giorno più forte, la lega a Melchor. Ma una tempesta ben più devastante sta per abbattersi sui loro tormenti: nel luglio 1749 i gitani vengono deportati in massa e condannati ai lavori forzati e alla reclusione, in quella che passerà alla storia come la grande retata. La vita di Milagros, sfuggita alla cattura, imbocca una drammatica svolta, e poco dopo un’altra, più intima tragedia la obbliga a separarsi da Caridad. Le loro strade si allontanano, ma il destino porterà entrambe a Madrid, cuore pulsante di una Spagna in cui soffia il vento del cambiamento... Una storia capace di commuovere e di indignare, ma soprattutto di emozionare; un impetuoso affresco popolato di personaggi indimenticabili che vivono, amano e lottano per la libertà, tratteggiato con la maestria di cui Ildefonso Falcones ha dato ampiamente prova con La cattedrale del mare, narrando la Storia dalla parte degli oppressi e creando personaggi memorabili.','https://play.google.com/store/books/details?id=HYu3AAAAQBAJ&source=gbs_api','http://books.google.it/books?id=HYu3AAAAQBAJ&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=8&source=gbs_api',NULL,'Longanesi',NULL,18,'it','true','NOT_MATURE','2.29.28.0.preview.3','2013-11-11T00:00:00+01:00','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La regina scalza','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8830447234','ISBN_10',906,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=0xrCDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=0xrCDAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-0xrCDAAAQBAJ','«Un grande bestseller, un romanzo grandioso come una cattedrale» El Pais Barcellona, 1387. Arnau Estanyol, dopo le mille traversie che hanno segnato la sua vita e la costruzione della grandiosa Cattedrale del Mare, è ormai uno dei più stimati notabili di Barcellona. Giunto in città ancora in fasce e stretto tra le braccia del padre, un misero bracciante, nessuno sa meglio di lui quanto Barcellona possa essere dura e ingiusta con gli umili. Tanto che oggi è Amministratore del Piatto dei Poveri, un’istituzione benefica della Cattedrale del Mare che offre sostegno ai più bisognosi mediante le rendite di vigneti, palazzi, botteghe e tributi, ma anche grazie alle elemosine che lo stesso Arnau si incarica di raccogliere per le strade. Sembra però che la città pretenda da lui il sacrificio estremo. Ed è proprio dalla chiesa tanto cara ad Arnau a giungere il segnale d’allarme. Le campane di Santa Maria del Mar risuonano in tutto il quartiere della Ribera; rintocchi a lutto, che annunciano la morte di re Pietro... Ad ascoltare quei suoni con particolare attenzione c’è un ragazzino di soli dodici anni. Si chiama Hugo Llor, è figlio di un uomo che ha perso la vita in mare, e ha trovato lavoro nei cantieri navali grazie al generoso interessamento di Arnau. Ma i suoi sogni di diventare un maestro d’ascia e costruire le splendide navi che per ora guarda soltanto dalla spiaggia si infrangono contro una realtà spietata. Al seguito dell’erede di Pietro, Giovanni, tornano in città i Puig, storici nemici di Arnau: finalmente hanno l’occasione di mettere in atto una vendetta che covano da anni, tanto sanguinosa quanto ignobile... Da quel momento, la vita di Hugo oscillerà tra la lealtà a Bernat, l’unico figlio di Arnau, e la necessità di sopravvivere. Dieci anni dopo La cattedrale del mare, Ildefonso Falcones torna al mondo che tanto ama e che così bene conosce: la Barcellona del Quattrocento. Tra le terre profumate di vino della Catalogna, negli anni turbolenti del Concilio di Costanza, ricrea alla perfezione una società effervescente ma imbrigliata da una nobiltà volubile e corrotta, nella quale emerge la lotta di un uomo per una vita che non sacrifichi dignità e affetti. Uno straordinario romanzo di lealtà e vendetta, amori e sogni, ma soprattutto di fortissime emozioni.','https://play.google.com/store/books/details?id=0xrCDAAAQBAJ&source=gbs_api','http://books.google.it/books?id=0xrCDAAAQBAJ&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=5&source=gbs_api',NULL,'Longanesi',NULL,15,'it','true','NOT_MATURE','1.23.21.0.preview.3','2016-10-03T00:00:00+02:00','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'Gli eredi della terra','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8830447463','ISBN_10',1560,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=pwcbDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=pwcbDQAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-pwcbDQAAQBAJ','IL VOLUME RACCOGLIE LA CATTEDRALE DEL MARE E IL SUO SEGUITO GLI EREDI DELLA TERRA Pensare alla Cattedrale del mare per me è come ascoltare una di quelle vecchie canzoni che hanno fatto da sottofondo ai momenti cardine della mia vita: un primo amore, magari un’amara delusione, feste, conversazioni, amici, un successo, sofferenze, non poche, ma anche gioie, speranze e desideri. Mille sensazioni che hanno fatto presa in me e che ancora porto dentro, finché un giorno la nostalgia, sulle note di una di quelle canzoni, non le ha fatte rinascere con una forza incredibile, come se si fossero appena manifestate. Arnau Estanyol e sua moglie Mar, i perfidi Puig, il barrio della Ribera, i pescatori, i bastaixos e gli scambi commerciali. Potenti e umili. Il mare e quella Madonna che sorride. Barcellona. Se accarezzare la mano o il viso di una persona amata che abbiamo perso o rimediare a un errore commesso nel passato rimane impossibile, tornare alla Cattedrale del mare, ai suoi protagonisti e alla sua gente non lo è stato affatto. La storia, che nel primo romanzo si svolgeva nella Ribera, negli Eredi della terra si sposta nel Raval, con le magnifiche opere dei cantieri navali e dell’ospedale della Santa Cruz, sullo sfondo di un’epoca tanto splendida quanto sofferta, nella quale un principe ereditario castigliano ottiene la sovranità sulla Catalogna e sui rimanenti regni della corona di Aragona. Una sfida alla nostalgia di quella meravigliosa cattedrale. Ildefonso Falcones','https://play.google.com/store/books/details?id=pwcbDQAAQBAJ&source=gbs_api','http://books.google.it/books?id=pwcbDQAAQBAJ&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=2&source=gbs_api',NULL,'Longanesi',NULL,12,'it','true','NOT_MATURE','1.14.12.0.preview.3','2016-10-03T00:00:00+02:00','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La cattedrale del mare e Gli eredi della terra','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8852025197','ISBN_10',312,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=Oux2kOuyPkUC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=Oux2kOuyPkUC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-Oux2kOuyPkUC','Sessant\'anni di attese e delusioni per Napoli e per il Napoli. Poi l\'arrivo di Diego Armando Maradona nel 1984, il sole che illumina una città e che ha segnato per sempre la vita di ogni vero tifoso. La stagione fatidica è quella 1986-87, una cavalcata entusiasmante verso lo scudetto e la Coppa Italia, che porta a un doppio, sospiratissimo trionfo.','https://play.google.com/store/books/details?id=Oux2kOuyPkUC&source=gbs_api','http://books.google.it/books?id=Oux2kOuyPkUC&pg=PT22&dq=Maradona&hl=&cd=3&source=gbs_api',4,'Edizioni Mondadori',5,3,'it','true','NOT_MATURE','2.19.18.0.preview.3','2012-05-22','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'Il Napoli di Maradona','Gigi Garanzini','Marco Bellinazzo',NULL,NULL,'Sports & Recreation',NULL,NULL,NULL,0,0),('8852074791','ISBN_10',228,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=ritADAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=ritADAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-ritADAAAQBAJ','Il 29 giugno 1986 Diego Armando Maradona, capitano e giocatore simbolo dell\'Argentina, alzava al cielo di Città del Messico la Coppa del Mondo, toccando il punto più alto della sua luminosissima carriera. E vi arrivava dopo aver scritto, in quel Mondiale, alcune delle pagine più belle della storia del calcio, tra cui l\'indimenticabile «partita delle partite», il quarto di finale con l\'Inghilterra, i 90 minuti in cui è condensato tutto Maradona. Dall\'irriverenza del gol di mano, mai rinnegato e anzi giustificato da un intervento soprannaturale, la «mano de Dios», alla sapienza calcistica elevata alla massima potenza in quello che è unanimemente considerato il gol più bello di sempre, uno slalom a saltare giocatori come birilli, che qui viene descritto mirabilmente dal grande giornalista e scrittore Víctor Hugo Morales. Ma anche l\'orgoglio patriottico del combattente che, sconfiggendo sul campo gli avversari inglesi, vendica un popolo ferito dalla sanguinosa guerra delle Malvine, il cui ricordo era ancora troppo fresco per pensare che quella fra Argentina e Inghilterra potesse essere solo una partita di calcio. Nel trentennale della fortunata spedizione messicana Diego ci racconta molto più di ciò che si vide allora sul terreno di gioco: ci porta in ritiro, sui campi d\'allenamento e nelle camere d\'albergo, nelle riunioni improvvisate tra i giocatori, che cambiarono il volto della Selección e il suo destino, negli spogliatoi, sui pullman e sugli aerei dove è nato e si è cementato quell\'incredibile gruppo da lui capitanato e condotto verso la gloria eterna. Contro tutto e contro tutti. Lo scetticismo dei tifosi e della stampa argentini. Il «caso Passarella» e quello di un allenatore, Bilardo, mai stimato ma comunque difeso, per il bene della squadra, contro il governo argentino che lo voleva esonerare. Gli intrighi dei politicanti del calcio. E gli avversari, di valore come Rummenigge, Zico e Lineker, o abili ma privi dei suoi valori come il «senzasangue» Platini. Infine il Pibe de oro, senza alcuna invidia per i campioni che verranno, ci dà la sua ricetta perché la Selección dell\'erede Messi torni presto a sollevare la Coppa del Mondo, anche se sa perfettamente che, per quanti titoli possa vincere e per quanti campioni possa sfornare, l\'Argentina non potrà mai più schierare a un Mondiale un altro Diego Armando Maradona.','https://play.google.com/store/books/details?id=ritADAAAQBAJ&source=gbs_api','http://books.google.it/books?id=ritADAAAQBAJ&printsec=frontcover&dq=Maradona&hl=&cd=4&source=gbs_api',NULL,'Edizioni Mondadori',NULL,4,'it','true','NOT_MATURE','1.22.21.0.preview.3','2016-06-21','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La mano di Dio','Daniel Arcucci','Diego Armando Maradona',NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('8854158852','ISBN_10',384,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=AciWAAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=AciWAAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-AciWAAAAQBAJ','Cancellati dalla storiaCondannati all\'oscuritàLa loro opera è un sacrilegioUn grande thriller storicoAdorano una macabra reliquiaSono cresciuti all\'ombra della storiaChi sono gli eredi di Giovanni?Nella Milano del 1500, il prefetto Vittore ha finalmente deciso di abbandonare il celibato e cercare moglie. Ma i suoi piani vengono stravolti da un delitto brutale: una testa umana, poggiata su un piatto d’argento con incisa la frase latina Venit sol iustitiae, “Il sole della giustizia ha brillato”, viene ritrovata nel battistero della basilica di Sant’Ambrogio. Vittore, soprannominato da tutti Granchio Nero e noto per le sue eccezionali doti investigative, mette così da parte i suoi desideri e si butta a capofitto nelle indagini. Le sue attente e intelligenti ricerche lo porteranno in breve tempo a conoscere un’affascinante nobildonna e i più famosi personaggi dell’epoca – tutti coinvolti nella macabra vicenda – tra cui Michelangelo Buonarroti e Giulio II. E, soprattutto, lo porteranno ad avvicinarsi pericolosamente ai membri di una setta religiosa, gli Eredi di Giovanni, che adorano la testa imbalsamata del Battista. Una setta le cui origini risalgono ai tempi di Erode e Salomè e del loro Banchetto maledetto da Dio...Una setta religiosa dalle antiche originiUna macabra vicenda macchiata di sangueQuale terribile segreto minaccia di far tremare il Vaticano?«Didier Convard propone una storia ricca di personaggi scolpiti a tutto tondo, dando vita a un intrigo fitto di riferimenti esoterici.»lelitteraire.com Didier ConvardHa iniziato la sua carriera come sceneggiatore e disegnatore di fumetti, collaborando alla rivista «Tintin». Ha curato molte serie per la casa editrice Glénat, di cui oggi è direttore editoriale e per cui segue l’adattamento a fumetti del Piccolo Principe. Didier è stato molto attivo anche come sceneggiatore per la TV e il cinema. Fra le sue numerose pubblicazioni, la serie a fumetti Il triangolo segreto, in sette volumi, bestseller in Francia, e il romanzo Vinci et l’ange brisé, prima indagine del prefetto Vittore.','https://play.google.com/store/books/details?id=AciWAAAAQBAJ&source=gbs_api','http://books.google.it/books?id=AciWAAAAQBAJ&printsec=frontcover&dq=La+cattedrale+del+mare&hl=&cd=10&source=gbs_api',NULL,'Newton Compton Editori',NULL,20,'it','true','NOT_MATURE','1.10.11.0.preview.3','2013-09-05','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La cattedrale del peccato','Didier Convard',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8858684443','ISBN_10',192,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=S_U4DAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=S_U4DAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-S_U4DAAAQBAJ','San Salvador è una città grande come una balena, dentro la sua pancia c\'è un taxi in moto perpetuo e dentro quel taxi un uomo con la voce bassa e i ricci ormai grigi. Nella sua vita precedente quel tassista regalava sogni su un campo da calcio.Ma chi è stato davvero Jorge Gonzalez, per tutti i suoi tifosi semplicemente \"El Mágo\"? Uno dei più forti calciatori degli anni Ottanta, il miglior marcatore nella storia della Nazionale di El Salvador, un genio dimenticato da molti ma amato da sua maestà Diego Armando Maradona?Un fuoriclasse che gettò al vento la possibilità di essere ingaggiato dal Barcellona facendosi trovare a letto in dolce compagnia durante un\'evacuazione antincendio, che alla maglia numero 10 preferì sempre la 11 e ai ricchi club di mezza Europa il modesto Cádiz, tanto \"la paga era decente e ci potevo comprare un numero sufficiente di fritture di calamari\"? Oppure è stato solo un cialtrone, un impunito, un dongiovanni dedito alla notte e al flamenco, un ingenuo e un vecchio saggio, uno a cui piaceva usare la testa per pensare più che per colpire la palla. Forse è stato tutte queste cose insieme, Jorge, e oggi, lungo le strade di San Salvador, è un mago senza più trucchi, un tassista all\'ultima corsa, destinata a essere indimenticabile come le sue giocate in campo.Ripercorrendo la parabola sportiva e umana di Gonzalez e mescolando sogno e realtà, fantasia e Storia, mito e cronaca, Marco Marsullo ci racconta cosa sia la passione, la nostalgia, l\'infanzia, l\'amicizia, il senso di una vittoria e di una sconfitta. Tutto quello che è il calcio, tutto quello che è la vita.','https://play.google.com/store/books/details?id=S_U4DAAAQBAJ&source=gbs_api','http://books.google.it/books?id=S_U4DAAAQBAJ&printsec=frontcover&dq=Maradona&hl=&cd=5&source=gbs_api',NULL,'Rizzoli',NULL,5,'it','false','NOT_MATURE','1.18.17.0.preview.3','2016-05-19','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'Il tassista di Maradona','Marco Marsullo',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('8897555675','ISBN_10',208,'BOOK','{\"image\":true,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=J1Ixsl2rKQ0C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=J1Ixsl2rKQ0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-J1Ixsl2rKQ0C',NULL,'https://play.google.com/store/books/details?id=J1Ixsl2rKQ0C&source=gbs_api','http://books.google.it/books?id=J1Ixsl2rKQ0C&pg=PT94&dq=Maradona&hl=&cd=9&source=gbs_api',NULL,'Edizioni BeccoGiallo',NULL,9,'it','true','NOT_MATURE','preview-1.0.0','2012-12-19',NULL,NULL,'Diego Armando Maradona','Paolo Castaldi',NULL,NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('8898001339','ISBN_10',140,'BOOK','{\"image\":true,\"text\":true}','{\"thumbnail\":\"http://books.google.com/books/content?id=1O5_CgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=1O5_CgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"}','https://market.android.com/details?id=book-1O5_CgAAQBAJ','«Io sono un attore. Gli attori hanno un copione da leggere. Io non leggo. Vivo.»Diego Armando Maradona è stato uno dei più grandi campioni nella storia del calcio. Per molti, il più grande. Questa biografia è la narrazione di una vita che ne contiene altre dieci, cento, mille, tra inimitabili colpi di genio ed eccessi di ogni genere, vertiginose ascese e cadute rovinose. A leggere bene, un romanzo più che una biografia.','https://play.google.com/store/books/details?id=1O5_CgAAQBAJ&source=gbs_api','http://books.google.it/books?id=1O5_CgAAQBAJ&pg=PT14&dq=Maradona&hl=&cd=8&source=gbs_api',NULL,'40K',NULL,8,'it','false','NOT_MATURE','2.14.17.0.preview.3','2013-10-02','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'Maradona','Tosco Giovanni','Bocchio Sandro',NULL,NULL,'Language Arts & Disciplines',NULL,NULL,NULL,0,0),('9788830452190','ISBN_13',642,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=NyM5uwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=NyM5uwEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/La_cattedrale_del_mare.html?hl=&id=NyM5uwEACAAJ',NULL,'http://books.google.it/books?id=NyM5uwEACAAJ&dq=La+cattedrale+del+mare&hl=&source=gbs_api','http://books.google.it/books?id=NyM5uwEACAAJ&dq=La+cattedrale+del+mare&hl=&cd=3&source=gbs_api',NULL,'La Gaja scienza',NULL,13,'it','false','NOT_MATURE','preview-1.0.0','2018','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La cattedrale del mare','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('9788839716408','ISBN_13',334,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=Mvd8oAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=Mvd8oAEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/Maradona_Il_sogno_di_un_bambino.html?hl=&id=Mvd8oAEACAAJ',NULL,'http://books.google.it/books?id=Mvd8oAEACAAJ&dq=Maradona&hl=&source=gbs_api','http://books.google.it/books?id=Mvd8oAEACAAJ&dq=Maradona&hl=&cd=7&source=gbs_api',NULL,NULL,NULL,7,'it','false','NOT_MATURE','preview-1.0.0','2014-09',NULL,NULL,'Maradona. Il sogno di un bambino','Stefano Ceci',NULL,NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('9788849522648','ISBN_13',290,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=0VOipwAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=0VOipwAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/Maradona_e_il_Napoli_Un_mito_all_ombra_d.html?hl=&id=0VOipwAACAAJ',NULL,'http://books.google.it/books?id=0VOipwAACAAJ&dq=Maradona&hl=&source=gbs_api','http://books.google.it/books?id=0VOipwAACAAJ&dq=Maradona&hl=&cd=6&source=gbs_api',NULL,NULL,NULL,6,'it','false','NOT_MATURE','preview-1.0.0','2011',NULL,NULL,'Maradona e il Napoli. Un mito all\'ombra del Vesuvio','John Ludden',NULL,NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('9788850224326','ISBN_13',593,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=amSrcQAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=amSrcQAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/La_cattedrale_del_mare_Ediz_speciale_ill.html?hl=&id=amSrcQAACAAJ',NULL,'http://books.google.it/books?id=amSrcQAACAAJ&dq=La+cattedrale+del+mare&hl=&source=gbs_api','http://books.google.it/books?id=amSrcQAACAAJ&dq=La+cattedrale+del+mare&hl=&cd=4&source=gbs_api',NULL,NULL,NULL,14,'it','false','NOT_MATURE','preview-1.0.0','2010',NULL,NULL,'La cattedrale del mare. Ediz. speciale illustrata','Ildefonso Falcones',NULL,NULL,NULL,'Fiction',NULL,NULL,NULL,0,0),('9788860441577','ISBN_13',450,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=tSqERAAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=tSqERAAACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/Io_sono_El_Diego.html?hl=&id=tSqERAAACAAJ',NULL,'http://books.google.it/books?id=tSqERAAACAAJ&dq=Maradona&hl=&source=gbs_api','http://books.google.it/books?id=tSqERAAACAAJ&dq=Maradona&hl=&cd=1&source=gbs_api',NULL,NULL,NULL,1,'it','false','NOT_MATURE','preview-1.0.0','2010',NULL,NULL,'Io sono El Diego','Diego A. Maradona',NULL,NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('9788865830246','ISBN_13',249,'BOOK','{\"image\":false,\"text\":false}','{\"thumbnail\":\"http://books.google.com/books/content?id=zvn_ewEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=zvn_ewEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"}','https://books.google.com/books/about/Maradona_Il_calcio_sono_io.html?hl=&id=zvn_ewEACAAJ',NULL,'http://books.google.it/books?id=zvn_ewEACAAJ&dq=Maradona&hl=&source=gbs_api','http://books.google.it/books?id=zvn_ewEACAAJ&dq=Maradona&hl=&cd=10&source=gbs_api',NULL,NULL,NULL,10,'it','false','NOT_MATURE','preview-1.0.0','2011',NULL,NULL,'Maradona. «Il calcio sono io»','Alexandre Juillard',NULL,NULL,NULL,'Biography & Autobiography',NULL,NULL,NULL,0,0),('OCLC:799810264','OTHER',642,'BOOK','{\"image\":false,\"text\":false}',NULL,'https://books.google.com/books/about/La_cattedrale_del_mare.html?hl=&id=4zTMtwEACAAJ',NULL,'http://books.google.it/books?id=4zTMtwEACAAJ&dq=La+cattedrale+del+mare&hl=&source=gbs_api','http://books.google.it/books?id=4zTMtwEACAAJ&dq=La+cattedrale+del+mare&hl=&cd=6&source=gbs_api',NULL,NULL,NULL,16,'en','false','NOT_MATURE','preview-1.0.0','2007','{\"containsImageBubbles\":false,\"containsEpubBubbles\":false}',NULL,'La cattedrale del mare','Ildefonso Falcones de Sierra',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booksreservations`
--

DROP TABLE IF EXISTS `booksreservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `booksreservations` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_identifier` varchar(255) NOT NULL,
  `book_title` varchar(255) NOT NULL,
  `reservation_date` datetime NOT NULL,
  `starting_reservation_date` date NOT NULL,
  `ending_reservation_date` date NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`reservation_id`),
  UNIQUE KEY `table_name_reservation_id_uindex` (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booksreservations`
--

LOCK TABLES `booksreservations` WRITE;
/*!40000 ALTER TABLE `booksreservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `booksreservations` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_on_reservation` AFTER INSERT ON `booksreservations` FOR EACH ROW BEGIN

    update library_2.books set quantity = quantity - NEW.quantity and

                                          books.reserved = true



    where library_2.books.identifier = NEW.book_identifier;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_count_on_delivering` AFTER DELETE ON `booksreservations` FOR EACH ROW begin

    update library_2.books set quantity = quantity + OLD.quantity and

                                          books.reserved = false

    where library_2.books.identifier = OLD.book_identifier;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `waiting_list_flowing` AFTER DELETE ON `booksreservations` FOR EACH ROW begin



    declare cond integer;

    declare user_id integer;

    declare reservation_date DATETIME;

    declare start_res_date DATE;

    declare end_res_date DATE;

    declare quantity integer;



    set cond = (select waiting_position from library_2.waitinglist where waitinglist.book_identifier =

                                                                         OLD.book_identifier and waiting_position = 1);



    if cond > 0 then



      set user_id = (select user_id from waitinglist where waitinglist.book_identifier =

                                                           OLD.book_identifier and waiting_position = 1);

      set reservation_date = (select waitinglist.reservation_date from waitinglist where waitinglist.book_identifier =

                                                                                         OLD.book_identifier and waiting_position = 1);

      set start_res_date = (select waitinglist.user_id from waitinglist where waitinglist.book_identifier =

                                                                              OLD.book_identifier and waiting_position = 1);

      set end_res_date = (select waitinglist.ending_reservation_date from waitinglist where waitinglist.book_identifier =

                                                                                            OLD.book_identifier and waiting_position = 1);

      set quantity = (select waitinglist.quantity from waitinglist where waitinglist.book_identifier =

                                                                         OLD.book_identifier and waiting_position = 1);



      insert into booksreservations (user_id, book_identifier, book_title, reservation_date, starting_reservation_date,

                                     ending_reservation_date, quantity)

      values (user_id, old.book_identifier, old.book_title, reservation_date, start_res_date,end_res_date, quantity );



      delete from waitinglist where waitinglist.book_identifier = old.book_identifier and

                                    waiting_position = 1;

    end if;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `event_partecipants`
--

DROP TABLE IF EXISTS `event_partecipants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `event_partecipants` (
  `event_id` int(11) NOT NULL,
  `partecipant_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`,`partecipant_id`),
  UNIQUE KEY `event_partecipants_event_id_uindex` (`event_id`),
  UNIQUE KEY `event_partecipants_partecipant_id_uindex` (`partecipant_id`),
  CONSTRAINT `event_partecipants_events_id_fk` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_partecipants`
--

LOCK TABLES `event_partecipants` WRITE;
/*!40000 ALTER TABLE `event_partecipants` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_partecipants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image_link` varchar(255) DEFAULT NULL,
  `seats` int(11) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'SEeeeee','Tutto vero','www.nonessite.ciao',15,'2018-12-31 00:00:00'),(2,'Viva l\'italia!!','Tutto vero','www.nonessite.ciao',15,'2018-12-31 00:00:00'),(3,'Forza Juve!','Tutto vero','www.nonessite.ciao',15,'2018-12-31 00:00:00');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `post_date` datetime NOT NULL,
  `content` mediumtext NOT NULL,
  `image_link` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Capelli golosi','2018-12-31 00:00:00','Pasquaaa, è pronto a tavolaaa!','www.mipiacitu.it'),(2,'Libri gratis per due ore','2018-12-31 00:00:00','Pasquaaa, è pronto a tavolaaa!','www.mipiacitu.it'),(3,'Ciro Esposito di gomorra qui!','2018-12-31 00:00:00','Pasquaaa, è pronto a tavolaaa!','www.mipiacitu.it');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ratings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_identifier` varchar(255) NOT NULL,
  `rating` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `on_insert_rating` AFTER INSERT ON `ratings` FOR EACH ROW begin



    update propietary_db.ratings set rating = (rating*number_of_ratings

                                                 + NEW.rating) / (number_of_ratings + 1)

    where book_identifier = new.book_identifier;



    update propietary_db.ratings set number_of_ratings = number_of_ratings + 1

    where book_identifier = new.book_identifier;



  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `waitinglist`
--

DROP TABLE IF EXISTS `waitinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `waitinglist` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `book_identifier` varchar(255) NOT NULL,
  `waiting_position` int(11) NOT NULL,
  `reservation_date` datetime NOT NULL,
  `starting_reservation_date` date NOT NULL,
  `ending_reservation_date` date NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waitinglist`
--

LOCK TABLES `waitinglist` WRITE;
/*!40000 ALTER TABLE `waitinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `waitinglist` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `on_insert_waiting_person` BEFORE INSERT ON `waitinglist` FOR EACH ROW begin

    declare var int;



    set var = count((select * from waitinglist where book_identifier = new.book_identifier));

    set new.waiting_position = var + 1;



    if NEW.waiting_position = 1 then

      update library_1.books set wating_list = true;

    end if;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `update_waitinglist_position` AFTER DELETE ON `waitinglist` FOR EACH ROW begin

    if OLD.waiting_position = 1 then

      update library_2.books set waiting_list = false;

    end if;

    update library_2.waitinglist set waiting_position = waiting_position - 1

    where library_2.waitinglist.book_identifier = OLD.book_identifier;

  end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-26 13:30:52
