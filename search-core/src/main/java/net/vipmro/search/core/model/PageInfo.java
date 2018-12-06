package net.vipmro.search.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 *
 * @param <T>
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class PageInfo<T> implements Serializable {
    /**
     * 第几页
     */
    private Integer pageNo;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    /**
     * 数据总数
     */
    private Integer total;
    /**
     * 数据集
     */
    private List<T> dataList;

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public Integer getPageTotal() {
        return (total + pageSize - 1) / pageSize;
    }
}
