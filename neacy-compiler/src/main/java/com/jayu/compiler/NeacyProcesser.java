package com.jayu.compiler;

import com.google.auto.service.AutoService;
import com.jayu.annotation.BindView;
import com.jayu.annotation.Intent;
import com.jayu.annotation.OnClick;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * @author yuzongxu
 * @since 2017/5/21
 */
@AutoService(Processor.class)
public class NeacyProcesser extends AbstractProcessor {

    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        types.add(Intent.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private Map<String, AnnotateClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();

        try {
            processBindView(roundEnv);
            processOnClick(roundEnv);
            processIntent(roundEnv);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            return true; // stop process
        }

        for (AnnotateClass annotatedClass : mAnnotatedClassMap.values()) {
            try {
                info("Generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(mFiler);
            } catch (IOException e) {
                error("Generate file failed, reason: %s", e.getMessage());
                return true;
            }
        }
        return true;
    }

    private void processBindView(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            // TODO: 16/8/4 检查 字段 的修饰符
            AnnotateClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
        }
    }

    private void processIntent(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Intent.class)) {
            AnnotateClass annotateClass = getAnnotatedClass(element);
            IntentField field = new IntentField(element);
            annotateClass.addIntent(field);
        }
    }

    private void processOnClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)) {
            AnnotateClass annotatedClass = getAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addMethod(method);
        }
    }

    private AnnotateClass getAnnotatedClass(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotateClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotateClass(classElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
