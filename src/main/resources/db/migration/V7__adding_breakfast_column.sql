RENAME TABLE DinnerEvent TO MealEvent;
ALTER TABLE PupilInfo MODIFY COLUMN dinnerPermitted BIT (1) NOT NULL DEFAULT 0;
ALTER TABLE PupilInfo ADD COLUMN breakfastPermitted BIT(1) NOT NULL DEFAULT 0;