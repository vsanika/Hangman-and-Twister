//sanika vaidya sanikav
package hw3;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SearchController extends WordNerdController {

	//this is a placeholder class. Will be completed in HW3
	ComboBox<String> gameComboBox = new ComboBox<>();
	ObservableList<String> options = FXCollections.observableArrayList("All games", "Hangman", "Twister");
	SearchView srv= new SearchView();
	@Override
	void startController() {
		//to be implemented in HW3

		wordNerdModel.readScore();

		srv.gameComboBox.setItems(options);
		srv.gameComboBox.setValue("All games");
		srv.searchTableView.setItems(wordNerdModel.scoreList);

		srv.setupView();

		setupBindings();


	}

	@Override
	void setupBindings() {
		//to be implemented in HW3

		srv.gameComboBox.valueProperty().addListener((x,y,z) -> {
			if(srv.gameComboBox.getSelectionModel().getSelectedIndex()==1)
			{
				srv.searchTableView.setItems(wordNerdModel.scoreListH);

			}
			else if(srv.gameComboBox.getSelectionModel().getSelectedIndex()==2)
				srv.searchTableView.setItems(wordNerdModel.scoreListT);
			else
				srv.searchTableView.setItems(wordNerdModel.scoreList);


		});

		srv.searchTextField.textProperty().addListener((x,y,z)->{
			ObservableList<Score> filter=searching(wordNerdModel.scoreList,z);
			srv.searchTableView.setItems(filter);
		});



	}
	public ObservableList<Score> searching(List<Score> score,String z)
	{
		List<Score> results=new ArrayList<>();

		for(Score sc:score)
		{
			if(search(sc,z)==1) {
				results.add(sc);
			}
		}
		return FXCollections.observableList(results);

	}

	public int search(Score sc, String z)
	{
		String[] searchString=z.split("");
		List<String> words=new ArrayList<String>();

		for(String st:searchString)
		{
			if(sc.getPuzzleWord().contains(st))
			{
				words.add(st);
			}
		}

		if(words.size()==searchString.length) return 1;
		else return 0;
	}


}


