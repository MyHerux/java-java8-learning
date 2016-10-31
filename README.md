# java-java8-learning
关于java8新特性的学习使用的例子

## 项目结构
- src 目录<br>
Java8 Learning (java8学习) <br>

- 包目录：
  
        ├── com.xu.java8.lambda	    // Lambda表达式
	    ├── com.xu.java8.Optional	// java8-Optional类
	    ├── com.xu.java8.Stream	    // java8-Stream接口
	    
- java8-Optional类

[Optional类的一些基本用法](https://github.com/MyHerux/java-java8-learning/blob/master/src/main/java/com/xu/java8/Optional/Optional.md)

- java8-Lambda表达式

[Lambda表达式的一些基本用法](https://github.com/MyHerux/java-java8-learning/blob/master/src/main/java/com/xu/java8/lambda/Lambda.md)

- java8-Stream接口

[Stream接口的一些基本用法](https://github.com/MyHerux/java-java8-learning/blob/master/src/main/java/com/xu/java8/Stream/Stream.md)


```flow
st=>start: Start|past:>http://www.google.com[blank]
e=>end: End:>http://www.google.com
op1=>operation: My Operation|past
op2=>operation: Stuff|current
sub1=>subroutine: My Subroutine|invalid
cond=>condition: Yes
or No?|approved:>http://www.baidu.com
c2=>condition: Good idea|rejected
io=>inputoutput: catch something...|request

st->op1(right)->cond
cond(yes, right)->c2
cond(no)->sub1(left)->op1
c2(yes)->io->e
c2(no)->op2->e
```