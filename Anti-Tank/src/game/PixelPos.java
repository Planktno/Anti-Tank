package game;

public class PixelPos{

	private int x;
	private int y;
	
	public PixelPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(PixelPos.class.isInstance(o)){
			PixelPos p = (PixelPos)o;
			if(p.getX() == x && p.getY() == y) return true;
			else return false;
		} else return false;
	}
	
	@Override
	public int hashCode() {
		return x*10000+y;
	}
	
}
