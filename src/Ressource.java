
public class Ressource {
	
	protected int value = 0;
	protected int production = 0;
	protected String name = new String();
	
	public Ressource() {
		
	}
	
	public Ressource(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public void addProductionToValue(){
		this.value += this.production;
	}
	
	public void setValue(int newValue){
		this.value = newValue;
	}
	
	public int getValue(){
		return value;
	}
	
	
	public void setProduction(int production, boolean add){
		if( add == true ){	
			this.production += production;
			
		}
		else{
			this.production -= production;
		}
		
	}

}
