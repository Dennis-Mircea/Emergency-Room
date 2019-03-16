# Emergency-Room


	Am creat urmatoarele pachete:
	- emergency - contine fisiere date in schelet plus clasa EmergencyRoom,
	care e clasa de baza in rularea simularii, continand toate campurile date
	in input(investigators, nurses, doctors, patients);
	- enitities - contine entitatile de doctor si pacienti cu toate campurile
	necesare;
	- inputRead - contine fisierele cu ajutorul carora se realizeaza citirea
	de la input;
	- outputWrite - contine clasa PrintRound cu ajutorul careia se realizeaza
	scrierea la output a rezultatelor dorite;
	- queue - contine clasele care reprezinta toate cozile din intreaga
	simulare;
	
	Logica algoritmului:
		Dupa ce se realizeaza citirea de la input fiecarui pacient i se
	atribuie o lista de doctori care se pot ocupa de afectiunea acestuia. Apoi
	in functie de runda in care trebuie sa intre fiecare pacient, pacientii vor
	fi adaugati mai intai in TriageQueue, ExaminationQueue, iar din examination
	in InvestigationQueue sau in Hospital(in cazul in care pacientul trebuie
	spitalizat sau operat).
		In acest algoritm ma bazez pe cele 4 cozi:
			-> pacientii din EmergencyRoom care urmeaza sa intre in rundele
			 urmatoare;
			-> pacientii din Triage care vor intra in runda curenta si care
			vor ramane sau nu in aceasta coada in cazul in care numar de
			asistente este mai mic decat numarul de pacienti;
			-> pacientii din Examination care vor veni din Triage, vor fi
			verificati pe rand de cate un doctor de la caz la caz;
			-> pacientii din Investigation care vor veni din Examination
			si care se vor intoarce in apoi dupa ce li se atribuie un
			rezultat, fie toti sau nu in functie de numarul de investigatori;
		Acest algoritm folosind aceste 4 cozi roteste in mod continuu doctorii,
	mai bine zis listele de doctori ale fiecarui pacient, atunci cand un doctor
	verifica un pacient, acesta este pus la finalul tuturor cozilor de doctori
	ai tuturor pacientilor.
	
	PRINTARE: ---
		In fiecare runda se vor printa doctorii care au ramas in triaj,
	examinare sau investigare, respectiv toti pacientii spitalizati(care se
	afla in spital avand InnestigationResult Operate sau Hospitalized), cat si
	toate asistentele care au grija de pacienti, si doctorii care isi verifica
	mereu pacientii spitalizati.

	CLASA Hospital
		Clasa Hospital retine de la inceput toti doctorii din EmergencyRoom,
	iar cand un pacient este spitalizat, daca acest doctor de afla in lista
	pacientului de doctori care ii pot vindeca boala, acest pacient i se va
	atribui doctorului, doctor care il va retine intr-o lista de pacienti,
	si il va lasa sa plece acasa(il va scoate din coada) doar atunci cand
	severitatea in va scadea sub 0 inclusiv sau rundele sub care trebuie sa
	stea sub tratament au ajuns la 0.
