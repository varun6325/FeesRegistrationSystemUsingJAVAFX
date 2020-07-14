package com.varun.db.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Installment", schema = "FeesRegistration", catalog = "")
public class InstallmentEntity {
    private int installmentId;
    private int installmentNo;
    private BigDecimal intallmentAmount;
    private boolean instalmentIsDone;
    private Date installmentDueDate;
    private Date installmentPaidDate;
    private RegistrationEntity registrationByRegistrationId;



    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "installmentId", insertable=false, updatable=false)
    public int getInstallmentId() {
        return installmentId;
    }

    public void setInstallmentId(int installmentId) {
        this.installmentId = installmentId;
    }

    @Basic
    @Column(name = "installmentNo")
    public int getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(int installmentNo) {
        this.installmentNo = installmentNo;
    }

    @Basic
    @Column(name = "intallmentAmount")
    public BigDecimal getIntallmentAmount() {
        return intallmentAmount;
    }

    public void setIntallmentAmount(BigDecimal intallmentAmount) {
        this.intallmentAmount = intallmentAmount;
    }

    @Basic
    @Column(name = "instalmentIsDone", columnDefinition = "TINYINT")
    public boolean isInstalmentIsDone() {
        return instalmentIsDone;
    }

    public void setInstalmentIsDone(boolean instalmentIsDone) {
        this.instalmentIsDone = instalmentIsDone;
    }

    @Basic
    @Column(name = "installmentDueDate")
    public Date getInstallmentDueDate() {
        return installmentDueDate;
    }

    public void setInstallmentDueDate(Date installmentDueDate) {
        this.installmentDueDate = installmentDueDate;
    }

    @Basic
    @Column(name = "installmentPaidDate")
    public Date getInstallmentPaidDate() {
        return installmentPaidDate;
    }

    public void setInstallmentPaidDate(Date installmentPaidDate) {
        this.installmentPaidDate = installmentPaidDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstallmentEntity that = (InstallmentEntity) o;
        return installmentId == that.installmentId &&
                installmentNo == that.installmentNo &&
                Objects.equals(intallmentAmount, that.intallmentAmount) &&
                Objects.equals(instalmentIsDone, that.instalmentIsDone) &&
                Objects.equals(installmentDueDate, that.installmentDueDate) &&
                Objects.equals(installmentPaidDate, that.installmentPaidDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(installmentId, installmentNo, intallmentAmount, instalmentIsDone, installmentDueDate, installmentPaidDate);
    }

    @ManyToOne
    @JoinColumn(name = "registrationId", referencedColumnName = "registrationId", nullable = false)
    public RegistrationEntity getRegistrationByRegistrationId() {
        return registrationByRegistrationId;
    }

    public void setRegistrationByRegistrationId(RegistrationEntity registrationByRegistrationId) {
        this.registrationByRegistrationId = registrationByRegistrationId;
    }
}
