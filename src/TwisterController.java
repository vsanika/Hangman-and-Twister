//sanika vaidya sanikav

package hw3;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class TwisterController extends WordNerdController {

	TwisterView twisterView; 
	Twister twister;
	static int size=0;

	@Override
	void startController() {
		// TODO Auto-generated method stub
		twisterView = new TwisterView();
		twister = new Twister();
		twister.twisterRound = twister.setupRound();
		twisterView.refreshGameRoundView(twister.twisterRound);
		setupBindings();

		VBox lowerPanel = new VBox();
		lowerPanel.getChildren().add(twisterView.bottomGrid);
		lowerPanel.getChildren().add(WordNerd.exitButton);
		lowerPanel.setAlignment(Pos.CENTER);

		WordNerd.root.setTop(twisterView.topMessageText);
		WordNerd.root.setCenter(twisterView.topGrid);
		WordNerd.root.setBottom(lowerPanel);

		GameView.wordTimer.timeline.setOnFinished(event -> { 
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[GameView.SADLY_INDEX]);
			twister.twisterRound.setIsRoundComplete(true);	

			sendScoreString(GameView.wordTimer.timerButton.getText());

		});
	}

	@Override
	void setupBindings() {
		// TODO Auto-generated method stub
		twisterView.clueGrid.disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());

		twisterView.playButtons[0].setOnAction(new NewButtonHandler());
		twisterView.playButtons[1].setOnAction(new TwistButtonHandler());

		for(int i=0;i<twister.twisterRound.getClueWord().length();i++)
		{
			twisterView.clueButtons[i].setOnAction(new ClueButtonHandler());
			twisterView.answerButtons[i].setOnAction(new AnswerButtonHandler());
		}

		twisterView.playButtons[2].setOnAction(new ClearButtonHandler());
		twisterView.playButtons[3].setOnAction(new SubmitButtonHandler());

		twisterView.bottomGrid.getChildren().clear();
		twisterView.topMessageText.setText("Twist to find " + twister.twisterRound.getSolutionWordsList().size() +" words");
		refresh();
	}


	private void refresh() {
		// TODO Auto-generated method stub
		for(int i=0;i<twister.twisterRound.getSolutionListsByWordLength().size();i++)
		{
			if(!(twister.twisterRound.getSolutionListsByWordLength().get(i).isEmpty()))
			{
				twisterView.wordLengthLabels[i] = new Label(String.format("%d", i + 3));  //starting with 3
				twisterView.wordLengthLabels[i].setPrefSize(50, 50);

				twisterView.wordLengthLabels[i].setStyle( "-fx-font: 15px Tahoma;" + 
						" -fx-background-color: lightgray;");
				twisterView.wordLengthLabels[i].setTextFill(Color.BLACK);
				twisterView.wordLengthLabels[i].setAlignment(Pos.CENTER);
				twisterView.bottomGrid.add(twisterView.wordLengthLabels[i], 0, i);

				twisterView.solutionListViews[i] = new ListView<>();
				twisterView.solutionListViews[i].setOrientation(Orientation.HORIZONTAL);
				twisterView.solutionListViews[i].setPrefSize(525, 50);
				twisterView.bottomGrid.add(twisterView.solutionListViews[i], 1, i);

				twisterView.wordScoreLabels[i] = new Label(String.format("%d", i + 3));  //starting with 3
				twisterView.wordScoreLabels[i].setPrefSize(50, 50);

				twisterView.wordScoreLabels[i].setStyle( "-fx-font: 15px Tahoma;" + 
						" -fx-background-color: lightgray;");
				twisterView.wordScoreLabels[i].setTextFill(Color.BLACK);
				twisterView.wordScoreLabels[i].setAlignment(Pos.CENTER);
				twisterView.bottomGrid.add(twisterView.wordScoreLabels[i], 2, i);

				String text=Integer.toString(twister.twisterRound.getSubmittedListByWordLength().get(i).size())+"/"+Integer.toString(twister.twisterRound.getSolutionListsByWordLength().get(i).size());
				twisterView.wordScoreLabels[i].setText(text);

			}
		}

	}


	class TwistButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<twisterView.clueButtons.length;i++)
			{
				if(!(twisterView.clueButtons[i].getText().equals(""))) {
					sb.append(twisterView.clueButtons[i].getText());
					twisterView.clueButtons[i].setText("");}
			}
			String updated=twister.makeAClue(sb.toString());
			for(int i=0;i<updated.length();i++)
			{
				twisterView.clueButtons[i].setText(String.format("%s",updated.charAt(i)));
			}
		}

	}

	class ClueButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Button button=(Button)arg0.getSource();

			for(int i=0;i<twisterView.clueButtons.length;i++)
			{
				String text=twisterView.answerButtons[i].getText();
				if(text.equals(""))
				{
					twisterView.answerButtons[i].setText(button.getText());
					button.setText("");
					break;

				}
			}
		}
	}

	class AnswerButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub

			Button button=(Button) arg0.getSource();

			for(int i=0;i<twisterView.answerButtons.length;i++)
			{
				String text=twisterView.clueButtons[i].getText();
				if(text.equals(""))
				{
					twisterView.clueButtons[i].setText(button.getText());
					button.setText("");
					break;
				}
			}

		}

	}
	class ClearButtonHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			for(int i=0;i<twisterView.answerButtons.length;i++)
			{
				twisterView.clueButtons[i].setText(String.valueOf(twister.twisterRound.getClueWord().charAt(i)));
				twisterView.answerButtons[i].setText("");
			}
		}

	}

	class NewButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub

			//twisterView.refreshGameRoundView(twister.setupRound());
			GameView.wordTimer.restart(Twister.TWISTER_GAME_TIME);
			startController();
			setupBindings();

		}

	}

	class SubmitButtonHandler implements EventHandler<ActionEvent>
	{

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub


			StringBuilder sb= new StringBuilder();
			for(int i=0;i<twisterView.answerButtons.length;i++)
			{
				sb.append(twisterView.answerButtons[i].getText());
			}

			String answer=sb.toString();

			int displaymessage=twister.nextTry(answer);
			twisterView.smileyButton.setGraphic(twisterView.smileyImageViews[displaymessage]);
			System.out.println(displaymessage);

			if(displaymessage==GameView.THUMBS_UP_INDEX)
			{
				int index=answer.length()-Twister.TWISTER_MIN_WORD_LENGTH;
				twisterView.solutionListViews[index].getItems().add(answer);
				Collections.sort(twisterView.solutionListViews[index].getItems());
				int solution=twister.twisterRound.getSolutionListsByWordLength().get(index).size();
				int submitted=twister.twisterRound.getSubmittedListByWordLength().get(index).size();
				twisterView.topMessageText.setText(twister.getScoreString());
				TwisterController.size++;
				twisterView.wordScoreLabels[answer.length()-Twister.TWISTER_MIN_WORD_LENGTH].setText(submitted+"/"+solution);


			}

			if(displaymessage==GameView.SMILEY_INDEX)
			{
				int index=answer.length()-Twister.TWISTER_MIN_WORD_LENGTH;
				twisterView.solutionListViews[index].getItems().add(answer);
				Collections.sort(twisterView.solutionListViews[index].getItems());
				int solution=twister.twisterRound.getSolutionListsByWordLength().get(index).size();
				int submitted=twister.twisterRound.getSubmittedListByWordLength().get(index).size();
				twisterView.topMessageText.setText(twister.getScoreString());
				TwisterController.size++;
				twisterView.wordScoreLabels[answer.length()-Twister.TWISTER_MIN_WORD_LENGTH].setText(submitted+"/"+solution);
				twister.twisterRound.setIsRoundComplete(true);
				twisterView.playButtons[1].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
				twisterView.playButtons[2].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
				twisterView.playButtons[3].disableProperty().bind(twister.twisterRound.isRoundCompleteProperty());
				twisterView.topMessageText.setText("Twist to find 0 of " + twister.twisterRound.getSolutionWordsList().size() +" words");
				//sendScoreString(GameView.wordTimer.timerButton.getText());
				GameView.wordTimer.timeline.stop();
			}

			new ClearButtonHandler().handle(arg0); //clear values after any type of submission

		}
	}

	public void sendScoreString(String timer) //send score string to write to csv file
	{
		String gameId=Integer.toString(WordNerd.TWISTER_ID);
		String puzzleWord=twister.twisterRound.getPuzzleWord();

		int submitted=0;
		for(int i=0;i<=Twister.TWISTER_MAX_WORD_LENGTH-Twister.TWISTER_MIN_WORD_LENGTH;i++)
		{
			submitted=submitted+twister.twisterRound.getSubmittedListByWordLength().get(i).size(); //count number of valid submitted answers
		}

		int solutions=twister.twisterRound.getSolutionWordsList().size();
		//int submitted=twister.twisterRound.getSubmittedListByWordLength().size();
		String score=Float.toString((float)submitted/solutions);

		int time=Twister.TWISTER_GAME_TIME-Integer.parseInt(timer);
		String timing=Integer.toString(time);


		new WordNerdModel().writeScore(gameId+","+puzzleWord+","+timing+","+score);



	}
}











