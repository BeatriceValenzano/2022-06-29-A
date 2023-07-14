package it.polito.tdp.itunes.model;

import java.util.*;

public class Album implements Comparable<Album>{
	private Integer albumId;
	private String title;
	private int nCanzoni;
	private int bilancio;
	private List<Album> listaSuccessori;

	public Album(Integer albumId, String title, int nCanzoni) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.nCanzoni = nCanzoni;
		this.listaSuccessori= new LinkedList<>();
	}

	public int getnCanzoni() {
		return nCanzoni;
	}

	public void setnCanzoni(int nCanzoni) {
		this.nCanzoni = nCanzoni;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setBilancio(int bilancio) {
		this.bilancio = bilancio;
	}

	public int getBilancio() {
		return bilancio;
	}

	public void aggiungiAlbum(Album a) {
		listaSuccessori.add(a);
	}
	public List<Album> getListaSuccessori() {
		return listaSuccessori;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album other) {
		return this.title.compareTo(other.getTitle());
	}
	
	
	
}
