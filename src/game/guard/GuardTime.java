package game.guard;

import body.Body;

public class GuardTime extends Guard{

	private int targetTime;
	public GuardTime(int targetTime) {
		this.targetTime =targetTime;
	}
    /**
     * 判定する
     * @return
     */
    @Override
    public boolean judge(){
        return (targetTime<Body.measure.getTime());
    }
}
