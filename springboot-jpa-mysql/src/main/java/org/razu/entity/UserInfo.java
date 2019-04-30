package org.razu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.razu.utils.PatternUtils;

// @EntityListeners(AuditingEntityListener.class)
// @JsonIgnoreProperties(value = {"createdDate", "updatedDate", "deleteDate"}, allowGetters = true)
//
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "u_name"
    })
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.AUTO
    @Column(name = "u_id", nullable = false)
    private Long id;

    @NotBlank(message = "Enter the User Name") // @NotNull(message = "Enter the User Name")
    @Size(min = 3, max = 30, message = "Enter the User Name between {min} to {max} characters")
    @Pattern(regexp = PatternUtils.ID_PATTERN, message = "Enter only character and digit in the User Name")
    @Column(name = "u_name", nullable = false, length = 30)
    private String userName;

    // password validation skip now because encripted not match here
    @NotBlank(message = "Enter the User Password")
    @Size(min = 8, max = 60, message = "Enter the User Password between {min} to {max} characters")
    //@Pattern(regexp = PatternUtils.ID_PATTERN, message = "Only characters and digit allow in the User Password")
    @Column(name = "u_password", nullable = false, length = 60)
    private String userPassword;

    @Column(name = "u_status")
    private Integer userStatus;

    @Column(name = "insert_by")
    private Integer insertBY;

    // @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_date")
    private Date insertDate;

    @Column(name = "update_by")
    private Integer updateBy;

    // @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "delete_by")
    private Integer deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delete_date")
    private Date deleteDate;

    // FetchType OneToOne by default EAGER it do EAGER  -->  FetchType OneToMany, ManyToMany do LAZY
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userInfo")
    @JsonManagedReference
    private UserName usersName;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the userStatus
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * @return the insertBY
     */
    public Integer getInsertBY() {
        return insertBY;
    }

    /**
     * @param insertBY the insertBY to set
     */
    public void setInsertBY(Integer insertBY) {
        this.insertBY = insertBY;
    }

    /**
     * @return the insertDate
     */
    public Date getInsertDate() {
        return insertDate;
    }

    /**
     * @param insertDate the insertDate to set
     */
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    /**
     * @return the updateBy
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy the updateBy to set
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the deleteBy
     */
    public Integer getDeleteBy() {
        return deleteBy;
    }

    /**
     * @param deleteBy the deleteBy to set
     */
    public void setDeleteBy(Integer deleteBy) {
        this.deleteBy = deleteBy;
    }

    /**
     * @return the deleteDate
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * @param deleteDate the deleteDate to set
     */
    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    /**
     * @return the usersName
     */
    public UserName getUsersName() {
        return usersName;
    }

    /**
     * @param usersName the usersName to set
     */
    public void setUsersName(UserName usersName) {
        this.usersName = usersName;
    }
}
