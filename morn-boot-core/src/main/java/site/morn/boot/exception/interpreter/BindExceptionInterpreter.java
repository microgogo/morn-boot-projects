package site.morn.boot.exception.interpreter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import site.morn.bean.annotation.Target;
import site.morn.exception.ExceptionInterpreter;
import site.morn.exception.ExceptionMessage;

/**
 * 数据绑定异常解释器
 *
 * <p>处理Spring validation 相关异常
 *
 * @author timely-rain
 * @since 1.0.0, 2018/8/21
 */
@Target(BindException.class)
public class BindExceptionInterpreter implements ExceptionInterpreter {

  @Override
  public ExceptionMessage resolve(Throwable throwable) {
    BindException bindException = (BindException) throwable;
    // 获取全部属性错误
    List<FieldError> errors = bindException.getFieldErrors();
    List<String> messages = new ArrayList<>();
    for (FieldError error : errors) {
      messages.add(generateMessage(error));
    }
    ExceptionMessage exceptionMessage = new ExceptionMessage();
    exceptionMessage.setMessage(StringUtils.collectionToCommaDelimitedString(messages));
    return exceptionMessage;
  }

  /**
   * 生成错误信息
   *
   * @param fieldError 属性错误
   * @return 错误信息
   */
  private String generateMessage(FieldError fieldError) {
    return fieldError.getObjectName() + "." + fieldError.getField() + fieldError
        .getDefaultMessage();
  }
}
