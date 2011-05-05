package paxiom25midi;

import java.lang.reflect.Method;

import javax.sound.midi.MidiMessage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import themidibus.MidiBus;
import themidibus.StandardMidiListener;

public class Paxiom25Midi implements PConstants, StandardMidiListener {
	boolean debug ;

	PApplet parent;
	MidiBus myBus;

	Axiom25 axiom25;

	PFont fontA;
	Method tapPressMethod;
	Method teclaBlancaPressMethod;
	Method teclaBlancaReleasedMethod;

	Method teclaNegraPressMethod;
	Method teclaNegraReleasedMethod;

	Method tapReleasedMethod;

	Method circularMoveMethod;
	Method neumaticoSueltoMoveMethod;
	Method neumaticoOrigenMoveMethod;

	public Paxiom25Midi(PApplet parent) {
		super();
		axiom25 = new Axiom25(parent);
		this.parent = parent;

		fontA = parent.createFont("Arial", 15, true);
		parent.textFont(fontA, 15);
		parent.size(800, 600);
		parent.background(0);

		myBus = new MidiBus(null, 0, 0); // Create a new MidiBus object
		myBus.addMidiListener(this);
		parent.smooth();
		parent.registerDispose(this);

		try {
			tapPressMethod = parent.getClass().getMethod("tapPress", Integer.class);
			tapReleasedMethod = parent.getClass().getMethod("tapReleased", Integer.class);

			teclaBlancaPressMethod = parent.getClass().getMethod("teclaBlancaPress", Integer.class);
			teclaBlancaReleasedMethod = parent.getClass().getMethod("teclaBlancaReleased", Integer.class);

			teclaNegraPressMethod = parent.getClass().getMethod("teclaNegraPress", Integer.class);
			teclaNegraReleasedMethod = parent.getClass().getMethod("teclaNegraReleased", Integer.class);
			circularMoveMethod = parent.getClass().getMethod("circularMove", Integer.class, Integer.class);
			neumaticoSueltoMoveMethod = parent.getClass().getMethod("neumaticoSueltoMove", Integer.class);
			neumaticoOrigenMoveMethod = parent.getClass().getMethod("neumaticoSueltoMove", Integer.class);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void makeEvent(Method method, int posicion, int valor) {
		makeEvent(method, new Object[] { posicion, valor });

	}

	public void makeEvent(Method method, int posicion) {
		makeEvent(method, new Object[] { posicion });
	}

	public void makeEvent(Method method, Object[] parametros) {
		if (method != null) {
			try {
				method.invoke(parent, parametros);
			} catch (Exception e) {
				System.err.println("Disabling " + tapPressMethod.getName() + " for tap because of an error.");
				e.printStackTrace();
				method = null;
			}
		}
	}

	public void dibuja() {
		parent.fill(255, 0, 0);
		parent.rect(0, 0, 100, 100);
	}

	public void draw() {
		parent.fill(0);
		parent.rect(0, 0, parent.width, parent.height);

		for (int i = 0; i < axiom25.circulares.size(); i++) {
			axiom25.circulares.get(i).display(parent.g);
		}

		for (int t = 0; t < axiom25.teclas.size(); t++) {
			axiom25.teclas.get(t).display();
		}

		for (int r = 0; r < axiom25.teclasNegras.size(); r++) {
			axiom25.teclasNegras.get(r).display();
		}

		for (int p = 0; p < axiom25.taps.size(); p++) {
			axiom25.taps.get(p).display();
		}

	}

	final int tipoControladorCircular = 176;
	final int tipoControladorNotaPresionada = 144;
	final int tipoControladorNotaSoltada = 128;
	final int tipoControladorTap = 153;
	final int tipoControladorNeumaticoConOrigen = 224;
	// es el mismo que el circular pero con id controlador=1
	final int tipoControladorNeumaticoSuelto = 176;

	public void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data,
										// String
		// bus_name)
		// Receive some raw data

		// data[0] will be the status byte
		// data[1] and data[2] will contain the parameter of the message (e.g.
		// pitch and volume for noteOn noteOff)
		if (debug) {
			parent.println();
			parent.println("Raw Midi Data:");
			parent.println("--------");
			parent.println("Status Byte/MIDI Command:" + (int) (data[0] & 0xFF));
		}
		// N.B. In some cases (noteOn, noteOff, controllerChange, etc) the first
		// half of the status byte is the command and the second half i

		// In these cases (data[0] & 0xF0) gives you the command and (data[0] &
		// 0x0F) gives you the channel

		int tipoControlador = dameValorMidi(data[0]);
		int idControlador = dameValorMidi(data[1]);
		int valorControlador = dameValorMidi(data[2]);

		switch (tipoControlador) {

		case tipoControladorCircular:

			if (idControlador != 1){
				actualizaControladorCircular(idControlador, valorControlador);
			}else{
				axiom25.actualizaControladorneumaticoSuelto(valorControlador);
			makeEvent(neumaticoSueltoMoveMethod,  valorControlador);
			}
			break;
		case tipoControladorNotaPresionada:
			actualizaControladorTecla(idControlador, true);
			break;
		case tipoControladorNotaSoltada:
			actualizaControladorTecla(idControlador, false);
			break;
		case tipoControladorTap:
			actualizaControladorTap(idControlador, valorControlador);
			break;
		case tipoControladorNeumaticoConOrigen:
			axiom25.actualizaControladorneumaticoOrigen(valorControlador);
			makeEvent(neumaticoOrigenMoveMethod,  valorControlador);
			break;
		}
		if (debug)
			for (int i = 1; i < data.length; i++) {
				parent.println("Param " + (i + 1) + ": " + dameValorMidi(data[i]));
			}
	}

	void actualizaControladorTecla(int idControlador, boolean pulsada) {
		try {
			int pos = axiom25.actualizaTeclaBlanca(idControlador, pulsada);
			if (pulsada)
				makeEvent(teclaBlancaPressMethod, pos);
			else
				makeEvent(teclaBlancaReleasedMethod, pos);

			return;
		} catch (ControladorTeclaNoExiste e) {
			// e.printStackTrace();
		}
		try {
			int pos = axiom25.actualizaTeclaNegra(idControlador, pulsada);
			if (pulsada)
				makeEvent(teclaNegraPressMethod, pos);
			else
				makeEvent(teclaNegraReleasedMethod, pos);
			return;
		} catch (ControladorTeclaNoExiste e) {
			// e.printStackTrace();
		}

	}

	void actualizaControladorTap(int idControlador, int valorControlador) {
		int pos;
		try {
			pos = axiom25.actualizaControladorTap(idControlador, valorControlador);
			if (valorControlador > 0)
				makeEvent(tapPressMethod, pos);
			else
				makeEvent(tapReleasedMethod, pos);

		} catch (ControladorTeclaNoExiste e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("no deberia ocurrir!");
		}

	}

	void actualizaControladorCircular(int idControlador, int valorControlador) {
		int pos = axiom25.actualizaControladorCircular(idControlador, valorControlador);
		makeEvent(circularMoveMethod, pos, valorControlador);
	}

	int dameValorMidi(byte b) {
		return (int) (b & 0xFF);
	}

	public void dispose() {
		// anything in here will be called automatically when
		// the parent applet shuts down. for instance, this might
		// shut down a thread used by this library.
	}

	public void midiMessage(MidiMessage message, long timeStamp) {
		rawMidi(message.getMessage());

	}

}
