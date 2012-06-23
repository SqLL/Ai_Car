// Agent seller in project AI_Car

/* Initial beliefs and rules */

mycompany(E).
myclient(C).
myconfig(CONF).

/* Initial goals */

!gui.

/* Plans */


+!gui : true <- makeArtifact("config","seller.Administration",[],K);
	makeArtifact("configgui","seller.AdministrationGUI",[],B);
	focus(B).


+waiting_client(CONF)
	<- -+myconfig(CONF); 
	  ?myClient(C);
		-+myclient(C);
		.print("There is client i need informations on Company");
		!create(T,CONF);
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

+!create(C,CONF): true
	<- makeArtifact("c1","seller.Company",[CONF],C);
	   setConfig(CONF)[artifact_id(C)].

	   //addPointOfSale()[artifact_id(C)].
	
	
/* Here you need to propose all car available to the customer */
+decisionAvailable(true) : true
	<- .print("Company got car available");
		?myclient(C);
		?mycompany(T);
		getRequest(Request)[artifact_id(C)];
		getCarAvailable(Request,List)[artifact_id(T)];
		setCarAvailable(List) [artifact_id(C)];
		setAnswer(true).

+decisionAvailable(false) : true
	<- .print("Company have'nt car available"). 
	