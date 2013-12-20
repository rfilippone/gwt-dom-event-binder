##What is GWT DOM EventBinder?
GWT's UiBinder is great in many ways. 
Let's refresh some ideas at the base of UiBinder.

From the [UiBinder Overview](http://www.gwtproject.org/doc/latest/DevGuideUiBinder.html#Overview):
> At heart, a GWT application is a web page. And when you're laying out a web page, writing HTML and CSS is the most natural way to get the job done. The UiBinder framework allows you to do exactly that: build your apps as HTML pages with GWT widgets sprinkled throughout them.

And from the [Simple binding of event handlers](http://www.gwtproject.org/doc/latest/DevGuideUiBinder.html#Simple_binding):
>One of UiBinder's goals is to reduce the tedium of building user interfaces in Java code, and few things in Java require more mind-numbing boilerplate than event handlers.

> .....

>In a UiBinder owner class, you can use the @UiHandler annotation to have all of that anonymous class nonsense written for you.

>```java
public class MyFoo extends Composite {
  @UiField Button button;
>
  public MyFoo() {
    initWidget(button);
  }
>
  @UiHandler("button")
  void handleClick(ClickEvent e) {
    Window.alert("Hello, AJAX");
  }
}
```
>However, there is one limitation (at least for now): you can only use @UiHandler with events thrown by widget objects, not DOM elements. That is, &lt;g:Button&gt;, not &lt;button&gt;.

**The last sentence explains the limitation that this library is trying to remove.**

##How do I use it?
Easy - just decide the event that you want to handle and the DOM element that generates the event, register an handler methods for it, and that's all.

###Registering DOM event handlers
DOM event handlers are methods annotated with the @DomEventHandler annotation. Registration of handlers works the same way as @UiHandler - the name of the method is ignored, the argument to the method is checked to determine what type of event to handle, and the string parameter of the annotation is used to determine the DOM element. In order to get this to work, you must also define an DomEventBinder interface and invoke bindEventHandlers on it in the same way you would for a UiBinder. Here's an example:
```java
public class HelloWidgetWorld extends Composite {

  interface MyUiBinder extends UiBinder<Widget, HelloWidgetWorld> {}
  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyEventBinder extends DomEventBinder<HelloWidgetWorld> {}
  private final MyEventBinder eventBinder = GWT.create(MyEventBinder.class);

  @UiField ButtonElement button;

  public HelloWidgetWorld() {
    initWidget(uiBinder.createAndBindUi(this));
    eventBinder.bindEventHandlers(this);
  }

  @DomEventHandler("button")
  void handleClick(ClickEvent e) {
    Window.alert("Hello, AJAX");
  }
}
```

##How do I install it?
If you're using Maven, you can add the following to your &lt;dependencies&gt; section:
```xml
<dependency>
  <groupId>com.webrippers</groupId>
  <artifactId>gwt-dom-event-binder</artifactId>
  <version>0.0.2</version>
</dependency>
```
You can dowload directy the [jar](https://oss.sonatype.org/content/repositories/releases/com/webrippers/gwt-dom-event-binder/0.0.2/gwt-dom-event-binder-0.0.2.jar), or you can also check out the source using git from [https://github.com/rfilippone/gwt-dom-event-binder.git](https://github.com/rfilippone/gwt-dom-event-binder.git) and build it yourself. Once you've installed DOM EventBinder, be sure to inherit the module in your .gwt.xml file like this:
```xml
<inherits name='com.webrippers.gwt.dom.event.DomEventBinder'/>
```

## Version history

### 0.0.2

 * EventBinder.bindEventHandler is now overloaded to take 2 parameters. The second parameter is any object inheriting from IsWidget. The event handlers will be attached on the second object 

### 0.0.1
 * Initial release.

