package game.guard;

import body.Body;

public class GuardDegrees extends Guard{

	private double targetDegrees;
	public GuardDegrees(double targetDegrees) {
		this.targetDegrees =targetDegrees;
	}
    /**
     * 判定する
     * @return
     */
    @Override
    public boolean judge(){
        return (targetDegrees<Body.selfPos.getAfterRadianToAngle());
    }
}