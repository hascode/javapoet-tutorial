package com.hascode.tutorial;

import java.io.IOException;

import javax.lang.model.element.Modifier;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Produces the following JAX-RS Spec based RESTful Webservice:
 *
 * <pre>
 * &#064;Path(&quot;book&quot;)
 * class BookService {
 * 	&#064;Path(&quot;/{id}&quot;)
 * 	&#064;GET
 * 	public Response findById(@PathParam(&quot;id&quot;) final long id) {
 * 		return Response.ok().build();
 * 	}
 * }
 * </pre>
 *
 */
public class JAXRSAnnotationExample {
	public static void main(final String[] args) throws IOException {
		AnnotationSpec path = AnnotationSpec.builder(Path.class).addMember("value", "$S", "/{id}").build();
		AnnotationSpec classParam = AnnotationSpec.builder(Path.class).addMember("value", "$S", "book").build();
		AnnotationSpec pathParam = AnnotationSpec.builder(PathParam.class).addMember("value", "$S", "id").build();
		ParameterSpec id = ParameterSpec.builder(long.class, "id", Modifier.FINAL).addAnnotation(pathParam).build();
		MethodSpec findById = MethodSpec.methodBuilder("findById").addAnnotation(path).addAnnotation(GET.class).returns(Response.class).addParameter(id)
				.addStatement("return $T.ok().build()", Response.class).build();
		TypeSpec bookService = TypeSpec.classBuilder("BookService").addMethod(findById).addAnnotation(classParam).build();
		JavaFile javaFile = JavaFile.builder("com.hascode.tutorial", bookService).build();
		javaFile.writeTo(System.out);
	}
}