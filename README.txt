proyecto eclipse java para el desarrollo de libreria processing para trabajar con el teclado axiom 25 con protocolo midy y utilizando la libreria themidibus

esta en git jaruz - codigodelaimagen@gmail.com y compartido con juanantonioruz@gmail.com


----------------- Ejemplo de uso en processing -----------


import paxiom25midi.*;

import themidibus.*;

// para que funcione hay que incluir la librera themidibus (del web de processing) y la que ha realizado un servidor para el piano midi de raul axiom25 que se llama paxiom25midi

Paxiom25Midi paxiom25;
void setup(){
   paxiom25=new Paxiom25Midi(this);
 
  paxiom25.dibuja();
}

void draw(){
 paxiom25.draw();
}


-------------------------------------------------------