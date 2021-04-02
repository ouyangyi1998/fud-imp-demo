package com.centerm.fud_demo.mapper;

import com.centerm.fud_demo.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ouyangyi
 */
@Mapper
public interface CommentMapper {
    /**
     * 插入回复内容
     * @param comment
     */
    @Insert("insert into comment(parent_id,type,commentor,create_time,content) " +
            "values (#{parentId},#{type},#{commentor},#{createTime},#{content})")
    void insert(Comment comment);

    /**
     * 通过id获取用户的回复，同时通过回复时间进行排序
     * @param id
     * @return
     */
    @Select("select id,parent_id as parentId,type,commentor,create_time as createTime,like_count as likeCount,content,comment_count as commentCount from comment where parent_id=#{id} order by create_time desc")
    List<Comment> getById(int id);

    /**
     * 通过id获取用户的回复，同时进行时间上的排序（针对性选择回复类型）
     * @param id
     * @param type
     * @return
     */
    @Select("select id,parent_id as parentId,type,commentor,create_time as createTime,like_count as likeCount,content,comment_count as commentCount from comment where parent_id=#{id} and type=#{type} order by create_time desc")
    List<Comment> getCommentById(@Param("id") int id, @Param("type") int type);

    /**
     * 更新回复数目（回复数目+1）
     * @param parentId
     */
    @Update("update comment set comment_count=comment_count+1 where id=#{parentId}")
    void updateCommentCount(int parentId);

    /**
     *通过父回复的id，获取到父回复的内容
     * @param parentId
     * @return
     */
    @Select("select id,parent_id as parentId,type,commentor,create_time as createTime,like_count as likeCount,content,comment_count as commentCount from comment where id=#{parentId}")
    Comment getParentById(int parentId);

    /**
     *通过outerId获取到回复内容
     * @param outerId
     * @return
     */
    @Select("select content from comment where id=#{outerId}")
    String getContentById(int outerId);

    /**
     *通过子回复寻找父回复
     * @param id
     * @return
     */
    @Select("select parent_id from comment where id=#{id}")
    int getParentIdById(int id);
}
