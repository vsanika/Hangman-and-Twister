//sanika vaidya sanikav
package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SearchView{

	ComboBox<String> gameComboBox = new ComboBox<>(); //shows drop down for filtering the tableView data
	TextField searchTextField = new TextField();  //for entering search letters
	TableView<Score> searchTableView = new TableView<>();  //displays data from scores.csv
	Callback<CellDataFeatures<Score,String>, ObservableValue<String>> gameNameCallback;
	Callback<CellDataFeatures<Score,String>, ObservableValue<String>> scoreCallback;

	/**setupView() sets up the GUI components
	 * for Search functionality
	 */
	void setupView() {

		VBox searchVBox = new VBox();  //searchVBox contains searchLabel and searchHBox
		Text searchLabel = new Text("Search");
		searchVBox.getChildren().add(searchLabel);

		HBox searchHBox = new HBox();  //searchHBox contain gameComboBox and searchTextField
		searchHBox.getChildren().add(gameComboBox);
		searchHBox.getChildren().add(new Text("Search letters"));
		searchHBox.getChildren().add(searchTextField);
		searchVBox.getChildren().add(searchHBox);

		searchLabel.setStyle( "-fx-font: 30px Tahoma;" + 
				" -fx-fill: linear-gradient(from 0% 50% to 50% 100%, repeat, lightgreen 0%, lightblue 50%);" +
				" -fx-stroke: gray;" +
				" -fx-background-color: gray;" +
				" -fx-stroke-width: 1;") ;
		searchHBox.setPrefSize(WordNerd.GAME_SCENE_WIDTH, WordNerd.GAME_SCENE_HEIGHT / 3);
		gameComboBox.setPrefWidth(200);
		searchTextField.setPrefWidth(300);
		searchHBox.setAlignment(Pos.CENTER);
		searchVBox.setAlignment(Pos.CENTER);
		searchHBox.setSpacing(10);

		setupSearchTableView();

		WordNerd.root.setPadding(new Insets(10, 10, 10, 10));
		WordNerd.root.setTop(searchVBox);
		WordNerd.root.setCenter(searchTableView);
		WordNerd.root.setBottom(WordNerd.exitButton);
	}

	@SuppressWarnings("unchecked")
	void setupSearchTableView() {
		//write your code here
		gameNameCallback=new Callback<CellDataFeatures<Score,String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Score, String> arg0) {
				// TODO Auto-generated method stub
				if(arg0.getValue().gameIdProperty().get()==WordNerd.HANGMAN_ID)
					return new SimpleStringProperty("Hangman");
				if(arg0.getValue().gameIdProperty().get()==WordNerd.TWISTER_ID)
					return new SimpleStringProperty("Twister");
				else
					return null;
			}

		};

		scoreCallback=new Callback<CellDataFeatures<Score,String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Score, String> arg0) {
				String score=String.format("%.2f",arg0.getValue().scoreProperty().get());


				return new SimpleStringProperty(score);
			}

		};

		TableColumn<Score,String> one = new TableColumn<Score,String>("Game");
		TableColumn<Score,String> two = new TableColumn<Score,String>("Word");
		TableColumn<Score,String> three = new TableColumn<Score,String>("Time (sec)");
		TableColumn<Score,String> four = new TableColumn<Score,String>("Score");

		one.setCellValueFactory(gameNameCallback);
		two.setCellValueFactory(new PropertyValueFactory<Score,String>("puzzleWord"));
		three.setCellValueFactory(new PropertyValueFactory<Score,String>("timeStamp"));
		four.setCellValueFactory(scoreCallback);

		searchTableView.getColumns().setAll(one,two,three,four);
		//		searchTableView.getColumns().set(0, one);
		//		searchTableView.getColumns().set(1, two);
		//		searchTableView.getColumns().set(2, three);
		//		searchTableView.getColumns().set(3, four);

		searchTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



	}
}
