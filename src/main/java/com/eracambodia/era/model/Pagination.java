package com.eracambodia.era.model;

public class Pagination {
    private int page;
    private int limit;
    private int offset;
    private int totalPage;
    private int totalItem;
    public Pagination(int page,int limit){
        setPage(page);
        setLimit(limit);
        setOffset(page*limit-limit);
    }
    public Pagination(int page,int limit,int count){
        setPage(page);
        setLimit(limit);
        setOffset(page*limit-limit);
        setTotalPage(Math.round(count/limit+1));
        setTotalItem(count);
    }
    public Pagination(){
        page=1;
        limit=10;
        offset=0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", offset=" + offset +
                ", totalPage=" + totalPage +
                ", totalItem=" + totalItem +
                '}';
    }
}
