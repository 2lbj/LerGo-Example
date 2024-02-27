package com.xxxxxx.common.exception;

import com.lergo.framework.exception.BizException;
import com.xxxxxx.common.constants.BizErrorEnum;

public class BizEnumException extends BizException {

    public BizEnumException(BizErrorEnum bizErrorEnum) {
        super(bizErrorEnum.getMessage());
        super.code = bizErrorEnum.getCode();
    }

    public BizEnumException(BizErrorEnum bizErrorEnum, Throwable throwable) {
        super(bizErrorEnum.getMessage(), throwable);
        super.code = bizErrorEnum.getCode();
    }
}
