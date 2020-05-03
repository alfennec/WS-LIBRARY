package com.fennec.e_media.entity;

public class information {

    public int id;
    public int id_element;
    public String annee;
    public String titre;
    public String isbn;
    public int nbr_page;
    public String date_achat;
    public String qrcode;

    public information() {
    }

    public information(int id, int id_element, String annee, String titre, String isbn, int nbr_page, String date_achat, String qrcode) {
        this.id = id;
        this.id_element = id_element;
        this.annee = annee;
        this.titre = titre;
        this.isbn = isbn;
        this.nbr_page = nbr_page;
        this.date_achat = date_achat;
        this.qrcode = qrcode;
    }
}
