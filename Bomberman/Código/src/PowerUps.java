import edu.uc3m.game.GameBoardGUI;

public class PowerUps {

	private int probabilidad;
	private int fil_bombas = (int)(Math.random()*15+1);
	private int col_bombas = (int)(Math.random()*15+1);
	private int fil_alcance = (int)(Math.random()*15+1);
	private int col_alcance = (int)(Math.random()*15+1);
	private int fil_max = (int)(Math.random()*15+1);
	private int col_max = (int)(Math.random()*15+1);
	private int fil_remoto = (int)(Math.random()*15+1);
	private int col_remoto = (int)(Math.random()*15+1);
	private int fil_velocidad = (int)(Math.random()*15+1);
	private int col_velocidad = (int)(Math.random()*15+1);
	private int fil_geta = (int)(Math.random()*15+1);
	private int col_geta = (int)(Math.random()*15+1);
	private int fil_puerta = (int)(Math.random()*15+1);
	private int col_puerta = (int)(Math.random()*15+1);
	private boolean creada=false;

	/*Como los power ups manejan probabilidades, se crea un número entre 1 y 10 y 
	 * si la probabilidad es del 10% sólo hay una opción, si es del 20% hay dos*/

	//Método que crea una posición aleatoria para la puerta con la condición de que haya un ladrillo
	public void Puerta(GameBoardGUI gui, Casillas[][] matriz) {
		do {
			if(matriz[fil_puerta][col_puerta].getRompible()==true && matriz[fil_puerta][col_puerta].getTraspasable()==false) {
				creada=true;
				matriz[fil_puerta][col_puerta].setPuerta(true);
			}
			else {
				fil_puerta = (int)(Math.random()*15+1);
				col_puerta = (int)(Math.random()*15+1);
			}
		}while(creada==false);
	}

	//Añade la condición de que esa posición no haya sido utilizada ya por otra power up
	public void Alcance(GameBoardGUI gui, Casillas[][] matriz) {	
		creada = false;
		do {
			if(matriz[fil_alcance][col_alcance].getRompible()==true && matriz[fil_alcance][col_alcance].getTraspasable()==false && (fil_alcance != fil_puerta || col_alcance != col_puerta)) {
				creada=true;
				matriz[fil_alcance][col_alcance].setAlcance(true);
			}
			else {
				fil_alcance = (int)(Math.random()*15+1);
				col_alcance = (int)(Math.random()*15+1);
			}
		}while(creada==false);
	}

	/*En las normas dice que la velocidad sólo aparece en los niveles pares 
	 * (que se haría calculando nivel%2==0), pero como no hemos	hecho más niveles 
	 * lo hemos incluido aqui para que se vean sus efectos, otra restricción
	 * es que la velocidad no puede superar 10, es decir, una casilla de cada vez*/
	public void Velocidad(GameBoardGUI gui, Casillas[][] matriz) {
		creada = false;
		probabilidad = (int)(Math.random()*10+1);
		if(probabilidad==3) {
			do {
				if(matriz[fil_velocidad][col_velocidad].getRompible()==true && matriz[fil_velocidad][col_velocidad].getTraspasable()==false && (fil_velocidad != fil_puerta || col_velocidad != col_puerta) && (fil_alcance != fil_velocidad || col_alcance != col_velocidad)) {
					creada=true;
					matriz[fil_velocidad][col_velocidad].setVelocidad(true);
				}
				else {
					fil_velocidad = (int)(Math.random()*15+1);
					col_velocidad = (int)(Math.random()*15+1);
				}
			}while(creada==false);
		}
	}

	public void Geta(GameBoardGUI gui, Casillas[][] matriz) {	
		creada = false;
		probabilidad = (int)(Math.random()*10+1);
		if(probabilidad==5||probabilidad==8) {
			do {
				if(matriz[fil_geta][col_geta].getRompible()==true && matriz[fil_geta][col_geta].getTraspasable()==false && (fil_alcance != fil_geta || col_alcance != col_geta) && (fil_puerta != fil_geta || col_puerta != col_geta) && (fil_velocidad!= fil_geta || col_velocidad!=col_geta)) {
					creada=true;
					matriz[fil_geta][col_geta].setGeta(true);
				}
				else {
					fil_geta = (int)(Math.random()*15+1);
					col_geta = (int)(Math.random()*15+1);
				}
			}while(creada==false);
		}
	}

	public void Bombas(GameBoardGUI gui, Casillas[][] matriz) {	
		creada = false;
		do {
			if(matriz[fil_bombas][col_bombas].getRompible()==true && matriz[fil_bombas][col_bombas].getTraspasable()==false && (fil_alcance != fil_bombas || col_alcance != col_bombas) && (fil_puerta != fil_bombas || col_puerta != col_bombas) && (fil_velocidad!= fil_bombas || col_velocidad!=col_bombas)&&(fil_geta!=fil_bombas||col_geta!=col_bombas)) {
				creada=true;
				matriz[fil_bombas][col_bombas].setBombas(true);
			}
			else {
				fil_bombas = (int)(Math.random()*15+1);
				col_bombas = (int)(Math.random()*15+1);
			}
		}while(creada==false);	
	}

	public void Max(GameBoardGUI gui, Casillas[][] matriz) {	
		creada = false;
		probabilidad = (int)(Math.random()*10+1);
		//En el juego completo habría que añadir la condición de que solo saliera cada 10 niveles
		if(probabilidad==6||probabilidad==4) {
			do {
				if(matriz[fil_max][col_max].getRompible()==true && matriz[fil_max][col_max].getTraspasable()==false && (fil_alcance != fil_max || col_alcance != col_max) && (fil_puerta != fil_max || col_puerta != col_max) && (fil_velocidad!= fil_max || col_velocidad!=col_max) && (fil_geta!= fil_max || col_geta!=col_max) && (fil_bombas!= fil_max || col_bombas!=col_max)) {
					creada=true;
					matriz[fil_max][col_max].setMax(true);
				}
				else {
					fil_max = (int)(Math.random()*15+1);
					col_max = (int)(Math.random()*15+1);
				}
			}while(creada==false);
		}
	}
	
	public void Remoto(GameBoardGUI gui, Casillas[][] matriz) {	
		creada = false;
		probabilidad = (int)(Math.random()*10+1);
		if(probabilidad==7) {
			do {
				if(matriz[fil_remoto][col_remoto].getRompible()==true && matriz[fil_remoto][col_remoto].getTraspasable()==false && (fil_alcance != fil_remoto || col_alcance != col_remoto) && (fil_puerta != fil_remoto || col_puerta != col_remoto) && (fil_velocidad!= fil_remoto || col_velocidad!=col_remoto) && (fil_geta!= fil_remoto || col_geta!=col_remoto) && (fil_bombas!= fil_remoto || col_bombas!=col_remoto) && (fil_max != fil_remoto || col_max != col_remoto)) {
					creada=true;
					matriz[fil_remoto][col_remoto].setRemoto(true);
				}
				else {
					fil_remoto = (int)(Math.random()*15+1);
					col_remoto = (int)(Math.random()*15+1);
				}
			}while(creada==false);
		}
	}

}
