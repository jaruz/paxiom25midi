package paxiom25midi;

import processing.core.PApplet;

public class EjemploPAppletUtilizaPaxiom25 extends PApplet {

	Paxiom25Midi paxiom25midi;

	public void setup() {

		paxiom25midi = new Paxiom25Midi(this);

		paxiom25midi.dibuja();
		paxiom25midi.debug=false;

	}

	public void draw() {
		paxiom25midi.draw();
	}

	public  void tapPress(Integer pos){
		System.out.println("taping"+pos);
	}
	public  void tapReleased(Integer pos){
		System.out.println("untaping"+pos);
	}

	public  void teclaBlancaPress(Integer pos){
		System.out.println("presiona tecla blanca:"+pos);
	}
	public  void teclaBlancaReleased(Integer pos){
		System.out.println("suelta tecla blanca :"+pos);
	}
	
	public  void teclaNegraPress(Integer pos){
		System.out.println("presiona tecla negra "+pos);
	}
	public  void teclaNegraReleased(Integer pos){
		System.out.println("suelta tecla negra"+pos);
	}
	
	public  void circularMove(Integer pos, Integer valor){
		System.out.println("circular: "+pos+" valor: "+valor);
	}
	public  void neumaticoOrigenMove( Integer valor){
		System.out.println("neumaticoOrigenMove: "+valor);
	}
	public  void neumaticoSueltoMove( Integer valor){
		System.out.println("neumaticoSueltoMove: "+valor);
	}
	
	
}

