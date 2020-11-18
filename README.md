# Bug simulation using order by ignoreCase with non-String column in QueryDSL
## Description:
Project to simulate a bug when assembling an SQL expression using QueryDSL with 'sort by true' in non-String columns.

This bug is complicated to simulate because it depends on the database, for columns of type Date (Timestamp) for example:
* Postgres, HSQL: the query will throw an exception;
* ORACLE: the query is executed, but the order is wrong 
(I believe that in fact Oracle uses Locale to convert the Date to String, and in the Brazilian standard 'dd/MM/yyyy' makes the order wrong, as the ideal would be : 'yyyy/MM/dd';
* H2: the query is executed correctly (at least in tests, it can be the same scenario as before, as it was with Locale.US the conversion from Date to String was consistent.

This bug was reported in 2007, but without correction (see the original problem below).
* My Issue: https://jira.spring.io/browse/DATAJPA-1779
* Original Issue: https://jira.spring.io/browse/DATAJPA-1198
* PullRequest: https://github.com/spring-projects/spring-data-jpa/pull/428/

## How tests?
* First run the command: **mvn clean install -DskipTests**, so that the QueryDSL classes are generated, they will be in the target package of the entity classes with a Q prefix.
* Example this project: **Example.class** and **QExample.class** 
  * ![image](https://user-images.githubusercontent.com/9442331/99594021-5b347400-29d1-11eb-95a3-9735b34cda4a.png)
* After compilation, execute the unitaries tests from class: **ExampleRepositoryTest** 
* Like on the print screen below, the tests **findAllQueryDslOrderByDateProperty** e **findAllQueryDslOrderByDateProperty** fails and 
tests **findAllJpaRepositoryOrderByDateProperty** and **findAllQueryDslOrderByStringProperty** passes:
  * ![image](https://user-images.githubusercontent.com/9442331/99594433-f2013080-29d1-11eb-978e-d051b63f29e1.png)
* Finally, update the **SpringBoot** version in pom.xml to **2.3.5.RELEASE**, rerun the tests and everyone passes!

## Technologies:
* JAVA 11
* SpringBoot 2.3.4.RELEASE / 2.3.5.RELEASE
* HSQL 2.5.1
* QueryDSL 4.3.1
* JUnit 5
