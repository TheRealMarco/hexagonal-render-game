import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;

import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

public class GameMain {
	
	//l'horloge pour le temps 
	protected static Clock clock = new Clock();
	//couleur du fond 
	protected static Color backgroundColor = new Color(20,135,252);
	//pour la musqiue 
	//protected static Music gameMusic = new Music();
	
	//la camera ( zone que l'on affiche )
	static View camera = new View(new FloatRect(0, 0,1920, 1080)); 
	
	//les tableau 2d de type HexaStruct ( voir classe hexastruct ) pour la map et le curseur 
	protected static ArrayList<ArrayList<HexaStruct>> loadMap = new ArrayList<ArrayList<HexaStruct>>(); 
	protected static ArrayList<ArrayList<RectangleShape>> loadCursor = new ArrayList<ArrayList<RectangleShape>>(); 
	
	//les textures du curseur 
	protected static Texture cursorTexture = new Texture();
	protected static Texture noCursorTexture = new Texture();
	
	//la bar de status 
	protected static RectangleShape statusBar = new RectangleShape(new Vector2f(1920,150));
	protected static Texture barTexture = new Texture();
	
	//x et y sont utilisés dans les boucles for qui parcours les tableaus 
	protected static int x;
	protected static int y;
	
	//les différentes ressources du jeu 
	protected static Ressource argent = new Ressource(200,"Argent");
	protected static Ressource bois = new Ressource(200,"Bois");
	protected static Ressource pierre = new Ressource(200,"Pierre");
	protected static Ressource nourriture = new Ressource(200,"Nourriture");
	protected static Ressource population = new Ressource (50,"Population");
	
	//le tableau 2D de la map 
	protected static int mapTab[][] = { 
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0},
			{0,0,0,0,12,0,0,0,0,0,0,0,0,0,0,0},
			{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,10,10,10,0,0,12,0,12,0,0},
			{0,0,0,0,0,0,10,10,10,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,10,10,10,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,10,10,10,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,11,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{13,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			
	
			
			
		};
	//le tableau 2D du curseur 
	protected static int cursorTab[][] = { 
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			
	
			
			
		};
	
	
	

	//methode main 
	public static void main(String[] args) throws IOException {
		
		//parametrage de la fenetre 
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(1920, 1080), "Hexagonal Game", WindowStyle.FULLSCREEN);
		window.setFramerateLimit(30);
		
		window.setView(camera);
		//gameMusic.openFromFile(Paths.get("src/musique.wav"));
		//gameMusic.setLoop(true);
		//gameMusic.play();
		HexaStruct.mapTexture[0].loadFromFile(Paths.get("src/Tiles/Terrain/Grass/grass_05.png"));
		HexaStruct.mapTexture[1].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_archery.png"));
		HexaStruct.mapTexture[2].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_archway.png"));
		HexaStruct.mapTexture[3].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_blacksmith.png"));
		HexaStruct.mapTexture[4].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_mine.png"));
		HexaStruct.mapTexture[5].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_church.png"));
		HexaStruct.mapTexture[6].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_farm.png"));
		HexaStruct.mapTexture[7].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_house.png"));
		HexaStruct.mapTexture[8].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_largeCastle.png"));
		HexaStruct.mapTexture[9].loadFromFile(Paths.get("src/Tiles/Medieval/medieval_lumber.png"));
		
		//autres textures 
		HexaStruct.mapTexture[10].loadFromFile(Paths.get("src/Tiles/Terrain/Grass/grass_12.png"));
		HexaStruct.mapTexture[11].loadFromFile(Paths.get("src/Tiles/Terrain/Grass/grass_17.png"));
		HexaStruct.mapTexture[12].loadFromFile(Paths.get("src/Tiles/Terrain/Grass/grass_11.png"));
		HexaStruct.mapTexture[13].loadFromFile(Paths.get("src/Tiles/Terrain/Grass/grass_14.png"));
		
		cursorTexture.loadFromFile(Paths.get("src/Tiles/cursor.png"));
		noCursorTexture.loadFromFile(Paths.get("src/Tiles/noCursor.png"));
		
		barTexture.loadFromFile(Paths.get("src/Tiles/statusBar.png"));
		statusBar.setTexture(barTexture);
		statusBar.setPosition(new Vector2f(0, 1080-150));
		mapGenerator();
		cursorGenerator();
		
		while(window.isOpen()) {
		   
		    window.clear(backgroundColor);
		     
		    //maj des ressources 
		    
		    if( clock.getElapsedTime().asMilliseconds() > 2000){
		    	clock.restart();
		    	bois.addProductionToValue();
		    	pierre.addProductionToValue();
		    	argent.addProductionToValue();
		    	nourriture.addProductionToValue();
		    	System.out.println(bois.getValue());
		    	
		    }
		    

		    for(int z = 0; z < loadMap.size(); z++){
				for(int y = 0; y < loadMap.get(z).size(); y++){
					window.draw(loadMap.get(z).get(y).display());
				}
			}
		    
		    for(int z = 0; z < loadCursor.size(); z++){
				for(int y = 0; y < loadCursor.get(z).size(); y++){
					window.draw(loadCursor.get(z).get(y));
				}
			}
		    
		    window.setView(camera);
		    window.draw(statusBar);
		    window.display();
		    
		    for(Event event : window.pollEvents()) {
				 
				if(event.type == Event.Type.CLOSED || Keyboard.isKeyPressed(Keyboard.Key.ESCAPE)) {
					
					window.close();
					}
				}
			//déplacements de la camera 
			if(Keyboard.isKeyPressed(Keyboard.Key.UP)){
				camera.move(new Vector2f(0,-15f));
				statusBar.move(new Vector2f(0, -15f));
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.DOWN)){
				camera.move(new Vector2f(0,15f));
				statusBar.move(new Vector2f(0, 15f));
			}
			if(Keyboard.isKeyPressed(Keyboard.Key.RIGHT)){
				camera.move(new Vector2f(15f,0));
				statusBar.move(new Vector2f(15f, 0));
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.LEFT)){
				camera.move(new Vector2f(-15f,0));
				statusBar.move(new Vector2f(-15f, 0));
			}
			
		
			
			//mouv curseur 
			if(Keyboard.isKeyPressed(Keyboard.Key.Z)){
				mouveCursor(1);
				cursorGenerator();
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.S)){
				mouveCursor(2);
				cursorGenerator();
			}
			if(Keyboard.isKeyPressed(Keyboard.Key.D)){
				mouveCursor(3);
				cursorGenerator();
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.Q)){
				mouveCursor(4);
				cursorGenerator();
			}
			
			
			
			//action on a item 
			if(Keyboard.isKeyPressed(Keyboard.Key.NUM1)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(1);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM2)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(2);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM3)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(3);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM4)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(4);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							pierre.setProduction(2, true);
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM5)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(5);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM6)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(6);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							nourriture.setProduction(2, true);
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM7)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(7);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							population.setValue(population.getValue()+5);
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM8)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(8);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							break;
						}
					}
				}
			}
			else if(Keyboard.isKeyPressed(Keyboard.Key.NUM9)){
				for(int z = 0; z < cursorTab.length; z++){
					for(int y = 0; y < cursorTab[z].length; y++){
						if(cursorTab[z][y] == 1 && mapTab[z][y] == 0 && bois.getValue() >= 25){
							bois.setValue(bois.getValue()-25);
							loadMap.get(z).get(y).setType(9);
							mapTab[z][y] = loadMap.get(z).get(y).getType();
							z = cursorTab.length - 1;
							bois.setProduction(2, true);
							break;
						}
					}
				}
			}
		}

	}
	
	
	public static void mapGenerator(){
		for(int i = 0; i < mapTab.length; i++){
			loadMap.add(new ArrayList<HexaStruct>());
			for(int j = 0; j < mapTab[i].length; j++){
					x=i*120+(j)*(120/2);
					y=j*104;
				
				
	                loadMap.get(i).add(new HexaStruct());
					loadMap.get(i).get(j).setPosition(x,y);
					loadMap.get(i).get(j).setType(mapTab[i][j]);
				
				
				
				
				
   
			}
		}
	}
	
	public static void cursorGenerator(){
		loadCursor.clear();
		for(int i = 0; i < cursorTab.length; i++){
			loadCursor.add(new ArrayList<RectangleShape>());
			for(int j = 0; j < cursorTab[i].length; j++){
				x=i*120+(j)*(120/2);
				y=j*104;
				
                loadCursor.get(i).add(new RectangleShape(new Vector2f(120f,140f)));
                loadCursor.get(i).get(j).setPosition(x,y);
                
                
				if( cursorTab[i][j] == 1 ){
					loadCursor.get(i).get(j).setTexture(cursorTexture);
				}
				else{
					loadCursor.get(i).get(j).setTexture(noCursorTexture);
				}
			}
		}

		
	}
	
	public static void mouveCursor(int direction){
		for(int i = 0; i < cursorTab.length; i++){
			for(int j = 0; j < cursorTab[i].length; j++){
				if(cursorTab[i][j] == 1){
					//haut
					if(direction == 1 && j != 0){
						cursorTab[i][j] = 0;
						cursorTab[i][j-1] = 1;
						i = cursorTab.length - 1;
						break;
						
					}
					//bas
					if(direction == 2 && j < cursorTab[i].length - 1){
						cursorTab[i][j] = 0;
						cursorTab[i][j+1] = 1;
						i = cursorTab.length - 1;
						break;
					}
					//droite
					if(direction == 3 && i < cursorTab.length - 1){
						cursorTab[i][j] = 0;
						cursorTab[i+1][j] = 1;
						i = cursorTab.length - 1;
						break;
					}
					//gauche
					if(direction == 4 && i !=0){
						cursorTab[i][j] = 0;
						cursorTab[i-1][j] = 1;
						i = cursorTab.length - 1;
						break;
						
					}
               
				}
			
			}
		}
	}
	
	

}
