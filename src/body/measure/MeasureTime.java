package body.measure;

import lejos.utility.Stopwatch;
/**
 * 時間計測クラス
 * @author 駒井
 *
 */
public class MeasureTime {
	private Stopwatch stopwatch;

	public MeasureTime() {
		this.stopwatch = new Stopwatch();
	}


	public void resetTime(){
		stopwatch.reset();
	}
	public int getTime(){
		return stopwatch.elapsed();
	}

}
