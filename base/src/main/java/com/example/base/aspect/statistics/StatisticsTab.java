package com.example.base.aspect.statistics;

public enum  StatisticsTab {
    LOGIN(1, "login"),
    REGISTER(2, "register");
    int functionId;
    String functionName;
    StatisticsTab(int functionId, String functionName) {
        this.functionId = functionId;
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
