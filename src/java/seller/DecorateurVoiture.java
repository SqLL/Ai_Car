package seller;
/**
 * Abstract decorator of Voiture
 * @author squall
 * @see Voiture
 *
 */
public abstract class DecorateurVoiture extends Voiture {

	protected Voiture voiture;
	
		
	public abstract int getIndice();
	public abstract String toString();
}
