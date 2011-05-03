package paxiom25midi;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Axiom25 {

	int[] idsCirculares = { 72, 8, 74, 71, 20, 22, 86, 73 };
	List<ControladorCircular> circulares = new ArrayList<ControladorCircular>(idsCirculares.length);

	public Axiom25(PApplet parent) {
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
			circulares.add(new ControladorCircular(parent, posicionX + margenInicialX, _y, idsCirculares[i]));
			contadorFila++;
		}
	}

	public void actualizaControladorCircular(int idControlador, int valorControlador) {
		ControladorCircular controladorCircular=new ControladorCircular(idControlador);
		int indexOf = circulares.indexOf(controladorCircular);
		circulares.get(indexOf).actualiza(valorControlador);
	}

}
