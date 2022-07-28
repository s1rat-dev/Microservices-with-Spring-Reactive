CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS banners
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    banner_url character varying(100),
    advertiser_name character varying(30),
    creation_date timestamp with time zone NOT NULL,
    CONSTRAINT banners_pkey PRIMARY KEY (id)
);