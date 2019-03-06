import edu.uc3m.game.GameBoardGUI;

public class Tablero {

	// Creamos una matriz de Casillas
	private Casillas Matriz[][];

	//Construimos todo el tablero
	public void todoTablero(GameBoardGUI gui)  {
		gui.gb_println("WUBBA LUBBA DUB DUB!!");
		gui.setVisible(true);

		int tam = 17;

		Matriz= new Casillas[tam][tam];

		//Creamos una matriz de casillas
		for (int f = 0; f < Matriz.length; f++) {
			for (int i = 0; i < Matriz.length; i++) {
				Matriz[f][i]=new Casillas(true,true);	
			}
		}

		//Creamos un número aleatorio de ladrillos por encima de 30 y por debajo de 50
		int nladrillos = (int) (Math.random()*50+1);

		do {
			nladrillos = (int) (Math.random()*50+1);
		}while(nladrillos<30);
		//Recorremos todas las posiciones del tablero
		for(int i = 0 ; i < tam ; i++) {
			for(int j = 0 ; j < tam ; j++) {

				//Coloreamos todas las casillas rositas por defecto
				gui.gb_setSquareColor(i, j, 248, 185, 192);
				//Colocamos los muros donde queramos
				if((i%2==0) && (j%2==0)) {
					Matriz[i][j].setTraspasable(false);
					Matriz[i][j].setRompible(false);
					gui.gb_setSquareImage(i, j, "wall.gif");
				}
				else {
					Matriz[0][j].setTraspasable(false);
					Matriz[0][j].setRompible(false);
					gui.gb_setSquareImage(0, j, "wall.gif");
					Matriz[i][0].setTraspasable(false);
					Matriz[i][0].setRompible(false);
					gui.gb_setSquareImage(i, 0, "wall.gif");
					Matriz[tam-1][j].setTraspasable(false);
					Matriz[tam-1][j].setRompible(false);
					gui.gb_setSquareImage(tam-1, j, "wall.gif");
					Matriz[i][tam-1].setTraspasable(false);
					Matriz[i][tam-1].setRompible(false);
					gui.gb_setSquareImage(i, tam-1, "wall.gif");
				}
				//Creamos localizaciones aleatorias para los ladrillos
				int fil_ladr = (int)(Math.random()*(tam-1));
				int col_ladr = (int)(Math.random()*(tam-1));
				//Si la posición creada no es de una pared colocamos un ladrillo
				if(Matriz[fil_ladr][col_ladr].getTraspasable()==true && (fil_ladr > 2 || col_ladr > 2) && nladrillos>0) {
					Matriz[fil_ladr][col_ladr].setTraspasable(false);
					Matriz[fil_ladr][col_ladr].setRompible(true);
					gui.gb_setSquareImage(fil_ladr, col_ladr, "bricks.gif");
					nladrillos--;
				}
			}
		}

		//Ponemos la imagen chachi esa de la demo
		gui.gb_setPortraitPlayer("rick.png");
		//Ponemos nombres a las habilidades
		gui.gb_setTextAbility1("Range");
		gui.gb_setValueAbility1(Config.power_up_expansion);
		gui.gb_setTextAbility2("Speed");
		gui.gb_setValueAbility2(Config.speed);
		gui.gb_setValueHealthCurrent(Config.health);
		gui.gb_setValueHealthMax(50);
		gui.gb_setTextPointsDown("Bombs");
		gui.gb_setValuePointsDown(Config.nbombas);
		gui.gb_setTextPointsUp("Points");
		gui.gb_setValuePointsUp(Config.points);
		gui.gb_setValueLevel(1);

	}

	//Getter de la matriz de casillas para el main
	public Casillas[][] getMatriz() {
		return Matriz;
	}
}
