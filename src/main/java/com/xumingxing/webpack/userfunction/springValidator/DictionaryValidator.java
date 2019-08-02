package com.xumingxing.webpack.userfunction.springValidator;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/8/111:42
 * Description: Version 1.0
 * Location: webpack
 */

import com.xumingxing.webpack.userfunction.entity.Dictionary;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DictionaryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        //对上诉的User进行数据校验
        return Dictionary.class.equals(clazz);
    }

    //对目标类target进行校验，并将校验错误记录在errors中
    //Errors是Spring用来存放错误信息的对象
    @Override
    public void validate(Object object, Errors errors) {
        // 验证name
        ValidationUtils.rejectIfEmpty(errors, "name", null, "选项名称不能为空");
        ValidationUtils.rejectIfEmpty(errors, "sort", null, "序列不能为空");
    }
}
