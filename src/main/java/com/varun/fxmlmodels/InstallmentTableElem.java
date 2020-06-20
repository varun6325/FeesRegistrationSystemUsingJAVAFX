package com.varun.fxmlmodels;

import java.util.Date;

public class InstallmentTableElem {
    private int installmentId;
    private Double installmentAmount;
    private boolean installmentPaid;
    private Date installmentDueDate;

    public int getInstallmentId() {
        return installmentId;
    }

    public InstallmentTableElem(int installmentId, Double installmentAmount, boolean installmentPaid, Date installmentDueDate) {
        this.installmentId = installmentId;
        this.installmentAmount = installmentAmount;
        this.installmentPaid = installmentPaid;
        this.installmentDueDate = installmentDueDate;
    }

    public void setInstallmentId(int installmentId) {
        this.installmentId = installmentId;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public boolean isInstallmentPaid() {
        return installmentPaid;
    }

    public void setInstallmentPaid(boolean installmentPaid) {
        this.installmentPaid = installmentPaid;
    }

    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }
}
