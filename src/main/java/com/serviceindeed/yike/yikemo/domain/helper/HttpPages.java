package com.serviceindeed.yike.yikemo.domain.helper;

public class HttpPages {
   //分页参数
    private   Integer draw;
    private   Integer start;
    private   Integer length;


    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "HttpPages{" +
                "draw=" + draw +
                ", start=" + start +
                ", length=" + length +
                '}';
    }
}
