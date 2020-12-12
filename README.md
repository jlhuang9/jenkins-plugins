
![avatar](./doc/description.png)

#environment
------

|       | version |
| ----- | ------- |
| jdk   | 1.8     |
| node  | v8.10.0 |
| npm   | 5.6.0   |
| mongodb | 4.4     |
| maven | 3.6.3   |

------
#build

- jvm and plugin
----------------
```shell script
mvn clean install -Dmaven.test.skip=true
cd jvm/target
java -jar jvm*.jar
```
---------------
- jvm-ui
```shell script
cd jvm -ui
npm install
npm run dev
```
