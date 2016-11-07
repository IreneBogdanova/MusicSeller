CREATE TABLE user
(
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(50) UNIQUE not null,
  pass  VARCHAR(120)  not null,
  money FLOAT
);
