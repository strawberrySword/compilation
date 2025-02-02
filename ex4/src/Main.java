   
import java.io.*;
import java.util.HashSet;

import java_cup.runtime.Symbol;
import AST.*;
import IR.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_DEC_LIST AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l, file_writer);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_DEC_LIST) p.parse().value;
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();

			/**********************/
			/* [8] IR the AST ... */
			/**********************/
			AST.IRme();
			
			/*************************/
			/* [9] CFG the me        */
			/*************************/
			Graph<IRcommand> cfg = IR.getInstance().CFGme();
			System.out.println("IR: ");
			/*************************/
			/* [10] Chaotic Iterate  */
			/*************************/
			
			IR.ChaoticIterate(cfg); // This performs chaotic iterations
			
			IRcommand curr = IR.getInstance().head;
			IRcommandList next = IR.getInstance().tail;

			while(curr != null){
				System.out.print(curr.toString());
				curr = next != null ? next.head : null;
				next = next != null ? next.tail : null;
			}
			/*******************************/
			/* [11] Perform goofy analysis */
			/*******************************/
			
			boolean IsTheCodeValidAccordingToExcersiceNuberFour = IR.CheckUsedBeforeAssigned();
			HashSet<String> unset = IR.getUsedBeforeAssignedVars();
			// System.out.println("\n###"+IsTheCodeValidAccordingToExcersiceNuberFour+"###\n");
			System.out.println(unset);
			/**************************/
			/* [12] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


