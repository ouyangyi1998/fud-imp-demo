package com.centerm.fud_demo.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO<T> {
    //问题内容
    private List<T> data;
    //是否展示前一页
    private Boolean showPre;
    //是否展示后一页
    private Boolean showNext;
    //是否展示第一页
    private Boolean showFirst;
    //是否展示最后一页
    private Boolean showLast;
    //当前页码
    private Integer page;
    //当前展示的页码集合
    private List<Integer> pages = new ArrayList<>();
    //所有页数
    private Integer totalPage;

    public void setPagination(int totalCount, int page, int size) {
        this.page = page;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        //page<1就显示1，page>最大页数就显示最大页数
        if (page<1){
            this.page=1;
        }
        if (page>totalPage){
            this.page=totalPage;
        }
        //将需要展示的页码插入到pages中
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        //是否展示上一页
        if (page == 1) {
            showPre = false;
        } else {
            showPre = true;
        }
        //是否展示后一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)) {
            showFirst = false;
        } else {
            showFirst = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showLast = false;
        } else {
            showLast = true;
        }
    }
}
