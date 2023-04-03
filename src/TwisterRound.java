//sanika vaidya sanikav

package hw3;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TwisterRound extends GameRound{


	private ObservableList<String> solutionWordsList= FXCollections.observableArrayList();
	private ObservableList<ObservableList<String>> submittedListsByWordLength=FXCollections.observableArrayList();
	private ObservableList<ObservableList<String>> solutionListsByWordLength=FXCollections.observableArrayList();


	public TwisterRound()
	{	
		for(int i=0;i<=Twister.TWISTER_MAX_WORD_LENGTH-Twister.TWISTER_MIN_WORD_LENGTH;i++)
		{
			submittedListsByWordLength.add(FXCollections.observableArrayList());
			solutionListsByWordLength.add(FXCollections.observableArrayList());
		}

	}

	public List<String> getSolutionWordsList() 
	{

		return this.solutionWordsList;
	}

	public void setSolutionWordsList(List<String> solutionWordsList) 
	{
		this.solutionWordsList = FXCollections.observableList(solutionWordsList);
	}

	public ObservableList<String> solutionWordsListProperty()
	{
		return solutionWordsListProperty();
	}

	////// solutions end

	public ObservableList<ObservableList<String>> getSubmittedListByWordLength() 
	{
		return this.submittedListsByWordLength;
	}

	public ObservableList<String> getSubmittedListsByWordLength(int letterCount) 
	{
		return this.submittedListsByWordLength.get(letterCount);
	}

	public void setSubmittedListsByWordLength(String word) 
	{
		this.submittedListsByWordLength.get(word.length()-Twister.TWISTER_MIN_WORD_LENGTH).add(word);
	}

	public ObservableList<ObservableList<String>> submittedListsByWordLengthProperty()
	{
		return submittedListsByWordLengthProperty();
	}

	////// submitted end

	public ObservableList<ObservableList<String>> getSolutionListsByWordLength() 
	{
		return this.solutionListsByWordLength;
	}

	public ObservableList<String> getSolutionListsByWordLength(int letterCount) 
	{
		return this.solutionListsByWordLength.get(letterCount);
	}

	public void setSolutionListsByWordLength(String word) 
	{
		ObservableList<String> list = this.solutionListsByWordLength.get(word.length()-Twister.TWISTER_MIN_WORD_LENGTH);
		list.add(word);
		this.solutionListsByWordLength.set(word.length()-Twister.TWISTER_MIN_WORD_LENGTH, list);

	}

	public ObservableList<ObservableList<String>> solutionListsByWordLengthProperty()
	{
		return solutionListsByWordLengthProperty();
	}

	////// solutions by word length end

}
