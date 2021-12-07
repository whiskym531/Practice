package com.example.carrot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: warm
 * Date: 2021/7/9
 * Description:
 */

public class PagingCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 2598875146576926658L;
    @JsonIgnore
    private Integer pageNo = 1;
    @JsonIgnore
    private Integer pageSize = 20;
    @JsonIgnore
    private Boolean hasNext = true;

    public PagingCriteria() {
    }

    @JsonIgnore
    public Boolean hasNext() {
        return this.hasNext;
    }

    public void nextPage() {
        if (this.pageNo == null) {
            this.pageNo = 1;
        }

        this.pageNo = this.pageNo + 1;
    }

    public Integer getLimit() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getLimit();
    }

    public Integer getOffset() {
        PageInfo pageInfo = new PageInfo(this.pageNo, this.pageSize);
        return pageInfo.getOffset();
    }

    public Map<String, Object> toMap() {
        this.formatDate();
        return super.toMap();
    }

    protected void formatDate() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PagingCriteria)) {
            return false;
        } else {
            PagingCriteria other = (PagingCriteria)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                label49: {
                    Object this$pageNo = this.getPageNo();
                    Object other$pageNo = other.getPageNo();
                    if (this$pageNo == null) {
                        if (other$pageNo == null) {
                            break label49;
                        }
                    } else if (this$pageNo.equals(other$pageNo)) {
                        break label49;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$hasNext = this.hasNext;
                Object other$hasNext = other.hasNext;
                if (this$hasNext == null) {
                    if (other$hasNext != null) {
                        return false;
                    }
                } else if (!this$hasNext.equals(other$hasNext)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PagingCriteria;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        result = result * 59 + super.hashCode();
        Object $pageNo = this.getPageNo();
        result = result * 59 + ($pageNo == null ? 43 : $pageNo.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $hasNext = this.hasNext;
        result = result * 59 + ($hasNext == null ? 43 : $hasNext.hashCode());
        return result;
    }

    public String toString() {
        return "PagingCriteria(super=" + super.toString() + ", pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + ", hasNext=" + this.hasNext + ")";
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
