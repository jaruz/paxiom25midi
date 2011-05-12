package paxiom25midi;

import processing.core.PApplet;
import codeanticode.gsvideo.*;
public class PruebaGsVideo extends PApplet{

	GSMovie myMovie;


	public void setup() {
	  size(640, 480, P3D);
	  background(0);
	  // Load and play the video in a loop
	  myMovie = new GSMovie(this, "columpio1.MOV");
	  myMovie.loop();
	}


	public void movieEvent(GSMovie myMovie) {
	  myMovie.read();
	  System.out.println(myMovie.width+" . "+myMovie.height);

	}


	public void draws() {
	  tint(255, 10);
	  image(myMovie, mouseX-myMovie.width/2, mouseY-myMovie.height/2);
	}
	
	public void draw() {
		  if (1 < myMovie.width && 1 < myMovie.height) {
		  
		    // A new time position is calculated using the current mouse location:
		    float f = constrain((float)mouseX / width, 0, 1);
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
		    image(myMovie, 0, 0, width, height);
		  }
		}
	
}
