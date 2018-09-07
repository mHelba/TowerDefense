package code.utils;

public enum EnumGameState {
	MAINSCREEN (1),
	GAME (2);
	
	private int id;
	
	private EnumGameState(int id){
		this.id = id;
	}
	
	public int getID(){
		return this.id;
	}
}
