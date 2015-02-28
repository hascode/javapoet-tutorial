package com.hascode.tutorial;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Generates the following code, one method uses name references to the other
 * generated method.
 * 
 * <pre>
 * package com.hascode.tutorial;
 * 
 * import java.lang.System;
 * 
 * class NumberUtil {
 * 	public long doubleNumber(long number) {
 * 		return number * 2;
 * 	}
 * 
 * 	public void printDoubleNumber(long number) {
 * 		System.out.println(&quot;Your number doubled is: &quot; + doubleNumber(number));
 * 	}
 * }
 * </pre>
 */
public class SelfReferencesExample {

	public static void main(final String[] args) throws IOException {
		MethodSpec doubleNumber = MethodSpec.methodBuilder("doubleNumber").addModifiers(Modifier.PUBLIC).addParameter(long.class, "number").returns(long.class).addStatement("return $L*2", "number")
				.build();
		MethodSpec printDoubleNumber = MethodSpec.methodBuilder("printDoubleNumber").addModifiers(Modifier.PUBLIC).addParameter(long.class, "number")
				.addStatement("$T.out.println(\"Your number doubled is: \"+$N($L))", System.class, doubleNumber, "number").build();
		TypeSpec numberUtil = TypeSpec.classBuilder("NumberUtil").addMethod(doubleNumber).addMethod(printDoubleNumber).build();
		JavaFile javaFile = JavaFile.builder("com.hascode.tutorial", numberUtil).build();
		javaFile.writeTo(System.out);
	}

	public static int fib(final int number) {
		if (number == 1 || number == 2) {
			return 1;
		}

		return fib(number - 1) + fib(number - 2);
	}
}
