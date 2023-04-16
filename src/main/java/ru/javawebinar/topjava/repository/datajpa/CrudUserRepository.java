package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User getByEmail(String email);

   // @EntityGraph(attributePaths = {"meals", "roles"})
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.meals WHERE u.id=?1")
   // @QueryHints(value = { @QueryHint(name = HINT_PASS_DISTINCT_THROUGH, value = "false")})
    User getWithMealsDistinctBy(int id);
}
