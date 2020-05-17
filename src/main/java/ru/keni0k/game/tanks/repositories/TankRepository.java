package ru.keni0k.game.tanks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keni0k.game.tanks.models.Tank;

public interface TankRepository extends JpaRepository<Tank, Long> {

}
