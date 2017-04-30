INSERT INTO Tax (id, percent) VALUES (1, 21);
INSERT INTO TaxCurrent (taxId) VALUE (1);

INSERT INTO Supplier (id, code, name) VALUE (1, 'M1', 'Maxima');
INSERT INTO Supplier (id, code, name) VALUE (2, 'N1', 'Norfa');
INSERT INTO Supplier (id, code, name) VALUE (3, 'R1', 'Rimi');
INSERT INTO ProductGroup (id, name, kcal100) VALUE (1, 'Makaronai', 350);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (2, 'Grikiai', 350);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (3, 'Aliejus', 900);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (4, 'Bananai', 150);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (5, 'Bulvės', 120);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (6, 'Burokėliai', 130);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (7, 'Klauliena', 250);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (8, 'Pienas', 42);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (9, 'Kava', 1);
INSERT INTO ProductGroup (id, name, kcal100) VALUE (10, 'Cukrus', 385);

INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (1, 'Gintariniai', 'GRAM', 500, 1);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (2, 'Optima Linija', 'GRAM', 400, 1);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (3, 'FASMA', 'GRAM', 800, 2);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (4, 'Skanėja', 'GRAM', 800, 2);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (5, 'Rapsų aliejus', 'GRAM', 1000, 3);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId)
   VALUE (6, 'Saulegražų aliejus', 'GRAM', 1000, 3);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (7, 'Geltoni bananai', 'GRAM', 1000, 4);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (8, 'Žali bananai', 'GRAM', 1000, 4);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (9, 'Šviežios bulvės', 'GRAM', 1000, 5);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (10, 'Extra burokeliai', 'GRAM', 1000, 6);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId)
   VALUE (11, 'Atšaldyta kiauliena', 'GRAM', 1000, 7);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId) VALUE (12, 'Kava Jacobs', 'GRAM', 500, 9);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId)
   VALUE (13, 'Marijampolės pienas', 'GRAM', 900, 8);
INSERT INTO Product (id, name, measureUnit, unitWeight, productGroupId)
   VALUE (14, 'Panevežio cukrus', 'GRAM', 1000, 10);

INSERT INTO DishGroup (id, name) VALUES (1, 'Sriubos');
INSERT INTO DishGroup (id, name) VALUES (2, 'Košės');
INSERT INTO DishGroup (id, name) VALUES (3, 'Salotos');
INSERT INTO DishGroup (id, name) VALUES (4, 'Vaisiai');
INSERT INTO DishGroup (id, name) VALUES (5, 'Desertas');
INSERT INTO DishGroup (id, name) VALUES (6, 'Gerimai');
INSERT INTO DishGroup (id, name) VALUES (7, 'Karšti patiekalai');

INSERT INTO Dish (id, name, dishGroupId) VALUES (1, 'Barščiai', 1);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (1, 5, 50);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (1, 6, 40);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (1, 7, 30);

INSERT INTO Dish (id, name, dishGroupId) VALUES (2, 'Makaronai su kotletu', 7);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (2, 1, 60);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (2, 7, 30);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (2, 3, 5);

INSERT INTO Dish (id, name, dishGroupId) VALUES (3, 'Kava su pienu', 6);
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (3, 8, 50);
#pienas
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (3, 9, 5);
#kava
INSERT INTO DishItem (dishId, productGroupId, outputWeight) VALUES (3, 10, 5);
#cukrus

INSERT INTO MealType (name) VALUES ('Pusryčiai'), ('Priešpiečiai'), ('Pietus'), ('Vakariene ir pavakariai');

INSERT INTO ReceiptStatement (id, supplierId, dateCreated) VALUE (1, 1, NOW() - INTERVAL 6 DAY);
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (1, 1, 2, 0.45, 15, (SELECT name
                            FROM Product
                            WHERE id = 2), (SELECT measureUnit
                                            FROM Product
                                            WHERE id = 2), (SELECT unitWeight
                                                            FROM Product
                                                            WHERE id = 2));
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (2, 1, 9, 0.8, 8, (SELECT name
                          FROM Product
                          WHERE id = 9), (SELECT measureUnit
                                          FROM Product
                                          WHERE id = 9), (SELECT unitWeight
                                                          FROM Product
                                                          WHERE id = 9));

INSERT INTO ReceiptStatement (id, supplierId, dateCreated) VALUE (2, 2, NOW() - INTERVAL 4 DAY);
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (3, 2, 6, 1.17, 8, (SELECT name
                           FROM Product
                           WHERE id = 6), (SELECT measureUnit
                                           FROM Product
                                           WHERE id = 6), (SELECT unitWeight
                                                           FROM Product
                                                           WHERE id = 6));

INSERT INTO ReceiptStatement (id, supplierId, dateCreated) VALUE (3, 3, NOW() - INTERVAL 2 DAY);
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (4, 3, 11, 3.65, 5.5, (SELECT name
                              FROM Product
                              WHERE id = 11), (SELECT measureUnit
                                               FROM Product
                                               WHERE id = 11), (SELECT unitWeight
                                                                FROM Product
                                                                WHERE id = 11));
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (5, 3, 3, 1, 2, (SELECT name
                        FROM Product
                        WHERE id = 3), (SELECT measureUnit
                                        FROM Product
                                        WHERE id = 3), (SELECT unitWeight
                                                        FROM Product
                                                        WHERE id = 3));
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (6, 3, 5, 1.3, 3, (SELECT name
                          FROM Product
                          WHERE id = 5), (SELECT measureUnit
                                          FROM Product
                                          WHERE id = 5), (SELECT unitWeight
                                                          FROM Product
                                                          WHERE id = 5));

INSERT INTO ReceiptStatement (id, supplierId, dateCreated) VALUE (4, 2, NOW());
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (7, 4, 12, 2.5, 2, (SELECT name
                           FROM Product
                           WHERE id = 12), (SELECT measureUnit
                                            FROM Product
                                            WHERE id = 12), (SELECT unitWeight
                                                             FROM Product
                                                             WHERE id = 12));
INSERT INTO ReceiptItem (id, receiptStatementId, productId, unitPrice, quantity, productNameSnapshot, productMeasureUnitSnapshot, productUnitWeightSnapshot)
VALUES (8, 4, 13, 1, 5, (SELECT name
                         FROM Product
                         WHERE id = 13), (SELECT measureUnit
                                          FROM Product
                                          WHERE id = 13), (SELECT unitWeight
                                                           FROM Product
                                                           WHERE id = 13));

INSERT INTO Menu (id, date) VALUES (1, now());
INSERT INTO Meal (id, menuId, mealTypeId) VALUES (1, 1, 1);
#pusryciai
INSERT INTO MealItem (id, mealId, dishId) VALUES (1, 1, 1);
#barsciai
INSERT INTO MealItem (id, mealId, dishId) VALUES (2, 1, 2);
#makaronai + kotletas

INSERT INTO Menu (id, date) VALUES (2, now());
INSERT INTO Meal (id, menuId, mealTypeId) VALUES (2, 2, 2);
#priespeciai
INSERT INTO MealItem (id, mealId, dishId) VALUES (3, 2, 3);
#kava su pienu
