package com.fennec.e_media.entity;

public class media {

    public int id;
    public String titre;
    public String des;

    public media() {
    }

    public media(int id, String titre, String des) {
        this.id = id;
        this.titre = titre;
        this.des = des;
    }

    public media(String titre, String des) {
        this.titre = titre;
        this.des = des;
    }
}
