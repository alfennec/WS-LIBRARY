package com.fennec.e_media.entity;

public class user {

    public int id;
    public String nom;
    public String prenom;
    public String tel;
    public String email;
    public String passord;
    public int type;

    public user() {
    }

    public user(int id, String nom, String prenom, String tel, String email, String passord, int type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.passord = passord;
        this.type = type;
    }

    public user(String nom, String prenom, String tel, String email, String passord, int type) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.passord = passord;
        this.type = type;
    }
}
