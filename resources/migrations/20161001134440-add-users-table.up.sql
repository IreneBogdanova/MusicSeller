CREATE TABLE user
(
  id    INTEGER PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(50) UNIQUE NOT NULL,
  pass  VARCHAR(120)       NOT NULL,
  money FLOAT,
  role_id INT NOT NULL,
  INDEX fk_user_role_idx (role_id ASC),
  CONSTRAINT fk_user_role
  FOREIGN KEY (role_id)
  REFERENCES role (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
