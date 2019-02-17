# Analyzer

It is a simple application which allows analyzing HTML and finds a specific element, 
even after changes, using a set of extracted attributes. In project presents validation for the file extension and if the file exists.

## Requirements

Install Java 8 on your pc and gradle 4.5
Below example how to check java and gradle versions

```
java -version
gradle -v
```

## Clone and Build project
Go to [Analizer project](https://github.com/AbramovEvgeniy/analyzer.git ) page and clone it.

On the root of project execute:  
```      
    gradle jar
```

## Usage

Go to build/libs and execute

```
    java -jar analyzer-1.0.jar <origin_file_path> <other_sample_file_path> <elementId>
```
elementId - is not mandatory.
In case of absent default value "make-everything-ok-button" will be used.



