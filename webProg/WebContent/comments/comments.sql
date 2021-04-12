-- Create table
create table COMMENTS
(
  id      VARCHAR2(10),
  name    VARCHAR2(100),
  content VARCHAR2(300)
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

  -- Create table
create table ID_REPOSITORY
(
  value NUMBER,
  name  VARCHAR2(30)
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
