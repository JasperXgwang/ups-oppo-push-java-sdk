package com.ups.oppo.push.server.sdk.vo;

/**
 * User: jasperxgwang
 * Date: 2018-5-31 15:03
 */
public class Statistics {

    private long targetCount;
    private long validCount;
    private long sendCount;
    private long arriveCount;
    private long showCount;
    private long openCount;

    public long getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(long targetCount) {
        this.targetCount = targetCount;
    }

    public long getValidCount() {
        return validCount;
    }

    public void setValidCount(long validCount) {
        this.validCount = validCount;
    }

    public long getSendCount() {
        return sendCount;
    }

    public void setSendCount(long sendCount) {
        this.sendCount = sendCount;
    }

    public long getArriveCount() {
        return arriveCount;
    }

    public void setArriveCount(long arriveCount) {
        this.arriveCount = arriveCount;
    }

    public long getShowCount() {
        return showCount;
    }

    public void setShowCount(long showCount) {
        this.showCount = showCount;
    }

    public long getOpenCount() {
        return openCount;
    }

    public void setOpenCount(long openCount) {
        this.openCount = openCount;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "targetCount=" + targetCount +
                ", validCount=" + validCount +
                ", sendCount=" + sendCount +
                ", arriveCount=" + arriveCount +
                ", showCount=" + showCount +
                ", openCount=" + openCount +
                '}';
    }
}
