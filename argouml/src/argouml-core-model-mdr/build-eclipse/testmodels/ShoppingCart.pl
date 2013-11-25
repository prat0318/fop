dbname(ShoppingCart, [ShoppingCart, Customer, CreditCard, ItermToPurchase, ]).
class(class_1, ShoppingCart, vk_public).
attribute(attr_1, subTotalMoney, vk_public).
attribute(attr_2, vatAmount, vk_public).
attribute(attr_3, totalMoeny, vk_public).

class(class_2, Customer, vk_public).
attribute(attr_4, customerName, vk_public).
attribute(attr_5, emailAddress, vk_private).

class(class_3, CreditCard, vk_public).
attribute(attr_6, issuer, vk_public).
attribute(attr_7, cardNumber, vk_private).

class(class_4, ItermToPurchase, vk_public).
attribute(attr_8, quantity, vk_public).
attribute(attr_9, pricePerUnit, vk_public).

composition(assoc_1, Customer_Cart).
association(assoc_end_1, class_1, 0..inf).
association(assoc_end_2, class_2, 1..1).

composition(assoc_2, Cart_Item).
association(assoc_end_3, class_1, 1..1).
association(assoc_end_4, class_4, 1..inf).

composition(assoc_3, Cart_Card).
association(assoc_end_5, class_1, 1..1).
association(assoc_end_6, class_3, 1..1).
