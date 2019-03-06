
public class Config {

	public static int nbombas = 1;
	
	//Creo un número aleatorio de enemigos
	public static int nenemigos = (int)(Math.random()*10+1);
	
	//Timer de actualizaión / velocidad
	public static long tiempo_inicial = System.currentTimeMillis();
	public static long tiempo_actual = System.currentTimeMillis();
	public static long tiempo_espera= 100L;
	
	public static int frames=0;

	public static int speed=2;

	public static int points=0;

	public static int health=50;
	

	public static int power_up_expansion=1;
	
	public static boolean remoto=false;
	public static boolean detonada=false;
}
