import edu.uc3m.game.GameBoardGUI;
public class Main {

	private Bomba [] objetoBomba;
	private GameBoardGUI gui;
	private Tablero objetoTablero;
	private Personajes objetoPersonaje;
	private Enemigo[] objetoEnemigo;
	private PowerUps objetoPowerUp;
	private static boolean vivo = true;
	private boolean me_mato = false;
	private int c_vel=0;
	private int c_geta=0;
	private int c_bombas=0;
	private int veces=0;
	private int c_alcance=0;
	private int c_max=0;
	private int c_remoto=0;

	//Constructor donde creamos todos los objetos
	public Main(){
		objetoBomba = new Bomba [5];
		//Cargamos la gui
		gui = new GameBoardGUI(17, 17);
		//Llamamos a la clase tablero
		objetoTablero=new Tablero();
		objetoPersonaje = new Personajes(gui);		
		objetoEnemigo = new Enemigo[Config.nenemigos];
		objetoPowerUp = new PowerUps();
	}


	//Getter y Setters de vida
	public static boolean getVivo() {
		return vivo;
	}
	public void setVivo(boolean vivo) {
		Main.vivo = vivo;
	}
	//Matamos a los enemigos
	public void MuerteYDestruccion() {
		for(int i=0;i<objetoEnemigo.length;i++) {
			if(objetoEnemigo[i]!=null && objetoTablero.getMatriz()[(objetoEnemigo[i].getXenemy()+5)/10][(objetoEnemigo[i].getYenemy()+5)/10].getFuego()==true) {
				gui.gb_setSpriteVisible(objetoEnemigo[i].getSprite_actual(), false);
				objetoEnemigo[i]=null;
				Config.points+=100;
				gui.gb_setValuePointsUp(Config.points);
				gui.gb_println("+ 100 points");
				Config.nenemigos--;
			}
		}
	}
	//Suicidio
	public void Suicidio() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getFuego()==true) {
			setMe_mato(true);
			objetoPersonaje.suicidio(gui, this);
		}
	}
	//Getter de si me he matado
	public boolean getMe_mato() {
		return me_mato;
	}
	public void setMe_mato(boolean me_mato) {
		this.me_mato = me_mato;
	}
	public void MoverJugador() {
		//Movemos al jugador
		objetoPersonaje.moverJugador(gui, objetoTablero.getMatriz(), objetoBomba, this);
	}
	public void InicializarBombas() {
		//Ponemos 5 bombas porque no da tiempo a poner más sin que estallen las anteriores
		for(int i = 0; i < objetoBomba.length; i++) {
			objetoBomba[i] = new Bomba(0, 0, gui, objetoBomba);
		}
	}
	public void CrearBombas() {
		//Creamos todas las bombas
		boolean bombaCreada = false;
		int cBomba = 0;
		if(objetoPersonaje.getEspacio()==true && (Config.nbombas>0)) {
			while(bombaCreada == false){
				if(objetoBomba[cBomba].getCreada() == false) {
					objetoBomba[cBomba] = new Bomba((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, gui, objetoBomba);
					objetoBomba[cBomba].setCreada(true);
					gui.gb_setValuePointsDown(Config.nbombas);
					bombaCreada = true;
					Config.nbombas--;
					gui.gb_setValuePointsDown(Config.nbombas);
					objetoPersonaje.setEspacio(false); 
				}else {
					cBomba++;
				}

			}
		}
	}

	//Comprobar cada vez si deben detonarse las bombas colocadas
	public void ComprobarDetonacion() {
		for(int i = 0 ; i<objetoBomba.length ; i++) {
			if(objetoBomba[i].getCreada()==true) {	
				objetoBomba[i].todo(objetoTablero.getMatriz(), gui, objetoEnemigo, objetoBomba);
			}
		}		
	}
	public void CrearEnemigos() {
		//Recoremos todo el array de enemigos
		for(int i = 0 ; i < Config.nenemigos ; i++) {
			boolean creado = false;
			do {
				//Creamos posiciones aleatorias para el enemigo
				int fil = (int)(Math.random()*15+1);
				int col = (int)(Math.random()*15+1);
				if(objetoTablero.getMatriz()[fil][col].getTraspasable()==true && (fil > 2 && col > 2)) {
					objetoEnemigo[i] = new Enemigo(fil, col, 1, gui, objetoEnemigo);
					creado = true;
				}
			}while(creado==false);
		}
	}
	public void MoverEnemigos() {
		if(Config.frames%2==0) {
			//Movemos a todos los enemigos con un timer
			for(int i = 0 ; i < objetoEnemigo.length ; i++) {
				if(objetoEnemigo[i]!=null) {
					objetoEnemigo[i].movimientoEnemigo(objetoTablero.getMatriz(), gui, objetoEnemigo);
				}
			}
		}
	}
	public void PintarJugador() {
		objetoPersonaje.pintarJugador(gui);
	}
	public void Tablero() {
		objetoTablero.todoTablero(gui);
	}
	public void Contacto() {
		for(int i = 0 ; i<objetoEnemigo.length ; i++) {
			if(objetoEnemigo[i]!=null) {
				if(objetoTablero.getMatriz()[(objetoEnemigo[i].getXenemy()+5)/10][(objetoEnemigo[i].getYenemy()+5)/10]==objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10] 
						&& objetoEnemigo[i]!=null && objetoTablero.getMatriz()[(objetoEnemigo[i].getXenemy()+5)/10][(objetoEnemigo[i].getYenemy()+5)/10].getFuego()==false) {
					Config.health--;
					gui.gb_setValueHealthCurrent(Config.health);
					gui.gb_animateDamage();
					if(Config.health<=0) {
						setVivo(false);
						gui.gb_showMessageDialog("Game Over");
					}
				}
			}
		}
	}

	public void Puerta() {
		objetoPowerUp.Puerta(gui, objetoTablero.getMatriz());
	}

	//Si pisas la puerta y has matado a los enemigos dice una cosa, si no los has matado dice otra cosa
	public void ComprobarPuerta() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getPuerta()==true && Config.nenemigos==0) {
			gui.gb_showMessageDialog("Congratulations! You have completed the level");
			gui.gb_showMessageDialog("Unfortunately the following ones are still in process...");
			setVivo(false);
		}else if (objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getPuerta()==true && Config.nenemigos!=0) {
			if(veces==0) {
				//Si te pones encima sin matar a todos dice esto sólo una vez, pero si te sales de la casilla y vuelves te lo vuelve a decir
				gui.gb_showMessageDialog("There are still some ballons to take down! \n Come back here when you are done with them");
			}
			veces++;
		}else {
			veces=0;
		}
	}

	public void Velocidad() {
		objetoPowerUp.Velocidad(gui, objetoTablero.getMatriz());
	}
	//Si pisas la power up de velocidad la incrementa, refresca su valor y te lo dice
	public void ComprobarVelocidad() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getVelocidad()==true) {
			if(c_vel==0) {
				if(Config.speed<10) {
					Config.speed++;
					gui.gb_println("Your speed has been increased to " + Config.speed);
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
					gui.gb_setValueAbility2(Config.speed);
				}else {
					gui.gb_println("You can't go any faster");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
				}
			}
			c_vel++;
		}
	}

	public void Geta() {
		objetoPowerUp.Geta(gui, objetoTablero.getMatriz());
	}
	//Si pisas la power up de velocidad la reinicia, refresca su valor y te lo dice
	public void ComprobarGeta() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getGeta()==true) {
			if(c_geta==0) {
				Config.speed=1;
				gui.gb_println("Your speed has dropped to 1");
				gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
				gui.gb_setValueAbility2(Config.speed);
			}
			c_geta++;
		}
	}

	public void Bombas() {
		objetoPowerUp.Bombas(gui, objetoTablero.getMatriz());
	}
	public void ComprobarBombas() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getBombas()==true) {
			if(c_bombas==0) {
				if(Config.nbombas<5) {
					Config.nbombas++;
					gui.gb_println("You can now detonate one more bomb");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
					gui.gb_setValuePointsDown(Config.nbombas);
				}else {
					gui.gb_println("You can't detonate any more bombs");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
				}
			}
			c_bombas++;
		}
	}

	public void Alcance() {
		objetoPowerUp.Alcance(gui, objetoTablero.getMatriz());
	}
	public void ComprobarAlcance() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getAlcance()==true) {
			if(c_alcance==0) {
				if(Config.power_up_expansion<4) {
					Config.power_up_expansion++;
					gui.gb_println("Your bomb's range \n has been increased by 1");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
					gui.gb_setValueAbility1(Config.power_up_expansion);
				}else {
					gui.gb_println("Your bombs are already at max range");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
				}
			}
			c_alcance++;
		}
	}
	
	public void Max() {
		objetoPowerUp.Max(gui, objetoTablero.getMatriz());
	}
	public void ComprobarMax() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getMax()==true) {
			if(c_max==0) {
	//			Config.power_up_expansion=5;
	//			gui.gb_println("Your bomb's range has been \n increased to the maximum: 5");
				gui.gb_println("Sorry but this power up is \n not implemented yet");
				gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
	//			gui.gb_setValueAbility1(Config.power_up_expansion);
			}
			c_max++;
		}
	}

	public void Remoto() {
		objetoPowerUp.Remoto(gui, objetoTablero.getMatriz());
	}
	public void ComprobarRemoto() {
		if(objetoTablero.getMatriz()[(objetoPersonaje.getXmuñeco()+5)/10][(objetoPersonaje.getYmuñeco()+5)/10].getRemoto()==true) {
			if(c_remoto==0) {
					Config.remoto=true;
					gui.gb_println("You can now detonate bombs \n by pressing tab");
					gui.gb_setSquareImage((objetoPersonaje.getXmuñeco()+5)/10, (objetoPersonaje.getYmuñeco()+5)/10, null);
			}
			c_remoto++;
		}
	}
	
	public void Consejo() {
		/*Este mensaje existe porque todo el mundo al que 
		 * le pongo el juego tiene problemas 
		 * con la colisión con la bomba y sirve 
		 * a modo de advertencia
		 */
		gui.gb_showMessageDialog("As you can't go through the bomb have in mind \n that your exit sholdn't be in the opposit \n side of the square");
		gui.gb_showMessageDialog("Have fun!");
	}

	public static void main(String[] args) throws InterruptedException{

		Main juego = new Main();

		juego.InicializarBombas();
		juego.Tablero();
		juego.CrearEnemigos();
		juego.PintarJugador();
		juego.Puerta();
		juego.Velocidad();
		juego.Geta();
		juego.Bombas();
		juego.Alcance();
		juego.Max();
		juego.Remoto();
		juego.Consejo();
		
		//Bucle de juego
		while(getVivo()==true) {

			juego.MoverJugador();
			juego.CrearBombas();
			juego.ComprobarDetonacion();
			juego.MoverEnemigos();
			juego.MuerteYDestruccion();
			juego.Contacto();
			juego.Suicidio();
			juego.ComprobarPuerta();
			juego.ComprobarVelocidad();
			juego.ComprobarGeta();
			juego.ComprobarBombas();
			juego.ComprobarAlcance();
			juego.ComprobarMax();
			juego.ComprobarRemoto();

			Config.frames++;
			//Frecuencia de refresco
			Thread.sleep(50L);
		}

	}
}
