package paxiom25midi;

import javax.sound.midi.MidiMessage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import themidibus.*;

public class Paxiom25 implements PConstants, StandardMidiListener {
	PApplet parent;
	MidiBus myBus;

	int[] idsCirculares = { 72, 8, 74, 71, 20, 22, 86, 73 };
	ControladorCircular[] circulares = new ControladorCircular[idsCirculares.length];

	ControladorTecla[] teclas = new ControladorTecla[15];
	int[] idTeclas = { 42, 43, 45, 47, 49, 50, 52, 54, 55, 57 };

	int[] idTeclasNegras = { 44, 46, 48, 51, 53, 56, 58 };
	ControladorTecla[] teclasNegras = new ControladorTecla[10];

	int[] idsTaps = { 50, 45, 51, 49, 36, 38, 46, 42 };
	ControladorTecla[] taps = new ControladorTecla[8];
	PFont fontA;

	public Paxiom25(PApplet parent) {
		super();
		this.parent = parent;

		fontA = parent.createFont("Arial", 15, true);
		parent.textFont(fontA, 15);
		parent.size(800, 600);
		parent.background(0);

		int _y = 100;
		int _x = 30;
		int margen = 10;
		int contadorFila = 0;
		int margenInicialX = 90;
		for (int i = 0; i < 8; i++) {

			if (i == 4) {
				_y = 200;
				contadorFila = 0;
			}
			int posicionX = (_x * (contadorFila + 1) + margen * contadorFila);
			circulares[i] = new ControladorCircular(posicionX + margenInicialX, _y, idsCirculares[i]);
			contadorFila++;
		}

		int contadorIdsTaps = 0;
		int posiX = 500;
		int posiY = 100;
		for (int p = 0; p < taps.length; p++) {

			int idTap = idsTaps[contadorIdsTaps];
			contadorIdsTaps++;
			int ancho = 50;

			if (p == 4) {
				posiY = 100 + ancho + ancho / 5;
				posiX = 500;
			}
			taps[p] = new ControladorTecla(posiX, posiY, false, idTap, parent.color(150), ancho, ancho);
			posiX += ancho + ancho / 5;
		}

		int contadorIdsTecla = 0;
		for (int t = 0; t < teclas.length; t++) {
			int posicionX = 50 * t;
			boolean disabled;
			int idTecla = 0;
			if (t < 2 || t > 11) {
				disabled = true;
			} else {
				disabled = false;
				idTecla = idTeclas[contadorIdsTecla];
				contadorIdsTecla++;
			}
			teclas[t] = new ControladorTecla(posicionX, 300, disabled, idTecla, parent.color(200), 200, 40);
		}

		int contadorIdsTeclasNegras = 0;
		int posicionX = 0;
		for (int r = 0; r < teclasNegras.length; r++) {

			boolean disabled;
			int idTeclaNegra = 0;
			if (r < 2 || r > 8) {
				disabled = true;
			} else {
				disabled = false;
				idTeclaNegra = idTeclasNegras[contadorIdsTeclasNegras];
				contadorIdsTeclasNegras++;
			}
			if (r == 2 || r == 5 || r == 7)
				posicionX += 50;
			teclasNegras[r] = new ControladorTecla(posicionX + 35, 300, disabled, idTeclaNegra, parent.color(0), 150, 20);
			posicionX += 50;
		}

		myBus = new MidiBus(null, 0, 0); // Create a new MidiBus object
		myBus.addMidiListener(this);
		parent.smooth();
		parent.registerDispose(this);
	}

	public void dibuja() {
		parent.fill(255, 0, 0);
		parent.rect(0, 0, 100, 100);
	}

	public void draw() {
		parent.fill(0);
		parent.rect(0, 0, parent.width, parent.height);

		for (int i = 0; i < circulares.length; i++) {
			circulares[i].display();
		}

		for (int t = 0; t < teclas.length; t++) {
			teclas[t].display();
		}

		for (int r = 0; r < teclasNegras.length; r++) {
			teclasNegras[r].display();
		}

		for (int p = 0; p < taps.length; p++) {
			taps[p].display();
		}

	}

	final int tipoControladorCircular = 176;
	final int tipoControladorNotaPresionada = 144;
	final int tipoControladorNotaSoltada = 128;
	final int tipoControladorTap = 153;

	public void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data, String
								// bus_name)
		// Receive some raw data

		// data[0] will be the status byte
		// data[1] and data[2] will contain the parameter of the message (e.g.
		// pitch and volume for noteOn noteOff)
		parent.println();
		parent.println("Raw Midi Data:");
		parent.println("--------");
		parent.println("Status Byte/MIDI Command:" + (int) (data[0] & 0xFF));
		// N.B. In some cases (noteOn, noteOff, controllerChange, etc) the first
		// half of the status byte is the command and the second half i

		// In these cases (data[0] & 0xF0) gives you the command and (data[0] &
		// 0x0F) gives you the channel

		int tipoControlador = dameValorMidi(data[0]);
		int idControlador = dameValorMidi(data[1]);
		switch (tipoControlador) {

		case tipoControladorCircular:

			int valorControlador = dameValorMidi(data[2]);

			actualizaControladorCircular(idControlador, valorControlador);

			break;
		case tipoControladorNotaPresionada:
			actualizaControladorTecla(idControlador, true);
			break;
		case tipoControladorNotaSoltada:
			actualizaControladorTecla(idControlador, false);
			break;
		case tipoControladorTap:
			valorControlador = dameValorMidi(data[2]);
			actualizaControladorTap(idControlador, valorControlador);
			break;
		}

		for (int i = 1; i < data.length; i++) {
			parent.println("Param " + (i + 1) + ": " + dameValorMidi(data[i]));
		}
	}

	void actualizaControladorTecla(int idControlador, boolean pulsada) {
		for (int i = 0; i < teclas.length; i++) {
			if (teclas[i].idTecla == idControlador) {
				teclas[i].actualiza(pulsada);
				return;

			}

		}
		for (int i = 0; i < teclasNegras.length; i++) {
			if (teclasNegras[i].idTecla == idControlador) {
				teclasNegras[i].actualiza(pulsada);
				return;

			}

		}

	}

	void actualizaControladorTap(int idControlador, int valorControlador) {
		for (int i = 0; i < taps.length; i++) {

			if (taps[i].idTecla == idControlador) {
				boolean valor = false;
				if (valorControlador > 0)
					valor = true;
				taps[i].actualiza(valor);
			}
		}

	}

	void actualizaControladorCircular(int idControlador, int valorControlador) {
		for (int i = 0; i < circulares.length; i++) {
			if (circulares[i].idControlador == idControlador) {
				circulares[i].actualiza(valorControlador);
			}

		}

	}

	int dameValorMidi(byte b) {
		return (int) (b & 0xFF);
	}

	public void dispose() {
		// anything in here will be called automatically when
		// the parent applet shuts down. for instance, this might
		// shut down a thread used by this library.
	}

	class ControladorTecla {
		int x;
		int y;
		int col;
		int ancho;
		int alto;
		boolean valor = false;
		boolean disabled = false;
		public int idTecla;
		int col_ref;

		public ControladorTecla(int _x, int _y, boolean _disabled, int _idTecla, int _color, int _altura, int _anchura) {
			x = _x;
			idTecla = _idTecla;
			y = _y;
			col = _color;
			col_ref = _color;
			ancho = _anchura;
			alto = _altura;
			disabled = _disabled;
		}

		void display() {
			parent.pushStyle();

			parent.textAlign(CENTER);
			if (!disabled) {
				parent.stroke(200);

				parent.fill(col);

				// text(""+valor, x+ancho/2, y+alto+20);
				parent.fill(col);

			} else {
				parent.stroke(30);
				parent.fill(30);
			}

			parent.rect(x, y, ancho, alto);
			parent.popStyle();
		}

		void actualiza(boolean valorNuevo) {
			valor = valorNuevo;
			if (valor)
				col = parent.color(255, 0, 0);
			else
				col = col_ref;
		}

	}

	class ControladorCircular {
		int x;
		int y;
		int col;
		int diametro;
		int valor = 0;
		public int idControlador;

		public ControladorCircular(int _x, int _y, int _idControlador) {
			x = _x;
			y = _y;
			col = parent.color(100);
			diametro = 30;
			idControlador = _idControlador;
		}

		void display() {
			parent.pushStyle();

			parent.textAlign(CENTER);
			parent.fill(255);
			parent.text(valor, x, y + diametro + 3);
			if (valor > 0) {
				parent.stroke(150);
				parent.fill(parent.map(valor, 0, 127, 50, 256), 0, 0);
			} else {
				parent.stroke(30);
				parent.fill(20);
			}
			parent.ellipse(x, y, diametro, diametro);
			parent.popStyle();
		}

		void actualiza(int valorNuevo) {
			valor = valorNuevo;
		}

	}

	@Override
	public void midiMessage(MidiMessage message, long timeStamp) {
		rawMidi(message.getMessage());
		
	}

}
