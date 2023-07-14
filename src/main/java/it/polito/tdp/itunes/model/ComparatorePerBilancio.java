package it.polito.tdp.itunes.model;

import java.util.Comparator;

public class ComparatorePerBilancio implements Comparator<Album>{

	@Override
	public int compare(Album a1, Album a2) {
		return - (a1.getBilancio()-a2.getBilancio());
	}

}
