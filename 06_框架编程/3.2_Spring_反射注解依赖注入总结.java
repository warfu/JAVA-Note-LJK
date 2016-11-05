分享一下对IOC（控制反转）和DI（依赖注入）的精彩讲解：

* Java反射机制的作用：
    1）在运行时判断任意一个对象'所属的类'
    2）在运行时判断任意一个类所'具有的成员变量和方法'
    3）在运行时任意调用一个'对象的方法'
    4）在运行时构造任意一个'类的对象'

1、什么是反射机制？
    简单说，反射机制值得是程序在'运行时'能够'获取自身的信息'。
        在java中，只要给定'类的名字'，那么就可以通过'反射机制'来获得类的'所有信息'。

2、java 反射机制提供了什么功能？
    在运行时能够判断'任意一个对象所属的类'
    在运行时构造'任意一个类的对象'
    在运行时判断任意一个类所'具有的成员变量和方法'
    在运行时调用任一'对象的方法'
    在运行时创建'新类对象'

3、哪里用到反射机制？
    jdbc中有一行代码：
        Class.forName('com.mysql.jdbc.Driver.class').newInstance(); // 这就是反射？
        // 和 OC 的运行时概念有多少差异。

    那个时候只知道生成驱动对象实例，后来才知道，这就是反射，现在
    很多框架都用到反射机制，SSH、SSM两组三大框架，都是用'反射机制实现'的。

4、反射机制的优缺点？
    静态编译：
            在编译时确定类型，绑定对象，即通过。
    动态编译：
            运行时确定类型，绑定对象。
            动态编译最大限度的发挥了'java的灵活性'，体现了'多态的应用'，有利于降低'类之间的耦合性'。

    * 一句话，反射机制的'优点'就是可以'实现动态创建对象和编译'，
            体现出很大的'灵活性'，特别是在J2EE的开发中，它的灵活性就表现的十分明显。
        比如，一个大型的软件，不可能一次就把把它设计的很完美，当这个程序编译后，发布了，
        当发现需要更新某些功能时，我们不可能要用户把以前的卸载，再重新安装新的版本，
        假如这样的话，这个软件肯定是没有多少人用的。

            采用静态的话，需要把整个程序重新编译一次才可以实现功能的更新，
            采用反射机制的话，它就可以不用卸载，只需要在运行时才动态的创建和编译，就可以实现该功能。

    它的缺点是对性能有影响。
        使用反射基本上是一种解释操作，我们可以告诉 JVM，
        我们希望做什么并且它满足我们的要求。这类操作总是慢于只直接执行相同的操作。

学习过 Spring框架的人一定都会听过 Spring的IoC(控制反转) 、DI(依赖注入)这两个概念，
        对于初学Spring的人来说，总觉得 IoC 、DI这两个概念是模糊不清的，是很难理解的，
    今天和大家分享网上的一些技术大牛们对 Spring框架的IOC的理解以及谈谈 我对 Spring Ioc的理解。
——————————————————————————————————————————————————————————————————————————————————————————————————
一、分享Iteye的开涛对Ioc的精彩讲解
　　原文地址：http://jinnianshilongnian.iteye.com/blog/1413846

1.1、IoC是什么
　　Ioc—Inversion of Control，即"控制反转"，不是什么技术，而是一种'设计思想'。
        在Java开发中，Ioc意味着 '将你设计好的对象' 交给容器控制，
            而不是传统的在你的'对象内部直接控制'。
        如何理解好Ioc呢？理解好Ioc的关键是要明确
            '谁控制谁，控制什么，为何是反转（有反转就应该有正转了），哪些方面反转了'

　　● 谁控制谁，控制什么
        传统Java SE程序设计，我们直接在对象内部通过 new 进行'创建对象'
            是程序主动去创建依赖对象
        而 IoC是有专门 "一个容器"来创建这些对象，即由Ioc容器来控制对象的创建；

        谁控制谁？ 当然是IoC 容器控制了对象；
        控制什么？ 那就是主要控制了 外部资源获取(不只是对象包括比如文件等)。

　　● 为何是反转，哪些方面反转了：
        有反转就有正转，传统应用程序是由我们'自己在对象中'主动控制去'直接获取依赖对象'，'也就是正转'；

        而反转则是由容器来帮忙 创建及注入依赖对象；

        为何是反转？ 因为由'容器帮我们' 查找及注入依赖对象，
                    对象只是被动的接受依赖对象，所以是反转；
        哪些方面反转了？依赖对象的获取被反转了。

　　用图例说明一下，传统程序设计如图2-1，都是主动去创建相关对象然后再组合起来：

* 传统应用程序示意图
　　当有了 IoC/DI的容器后，在客户端类中不再主动去创建这些对象了，如图2-2所示:

*有IoC/DI容器后程序结构示意图
    http://jinnianshilongnian.iteye.com/blog/1413846
    // 客户端 获取


1.2 IoC能做什么
　　IoC 不是一种技术，只是一种思想，一个重要的面向对象编程的法则，
        它能指导我们如何设计出松耦合、更优良的程序。

        传统应用程序都是由我们在 类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；

        有了 IoC容器后，把创建和查找依赖对象的控制权 交给了容器，
        由容器进行注入组合对象，所以 对象与对象之间是 松散耦合，这样也方便测试，利于功能复用，
        更重要的是 使得程序的 整个体系结构变得非常灵活。

　　其实 IoC 对编程带来的最大改变不是从代码上，而是从思想上，
        发生了"主从换位"的变化。
        应用程序原本是老大，要获取什么资源都是主动出击，但是在IoC/DI思想中，
        应用程序就变成被动的了，被动的等待IoC容器来创建并注入它所需要的资源了。

　　IoC很好的体现了面向对象设计法则之一—— 
        好莱坞法则："别找我们，我们找你"；
            即由IoC容器帮对象找相应的 依赖对象并注入，而不是由对象主动去找。

1.3、IoC和DI

　　DI—Dependency Injection，即"依赖注入"：
        组件之间依赖关系由容器在'运行期决定'，形象的说，
        即由容器'动态的'将某个依赖关系'注入到组件之中'。
        依赖注入的目的并非为软件系统带来更多功能，
        而是为了'提升'组件'重用的频率'，并为系统搭建一个灵活、可扩展的平台。

        通过'依赖注入机制'，我们只需要通过'简单的配置'，
            而无需任何代码就可指定'目标需要的资源'，
            完成自身的业务逻辑，而不需要关心具体的'资源来自何处'，'由谁实现'。

　　理解DI的关键是：
        "谁依赖谁，为什么需要依赖，谁注入谁，注入了什么"

　　● 谁依赖于谁：
            当然是应用程序依赖于IoC容器；
　　● 为什么需要依赖：
            应用程序'需要IoC容器'来提供对象'需要的外部资源'；
　　● 谁注入谁：
            很明显是 'IoC容器'  注入  应用程序'某个对象'，应用程序依赖的对象；
　　● 注入了什么：
            就是注入某个对象所需要的'外部资源'（包括'对象'、资源、常量数据）。
　　
    IoC和DI由什么关系呢？
        其实它们是同一个概念的不同角度描述，
        由于控制反转概念比较含糊
            （可能只是理解为 '容器控制对象' 这一个层面，很难让人想到'谁来维护对象关系'），
        所以2004年大师级人物 Martin Fowler又给出了一个新的名字：

        "依赖注入"，相对 IoC 而言，"依赖注入" 明确描述了 "被注入对象依赖IoC容器 配置 依赖对象"。

二、分享Bromon的blog上对IoC与DI浅显易懂的讲解
2.1、IoC(控制反转)

　　首先想说说 IoC (Inversion of Control，控制反转)。
    
    这是 spring的核心，贯穿始终。所谓 IoC，对于spring框架来说，
    就是由 spring 来负责控制对象的生命周期和对象间的关系。

    这是什么意思呢，举个简单的例子，我们是如何找女朋友的？
        常见的情况是，我们到处去看哪里有长得漂亮身材又好的mm，然后打听她们的兴趣爱好、qq号、电话号、ip号、iq号………，
                想办法认识她们，投其所好送其所要，然后嘿嘿……这个过程是复杂深奥的，
                '我们必须自己设计和面对每个环节'。
        传统的程序开发也是如此，在一个对象中，如果要使用另外的对象，就必须得到它（自己new一个，或者从JNDI中查询一个），
                使用完之后还要将对象销毁（比如Connection等），"对象始终会 和 其他的接口 或 类藕合起来"。

    // 讲的很好，注意
　　那么 IoC 是如何做的呢？
        有点像通过婚介找女朋友，在我和女朋友之间引入了一个第三者：
                婚姻介绍所。婚介'管理了'很多男男女女的资料，我可以向婚介提出'一个列表'，告诉它我想找个什么样的女朋友，
                比如长得像李嘉欣，身材像林熙雷，唱歌像周杰伦，速度像卡洛斯，技术像齐达内之类的，然后婚介就会按照我们的要求，提供一个mm，
                我们'只需要'去和她谈恋爱、结婚就行了。简单明了，
        如果婚介给我们的人选'不符合要求'，我们就会抛出异常。
                整个过程'不再由我自己控制'，而是有婚介这样一个'类似容器的机构'来控制。
                Spring所倡导的开发方式就是如此，'所有的类'都会在 Spring容器中"登记"，
                告诉 Spring你是个什么东西，你需要什么东西，然后 Spring会在系统运行到适当的时候，
                把你要的东西'主动给你'，同时也把你交给'其他' '需要'你的东西。
                '所有的类的创建、销毁都由 spring来控制'，
                也就是说控制对象生存周期的'不再是引用它的对象'，而是 Spring。
        
        对于某个具体的对象而言，以前是'它控制其他对象'，现在是所有对象都被 Spring控制，所以这叫 '控制反转'。

2.2、DI(依赖注入)

　　IoC的一个重点是在'系统运行中'，
        动态的向某个对象'提供'它所'需要的其他对象'。
        这一点是通过 DI（Dependency Injection，依赖注入）来实现的。

        比如对象 A需要操作数据库，以前我们总是要在 A中自己编写代码来获得一个Connection对象，
            有了 Spring 我们就只需要告诉spring，A中需要一个Connection，
            至于这个 Connection '怎么构造'，'何时构造'，A不需要知道。
        
        在系统运行时，Spring会在适当的时候制造一个 Connection，
            然后像打针一样，注射到 A当中，这样就完成了对各个对象之间关系的控制。A需要依赖 Connection才能正常运行，而这个Connection是由spring注入到A中的，依赖注入的名字就这么来的。那么DI是如何实现的呢？ Java 1.3之后一个重要特征是反射（reflection），它允许程序在运行的时候动态的生成对象、执行对象的方法、改变对象的属性，spring就是通过反射来实现注入的。

　　理解了IoC和DI的概念后，一切都将变得简单明了，剩下的工作只是在spring的框架中堆积木而已。

三、我对 IoC(控制反转)和 DI(依赖注入)的理解

　　在平时的java应用开发中，我们要实现某一个功能或者说是完成某个业务逻辑时'至少需要'两个或'以上的对象'来协作完成，
        在没有使用Spring的时候，每个对象在'需要使用他的合作对象'时，
        自己均要使用像 'new object()' 这样的语法来将合作对象创建出来，
        这个合作对象是由自己主动创建出来的，创建合作对象的主动权在自己手上，
        自己'需要哪个合作对象'，就主动去创建，创建合作对象的'主动权'和'创建时机'是由自己把控的，
        而这样就会使得对象间的耦合度高了，

        A对象需要使用合作对象B来共同完成一件事，A要使用B，那么A就对B产生了'依赖'，
            也就是 A和B之间存在一种耦合关系，并且是紧密耦合在一起，
        
        而使用了Spring之后就不一样了，创建合作对象B的工作是由 Spring来做的，Spring'创建好B对象'，
            然后存储到一个容器里面，当 A对象需要使用B对象时，Spring就从存放对象的那个容器里面
            取出A要使用的那个B对象，然后交给 A对象使用，
        至于 Spring是如何创建那个对象，以及什么时候创建好对象的，
            A对象不需要关心这些细节问题(你是什么时候生的，怎么生出来的我可不关心，能帮我干活就行)，
            A得到Spring给我们的对象之后，两个人一起协作完成要完成的工作即可。

　　所以'控制反转' IoC(Inversion of Control) 是说创建对象的控制权进行转移，
        以前 创建对象的主动权和创建时机是由'自己把控'的，而现在这种权力'转移到第三方'，
        比如 '转移交给了IoC容器'，它就是一个专门用来'创建对象的工厂'，你要什么对象，它就给你什么对象，
    有了 IoC容器，依赖关系就变了，原先的依赖关系就没了，它们都'依赖IoC容器'了，通过IoC容器来建立它们之间的关系。

这是我对 Spring的IoC(控制反转)的理解。
        DI(依赖注入)其实就是 IOC(控制反转) 的另外一种说法，
        DI 是由Martin Fowler 在2004年初的一篇论文中首次提出的。

        他总结：
                控制的什么被反转了？
        就是：
                获得依赖对象的方式反转了。