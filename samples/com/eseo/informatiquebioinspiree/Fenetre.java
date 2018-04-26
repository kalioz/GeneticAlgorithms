package com.eseo.informatiquebioinspiree;

import javax.swing.JFrame;

import fr.kalioz.geneticalgorithm.Laboratory;

public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Panneau panneau;

	public Panneau getPanneau() {
		return panneau;
	}

	public void setPanneau(Panneau panneau) {
		this.panneau = panneau;
	}

	public Fenetre(Laboratory<Individu> laboratory) {
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setPanneau(new Panneau(laboratory));

		this.add(getPanneau());
		this.pack();

		this.setVisible(true);
		// this.setAlwaysOnTop(true);
	}
}