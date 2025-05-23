package pizzashop.validation;

import pizzashop.model.MenuItem;

public class MenuItemValidation implements Validator<MenuItem> {

    @Override
    public void validate(MenuItem entity) throws ValidationException {
        StringBuilder errors = new StringBuilder();
        if (entity.getName() == null || entity.getName().isEmpty()) {
            errors.append("Name cannot be empty!\n");
        }
        if (entity.getPrice() <= 0) {
            errors.append("Price must be greater than 0!\n");
        }
        if (errors.length() > 0) {
            throw new ValidationException(errors.toString());
        }
    }
}
