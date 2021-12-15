package testeIGPoker;
public class PseudoCarte implements interfaceGraphique.IGPokerable {

	private int x;
	
	public PseudoCarte(int xx) {
		x = xx;
	}
	
	@Override
	public int toIGPInt() {
		return x;
	}

}
