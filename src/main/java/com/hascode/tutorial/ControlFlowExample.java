package com.hascode.tutorial;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Generates the following code
 *
 * <pre>
 * package com.hascode.tutorial;
 *
 * class Counter {
 * 	public void count() {
 * 		for (int i = 0; i &lt; 10; i++) {
 * 			total += i;
 * 		}
 * 	}
 * }
 * </pre>
 */
public class ControlFlowExample {
	public static void main(final String[] args) throws IOException {
		MethodSpec count = MethodSpec.methodBuilder("count").addModifiers(Modifier.PUBLIC).beginControlFlow("for (int i = 0; i < 10; i++)").addStatement("total += i").endControlFlow().build();
		TypeSpec counter = TypeSpec.classBuilder("Counter").addMethod(count).build();
		JavaFile javaFile = JavaFile.builder("com.hascode.tutorial", counter).build();
		javaFile.writeTo(System.out);
	}

}
