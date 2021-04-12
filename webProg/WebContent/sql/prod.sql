SELECT *
FROM   tab;

SELECT NVL(MAX(product_id)
          ,0) + 1
FROM   products;

SELECT *
FROM   products
ORDER  BY 1 DESC;

-- Create table
create table PRODUCTS
(
  product_id    NUMBER,
  product_name  VARCHAR2(100),
  product_price NUMBER,
  product_cont  VARCHAR2(3000),
  product_img   VARCHAR2(100),
  likeit        NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
