package core;

import User.*;
import Equipment.*;
import Rental.*;
import Billing.*;

public record SystemServices(
    UserController userService,
    CategoryController categoryService,
    EquipmentController equipmentService,
    RentalController rentalService,
    BillingController billingService
) {}
