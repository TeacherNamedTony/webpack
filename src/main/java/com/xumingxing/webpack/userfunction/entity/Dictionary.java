package com.xumingxing.webpack.userfunction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/3110:14
 * Description:  * 数据字典- 字典项
 * Location: webpack
 */
@Component
/*
    @Transient注解 设置透明属性
*/

@Document(collection = "dictionary")
public class Dictionary {
        /**
         * 主键
         *
         * 新增请勿自定义该ID字段
         */
        @Id
        private String id;
        /**
         * 分类类型
         */
        @Field
        private String classify;
        /**
         * 字典名称
         */
        @NotBlank(message = "数据字典项目名称不能为空")
        @Field
        private String name;
        /**
         * 字典描述
         */
        @NotEmpty
        @Field
        private String describe;
        /**
         * 排序
         */
        @Field
        private int sort;
        /**
         * 操作
         * 0 可读可写
         * 1 只读
         */
        @Field
        private int operate;
        /**
         * 数据状态
         * 0 正常
         * 1 删除
         */
        @Field("data_status")
        private int dataStatus;
        /**
         * 创建时间
         */
        @Field("create_time")
        private LocalDateTime createTime;
        /**
         * 修改时间
         */
        @Field("revise_time")
        private LocalDateTime reviseTime;

        /**
         * 字典项
         * @see DictionaryItem
         */
        @Field("items")
        private List<DictionaryItem> items;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getOperate() {
            return operate;
        }

        public void setOperate(int operate) {
            this.operate = operate;
        }

        public int getDataStatus() {
            return dataStatus;
        }

        public void setDataStatus(int dataStatus) {
            this.dataStatus = dataStatus;
        }

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getReviseTime() {
            return reviseTime;
        }

        public void setReviseTime(LocalDateTime reviseTime) {
            this.reviseTime = reviseTime;
        }

        public List<DictionaryItem> getItems() {
            return items;
        }

        public void setItems(List<DictionaryItem> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "Dictionary{" +
                    "id='" + id + '\'' +
                    ", classify='" + classify + '\'' +
                    ", name='" + name + '\'' +
                    ", describe='" + describe + '\'' +
                    ", sort=" + sort +
                    ", operate=" + operate +
                    ", dataStatus=" + dataStatus +
                    ", createTime=" + createTime +
                    ", reviseTime=" + reviseTime +
                    ", items=" + items +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dictionary that = (Dictionary) o;
            return sort == that.sort &&
                    operate == that.operate &&
                    dataStatus == that.dataStatus &&
                    Objects.equals(id, that.id) &&
                    Objects.equals(classify, that.classify) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(describe, that.describe) &&
                    Objects.equals(createTime, that.createTime) &&
                    Objects.equals(reviseTime, that.reviseTime) &&
                    Objects.equals(items, that.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, classify, name, describe, sort, operate, dataStatus, createTime, reviseTime, items);
        }

}
