package com.example.projecteandroid;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Registre {
    private LatLng latLng;
    private String titul = "";
    private String descripcio = "";
    private Date data;

    public Registre(LatLng latLng, String titul, String descripcio, Date data) {
        this.latLng = latLng;
        this.titul = titul;
        this.descripcio = descripcio;
        this.data = data;
    }

    public Registre(String titul){
        this.latLng = new LatLng(0,0);
        this.titul = titul;
        this.data = new Date();
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitul() {
        return titul;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public Date getData() {
        return data;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setTitul(String titul) {
        this.titul = titul;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return latLng.latitude+ "|" + latLng.longitude+"|"+ titul + "|" + descripcio + "|"+ data;
    }
}
