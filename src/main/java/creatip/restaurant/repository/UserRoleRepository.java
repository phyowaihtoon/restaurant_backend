package creatip.restaurant.repository;

import creatip.restaurant.domain.UserRole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findOneByName(String roleName);
}
