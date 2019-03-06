import edu.uc3m.game.GameBoardGUI;

public class Bomba {
	private int xbomba;
	private int ybomba;
	private int id=300000;
	private int id_ant=500;
	private int id_ant_iz;
	private int id_ant_der;
	private int id_ant_hor_iz;
	private int id_ant_hor_der;
	private int id_ant_ab;
	private int id_ant_arr;
	private int id_ant_ver_ab;
	private int id_ant_ver_arr;
	private int minId;
	private int contador=0;
	private int nexplosion=0;
	private long tiempo_inicial;
	private boolean creada = false;
	private int i = 0;
	private boolean detonacion;
	private boolean mostrar_iz=true;
	private boolean mostrar_der=true;
	private boolean mostrar_ar=true;
	private boolean mostrar_ab=true;

	//Constructor de la bomba
	public Bomba(int xbomba, int ybomba, GameBoardGUI gui, Bomba[]arrayBomba) {
		this.xbomba = xbomba;
		this.ybomba = ybomba;
		sprite(gui);
		mostrar(500, xbomba, ybomba, gui, arrayBomba);
		tiempo_inicial= System.currentTimeMillis();
	}
	//Creamos un timer para que cambie los sprites la bomba
	public void preBoom(GameBoardGUI gui, Bomba[]arrayBomba) {
		if(contador>=2) {
			contador=0;
		}
		mostrar(contador+minId, getXbomba(), getYbomba(), gui, arrayBomba);
		gui.gb_setSpriteVisible(id_ant, false);
		id_ant=contador+minId;
		contador++;		
	}
	//Creamos un timer para que explote la bomba
	public void Boom(GameBoardGUI gui, Casillas [][] matriz, Enemigo [] arrayEnemigos, Bomba[]arrayBomba) {
		if(i>=4) {
			i=0;
		}
		/*Para el restp de tamaños haría lo mismo variando el 
		 * Config.power_up-n y creando nuevas variables de id anterior, 
		 * no lo hago por falta de tiempo
		 */
		if(Config.power_up_expansion-1>0) {
			//10 para horizontal
			if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getRompible()==true) {
				mostrar(i+minId+10, getXbomba()+(Config.power_up_expansion-1), getYbomba(), gui, arrayBomba);
				matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].setFuego(true);
				//Compruebo si la puedo romper o no
				if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getTraspasable()==false) {
					if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "DoorClosed.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Skatesprite.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Getasprite.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Bombupsprite.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Fireupsprite.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getMax()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Fullfiresprite.png");
					}else if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba()+(Config.power_up_expansion-1), getYbomba(), null);
					}
					matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].setTraspasable(true);
				}
				//Destruye los sprites anteriores desde el segundo
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_hor_der, false);
				}
				id_ant_hor_der=i+minId+10;
			}else {
				mostrar_der=false;
			}
			//30 para horizontal izquierda
			if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getRompible()==true) {
				mostrar(i+minId+30, getXbomba()-(Config.power_up_expansion-1), getYbomba(), gui, arrayBomba);
				matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].setFuego(true);
				if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getTraspasable()==false) {
					if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "DoorClosed.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Skatesprite.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Getasprite.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Bombupsprite.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Fireupsprite.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getMax()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Fullfiresprite.png");
					}else if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba()-(Config.power_up_expansion-1), getYbomba(), null);
					}
					matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].setTraspasable(true);
				}
				//Destruye los sprites anteriores desde el segundo
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_hor_iz, false);
				}
				id_ant_hor_iz=i+minId+30;
			}else {
				mostrar_iz=false;
			}
			//22 para vertical
			if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getRompible()==true) {
				matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].setFuego(true);
				mostrar(i+minId+22, getXbomba(), getYbomba()-(Config.power_up_expansion-1), gui, arrayBomba);
				if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getTraspasable()==false) {
					if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "DoorClosed.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Skatesprite.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Getasprite.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Bombupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Fireupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getMax()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Fullfiresprite.png");
					}else if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-(Config.power_up_expansion-1), null);
					}
					matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].setTraspasable(true);
				}
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_ver_arr, false);
				}
				id_ant_ver_arr=i+minId+22;
			}else {
				mostrar_ar=false;
			}
			//34 para vertical abajo
			if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getRompible()==true) {
				matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].setFuego(true);
				mostrar(i+minId+34, getXbomba(), getYbomba()+(Config.power_up_expansion-1), gui, arrayBomba);
				if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getTraspasable()==false) {
					if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "DoorClosed.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Skatesprite.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Getasprite.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Bombupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Fireupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getMax()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Fullfiresprite.png");
					}else if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+(Config.power_up_expansion-1), null);
					}
					matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].setTraspasable(true);
				}
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_ver_ab, false);
				}
				id_ant_ver_ab=i+minId+34;
			}else {
				mostrar_ab=false;
			}
		}
		
		/*Muestro primero los del power up por si hubiera 
		 * un obstáculo que no se mostrasen los extremos
		 */
		
		//2 para central
		matriz[getXbomba()][getYbomba()].setFuego(true);
		mostrar(i+minId+2, getXbomba(), getYbomba(), gui, arrayBomba);
		gui.gb_setSpriteVisible(id_ant, false);
		id_ant=i+minId+2;
		if(getXbomba()+Config.power_up_expansion<16) {
			//6 para derecha
			if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getRompible()==true && mostrar_der==true) {
				mostrar(i+minId+6, getXbomba()+Config.power_up_expansion, getYbomba(), gui, arrayBomba);
				matriz[getXbomba()+Config.power_up_expansion][getYbomba()].setFuego(true);
				if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getTraspasable()==false) {	
					//Elimina la imagen de los ladrillos y las hace traspasables si coinciden las coordenadas
					if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "DoorClosed.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Skatesprite.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Getasprite.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Bombupsprite.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Fireupsprite.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getMax()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Fulllfiresprite.png");
					}else if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba()+Config.power_up_expansion, getYbomba(), null);
					}
					matriz[getXbomba()+Config.power_up_expansion][getYbomba()].setTraspasable(true);
				}
				//Destruye los sprites anteriores desde el segundo
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_der, false);
				}
				id_ant_der=i+minId+6;
			}
		}
		if(getYbomba()-Config.power_up_expansion>0) {
			//14 para arriba
			if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getRompible()==true && mostrar_ar==true) {
				matriz[getXbomba()][getYbomba()-Config.power_up_expansion].setFuego(true);
				mostrar(i+minId+14, getXbomba(), getYbomba()-Config.power_up_expansion, gui, arrayBomba);
				if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getTraspasable()==false) {
					if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "DoorClosed.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Skatesprite.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Getasprite.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Bombupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Fireupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getMax()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Fullfiresprite.png");
					}else if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba(), getYbomba()-Config.power_up_expansion, null);
					}
					matriz[getXbomba()][getYbomba()-Config.power_up_expansion].setTraspasable(true);
				}
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_arr, false);
				}
				id_ant_arr=i+minId+14;
			}
		}
		if(getYbomba()+Config.power_up_expansion<16) {
			//18 para abajo
			if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getRompible()==true&&mostrar_ab==true) {
				matriz[getXbomba()][getYbomba()+Config.power_up_expansion].setFuego(true);
				mostrar(i+minId+18, getXbomba(), getYbomba()+Config.power_up_expansion, gui, arrayBomba);
				if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getTraspasable()==false) {
					if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "DoorClosed.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Skatesprite.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Getasprite.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Bombupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Fireupsprite.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getMax()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Fullfiresprite.png");
					}else if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba(), getYbomba()+Config.power_up_expansion, null);
					}
					matriz[getXbomba()][getYbomba()+Config.power_up_expansion].setTraspasable(true);
				}
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_ab, false);
				}
				id_ant_ab=i+minId+18;
			}
		}
		if(getXbomba()-Config.power_up_expansion>0) {
			//26 para izquierda
			if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getRompible()==true&&mostrar_iz==true) {
				matriz[getXbomba()-Config.power_up_expansion][getYbomba()].setFuego(true);
				mostrar(i+minId+26, getXbomba()-Config.power_up_expansion, getYbomba(), gui, arrayBomba);
				if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getTraspasable()==false) {
					if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getPuerta()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "DoorClosed.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getVelocidad()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Skatesprite.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getGeta()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Getasprite.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getBombas()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Bombupsprite.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getAlcance()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Fireupsprite.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getMax()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Fullfiresprite.png");
					}else if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getRemoto()==true) {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), "Remote_Control_2.png");
					}else {
						gui.gb_setSquareImage(getXbomba()-Config.power_up_expansion, getYbomba(), null);
					}
					matriz[getXbomba()-Config.power_up_expansion][getYbomba()].setTraspasable(true);
				}
				if(nexplosion>0) {
					gui.gb_setSpriteVisible(id_ant_iz, false);
				}
				id_ant_iz=i+minId+26;
			}
		}
		i++;
		nexplosion++;
	}
	//Método que maneja la explosión
	public void todo(Casillas matriz[][], GameBoardGUI gui, Enemigo [] arrayEnemigos, Bomba[]arrayBomba) {
		long tiempo_actual = System.currentTimeMillis();
		long tiempo_espera= 2000L;

		if(Config.remoto==true&&Config.detonada==true) {
			detonacion=true;
		}

		if((Config.remoto==true && detonacion==false) || tiempo_actual-tiempo_inicial<tiempo_espera) {
			//Primero hace la bomba no traspasable y muestra los sprites de vibración
			matriz[getXbomba()][getYbomba()].setTraspasable(false);
			preBoom(gui, arrayBomba);
		}else if ((Config.remoto==true && detonacion==true) || (tiempo_actual-tiempo_inicial>=tiempo_espera && tiempo_actual-tiempo_inicial<3000L)) {
			//Muestra los sprites de la explosión
			Boom(gui, matriz, arrayEnemigos, arrayBomba);
		} if(tiempo_actual-tiempo_inicial>=3000) {
			//Destruye el último sprite central
			gui.gb_setSpriteVisible(id_ant, false);
			//Vuelve a hacer traspasable la casilla donde se puso la bomba
			matriz[getXbomba()][getYbomba()].setTraspasable(true);
			matriz[getXbomba()][getYbomba()].setFuego(false);
			if(getXbomba()-Config.power_up_expansion>0) {
				//Destruye el último sprite izquierdo si han sido mostrados
				if(matriz[getXbomba()-Config.power_up_expansion][getYbomba()].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_iz, false);
					matriz[getXbomba()-Config.power_up_expansion][getYbomba()].setFuego(false);
				}
			}
			if(getXbomba()+Config.power_up_expansion<16) {
				//Destruye el último sprite derecho si han sido mostrados
				if(matriz[getXbomba()+Config.power_up_expansion][getYbomba()].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_der, false);
					matriz[getXbomba()+Config.power_up_expansion][getYbomba()].setFuego(false);
				}
			}
			if(getYbomba()-Config.power_up_expansion>0) {
				//Destruye el último sprite superior si han sido mostrados
				if(matriz[getXbomba()][getYbomba()-Config.power_up_expansion].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_arr, false);
					matriz[getXbomba()][getYbomba()-Config.power_up_expansion].setFuego(false);
				}
			}
			if(getYbomba()+Config.power_up_expansion<16) {
				//Destruye el último sprite inferior si han sido mostrados
				if(matriz[getXbomba()][getYbomba()+Config.power_up_expansion].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_ab, false);
					matriz[getXbomba()][getYbomba()+Config.power_up_expansion].setFuego(false);
				}
			}
			if(Config.power_up_expansion-1>0) {
				//Destruye el último sprite izquierdo si han sido mostrados
				if(matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_hor_iz, false);
					matriz[getXbomba()-(Config.power_up_expansion-1)][getYbomba()].setFuego(false);
				}
				//Destruye el último sprite derecho si han sido mostrados
				if(matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_hor_der, false);
					matriz[getXbomba()+(Config.power_up_expansion-1)][getYbomba()].setFuego(false);
				}
				//Destruye el último sprite superior si han sido mostrados
				if(matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_ver_arr, false);
					matriz[getXbomba()][getYbomba()-(Config.power_up_expansion-1)].setFuego(false);
				}
				//Destruye el último sprite superior si han sido mostrados
				if(matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].getRompible()==true) {
					gui.gb_setSpriteVisible(id_ant_ver_ab, false);
					matriz[getXbomba()][getYbomba()+(Config.power_up_expansion-1)].setFuego(false);
				}
			}
			setCreada(false);
			Config.nbombas++;
			gui.gb_setValuePointsDown(Config.nbombas);
		}
	}
	//Cargamos todos los sprites de las bombas
	private void sprite(GameBoardGUI gui) {
		minId=id;
		//Bomba
		gui.gb_addSprite(500, "bomb1.gif", false);
		gui.gb_addSprite(id++, "bomb1.gif", false);
		gui.gb_addSprite(id++, "bomb2.gif", false);
		//Explosión central
		gui.gb_addSprite(id++, "explosion_C1.gif", false);
		gui.gb_addSprite(id++, "explosion_C2.gif", false);
		gui.gb_addSprite(id++, "explosion_C3.gif", false);
		gui.gb_addSprite(id++, "explosion_C4.gif", false);
		//Explosión extremo derecha
		gui.gb_addSprite(id++, "explosion_E1.gif", false);
		gui.gb_addSprite(id++, "explosion_E2.gif", false);
		gui.gb_addSprite(id++, "explosion_E3.gif", false);
		gui.gb_addSprite(id++, "explosion_E4.gif", false);
		//Explosión horizontal
		gui.gb_addSprite(id++, "explosion_H1.gif", false);
		gui.gb_addSprite(id++, "explosion_H2.gif", false);
		gui.gb_addSprite(id++, "explosion_H3.gif", false);
		gui.gb_addSprite(id++, "explosion_H4.gif", false);
		//Explosión extremo arriba
		gui.gb_addSprite(id++, "explosion_N1.gif", false);
		gui.gb_addSprite(id++, "explosion_N2.gif", false);
		gui.gb_addSprite(id++, "explosion_N3.gif", false);
		gui.gb_addSprite(id++, "explosion_N4.gif", false);
		//Explosión extremo abajo
		gui.gb_addSprite(id++, "explosion_S1.gif", false);
		gui.gb_addSprite(id++, "explosion_S2.gif", false);
		gui.gb_addSprite(id++, "explosion_S3.gif", false);
		gui.gb_addSprite(id++, "explosion_S4.gif", false);
		//Explosión vertical
		gui.gb_addSprite(id++, "explosion_V1.gif", false);
		gui.gb_addSprite(id++, "explosion_V2.gif", false);
		gui.gb_addSprite(id++, "explosion_V3.gif", false);
		gui.gb_addSprite(id++, "explosion_V4.gif", false);
		//Explosión extremo izquierda
		gui.gb_addSprite(id++, "explosion_W1.gif", false);
		gui.gb_addSprite(id++, "explosion_W2.gif", false);
		gui.gb_addSprite(id++, "explosion_W3.gif", false);
		gui.gb_addSprite(id++, "explosion_W4.gif", false);
		//Explosión horizontal
		gui.gb_addSprite(id++, "explosion_H1.gif", false);
		gui.gb_addSprite(id++, "explosion_H2.gif", false);
		gui.gb_addSprite(id++, "explosion_H3.gif", false);
		gui.gb_addSprite(id++, "explosion_H4.gif", false);
		//Explosión vertical
		gui.gb_addSprite(id++, "explosion_V1.gif", false);
		gui.gb_addSprite(id++, "explosion_V2.gif", false);
		gui.gb_addSprite(id++, "explosion_V3.gif", false);
		gui.gb_addSprite(id++, "explosion_V4.gif", false);
	}
	//Método que muestra cada sprite de las bombas
	public void mostrar(int id, int x, int y, GameBoardGUI gui, Bomba[]arrayBomba) {
		gui.gb_moveSprite(id, x, y);
		for(int i = 0 ; i<arrayBomba.length;i++) {
			if(arrayBomba[i]!=null) {
				gui.gb_setSpriteVisible(id, true);
			}
		}
	}
	//Getters de la posición de la bomba
	public int getXbomba() {
		return xbomba;
	}
	public int getYbomba() {
		return ybomba;
	}
	//Getters y Setters de creación de la bomba
	public boolean getCreada() {
		return creada;
	}
	public void setCreada(boolean creada) {
		this.creada = creada;
	}
}
