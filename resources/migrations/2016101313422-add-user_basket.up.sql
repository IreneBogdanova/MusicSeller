CREATE TABLE user_basket (
  user_id  INT NOT NULL,
  audio_id INT NOT NULL,
  PRIMARY KEY (user_id, audio_id),
  INDEX fk_user_basket_user (user_id ASC),
  INDEX fk_user_basket_audio (audio_id ASC),
  CONSTRAINT fk_user_basket_user
  FOREIGN KEY (user_id)
  REFERENCES user (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_user_basket_audio
  FOREIGN KEY (audio_id)
  REFERENCES audio (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);