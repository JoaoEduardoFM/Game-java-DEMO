package jogo.combate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jogo.armas.Tiro;
import jogo.personagens.npc.Mob;
import jplay.Keyboard;
import jplay.Scene;
import jplay.Sound;
import jplay.URL;
import jplay.Window;

public class ControleTiros {

	LinkedList<Tiro> tiros = new LinkedList<>();

	public Tiro adicionaTiro(double x, double y, int caminho, Scene cena) {
		Tiro tiro = new Tiro(x, y, caminho);
		tiros.add(tiro);
		// adiciona tiro da tela
		cena.addOverlay(tiro);
		// somDisparo();
		return tiro;
	}

	boolean ataqueMob = false;

	public void run(Mob[] mobs, Window janela, Keyboard teclado) {
		List<Tiro> tirosToRemove = new ArrayList<>();

		for (Tiro tiro : tiros) {
			tiro.mover(janela, teclado, tiro);

			for (Mob inimigo : mobs) {
				if (inimigo.vidaMob > 0) {
					if (tiro.collided(inimigo)) {
						tiro.x = 10_000;
						inimigo.vidaMob -= 250;

						if (inimigo.vidaMob <= 0) {
							inimigo.morrer();
							inimigo.sofrerRecuo(7);
						}

						tirosToRemove.add(tiro);
					}
				}
			}
		}

		// Remove bullets after processing collisions
		tiros.removeAll(tirosToRemove);
	}

	private void somDisparo() {
		new Sound(URL.audio("tiro.WAV")).play();
	}
}
