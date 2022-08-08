CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS comments
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    product_id uuid NOT NULL,
    creation_date timestamp with time zone,
    text character varying(100) NOT NULL,
    CONSTRAINT comments_pkey PRIMARY KEY (id)
)