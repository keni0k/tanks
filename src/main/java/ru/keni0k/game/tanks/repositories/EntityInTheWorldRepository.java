package ru.keni0k.game.tanks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keni0k.game.tanks.models.EntityInTheWorld;
import ru.keni0k.game.tanks.models.GameEntity;

public interface EntityInTheWorldRepository extends JpaRepository<EntityInTheWorld, Long> {

    EntityInTheWorld getByTargetEntity(GameEntity entity);

}
