package paxiom25midi;

import processing.core.PApplet;
import codeanticode.gsvideo.*;
public class GsVideoMidi  extends PApplet implements Paxiom{

	GSMovie myMovie;

	Paxiom25Midi paxiom25midi;

	public void setup() {
//		this.sketchPath;
//		println(sketchPath("")
		//println(sketchFile(arg0));
//		println(dataPath());
//		println(savePath());
	//  size(640, 480, P3D);
		paxiom25midi = new Paxiom25Midi(this, false);
		paxiom25midi.info=true;
	  background(0);
	  // Load and play the video in a loop
	  myMovie = new GSMovie(this, "carrera.MOV");
	  myMovie.loop();
	}


	public void movieEvent(GSMovie myMovie) {
	  myMovie.read();
	}

	
	float posicion=0.5f;
	
	public void draw() {
		if(activo1)amplia();
		if(activo5)reduce();
		  if (1 < myMovie.width && 1 < myMovie.height) {
		  
		    // A new time position is calculated using the current mouse location:
		    //float f = constrain((float)paxiom25midi.valorCircular(1) / paxiom25midi.limiteMidi, 0, 1);
		    float t = myMovie.duration() * posicion;
		    
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

	boolean activo1;
	boolean activo5;
	
	void amplia(){
		 if(posicion<1){
			 posicion+=incremento;
			 activo1=true;
		 }else
			 activo1=false;
	}
	float incremento=0.05f;
	
	void reduce(){
		 if(posicion>1){
			 posicion-=incremento;
			 activo5=true;
		 }else{
			 activo5=false;
		 }
	}
	
	public void tapPress(Integer pos) {
		if(pos.intValue()==1){
			activo1=true;
		}
		if(pos.intValue()==5 ){
			activo5=true;
		}
		// TODO Auto-generated method stub
		
	}


	public void tapReleased(Integer pos) {
		if(pos.intValue()==1){
			activo1=false;
		}
		if(pos.intValue()==5 ){
			activo5=false;
		}		
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
	//	incremento=map(valor, 0,127, 0.01f, 0.1f);
		
	}
	
}
