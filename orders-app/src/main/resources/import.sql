insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (1, current_date, 1, 'order-created')
insert into order_status (order_id, date_created, status_id, status_description) values (1, current_date, 2, 'order-confirmed')
insert into order_status (order_id, date_created, status_id, status_description) values (1, current_date, 9, 'order-canceled')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (1, 1, 1, 100, 10.5)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (1, 1, 2, 50, 5.5)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (1, 1, 3, 25, 1.5)

insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (2, current_date, 1, 'order-created')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (2, 2, 1, 100, 6.5)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (2, 3, 1, 10, 6.5)

insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (3, current_date, 1, 'order-created')
insert into order_status (order_id, date_created, status_id, status_description) values (3, current_date, 2, 'order-confirmed')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (3, 4, 4, 2, 15.0)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (3, 4, 5, 3, 11.0)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (3, 5, 6, 10, 7.25)

insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 1, 'order-created')
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 2, 'order-confirmed')
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 3, 'in-preparation')
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 4, 'ready-for-delivery')
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 5, 'order-dispatched')
insert into order_status (order_id, date_created, status_id, status_description) values (4, current_date, 6, 'order-delivered')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (4, 6, 7, 150, 1.0)

insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (5, current_date, 1, 'order-created')
insert into order_status (order_id, date_created, status_id, status_description) values (5, current_date, 9, 'order-canceled')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (5, 7, 8, 1, 20)

insert into orders (date_created) values (current_date)
insert into order_status (order_id, date_created, status_id, status_description) values (6, current_date, 1, 'order-created')
insert into order_status (order_id, date_created, status_id, status_description) values (6, current_date, 2, 'order-confirmed')
insert into order_status (order_id, date_created, status_id, status_description) values (6, current_date, 3, 'in-preparation')
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (6, 8, 9, 15, 6.5)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (6, 8, 10, 10, 1.75)
insert into order_items (order_id, product_id, fruit_id, quantity, price) values (6, 8, 11, 20, 10.75)