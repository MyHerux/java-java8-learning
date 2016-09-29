##optional类
- **创建一个空Optional对象**
输出的是一个空的optional对象
```
 Optional<String> optional = Optional.empty();
 System.out.println(optional);
 ##:Optional.empty
```
- **创建一个非空Optional对象**
如果person是null，将会立即抛出,而不是访问person的属性时获得一个潜在的错误

```
 Person person = new Person("xu","hua");
 Optional<Person> optional2 = Optional.of(person);
 System.out.println(optional2);
 System.out.println(optional2.get());
 System.out.println(optional2.get().firstName);
 ##:Optional[xuhua]
    xuhua
    xu
```
- **判断对象是否存在**
```
 System.out.println(optional.isPresent());
 System.out.println(optional2.isPresent());
 ##:false
    true
```
- **如果Optional为空返回默认值**
```
 System.out.println(optional.orElse("fallback"));
 optional.ifPresent(System.out::println);
 ##:fallback
    xuhua
```