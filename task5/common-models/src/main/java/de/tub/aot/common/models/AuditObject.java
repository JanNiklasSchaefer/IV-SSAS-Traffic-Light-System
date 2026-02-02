package de.tub.aot.common.models;

import java.io.Serializable;
import java.util.Objects;

public class AuditObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private long timestamp;
    private String serviceType;
    private String logMessage;

    public AuditObject() {
        // default constructor
    }

    public AuditObject(String serviceType, String logMessage) {
        this.timestamp = System.currentTimeMillis();
        this.serviceType = serviceType;
        this.logMessage = logMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    @Override
    public String toString() {
        return "AuditObject{" +
                "timestamp=" + timestamp +
                ", serviceType='" + serviceType + '\'' +
                ", logMessage='" + logMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditObject)) return false;
        AuditObject that = (AuditObject) o;
        return timestamp == that.timestamp &&
                Objects.equals(serviceType, that.serviceType) &&
                Objects.equals(logMessage, that.logMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, serviceType, logMessage);
    }
}

