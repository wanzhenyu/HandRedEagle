package unicom.hand.redeagle.zhfy.bean;

import java.util.List;

/**
 * Created by linana on 2018-07-03.
 */

public class QueryMeetingBean1 {
    public List<Integer> searchTypes;
//    public int confType;
//    private boolean active;
//    private String role;
    private int pageNo;
//    private int total;
    private int pageSize;
//    private int orderByType;
//    private String orderByField;
    private long queryDate;

    private String searchKey;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public long getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(long queryDate) {
        this.queryDate = queryDate;
    }

    public List<Integer> getSearchType() {
        return searchTypes;
    }

    public void setSearchType(List<Integer> searchType) {
        this.searchTypes = searchType;
    }

//    public int getConfType() {
//        return confType;
//    }
//
//    public void setConfType(int confType) {
//        this.confType = confType;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

//    public int getTotal() {
//        return total;
//    }
//
//    public void setTotal(int total) {
//        this.total = total;
//    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

//    public int getOrderByType() {
//        return orderByType;
//    }
//
//    public void setOrderByType(int orderByType) {
//        this.orderByType = orderByType;
//    }
//
//    public String getOrderByField() {
//        return orderByField;
//    }
//
//    public void setOrderByField(String orderByField) {
//        this.orderByField = orderByField;
//    }
}
