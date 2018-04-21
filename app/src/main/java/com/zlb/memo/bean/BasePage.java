package com.zlb.memo.bean;

import java.io.Serializable;
import java.util.List;

public class BasePage<T> implements Serializable {
    private int totalRol;
    private int pageNumber;
    private int totalPage;
    private int pageSize;
    private List<T> list;

    public int getTotalRol() {
        return totalRol;
    }

    public void setTotalRol(int totalRol) {
        this.totalRol = totalRol;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean hasNextPage() {
        if (pageNumber < totalPage) {
            return true;
        } else {
            return false;
        }
    }
}
