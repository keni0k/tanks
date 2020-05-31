package ru.keni0k.game.tanks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keni0k.game.tanks.models.Bullet;
import ru.keni0k.game.tanks.models.World;

import java.util.List;

public interface BulletRepository extends JpaRepository<Bullet, Long> {

}
