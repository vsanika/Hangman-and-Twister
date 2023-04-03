//sanika vaidya sanikav
package hw3;

import java.util.Random;

public class Hangman extends Game{
	static final int MIN_WORD_LENGTH = 5; //minimum length of puzzle word
	static final int MAX_WORD_LENGTH = 10; //maximum length of puzzle word
	static final int HANGMAN_TRIALS = 10;  // max number of trials in a game
	static final int HANGMAN_GAME_TIME = 30; // max time in seconds for one round of game

	HangmanRound hangmanRound;

	/** setupRound() is a replacement of findPuzzleWord() in HW1. 
	 * It returns a new HangmanRound instance with puzzleWord initialized randomly drawn from wordsFromFile.
	 * The puzzleWord must be a word between HANGMAN_MIN_WORD_LENGTH and HANGMAN_MAX_WORD_LEGTH. 
	 * Other properties of Hangmanround are also initialized here. 
	 */
	@Override
	HangmanRound setupRound() {
		//write your code here
		hangmanRound= new HangmanRound();
		Random r= new Random();
		String returnword="";
		int index=0;
		do			//pick a word to return till it meets the length criteria
		{
			index=r.nextInt(WordNerdModel.wordsFromFile.length);
			returnword= WordNerdModel.wordsFromFile[index];

		}while(!(WordNerdModel.wordsFromFile[index].length()>=MIN_WORD_LENGTH && WordNerdModel.wordsFromFile[index].length()<=MAX_WORD_LENGTH));
		hangmanRound.setPuzzleWord(returnword);	//set our word to the returnword
		hangmanRound.setClueWord(makeAClue(returnword)); //make a clue from our returnword
		getScoreString();
		return hangmanRound;
		//
	}


	/** Returns a clue that has at least half the number of letters in puzzleWord replaced with dashes.
	 * The replacement should stop as soon as number of dashes equals or exceeds 50% of total word length. 
	 * Note that repeating letters will need to be replaced together.
	 * For example, in 'apple', if replacing p, then both 'p's need to be replaced to make it a--le */
	@Override
	String makeAClue(String puzzleWord) {
		//write your code here
		Random r=new Random();
		int count=0;
		while(count<(puzzleWord.length()-1)/2)
		{
			char c=puzzleWord.charAt(r.nextInt(puzzleWord.length()));
			puzzleWord=puzzleWord.replace(c, '_');	//to replace all instances of the character
			String temp = puzzleWord.replace("_", "");	//temp String to find length of the word without the dashes
			count=puzzleWord.length()-temp.length();	//taking the count of dashes as total length of the string minus the length of the string without the dashes			
		}
		countDashes(puzzleWord);

		return puzzleWord;
	}

	/** countDashes() returns the number of dashes in a clue String */ 
	int countDashes(String word) {
		//write your code here
		int count=0;
		for(int i=0;i<word.length();i++)
		{
			if(word.charAt(i)=='_')
				count++;
		}
		return count;
	}

	/** getScoreString() returns a formatted String with calculated score to be displayed after
	 * each trial in Hangman. See the handout and the video clips for specific format of the string. */
	@Override
	String getScoreString() {
		//write your code here
		float score=0.0f;
		if(hangmanRound.getMissCount()==0)
		{
			score=hangmanRound.getHitCount();
		}
		else 
			score=(float)hangmanRound.getHitCount()/hangmanRound.getMissCount();
		String output="Hit: "+hangmanRound.getHitCount()+" Miss: "+hangmanRound.getMissCount()+". Score: "+String.format("%.2f",score);
		return  output;
	}

	/** nextTry() takes next guess and updates hitCount, missCount, and clueWord in hangmanRound. 
	 * Returns INDEX for one of the images defined in GameView (SMILEY_INDEX, THUMBS_UP_INDEX...etc. 
	 * The key change from HW1 is that because the keyboardButtons will be disabled after the player clicks on them, 
	 * there is no need to track the previous guesses made in userInputs*/
	@Override
	int nextTry(String guess) {  
		//write your code here
		char guessc=guess.charAt(0);
		int displaymessage=0;
		String clue=hangmanRound.getClueWord();
		char[] cluearray=clue.toCharArray();		//convert the clue word to char array to update dashes if right letter is input
		int repeat=0;

		if(hangmanRound.getPuzzleWord().contains(String.valueOf(guessc))) //if the input character is a correct guess
		{
			displaymessage=GameView.THUMBS_UP_INDEX;
		}
		else if(!hangmanRound.getPuzzleWord().contains(String.valueOf(guessc))) //if the input character is a wrong guess
		{
			displaymessage=GameView.THUMBS_DOWN_INDEX;
			hangmanRound.setMissCount(hangmanRound.getMissCount()+ 1);
		}

		if(displaymessage==GameView.THUMBS_UP_INDEX)	//if the input character is correct, then update the clue word with the character
		{
			for(int i=0;i<hangmanRound.getPuzzleWord().length();i++)
			{

				if(hangmanRound.getPuzzleWord().charAt(i)==guessc)
				{
					cluearray[i]=guessc;
					repeat++;	
					if(repeat==1) {		//to avoid multiple hits for the same repeating character
						hangmanRound.setHitCount(hangmanRound.getHitCount()+ 1);	
					}
				}
			}
		}

		String updatedClue=String.valueOf(cluearray);		//update the clue word
		hangmanRound.setClueWord(updatedClue);				//set clue word

		if(hangmanRound.getClueWord().equals(hangmanRound.getPuzzleWord())) //win or loss conditions
		{
			displaymessage=GameView.SMILEY_INDEX;
		}
		else if(!(hangmanRound.getClueWord().equals(hangmanRound.getPuzzleWord()))&& hangmanRound.getMissCount()+hangmanRound.getHitCount()==HANGMAN_TRIALS)
		{
			displaymessage=GameView.SADLY_INDEX;
		}

		return displaymessage;

	}
}
