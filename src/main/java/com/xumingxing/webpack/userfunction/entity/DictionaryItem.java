package com.xumingxing.webpack.userfunction.entity;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/3110:16
 * Description: 数据字典项
 * Location: webpack
 */
public class DictionaryItem {

    /**
     * 项名
     */
    private String name;
    /**
     * 项值
     */
    private int value;

    public DictionaryItem() {}

    public DictionaryItem(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DictionaryItem{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryItem that = (DictionaryItem) o;
        return value == that.value &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

}
