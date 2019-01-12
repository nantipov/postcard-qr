CREATE TABLE postcard (
  id BIGSERIAL PRIMARY KEY,
  message_code TEXT NOT NULL UNIQUE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE access_log (
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  ip_address TEXT,
  ip_info TEXT,
  user_agent TEXT,
  message_code TEXT,
  request_path TEXT
);
