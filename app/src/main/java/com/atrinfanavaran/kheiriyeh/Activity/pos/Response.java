package com.atrinfanavaran.kheiriyeh.Activity.pos;


public class Response {

    public static final String TransactionType = "TransactionType";
    public static final String ResponseCode = "ResponseCode";
    public static final String CardNumber = "CardNumber";
    public static final String RRN = "RRN";
    public static final String SerialNumber = "SerialNumber";
    public static final String TerminalNumber = "TerminalNumber";
    public static final String DateTime = "DateTime";
    public static final String Amount = "Amount";

    public static final String PrintStatus = "PrintStatus";
    public static final String CheckPaper = "CheckPaper";
    public static final String BarCodeStatus = "BarCodeStatus";
    public static final String MifareResult = "MifareResult";
    public static final String SwipeCard = "SwipeCard";
    public static final String TimeOut = "TimeOut";
    public static final String GetInfo = "GetInfo";
    public static final String GetApp = "GetApp";
    public static final String GetPositionInfo = "PositionInfo";
    public static final String Error = "Error";

    public String trxType = "";
    public String RS = "";
    public String CN = "";
    public String _RRN = "";
    public String SN = "";
    public String TN = "";
    public String DT = "";
    public String AM = "";
    public String PS = "";
    public String CP = "";
    public String BR = "";
    public String MI ;
    public String[] GI ;
    public String SC = "";
    public String TO = "";
    public String[] PO ;
    public String ER ;

    @Override
    public String toString() {
        return "Response{" +
                "ER='" + ER + '\'' +
                ", trxType='" + trxType + '\'' +
                ", RS='" + RS + '\'' +
                ", CN='" + CN + '\'' +
                ", _RRN='" + _RRN + '\'' +
                ", SN='" + SN + '\'' +
                ", TN='" + TN + '\'' +
                ", DT='" + DT + '\'' +
                ", AM='" + AM + '\'' +
                ", PS='" + PS + '\'' +
                ", CP='" + CP + '\'' +
                ", BR='" + BR + '\'' +
                ", MI='" + MI + '\'' +
                ", GI='" + GI + '\'' +
                ", SC='" + SC + '\'' +
                ", TO='" + TO + '\'' +
                ", PO='" + PO + '\'' +
                '}';
    }
}

