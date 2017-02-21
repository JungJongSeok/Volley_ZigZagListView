package com.example.ldcc.volley_test.model;

import java.io.Serializable;
import java.util.Date;

public class ObjectCPU{
    private String hostName;
    private String use;
    private String memory;
    private String cpu;
    private String diskTotal;
    private String diskFree;
    private String diskStatus;
    private String date;

    public ObjectCPU(String hostName, String use, String memory, String cpu, String diskTotal, String diskFree, String diskStatus, String date) {
        this.hostName = hostName;
        this.memory = memory;
        this.use = use;
        this.cpu = cpu;
        this.diskTotal = diskTotal;
        this.diskFree = diskFree;
        this.diskStatus = diskStatus;
        this.date = date;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
    }

    public String getDiskFree() {
        return diskFree;
    }

    public void setDiskFree(String diskFree) {
        this.diskFree = diskFree;
    }

    public String getDiskStatus() {
        return diskStatus;
    }

    public void setDiskStatus(String diskStatus) {
        this.diskStatus = diskStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
