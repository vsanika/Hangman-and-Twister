//sanika vaidya sanikav

package hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Twister {

	public static final int SOLUTION_LIST_COUNT = 5;
	public static final int TWISTER_MAX_WORD_LENGTH = 7;
	public static final int TWISTER_MIN_WORD_LENGTH = 3;
	public static final int NEW_WORD_BUTTON_INDEX = 0;
	public static final int TWIST_BUTTON_INDEX = 1;
	public static final int CLEAR_BUTTON_INDEX = 2;
	public static final int SUBMIT_BUTTON_INDEX = 3;
	public static final int CLUE_BUTTON_SIZE = 75;
	public static final int TWISTER_GAME_TIME = 120;
	public static final int MIN_SOLUTION_WORDCOUNT = 10;

	TwisterRound twisterRound = new TwisterRound();

	public TwisterRound setupRound(){

		List<String> answers= new ArrayList<String>();

		Random r= new Random();
		String returnword="";
		int index=0;
		do			//pick a word to return till it meets the length criteria
		{
			do {
				index=r.nextInt(WordNerdModel.wordsFromFile.length);
				returnword= WordNerdModel.wordsFromFile[index];
			} while((!(WordNerdModel.wordsFromFile[index].length()>=TWISTER_MIN_WORD_LENGTH && WordNerdModel.wordsFromFile[index].length()<=TWISTER_MAX_WORD_LENGTH)));
			answers = checkSubWords(returnword);
		}while(answers.size()<MIN_SOLUTION_WORDCOUNT); //check if list of twister word subsets is more than minimum solution length

		twisterRound.setPuzzleWord(returnword);
		twisterRound.setClueWord(makeAClue(returnword).toString());
		twisterRound.setSolutionWordsList(answers);

		for (int i=0;i<answers.size();i++)
		{
			twisterRound.setSolutionListsByWordLength(answers.get(i));
		}

		return twisterRound;

	}

	public String makeAClue(String puzzleWord){

		List<Character> clueChars=new ArrayList<>(puzzleWord.length());

		for(int i=0;i<puzzleWord.length();i++)
		{
			clueChars.add(puzzleWord.toCharArray()[i]);
		}

		Collections.shuffle(clueChars);

		char[] shuffledChars=new char[puzzleWord.length()];

		for(int i=0;i<puzzleWord.length();i++)
		{
			shuffledChars[i]=clueChars.get(i);
		}

		return String.valueOf(shuffledChars);

	}

	public int nextTry(String guess){

		int displaymessage=0;

		if(guess.length()<TWISTER_MIN_WORD_LENGTH) //if input answer is less than min word length
			displaymessage=GameView.THUMBS_DOWN_INDEX;

		else if(twisterRound.getSubmittedListsByWordLength(guess.length()-TWISTER_MIN_WORD_LENGTH).contains(guess)) //if previous input answer is input again
			displaymessage=GameView.REPEAT_INDEX;	

		else if(twisterRound.getSolutionWordsList().contains(guess)) //if guess is valid and correct
		{
			displaymessage=GameView.THUMBS_UP_INDEX;
			twisterRound.setSubmittedListsByWordLength(guess);

			int submitted=0;

			for(int i=0;i<=Twister.TWISTER_MAX_WORD_LENGTH-Twister.TWISTER_MIN_WORD_LENGTH;i++)
			{
				submitted=submitted+twisterRound.getSubmittedListByWordLength().get(i).size();
			}

			if(twisterRound.getSolutionWordsList().size()==submitted)
				displaymessage=GameView.SMILEY_INDEX;

		}
		else
			displaymessage=GameView.THUMBS_DOWN_INDEX;	

		return displaymessage;

	}

	public String getScoreString(){

		int solutions=twisterRound.getSolutionWordsList().size();
		int submitted=0;

		for(int i=0;i<=Twister.TWISTER_MAX_WORD_LENGTH-Twister.TWISTER_MIN_WORD_LENGTH;i++)
		{
			submitted=submitted+twisterRound.getSubmittedListByWordLength().get(i).size(); //count number of valid submitted answers
		}


		return "Twist to find "+(solutions-submitted)+" of "+solutions;
	}

	public List<String> checkSubWords(String puzzleWord) //find valid subwords in the dictionary of words and return a list of these words
	{
		boolean flag = true;
		List<String> answers= new ArrayList<String>();
		for(int i = 0; i<WordNerdModel.wordsFromFile.length; i++){
			String word = WordNerdModel.wordsFromFile[i];
			if((word.length()>=TWISTER_MIN_WORD_LENGTH) && (word.length()<=puzzleWord.length())){
				String check_puzzleWord = puzzleWord;
				flag = true;
				char[] charArrayCheckWord = word.toCharArray();
				for(int j=0; j<charArrayCheckWord.length;j++) {
					if(check_puzzleWord.contains(String.valueOf(charArrayCheckWord[j]))){
						check_puzzleWord = check_puzzleWord.replaceFirst(String.valueOf(charArrayCheckWord[j]), "_");
					}else{
						flag = false;
					}
				}
				if(flag)
					answers.add(word);
			}
		}
		return answers;
	}
}




