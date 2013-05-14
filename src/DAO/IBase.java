package DAO;

import java.util.ArrayList;

/**
 * 
 * @author Antoine
 *
 */
public interface IBase 
{

    /**
     * Connection à une base de données
     */
    public void connection();
	
	    /**
     *Insertion de données dans une table
     * @param table la table dans laquelle on souhaite inserer les données
     * @param values listes des valeurs à insérer COMPRENANT les colonnes
     * @return
     */
    public boolean insertInto(String table, ArrayList<String> values);

    /**
     * Deconnection d'une base
     */
    public void deconnection();
    
    /**
     *Selection et affichage d'une vue ou du contenu d'une table
     * @param name
     */
    public void selectVisual(String name);
    
    /**
     *Affichage de la description des éléments composant une vue ou une table
     * @param name
     */
    public void selectDesc(String name);
}
