package paxiom25midi;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BateriaImagenes extends PApplet {

	String[] prefijosImagenes = { "aznar", "benedicto", "botin", "bush", "camps", "caracol", "chocolate", "fabra", "franco", "rato", "zapatero" };
	Paxiom25Midi paxiom25midi;
	boolean debug=true;
	PFont fontA;

	public void setup() {
		paxiom25midi = new Paxiom25Midi(this);
		size(1280, 1024);
		background(0);
		println(sketchPath);
		for (String prefijo : prefijosImagenes) {
			Secuencia secuencia = new Secuencia();
			secuencia.prefijo = prefijo;
			cargaPathsDeImagenesDeDirectorio(secuencia);
			secuencias.add(secuencia);
		}
		fontA = createFont("Arial", 15, true);
		textFont(fontA, 15);

	}

	List secuencias = new ArrayList();
	int contadorSecuencia=0;
	Secuencia secuenciaActual;
	public void draw() {
		textAlign(CENTER);
		fill(255);
		frameRate(map(paxiom25midi.valorCircular(1), 0,127,1,30));

		if(secuenciaActual==null){
			 secuenciaActual = dameSecuencia(contadorSecuencia);
		}else{
			 secuenciaActual = dameSecuencia(contadorSecuencia%secuencias.size());
		}
		
		String fotoActual = (String) secuenciaActual.getFotoActual();

		if(secuenciaActual.finSecuencia){
			contadorSecuencia++;
			secuenciaActual.finSecuencia=false;
		}
		
		PImage loadImage = loadImage(fotoActual);

		image(loadImage, 0, 0);
		if(debug){
		text(secuenciaActual.prefijo+" - "+(secuenciaActual.contadorFoto-1), 30,30);
		}

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
				//	System.out.println(childFile.getPath().replace(directorioFotos(), ""));

					// PImage loadImage = loadImage(childFile.getPath());
					if(childFile.getPath().contains(secuencia.prefijo))
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
		
		String getFotoActual(){
			String string = (String) fotos.get(contadorFoto);
			if(contadorFoto==fotos.size()-1){
				finSecuencia=true;
				contadorFoto=0;
			}else{
				contadorFoto++;
			}
			return  string;
		}
	}
	
	

}
