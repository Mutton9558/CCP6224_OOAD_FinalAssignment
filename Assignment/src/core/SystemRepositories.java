package core;

import Equipment.*;

public record SystemRepositories(
        CategoryDB categoryRepository,
        EquipmentDB equipmentRepository
) {}
