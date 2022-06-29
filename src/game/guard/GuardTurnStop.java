package game.guard;

import body.Body;

/**
 * 旋回動作停止条件
 * @author 尾角 武俊
 */
public class GuardTurnStop extends Guard  {

	public GuardTurnStop() {
	}

    /**
     * 旋回停止を判定する
     * @return	True : 旋回停止 / False ： 旋回中
     */
	@Override
	public boolean judge() {
		return Body.selfPos.isTurnStopped();
	}

}