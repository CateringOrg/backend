package pl.edu.pw.ee.catering_backend.infrastructure.db.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.catering_backend.infrastructure.db.UserDb;

public interface UserRepository extends JpaRepository<UserDb, String> {

    Optional<UserDb> findByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.wallet.amountOfMoney = :amountOfMoney WHERE u.login = :login")
    void updateWalletAmount(@Param("amountOfMoney") BigDecimal amountOfMoney, @Param("login") String login);}
