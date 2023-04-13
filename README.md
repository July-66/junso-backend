下载Java8
yum install -y java-1.8.0-openjdk*

下载maven
wget https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
tar -zxvf apache-maven-3.6.3-bin.tar.gz

编辑环境变量
vim /etc/profile
在尾部增加
export PATH=$PATH:/opt/apache-maven-3.6.3/bin

让刚配置的环境变量生效
source /etc/profile

测试是否生效
mvn -v

配置镜像和jdk版本
vim /opt/apache-maven-3.6.3/conf/settings.xml
添加
#在mirrors下添加 阿里云加速镜像
<mirror>
     <id>alimaven</id>
     <name>aliyun maven</name>
     <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
     <mirrorOf>central</mirrorOf>
</mirror> 
 
#在profiles下添加jdk版本详细
<profile>    
     <id>jdk-1.8</id>    
     <activation>    
       <activeByDefault>true</activeByDefault>    
       <jdk>1.8</jdk>    
     </activation>    
       <properties>    
         <maven.compiler.source>1.8</maven.compiler.source>    
         <maven.compiler.target>1.8</maven.compiler.target>    
         <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>    
       </properties>    
</profile>


打包构建，跳过测试
git clone xxx 下载代码

打包构建，跳过测试
mvn package -DskipTests
加个 nohup     &后 台运行
nohup java -jar ./junso-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
