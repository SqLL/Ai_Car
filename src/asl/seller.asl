// Agent seller in project AI_Car

/* Initial beliefs and rules */

mycompany(E).
myclient(C).
myconfig(CONF).
mycontract(P).

/* Initial goals */

!gui.

/* Plans */

/* Interface ou on affiche la fenetre*/
+!gui : true <- makeArtifact("config","seller.Administration",[],K);
	makeArtifact("configgui","seller.AdministrationGUI",[],B);
	focus(B).



//fonction principale ou on attend un signal du dialog artifact
+waiting_client(CONF)
	<- -+myconfig(CONF); 
	  ?myClient(C);
		-+myclient(C);
		.print("There is client i need informations on Company");
		focus(C);
		getRequest(Req)[artifact_id(C)];
		!create(T,CONF,Req);
		-+mycompany(T);
		focus(T);
		.print("There is some informations about Company");
		displayContent;
		carAvailable.
		

+?myClient(DialogId): true
  <- lookupArtifact("c0",DialogId).

-?myClient(DialogId): true
  <- .wait(10);
     ?myClient(DialogId).

+!create(C,CONF,Req): true
	<- makeArtifact("c1","seller.Company",[CONF,Req],C).
	  // setConfig(CONF)[artifact_id(C)]. // Mise en place de la configuration
	
	
/* Here you need to propose all car available to the customer */
+decisionAvailable(true) : true
	<- .print("[SELLER] Company got car available");
		?myclient(C);
		?mycompany(T);
		getRequest(Request)[artifact_id(C)];
		getCarAvailable(Request,List)[artifact_id(T)]; //Here we can make contract
		getCompany(Company)[artifact_id(T)];//Here can be optimized
		makeArtifact("contract","contract.Contract",[Request,Company,List],P);
		-+mycontract(P);
		makeContract[artifact_id(P)];
		setCarAvailable(List) [artifact_id(C)];
		setAnswer(true).

+decisionAvailable(false) : true
	<- .print("[SELLER] Company have'nt car available"). 
	
	
+requestCar[source(customer)]
    <-  getCarChoose(ObjectCar)[artifact_name(Car,"car")];
    	makeArtifact("car","seller.Car",[ObjectCar],CAR);
		setCarChoosen(ObjectCar)[artifact_id(P)];
    	changeKey(Key)[artifact_name(Car,"car")];
    	.print("[SELLER] Generate Key and Make Artifact Car for customer");
    	.send(customer,achieve,useCar(Car,Key)).

+!giveBack(Car)[source(customer)] : true
	<- .print("[SELLER] Now we can end the contract");
		calculPrice(Price)[artifact_name(Contract,"contract")];
		.print("[SELLER] Price of the rent is ",Price).
	

	