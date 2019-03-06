import edu.uc3m.game.GameBoardGUI;

public class Enemigo {

	private int xenemy;
	private int yenemy;
	private int tipo;
	private static int id=21;
	private int minId;
	private int contador=0;
	private int s_ant=100;
	private int movimiento = (int) (Math.random()*4+1);
	private int sprite_actual;

	//Constructor
	public Enemigo(int x, int y, int tipo, GameBoardGUI gui, Enemigo[] arrayEnemigos) {
		this.xenemy = x*10;
		this.yenemy = y*10;
		this.tipo = tipo;
		sprites(gui);
		mostrar(100, gui, arrayEnemigos);
	}
	//Movimiento aleatorio de los enemigos
	public void movimientoEnemigo(Casillas[][] matriz, GameBoardGUI gui, Enemigo[] arrayEnemigos) {
		//Timer de decisión de movimiento
		long tiempo_espera= 1000L;
		if(Config.tiempo_actual-Config.tiempo_inicial>=tiempo_espera) {
			movimiento = (int) (Math.random()*4+1);
			Config.tiempo_inicial= Config.tiempo_actual;
		}
		switch(movimiento) {
		case 1: //Izquierda
			//Comprueba que la siguiente coordenada es traspasable
			if(matriz[(xenemy-1)/10-1/10][(yenemy+8)/10].getTraspasable()==true&& matriz[(xenemy-1)/10-1/10][(yenemy+2)/10].getTraspasable()==true) {
				xenemy-=1;
				//Reinicia el contador de sprites
				if(contador>=3) {
					contador=0;
				}
				mostrar(contador+minId, gui, arrayEnemigos);
				gui.gb_setSpriteVisible(s_ant, false);
				s_ant=contador+minId;
				contador++;
			}else {
				//En caso de que no sea traspasable vuelve a elegir movimiento
				movimiento = (int) (Math.random()*4+1);
			}
			break;

		case 2: //Derecha
			if(matriz[(xenemy+10)/10+1/10][(yenemy+8)/10].getTraspasable()==true && matriz[(xenemy+10)/10+1/10][(yenemy+2)/10].getTraspasable()==true) {
				xenemy+=1;
				if(contador>=3) {
					contador=0;
				}
				mostrar(contador+minId+3, gui, arrayEnemigos);
				gui.gb_setSpriteVisible(s_ant, false);
				s_ant=contador+minId+3;
				contador++;
			}else {
				movimiento = (int) (Math.random()*4+1);
			}
			break;

		case 3: //Arriba
			if(matriz[(xenemy+2)/10][(yenemy)/10-1/10].getTraspasable()==true&& matriz[(xenemy+8)/10][(yenemy)/10-1/10].getTraspasable()==true) {
				yenemy-=1;
				if(contador>=3) {
					contador=0;
				}
				mostrar(contador+minId, gui, arrayEnemigos);
				gui.gb_setSpriteVisible(s_ant, false);
				s_ant=contador+minId;
				contador++;
			}else {
				movimiento = (int) (Math.random()*4+1);
			}
			break;

		case 4: //Abajo
			if(matriz[(xenemy+8)/10][(yenemy+11)/10+1/10].getTraspasable()==true && matriz[(xenemy+2)/10][(yenemy+11)/10+1/10].getTraspasable()==true) {
				yenemy+=1;
				if(contador>=3) {
					contador=0;
				}
				mostrar(contador+minId+3, gui, arrayEnemigos);
				gui.gb_setSpriteVisible(s_ant, false);
				s_ant=contador+minId+3;
				contador++;
			}else {
				movimiento = (int) (Math.random()*4+1);
			}
			break;
		}
	}
	//Todos los sprites de los enemigos
	public void sprites(GameBoardGUI gui) {
		minId=id;
		switch(tipo) {
		case 1:
			gui.gb_addSprite(100, "enemy111.png", true);
			gui.gb_addSprite(id++, "enemy111.png", true);
			gui.gb_addSprite(id++, "enemy112.png", true);
			gui.gb_addSprite(id++, "enemy113.png", true);
			gui.gb_addSprite(id++, "enemy121.png", true);
			gui.gb_addSprite(id++, "enemy122.png", true);
			gui.gb_addSprite(id++, "enemy123.png", true);
			break;
		}
	}
	//Mostrador de sprites
	public void mostrar(int id, GameBoardGUI gui, Enemigo[] arrayEnemigos) {
		gui.gb_moveSpriteCoord(id, xenemy , yenemy);  
		for(int i = 0; i<arrayEnemigos.length ; i++) {
			if(arrayEnemigos[i] !=null) {
				gui.gb_setSpriteVisible(id, true);
			}
		}
		sprite_actual=id;
	}
	//Getters de la posición del enemigo
	public int getXenemy() {
		return xenemy;
	}
	public int getYenemy() {
		return yenemy;
	}
	//Getter del último sprite del enemigo
	public int getSprite_actual() {
		return sprite_actual;
	}	
}
