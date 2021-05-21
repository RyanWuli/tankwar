## 坦克大战第一期 v2.0
### 说明记录
加入了单例模式、策略模式 - tank7

加入了工厂模式（其实并不适用，练习设计模式而已）- tank8

使用 mv 模式分离(门面模式-facade) - tank9

tank13 - 减少耦合 gameModel 单例模式，使用的时候直接获取不需要再传参

tank14 - 碰撞采用责任链的方式

tank15 - 14 中加入了装饰模式

tank16 - 14 中 加入了观察者模式（没有必要，只是练习设计模式）

tank17 - 加入了存盘和加载

坦克大战 是结合了设计模式

netty study - netty 的入门代码（后面有 netty 版 tank）

netty study 5 - 加入 serverFrame

netty study 6 - 处理 ui 线程阻塞等问题

netty study 7 - netty 编码解码器的使用




### 测试
包括了 加载项目中的文件方式、netty 编码解码嵌入式测试

unit 用 assets 的好处：修改了代码 直接跑 绿色 就通过，不用每次测试都用人去判断是否通过（相比 println），主要是测试的复用性利用
