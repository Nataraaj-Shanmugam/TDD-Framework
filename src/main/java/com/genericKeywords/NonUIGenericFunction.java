package com.genericKeywords;

import com.genericKeywords.Interface.NonUIGenericFunctionInterface;

public class NonUIGenericFunction implements NonUIGenericFunctionInterface {


    @Override
    public String getClassName(Exception exceptionMessage) {
        return exceptionMessage.getStackTrace()[0].getClassName();
    }

    @Override
    public String getMethodName(Exception exceptionMessage) {
        return exceptionMessage.getStackTrace()[0].getMethodName();
    }

    @Override
    public String getClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }

    @Override
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
}
