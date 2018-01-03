package com.serviceindeed.yike.yikemo.domain.helper;

public class CardTypeCount {
    private Integer num;
    private String status;
    private String statusValue;
    private Long rechargeType;
    private String rechargeName;
    private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Long getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Long rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeName() {
        return rechargeName;
    }

    public void setRechargeName(String rechargeName) {
        this.rechargeName = rechargeName;
    }

    @Override
    public String toString() {
        return "CardTypeCount{" +
                "num=" + num +
                ", status='" + status + '\'' +
                ", statusValue='" + statusValue + '\'' +
                ", rechargeType=" + rechargeType +
                ", rechargeName='" + rechargeName + '\'' +
                ", orgId=" + orgId +
                '}';
    }
}
