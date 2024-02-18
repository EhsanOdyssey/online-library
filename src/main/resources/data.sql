INSERT INTO online_library.`user` (id,first_name,last_name,username) VALUES ('6f154940-b713-4819-a135-3ce4accabbad','ehsan','shahmirzaloo','ehsan.odyssey');

INSERT INTO online_library.book (id,isbn_13,pages,title) VALUES ('0719d661-f2fb-4186-90bf-1ed1a4b02628','hb_jpa-100',500,'Hibenate');
INSERT INTO online_library.book (id,isbn_13,pages,title) VALUES ('67542bd3-8284-49f4-b3bd-8017f06ed197','jc_001',100,'Java Core');
INSERT INTO online_library.book (id,isbn_13,pages,title) VALUES ('e36d9510-9d92-4f6c-91fb-f7c1e69c9283','web-800',600,'J2EE');

INSERT INTO online_library.library (id,active,created_at,name,user_id) VALUES ('082a3c02-dc15-4d8c-ab1a-ee33ff6ce389',0,'2024-02-18 13:45:03.651020','My Java','6f154940-b713-4819-a135-3ce4accabbad');

INSERT INTO online_library.library_books (library_id,book_id) VALUES ('082a3c02-dc15-4d8c-ab1a-ee33ff6ce389','0719d661-f2fb-4186-90bf-1ed1a4b02628');
