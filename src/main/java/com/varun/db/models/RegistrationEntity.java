package com.varun.db.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Registration", schema = "FeesRegistration", catalog = "")
public class RegistrationEntity {
    private int registrationId;
    private BigDecimal discount;
    private Date registrationDate;
    Date lastModified;
    private int studentId;
    private int courseId;
    private Collection<InstallmentEntity> installmentsByRegistrationId;
    private StudentEntity studentByStudentId;
    private CourseEntity courseByCourseId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "registrationId", insertable=false, updatable=false)
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    @Basic
    @Column(name = "discount")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "registrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    @Basic
    @Column(name = "lastModified")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationEntity that = (RegistrationEntity) o;
        return registrationId == that.registrationId &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(registrationDate, that.registrationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(registrationId, discount, registrationDate);
    }

    @Basic
    @Column(name = "studentId", insertable = false, updatable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "courseId", insertable = false, updatable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @OneToMany(mappedBy = "registrationByRegistrationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Collection<InstallmentEntity> getInstallmentsByRegistrationId() {
        return installmentsByRegistrationId;
    }

    public void setInstallmentsByRegistrationId(Collection<InstallmentEntity> installmentsByRegistrationId) {
        this.installmentsByRegistrationId = installmentsByRegistrationId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", referencedColumnName = "studentId", nullable = false)
    public StudentEntity getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(StudentEntity studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId", referencedColumnName = "courseId", nullable = false)
    public CourseEntity getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(CourseEntity courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastModified = new Date(System.currentTimeMillis());
        if (registrationDate==null) {
            registrationDate = new Date(System.currentTimeMillis());
        }
    }
}
