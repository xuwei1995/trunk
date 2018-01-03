package com.serviceindeed.yike.yikemo.domain.helper;

import com.serviceindeed.yike.yikemo.util.YiKeMoHelper;

public class Columns {
    private   String sortField;
    private   String sortDir;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = YiKeMoHelper.getInstance().underscoreName(sortField);//自动转对应数据库字段
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
