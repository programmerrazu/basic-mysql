package org.razu.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users_name", catalog = "spring_boot_mysql_jpa_db")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "un_id", nullable = false, length = 10)
    private Long id;

    @NotBlank(message = "Enter the user first name")
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @NotBlank(message = "Enter the user last name")
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserInfo userInfo;

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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
