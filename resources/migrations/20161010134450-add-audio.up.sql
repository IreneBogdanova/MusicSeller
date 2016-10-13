CREATE TABLE audio (
  id               INTEGER PRIMARY KEY AUTO_INCREMENT,
  name             VARCHAR(45) NOT NULL,
  artist           VARCHAR(45) NOT NULL,
  upload_date      DATETIME    NOT NULL,
  price            DOUBLE      NULL,
  path             VARCHAR(50) NOT NULL,
  listen_auth_only BIT         NULL,
  playback_type    INT         NOT NULL,
  CONSTRAINT fk_audio_playback_type
  FOREIGN KEY (playback_type)
  REFERENCES playback_type (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
