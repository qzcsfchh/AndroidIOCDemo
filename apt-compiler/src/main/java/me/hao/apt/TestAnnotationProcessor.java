package me.hao.apt;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import me.hao.annotation.Router;
import me.hao.annotation.RouterGenerated;
import me.hao.apt.utils.AptUtil;

@AutoService({Processor.class})
public class TestAnnotationProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        AptUtil.init(processingEnv);
        AptUtil.log("Options = "+processingEnv.getOptions());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<TypeElement> typeElements = ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Router.class));
        if (typeElements.size()>0) {
            // 获取所在包名
            TypeElement typeElement = typeElements.iterator().next();
            AptUtil.log("QualifiedName = "+typeElement.getQualifiedName().toString());
            AptUtil.log("SimpleName = "+typeElement.getSimpleName().toString());
            AptUtil.log("Type = "+typeElement.asType().toString());
            String packageName = AptUtil.getElementPackage(typeElement);
            // 生成第一个类
            TypeSpec generated = TypeSpec.classBuilder("RouterGenerated")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addJavadoc("@generated")
                    .addAnnotation(RouterGenerated.class)
                    .build();
            AptUtil.writeJavaFile(packageName, generated);
            // 生成第二个类
            ClassName routerGenerated = ClassName.get(packageName, "RouterGenerated");
            ClassName listName = ClassName.get("java.util", "List");
            ClassName arrayListName = ClassName.get("java.util", "ArrayList");
            ParameterizedTypeName list = ParameterizedTypeName.get(listName, routerGenerated);
            // register()
            MethodSpec.Builder register = MethodSpec.methodBuilder("register").addModifiers(Modifier.PRIVATE).returns(void.class);
            for (TypeElement element : typeElements) {
                register.addStatement("list.add(new $T())", routerGenerated);
            }
            MethodSpec build = register.build();
            // constructor
            MethodSpec contructor = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PRIVATE)
                    .addStatement("$N()", build)
                    .build();

            TypeSpec routerCenter = TypeSpec.classBuilder("RouterCenter")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addJavadoc("@generated")
                    .addField(routerGenerated,"instance",Modifier.PRIVATE,Modifier.STATIC,Modifier.VOLATILE)
                    .addField(FieldSpec.builder(list,"list",Modifier.PRIVATE,Modifier.FINAL).initializer("new $T()", arrayListName).build())
                    .addMethod(contructor)
                    .addMethod(build)
                    .build();

            AptUtil.writeJavaFile(packageName,routerCenter);
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Router.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
