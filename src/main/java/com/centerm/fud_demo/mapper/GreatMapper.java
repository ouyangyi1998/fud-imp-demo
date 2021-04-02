package com.centerm.fud_demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ouyangyi
 */
@Mapper
public interface GreatMapper {
    /**
     * 返回问题的总点赞数目
     * @param questionId
     * @return
     */
    @Select("select count(1) from great where question_id = #{questionId}")
    Integer getAllGreatByQuestion(int questionId);

    /**
     * 返回问题下面的某个回复的点赞数目
     * @param questionId
     * @param commentId
     * @return
     */
    @Select("select count(1) from great where question_id = #{questionId} and comment_id = #{commentId}")
    Integer getGreatByQuestionAndComment(int questionId,int commentId);


    /**
     * 插入点赞数目
     * @param questionId
     * @param commentId
     * @param userId
     */
    @Insert("insert into great(question_id,comment_id,user_id) values (#{questionId},#{commentId},#{userId}) ")
    void insertGreatByQuestionAndComment(int questionId, int commentId, int userId);

    /**
     * 移除点赞数目
     * @param questionId
     * @param commentId
     * @param userId
     */
    @Delete("delete from great where question_id = #{questionId} and comment_id = #{commentId} and user_id = #{userId}")
    void removeGreatByQuestionAndComment(int questionId, int commentId, int userId);

    /**
     * 查询之前用户是否点赞
     * @param questionId
     * @param commentId
     * @param userId
     * @return
     */
    @Select("select count(1) from great where question_id = #{questionId} and comment_id = #{commentId} and user_id = #{userId}")
    Integer selectGreatbyQuestionAndComment(int questionId,int commentId,int userId);
}
