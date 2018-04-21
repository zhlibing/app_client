package com.zlb.memo.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TestModel implements Serializable {
    public String a = "";
    public String b = "";
    public String c = "";
    public String d = "";
    public String e = "";

    private ArrayList<ArrayList<MyPoiItem>> T;
;
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public TestModel(String a) {
        this.a = a;
    }

    public TestModel(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public TestModel(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public TestModel(String a, String b, String c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public TestModel(String a, String b, String c, String d, String e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public ArrayList<ArrayList<MyPoiItem>> getT() {
        return T;
    }

    public void setT(ArrayList<ArrayList<MyPoiItem>> t) {
        T = t;
    }
}
