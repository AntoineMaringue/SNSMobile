/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.sciencesu.scannstockmobile.SCANNSTOCK;

/**
 *
 * @author antoi_000
 */
class Produit implements IObject {

    private String ID;
    private String marque;
    private String prix;
    private String name;
    private String contenance;
    private String imgSrc;
    private String categorie;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    

     public void setPrix(String prix) {
        this.prix = prix;
    }
     
     public String getPrix() {
        return this.prix;
    }
     
    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContenance(String contenance) {
        this.contenance = contenance;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    

    public String getName() {
        return name;
    }

    public String getImg() {
        return imgSrc;
    }

    public String getContenance() {
        return contenance;
    }

    public String getMarque() {
        return marque;
    }

    public String getCategorie() {
        return categorie;
    }

    @Override
    public String toString() {
        return "" + marque+"\n"+ prix + "\n" + name +"\n" + categorie + '\n';
    }
    
    
}
