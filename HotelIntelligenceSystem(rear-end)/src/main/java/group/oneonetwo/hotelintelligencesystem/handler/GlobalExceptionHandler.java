package group.oneonetwo.hotelintelligencesystem.handler;

import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 文
 * @description 全局异常捕捉
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    private static final String LOG_EXCEPTION_FORMAT = "Capture Exception By GlobalExceptionHandler: Code: %s Detail: %s";
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 运行时异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Reply runtimeExceptionHandler(RuntimeException ex) {
        return resultFormat(1001, ex);
    }

    /**
     * 空指针异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Reply nullPointerExceptionHandler(NullPointerException ex) {
        log.error("NullPointerException : ");
        return resultFormat(1002, ex);
    }

    /**
     * 类型转换异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    public Reply classCastExceptionHandler(ClassCastException ex) {
        return resultFormat(1003, ex);
    }

    /**
     * IO异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(IOException.class)
    public Reply ioExceptionHandler(IOException ex) {
        return resultFormat(1004, ex);
    }

    /**
     * 未知方法异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public Reply noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return resultFormat(1005, ex);
    }

    /**
     * 数组越界异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Reply indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return resultFormat(1006, ex);
    }

    /**
     * 400错误:Http消息不可读异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Reply requestNotReadable(HttpMessageNotReadableException ex) {
        log.error("400 error -- requestNotReadable : ");
        return resultFormat(1007, ex);
    }

    /**
     * 400错误:类型不匹配异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public Reply requestTypeMismatch(TypeMismatchException ex) {
        log.error("400 error -- TypeMismatchException : ");
        return resultFormat(1008, ex);
    }

    /**
     * 400错误:缺少Servlet请求参数异常
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Reply requestMissingServletRequest(MissingServletRequestParameterException ex) {
        log.error("400 error -- MissingServletRequest : ");
        return resultFormat(1009, ex);
    }

    /**
     * 405错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Reply request405(HttpRequestMethodNotSupportedException ex) {
        return resultFormat(1010, ex);
    }

    /**
     * 406错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public Reply request406(HttpMediaTypeNotAcceptableException ex) {
        log.error("406 error : ");
        return resultFormat(1011, ex);
    }

    /**
     * 500错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public Reply server500(RuntimeException ex) {
        log.error("500 error : ");
        return resultFormat(1012, ex);
    }

    /**
     * 栈溢出
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({StackOverflowError.class})
    public Reply requestStackOverflow(StackOverflowError ex) {
        return resultFormat(1013, ex);
    }

    /**
     * 除数不能为0
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({ArithmeticException.class})
    public Reply arithmeticException(ArithmeticException ex){
        return resultFormat(1014, ex);
    }

    /**
     * 404错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({NotFoundException.class})
    public Reply notFindException(NotFoundException ex){
        return resultFormat(1015,ex);
    }


    /**
     * 其他系统级错误
     * @author 文
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Reply exception(Exception ex) {
        return resultFormat(1016, ex);
    }

//    /**
//     * token过期异常
//     * @author 文
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler({TokenIsExpiredException.class})
//    public String tokenExpired(TokenIsExpiredException ex){
//        return resultFormat(1040,ex);
//    }

    /**
     * 通用异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler({CommonException.class})
    public Reply commonException(CommonException ex) {
        return resultFormat(ex.getCode(),ex);
    }

    /**
     * 捕捉结果格式化
     * @author 文
     * @param code
     * @param ex
     * @param <T>
     * @return
     */
    private <T extends Throwable> Reply resultFormat(Integer code, T ex) {
        ex.printStackTrace();
        log.error(String.format(LOG_EXCEPTION_FORMAT, code, ex.getMessage()));
        return Reply.failed(String.valueOf(code), ex.getMessage(), null);
    }

}
