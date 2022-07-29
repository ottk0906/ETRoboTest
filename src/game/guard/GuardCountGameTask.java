package game.guard;

public class GuardCountGameTask extends Guard {
	private int count;
	private int targetCount;

	public GuardCountGameTask(int targetcount){
		this.count = 0;
		this.targetCount = targetcount;
	}

	@Override
	public boolean judge() {
		return targetCount <= count++;
	}

}
