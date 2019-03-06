import edu.uc3m.game.GameBoardGUI;

public class Personajes {

	//Atributos
	private int Xmuñeco;
	private int Ymuñeco;
	private int c = 1;
	private int c_ant=1;
	private int max = 5;
	private boolean espacio=false;
	private int sprite_actual;
	private boolean vivo=true;
	
	//Constructor
	public Personajes(GameBoardGUI gui) {
		this.Xmuñeco = 10;
		this.Ymuñeco = 10;
	}
	//Todos los sprites del jugador
	public void pintarJugador(GameBoardGUI gui) {
		//Creamos nuestro personaje
		gui.gb_addSprite(1, "bomberman111.png", true);
		gui.gb_moveSpriteCoord(1,Xmuñeco,Ymuñeco);
		gui.gb_setSpriteVisible(1, true);
		//Cargamos todas las imagenes de abajo
		gui.gb_addSprite(2, "bomberman112.png", true);
		gui.gb_addSprite(3, "bomberman113.png", true);
		gui.gb_addSprite(4, "bomberman114.png", true);
		gui.gb_addSprite(5, "bomberman115.png", true);
		//Cargamos todas las imagenes de arriba
		gui.gb_addSprite(6, "bomberman101.png", true);
		gui.gb_addSprite(7, "bomberman102.png", true);
		gui.gb_addSprite(8, "bomberman103.png", true);
		gui.gb_addSprite(9, "bomberman104.png", true);
		gui.gb_addSprite(10, "bomberman105.png", true);
		//Cargamos todas las imagenes de izquierda
		gui.gb_addSprite(11, "bomberman121.png", true);
		gui.gb_addSprite(12, "bomberman122.png", true);
		gui.gb_addSprite(13, "bomberman123.png", true);
		gui.gb_addSprite(14, "bomberman124.png", true);
		gui.gb_addSprite(15, "bomberman125.png", true);
		//Cargamos todas las imagenes de derecha
		gui.gb_addSprite(16, "bomberman131.png", true);
		gui.gb_addSprite(17, "bomberman132.png", true);
		gui.gb_addSprite(18, "bomberman133.png", true);
		gui.gb_addSprite(19, "bomberman134.png", true);
		gui.gb_addSprite(20, "bomberman135.png", true);
	}
	//El movimiento del jugador
	public void moverJugador(GameBoardGUI gui, Casillas[][] matriz, Bomba[]arrayBomba, Main main) {

		//captura por teclado una cosa de cada vez
		String lastAction = gui.gb_getLastAction().trim();
		//Para resetear los sprites cuando llegue al último
		if (c > max){
			c = 1;
		}
		//Si es mayor de 0 es que has hecho algo
		if (lastAction.length() > 0 && vivo == true){
			//Definimos movimiento con flechitas
			if (lastAction.equals("right") && matriz[(Xmuñeco+10)/10+Config.speed/10][(Ymuñeco+8)/10].getTraspasable()==true && matriz[(Xmuñeco+10)/10+2/10][(Ymuñeco+2)/10].getTraspasable()==true){
				Xmuñeco+=Config.speed;
				gui.gb_setSpriteVisible(c_ant, false);
				c++;
				mostrar(gui,c+14);
				c_ant=c+14;
			}
			else if (lastAction.equals("left")&& matriz[(Xmuñeco-1)/10-Config.speed/10][(Ymuñeco+8)/10].getTraspasable()==true&& matriz[(Xmuñeco-1)/10-2/10][(Ymuñeco+2)/10].getTraspasable()==true){
				Xmuñeco-=Config.speed;
				gui.gb_setSpriteVisible(c_ant, false);
				c++;
				mostrar(gui,c+9);
				c_ant=c+9;
			}
			else if (lastAction.equals("up")&& matriz[(Xmuñeco+2)/10][(Ymuñeco)/10-Config.speed/10].getTraspasable()==true&& matriz[(Xmuñeco+8)/10][(Ymuñeco)/10-2/10].getTraspasable()==true){
				Ymuñeco-=Config.speed;
				gui.gb_setSpriteVisible(c_ant, false);
				c++;
				mostrar(gui,c+4);
				c_ant=c+4;
			}
			else if (lastAction.equals("down")&& matriz[(Xmuñeco+8)/10][(Ymuñeco+11)/10+Config.speed/10].getTraspasable()==true&& matriz[(Xmuñeco+2)/10][(Ymuñeco+11)/10+2/10].getTraspasable()==true){
				Ymuñeco+=Config.speed;
				gui.gb_setSpriteVisible(c_ant, false);
				c++;
				mostrar(gui,c-1);
				c_ant=c-1;
			}
			else if (lastAction.equals("space") && Config.nbombas>0){
				setEspacio(true);
			}
			else if(lastAction.equals("exit game")) {
				System.exit(0);
			}
			else if(lastAction.equals("new game")) {
				
			}
			else if(lastAction.equals("tab")) {
				Config.detonada=true;
			}
		}
	}

	//El mostrador de los sprites
	private void mostrar(GameBoardGUI gui, int id) {
		gui.gb_moveSpriteCoord(id, Xmuñeco , Ymuñeco);
		gui.gb_setSpriteVisible(id, true);
		sprite_actual=id;
	}
	//Los getters de la posición del jugador
	public int getXmuñeco() {
		return Xmuñeco;
	}
	public int getYmuñeco() {
		return Ymuñeco;
	}
	//Getter de si he pulsado el espacio
	public boolean getEspacio() {
		return espacio;
	}
	public void setEspacio(boolean espacio) {
		this.espacio = espacio;
	}

	public void suicidio(GameBoardGUI gui, Main main) {
		
		//Cargamos todos los sprites del suisidio
		gui.gb_addSprite(1000, "bomberman141.png", true);
		gui.gb_addSprite(1001, "bomberman142.png", true);
		gui.gb_addSprite(1002, "bomberman143.png", true);
		gui.gb_addSprite(1003, "bomberman144.png", true);
		gui.gb_addSprite(1004, "bomberman145.png", true);

		//Borramos sprite actual
		gui.gb_setSpriteVisible(sprite_actual, false);
		if(Config.frames%5==0 && main.getMe_mato()==true) {
			vivo = false;
			mostrar(gui, 999+c);
			if(c>1) {
				gui.gb_setSpriteVisible(998+c, false);
			}
			if(c>=5) {
				gui.gb_showMessageDialog("Game Over");
				main.setVivo(false);
			}
			c++;
		}
	}
}

