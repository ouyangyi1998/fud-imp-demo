package com.centerm.fud_demo.entity.dto;

import lombok.Data;

/**
 * 返回参数配置
 * @author ouyangyi
 * @time 返回参数
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 成功默认返回值
     * @return 返回值
     */
    public ResultDTO<T> success(){
        ResultDTO<T> resultDto=new ResultDTO<T>();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        return resultDto;
    }

    /**
     *  成功默认返回值 带数据
     * @param data 数据
     * @return 返回值
     */
    public ResultDTO<T> success(T data){
        ResultDTO<T> resultDto=new ResultDTO<T>();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        resultDto.setData(data);
        return resultDto;
    }
}
