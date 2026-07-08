package core;

import Equipment.*;
import Rental.*;
import Billing.*;

public record SystemServices(
    CategoryController categoryService,
    EquipmentController equipmentService,
    RentalController rentalService,
    BillingController billingService
) {}
