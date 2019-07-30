package com.xumingxing.webpack.userfunction.common;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2915:04
 * Description: Version 1.0
 * Location: webpack
 */
public class PageEntity<T> {

    private int page;
    private int size;
    private long total;
    private T data;

    public static PageEntity<Object> generateData (int page, int size, long total, Object data) {
        return new PageEntity<>(page,size,total,data);
    }

    public static PageEntity<Object> generateData (long total, Object data) {
        return new PageEntity<>(total,data);
    }

    public static PageEntity<Object> generateData () {
        return new PageEntity<>();
    }

    private PageEntity() {
    }

    private PageEntity(int page, int size, long total, T data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.data = data;
    }

    private PageEntity(long total, T data) {
        this.total = total;
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public PageEntity<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public PageEntity<T> setSize(int size) {
        this.size = size;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PageEntity<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public T getData() {
        return data;
    }

    public PageEntity<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "page=" + page +
                ", size=" + size +
                ", total=" + total +
                ", data=" + data +
                '}';
    }
}
