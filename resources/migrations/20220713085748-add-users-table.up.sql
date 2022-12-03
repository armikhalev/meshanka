CREATE TABLE users
(id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 email VARCHAR(30),
 admin BOOLEAN,
 last_login TIMESTAMP,
 is_active BOOLEAN,
 pass VARCHAR(300));
