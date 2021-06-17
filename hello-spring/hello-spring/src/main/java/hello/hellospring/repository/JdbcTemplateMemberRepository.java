package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/* 왜 Template이라고 불릴까 ??
*
* A: 디자인 패턴중에 Template method pattern이 있고, 해당 로직이 많이 포함이 되어 있으므로 ,,,
*
*/
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    /* Jdbc Template은 인젝션을 받을 수 없다. 대신 datasource를 인젝션 받아야함*/
    /* class에 생성자가 한개만 등록이 되어 있다면, @Autowired를 생략할 수 있다. 어차피 주입 방법이 한가지이기 때문인것같음 */
    /* 그래도 난 초보니까 달아놓자 */
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource); /* spring에서도 이러한 형태를 권장하며, Spring에서 알아서 인젝션 해준다 */

    }

    @Override
    public Member save(Member member) {
        /* SimpleJdbcInsert : query를 만들 필요가 없음. */
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /* 실질적으로 한건의 자료를 반환하도록 작성해야 하나, 이번 강의는 입문용이기 때문에 단순하게 설명하기 위함 */
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }


    /* RowMapper는 무엇일까 ???
    * database에 조회한 내용을 개발자가 원하는 형태 (틀)로 담는 과정을 구현한 method
    *
    */
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
        /* 원래 강의에선 이러한 형태로 사용했으나, 람다로 변경할 수 있음 : alt + enter key
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };
        */
    }
}
