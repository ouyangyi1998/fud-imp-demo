package com.centerm.fud_demo.mapper;

import com.centerm.fud_demo.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ouyangyi
 */
@Mapper
public interface NotificationMapper {
    /**
     *插入提醒
     * @param notification
     */
    @Insert("insert into notification(notifier,receiver,outer_id,type,create_time,status) values (#{notifier},#{receiver},#{outerId},#{type},#{createTime},#{status})" )
    void insert(Notification notification);

    /**
     *计算提醒的次数
     * @param id
     * @return
     */
    @Select("select count(1) from notification where receiver=#{id}")
    int count(int id);

    /**
     *通过接收者id，按时间顺序拉取提醒（同时控制数目和开始位置）
     * @param id
     * @param offset
     * @param size
     * @return
     */
    @Select("select id,notifier,receiver,outer_id as outerId,type,create_time as createTime,status from notification where receiver=#{id} order by create_time desc limit #{offset},#{size}")
    List<Notification> list(@Param("id") int id, @Param("offset") int offset, @Param("size") int size);

    /**
     *获取为查看的提醒个数
     * @param id
     * @return
     */
    @Select("select count(1) from notification where receiver=#{id} and status=0")
    int getUnreadCount(int id);

    /**
     *更新提醒状态
     * @param id
     */
    @Update("update notification set status=1 where id=#{id} and status=0")
    void updateStatus(int id);

    /**
     *获取到回复的状态
     * @param id
     * @return
     */
    @Select("select type from notification where id=#{id}")
    int getTypeById(int id);

    /**
     *获取到outerId
     * @param id
     * @return
     */
    @Select("select outer_id from notification where id=#{id}")
    int getOuterIdById(int id);
}
