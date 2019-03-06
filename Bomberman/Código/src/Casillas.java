
public class Casillas {

	private boolean traspasable;
	private boolean rompible=true;
	private boolean fuego;
	private boolean Puerta;
	private boolean Max;
	private boolean Alcance;
	private boolean Velocidad;
	private boolean Geta;
	private boolean Remoto;
	private boolean Bombas;

	
	public Casillas(boolean traspasable, boolean rompible) {
		this.traspasable = traspasable;
		this.rompible=rompible;
		this.fuego = false;
	}
	
	//Getters y Setters de traspasable
	public boolean getTraspasable() {
		return traspasable;
	}
	public void setTraspasable(boolean traspasable) {
		this.traspasable = traspasable;
	}	
	
	//Getters y Setters de rompible
	public boolean getRompible() {
		return rompible;
	}
	public void setRompible(boolean rompible) {
		this.rompible = rompible;
	}
	
	//Getters y Setters del fuego
	public boolean getFuego() {
		return fuego;
	}

	public void setFuego(boolean fuego) {
		this.fuego = fuego;
	}

	public boolean getPuerta() {
		return Puerta;
	}

	public void setPuerta(boolean puerta) {
		Puerta = puerta;
	}

	public boolean getMax() {
		return Max;
	}

	public void setMax(boolean max) {
		Max = max;
	}

	public boolean getAlcance() {
		return Alcance;
	}

	public void setAlcance(boolean alcance) {
		Alcance = alcance;
	}

	public boolean getVelocidad() {
		return Velocidad;
	}

	public void setVelocidad(boolean velocidad) {
		Velocidad = velocidad;
	}

	public boolean getGeta() {
		return Geta;
	}

	public void setGeta(boolean geta) {
		Geta = geta;
	}

	public boolean getRemoto() {
		return Remoto;
	}

	public void setRemoto(boolean remoto) {
		Remoto = remoto;
	}

	public boolean getBombas() {
		return Bombas;
	}

	public void setBombas(boolean bombas) {
		Bombas = bombas;
	}	
}