package hello.hellospring.domain;

import javax.persistence.*;

/* JPA를 사용하기 위해서는 entity를 mapping 해야함
*  JPA는 Interface라고 생각하며, 현 강의에서는 JPA중 hibernate만 사용할 것이다
*  JPA -> 객체 + ORM (Object Relational database Mapping)
* Mapping은 annotaion으로 진행한다.
*/
/* JPA가 관리하는 Entity임을 알린다 */
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) /* Database 설정할 때, db에서 알아서 ID를 생성했었음 (Identity 전략이라고 불름)*/
    private Long id;

    //@Column(name = "username") 컬럼 명이 다를 경우 (?) 내가 지정한 column의 이름으로 맵핑할 수 있음
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
