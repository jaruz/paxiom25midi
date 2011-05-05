package paxiom25midi;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Axiom25 {

	int[] idsCirculares = { 72, 8, 74, 71, 20, 22, 86, 73 };
	List<ControladorCircular> circulares = new ArrayList<ControladorCircular>(idsCirculares.length);
	int[] idsTaps = { 50, 45, 51, 49, 36, 38, 46, 42 };
	List<ControladorTecla> taps = new ArrayList<ControladorTecla>();

	List<ControladorTecla> teclas = new ArrayList<ControladorTecla>();
	int[] idTeclas = { 42, 43, 45, 47, 49, 50, 52, 54, 55, 57 };

	int[] idTeclasNegras = { 44, 46, 48, 51, 53, 56, 58 };
	List<ControladorTecla> teclasNegras = new ArrayList<ControladorTecla>();
	
	ControladorCircular neumaticoSuelto;
	ControladorCircular neumaticoOrigen;

	public Axiom25(PApplet parent) {
		iniciaCirculares(parent);

		inicializaTaps(parent);

		inicializaTeclasBlancas(parent);

		inicializaTeclasNegras(parent);
		neumaticoSuelto=new ControladorCircular(parent, 0,0,1,1);
		// el 300 es porque tiene un cambio anomalo y no hace falta reconocerlo por el idCOntrolador sino por el tipo de controlador
		neumaticoOrigen=new ControladorCircular(parent, 0,0,300,0);
	}

	public void actualizaControladorneumaticoSuelto( int valorControlador) {
		neumaticoSuelto.actualiza(valorControlador);

	}
	public void actualizaControladorneumaticoOrigen( int valorControlador) {
		neumaticoOrigen.actualiza(valorControlador);

	}

	
	private void inicializaTeclasNegras(PApplet parent) {
		int contadorIdsTeclasNegras = 0;
		int posicionX = 0;
		for (int r = 0; r < 10; r++) {

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
			teclasNegras.add(new ControladorTecla(parent, posicionX + 35, 300, disabled, idTeclaNegra, parent.color(0), 150, 20, teclasNegras.size() + 1));
			posicionX += 50;
		}
	}

	private void inicializaTeclasBlancas(PApplet parent) {
		int contadorIdsTecla = 0;
		for (int t = 0; t < 15; t++) {
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
			teclas.add(new ControladorTecla(parent, posicionX, 300, disabled, idTecla, parent.color(200), 200, 40, teclas.size() + 1));
		}
	}

	private void inicializaTaps(PApplet parent) {
		int contadorIdsTaps = 0;
		int posiX = 500;
		int posiY = 100;
		for (int p = 0; p < idsTaps.length; p++) {

			int idTap = idsTaps[contadorIdsTaps];
			contadorIdsTaps++;
			int ancho = 50;

			if (p == 4) {
				posiY = 100 + ancho + ancho / 5;
				posiX = 500;
			}
			taps.add(new ControladorTecla(parent, posiX, posiY, false, idTap, parent.color(150), ancho, ancho, taps.size() + 1));
			posiX += ancho + ancho / 5;
		}
	}

	private void iniciaCirculares(PApplet parent) {
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
			circulares.add(new ControladorCircular(parent, posicionX + margenInicialX, _y, idsCirculares[i], circulares.size()+1));
			contadorFila++;
		}
	}

	// TODO GESTION DE EXCEPCIONES EN CASO DE QUE NO EXISTA EL CONTROLADOR
	public int actualizaControladorCircular(int idControlador, int valorControlador) {
		ControladorCircular controladorCircular = new ControladorCircular(idControlador);
		int indexOf = circulares.indexOf(controladorCircular);
		try {
			ControladorCircular controladorCircular2 = circulares.get(indexOf);
			controladorCircular2.actualiza(valorControlador);
			return controladorCircular2.posicion;
		} catch (Exception e) {
			throw new RuntimeException("no deberia ocurrir: "+idControlador+" valor: "+valorControlador);
			
		}

	}

	// TODO GESTION DE EXCEPCIONES EN CASO DE QUE NO EXISTA EL CONTROLADOR
	public int actualizaControladorTap(int idControlador, int valorControlador) throws ControladorTeclaNoExiste {
		ControladorTecla tecla = encuentraTecla(idControlador, taps);
		boolean valor = false;
		if (valorControlador > 0)
			valor = true;

		tecla.actualiza(valor);
		return tecla.posicion;
	}

	public int actualizaTeclaNegra(int idControlador, boolean pulsada) throws ControladorTeclaNoExiste {
		ControladorTecla tecla = encuentraTecla(idControlador, teclasNegras);
		tecla.actualiza(pulsada);
		return tecla.posicion;

	}

	public int actualizaTeclaBlanca(int idControlador, boolean pulsada) throws ControladorTeclaNoExiste {
		ControladorTecla tecla = encuentraTecla(idControlador, teclas);
		tecla.actualiza(pulsada);
		return tecla.posicion;

	}

	private ControladorTecla encuentraTecla(int idControlador, List<ControladorTecla> teclas) throws ControladorTeclaNoExiste {
		ControladorTecla controladorTecla = new ControladorTecla(idControlador);
		int indexOf = teclas.indexOf(controladorTecla);
		try {
			ControladorTecla controladorTeclaExistente = teclas.get(indexOf);
			return controladorTeclaExistente;
		} catch (Exception e) {
			throw new ControladorTeclaNoExiste();
		}
	}

}
