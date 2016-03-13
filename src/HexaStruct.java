import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class HexaStruct {
	
	protected RectangleShape hexaSprite = new RectangleShape(new Vector2f(120f,140f));
	protected int tileType;
	public static Texture mapTexture[] = {new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture(),new Texture()}; 
	
	public HexaStruct() {
		
	}
	
	public void setTexture(int tileNumber){
		hexaSprite.setTexture(mapTexture[tileNumber]);
	}
	
	public void setPosition(float x, float y){
		hexaSprite.setPosition(x,y);
	}
	
	public RectangleShape display(){
		return hexaSprite;
	}
	
	public void setType(int type){
		tileType = type;
		this.setTexture(type);
	}
	
	public int getType(){
		return this.tileType;
	}

}
