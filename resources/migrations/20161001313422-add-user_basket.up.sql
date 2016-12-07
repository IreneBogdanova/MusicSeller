CREATE TABLE user_basket (
  user_email VARCHAR(50) NOT NULL ,
  audio_id INT NOT NULL,
  PRIMARY KEY (user_email, audio_id),
  INDEX fk_user_basket_user (user_email ASC),
  INDEX fk_user_basket_audio (audio_id ASC),
  CONSTRAINT fk_user_basket_user
  FOREIGN KEY (user_email)
  REFERENCES user (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_user_basket_audio
  FOREIGN KEY (audio_id)
  REFERENCES audio (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);