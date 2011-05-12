package paxiom25midi;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import codeanticode.gsvideo.GSMovie;

public class BateriaImagenesPApplet extends PApplet implements Paxiom{

	String[] prefijosImagenes = { "aznar", "benedicto", "botin", "bush", "camps", "caracol", "chocolate", "fabra", "franco", "rato", "zapatero" };
	String[] prefijosPelis = { "carrera.MOV", "columpio1.MOV", "station.MOV"};
	List pelis=new ArrayList();
	Paxiom25Midi paxiom25midi;
	boolean debug = true;
	PFont fontA;
	GSMovie myMovie;

	public void setup() {
		setupPaxiomMidi();

	setupSecuenciasVideo();

		setupSecuencias();
		fontA = createFont("Arial", 15, true);
		textFont(fontA, 15);
		textAlign(CENTER);
	}

	
	private void setupSecuenciasVideo() {
		for(String prefijoPeli:prefijosPelis) {
			GSMovie gsMovie = new GSMovie(this, prefijoPeli);
			gsMovie.loop();
			gsMovie.play();
			gsMovie.volume(0);
			gsMovie.jump(5);
			gsMovie.pause();
			pelis.add(gsMovie);
	
		}
		// TODO Auto-generated method stub
		
	}


	List secuencias = new ArrayList();
	int contadorSecuencia = 0;
	Secuencia secuenciaActual;

	boolean fotoVideo=true;
	
	public void draw() {
		//repintaTodo();
		if(fotoVideo){
	frameRate(map(paxiom25midi.valorCircular(1), 0, 127, 1, 30));
		
		drawSecuencias();
		}else{
			frameRate(25);
			drawVideo();
			
		}
			

	}
	public void drawVideo() {
		  
//		    sincronizaVideoConMouse();
		    sincronizaVideoEnPlay();
		}


	private void sincronizaVideoEnPlay() {
		  if (1 < myMovie.width && 1 < myMovie.height) {

		myMovie.play();
		tint(255,10);
		image(myMovie, 0, 0, width, height);
		  }
	}


	private void sincronizaVideoConMouse() {
		  if (1 < myMovie.width && 1 < myMovie.height) {

		// A new time position is calculated using the current mouse location:
//			    float f = constrain((float)paxiom25midi.valorCircular(1) / paxiom25midi.limiteMidi, 0, 1);
		    float f = constrain((float)mouseX / width, 0, 1);
//			    println(f);
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

	private void drawSecuencias() {
		fill(255);

		if (secuenciaActual == null) {
			secuenciaActual = dameSecuencia(contadorSecuencia);
		} else {
			secuenciaActual = dameSecuencia(contadorSecuencia % secuencias.size());
		}

		String fotoActual = (String) secuenciaActual.getFotoActual();

		if (secuenciaActual.finSecuencia) {
			contadorSecuencia++;
			secuenciaActual.finSecuencia = false;
		}

		PImage loadImage = loadImage(fotoActual);

		image(loadImage, 0, 0);
		if (debug) {
			text(secuenciaActual.prefijo + " - " + (secuenciaActual.contadorFoto - 1), 30, 30);
		}
	}

	private void repintaTodo() {
		fill(0);
		rect(0, 0, width, height);
	}

	public void movieEvent(GSMovie myMovie) {
//		fill(255);
//		text("evento de movie!" + myMovie.time(), 730, 30);
//
//		// println();
		myMovie.read();
	}



	private void setupSecuencias() {
		size(1280, 1024);
		background(0);
		println(sketchPath);
		for (String prefijo : prefijosImagenes) {
			Secuencia secuencia = new Secuencia();
			secuencia.prefijo = prefijo;
			cargaPathsDeImagenesDeDirectorio(secuencia);
			secuencias.add(secuencia);
		}


	}

	private void setupPaxiomMidi() {
		paxiom25midi = new Paxiom25Midi(this, false);
		//paxiom25midi.info();

		paxiom25midi.info = true;
	}

	
	public void teclaBlancaPress(Integer pos) {
		if(pos.intValue()==3){
			finalizaVideo();
		}else if(pos.intValue()==4){
			iniciaVideo(0);
		}else  if(pos.intValue()==5){
			iniciaVideo(1);
		}else  if(pos.intValue()==6){
			iniciaVideo(2);
		}
		System.out.println(pos);

	}

	private void iniciaVideo(int posicion) {
		
		
//		myMovie =  new GSMovie(this, prefijosPeli[posicion]);
//		myMovie.volume(0);
//		myMovie.jump(5);
		
		myMovie=(GSMovie) pelis.get(posicion);
		myMovie.play();
	

	
		fotoVideo=false;

	}

	


	private void finalizaVideo() {
//		if(myMovie!=null)myMovie.noLoop();
		myMovie.stop();
//		myMovie=null;
		fotoVideo=true;
	}
	public void tapPress(Integer pos) {
		// TODO Auto-generated method stub

	}

	public void tapReleased(Integer pos) {
		// TODO Auto-generated method stub

	}

	public void teclaNegraPress(Integer pos) {

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

	private Secuencia dameSecuencia(int indice) {
		Secuencia secuencia = (Secuencia) secuencias.get(indice);
		return secuencia;
	}

	void cargaPathsDeImagenesDeDirectorio(Secuencia secuencia) {
		File dir = new File(directorioFotos());
		if (dir.isDirectory()) {
			String[] contents = dir.list();

			for (int i = 0; i < contents.length; i++) {
				if (contents[i].charAt(0) == '.')
					continue;
				else if (contents[i].toLowerCase().endsWith(".jpg")) {
					File childFile = new File(dir, contents[i]);
					// paths.add(childFile.getPath().replace("data/", ""));
					// System.out.println(childFile.getPath().replace(directorioFotos(),
					// ""));

					// PImage loadImage = loadImage(childFile.getPath());
					if (childFile.getPath().contains(secuencia.prefijo))
						secuencia.fotos.add(childFile.getPath());
					// image(loadImage, 0, 0);
					// println(childFile.getPath()+"------"+childFile.getPath().replace("data/",
					// ""));

				}
			}
		}

	}

	String directorioFotos() {
		return sketchPath + "/data/secuencias/";
	}

	class Secuencia {
		public String prefijo;
		int contadorFoto;
		List fotos = new ArrayList();
		boolean finSecuencia;

		String getFotoActual() {
			String string = (String) fotos.get(contadorFoto);
			if (contadorFoto == fotos.size() - 1) {
				finSecuencia = true;
				contadorFoto = 0;
			} else {
				contadorFoto++;
			}
			return string;
		}
	}

	public void teclaBlancaReleased(Integer pos) {
		// TODO Auto-generated method stub
		
	}

}
