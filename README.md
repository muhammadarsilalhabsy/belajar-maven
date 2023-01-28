# Build Automations

Build automasi merupakan proses meng-otomatisasi tahapan pembuatan software
dan hal-hal yang berhubungan dengannya, seperti kompilasi source code
menjadi binary code, mem-package binary code menjadi distribution file (.jar),
membuat dokumentasi, menjalakan automated test, sampai menejemen dependency

# Installing

1. mvn archetype:generate (Generating maven folder)
2. maven-archetype-quickstart (Default maven template)

# Maven Lifecycle

untuk menjalakan life cycle, dengan menggunakan perintah `mvn nameLifecycle`
berikut daftar lifcycle di maven:

1. clean (menghapus folder target/ tempat hasil kompilasi code)
2. test-compile (untuk melakukan kompilasi source code test ke binary file)
3. test (untuk menjalankan unit test)
4. package (untuk membuat distribution file `.jar>` atau `.war`)
5. compile (untuk melakukan kompilasi source code ke binary file)
6. instal (untuk menginstal project ke local repository)
7. deploy (deploy project ke remote repository di server).

running .jar file
java -jar fileName.jar
example : java -jar target/belajar-apache-maven-1.0-SNAPSHOT.jar

# Dependency

Saat hendak menambahkan dependency ke project maven, kita harus menentukan
scope dependency yang akan kita tambahkan. maven memiliki banyak jenis scope yaitu
yang biasa diguanakan cuman compile dan test.

- compile merupakan scope default. Compile memiliki arti bahwa dependency
  tersebut akan diguankana untuk build, test, dan menjalankan project.
- test merupakan scope yang hanya akan digunakan pada bagian test project (unit testing).

contoh menambahkan dependency:

```xml

<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.11</version>
        <scope>test</scope> <!-- Scope yang hanya diguanakan untuk unit testing-->
    </dependency>
</dependencies>

```

Source untuk mendapatkan dependency maven:

1. [search.maven.org](https://search.maven.org/)
2. [mvnrepository.com](https://mvnrepository.com)

## Menambahkan Repository sendiri

Selain depenedency yang disediakan maven, kita juga bisa menambahkan
dependecy repository milik kita sendiri. untuk menambahakan repository bisa dilihat seperti dibawah ini.

```xml

<repositories>
    <repository>
        <id>example id</id>
        <name>example name</name>
        <url>https://example.com</url>
    </repository>
</repositories>
```

# Properties

Maven mendukung properties untuk menyimpan konfigurasi layaknya sebuah variable
berikut cara membuat konfigurasi di maven

```xml

<properties>
    <!--  cara membuat properties  -->
    <variable.name>example configuration</variable.name>
</properties>

<dependencies>
  <dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <!-- cara menggunakan properties-->
    <version>${gson.version}</version>
  </dependency>
</dependencies>
        
```

# Membuat Distribution File

Secara default maven mendukung pembuatan distribution file menggunakan
lifecycle `pacakage`, namun distribution yang dilakukan menggunakan `pacakage`
masih memiliki kekurangan karena hanya berisikan binary code dari project kita saja
dan tidak menginclude dependency lain sehingga program `.jar` tidak bisa berjalan.
Solusi dari itu kita bisa menggunakan plugin yang sudah disediakan oleh maven salah satunya
[Assembly plugin](https://maven.apache.org/plugins/maven-assembly-plugin/usage.html).
Untuk me-running Assemby plugin bisa menggunakan perintah `mvn pacakge assembly:single`,
untuk lebih detailnya bisa dilihat [disini](https://maven.apache.org/plugins/maven-assembly-plugin/usage.html).

example menambahkan assembly plugin 
```xml
<plugin>
  <artifactId>maven-assembly-plugin</artifactId>
  <version>3.4.2</version>
  <configuration>
    <descriptorRefs>
      <descriptorRef>jar-with-dependencies</descriptorRef>
    </descriptorRefs>
    <archive>
      <manifest>
        <mainClass>com.tutorial.maven.App</mainClass> <!-- lokasi Main file -->
      </manifest>
    </archive>
  </configuration>
  <executions>
    <execution>
      <id>make-assembly</id> <!-- this is used for inheritance merges -->
      <phase>package</phase> <!-- bind to the packaging phase -->
      <goals>
        <goal>single</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

# Multi Module Project

Saat aplikasi kita sudah besar, ada baiknya project aplikasi kita dipecah menjado modular
misal model, view, controller, service, repository dan lain sebagainya.
By default Module di maven itu terisolasi, Module di maven harus memiliki parent yang dimana parentnya adalah 
project yang pertama kali dibuat. Parentya harus di registrasikan bahwah project 
ini adalah multi module. Berikut langkah langkah membuat porject multi module:

1. Membuat directory baru
2. Menambahkan file `pom.xml` pada tiap directory yang baru dibuat
3. Melakukan konfigurasi module

example `pom.xml` di modoule belajar-apache-maven-data
```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- parent pom.xml sesuai dengan project yang pertama kali dibuat -->
    <parent>
        <groupId>m19y__</groupId>
        <artifactId>belajar-apache-maven</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <!-- memberi nama module menggunakan artifactId -->
    <artifactId>belajar-apache-maven-data</artifactId> <!-- sama dengan nama foldernya -->
  
</project>

```
4. Memberi tahu parentnya bahwa packaging-nya menjadi pom

example menambahkan packaging
```xml
<!--  pom.xml pada folder utama (belajar-apache-maven)  -->
<project>
  [...]
    <groupId>m19y__</groupId>
    <artifactId>belajar-apache-maven</artifactId>
    <version>1.0-SNAPSHOT</version>
  
    <packaging>pom</packaging> <!-- mengubah default packging dari .jar, menajadi pom -->
  [...]
</project>

```
5. Meregistrasikan modules

example meregistrasikan module yang sudah dibuat
```xml
<project>
  [...]
    <groupId>m19y__</groupId>
    <artifactId>belajar-apache-maven</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging> 
  
    <name>belajar-apache-maven</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>
  
    <!-- menambahkan module -->
    <modules>
      <module>belajar-apache-maven-app</module>
      <module>belajar-apache-maven-data</module>
    </modules>
  
  [...]
</project>
```

6. selanjutnya reload lagi mavennya
7. Untuk menginclude dependency di module lain

berikut cara menggunakan source code di module lain
```xml
<!-- 
menambahkan source code dari modlue belajar-apache-maven-data
ke belajar-apache-maven-app
-->
<dependencies>
    <dependency>
        <groupId>m19y__</groupId>
        <artifactId>belajar-apache-maven-data</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```
8. kemudian semua depenedecy telah terhubung dan bisa di run lagi

# Dependency Management

Saat kita menggunakan banyak dependency terdapat banyak masalah yang kemungkinana akan 
ditemukan salah satunya nama dependency yang sama tapi dengan versi yang berbeda.
Tidak perlu khawatir maven juga mendukung dependency management, yang dimana
kita bisa memasukan daftar dependency di parent module beserta versinya, dan menggunakan
di sub module tanpa harus mendeklarasikan versinya. Berikut langkah-langkah yang harus dilakukan:

1. menambhakan tag `<dependencyManagement></dependencyManagement>`

example:
```xml
<!--kita harus membungkus semua dependency menggunakan tag <dependencyManagement>-->
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
        <scope>test</scope>
      </dependency>
      <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>${gson.version}</version>
      </dependency>
    </dependencies>  
  </dependencyManagement>

```
2. Cara menggunakannya di sub module

example:
```xml
<!-- Menambahkan dependency ke sub module (belajar-apache-maven-app)-->
<dependencies>
  <dependency>
    <groupId>m19y__</groupId>
    <artifactId>belajar-apache-maven-data</artifactId>
    <version>${project.version}</version>
  </dependency>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
  </dependency>
</dependencies>
```






