//sanika vaidya sanikav 

package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WordNerdModel {

	public static final String WORDS_FILE_NAME = "data\\wordsFile.txt";  
	public static final String SCORE_FILE_NAME ="data\\scores.csv";
	public static String[] wordsFromFile;	
	ObservableList<Score> scoreList=FXCollections.observableArrayList();
	ObservableList<Score> scoreListH=FXCollections.observableArrayList();
	ObservableList<Score> scoreListT=FXCollections.observableArrayList();

	public static void readWordsFile(String filename) throws InvalidWordSourceException{
		//write your code here
		//read file data
		String temp="";
		int count=0;
		StringBuilder sb=new StringBuilder();
		try {
			Scanner s = new Scanner(new File(filename));
			count++;
			if(count==1 && !s.hasNextLine()) throw new InvalidWordSourceException("Check word source format!"); //check if file is empty
			else {
				while(s.hasNextLine())
				{
					temp=s.nextLine();
					//if(temp.matches("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")||temp.matches("[0-9]"))
					if(temp.matches("^[a-zA-Z]*$")) //check if file has only alphabet strings
					{
						sb.append(temp+"\n");	
					} else {
						throw new InvalidWordSourceException("Check word source format!");
					}

				}
			}
			wordsFromFile=sb.toString().split("\n");
			s.close();

		} 

		catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

	public void writeScore(String scoreString) 
	{
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(SCORE_FILE_NAME, true));)//wtite score to csv
		{
			String[] temp=scoreString.split(",");
			bw.write(String.format("%s,%s,%s,%s%n", temp[0], temp[1], temp[2], temp[3]));
			bw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

	public void readScore()
	{	
		scoreList=FXCollections.observableArrayList();
		
		try(BufferedReader br = new BufferedReader(new FileReader(SCORE_FILE_NAME));)//read score from csv
		{
			String line="";

			while((line=br.readLine())!=null)
			{
				String[] temp=line.split(",");
				Score scr=new Score(Integer.parseInt(temp[0].trim()),temp[1].trim(),Integer.parseInt(temp[2].trim()),Float.parseFloat(temp[3].trim()));
				scoreList.add(scr);
				if(scr.getGameId()==WordNerd.HANGMAN_ID)
					scoreListH.add(scr);
				else if(scr.getGameId()==WordNerd.TWISTER_ID)
					scoreListT.add(scr);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}


