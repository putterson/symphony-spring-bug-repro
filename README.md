# Description
As of version 1.5.0.BETA, the symphony-api-client-java attempts to iterate over all beans in the context and call
 `getBean()` on the application context for those beans. If you depend on symphony-api-client-java and have beans defined
 that fail to be constructed via `applicationContext.getBean()` then an exception will be thrown that ends your application
  Some examples of beans that could fail to be constructed in this way are ones depending on having an InjectionPoint
  passed into the bean factory and beans that are `request` scoped. Examples are included in this spring boot application. 
 
The code that causes this behaviour is located in the `setApplicationContext()` method in `SlashAnnotationProcessor` in the
 BdkActivityConfig.java file

# Running

From the project root directory run `gradle bootRun`

The application should fail to start with one of the two following exceptions, depending on which example bean was
processed first
- `org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'slashAnnotationProcessor' defined in class path resource [com/symphony/bdk/spring/config/BdkActivityConfig.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'produceReqResource': Scope 'request' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton; nested exception is java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.`
- `org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'slashAnnotationProcessor' defined in class path resource [com/symphony/bdk/spring/config/BdkActivityConfig.class]: Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'produceLogger' defined in class path resource [com/patgoebel/symphonyspringbugrepro/LoggingConfiguration.class]: Unexpected exception during bean creation; nested exception is java.lang.IllegalStateException: No current InjectionPoint available for method 'produceLogger' parameter 0`