package com.web.handler;

import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {

    boolean supportsParameter(Parameter parameter);

    Object resolveArgument()throws Exception;

}
