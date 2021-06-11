package hello.hellospring.controller;

/* 굳이 MemberForm class를 생성해서 name을 주고 받는 이유가 무엇일까?
    따로 annotation을 사용하지도 않는데 말이지 ,,,

    A : 컨트롤러에 Member를 그대로 받아도 기술적인 문제는 없다. 하지만 실무에서는 컨트롤러에 넘어오는 정보가
    Member가 필요한 데이터 이상으로 많은 데이터들이 들어온다. 예를 들어, 회원 정보 뿐만아니라 약관 정보, 화면을 처리하는
    정보등이 넘어올 수 있다. 따라서 Member와 MemberForm을 분리하는것이 좋다.

    VO / DTO 비즈니스 복잡도에 따라 다르게 사용 / 커맨드 객체
 */
public class MemberForm {
    /* createMemberForm.html에서 name="name"으로 설정했기 때문에, 이 class의 name에 저장이 된다.*/
    /* private 멤버이기 때문에, spring이 setter(set{html에서 지정한 값}일치필요요)를 통해서 ata를 setting 함 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
