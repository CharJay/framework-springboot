package com.framework.core.utils.helper;

/**
 * 提供通用的编码定义,最大值3000.<br>
 * 若使用自定义的编码值,使用大于3000的值.
 * 
 * @author
 */
public enum CodeEnum {

    OK(0, "OK"),
    
    //400-499 客户端异常操作
    /**
     * 登录，token失效
     */
    LOGON_FAILURE(401, "登录失效"),
    /**
     * 非法操作或没有权限
     */
    PERMISSION_DENIED(403, "无执行权限"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
	/**
	 * 处理超时
	 */
	TIMEOUT(408, "处理超时"),
	/**
	 * 入参不符合约定
	 */
	PARAMS_ERROR(412, "入参不符合约定"),
	/**
	 * 请求实体过大
	 */
	ENTITY_TOO_LARGE(413, "请求实体过大"),
	/**
	 * 接口调用频繁
	 */
	FREQUENCY(416, "接口调用频繁"),
	/**
	 * 重复操作
	 */
	REPETITION(417, "重复操作"),
    
    
	//500-599 服务端异常
    /**
     * 接口出现异常
     */
    ERROR(500, "接口出现异常"),
    /**
     * 接口维护中，暂时关闭
     */
    UNAVAILABLE(503, "接口维护中"),
    /**
     * 服务异常
     */
    SERVER_ERROR(504, "服务异常"),
    // ------go on------
    
    CODE_101(101,"请求失败"),
	CODE_102(102,"参数不正确"),
	CODE_103(103,"空数据"),
	CODE_104(104,"token不存在或已失效"),
	CODE_105(105,"token不允许为空"),
	CODE_106(106,"用户信息不存在"),
	CODE_107(107,"用户不存在或密码错误"),
	CODE_108(108,"密码错误,您的账户已锁定,请2小时后再试"),
	CODE_109(109,"您的账户已锁定,请稍后再试"),
	CODE_110(110,"密码不正确"),
	CODE_111(111,"appid为空或不存在"),
	CODE_112(112,"签名不正确"),
	CODE_113(113,"服务编码不允许为空"),
    ;

    /**
     * 编码
     */
    private int    code;
    /**
     * 说明
     */
    private String descr;

    private CodeEnum(int code, String descr) {
        if (code > 3000)
            throw new IndexOutOfBoundsException("code值不要超过3000");
        this.code = code;
        this.descr = descr;
    }

    public int getCode() {
        return code;
    }

    public String getDescr() {
        return descr;
    }

}
