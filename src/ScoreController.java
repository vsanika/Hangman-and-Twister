//sanika vaidya sanikav
package hw3;

import java.util.List;

import javafx.collections.ListChangeListener;

import javafx.scene.chart.LineChart;

public class ScoreController extends WordNerdController{

	//this is a placeholder class. Will be completed in HW3
	ScoreView scr;
	ScoreChart sc;

	@Override
	void startController() {
		//to be implemented in HW3
		scr= new ScoreView();
		WordNerd.root.setCenter(scr.scoreGrid);
		setupBindings();
		wordNerdModel.readScore();
		scr.setupView();

		sc=new ScoreChart();

		List<LineChart<Number,Number>> chart=sc.drawChart(wordNerdModel.scoreList);

		for(int i=0;i<chart.size();i++)
		{
			scr.scoreGrid.add(chart.get(i), 0, i+1,2,1);
		}
	}

	@Override
	void setupBindings() {
		//to be implemented in HW3

		wordNerdModel.scoreList.addListener(new ListChangeListener<Score>() 
		{
			@Override
			public void onChanged(Change<? extends Score> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
