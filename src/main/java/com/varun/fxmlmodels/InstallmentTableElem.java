package com.varun.fxmlmodels;

import java.util.Date;

public class InstallmentTableElem {
    private int installmentNo;

    public int getInstallmentId() {
        return installmentId;
    }

    public void setInstallmentId(int installmentId) {
        this.installmentId = installmentId;
    }

    private int installmentId;
    private Double installmentAmount;
    private boolean isInstallmentPaid;
    private Date installmentDueDate;

    public int getInstallmentNo() {
        return installmentNo;
    }

    public InstallmentTableElem(int installmentId, int installmentNo, Double installmentAmount, boolean isInstallmentPaid, Date installmentDueDate) {
        this.installmentId = installmentId;
        this.installmentNo = installmentNo;
        this.installmentAmount = installmentAmount;
        this.isInstallmentPaid = isInstallmentPaid;
        this.installmentDueDate = installmentDueDate;
    }

    public void setInstallmentNo(int installmentNo) {
        this.installmentNo = installmentNo;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public boolean isInstallmentPaid() {
        return isInstallmentPaid;
    }

    public boolean getIsInstallmentPaid() {
        return isInstallmentPaid;
    }

    public void setIsInstallmentPaid(boolean isInstallmentPaid) {
        this.isInstallmentPaid = isInstallmentPaid;
    }

    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }
}
