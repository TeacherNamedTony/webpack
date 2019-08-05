package com.xumingxing.webpack.userfunction.validation.validate;

import com.xumingxing.webpack.userfunction.entity.Dictionary;
import com.xumingxing.webpack.userfunction.validation.validator.DictionaryInsertValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/8/111:42
 * Description: Version 1.0
 * Location: webpack
 */
public class DictionaryValidate {

    private final static Validator validator = new DictionaryInsertValidator();

    public static Dictionary validate(Dictionary dictionary) {
        Errors errors = new BeanPropertyBindingResult(dictionary, "dictionary");
        validator.validate(dictionary,errors);
        if (errors.hasErrors()){
            throw new ServerWebInputException(errors.toString());
        }
        return dictionary;
    }
}
