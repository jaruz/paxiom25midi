package paxiom25midi;

import processing.core.PApplet;
import processing.core.PGraphics;

public class ControladorCircular {
	int x;
	int y;
	int col;
	int diametro;
	int valor = 0;
	public int idControlador;
	private  PApplet parent;
	 int posicion;

	public ControladorCircular(PApplet parent, int _x, int _y, int _idControlador, int posicion) {
		this.parent = parent;
		x = _x;
		y = _y;
		col = parent.color(100);
		diametro = 30;
		idControlador = _idControlador;
		this.posicion=posicion;
	}
/**
 * sirve para realizar comparaciones en colecciones
 * @param idControlador2
 */
	public ControladorCircular(int idControlador2) {
		idControlador = idControlador2;
	}

	void display(PGraphics parent) {
		parent.pushStyle();

		parent.textAlign(parent.CENTER);
		parent.fill(255);
		parent.text(valor, x, y + diametro + 3);
		if (valor > 0) {
			parent.stroke(150);
			parent.fill(PApplet.map(valor, 0, 127, 50, 256), 0, 0);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idControlador;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ControladorCircular other = (ControladorCircular) obj;
		if (idControlador != other.idControlador)
			return false;
		return true;
	}

}
