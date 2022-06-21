package log;

/**
 * 自己位置推定ログデータクラス
 * @author 尾角 武俊
 */
public class LogSelfPositionData {

	private double currentX;
	private double currentY;
	private double currentAngle;

    /**
     * コンストラクタ
     * @param currentX			X座標
     * @param currentY			Y座標
     * @param currentAngle		回転角度
     */
	public LogSelfPositionData(double currentX, double currentY, double currentAngle ) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.currentAngle = currentAngle;
	}

    /**
     * オブジェクトの文字列表現を取得する
     * <p>CSVファイルへの出力形式  →  X座標 , Y座標 , 回転角度 改行
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(currentX);
        sb.append(",");
        sb.append(currentY);
        sb.append(",");
        sb.append(currentAngle);
        sb.append("\r\n");
        return sb.toString();
    }



}
