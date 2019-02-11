package Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ErrorReport {
    String reporterID, doing, errorDescription;
    int reportType;
    String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    boolean solved=false;

    public ErrorReport(String reporterID,  int reportType, String doing, String errorDescription) {
        this.reporterID = reporterID;
        this.doing = doing;
        this.errorDescription = errorDescription;
        this.reportType = reportType;
    }

    public String getReporterID() {
        return reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    public String getDoing() {
        return doing;
    }

    public void setDoing(String doing) {
        this.doing = doing;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}

