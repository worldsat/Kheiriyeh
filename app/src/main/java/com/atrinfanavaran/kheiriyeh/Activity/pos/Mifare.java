package com.atrinfanavaran.kheiriyeh.Activity.pos;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Mifare {

    public String serial;
    public String keyA;
    public String keyB;
    public String blNum;
    public String opTyp;
    public String data;
    public String authRetVal;
    public String writeRetVal;
    public String readRetVal;

    public static List<Mifare> getMifare(String jsonStr) {
        try {
            List<Mifare> mifareList = new ArrayList<>();
            if(jsonStr.charAt(0) == '['){
                JSONArray jsonarray = new JSONArray(jsonStr);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject json = jsonarray.getJSONObject(i);
                    Mifare mifare = new Mifare();
                    mifare.serial = json.getString("serial");
                    mifare.keyA = json.getString("keyA");
                    mifare.keyB = json.getString("keyB");
                    mifare.blNum = json.getString("blNum");
                    mifare.opTyp = json.getString("opTyp");
                    mifare.data = json.getString("data");
                    mifare.authRetVal = json.getString("authRetVal");
                    mifare.writeRetVal = json.getString("writeRetVal");
                    mifare.readRetVal = json.getString("readRetVal");
                    mifareList.add(mifare);
                }
            }
            else {
                JSONObject json = new JSONObject(jsonStr);
                Mifare mifare = new Mifare();
                mifare.serial = json.getString("serial");
                mifare.keyA = json.getString("keyA");
                mifare.keyB = json.getString("keyB");
                mifare.blNum = json.getString("blNum");
                mifare.opTyp = json.getString("opTyp");
                mifare.data = json.getString("data");
                mifare.authRetVal = json.getString("authRetVal");
                mifare.writeRetVal = json.getString("writeRetVal");
                mifare.readRetVal = json.getString("readRetVal");
                mifareList.add(mifare);
            }
            return mifareList;
        }
        catch (JSONException e) { e.printStackTrace(); return null; }
    }

    @Override
    public String toString() {
        return "{" +
                "serial='" + serial + '\'' +
                ", keyA='" + keyA + '\'' +
                ", keyB='" + keyB + '\'' +
                ", blNum='" + blNum + '\'' +
                ", opTyp='" + opTyp + '\'' +
                ", data='" + data + '\'' +
                ", authRetVal='" + authRetVal + '\'' +
                ", writeRetVal='" + writeRetVal + '\'' +
                ", readRetVal='" + readRetVal + '\'' +
                '}';
    }

}
