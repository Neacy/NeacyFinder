package com.jayu.compiler;

import com.jayu.annotation.Intent;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author yuzongxu
 * @since 2017/6/17
 */
public class IntentField {

    private VariableElement variableElement;
    private String key;

    public IntentField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", Intent.class.getSimpleName()));
        }
        variableElement = (VariableElement) element;
        Intent intent = variableElement.getAnnotation(Intent.class);
        key = intent.value();
    }

    public Name getFieldName() {
        return variableElement.getSimpleName();
    }

    public String getKey() {
        return key;
    }

    public TypeMirror getFieldType() {
        return variableElement.asType();
    }
}
