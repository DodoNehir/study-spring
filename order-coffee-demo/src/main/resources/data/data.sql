-- products
INSERT INTO products VALUES ("1", '아이스 아메리카노', '차가운 ice cold', 3500.0);
INSERT INTO products VALUES ("2", '아메리카노', '따신거', 3000.0);
INSERT INTO products VALUES ("3", '아이스 라떼', '아이스 아메리카노 + 우유', 5500.0);
INSERT INTO products VALUES ("4", '라떼', '아메리카노 + 따신 우유', 5000.0);

-- stores : 하나의 매장에
INSERT INTO stores VALUES ('1', '하남점', '서울', '02-123-4123', '0900', '1800')

-- store_products : 4 종류의 재고가 있는 것. 두 번째 파라미터가 매장을 뜻하고 세 번째가 products 종류, 네 번째가 재고 개수
INSERT INTO store_products VALUES ('1', '1', '1', 10);
INSERT INTO store_products VALUES ('2', '1', '2', 20);
INSERT INTO store_products VALUES ('3', '1', '3', 30);
INSERT INTO store_products VALUES ('4', '1', '4', 40);