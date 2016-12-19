# -- Schema

# --- !Ups

CREATE TABLE users (
  id INT NOT NULL PRIMARY KEY, -- Comes from GitHub
  login VARCHAR(255) NOT NULL UNIQUE,
  avatar_url TEXT,
  url TEXT,
  site_admin BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE issues (
  id INT NOT NULL PRIMARY KEY, -- Comes from GitHub
  number INT NOT NULL CHECK (number > 0),
  title TEXT,
  user_id INT NOT NULL REFERENCES users(id),
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  body TEXT,
  url TEXT
);

# --- !Downs

DROP TABLE issues;
DROP TABLE users;