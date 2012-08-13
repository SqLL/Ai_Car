want(Request).
get(Car).

!gui.

/* Plans */

/* Partie interface avec le client */
+!gui : true <- makeArtifact("req","customer.Contrainte",[],K);
	makeArtifact("gui","customer.ContrainteGUI",[],B);
	focus(B).


/* Partie principale lancement de la requete et attente d'informations */
+launch(Req)<- .print("START");
	!setupTool(Id);
	.print("I'm looking for a seller i do my request");
	-+want(Req);
	setRequest(Req);
	.print("Request ", R);
	waitingInformations[artifact_id(Id)].


+!setupTool(C): true
  <- makeArtifact("c0","communication.Dialog",[],C);
  focus(C).


// Here it's the reflexion from the customer about the contract NOT IMPLEMENTED YET
+!reflexion : true
	<- .print("[CUSTOMER] Reflexion");
		!quickChoice.
	

// Sign the first contract with the first car available
+!quickChoice : true
	<- .print("[CUSTOMER] I'm a busy client i choice the first car available");
		quickChoice[artifact_name(Id,"c0")];
		.print("[CUSTOMER] I Ask To Key and the car");
		.send(seller,tell,requestCar).



/*Decisions a prendre en fonction de la reponse */
+answerAvailable(true) [artifact_name(Id,"c0")]
	<- .print("I know than some car are available for me");
		focus(Id);
		display[artifact_id(Id)];
		!reflexion.
		
		
+answerAvailable(false) [artifact_name(Id,"c0")]
	<- .print("no car are available for me").


+!useCar(Car,Key) [source(seller)]
	<- .print("[CUSTOMER] Let's Drive");
	drive(Key)[artifact_name(Car,"car")].

	