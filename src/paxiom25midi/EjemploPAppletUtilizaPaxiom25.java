package paxiom25midi;

import processing.core.PApplet;

public class EjemploPAppletUtilizaPaxiom25 extends PApplet {

	Paxiom25Midi paxiom25midi;

	public void setup() {

		paxiom25midi = new Paxiom25Midi(this);

		paxiom25midi.dibuja();

	}

	public void draw() {
		paxiom25midi.draw();
	}

}
