package com.example.baikt;

public class Thisinh {
    String SBD;
    String Hoten;
    float T,l,h;

    public Thisinh(String SBD, String hoten, float t, float l, float h) {
        this.SBD = SBD;
        this.Hoten = hoten;
        this.T = t;
        this.l = l;
        this.h = h;
    }

    public String getSBD() {
        return SBD;
    }

    public void setSBD(String SBD) {
        this.SBD = SBD;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public float getT() {
        return T;
    }

    public void setT(float t) {
        T = t;
    }

    public float getL() {
        return l;
    }

    public void setL(float l) {
        this.l = l;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "Thisinh{" +
                "SBD='" + SBD + '\'' +
                ", Hoten='" + Hoten + '\'' +
                ", T=" + T +
                ", l=" + l +
                ", h=" + h +
                '}';
    }

    public float Tinhtongdiem(){
        return this.h+this.l+this.T;
    }
    public float TinhdiemTB(){
        return (this.h+this.l+this.T)/3;
    }
}
