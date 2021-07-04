package com.cloneproject.domain;

public class SearchInfo {
    private boolean searchFlag;
    private String searchKey;

    public boolean getSearchFlag() {
        return this.searchFlag;
    }

    public void setSearchFlag(boolean searchFlag) {
        this.searchFlag = searchFlag;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
