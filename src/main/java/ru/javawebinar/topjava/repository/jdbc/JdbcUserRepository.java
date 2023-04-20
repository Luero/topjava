package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            setRoles(user.id(), user.getRoles());
        } else if (namedParameterJdbcTemplate.update("UPDATE users SET name=:name, email=:email, " +
                        "password=:password, registered=:registered, enabled=:enabled, " +
                        "calories_per_day=:caloriesPerDay WHERE id=:id",
                parameterSource) == 0) {
            return null;
        } else {
            deleteRoles(user.id());
            setRoles(user.id(), user.getRoles());
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id));
        if (user != null) {
            setRoles(user);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
        if (user != null) {
            setRoles(user);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day," +
                "string_agg(ur.role, ', ')  AS roles FROM users u LEFT JOIN user_role ur ON u.id = ur.user_id GROUP BY " +
                "u.id ORDER BY u.name, u.email", ROW_MAPPER);
    }

    private void setRoles(User user) {
        Integer id = user.getId();
        List <Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_role WHERE user_id = ?", Role.class, id);
        user.setRoles(roles);
    }

    private int[] setRoles(int userId, Set<Role> roles) {
        List<Role> rolesList = new ArrayList<>(roles);
        return jdbcTemplate.batchUpdate("INSERT INTO user_role (user_id, role) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, userId);
                ps.setString(2, rolesList.get(i).name());
            }

            @Override
            public int getBatchSize() {
                return rolesList.size();
            }
        });
    }

    public void deleteRoles(int userId) {
        jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", userId);
    }
}
