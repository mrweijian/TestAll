package com.web;

import com.web.annotaion.Controller;
import com.web.annotaion.RequestMapping;
import com.web.annotaion.ResponseBody;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class ServletDemo extends HttpServlet {

    private static String XML_PATH_LOCAL= "xmlPathLocal";

    private static String projectPath = ServletDemo.class.getResource("/").getPath();

    private static String COMPENT_SCAN_ELEMENT_NAME = "compentScan";

    private static String  COMPENT_SCAN_ELEMENT_PACKAGE_NAME= "package";

    private HashMap<String,Method> methodMap = new HashMap<>();

    private  static String prefix = "";
    private  static String suffix = "";
    @Override
    public void init(ServletConfig config) {

        String initParameter = config.getInitParameter(XML_PATH_LOCAL);
        File file = new File(projectPath + "//" + initParameter);
        Document document = prase(file);
        Element rootElement = document.getRootElement();
        Element compentScan = rootElement.element(COMPENT_SCAN_ELEMENT_NAME);
        String value = compentScan.attribute(COMPENT_SCAN_ELEMENT_PACKAGE_NAME).getValue();

        Element view = rootElement.element("view");
        prefix = view.attribute("prefix").getValue();
        suffix = view.attribute("suffix").getValue();

        projectPath = projectPath.replaceAll("%20"," ");
        String pakgePath = projectPath + "\\" + value;
        scanProjectByPath(pakgePath);
    }

    public void scanProjectByPath(String path){
        File file =new File(path);
        //递归解析项目所有文件
        scanFile(file);
    }

    public void scanFile(File file){
        if(file.isDirectory()){
            for (File listFile : file.listFiles()) {
                scanFile(listFile);
            }
        }else {
            String filePath = file.getPath();
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            if(suffix.equals(".class")){
                String classPath = filePath.replace(new File(projectPath).getPath()+"\\" ,"");
                classPath = classPath.replaceAll("\\\\",".");
                System.out.println("classpath: " + classPath);
                String className = classPath.substring(0,classPath.lastIndexOf("."));

                //解析出class文件之后进行class的处理
                try {
                    Class<?> clazz = Class.forName(className);
                    if(clazz.isAnnotationPresent(Controller.class)){
                        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                        String classRequestMappingUrl = "";
                        if(requestMapping != null){
                            classRequestMappingUrl = requestMapping.value();
                        }

                        for (Method declaredMethod : clazz.getDeclaredMethods()) {
                            //判断是否是合成函数
                            if(!declaredMethod.isSynthetic()){
                                if(declaredMethod.isAnnotationPresent(RequestMapping.class)){
                                    RequestMapping annotation = declaredMethod.getAnnotation(RequestMapping.class);
                                    if(annotation != null){
                                        String methodRequestMappingUrl = annotation.value();
                                        System.out.println("类:"+clazz.getName()+"的"+declaredMethod.getName()+"方法被映射到了"
                                                +classRequestMappingUrl+methodRequestMappingUrl+"上面");
                                        String methodPath = classRequestMappingUrl+methodRequestMappingUrl;
                                        if(methodMap.get(methodPath) != null){
                                            throw new IllegalAccessException();
                                        }
                                        methodMap.put(methodPath,declaredMethod);
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 采用dom4j解析xml文件
     * @param file
     * @return
     */
    public Document prase(File file){
        SAXReader saxReader = new SAXReader();
        try {
            return  saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletContext().getContextPath();
        System.out.println("项目名称："+path);
        String requestURI = req.getRequestURI();
        System.out.println("requestURI-1: "+ requestURI);
        requestURI = requestURI.replace(path,"");
        System.out.println("requestURI-2: "+ requestURI);
        Method method = methodMap.get(requestURI);
        if(method == null){
            return;
        }
        Parameter[] parameters = method.getParameters();
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i<parameters.length; i++){
            Parameter parameter = parameters[i];
            String name = parameter.getName();
            Class<?> type = parameter.getType();
            if(type.equals(String.class)){
                objects[i] = req.getParameter(name);
            }else if(type.equals(HttpServletRequest.class)){
                objects[i] = req;
            }else if (type.equals(HttpServletResponse.class)){
                objects[i] = resp;
            }else {
                try {
                    Object o = type.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        String fieldName = field.getName();
                        field.set(o,req.getParameter(fieldName));
                    }
                    objects[i] = o;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        try {
            Object invoke = method.invoke(method.getDeclaringClass().newInstance(), objects);
            if (!method.getReturnType().equals(Void.class)) {
                ResponseBody annotation = method.getAnnotation(ResponseBody.class);
                if(annotation != null){
                    if(invoke != null){
                        resp.getWriter().write(String.valueOf(invoke));
                    }
                }else {
                    req.getRequestDispatcher(prefix+String.valueOf(invoke)+suffix).forward(req,resp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
