// Agent seller in project AI_Car

/* Initial beliefs and rules */

mycompany(E).
myclient(C).
myconfig(CONF).
mycontract(P).
myjustice(J).
mycar(CAR).

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
		!updateDialog(true);
		makeArtifact("justice","justice.Justice",[],J);
		-+myjustice(J);
		setAnswer(true).
		
+!updateDialog(true)
	<-	getContractsList(ContractsList)[artifact_id(P)];
		getCarPrices(CarPrices)[artifact_id(P)];
		setlPrices(CarPrices)[artifact_id(C)];
		setlFiles(ContractsList)[artifact_id(C)].

+decisionAvailable(false) : true
	<- .print("[SELLER] Company have'nt car available"). 
	
	
+requestCar[source(customer)]
    <-  getCarChoose(ObjectCar)[artifact_name(Id,"c0")];
    	?mycontract(P);
    	?myjustice(J);
    	getLocationDepart(Location);
    	setCarChoosen(ObjectCar)[artifact_id(P)];
    	makeArtifact("car","seller.Car",[ObjectCar,Location],IdCar);
    	-+mycar(IdCar);
    	getPolicy(ContractPolicy)[artifact_id(P)];
    	setPolicy(ContractPolicy)[artifact_id(J)];
    	setCar(ObjectCar)[artifact_id(J)];

		.send(customer,achieve,proposalContract(true)).
	
//Sign of the contract with the client
+!signContract(true)[source(customer)]
		<-calculPrice(Price)[artifact_id(P)];
		.send(customer,achieve,firstPayment(Price)).//first payement
		
//Update of list of action and then apply Checkpoint A and give the car
+!getCar(true)[source(customer)]
		<- !update(true);
		checkPoint[artifact_id(J)];
		getMasterKey(MKey);
		?mycar(IdCar);
		focus(IdCar);
    	changeKey(Key,MKey)[artifact_name(Car,"car")];
    	.print("[SELLER] Generate Key and Make Artifact Car for customer");
    //	.send(customer,achieve,lookupArtifact(Car));
    	.send(customer,achieve,useCar(Car,Key,Car)).


+!giveBack(Car)[source(customer)] : true
	<- .print("[SELLER] Now we can end the contract");
	checkPoint[artifact_id(J)].


+!update(true)[source(self)] : true
	<- getlActionCustomer(LActionCustomer)[artifact_name(Id,"c0")];
		getlActionSeller(LActionSeller)[artifact_name(Id,"c0")];
		setlActionCustomer(LActionCustomer)[artifact_id(J)];
		setlActionSeller(LActionSeller)[artifact_id(J)].


+damage(true) 
	<- .print("[SELLER] i know that There is some damages on car").
	
+malfunction(true) 
	<- .print("[SELLER] i know that There is some malfunction on car").	
	