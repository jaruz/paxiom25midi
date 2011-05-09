package paxiom25midi;

public interface Paxiom {
public void tapPress(Integer pos);

	public void tapReleased(Integer pos);

	public void teclaBlancaPress(Integer pos) ;
	public void teclaBlancaReleased(Integer pos) ;
	public void teclaNegraPress(Integer pos) ;
	public void teclaNegraReleased(Integer pos) ;
	public void circularMove(Integer pos, Integer valor) ;

	public void neumaticoOrigenMove(Integer valor) ;

	
	public void neumaticoSueltoMove(Integer valor) ;
	}
