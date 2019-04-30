package org.razu.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class BaseEntity {

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
}
