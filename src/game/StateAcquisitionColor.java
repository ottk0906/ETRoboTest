package game;

public class StateAcquisitionColor extends State {
	/*インスタンス*/
	private static StateAcquisitionColor instance = new StateAcquisitionColor();

	/**
	 * コンストラクタ
	 */
	public StateAcquisitionColor() {
		name = "StateAC";
	}

	/**
	 * 競技状態を遷移する
	 */
	@Override
	public void changeState(Game game) {
		game.changeState(this, StateEnd.getInstance());
	}

	public static StateAcquisitionColor getInstance() {
		return instance;
	}

}
