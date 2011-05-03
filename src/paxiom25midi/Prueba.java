package paxiom25midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import processing.core.PApplet;
import themidibus.*;

public class Prueba extends PApplet implements StandardMidiListener {
	MidiBus myBus;

	public void setup() {
		// Paxiom25 paxiom25 = new Paxiom25(this);
		size(300, 300);
		// paxiom25.dibuja();
		myBus = new MidiBus(null, 0, 0); // Create a new MidiBus object
		myBus.addMidiListener(this);
		// myBus.iniciaMetodo(this);
	}

	public void draw() {
	}

	public void rawMidi(byte[] data) { // You can also use rawMidi(byte[] data,
										// String
		// bus_name)
		// Receive some raw data

		// data[0] will be the status byte
		// data[1] and data[2] will contain the parameter of the message (e.g.
		// pitch and volume for noteOn noteOff)
		println();
		println("Raw Midi Data:");
		println("--------");
		println("Status Byte/MIDI Command:" + (int) (data[0] & 0xFF));
		System.out.println("DDDDDDDDDDDDDDDDDD");

	}

	@Override
	public void midiMessage(MidiMessage message, long timeStamp) {
	rawMidi(message.getMessage());

	}

	

	// void notifyListeners(MidiMessage message, long timeStamp) {
	// byte[] data = message.getMessage();
	//
	// for(MidiListener listener : listeners) {
	//
	// /* -- RawMidiListener -- */
	//
	// if(listener instanceof RawMidiListener)
	// ((RawMidiListener)listener).rawMidiMessage(data);
	//
	// /* -- SimpleMidiListener -- */
	//
	// if(listener instanceof SimpleMidiListener) {
	// if((int)((byte)data[0] & 0xF0) == ShortMessage.NOTE_ON) {
	// ((SimpleMidiListener)listener).noteOn((int)(data[0] & 0x0F),(int)(data[1]
	// & 0xFF),(int)(data[2] & 0xFF));
	// } else if((int)((byte)data[0] & 0xF0) == ShortMessage.NOTE_OFF) {
	// ((SimpleMidiListener)listener).noteOff((int)(data[0] &
	// 0x0F),(int)(data[1] & 0xFF),(int)(data[2] & 0xFF));
	// } else if((int)((byte)data[0] & 0xF0) == ShortMessage.CONTROL_CHANGE) {
	// ((SimpleMidiListener)listener).controllerChange((int)(data[0] &
	// 0x0F),(int)(data[1] & 0xFF),(int)(data[2] & 0xFF));
	// }
	// }
	//
	// /* -- StandardMidiListener -- */
	//
	// if(listener instanceof StandardMidiListener)
	// ((StandardMidiListener)listener).midiMessage(message, timeStamp);
	//
	// }
	// }

}
