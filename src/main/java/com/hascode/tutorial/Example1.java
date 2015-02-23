package com.hascode.tutorial;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * In the first example we're creating the following class: <code><pre>
 * public class CustomerService {
 * 	public String greetCustomer(String name){
 * 		return "Welcome, "+name;
 * 	}
 * }
 * </pre></code>
 */
public class Example1 {
	public static void main(final String[] args) throws IOException {
		MethodSpec greetCustomer = MethodSpec.methodBuilder("greetCustomer").addModifiers(Modifier.PUBLIC).returns(String.class).addParameter(String.class, "name")
				.addStatement("return $S+$N", "Welcome, ", "name").build();
		TypeSpec customerService = TypeSpec.classBuilder("CustomerService").addModifiers(Modifier.PUBLIC).addMethod(greetCustomer).build();
		JavaFile javaFile = JavaFile.builder("com.hascode.tutorial", customerService).build();
		javaFile.writeTo(System.out);
	}
}
