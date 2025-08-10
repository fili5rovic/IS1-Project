DROP TABLE IF EXISTS audio_kategorija;

CREATE TABLE audio_kategorija (
  id_audio int NOT NULL,
  id_kategorija int NOT NULL,
  PRIMARY KEY (id_audio,id_kategorija),
  KEY kategorija_idx (id_kategorija),
  CONSTRAINT audio FOREIGN KEY (id_audio) REFERENCES audio_snimak (ida) ON DELETE CASCADE,
  CONSTRAINT kategorija FOREIGN KEY (id_kategorija) REFERENCES kategorija (idk) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO audio_kategorija VALUES (1,1);
INSERT INTO audio_kategorija VALUES (2,1);
INSERT INTO audio_kategorija VALUES (2,2);
INSERT INTO audio_kategorija VALUES (2,3);
INSERT INTO audio_kategorija VALUES (3,3);

DROP TABLE IF EXISTS audio_snimak;

CREATE TABLE audio_snimak (
  ida int NOT NULL AUTO_INCREMENT,
  naziv varchar(45) NOT NULL,
  trajanje int NOT NULL,
  vlasnik_idk int NOT NULL,
  datum datetime NOT NULL,
  PRIMARY KEY (ida),
  KEY id_vlasnik_idx (vlasnik_idk),
  CONSTRAINT id_vlasnik FOREIGN KEY (vlasnik_idk) REFERENCES korisnik (idk) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO audio_snimak VALUES (1,'glasovna1',39,1,'2024-12-12 00:00:00');
INSERT INTO audio_snimak VALUES (2,'Ekonomija drustva',632,3,'2022-06-22 00:00:00');
INSERT INTO audio_snimak VALUES (3,'ETF',209,2,'2025-02-10 00:00:00');

DROP TABLE IF EXISTS kategorija;

CREATE TABLE kategorija (
  idk int NOT NULL AUTO_INCREMENT,
  naziv varchar(45) NOT NULL,
  PRIMARY KEY (idk)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO kategorija VALUES (1,'Glasovna poruka');
INSERT INTO kategorija VALUES (2,'Podcast');
INSERT INTO kategorija VALUES (3,'Dokumentarac');
INSERT INTO kategorija VALUES (4,'Pesma');

DROP TABLE IF EXISTS korisnik;

CREATE TABLE korisnik (
  idk int NOT NULL AUTO_INCREMENT,
  ime varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  godiste date NOT NULL,
  pol enum('musko','zensko','nebinarno','drugo') NOT NULL,
  id_mesto int NOT NULL,
  PRIMARY KEY (idk),
  KEY korisnik_mesto_idx (id_mesto),
  CONSTRAINT korisnik_mesto FOREIGN KEY (id_mesto) REFERENCES mesto (idm) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO korisnik VALUES (1,'Filip','filip@gmail.com','2003-09-03','musko',1);
INSERT INTO korisnik VALUES (2,'Jovan','jovan@gmail.com','2002-07-15','musko',1);
INSERT INTO korisnik VALUES (3,'Lazar','lazar@gmail','2003-07-31','musko',1);
INSERT INTO korisnik VALUES (4,'Milica','milica@gmail.com','1999-11-29','zensko',2);
INSERT INTO korisnik VALUES (5,'Vanja','vanja@gmail.com','2005-06-16','zensko',3);
INSERT INTO korisnik VALUES (17,'test','test@gmail.com','2000-09-03','musko',1);

DROP TABLE IF EXISTS mesto;

CREATE TABLE mesto (
  idm int NOT NULL AUTO_INCREMENT,
  naziv varchar(45) NOT NULL,
  PRIMARY KEY (idm)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO mesto VALUES (1,'Beograd');
INSERT INTO mesto VALUES (2,'Novi Sad');
INSERT INTO mesto VALUES (3,'Nis');

DROP TABLE IF EXISTS ocena;

CREATE TABLE ocena (
  id_korisnik int NOT NULL,
  id_audio int NOT NULL,
  ocena int NOT NULL,
  datum datetime NOT NULL,
  PRIMARY KEY (id_korisnik,id_audio),
  KEY ocena_audio_idx (id_audio),
  CONSTRAINT ocena_audio FOREIGN KEY (id_audio) REFERENCES audio_snimak (ida) ON DELETE CASCADE,
  CONSTRAINT ocena_korisnik FOREIGN KEY (id_korisnik) REFERENCES korisnik (idk) ON DELETE CASCADE,
  CONSTRAINT ocena_chk_1 CHECK ((`ocena` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO ocena VALUES (1,1,5,'2024-01-03 13:30:08');
INSERT INTO ocena VALUES (2,2,3,'2024-01-02 09:23:47');
INSERT INTO ocena VALUES (3,3,2,'2023-07-07 11:01:59');

DROP TABLE IF EXISTS omiljeni;

CREATE TABLE omiljeni (
  id_korisnika int NOT NULL,
  id_audio int NOT NULL,
  PRIMARY KEY (id_korisnika,id_audio),
  KEY omiljeni_audio_idx (id_audio),
  CONSTRAINT omiljeni_audio FOREIGN KEY (id_audio) REFERENCES audio_snimak (ida) ON DELETE CASCADE,
  CONSTRAINT omiljeni_korisnik FOREIGN KEY (id_korisnika) REFERENCES korisnik (idk) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO omiljeni VALUES (1,1);
INSERT INTO omiljeni VALUES (2,2);

DROP TABLE IF EXISTS paket;

CREATE TABLE paket (
  idp int NOT NULL AUTO_INCREMENT,
  cena decimal(10,2) NOT NULL,
  PRIMARY KEY (idp)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO paket VALUES (1,1000.00);
INSERT INTO paket VALUES (2,1500.00);
INSERT INTO paket VALUES (3,2000.00);
INSERT INTO paket VALUES (5,15.00);

DROP TABLE IF EXISTS pretplata;

CREATE TABLE pretplata (
  idp int NOT NULL AUTO_INCREMENT,
  datum_od datetime NOT NULL,
  idk int NOT NULL,
  cena decimal(10,2) NOT NULL,
  id_paket int NOT NULL,
  PRIMARY KEY (idp),
  UNIQUE KEY idk_UNIQUE (idk),
  KEY pretplata_paket_idx (id_paket),
  CONSTRAINT idk FOREIGN KEY (idk) REFERENCES korisnik (idk) ON DELETE CASCADE,
  CONSTRAINT pretplata_paket FOREIGN KEY (id_paket) REFERENCES paket (idp) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO pretplata VALUES (1,'2024-07-15 00:00:00',1,1000.00,1);
INSERT INTO pretplata VALUES (2,'2024-06-06 00:00:00',2,1500.00,2);
INSERT INTO pretplata VALUES (3,'2024-07-16 00:00:00',3,2000.00,3);
INSERT INTO pretplata VALUES (4,'2024-08-07 00:00:00',4,2000.00,3);
INSERT INTO pretplata VALUES (15,'2024-07-15 00:00:00',5,1000.00,1);


DROP TABLE IF EXISTS slusanje;

CREATE TABLE slusanje (
  id_korisnik int NOT NULL,
  id_audio int NOT NULL,
  datum_slusanja_od datetime NOT NULL,
  sekunda_od int NOT NULL,
  sekunda_odslusano int NOT NULL,
  PRIMARY KEY (id_korisnik,id_audio),
  KEY slusanje_audio_idx (id_audio),
  CONSTRAINT slusanje_audio FOREIGN KEY (id_audio) REFERENCES audio_snimak (ida),
  CONSTRAINT slusanje_korisnik FOREIGN KEY (id_korisnik) REFERENCES korisnik (idk) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO slusanje VALUES (1,1,'2025-01-03 00:00:00',0,30);
INSERT INTO slusanje VALUES (2,2,'2025-01-04 00:00:00',0,3);