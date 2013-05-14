package fr.sciencesu.scannstockmobile.API;

import java.util.Date;

public interface IScanneurBarreCode 
{
	/**
	 * Scan des dates avec le téléphone
	 */
	public void runScanningDate();
	
	/**
	 * Scan l'image prise avec le téléphone android
	 * 
	 * @param multiBc : false = 1 seul ; true = plusieurs
	 */
	public void runScan(boolean multiBc);
	
	/**
	 * Obtenir le code EAN du produit
	 * 
	 * @return : retourne le code EAN
	 */
	public String getCodeEAN();
	
	/**
	 * Obtenir la date scannée
	 * 
	 * @return
	 */
	public Date getScanningDate();
	
	/**
	 * Conversion de la chaine de caractère date en vrai date
	 * 
	 * @param dateStr : year-mois-jour
	 * @return
	 */
	public Date getDate(String dateStr);
}
