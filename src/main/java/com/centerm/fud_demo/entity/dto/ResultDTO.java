package com.centerm.fud_demo.entity.dto;

import lombok.Data;

/**
 * @author ouyangyi
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultDTO success(){
        ResultDTO resultDto=new ResultDTO();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        return resultDto;
    }
    public <T> ResultDTO success(T data){
        ResultDTO resultDto=new ResultDTO();
        resultDto.setCode(200);
        resultDto.setMessage("成功");
        resultDto.setData(data);
        return resultDto;
    }
}
