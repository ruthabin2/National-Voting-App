package com.example.ethiopiannationalvoting;

public class PartyMode {
     String PartyName;
    String Objective;
    String Symbol;

    public PartyMode() {
    }

    public PartyMode(String partyName, String objective, String symbol) {
        PartyName = partyName;
        Objective = objective;
        Symbol = symbol;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getObjective() {
        return Objective;
    }

    public void setObjective(String objective) {
        Objective = objective;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }
}
