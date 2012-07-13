package contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contract {

	String titre="Respect Project";
	/**
	 * 
	 * @param target Fichier contrat a modifier
	 * @throws IOException Exception principalement sur l'ouverture du fichier
	 */
	public void Parse(File target,File dst) throws IOException
	{
		PrintWriter ecri = null ;
		BufferedReader lecteurAvecBuffer = null;
		String ligne;

		
		
		// Compile regular expression
		String patternStr = ".*newcommand.*";//{\titre}{titre}";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher =pattern.matcher("r");
		
		try
	      {
		lecteurAvecBuffer = new BufferedReader(new FileReader(target));
		ecri = new PrintWriter(new FileWriter(dst));
	      }
	    catch(FileNotFoundException exc)
	      {
		System.out.println("Erreur d'ouverture");
	      }
	    while ((ligne = lecteurAvecBuffer.readLine()) != null)
	    {
	    	System.out.println(ligne);
	    	 matcher.reset(ligne);
	    	 if(matcher.matches())
	    	 {
	    		 System.out.println("Suceed");
	    		 ligne=Replace(ligne);
	    		 System.out.println("ligne="+ligne);
	    	 }
	      ecri.print(ligne+System.getProperty("line.separator"));
	    }
	    lecteurAvecBuffer.close();
		ecri.flush();
		ecri.close();
	  }

	/**
	 * this function for example will extract the substring between the first { }
	 * to identify the string and to replace by the value into the last { }
	 * 
	 * @param ligne is the string who need to be modify to personalize the contract
	 * @return the new string 
	 */
	protected String Replace(String ligne)
	{
		//Here we have to extract the information about the line
		StringBuffer toBeAnalyzed=new StringBuffer(ligne);
		String idRepair=toBeAnalyzed.substring(toBeAnalyzed.indexOf("{")+2,toBeAnalyzed.indexOf("}"));
		String content=new String();
		System.out.println(idRepair);
		
		/*
		 * Here is the if with all possibilities
		 */
		if(idRepair.equals("titre"))
		{
			System.out.println("Ceci est un titre");
			content=titre;
		}
		
		/*
		 * replace the string between the second { }
		 */
		toBeAnalyzed.replace(toBeAnalyzed.lastIndexOf("{")+1,toBeAnalyzed.lastIndexOf("}"),content);
		
		return toBeAnalyzed.toString();
	}
	
	/**
	 * Determine the compilation line from your os
	 * 
	 * @param target is the name of the new files
	 */
	public void Execute(File target){	
		
		String latexCmd=new String();
		/*
		 * To determine the correct line to compile the file i need to know with operating system the program is launch
		 */
		if(System.getProperty("os.name").matches(".*Mac.*")){
			latexCmd="/usr/texbin/pdflatex "+target.getName();
		}
		
		
		File base= new File(target.getParent());
		String cmd[]={"bash","-c",latexCmd};

		try {
			Process proc = Runtime.getRuntime().exec(cmd, null,base);
			InputStream in = proc.getInputStream();

			StringBuilder build = new StringBuilder();
			char c = (char) in.read();

			while (c != (char) -1) {
				build.append(c);
				c = (char) in.read();
			}

			String response = build.toString();

			//on l'affiche
			System.out.println(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
	}
}
