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
	 PApplet parent;
	 int posicion;

	public ControladorTecla(PApplet parent, int _x, int _y, boolean _disabled, int _idTecla, int _color, int _altura, int _anchura, int posicion) {
		this.parent = parent;
		x = _x;
		idTecla = _idTecla;
		y = _y;
		col = _color;
		col_ref = _color;
		ancho = _anchura;
		alto = _altura;
		disabled = _disabled;
		this.posicion=posicion;
	}

	/**
	 * sirve para comparar y encontrar elementos en colecciones
	 * @param idControlador
	 */
	public ControladorTecla(int idControlador) {
		idTecla = idControlador;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idTecla;
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
		ControladorTecla other = (ControladorTecla) obj;
		if (idTecla != other.idTecla)
			return false;
		return true;
	}

	
	
}