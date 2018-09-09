package com.framework.core.db.bean;

import java.util.List;

public class ListDataWrap<T> {
    
    public static final Long NO_COUNT = Long.valueOf( -1L );
    
    private Long total = NO_COUNT;
    private List<T> list;
    
    public ListDataWrap() {
        super();
    }
    
    public ListDataWrap( List<T> list ) {
        setList( list );
    }
    
    public ListDataWrap( List<T> list, Long total ) {
        setList( list );
        setTotal( total );
    }
    
    public Long getTotal() {
        return total;
    }
    
    public void setTotal( Long total ) {
        this.total = total;
    }
    
    public List<T> getList() {
        return list;
    }
    
    public void setList( List<T> list ) {
        this.list = list;
    }
    
}
