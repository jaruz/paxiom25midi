package paxiom25midi;

import processing.core.PApplet;

public class EjemploPAppletUtilizaPaxiom25 extends PApplet implements Paxiom{

	Paxiom25Midi paxiom25midi;

	int limite = 100;

	public void setup() {

		paxiom25midi = new Paxiom25Midi(this, true);

		// paxiom25midi.dibuja();
		paxiom25midi.debug = true;
		paxiom25midi.info = true;
		paxiom25midi.info();
		colorMode(HSB, limite, limite, limite, limite);
		noStroke();

	}

	float angle = 0;
	
	public void draw() {
	// paxiom25midi.draw();
//		if (paxiom25midi.valorTap(7)) {
//			angle += 0.05;
//			translate(width / 2, height / 2);
//			rotate(angle);
//		}
		fill(dameValorMapeado(1, colorHue), dameValorMapeado(2, colorSat),dameValorMapeado(3, colorBri),dameValorMapeado(4, colorAlp));
		translate(width/2, height/2);
		ellipse(adapta(paxiom25midi.valorCircular(1), width/2),adapta(paxiom25midi.valorCircular(2), height/2),paxiom25midi.valorCircular(3),paxiom25midi.valorCircular(3));
		
//		strokeWeight(paxiom25midi.valorCircular(3));
//		stroke(hue, saturation, brightness, paxiom25midi.valorCircular(8, limite));
//		line(random(0, width), random(0, height), paxiom25midi.valorCircular(1, width), paxiom25midi.valorCircular(2, height));

	}

	private float adapta(int valor, int limite) {
		 float map = map(valor, 0, paxiom25midi.limiteMidi, 0,  limite);
		float random = random(-map,map);
		return random;
	}
	
	float colorHue;
	float colorSat;
	float colorBri;
	float colorAlp;
	
	private float dameValorMapeado(int i, float valorActual) {
		float map = map(valorActual, 0,paxiom25midi.limiteMidi, 0, limite);
		return map;
	}

	private void limpiaPantalla() {
		pushStyle();
		fill(random(limite));
		noStroke();
		rect(-width/2, -height/2, width, height);
		popStyle();
	}

	public void tapPress(Integer pos) {
		

	}

	public void tapReleased(Integer pos) {
		if (pos.intValue() == 1) colorHue=paxiom25midi.ultimoValorNeumaticoSuelto;
		if (pos.intValue() == 2) colorSat=paxiom25midi.ultimoValorNeumaticoSuelto;
		if (pos.intValue() == 3) colorBri=paxiom25midi.ultimoValorNeumaticoSuelto;
		if (pos.intValue() == 4) colorAlp=paxiom25midi.ultimoValorNeumaticoSuelto;
//			loop();

	}

	public void teclaBlancaPress(Integer pos) {
		if(pos.intValue()==3)
		limpiaPantalla();

	}

	public void teclaBlancaReleased(Integer pos) {

	}

	public void teclaNegraPress(Integer pos) {

	}

	public void teclaNegraReleased(Integer pos) {

	}

	public void circularMove(Integer pos, Integer valor) {

	}

	public void neumaticoOrigenMove(Integer valor) {

	}

	
	public void neumaticoSueltoMove(Integer valor) {
		if(paxiom25midi.valorTap(1)) colorHue=valor;
		if(paxiom25midi.valorTap(2)) colorSat=valor;
		if(paxiom25midi.valorTap(3)) colorBri=valor;
		if(paxiom25midi.valorTap(4)) colorAlp=valor;

		//frameRate(paxiom25midi.valorNeumaticoSuelto(1, 30));
	}

}
