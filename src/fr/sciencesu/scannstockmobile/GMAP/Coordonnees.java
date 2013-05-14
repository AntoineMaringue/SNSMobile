package fr.sciencesu.scannstockmobile.GMAP;

/**
 * Classe répertoriant les différentes coordonnées des sites recherché pour les restos du coeurs
 * @author Antoine
 *
 */
public class Coordonnees 
{
	/**
	 * Latitude du site
	 */
	private double _latitude;
	/**
	 * Longitude du site
	 */
	private double _longitude;
	
	/**
	 * Coordonnées constructeur vide
	 */
	public Coordonnees() 
	{
		super();
		_latitude=0;
		_longitude=0;
	}
	
	/**
	 * Constructeur avec initialisation des variables 
	 * 
	 * @param _latitude
	 * @param _longitude
	 */
	public Coordonnees(double _latitude, double _longitude) {
		super();
		this._latitude = _latitude;
		this._longitude = _longitude;
	}
	
	/**
	 * Retourne la latitude
	 * 
	 * @return
	 */
	public double get_latitude() {
		return _latitude;
	}
	/**
	 * Modification de la latitude
	 * 
	 * @param _latitude
	 */
	public void set_latitude(double _latitude) {
		this._latitude = _latitude;
	}
	/**
	 * Retourne la longitude
	 * 
	 * @return
	 */
	public double get_longitude() {
		return _longitude;
	}
	/**
	 * Modification de la longitude
	 * 
	 * @param _longitude
	 */
	public void set_longitude(double _longitude) {
		this._longitude = _longitude;
	}

}
