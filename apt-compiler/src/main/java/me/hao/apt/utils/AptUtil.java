package me.hao.apt.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class AptUtil {
    private static Filer mFiler;
    private static Messager mMessager;
    private static Elements mElementUtils;
    private static Types mTypeUtils;
    /**
     * 支持通过annotationProcessorOptions进行传参，options是一个map
     * <pre>
     * android.defaultConfig.javaCompileOptions.annotationProcessorOptions{
     *     includeCompileClasspath = true
     * }
     * </pre>
     */
    private static Map<String, String> mOptions;

    public static void init(ProcessingEnvironment processingEnv) {
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mOptions = processingEnv.getOptions();
        mTypeUtils = processingEnv.getTypeUtils();
//        SourceVersion sourceVersion = processingEnv.getSourceVersion();
//        Locale locale = processingEnv.getLocale();
    }


    public static String getElementPackage(Element element) {
        PackageElement packageElement = mElementUtils.getPackageOf(element);
        if (packageElement == null) {
            err("can not get package name of element :" + element);
            return null;
        }
//            String qualifiedName = typeElements.iterator().next().getQualifiedName().toString();
//            String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
        return packageElement.toString();
    }

    public static ClassName getClassName(String classFullName) {
        int index = classFullName.lastIndexOf(".");
        String packageName = classFullName.substring(0, index);
        String simpleName = classFullName.substring(index + 1);
        return ClassName.get(packageName, simpleName);
    }

    /**
     * 返回List泛型：List<T>
     *
     * @param typeName the type name
     * @return the parameterized type name
     * @author huanghao6 Created on 2020-07-20
     */
    public static ParameterizedTypeName getListName(TypeName typeName) {
        ClassName list = getClassName("java.util.List");
        return ParameterizedTypeName.get(list, typeName);
    }

    /**
     * 返回map泛型：Map<K,V>
     *
     * @param keyName   the key name
     * @param valueName the value name
     * @return the parameterized type name
     * @author huanghao6 Created on 2020-07-20
     */
    public static ParameterizedTypeName getMapName(TypeName keyName, TypeName valueName) {
        ClassName map = getClassName("java.util.Map");
        return ParameterizedTypeName.get(map, keyName, valueName);
    }


    public static boolean isPublic(Element element){
        return element.getModifiers().contains(Modifier.PUBLIC);
    }

    public static boolean isAbstract(Element element) {
        return element.getModifiers().contains(Modifier.ABSTRACT);
    }

    public static void writeJavaFile(String packageName, TypeSpec typeSpec) {
        try {
            JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public static void err(String msg) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
