package paxiom25midi;

import processing.core.PApplet;

public class ControladorTecla {
	int x;
	int y;
	int col;
	int ancho;
	int alto;
	boolean valor = false;
	boolean disabled = false;
	public int idTecla;
	int col_ref;
	private final PApplet parent;

	public ControladorTecla(PApplet parent, int _x, int _y, boolean _disabled, int _idTecla, int _color, int _altura, int _anchura) {
		this.parent = parent;
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

		parent.textAlign(parent.CENTER);
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