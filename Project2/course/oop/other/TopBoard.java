package course.oop.other;

public class TopBoard extends Board<BottomBoard> {

    public TopBoard() {
    }

	@Override
	public String draw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean attemptMove(Player player, Pair move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameStatus updateStatus(Pair lastMove) {
		// TODO Auto-generated method stub
		return null;
	}
}