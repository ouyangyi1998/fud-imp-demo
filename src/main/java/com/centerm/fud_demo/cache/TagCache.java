package com.centerm.fud_demo.cache;

import com.centerm.fud_demo.entity.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 论坛Tag参数
 * @author ouyangyi
 * @time 2021.2.23
 */
public class TagCache {

    /**
     * 获取到论坛Tag表单
     * @return Tag表单
     */
    public List<TagDTO> getTags(){
        List<TagDTO> tags=new ArrayList<>();

        TagDTO language=new TagDTO();
        language.setCategoryName("论坛交流");
        language.setTags(Arrays.asList("电影","书籍","图片","游戏","主机","数码"));
        tags.add(language);

        TagDTO tools=new TagDTO();
        tools.setCategoryName("问题反馈");
        tools.setTags(Arrays.asList("系统BUG","申请解除封禁","咨询管理员"));
        tags.add(tools);

        return tags;
    }
}
