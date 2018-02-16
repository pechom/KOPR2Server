KOPR 2. projekt prvé zadanie.
Celý projekt predstavuje repozitár KOPR2Server a KOPR2Client
Používa sa MySQL databáza, vytvoriť ju je možné s sql súborom v KOPR2Server/src/main/resources/.
DataSource na zmenu prístupu k databáze sa nachádza v ObjectFactory na serveri a v triede AJSServiceTest na klientovi.
Oba projekty používajú JavaSE-1.8 (jdk1.8.0_91).
Oba projekty využívaju Maven, vytvorené sú v Eclipse 4.7.2.
Testy sa v oboch projektoch nachádzajú v priečinku src/test/java. 
Pri testovaní klienta treba najprv spustiť na serveri v src/main/java triedu Server. 
