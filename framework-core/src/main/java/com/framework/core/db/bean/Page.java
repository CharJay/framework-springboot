package com.framework.core.db.bean;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 每页条数
     */
    private int pageSize = 20;

    /**
     * 记录总数
     */
    private long totalItems = 0L;

    /**
     * 当前页
     */
    private int currentPage = 1;

    public Page() {

    }
    
    /**
     * 上一页
     * @return
     */
    public int getPrePage(){
        return currentPage-1<=0?1:currentPage-1;
    }
    public int getNextPage(){
        int t = getTotalPages();
        return currentPage+1 > t ?t : currentPage+1;
    }

    /**
     * construct the page by everyPage
     * 
     * @param everyPage
     */
    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    /** The whole constructor */
    public Page(int pageSize, long totalItems, int currentPage) {
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
    }

    public int getBeginIndex() {
        if (currentPage < 1)
            currentPage = 1;
        return (currentPage - 1) * pageSize + 1;
    }

    public int getEndIndex() {
        if (currentPage < 1)
            currentPage = 1;
        return (currentPage) * pageSize;
    }

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        if (totalItems == 0 || pageSize == 0)
            return 1;
        else
            return (int) ((totalItems + pageSize - 1) / pageSize);
    }

    /**
     * @return the totalItems
     */
    public long getTotalItems() {
        return totalItems;
    }

    /**
     * @param totalItems
     *            the totalItems to set
     */
    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * 获取实际查询时的页面条数。（为最后一页准备的）
     * @return
     */
    public Integer getQueryItemsNum() {
        long begin = getBeginIndex() -1;
        long end = getEndIndex();
        long total = getTotalItems();
        end = end >total?total:end;
        return Integer.parseInt(""+(end - begin));
    }

    @Override
    public String toString() {
        return new StringBuffer().append("page size:"+getPageSize()).append("current page"+getCurrentPage())
                .append("total items:" + getTotalItems()).toString();
//      return new ToStringBuilder(this).append("page size:", getPageSize()).append("current page", getCurrentPage())
//      .append("total items:" + getTotalItems()).toString();
    }
}
