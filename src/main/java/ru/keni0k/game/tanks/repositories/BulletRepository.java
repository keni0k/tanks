package ru.keni0k.game.tanks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keni0k.game.tanks.models.Bullet;

public interface BulletRepository extends JpaRepository<Bullet, Long> {

}