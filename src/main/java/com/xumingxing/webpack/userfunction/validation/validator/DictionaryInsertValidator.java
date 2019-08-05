package com.xumingxing.webpack.userfunction.validation.validator;

import com.xumingxing.webpack.userfunction.entity.Dictionary;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/8/111:42
 * Description: Version 1.0
 * Location: webpack
 */
public class DictionaryInsertValidator implements Validator {

    /**
     * 此校验器仅支持 Dictionary 实例
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Dictionary.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors,"name","name.empty");

        Dictionary dictionary = (Dictionary) target;
        if (dictionary.getDataStatus() < 0 || dictionary.getDataStatus()> 2) {
            errors.rejectValue("type","type illegal");
        }

    }
}
