ALTER TABLE MealEventLog ADD INDEX dateIndex (date);

ALTER TABLE MealEventLog MODIFY date DATETIME NOT NULL; #5.5 version does not support "default now()"