package com.yuanyu.ceramics.logistics;

import java.util.List;

public class LogisticsBean {
    private String State;
    private List<Logistics> Traces;
    private String LogisticCode;
    private String ShipperCode;
    public String getState() {
        return State;
    }

    public List<Logistics> getTraces() {
        return Traces;
    }

    public class Logistics{
        private String AcceptTime;
        private String AcceptStation;

        public Logistics(String acceptTime, String acceptStation) {
            AcceptTime = acceptTime;
            AcceptStation = acceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptTime(String acceptTime) {
            AcceptTime = acceptTime;
        }

        public void setAcceptStation(String acceptStation) {
            AcceptStation = acceptStation;
        }
    }
}
