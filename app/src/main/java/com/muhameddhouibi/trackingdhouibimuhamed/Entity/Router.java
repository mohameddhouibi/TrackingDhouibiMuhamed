package com.muhameddhouibi.trackingdhouibimuhamed.Entity;

public class Router {

    String router_name ;
    String macAdress ;
    String routerModel ;
    String Ipadress ;
    String timestamp ;
    String etat ;

    public Router() {
    }

    public Router(String router_name, String macAdress, String routerModel, String ipadress, String timestamp, String etat) {
        this.router_name = router_name;
        this.macAdress = macAdress;
        this.routerModel = routerModel;
        Ipadress = ipadress;
        this.timestamp = timestamp;
        this.etat = etat;
    }

    public String getRouter_name() {
        return router_name;
    }

    public void setRouter_name(String router_name) {
        this.router_name = router_name;
    }

    public String getMacAdress() {
        return macAdress;
    }

    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    public String getRouterModel() {
        return routerModel;
    }

    public void setRouterModel(String routerModel) {
        this.routerModel = routerModel;
    }

    public String getIpadress() {
        return Ipadress;
    }

    public void setIpadress(String ipadress) {
        Ipadress = ipadress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
