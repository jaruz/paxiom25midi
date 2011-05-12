package paxiom25midi;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class CompasApplet extends PApplet {
	public void setup() {
		size(300, 300);
		frameRate(25);
		colorMode(RGB, 255, 255, 255, 100);
	}

	int posicion = 0;
	Intervalo actual;
	Intervalo anterior;
	boolean debug = false;
	int colorActual = 0;

	float tiempo;
	boolean primeraVez = true;
	float inicioSecuencia = 0;
	float inicioIntervalo = 0;

	float tiempoFrame = 0;
	float tiempoTranscurridoSecuencia = 0;
	float tiempoTranscurridoIntervalo = 0;

	public void draw() {
		// fill(0);
		// rect(0, 0, width, height);
		fill(0, 60);
		rect(0, 0, width, height);
		tiempoFrame = millis();

		if (secuencia != null && grabando == false) {
			actual = (Intervalo) secuencia.get(posicion);
			float inicioTime = actual.getTime();
			float finTime = actual.getFin();
			colorActual = actual.getColor();

			if (primeraVez) {
				primeraVez = false;
				tiempo = finTime - inicioTime;
				inicioSecuencia = millis();
				inicioIntervalo = millis();
				cambiaSecuenciaPintado();
			
					pintaIntervalo();
				
			}
			tiempoTranscurridoIntervalo = tiempoFrame - inicioIntervalo;

			tiempoTranscurridoSecuencia = tiempoFrame - inicioSecuencia;
			// println("tiempoTranscurridoSecuencia"+tiempoTranscurridoSecuencia);
			// println("posicion: "+posicion+"    tiempoTranscurridoIntervalo"+tiempoTranscurridoIntervalo);
			int millis = millis();

			debugea(inicioTime, finTime, millis);

			if (tiempoTranscurridoIntervalo <= (finTime - inicioTime)) {
				if (debug)
					println("" + posicion + actual.getClass().getSimpleName());
				pintaIntervalo();

			} else {
				println("pasa posicion" + posicion);
					pintaIntervalo();
				inicioIntervalo = millis;
				if (posicion == secuencia.size() - 1) {
					posicion = 0;
					// println("!!!!!!!!!!!!!+++++++++++++++++++++++++ inicia secuencia");

					inicioSecuencia = 0;
					primeraVez = true;
				} else {

					// println("+++++++++++++++++++++++++ aumentaposicionnnnnnnnnnnnnnnnnnnn");
					posicion++;

					if (debug) {
						println("aumenta posicion");
						println(actual.getClass().getSimpleName());
					}

				}

			}

		}

	}

	private void cambiaSecuenciaPintado() {
		for (int i = 0; i < secuencia.size(); i++) {
			Intervalo intervalo = (Intervalo) secuencia.get(i);
			if(intervalo.isPintado())
			intervalo.cambiaPintado();

		}
	}

	private void debugea(float inicioTime, float finTime, int millis) {
		if (debug) {
			println();

			println("posicion: " + posicion);
			println("inicioTime: " + inicioTime);
			println("finTime: " + finTime);
			println("millis(): " + millis);
			println("inicio: " + inicioSecuencia);
			println("millis()-inicio: " + (millis - inicioSecuencia));
			println("( finTime-inicioTime): " + (finTime - inicioTime));

			println("evaluar---------" + ((finTime - inicioTime) >= (millis - inicioSecuencia)));
		}
	}

	private void pintaIntervalo() {
		if(!actual.isPintado()){
			pintaRectangulo(colorActual);
			actual.cambiaPintado();
		}

		
		
	}

	private void pintaRectangulo(int color) {
		fill(color);

		rect(100, 100, 100, 100);
	}

	boolean grabando;

	List secuencia;
	float inicioGrabacion = 0;

	int azul = color(0, 0, 255);
	int rojo = color(255, 0, 0);
	@Override
	public void keyPressed() {
		if (key == 'a') {
			iniciaGrab();
		} else if (key == 's') {
			iniciaGrab();

			println("tiempo");

			secuencia.add(new Tiempo(millis() - inicioGrabacion, azul));
			pintaRectangulo(azul);

		} else if (key == 'd') {
			iniciaGrab();
			println("acento");
			secuencia.add(new Acento(millis() - inicioGrabacion, rojo));
			pintaRectangulo(rojo);
		} else if (key == 'f') {
			grabando = false;
			actualizaFinales();

			println("fin grabacion");
		}
	}

	private void iniciaGrab() {
		if (secuencia == null) {
			println("comienzo a grabar");
			grabando = true;
			inicioGrabacion = millis();
			secuencia = new ArrayList();
		}
	}

	private void actualizaFinales() {
		float fin = millis();
		for (int i = 0; i < secuencia.size(); i++) {
			Intervalo intervalo = (Intervalo) secuencia.get(i);
			if (i != secuencia.size() - 1) {
				Intervalo siguiente = (Intervalo) secuencia.get(i + 1);
				intervalo.setFin(siguiente.getTime());
			} else {
				intervalo.setFin(fin - inicioGrabacion);
			}
		}
	}

	interface Intervalo {
		float getTime();

		void setFin(float fin);

		float getFin();

		int getColor();

		void cambiaPintado();

		boolean isPintado();
	}

	class Tiempo implements Intervalo {
		float time;
		float fin;
		int color;

		public Tiempo(float time, int color) {
			super();
			this.time = time;
			this.color = color;
		}

		public float getTime() {
			return time;
		}

		@Override
		public String toString() {
			return "Tiempo [time=" + time + "]";
		}

		public void setFin(float fin) {
			this.fin = fin;

		}

		public float getFin() {
			return fin;
		}

		public int getColor() {
			return color;
		}

		boolean pintado;

		public boolean isPintado() {
			return pintado;
		}

		public void cambiaPintado() {
			if (pintado)
				pintado = false;
			else
				pintado = true;

		}

	}

	class Acento implements Intervalo {
		float time;
		boolean pintado;

		public boolean isPintado() {
			return pintado;
		}

		float fin;
		int color;

		public int getColor() {
			return color;
		}

		public void cambiaPintado() {
			if (pintado)
				pintado = false;
			else
				pintado = true;

		}

		public Acento(float time, int color) {
			super();
			this.time = time;
			this.color = color;
		}

		public float getTime() {
			return time;
		}

		@Override
		public String toString() {
			return "Acento [time=" + time + "]";
		}

		public void setFin(float fin) {
			this.fin = fin;

		}

		public float getFin() {
			return fin;
		}

	}

}
