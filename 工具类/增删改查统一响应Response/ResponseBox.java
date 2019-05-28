package com.telecom.js.noc.hxtnms.operationplan.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: liuwei
 * Date: 2019-05-27 14:16
 * Desc: 增删改查响应前台结果综合类
 */
@Data
public class ResponseBox<T>{
    //响应结果
    private boolean success;
    //响应信息
    private String msg;
    //返回码
    private Integer responseCode;
    //响应实体类：查详情
    private T detail;
    //响应实体类列表：查列表
    private List<T> list;
    //分页参数
    private Map<String,Integer> page;

    /**
     * 详情查询默认构造
     * @param detail
     * @return
     */
    public ResponseBox (T detail){
        this.setDetail(detail);
        this.setAll(true,"查询成功！",CommonConstants.SUCCESS_CODE);
    }

    /**
     * 列表查询默认构造
     * @param list 查询列表结果
     * @param queryHomeBox 查询box(分页相关参数已被赋值)
     */
    public ResponseBox (List<T> list,QueryHomeBox<T> queryHomeBox){
        this.list = list;
        page = new HashMap<>();
        //每页数量
        page.put("pageDataCount",queryHomeBox.getPageDataCount());
        //查询的页码
        page.put("queryPageNum",queryHomeBox.getQueryPageNum());
        //总数量
        page.put("totalCount",queryHomeBox.getTotalCount());
        //总页码
        page.put("totalPageNum",queryHomeBox.getTotalPageNum());
        this.setAll(true,"查询成功！",CommonConstants.SUCCESS_CODE);
    }

    private ResponseBox(){
        //禁用无参构造
    }

    /**
     * 增删改默认构造
     * @param count 增删改结果count
     */
    public ResponseBox(int count){
        String successMsg = "操作成功！";
        String failMsg = "操作失败！";
        setResponseBox(count,successMsg,failMsg);
    }

    /**
     * 自定义返回信息的增删改构造
     * @param count 增删改结果count
     * @param successMsg 成功时返回的信息
     * @param failMsg 失败时返回的信息
     */
    public ResponseBox(int count,String successMsg,String failMsg){
        setResponseBox(count,successMsg,failMsg);
    }

    /**
     * 参数错误的构造
     * @param failParamMsg 错误参数信息
     */
    public ResponseBox(String failParamMsg){
        setAll(false,failParamMsg,CommonConstants.PARAM_ERROR_CODE);
    }

    private void setAll(boolean success, String msg, Integer responseCode) {
        this.success = success;
        this.msg = msg;
        this.responseCode = responseCode;
    }

    private void setResponseBox(int count,String successMsg,String failMsg){
        //增删改添加了事务，操作失败时返回0
        if ( 0 == count){
            setAll(false,failMsg,CommonConstants.FAILE_CODE);
        }else{
            setAll(true,successMsg,CommonConstants.SUCCESS_CODE);
        }
    }
}
