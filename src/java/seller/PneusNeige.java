package seller;

public class PneusNeige extends DecorateurVoiture {

	public PneusNeige(Voiture v)
	{
		this.voiture=v;
	}
	
	@Override
	public int getIndice() {
		return this.voiture.getIndice()+10;
	}

	@Override
	public String toString() {
		return this.voiture.toString()+System.getProperty("line.separator")+"Option : Pneus neige";
	}

	@Override
	public String getType() {
		return this.voiture.getType();
	}
}
