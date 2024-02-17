# Model

## Table of Contents

* [General Info](#general-information)
* [Usage](#usage)

## General Information

Jar provide base classes to JPA Entities.

## Usage

1. Example of [AbstractEntity.java](src%2Fmain%2Fjava%2Fpl%2Fapp%2Fcommon%2Fmodel%2FAbstractEntity.java)

```java

@Entity
@Table(name = "t_organization")
public class OrganizationEntity extends BaseEntity<OrganizationEntity, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id", nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;
    
    // ...
}
```