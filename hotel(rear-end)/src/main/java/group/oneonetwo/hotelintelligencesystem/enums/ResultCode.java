package group.oneonetwo.hotelintelligencesystem.enums;

public enum ResultCode {
    SUCCESS("200", "操作成功"),
    NOT_FOUND("404", "无效的请求"),
    FAILED("500", "服务器错误"),
    NO_DATA("600","找不到该数据"),
    UNKNOWN("9999", "未知错误"),
    VALIDATE_FAILED("5000", "参数校验失败");

    private String code;
    private String msg;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
