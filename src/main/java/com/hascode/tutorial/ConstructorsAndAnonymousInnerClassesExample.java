package com.hascode.tutorial;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Produces the following classes.
 *
 * <pre>
 * class Outer {
 * 	public Outer(final String str) {
 * 		System.out.println(&quot;outer created with &quot; + str);
 * 	}
 *
 * 	class Inner {
 * 		public void runInner() {
 * 			new Thread(new Runnable() {
 * 				&#064;Override
 * 				public void run() {
 * 					System.out.println(&quot;inner runs in a thread&quot;);
 * 				}
 * 			}).start();
 * 		}
 * 	}
 * }
 * </pre>
 */
public class ConstructorsAndAnonymousInnerClassesExample {
	public static void main(final String[] args) throws IOException {
		MethodSpec run = MethodSpec.methodBuilder("run").addModifiers(Modifier.PUBLIC).returns(void.class).addStatement("$T.out.println($S)", System.class, "inner runs in a thread")
				.addAnnotation(Override.class).build();
		TypeSpec anonRunnable = TypeSpec.anonymousClassBuilder("").addSuperinterface(Runnable.class).addMethod(run).build();
		MethodSpec runInner = MethodSpec.methodBuilder("runInner").addModifiers(Modifier.PUBLIC).addStatement("new $T($L)", Thread.class, anonRunnable).build();
		TypeSpec inner = TypeSpec.classBuilder("Inner").addMethod(runInner).build();
		MethodSpec outerConstructor = MethodSpec.constructorBuilder().addParameter(String.class, "str", Modifier.FINAL)
				.addStatement("$T.out.println($S+$N)", System.class, "outer created with ", "str").addModifiers(Modifier.PUBLIC).build();
		TypeSpec outer = TypeSpec.classBuilder("Outer").addMethod(outerConstructor).addType(inner).build();
		JavaFile javaFile = JavaFile.builder("com.hascode.tutorial", outer).build();
		javaFile.writeTo(System.out);
	}

}

class Outer {
	public Outer(final String str) {
		System.out.println("Outer created with " + str);
	}

	class Inner {
		public void runInner() {
			new Thread(() -> System.out.println("inner runs in a thread")).start();
		}
	}
}
