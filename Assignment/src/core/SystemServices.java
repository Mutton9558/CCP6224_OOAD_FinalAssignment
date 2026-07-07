package core;

import Equipment.*;
import Rental.*;

public record SystemServices(
    CategoryController categoryService,
    EquipmentController equipmentService,
    RentalController rentalService
) {}
