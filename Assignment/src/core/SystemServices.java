package core;

import Equipment.*;

public record SystemServices(
       CategoryController categoryService,
       EquipmentController equipmentService
) {}
