package com.fennec.e_media.entity;

public class emprunts {

    public int id;
    public int id_user;
    public int id_element;
    public String date_debut;
    public String date_fin;
    public int rendu;

    public emprunts() {
    }

    public emprunts(int id, int id_user, int id_element, String date_debut, String date_fin, int rendu) {
        this.id = id;
        this.id_user = id_user;
        this.id_element = id_element;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.rendu = rendu;
    }
}
