package game;
/**
 * 左右の列挙型
 * 左がfalse、右がtrue
 * @author 駒井
 *
 */
public enum LR {

	LEFT(false), RIGHT(true);

	private boolean pram;

	private LR(boolean param) {
		this.pram = param;
	}

	public boolean getParam() {
		return pram;
	}
}
