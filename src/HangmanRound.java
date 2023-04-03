//sanika vaidya sanikav

package hw3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HangmanRound extends GameRound{
	private IntegerProperty hitCount = new SimpleIntegerProperty();
	private IntegerProperty missCount = new SimpleIntegerProperty();


	public int getHitCount()
	{
		return hitCount.get();
	}
	public void setHitCount(int hitCount)
	{
		this.hitCount.set(hitCount);
	}
	public IntegerProperty hitCountProperty() 
	{ 
		return hitCount; 
	}
	public int getMissCount()
	{
		return missCount.get();
	}
	public void setMissCount(int missCount)
	{
		this.missCount.set(missCount);
	}
	public IntegerProperty missCountProperty() 
	{ 
		return missCount; 
	}

}
