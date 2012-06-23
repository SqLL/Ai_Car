want(Request).


!gui.

/* Plans */


+!gui : true <- makeArtifact("req","customer.Contrainte",[],K);
	makeArtifact("gui","customer.ContrainteGUI",[],B);
	focus(B).

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


+answerAvailable(true) [artifact_name(Id,"c0")]
	<- .print("I know than some car are available for me");
		focus(Id);
		display[artifact_id(Id)].
		
+answerAvailable(false) [artifact_name(Id,"c0")]
	<- .print("no car are available for me").




