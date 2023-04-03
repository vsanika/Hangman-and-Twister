//sanika vaidya sanikav
package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Score {

	private IntegerProperty gameId=new SimpleIntegerProperty();
	private StringProperty puzzleWord=new SimpleStringProperty();
	private IntegerProperty timeStamp=new SimpleIntegerProperty();
	private FloatProperty score=new SimpleFloatProperty();

	Score(int gameId, String puzzleWord,int timeStamp,float score)
	{
		//this.gameId=gameId;
		this.gameId.set(gameId);
		this.puzzleWord.set(puzzleWord);
		this.timeStamp.set(timeStamp);
		this.score.set(score);

	}
	Score()
	{

	}
	public StringProperty puzzleWordProperty() { return puzzleWord; }
	public String getPuzzleWord() {
		return puzzleWord.get();
	}

	public void setPuzzleWord(String puzzleWord) {
		this.puzzleWord.set(puzzleWord);
	}


	public int getTimeStamp() {
		return timeStamp.get();
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp.set(timeStamp);
	}

	public IntegerProperty timeStampProperty() { return timeStamp; }


	public void setGameId(int gameId) {
		this.gameId.set(gameId);
	}

	public int getGameId() {
		return gameId.getValue();
	}

	public IntegerProperty gameIdProperty() { return gameId; }

	public void setScore(float score) {
		this.score.set(score);
	}
	public Number getScore() {
		return score.get();
	}

	public FloatProperty scoreProperty() { return score; }

}
