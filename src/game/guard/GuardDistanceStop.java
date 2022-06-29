package game.guard;

import body.Body;

/**
 * 指定距離による走行動作停止条件
 * @author 尾角 武俊
 */
public class GuardDistanceStop extends Guard {

    /**
     * 指定距離による走行動作停止を判定する
     * @return	True : 走行停止 / False ： 走行中
     */
	@Override
	public boolean judge() {
		return Body.selfPos.isDistanceStopped();
	}
}
