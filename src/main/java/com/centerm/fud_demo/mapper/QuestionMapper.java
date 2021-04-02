package com.centerm.fud_demo.mapper;

import com.centerm.fud_demo.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ouyangyi
 */
@Mapper
public interface QuestionMapper {
    /**
     * 创建问题
     * @param question
     */
    @Insert("insert into question(title,description,create_id,tag,create_time) values (#{title},#{description},#{createId},#{tag},#{createTime})")
    void createQuestion(Question question);

    /**
     * 通过时间拉取问题链路
     * @param offset
     * @param size
     * @return
     */
    @Select("select id,title,description,create_id as createId,comment_count as commentCount,view_count as viewCount,like_count as likeCount,tag,create_time as createTime from question order by create_time desc limit #{offset},#{size} ")
    List<Question> list(@Param("offset") int offset, @Param("size") int size);

    /**
     * 查询问题的数目
     * @return
     */
    @Select("select count(1) from question")
    int count();

    /**
     * 通过用户id查找他之前发送的问题
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    @Select("select id,title,description,create_id as createId,comment_count as commentCount, view_count as viewCount,like_count as likeCount, tag, create_time as createTime from question where create_id=#{userId} limit #{offset},#{size}")
    List<Question> listById(@Param("userId") int userId, @Param("offset") int offset, @Param("size") int size);

    /**
     * 查询该用户发送的问题数目
     * @param userId
     * @return
     */
    @Select("select count(1) from question where create_id=#{userId}")
    int countById(int userId);

    /**
     * 通过id查询问题
     * @param id
     * @return
     */
    @Select("select id,title,description,create_id as createId,comment_count as commentCount, view_count as viewCount,like_count as likeCount, tag, create_time as createTime from question where id=#{id}")
    Question getById(int id);

    /**
     * 通过id更新问题信息
     * @param question
     */
    @Update("update question set title=#{title},description=#{description},tag=#{tag},create_time=#{createTime} where id=#{id}")
    void updateQuestion(Question question);

    /**
     * 更新问题的查看人数
     * @param id
     */
    @Update("update question set view_count=view_count+1 where id=#{id}")
    void updateView(int id);

    /**
     * 更新问题的回复数目
     * @param parentId
     */
    @Update("update question set comment_count=comment_count+1 where id=#{parentId}")
    void updateComment(int parentId);

    /**
     * 通过tag查找问题
     * @param id
     * @param result
     * @return
     */
    @Select("select id,title,description,create_id as createId,comment_count as commentCount, view_count as viewCount,like_count as likeCount, tag, create_time as createTime from question where tag REGEXP #{result} and id!=#{id} limit 0,10")
    List<Question> getByTag(@Param("id") int id, @Param("result") String result);

    /**
     * 通过outerId查询标题
     * @param outerId
     * @return
     */
    @Select("select title from question where id=#{outerId}")
    String getTitleById(int outerId);

    /**
     * 查询访问量前十的问题
     * @return
     */
    @Select("select * from question order by view_count desc limit 0,10")
    List<Question> getTopTen();

    /**
     *
     * @param search
     * @param offset
     * @param size
     * @return
     */
    @Select("select id,title,description,create_id as createId,comment_count as commentCount, view_count as viewCount,like_count as likeCount, tag, create_time as createTime from question where title like #{search} limit #{offset},#{size}")
    List<Question> listBySearch(@Param("search") String search,@Param("offset") int offset, @Param("size") int size);

    /**
     * 查询搜索的个数
     * @param search
     * @return
     */
    @Select("select count(1) from question where title like #{search}")
    int countBySearch(@Param("search") String search);
}
