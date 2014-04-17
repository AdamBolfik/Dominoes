// As Stated

public class GameState {
	
	private static GameState instance = new GameState();
	private BoneYard boneyard;
	private Board board;
	private Hand[] hands;
	private Strategy[] strategies;
	private int[] scores;
	private Status status;
	private int numPlayers;
	private int currentPlayer;
	private int whoDominoed;
	private int winner;
	
	private GameState() {
		hands = new Hand[2];
	}
	
	public static GameState getInstance(){
		return instance;
	}

	public BoneYard getBoneyard() {
		return boneyard;
	}

	public void setBoneyard(BoneYard boneyard) {
		this.boneyard = boneyard;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Hand getHand(int player){
		return hands[player];
	}
	
	public void setHand(int player, Hand hand){
		hands[player] = hand;
	}

	public Hand[] getHands() {
		return hands;
	}

	public void setHands(Hand[] hands) {
		this.hands = hands;
	}

	public Strategy[] getStrategies() {
		return strategies;
	}

	public void setStrategies(Strategy[] strategies) {
		this.strategies = strategies;
	}
	
	public int getScore(int i){
		return scores[i];
	}
	
	public void setScore(int p, int s){
		scores[p] = s;
	}
	
	public Status getStatus(){
		return status;
	}
	
	public void setStatus(Status status){
		this.status = status;
	}
	
	public int[] getScores() {
		return scores;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getWhoDominoed() {
		return whoDominoed;
	}

	public void setWhoDominoed(int whoDominoed) {
		this.whoDominoed = whoDominoed;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

}
