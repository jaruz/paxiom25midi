                                                                                                                                                                                                                                                                                                                                                                                                                          package paxiom25midi;

import processing.core.PApplet;
import codeanticode.gsvideo.*;
public class GsVideoMidiMinimal  extends PApplet implements Paxiom{

	GSMovie myMovie;

	Paxiom25Midi paxiom25midi;

	public void setup() {
	//  size(640, 480, P3D);
		paxiom25midi = new Paxiom25Midi(this);

	  background(0);
	  // Load and play the video in a loop
	  myMovie = new GSMovie(this, "carrera.MOV");
	  myMovie.loop();
	}


	public void movieEvent(GSMovie myMovie) {
	  myMovie.read();
	}


	public void draws() {
	  tint(255, 10);
	  image(myMovie, mouseX-myMovie.width/2, mouseY-myMovie.height/2);
	}
	
	public void draw() {
		  if (1 < myMovie.width && 1 < myMovie.height) {
		  
		    // A new time position is calculated using the current mouse location:
		    float f = constrain((float)paxiom25midi.valorCircular(1) / paxiom25midi.limiteMidi, 0, 1);
		    float t = myMovie.duration() * f;
		    
		    // If the new time is different enough from the current position,
		    // then we jump to the new position. But how different? Here the
		    // difference has been set to 0.1 (1 tenth of a second), but it can
		    // be smaller. My guess is that the smallest value should correspond
		    // to the duration of a single frame (for instance 1/24 if the frame rate 
		    // of the video file is 24fps). Setting even smaller values seem to lead
		    // to choppiness. This will become trickier once the GSMovie.speed()  
		    // and GSMovie.frameRate() methods become functional. 
		    if (0.1 < abs(t - myMovie.time())) {
		      // The movie stream must be in play mode in order to jump to another
		      // position along the stream. Otherwise it won't work.
		      myMovie.play();
		      myMovie.jump(t);
		      myMovie.pause();
		    }
			  tint(255, 230);

		    image(myMovie, 0, 0, width, height);
		  }
		}


	public void tapPress(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void tapReleased(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void teclaBlancaPress(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void teclaBlancaReleased(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void teclaNegraPress(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void teclaNegraReleased(Integer pos) {
		// TODO Auto-generated method stub
		
	}


	public void circularMove(Integer pos, Integer valor) {
		// TODO Auto-generated method stub
		
	}


	public void neumaticoOrigenMove(Integer valor) {
		// TODO Auto-generated method stub
		
	}


	public void neumaticoSueltoMove(Integer valor) {
		// TODO Auto-generated method stub
		
	}
	
}
