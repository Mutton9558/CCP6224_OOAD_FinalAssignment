package core;

import Equipment.*;
import Rental.*;
import Billing.*;
import User.UserController;

public record SystemServices(
    CategoryController categoryService,
    EquipmentController equipmentService,
    RentalController rentalService,
    BillingController billingService,
    UserController userService
) {}
